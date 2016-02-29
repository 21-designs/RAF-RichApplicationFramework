package org.drost.application;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

/**
 * Allows specified viewable objects that extend the {@code Component} type to
 * store and restore their current states. Those states are associated to
 * components that can be visually modified like objects of the types
 * {@code JScrollPane, JSplitPane, JTabbedPane, Window} (For more details read
 * the documentation of {@link #getStateables()}). Therefore this class holds
 * several related inner property classes like {@link WindowProperty},
 * {@link ScrollPaneProperty}, {@link SplitPaneProperty} or
 * {@link TabbedPaneProperty}.
 * 
 * <p>
 * The data storage on the local file system is defined using a local
 * {@link FileStorage} instance which may be initialized by either an
 * object of this type or an file path by the two different constructors.
 * </p>
 * 
 * <p>
 * Two methods {@code store(Component)} and {@code restore(Component)} allows
 * the functionality to write and read component related information to a local
 * file. Class intern there is only one method to iterate over the given
 * component tree while the associated component object is of type
 * {@code Container}. This method is given a operation object that either sets
 * or gets the current component state. The states are stored to a map with the
 * component name as the key. The values and keys are each saved to separate
 * files. Renaming these files or even the component names during such a state
 * session may cause errors or result in wrong restating.
 * </p>
 * 
 * @author kimschorat
 * @since 1.0
 * 
 * @see #store(Component)
 * @see #restore(Component)
 * @see #getStateables()
 *
 */
public class ViewStateManager 
{
	// TODO Store those constants in a separate ApplicationConstants class file.
	private final String STATE_KEYS_EXTENSION = ".stk";
	private final String STATE_VALUES_EXTENSION = ".stv";
	
//	private String dataDirectory = "";	// TODO Create getters and setters
	
	protected FileStorage storage;
	
	/**
	 * Maps the right property to the related class type. Using the class type
	 * as a key returns a {@code Property} object to handle the state of this
	 * object of this type. For example taking the {@code Window.class} returns
	 * a {@code WindowProperty} instance.
	 * 
	 * <pre>
	 * Property p = propertyMap.get(Window.class);
	 * </pre>
	 * 
	 * Use a proper cast like {@code WindowProperty} in this case to gain access
	 * to specific methods.</br>
	 * Note the key type of this map is restricted to
	 * {@code <? extends Component>}.
	 * 
	 * @see Property
	 */
	protected final Map<Class<? extends Component>, Property> propertyMap;
	
	/**
	 * Represents all types that allow to store a state. This is simply the key
	 * {@code Set} of the {@code propertyMap} that contains class types and
	 * related property pairs. This field is {@code final} and unmodifiable.
	 * 
	 * @see Collections#unmodifiableSet(Set)
	 */
	public final Set<Class<?>> stateables;
	
	
	
	
	/**
	 * Defines a state related operation on a certain component. The operation
	 * of this operation is either to extract or to set the GUI state for the
	 * specified component.
	 * 
	 * @author kimschorat
	 * @since 1.0
	 * 
	 * @param <ReturnType>
	 *            Determines the return value of the
	 *            {@code operate(Property, Component, Map)} method. The returned
	 *            value may be the modified {@code stateMap} argument or
	 *            {@code Void} since there is nothing to return.
	 * 
	 * @see #operate(Property, Component, Map)
	 * @see StateGetter
	 * @see StateSetter
	 */
	private interface StateOperation<ReturnType>
	{
		/**
		 * Implement this method to either get or set the GUI related state of
		 * the specified component
		 * 
		 * @param p
		 *            The property object to handle the state operation.
		 * @param c
		 *            The specified {@code Component} object that GUI state is
		 *            handled.
		 * @param stateMap
		 *            The map to either store the state or get the state from.
		 * 
		 * @see Property
		 */
		public ReturnType operate(Property p, Component c, Map<String, Object> stateMap);
	}
	
	
	/**
	 * This class implements the {@code StateOperation} interface. For its
	 * generic type argument this implementation uses
	 * {@code <Map<String, Object>>} to declare a return type. This special
	 * operation is mapped to each component in a component tree and to
	 * extract its GUI state when possible (declared by
	 * {@link ViewStateManager#getStateables()} ).
	 * 
	 * @author kimschorat
	 * 
	 * @since 1.0
	 * 
	 * @see #operate(Property, Component, Map)
	 * @see StateOperation
	 */
	protected class StateGetter implements StateOperation<Map<String, Object>>
	{
		/**
		 * This method implementation extracts the components state object using
		 * the {@code Property} parameter and saves it to the map. Finally it
		 * returns the modified map holding the state of this component. The
		 * {@code Property} instance needs to fit the component type, otherwise
		 * the property handles the invalid class type and mostly will throw an
		 * exception.
		 * 
		 * <p>
		 * Note that the {@code stateMap} cannot be {@code null}.
		 * </p>
		 */
		@Override
		public Map<String, Object> operate(Property p, Component c, Map<String, Object> stateMap) 
		{
			assert stateMap != null;
			
			Object state = p.getState(c);
			if(!isNull(state))
			{
				String key = ViewStateManager.this.getComponentName(c);
				if(!isNull(key))
				{
					stateMap.put(key, state);
				}							
			}
			return stateMap;
		}
	}
	
	
	/**
	 * This class implements the {@code StateOperation} interface. For its
	 * generic type argument this implementation uses
	 * {@code <Void>} to declare a {@code null} return type. This special
	 * operation is mapped to each component in a component tree and to
	 * set its GUI state when possible (declared by
	 * {@link ViewStateManager#getStateables()} ).
	 * 
	 * @author kimschorat
	 * 
	 * @since 1.0
	 * 
	 * @see #operate(Property, Component, Map)
	 * @see StateOperation
	 */
	protected class StateSetter implements StateOperation<Void>
	{

		/**
		 * This method implementation sets the components state object using the
		 * {@code Property} parameter. The related state object is given by the
		 * {@code stateMap} parameter, but does nothing while the map has no
		 * suitable entry for the component key. The {@code Property} instance
		 * needs to fit the component type, otherwise the property handles the
		 * invalid class type and mostly will throw an exception.
		 * 
		 */
		@Override
		public Void operate(Property p, Component c, Map<String, Object> stateMap) 
		{
			String key = getComponentName(c);
			if(!isNull(key))
			{
				// Returns null since no state is available for this component
				Object state = stateMap.get(key);
				if(!isNull(state))
				{
					p.setState(c, state);
				}
			}
			return null;
		}
	}
	
	/**
	 * This operation indicates the the components state should be extracted.
	 * 
	 * @see ViewStateManager#iterateComponentHierarchy(List, Map, StateOperation)
	 */
	protected final StateOperation<Map<String, Object>> GET_VIEW_STATE = ViewStateManager.this.new StateGetter();
	
	/**
	 * This operation indicates the the components state should be set.
	 * 
	 * @see ViewStateManager#iterateComponentHierarchy(List, Map, StateOperation)
	 */
	protected final StateOperation<Void> SET_VIEW_STATE = ViewStateManager.this.new StateSetter();
	
	
	
	
	
	
	
	
	
	/**
	 * Creates a new instance with a reference to a related file directory to
	 * handle file storage.
	 * 
	 * @param directoryPath
	 *            The local directory to store the states.
	 *            
	 * @see FileStorage
	 */
	public ViewStateManager(String directoryPath)
	{
		// Invalid path argument handled by the storage 
		storage = new FileStorage(directoryPath);
		
		propertyMap = new HashMap<Class<? extends Component>, Property>();
		
		propertyMap.put(Window.class, new WindowProperty());
		propertyMap.put(JTabbedPane.class, new TabbedPaneProperty());
		propertyMap.put(JScrollPane.class, new ScrollPaneProperty());
		propertyMap.put(JSplitPane.class, new SplitPaneProperty());
		
		stateables = Collections.unmodifiableSet(propertyMap.keySet());
	}
	
	
	/**
	 * Creates a new instance with a reference to a predefined
	 * {@link FileStorage storage} to handle file storage.
	 * 
	 * @param storage
	 *            Object to handle local file storage.
	 */
	public ViewStateManager(FileStorage storage)
	{
		this.storage = storage;
		
		propertyMap = new HashMap<Class<? extends Component>, Property>();
		
		propertyMap.put(Window.class, new WindowProperty());
		propertyMap.put(JTabbedPane.class, new TabbedPaneProperty());
		propertyMap.put(JScrollPane.class, new ScrollPaneProperty());
		propertyMap.put(JSplitPane.class, new SplitPaneProperty());
		
		stateables = Collections.unmodifiableSet(propertyMap.keySet());
	}
	
	
	/**
	 * Creates a new instance. Uses the system default file directory to
	 * handle file storage.
	 * 
	 * @see FileStorage#getSystemDefaultDirectory()
	 */
	public ViewStateManager()
	{
		storage = new FileStorage(FileStorage.getSystemDefaultDirectory());
		
		propertyMap = new HashMap<Class<? extends Component>, Property>();
		
		propertyMap.put(Window.class, new WindowProperty());
		propertyMap.put(JTabbedPane.class, new TabbedPaneProperty());
		propertyMap.put(JScrollPane.class, new ScrollPaneProperty());
		propertyMap.put(JSplitPane.class, new SplitPaneProperty());
		
		stateables = Collections.unmodifiableSet(propertyMap.keySet());
	}
	
	
//	/**
//	 * A wrapper method for changing the directory by
//	 * {@link ApplicationDataStorage#changeDirectory()}.
//	 * 
//	 * @param directoryPath
//	 */
//	public void setDirectory(String directoryPath)
//	{
//		storage.changeDirectory(directoryPath);
//	}
	
	
//	/**
//	 * A wrapper method for the {@link ApplicationDataStorage#getDirectory()}.
//	 */
//	public String getDirectory()
//	{
//		return storage.getDirectory();
//	}
	
	
	
	public FileStorage getStorage() {
		return storage;
	}


	public void setStorage(FileStorage storage) {
		this.storage = storage;
	}


	/**
	 * Returns whether this object is {@code null}. The usage of this method 
	 * is similar to the {@code assert} statement to simply prevent invalid 
	 * arguments.
	 * 
	 * @param obj The object argument.
	 * @return {@code true} while the object is {@code null}, otherwise
	 * {@code false}.
	 */
	private static boolean isNull(Object obj)
	{
		return (obj == null);
	}
	
	/**
	 * An unmodifiable {@code Set} that holds all valid class types that allows
	 * to store or restore a state from.
	 * 
	 * @return A {@code Set} instance with valid class types.
	 */
	public Set<Class<?>> getStateables() {
		return stateables;
	}

//	/**
//	 * 
//	 * @param container
//	 * @param name
//	 * @return
//	 */
//	private Component getComponent(Container container, String name){
//
//        if(container.getComponents().length > 0)
//        {
//            for(Component c : container.getComponents())
//            {
//                if(name.equals(c.getName()))
//                {
//                    return c;
//                }
//                if(c instanceof Container)
//                {
//                    return getComponent((Container) c, name);
//                }
//            }
//        }
//
//        return null;
//    }
	
	/**
	 * Separates the components names while traversing the component hierarchy.
	 * 
	 * @see #getComponentName(Component)
	 */
	protected final char hierarchyLevelSeparator = '.';
	
	
	/**
	 * Generates a unique identifier name for a specified {@code Component}
	 * object. All usage of component states is manages by a
	 * {@code Map<String, Object> stateMap} that uses unique component names as
	 * a key. While {@link Component#getName()} sometimes returns {@code null}
	 * this method creates a identifier string by traversing the associated
	 * component hierarchy. In each level the related parents component name is
	 * appended to the identifier by using {code Component#getName()}. To avoid
	 * a {@code null} value that could be returned is replaces it by a special
	 * placeholder string. Each level segment is divided by the
	 * {@link #hierarchyLevelSeparator}.
	 * 
	 * @param c The specified component.
	 * @return An unique identifier name for this component.
	 */
	public String getComponentName(Component c)
	{
		StringBuilder sb = new StringBuilder();
		do
		{
			String name = c.getName();
			
			if(name == null)
			{
				int n = c.getParent().getComponentZOrder(c);
				name = c.getClass().getSimpleName();
				
				if(name == null)	// Anonymous class
				{
					name = c.getClass().getSuperclass().getSimpleName();
					if(name == null)	// Anonymous superclass - Object, Void or Primitive
					{
						name = "undefinedType" + n;
					}
					else
					{
						name = "undefined" + name + n;
					}
				}
			}
			if(sb.length() != 0)
				name = "." + name;
			sb.append(name);
			
			c = c.getParent();
		}
		while((c != null) && !(c instanceof Window) && !(c instanceof Applet));
		
		return sb.toString();		
	}
	
	
	/**
	 * Returns the first stateable class in the hierarchy of this component.
	 * This method iterates over all superclasses and interfaces and returns the
	 * first occurrence that is contained in the predefined list of
	 * {@link stateables}.
	 * <p>
	 * If the parameter is not an subclass or implementation of one of the
	 * defined stateable classes this method returns {@code null}.
	 * </p>
	 * 
	 * @param comp
	 *            The component to check for.
	 * @return The first stateable occurrence in the class hierarchy or
	 *         {@code null} while this component has no state to store.
	 * @see #isStateable(Class)
	 */
	Property getProperty(Component comp)
	{
		Class<?> c = comp.getClass();
		if(isStateable(c))
			return propertyMap.get( c );
		
		Class<?> superclass = c.getSuperclass();
		while(superclass != null)
		{
			if(isStateable(superclass))
			{
				return propertyMap.get( superclass );
			}
			superclass = superclass.getSuperclass();
		}
		
		Class<?>[] interfaces = c.getInterfaces();
		for(Class<?> i : interfaces)
			if(isStateable(i))
				return propertyMap.get( i );
		
		return null;
	}
	
	
	/**
	 * Returns whether this {@code Class} object is stateable.
	 * 
	 * @param c The specified class.
	 * @return
	 */
	private boolean isStateable(Class<?> c)
	{
		if(stateables.contains(c))
			return true;
		return false;
	}
	
	
	/**
	 * Traverse the specified {@code List<Component>} holding components that
	 * each could represent a separate root component for a component tree.
	 * While the components are type of {@code Container} this method iterates
	 * the related hierarchy in a common breadth-first-search BFS. While
	 * traversing the children it performs the specified {@code operation}.
	 * Depending on the operation mode the {@code stateMap} argument can be
	 * {@code null}. This and the return value depends on the {@code operation}
	 * argument.
	 * 
	 * @param components
	 *            A list of root components that component tree gets traversed.
	 * @param stateMap
	 *            Either holding the state object to set the components or to
	 *            extract the states to from the components.
	 * @return The {@code stateMap} argument. This might be null or stays
	 *         untouched for setting states.
	 * 
	 * @see StateOperation
	 */
	private Map<String, Object> iterateComponentHierarchy(List<Component> components, Map<String, Object> stateMap,
			@SuppressWarnings("rawtypes") StateOperation operation) 
	{
		if(isNull(components))
			return null;
		
		/*
		 * Checks against the chosen operation. While the it sets the states
		 * the map cannot be empty or null. If this method extracts the states
		 * an null map is initialized.
		 */
		if(operation == SET_VIEW_STATE)
		{
			if(isNull(stateMap) || stateMap.isEmpty())
				throw new IllegalArgumentException("State map cannot be null or empty.");			
		}
		else if(operation == GET_VIEW_STATE)
		{
			if(isNull(stateMap))
				stateMap = new HashMap<String, Object>();			
		}
		else 
			throw new IllegalArgumentException("Invalid state operation");
		
		// List to fetch all children in BFS
		List<Component> allChildren = new ArrayList<Component>();
		
		// Iterate over a list of components. The first call is always a single component.
		for(Component comp : components)
		{			
			if(!isNull(comp))
			{
				// Return the stateable for this component
				Property p = getProperty(comp);
				if(p != null)
				{
					/*
					 * If the operation equals GET this method extracts all the
					 * states while iterating over the components/children.
					 */
					if(operation == GET_VIEW_STATE)
					{
						stateMap = (Map<String, Object>) GET_VIEW_STATE.operate(p, comp, stateMap);
					}
					/*
					 * If the operation equals SET this method sets all the
					 * states while iterating over the components/children.
					 */
					else if(operation == SET_VIEW_STATE)
					{
						SET_VIEW_STATE.operate(p, comp, stateMap);
					}
					
				}
				
				// While the current component contains some child components
				// invoke this method recursively.
				if(comp instanceof Container)
				{
					Component[] children = ((Container) comp).getComponents();
					if(!isNull(children) && children.length > 0)
						Collections.addAll(allChildren, children);
				}
			}
		}
		
		if(allChildren.size() > 0)
		{
			if(operation == GET_VIEW_STATE)
				stateMap = iterateComponentHierarchy(allChildren, stateMap, operation);
			else if(operation == SET_VIEW_STATE)
				iterateComponentHierarchy(allChildren, stateMap, operation);
		}
		
		return stateMap;
	}
	
	
	
	/**
	 * Saves the components GUI states to a local file to allow to restore those
	 * states any other time.
	 * 
	 * @param c
	 *            The root component mostly a {@link Window} instance. The file
	 *            and the local location in respect to the file system is
	 *            defined by the {@link FileStorage}.
	 */
	public void store(Component c)	
	{
		if(!isNull(c))
		{
			// fetch component states/properties
			Map<String, Object> stateMap = iterateComponentHierarchy(Collections.singletonList(c), null, GET_VIEW_STATE);
			// write to file 
			write(stateMap, getComponentName(c));
		}
	}
	
	
	/**
	 * Writes out all components provided by {@code stateMap} to a file named by
	 * the {@code componentName}. The location is depending on the underlying OS
	 * or the user settings applied to the {@link FileStorage}.
	 * 
	 * @param stateMap
	 *            Holding all objects to be written out.
	 * @param componentName
	 *            Used to name the file that stores the maps content.
	 * 
	 * @see #store(Component)
	 */
	private void write(Map<String, Object> stateMap, String componentName)
	{
		if(isNull(stateMap) || isNull(componentName))
			throw new IllegalArgumentException("Null argument. Cannot write component state to file.");
		
		Collection<String> keys = stateMap.keySet();
		Collection<Object> values = stateMap.values();
		
		// Because Collection is not serializable!
		ArrayList<String> serializableKeys = new ArrayList<String>(keys);
		ArrayList<Object> serializableValues = new ArrayList<Object>(values);
		
		storage.save(serializableKeys, componentName + STATE_KEYS_EXTENSION);
		storage.save(serializableValues, componentName + STATE_VALUES_EXTENSION);
	}
	
	
	/**
	 * Restores a components state and all states for the children in that
	 * component tree.
	 * 
	 * @param c
	 *            The root component that serves also as the filename. The file
	 *            and the local location in respect to the file system is
	 *            defined by the {@link FileStorage}.
	 */
	public void restore(Component c)
	{
		if(!isNull(c))
		{
			storage.containsFile(storage.getDirectoryFor(this.getClass()), getComponentName(c));
			// read from file
			Map<String, Object> stateMap = read( getComponentName(c) );
			// fetch component states/properties
			if(!isNull(stateMap))
				iterateComponentHierarchy(Collections.singletonList(c), stateMap, SET_VIEW_STATE);
		}
	}
	
	
	/**
	 * Reads out a file containing one or more component states.
	 * 
	 * @param componentName
	 *            The file name.
	 * @return A {@code Map} object that holds all the states from the related
	 *         file.
	 * 
	 * @see #restore(Component)
	 */
	private Map<String, Object> read(String componentName)
	{
		boolean keysExist = storage.containsFile(storage.getDirectoryFor(this.getClass()), componentName + STATE_KEYS_EXTENSION);
		boolean valuesExist = storage.containsFile(storage.getDirectoryFor(this.getClass()), componentName + STATE_VALUES_EXTENSION);
		
		if(!(keysExist && valuesExist))
			return null;
		
		ArrayList<String> serializableKeys = (ArrayList<String>) storage.load(componentName + STATE_KEYS_EXTENSION);
		ArrayList<Object> serializableValues = (ArrayList<Object>) storage.load(componentName + STATE_VALUES_EXTENSION);
		
		Map<String, Object> states = new HashMap<String, Object>();
		for(int i = 0; i < serializableKeys.size(); i++)
		{
			states.put(serializableKeys.get(i), serializableValues.get(i));
		}
		
		return states;
	}
	
	
	
	
	
	

	
	
	
	
	
	/**
	 * Provides access to the states of viewable components.
	 * 
	 * @author kimschorat
	 * @since 1.0
	 */
	public interface Property extends Serializable
	{
		/**
		 * Returns the associated state object for this component.
		 * 
		 * @param c
		 *            The component to get the state object from.
		 * @return The state object.
		 */
		public Object getState(Component c);
		
		
		/**
		 * Sets the associated state object for this component.
		 * 
		 * @param c
		 *            The component to set the state object for.
		 * @param state
		 *            The state object.
		 */
		public void setState(Component c, Object state);
	}
	
	
	/**
	 * Defines a state for a {@code Window} component. This state stores several
	 * information like size and position of the related window.
	 * 
	 * @author kimschorat
	 * @since 1.0
	 *
	 */
	public static class WindowState implements Serializable
	{
		private Rectangle bounds = null;
		private int frameState = Frame.NORMAL;
		
		public static boolean fitToScreenSize = true;
		
		/*
		 * Append this class by a field that stores on which screen the frame 
		 * is shown (for devices using more than one screen).
		 * 
		 * private GraphicsDevice screenDevice;
		 */
		
		/**
		 * Creates a new window state instance with initial bounds properties.
		 * 
		 * @param bounds
		 *            The bounds of the window component.
		 */
		public WindowState(Rectangle bounds)
		{
			this.bounds = bounds;
		}
		
		
		/**
		 * Creates a new window state instance with initial bounds and frame
		 * state properties.
		 * 
		 * @param bounds
		 *            The bounds of the window component.
		 * @param frameState
		 *            The current state of this component while it is of type
		 *            {@code Frame}
		 * 
		 * @see Frame#getExtendedState()
		 */
		public WindowState(Rectangle bounds, int frameState)
		{
			this.bounds = bounds;
			this.frameState = frameState;
		}
		
		public Rectangle getBounds() {
			return bounds;
		}
		
		public void setBounds(Rectangle bounds) {
			this.bounds = bounds;
		}
		
		public int getFrameState() {
			return frameState;
		}
		
		public void setFrameState(int frameState) {
			this.frameState = frameState;
		}


		/**
		 * Returns whether the window position should be limited to the actual
		 * screen size when restoring the window properties.
		 * 
		 * @return Returns {@code true} if the window position is limited to the
		 *         current screen size, otherwise {@code false}.
		 */
		public static boolean isLimitedToScreenSize() {
			return fitToScreenSize;
		}

		/**
		 * When set to {@code true} this limits the window bounds to the screen
		 * size. When the state of the related window is restored and the window
		 * object has been outside the current screen size, it is moved into the
		 * displayable area of the current graphics device.
		 * <p>
		 * This may helpful when the graphics device has been replaced by
		 * another one thats resolution is smaller than before.
		 * </p>
		 * 
		 * @param fitToScreenSize
		 *            {@code true} to limit the windows position to the current
		 *            screen size, otherwise {@code false}.
		 */
		public static void setLimitedToScreenSize(boolean fitToScreenSize) {
			WindowState.fitToScreenSize = fitToScreenSize;
		}
	}
	
	
	/**
	 * The property to handle window state objects for components of type
	 * {@code Window}.
	 * 
	 * @author kimschorat
	 * @since 1.0
	 * 
	 * @see WindowState
	 * @see Property
	 */
	public static class WindowProperty implements Property
	{

		/**
		 * Returns an {@code WindowState} object while the component is of type
		 * {@code Window}. Returns {@code null} if {@code c} is {@code null} or
		 * {@code c} is not of type {@code Window}. </br>
		 * 
		 * If component parameter is of type {@code Frame} it also sets its
		 * current extended state.
		 * 
		 * @param c
		 *            The component to get the state from.
		 * 
		 * @return A {@code WindowState} object.
		 */
		@Override
		public Object getState(Component c) 
		{
			if(isNull(c))
				return null;
			else if(c instanceof Window)
				return new WindowState(new Rectangle(c.getX(), c.getY(), c.getWidth(), c.getHeight()),
						(c instanceof Frame) ? ((Frame) c).getExtendedState() : -1);
			
			return null;
		}

		
		/**
		 * Sets the position and size stored in the given state object. While
		 * {@code c} is a {@code Frame} it also sets its extended state.
		 * <p>
		 * While {@link WindowState#isLimitedToScreenSize()} it suits the window
		 * position to the currently used graphics device.
		 * </p>
		 * 
		 * @param c
		 *            The window to set the state for.
		 * @param state
		 *            The state object. Must be of type {@code WindowState}.
		 * 
		 * @throws IllegalArgumentException
		 *             while either the component or the state object is
		 *             {@code null} or has an invalid class type.
		 */
		@Override
		public void setState(Component c, Object state) 
		{
			if(isNull(c) || isNull(state))
			{
				throw new IllegalArgumentException("The parameters can not be null.");
			}
			
			// The following sets the previous window bounds considering a
			// change of the screen device during two launches of the program.
			if(c instanceof Window && state instanceof WindowState)
			{
				if(WindowState.isLimitedToScreenSize())
				{
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					double width = screenSize.getWidth();
					double height = screenSize.getHeight();
					
					Rectangle bounds = ((WindowState)state).getBounds();
					
					// While the previous window bounds were outside the current screen device
					if(bounds.x + bounds.width < 0 || bounds.x > width || bounds.y + bounds.height < 0 || bounds.y > height)
					{
						// Places the window in respect to any OS related menu bars 
						c.setBounds( new Rectangle(50, 50, (int) (width-100), (int) (height-100)) );
					}
					
					// While a part of the window has been outside the current screen.
					double cutoffX = bounds.x + bounds.width - width;
					double cutoffY = bounds.y + bounds.height - height;
					
					c.setBounds( new Rectangle(
							(bounds.x < 0) ? 0 : bounds.x, 
							(bounds.y < 0) ? 0 : bounds.y, 
							(cutoffX > 0) ? (int) (bounds.width - cutoffX) : bounds.width, 
							(cutoffY > 0) ? (int) (bounds.height - cutoffY) : bounds.height) 
							);
				}
				else
				{
					c.setBounds(((WindowState)state).getBounds());
				}
								
				
				if( c instanceof Frame )
					((Frame) c).setExtendedState( ((WindowState)state).getFrameState() );
			}
			else
			{
				throw new IllegalArgumentException("One of the parameter has the wrong class type.");
			}
			
		}
	}
	
	
	
	
	
	
	/**
	 * Defines a state for a {@code JScrollPane} component. This state stores several
	 * information like vertical and horizontal scroll value of the related viewport.
	 * 
	 * @author kimschorat
	 * @since 1.0
	 *
	 */
	public static class ScrollPaneState implements Serializable
	{
		private int verticalPosition = 0;
		private int horizontalPosition = 0;
		
		/**
		 * Creates a new state with initial vertical and horizontal scroll
		 * values for the viewport.
		 * 
		 * @param verticalPos
		 *            The vertical scroll position.
		 * @param horizontalPos
		 *            The horizontal scroll position.
		 */
		public ScrollPaneState(int verticalPos, int horizontalPos)
		{
			verticalPosition = verticalPos;
			horizontalPosition = horizontalPos;
		}

		public int getVerticalPosition() {
			return verticalPosition;
		}

		public void setVerticalPosition(int verticalPosition) {
			this.verticalPosition = verticalPosition;
		}

		public int getHorizontalPosition() {
			return horizontalPosition;
		}

		public void setHorizontalPosition(int horizontalPosition) {
			this.horizontalPosition = horizontalPosition;
		}
		
		
	}
	
	
	/**
	 * The property to handle {@code ScrollPaneState} objects for components of type
	 * {@code JScrollPane}.
	 * 
	 * @author kimschorat
	 * @since 1.0
	 * 
	 * @see ScrollPaneState
	 * @see Property
	 */
	public static class ScrollPaneProperty implements Property
	{

		/**
		 * Returns an {@code ScrollPaneState} object while the component is of
		 * type {@code JScrollPane}. Returns {@code null} if {@code c} is
		 * {@code null} or {@code c} is not of type {@code JScrollPane}.
		 * 
		 * @param c
		 *            The component to get the state from.
		 * 
		 * @return A {@code ScrollPaneState} object.
		 */
		@Override
		public Object getState(Component c) 
		{
			if(isNull(c))
				return null;
			else if(c instanceof JScrollPane)
				return new ScrollPaneState(((JScrollPane) c).getVerticalScrollBar().getValue(), ((JScrollPane) c).getHorizontalScrollBar().getValue());
			
			return null;
		}

		
		/**
		 * Sets the vertical and horizontal scroll value of the underlying
		 * scrollbars.
		 * 
		 * @param c
		 *            The scrollpane to set the state for.
		 * @param state
		 *            The state object. Must be of type {@code ScrollPaneState}.
		 * @throws IllegalArgumentException
		 *             while either the component or the state object is
		 *             {@code null} or has an invalid class type.
		 */
		@Override
		public void setState(Component c, Object state) 
		{
			if(isNull(c) || isNull(state))
			{
				throw new IllegalArgumentException("The parameters can not be null.");
			}
			
			if(c instanceof JScrollPane && state instanceof ScrollPaneState)
			{
				((JScrollPane) c).getVerticalScrollBar().setValue(((JScrollPane) c).getVerticalScrollBar().getMaximum());
				((JScrollPane) c).getVerticalScrollBar().setValue(((ScrollPaneState) state).getVerticalPosition());
				
				((JScrollPane) c).getHorizontalScrollBar().setValue(((JScrollPane) c).getHorizontalScrollBar().getMaximum());
				((JScrollPane) c).getHorizontalScrollBar().setValue(((ScrollPaneState) state).getHorizontalPosition());
			}
			else
			{
				throw new IllegalArgumentException("One of the parameter has the wrong class type.");
			}
		}
	}
	
	
	
	
	
	
	/**
	 * Defines a state for a {@code JTabbedPane} component. This state stores
	 * several information like the current selected index and tab count to
	 * verify the tabbed pane state.
	 * 
	 * @author kimschorat
	 * @since 1.0
	 *
	 */
	public static class TabbedPaneState implements Serializable
	{
		private int index = 0;
		private int tabCount = 0;
		
		
		/**
		 * Creates a new instance and sets the selected index and the current
		 * number of tabs.
		 * 
		 * @param index
		 *            The selected tab index.
		 * @param tabCount
		 *            The number of tabs.
		 */
		public TabbedPaneState(int index, int tabCount)
		{
			this.index = index;
			this.tabCount = tabCount;
		}

		public int getSelectedIndex() {
			return index;
		}

		public void setSelectedIndex(int index) {
			this.index = index;
		}

		public int getTabCount() {
			return tabCount;
		}

		public void setTabCount(int tabCount) {
			this.tabCount = tabCount;
		}
	}
	
	
	/**
	 * The property to handle {@code TabbedPaneState} objects for components of type
	 * {@code JTabbedPane}.
	 * 
	 * @author kimschorat
	 * @since 1.0
	 * 
	 * @see TabbedPaneState
	 * @see Property
	 */
	public static class TabbedPaneProperty implements Property
	{
		/**
		 * Returns an {@code TabbedPaneState} object while the component is of
		 * type {@code JTabbedPane}. Returns {@code null} if {@code c} is
		 * {@code null} or {@code c} is not of type {@code JTabbedPane}.
		 * 
		 * @param c
		 *            The component to get the state from.
		 * 
		 * @return A {@code TabbedPaneState} object.
		 */
		@Override
		public Object getState(Component c) 
		{
			if(isNull(c))
				return null;
			else if(c instanceof JTabbedPane)
			{
				JTabbedPane t = (JTabbedPane) c;
				return new TabbedPaneState(t.getSelectedIndex(), t.getTabCount());
			}
			
			return null;
		}

		
		/**
		 * Sets the currently selected tab index of the underlying
		 * tabbed pane.
		 * 
		 * @param c
		 *            The tabbedpane to set the state for.
		 * @param state
		 *            The state object. Must be of type {@code TabbedPaneState}.
		 * @throws IllegalArgumentException
		 *             while either the component or the state object is
		 *             {@code null} or has an invalid class type.
		 */
		@Override
		public void setState(Component c, Object state) 
		{
			if(isNull(c) || isNull(state))
			{
				throw new IllegalArgumentException("The parameters can not be null.");
			}
			
			if(c instanceof JTabbedPane && state instanceof TabbedPaneState)
			{
				JTabbedPane t = (JTabbedPane) c;
				TabbedPaneState tabState = (TabbedPaneState) state;
				if(t.getTabCount() == tabState.getTabCount())
					t.setSelectedIndex( tabState.getSelectedIndex());
			}
			else
			{
				throw new IllegalArgumentException("One of the parameter has the wrong class type.");
			}
			
		}
	}
	
	
	
	
	
	/**
	 * Defines a state for a {@code JSplitPane} component. This state stores
	 * several information like the orientation and the divider location of the
	 * component.
	 * 
	 * @author kimschorat
	 * @since 1.0
	 *
	 */
	public static class SplitPaneState implements Serializable
	{
		private int orientation = JSplitPane.HORIZONTAL_SPLIT;
		private int dividerLocation = 0;
		private double resizeWeight = 0.5;
		private boolean oneTouchExpand = false;
		
		/**
		 * Creates a new state object and sets important information of the
		 * visible state. This constructor considers the orientation as well as
		 * the divider location. Also it sets the current resizeWeigth and
		 * whether the split pane is {@link JSplitPane#isOneTouchExpandable()
		 * oneTouchExpandable}.
		 * 
		 * @param orientation
		 *            Whether it is vertical or horizontal splitted.
		 * @param dividerLocation
		 *            The location of the divider.
		 * @param resizeWeight
		 *            The current resizeWeight.
		 * @param oneTouchExpand
		 *            Whether the split pane is
		 *            {@link JSplitPane#isOneTouchExpandable()
		 *            oneTouchExpandable}.
		 */
		public SplitPaneState(int orientation, int dividerLocation, double resizeWeight, boolean oneTouchExpand)
		{
			if(orientation == JSplitPane.HORIZONTAL_SPLIT || orientation == JSplitPane.VERTICAL_SPLIT)
				this.orientation = orientation;
			this.dividerLocation = dividerLocation;
			this.resizeWeight = (resizeWeight <= 1 && resizeWeight >= 0) ? resizeWeight : 0.5;
			this.oneTouchExpand = oneTouchExpand;
		}

		public int getOrientation() {
			return orientation;
		}

		public void setOrientation(int orientation) {
			this.orientation = orientation;
		}

		public int getDividerLocation() {
			return dividerLocation;
		}

		public void setDividerLocation(int dividerLocation) {
			this.dividerLocation = dividerLocation;
		}

		public double getResizeWeight() {
			return resizeWeight;
		}

		public void setResizeWeight(double resizeWeight) {
			this.resizeWeight = resizeWeight;
		}

		public boolean isOneTouchExpandable() {
			return oneTouchExpand;
		}

		public void setOneTouchExpandable(boolean oneTouchExpand) {
			this.oneTouchExpand = oneTouchExpand;
		}
	}
	
	
	/**
	 * The property to handle {@code SplitPaneState} objects for components of type
	 * {@code JSplitPane}.
	 * 
	 * @author kimschorat
	 * @since 1.0
	 * 
	 * @see SplitPaneState
	 * @see Property
	 */
	public static class SplitPaneProperty implements Property
	{
		/**
		 * Returns an {@code SplitPaneState} object while the component is of
		 * type {@code JSplitPane}. Returns {@code null} if {@code c} is
		 * {@code null} or {@code c} is not of type {@code JSplitPane}.
		 * 
		 * @param c
		 *            The component to get the state from.
		 * 
		 * @return A {@code SplitPaneState} object.
		 */
		@Override
		public Object getState(Component c) 
		{
			if(isNull(c))
				return null;
			else if(c instanceof JSplitPane)
			{
				JSplitPane s = (JSplitPane) c;
				return new SplitPaneState(s.getOrientation(), s.getUI().getDividerLocation(s), s.getResizeWeight(), s.isOneTouchExpandable());
			}
			
			return null;
		}

		
		/**
		 * Sets the divider location while the orientation remains the same like
		 * previously stored in the state object. It also sets the
		 * {@link JSplitPane#getResizeWeight() resizeWeigth} and whether the
		 * split pane is {@link JSplitPane#isOneTouchExpandable()
		 * oneTouchExpandable}.
		 * 
		 * @param c
		 *            The splitpane to set the state for.
		 * @param state
		 *            The state object. Must be of type {@code SplitPaneState}.
		 * @throws IllegalArgumentException
		 *             while either the component or the state object is
		 *             {@code null} or has an invalid class type.
		 */
		@Override
		public void setState(Component c, Object state) 
		{
			if(isNull(c) || isNull(state))
			{
				throw new IllegalArgumentException("The parameters can not be null.");
			}
			
			if(c instanceof JSplitPane && state instanceof SplitPaneState)
			{
				JSplitPane s = (JSplitPane) c;
				SplitPaneState splitState = (SplitPaneState) state;
				// While  the orientation has not been changed
				if(s.getOrientation() == splitState.getOrientation() )
				{
					// Important to use either setDividerLocation(double) or
					// seDividerLocation(int) but not a mix of both of them!
					s.setDividerLocation( splitState.getDividerLocation() );
					s.setResizeWeight( splitState.getResizeWeight() );
					s.setOneTouchExpandable( splitState.isOneTouchExpandable() );
				}
			}
			else
			{
				throw new IllegalArgumentException("One of the parameter has the wrong class type. Cannot cast from "+c.getClass().getSimpleName()+" to JSplitPane.");
			}
		}
	}
	
	
}
