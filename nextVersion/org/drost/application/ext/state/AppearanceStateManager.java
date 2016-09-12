package org.drost.application.ext.state;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.drost.application.Application;
import org.drost.application.LocalStorage;
import org.drost.application.session.XMLIO;
import org.drost.application.utils.FileUtils;

/**
 * Allows specified viewable objects that extend the {@code Component} type to
 * store and restore their current states. Those states are associated to
 * components that can be visually modified like objects of the types
 * {@code JScrollPane, JSplitPane, JTabbedPane, Window} (For more details read
 * the documentation of {@link #getStateables()}). 
 * 
 * <p>
 * The data storage on the local file system is defined using a local
 * {@link LocalStorage} instance which may be initialized by either an
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
public class AppearanceStateManager implements StateManager<Component>
{
	// TODO Store those constants in a separate ApplicationConstants class file.
	private final String STATE_KEYS_EXTENSION = ".stk";
	private final String STATE_VALUES_EXTENSION = ".stv";
	
//	private String dataDirectory = "";	// TODO Create getters and setters
	
	protected LocalStorage storage;
	
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
	protected final Map<Class<? extends Component>, Class<? extends State<?>>> stateMap;
	
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
		public ReturnType operate(Component c, Map<String, State<?>> stateMap);
	}
	
	
	/**
	 * This class implements the {@code StateOperation} interface. For its
	 * generic type argument this implementation uses
	 * {@code <Map<String, Object>>} to declare a return type. This special
	 * operation is mapped to each component in a component tree and to
	 * extract its GUI state when possible (declared by
	 * {@link UIPersistenceManager#getStateables()} ).
	 * 
	 * @author kimschorat
	 * 
	 * @since 1.0
	 * 
	 * @see #operate(Property, Component, Map)
	 * @see StateOperation
	 */
	protected class StateGetter implements StateOperation<Map<String, ? extends State<?>>>
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
		public Map<String, State<?>> operate(Component c, Map<String, State<?>> stateMap) 
		{
			assert stateMap != null;
			
			Class<? extends State> stateClass = getState(c);
			String key = AppearanceStateManager.this.getComponentName(c);
			
			if(!isNull(key))
			{
				Constructor<? extends State> constructor = null;
				State<?> state = null;
				try
				{
					constructor = stateClass.getConstructor( /*String.class*/ );
					state = constructor.newInstance( /*key*/ );
				}
				catch ( NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e )
				{
					e.printStackTrace( );
				}
				
				
				if(!isNull(state))
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
	 * {@link UIPersistenceManager#getStateables()} ).
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
		public Void operate(Component c, Map<String, State<?>> stateMap) 
		{
			Class<? extends State<?>> stateClass = getState(c);
			String key = getComponentName(c);
			if(!isNull(key))
			{
				// Returns null since no state is available for this component
//				Constructor<? extends State<?>> constructor = null;
//				State state = null;
//				try
//				{
//					constructor = stateClass.getConstructor( String.class );
//					state = constructor.newInstance( key );
//				}
//				catch ( NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
//						| IllegalArgumentException | InvocationTargetException e )
//				{
//					e.printStackTrace( );
//				}
				
				@SuppressWarnings( "rawtypes" )
				State state = stateMap.get(key);
				if(!isNull(state))
				{
					state.getState( c );
				}
			}
			return null;
		}
	}
	
	/**
	 * This operation indicates the the components state should be extracted.
	 * 
	 * @see UIPersistenceManager#iterateComponentHierarchy(List, Map, StateOperation)
	 */
	protected final StateOperation<Map<String, ? extends State<?>>> GET_STATE = AppearanceStateManager.this.new StateGetter();
	
	/**
	 * This operation indicates the the components state should be set.
	 * 
	 * @see UIPersistenceManager#iterateComponentHierarchy(List, Map, StateOperation)
	 */
	protected final StateOperation<Void> SET_STATE = AppearanceStateManager.this.new StateSetter();
	
	
	
	
	
	
	
	
	
//	/**
//	 * Creates a new instance with a reference to a related file directory to
//	 * handle file storage.
//	 * 
//	 * @param directoryPath
//	 *            The local directory to store the states.
//	 *            
//	 * @see Local
//	 */
//	public PersistenceManager(String directoryPath)
//	{
//		// Invalid path argument handled by the storage 
//		storage = new Local(directoryPath);
//		
//		propertyMap = new HashMap<Class<? extends Component>, Property>();
//		
//		propertyMap.put(Window.class, new WindowProperty());
//		propertyMap.put(JTabbedPane.class, new TabbedPaneProperty());
//		propertyMap.put(JScrollPane.class, new ScrollPaneProperty());
//		propertyMap.put(JSplitPane.class, new SplitPaneProperty());
//		
//		stateables = Collections.unmodifiableSet(propertyMap.keySet());
//	}
	
	
	/**
	 * Creates a new instance with a reference to a predefined
	 * {@link LocalStorage storage} to handle file storage.
	 * 
	 * @param storage
	 *            Object to handle local file storage.
	 */
	public AppearanceStateManager(LocalStorage storage)
	{
		this.storage = storage;
		
		stateMap = new HashMap<Class<? extends Component>, Class<? extends State<?>>>();
		
		stateMap.put(Window.class, WindowState.class);
		stateMap.put(JTabbedPane.class, TabbedPaneState.class);
		stateMap.put(JScrollPane.class, ScrollPaneState.class);
		stateMap.put(JSplitPane.class, SplitPaneState.class);
		
		stateables = Collections.unmodifiableSet(stateMap.keySet());
	}
	
	
	/**
	 * Creates a new instance. Uses the system default file directory to
	 * handle file storage.
	 * 
	 * @see LocalStorage#getSystemDefaultDirectory()
	 */
	public AppearanceStateManager()
	{
		if(Application.running( ))
			storage = Application.get( ).getLocalStorage( );
		else
		{
			try
			{
				storage = new LocalStorage("data");
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
		
		stateMap = new HashMap<Class<? extends Component>, Class<? extends State<?>>>();
		
		stateMap.put(Window.class, WindowState.class);
		stateMap.put(JTabbedPane.class, TabbedPaneState.class);
		stateMap.put(JScrollPane.class, ScrollPaneState.class);
		stateMap.put(JSplitPane.class, SplitPaneState.class);
		
		stateables = Collections.unmodifiableSet(stateMap.keySet());
	}
	
	
	
	@SuppressWarnings( "unused" )
	private void init()
	{
		 // Until there remain final class fields that need to be initialized in the constructors.
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
	
	
	
	public LocalStorage getStorage() {
		return storage;
	}


	public void setStorage(LocalStorage storage) {
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
	Class<? extends State<?>> getState(Component comp)
	{
		Class<?> c = comp.getClass();
		if(isStateable(c))
			return stateMap.get( c );
		
		Class<?> superclass = c.getSuperclass();
		while(superclass != null)
		{
			if(isStateable(superclass))
			{
				return stateMap.get( superclass );
			}
			superclass = superclass.getSuperclass();
		}
		
		Class<?>[] interfaces = c.getInterfaces();
		for(Class<?> i : interfaces)
			if(isStateable(i))
				return stateMap.get( i );
		
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
	private Map<String, State<?>> iterateComponentHierarchy(List<Component> components, Map<String, State<?>> stateMap,
			@SuppressWarnings("rawtypes") StateOperation operation) 
	{
		if(isNull(components))
			return null;
		
		/*
		 * Checks against the chosen operation. While the it sets the states
		 * the map cannot be empty or null. If this method extracts the states
		 * an null map is initialized.
		 */
		if(operation == SET_STATE)
		{
			if(isNull(stateMap))
				throw new IllegalArgumentException("State map cannot be null or empty.");	
			
			if(stateMap.isEmpty())
				return null;
		}
		else if(operation == GET_STATE)
		{
			if(isNull(stateMap))
				stateMap = new HashMap<String, State<?>>();			
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
				Class<? extends State<?>> p = getState(comp); 
				if(p != null)
				{
					/*
					 * If the operation equals GET this method extracts all the
					 * states while iterating over the components/children.
					 */
					if(operation == GET_STATE)
					{
						stateMap = (Map<String, State<?>>) GET_STATE.operate(comp, stateMap);
					}
					/*
					 * If the operation equals SET this method sets all the
					 * states while iterating over the components/children.
					 */
					else if(operation == SET_STATE)
					{
						SET_STATE.operate(comp, stateMap);
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
			if(operation == GET_STATE)
				stateMap = iterateComponentHierarchy(allChildren, stateMap, operation);
			else if(operation == SET_STATE)
				iterateComponentHierarchy(allChildren, stateMap, operation);
		}
		
//		for(Entry<String, State<?>> e : stateMap.entrySet( ))
//		{
//			System.out.println( e.getKey( ) );
//		}
		
		return stateMap;
	}
	
	
	
	/**
	 * Saves the components GUI states to a local file to allow to restore those
	 * states any other time.
	 * 
	 * @param c
	 *            The root component mostly a {@link Window} instance. The file
	 *            and the local location in respect to the file system is
	 *            defined by the {@link LocalStorage}.
	 */
	@Override
	public void store(Component c)	
	{
		if(!isNull(c))
		{
			// fetch component states/properties
			Map<String, State<?>> stateMap = iterateComponentHierarchy(Collections.singletonList(c), null, GET_STATE);
			// write to file 
			write(stateMap, getComponentName(c));
			try
			{
				XMLIO.save( stateMap, storage.getDirectory( ) + getComponentName(c) );
			}
			catch ( FileNotFoundException e )
			{
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Writes out all components provided by {@code stateMap} to a file named by
	 * the {@code componentName}. The location is depending on the underlying OS
	 * or the user settings applied to the {@link LocalStorage}.
	 * 
	 * @param stateMap
	 *            Holding all objects to be written out.
	 * @param componentName
	 *            Used to name the file that stores the maps content.
	 * 
	 * @see #store(Component)
	 */
	@Deprecated
	private void write(Map<String, State<?>> stateMap, String componentName)
	{
		if(isNull(stateMap) || isNull(componentName))
			throw new IllegalArgumentException("Null argument. Cannot write component state to file.");
		
		Collection<String> keys = stateMap.keySet();
		Collection<State<?>> values = stateMap.values();
		
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
	 *            defined by the {@link LocalStorage}.
	 */
	@Override
	public void restore(Component c)
	{
		if(!isNull(c))
		{
			storage.containsFile(storage.getDirectoryFor(this.getClass()), getComponentName(c));
			// read from file
			Map<String, State<?>> stateMap = read( getComponentName(c) );
//			Object o = null;
//			try
//			{
//				o = XMLIO.load( getComponentName(c) );
//			}
//			catch ( FileNotFoundException ignore )
//			{
//				return;
//			}
//			Map<String, State<?>> stateMap = (Map<String, State<?>>) o;
			// fetch component states/properties
			if(!isNull(stateMap))
				iterateComponentHierarchy(Collections.singletonList(c), stateMap, SET_STATE);
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
	@Deprecated
	private Map<String, State<?>> read(String componentName)
	{
		boolean keysExist = storage.containsFile(storage.getDirectoryFor(this.getClass()), componentName + STATE_KEYS_EXTENSION);
		boolean valuesExist = storage.containsFile(storage.getDirectoryFor(this.getClass()), componentName + STATE_VALUES_EXTENSION);
		
		if(!(keysExist && valuesExist))
			return null;
		
		ArrayList<String> serializableKeys = (ArrayList<String>) storage.load(componentName + STATE_KEYS_EXTENSION);
		ArrayList<State<?>> serializableValues = (ArrayList<State<?>>) storage.load(componentName + STATE_VALUES_EXTENSION);
		
		Map<String, State<?>> states = new HashMap<String, State<?>>();
		for(int i = 0; i < serializableKeys.size(); i++)
		{
			states.put(serializableKeys.get(i), serializableValues.get(i));
		}
		
		return states;
	}
	
}
