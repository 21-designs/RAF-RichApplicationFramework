/*
 * This file is part of the application library that simplifies common 
 * initialization and helps setting up any java program.
 * 
 * Copyright (C) 2016 Yannick Drost, all rights reserved.
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.drost.application.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.swing.FocusManager;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.drost.application.Application;
import org.drost.application.adapter.EdgeSnapAdapter;
import org.drost.application.adapter.KeyControlAdapter;
import org.drost.application.plaf.rich.RichLookAndFeel;
import org.drost.application.session.UIPersistenceManager;

/**
 * Holds the main window of the current application and provides several
 * functionalities related to displayable application content. This class only
 * stores one window instance at the same time.
 * 
 * @author kimschorat
 * @since 1.0
 */
public class GUI
{
	/**
	 * The underlying window object.
	 */
	private JFrame window;
	
	private boolean implicitExit = false;
	
	private final EdgeSnapAdapter edgeSnapAdapter;
	
	// A input blocking component that hides other applications except this one.
	private final JFrame standalone = new JFrame();
	private final HashMap<Window, Boolean> alwaysOnTopPropertyMap = new HashMap<Window, Boolean>();
	
	/**
	 * Indicates whether a confirmation dialog prompts on closing the main window.
	 */
	private boolean isConfirmExit = false;
	
	
	/**
	 * The window listener that prompts a confirmation dialog when closing the
	 * window. When the closing action is canceled the windows closing operation
	 * is set to {@code javax.swing.WindowConstants#DO_NOTHING_ON_CLOSE} for the
	 * time the closing event is handled. After blocking the closing event the
	 * previous closing operation is restored.
	 * <p>
	 * <b>Important:</b> this only takes effect while the windows default close
	 * operation is either equal to {@code JFrame#EXIT_ON_CLOSE} or to
	 * {@code WindowConstants#DISPOSE_ON_CLOSE}.
	 * </p>
	 * 
	 * @see JFrame#setDefaultCloseOperation(int)
	 */
	private WindowListener confirmExitAdapter = new WindowAdapter()
	{
		private int defaultCloseOperation = 3;
		
		private boolean defaultCloseOperationChanged = false;
		
		@Override
		public void windowClosing(WindowEvent e)
		{
			if(isConfirmExit)
			{
				if(window.getDefaultCloseOperation( ) == JFrame.EXIT_ON_CLOSE)
				{
					int dialogResult = JOptionPane.showConfirmDialog (window, "Would You Like to Quit?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
					if( (dialogResult != JOptionPane.OK_OPTION) )
					{
						defaultCloseOperation = window.getDefaultCloseOperation( );
						window.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
						
						defaultCloseOperationChanged = true;
					}
				}
			}
		}
		
		@Override
		public void windowActivated(WindowEvent e)
		{
			if( defaultCloseOperationChanged )
			{
				window.setDefaultCloseOperation( defaultCloseOperation );
				defaultCloseOperationChanged = false;
			}
		}
	};
		
	
//	/**
//	 * Creates a new view to handle viewable application content.
//	 */
//	public GUI()
//	{
//		this(null);
//	}
	
	
	/**
	 * Creates a view and initializes the main window.
	 * 
	 * @param window
	 *            The main window.
	 * 
	 * @see #setMainWindow(Window)
	 */
	public GUI(JFrame window)
	{
		this.setMainWindow(window);
		
		edgeSnapAdapter = new EdgeSnapAdapter();
		
		initializeSpotlightBg();
	}
	
	
	/*
	 * Initializes the background dialog and sets the transparency if supported
	 * by the OS.
	 */
	private void initializeSpotlightBg()
	{
		standalone.getContentPane( ).setBackground( Color.BLACK );
		standalone.setFocusable( false );
		standalone.setFocusableWindowState( false );
		standalone.setUndecorated( true );
		standalone.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		standalone.addWindowListener( new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e)
			{
				standalone.toBack( );
			}
		});
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment( );
		GraphicsDevice gd = ge.getDefaultScreenDevice( );
		
		if( gd.isWindowTranslucencySupported( GraphicsDevice.WindowTranslucency.TRANSLUCENT ) )
		{
			standalone.setOpacity(0.70f);
		}
	}

	public void repaintGUI()
	{
		for( Window w : getAllWindows() )
			w.repaint( );
	}
	
	
	public void disposeGUI()
	{
		
	}
	
	
	/**
	 * Because swing is not thread safe this method ensures that GUI associated
	 * code is run on the event dispatch thread to avoid any unexpected GUI
	 * behavior.
	 * 
	 * @param runnable
	 *            The runnable to be invoked on the EDT.
	 */
	public void runOnUIThreadSynchroniously(Runnable runnable)	// FIXME Moved to GUIManager
	{
		if (SwingUtilities.isEventDispatchThread()) 
		{
	        runnable.run();
	    } 
		else 
		{
	        SwingUtilities.invokeLater(runnable);
	    }
	}
	
	
	/**
	 * Because swing is not thread safe this method ensures that GUI associated
	 * code is run on the event dispatch thread to avoid any unexpected GUI
	 * behavior.
	 * 
	 * @param runnable
	 *            The runnable to be invoked on the EDT.
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 */
	public void runOnUIThreadAsynchroniously(Runnable runnable) throws InvocationTargetException, InterruptedException // FIXME Moved to GUIManager
	{
		if (SwingUtilities.isEventDispatchThread()) 
		{
	        runnable.run();
	    } 
		else 
		{
	        SwingUtilities.invokeAndWait(runnable);
	    }
	}
	
	
	/**
	 * Initializes the window object and sets some default parameters.
	 * 
	 * @param window
	 * @param size
	 * @param title
	 * @return
	 */
	@Deprecated
	public <T extends Window> T initializeWindow( T window, Dimension size, String title)
	{
		if(window == null)
			return null;
		
		window.setSize( size );
		window.setLocationRelativeTo( null );
		if(window instanceof Frame )
		{
			( (Frame) window ).setTitle(title);
		}
		
		if(window instanceof JFrame)
		{
			( (JFrame) window ).setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		}
		
		if(window instanceof Dialog)
		{
			( (Dialog) window ).setTitle(title);
		}
		
		return window;
	}
	
	
	/**
	 * Creates a main view and initializes the instance. Additionally this
	 * method also sets this instance the main view by
	 * {@link GUI#setMainWindow(Window)}.
	 * <p>
	 * Note that this method may replace a previous set main window. Use this to
	 * initialize the main frame of the program an apply individual parameters
	 * to the returned instance.
	 * </p>
	 * <pre>
	 * GUI gui = new GUI(null);
	 * gui.createMainWindow();
	 * </pre>
	 * 
	 * @param title The title of the window
	 * @return A predefined window with additional functionalities
	 */
	public JFrame createMainWindow()
	{
		JFrame f = new ExtendedFrame();
		
		f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		this.setMainWindow( f );
		
		return f;
	}
	
	/**
	 * Deletes the main window.
	 */
	public void destroyMainWindow()
	{
		this.getMainWindow( ).dispose( );
		this.setMainWindow( null );
	}
	
	
//	public JDialog createDialog(String title)
//	{
//		return createDialog(getMainView( ), title);
//	}
	
	/**
	 * 
	 * @param title
	 * @return
	 */
	public JDialog createChildDialog(String title)
	{
		JDialog d = new JDialog( window );
		d.setLocationRelativeTo( window );
		d.setTitle( title );

		// Add a KeyListener to close on ESC
		d.addKeyListener( new KeyControlAdapter( d ) );

		return d;
	}
	
	
	/**
	 * When closing the last window the application terminates and shuts down.
	 * 
	 * @param implicit
	 *            Whether to exit the currently running application when the
	 *            last window is closed.
	 */
	public void setImplicitExit(boolean implicit)
	{
		// TODO Ensure the application closes even when the main view is disposed.
		implicitExit = implicit;
	}
	
	
	/**
	 * Returns whether the application terminates if the last open window is
	 * closing.
	 * 
	 * @return Whether the application terminates if the last open window is
	 *         closing.
	 */
	public boolean isImplicitExit()
	{
		return implicitExit;
	}
	
	
	
	
	
	
	
	


	/**
	 * Sets the main window for the underlying application. Does not provide any
	 * support for multi-framed applications where all those frames are main
	 * windows. This makes sense because the main view represents the one which 
	 * state indicates the state of the application.
	 * 
	 * @param window
	 *            The application main window.
	 * @throws IllegalArgumentException
	 *             while the parameter is {@code null} or if this view instance
	 *             already has a window object.
	 */
	public void setMainWindow(JFrame window)
	{
		this.window = window;
		
		if(window == null)
			return;
		
		if( Application.running( ) )
		{
			if( window instanceof Frame )
			{
				Frame f = (Frame) window;
				
				if( f.getTitle( ).equals( null ) ||  f.getTitle( ).equals( "" ) )
				{
					f.setTitle( Application.get( ).getID( ) );
				}
			}
//			else if( window instanceof Dialog )
//			{
//				Dialog f = (Dialog) window;
//				
//				if( f.getTitle( ).equals( null ) ||  f.getTitle( ).equals( "" ) )
//				{
//					f.setTitle( Application.get( ).getID( ) );
//				}
//			}
		}
		
	}
	
	
	/**
	 * Returns the main window of the application.
	 * 
	 * @return The main window.
	 */
	public Window getMainWindow()
	{
		return window;
	}
	
	
	/**
	 * Returns whether the main view has already been set.
	 * 
	 * @return <code>true</code> if the window has already been set.
	 */
	public boolean hasMainWindow()
	{
		return (window != null);
	}
	

	
	/**
	 * Sets the applications default {@link RichLookAndFeel} and updates the
	 * current UI components.
	 */
	public void setConsistentLookAndFeel()	// FIXME Moved to GUIManager
	{
		setLookAndFeel( new RichLookAndFeel() );
	}
	
	
	/**
	 * <p>
	 * Applies the specified look and feel to this application, meaning all
	 * associated windows will be updated.
	 * </p>
	 * 
	 * @param qualifiedName
	 *            The name of the <code>LookAndFeel</code>.
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 *             If the L&F is not supported.
	 * @throws InvocationTargetException
	 *             While an exception occurred while running on EDT.
	 * @throws InterruptedException
	 *             While a exception has been thrown while this current thread
	 *             waits for finishing the UI update.
	 * 
	 * @see UIManager#setLookAndFeel(String)
	 * @see SwingUtilities#updateComponentTreeUI(Component)
	 */
	public void setLookAndFeel(String qualifiedName)	// FIXME Moved to GUIManager
	{
		try
		{
			runOnUIThreadAsynchroniously( new Runnable( )
			{

				@Override
				public void run( )
				{
					try
					{
						UIManager.setLookAndFeel( qualifiedName );
					}
					catch ( ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e )
					{
						e.printStackTrace( );
					}
					for( int i = 0; i < JFrame.getWindows( ).length; i++ )
					{
						Window w = JFrame.getWindows( )[i];
						SwingUtilities.updateComponentTreeUI( w );
					}
				}

			} );
		}
		catch ( InvocationTargetException | InterruptedException e )
		{
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * <p>
	 * This method simply invokes the homonymous method
	 * {@link #setLookAndFeel(String)} passing the class name of the specified class
	 * type.
	 * </p>
	 * 
	 * @param laf
	 *            The <code>LookAndFeel</code> class.
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 */
	public void setLookAndFeel(LookAndFeel laf) 	// FIXME Moved to GUIManager
	{
		setLookAndFeel(laf.getClass().getCanonicalName());
	}
	
	
	
	
	
	/**
	 * Traverse the component tree and whether enables or disables the
	 * component. While a component is the {@code Container} instance it
	 * recursively calls this method on all children components. Performs a
	 * depth first search to traverse the component tree.
	 * 
	 * @param comp
	 *            The component to whether enable or not.
	 * @param enabled
	 *            {@code true} to enable this component and all components
	 *            contained within, otherwise {@code false}.
	 */
	public static void setEnabled(Component comp, boolean enabled) 
	{
		comp.setEnabled(enabled);
		
		if(comp instanceof Container)
		{
			for(int i = 0; i < ((Container) comp).getComponentCount(); i++)
			{
				Component child = ((Container) comp).getComponent(i);
				if(child instanceof Container && ((Container) child).getComponentCount() > 0)
				{
					setEnabled(child, enabled);
				}
				else
				{
					child.setEnabled(enabled);
				}
			}
		}
	}
	
	public Window getActiveWindow()
	{
		return FocusManager.getCurrentManager().getActiveWindow();
	}
	
	public Window[] getAllWindows()
	{
		return Window.getOwnerlessWindows( );
	}
	
	
	public int getWindowCount()
	{
		return Window.getOwnerlessWindows( ).length;
	}
	
	
	
	
	/**
	 * Enables or disables the confirmation dialog on exit. When the closing
	 * action is canceled by the prompted dialog the windows closing operation
	 * is set to {@link WindowConstants#DO_NOTHING_ON_CLOSE} for the
	 * time the closing event is handled. After blocking the closing event the
	 * previous closing operation is restored. It's default value is
	 * {@code false}.
	 * <p>
	 * <b>Important:</b> this only takes effect while the windows default close
	 * operation is equal to {@link JFrame#EXIT_ON_CLOSE}, because any other
	 * close operation would not shut down the application.
	 * </p>
	 * 
	 * @param {@code true} to prompt a confirmation dialog on exit, otherwise
	 *            {@code false}.
	 * 
	 * @see JFrame#setDefaultCloseOperation(int)
	 */
	public void setMainWindowConfirmExit(boolean enable)
	{
		isConfirmExit = enable;
		
		if(isConfirmExit)
		{
			window.addWindowListener( confirmExitAdapter );
		}
		else
		{
			window.removeWindowListener( confirmExitAdapter );
		}
	}
	
	
	public boolean isMainWindowConfirmExit()
	{
		return this.isConfirmExit;
	}
	
	
	
	
	
	
	
	
	public void minimize(Frame view)
	{
		if(view != null )
		{
			view.setExtendedState(Frame.ICONIFIED);
		}
	}
	
	
	public void maximize(Frame view)
	{
		if(view != null )
		{
			view.setExtendedState(Frame.MAXIMIZED_BOTH);
		}
	}
	
	
	public void fullScreen(Window view)
	{
		// Screen size + always on top + undecorated
		// Remember to apply an option to disable fullscreen
	}
	
	
	public void close(Window view)
	{
		if(view != null && view.isShowing( ))
			view.dispose( );
	}
	
	
	public void closeAll()
	{
		for(Window w : getAllWindows() )
		{
			w.dispose( );
		}
	}
	
	
	public void toFront(Window view)
	{
		if(view != null  )
			view.toFront( );
	}
	
	
	public void toBack(Window view)
	{
		if(view != null )
			view.toBack( );
	}
	
	
	public void requestFocus(Window w)
	{
		if(w.isFocusableWindow( ))
			w.requestFocus( );
	}
	
	
	public boolean spotlight(Window w, boolean b)
	{
		Window[] windows = {w};
		return spotlight(windows, b);
	}
	
	public boolean spotlight(Window[] windows, boolean b)
	{
		if(windows.length == 0)
			return false;
		
		for(Window w : windows)
		{
			if( !w.isAlwaysOnTopSupported( ) )
				return false;
		}
		
		if(b)
		{
			standalone.toFront( );
		}
		else
		{
			standalone.dispose( );
		}
		
		for(Window w : windows)
		{
			if(b)
			{			
				alwaysOnTopPropertyMap.put( w, w.isAlwaysOnTop( ) );
				w.setAlwaysOnTop( true );
			}
			else
			{
				boolean wasAlwaysOnTop = alwaysOnTopPropertyMap.remove( w );
				if(!wasAlwaysOnTop)
					w.setAlwaysOnTop( false );
			}
		}
		
		if(b)
		{
			standalone.setVisible( true );
		}
		
		return true;
	}
	
	
	public void setSpotlightBackground( Color c )
	{
		standalone.setBackground( c );
	}
	
	/**
	 * A value between 0.0 and 1.0, where 1.0 is completely opaque. Note that this only works when transparency is supported by the {@code GraphicsDevice}. 
	 * This method checks the availability by calling {@link GraphicsDevice#isWindowTranslucencySupported(java.awt.GraphicsDevice.WindowTranslucency)}.
	 * 
	 * @param value 
	 */
	public void setSpotlightTransparency(float value)
	{
		if(value < 0f || value > 1f)
			throw new IllegalArgumentException("Value "+value+" out of range. Must be between 0.0 and 1.0.");
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment( );
		GraphicsDevice gd = ge.getDefaultScreenDevice( );
		
		if( gd.isWindowTranslucencySupported( GraphicsDevice.WindowTranslucency.TRANSLUCENT ) )
		{
			standalone.setOpacity(0.70f);
		}
	}
	
	/**
	 * Listens to keystrokes and for example tries to close when ESC is pressed.
	 * 
	 * @param view
	 */
	public void addKeyControl(Window view)
	{
		// see Appearance.createSubDialog()
	}
	
	
	public void setFixSize( Window w, Dimension d )
	{
		w.setMaximumSize( d );
		w.setMaximumSize( d );
		w.setPreferredSize( d );
		if(w instanceof Frame)
		{
			( (Frame) w ).setResizable( false );
		}
		else if(w instanceof Dialog)
		{
			( (Dialog) w ).setResizable( false );
		}
	}
	
	


	/**
	 * Enables or disables the edge snapping functionality by adding or removing
	 * the adapter from the main window. 
	 * 
	 * @param enable
	 *            Enables or disables the snapping functionality.
	 */
	public boolean enableEdgeSnap(Window window, boolean enable)
	{
		if(window == null)
			return false;
		
		if(enable)
		{
//			if(!snapEnabled)
//			{
				for(ComponentListener l : window.getComponentListeners( ))
				{
					if(l.equals( edgeSnapAdapter ))
						return false;
				}
				window.addComponentListener(edgeSnapAdapter);
//				snapEnabled = true;
//			}
		}
		else
		{
//			if(snapEnabled)
//			{
				window.removeComponentListener(edgeSnapAdapter);
//				snapEnabled = false;
//			}
		}
		
		return true;
	}
	
	
	public void shake(Window view)
	{
		this.shake( view, 200 );
	}
	
	
	public void shake(Window view, long millis)
	{
		final long startMillis = System.currentTimeMillis( );
		int posX = view.getLocation( ).x;
		int posY = view.getLocation( ).y;
		
		final int DELAY = 5;
		final double CYCLE = 50;
		final int DISTANCE = 10;
		final double PI2 = Math.PI * 2;
		
		double angle;
		int newPosX = 0;
		long elapsedMillis = 0;
		
		while(elapsedMillis < millis)
		{
			elapsedMillis = System.currentTimeMillis( ) - startMillis;
			
			if(elapsedMillis % DELAY == 0)
			{
				angle = (elapsedMillis % CYCLE) / CYCLE * PI2;
				newPosX = (int) ((Math.sin( angle ) * DISTANCE) + posX);
				
				// Perform this on another thread to provide accessibility to the window
				view.setLocation( newPosX, posY );
			}
		}
		
		view.setLocation( posX, posY );
		view.repaint( );
	}
	
	
	@Deprecated
	public void windowFadeOut(Frame view)
	{
		this.windowFadeOut( view, 500 );
	}
	
	@Deprecated
	public void windowFadeOut(Frame view, long millis)
	{
		// TODO	Due to the Java7 undecorated frame bug
	}
	
	@Deprecated
	public void windowFadeIn(Window view)
	{
		this.windowFadeIn( view, 500 );
	}
	
	@Deprecated
	public void windowFadeIn(Window view, long millis)
	{
		// TODO Due to the Java7 undecorated frame bug
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * This class extends {@code JFrame} by additional functionalities and predefined behavior.
	 * @author kimschorat
	 *
	 */
	public static class ExtendedFrame extends JFrame
	{
		private final UIPersistenceManager pm;
		
		public ExtendedFrame()
		{
			if( Application.running( ) )
			{
				 pm = new UIPersistenceManager( Application.get( ).getLocalStorage( ) );
			}
			else
			{
				 pm = new UIPersistenceManager();
			}
			
			// Add WindowListener to store and restore component parameters
			this.addWindowListener( new WindowAdapter() {

				@Override
				public void windowClosing( WindowEvent e )
				{
					// Store components
					pm.store( GUI.ExtendedFrame.this );
				}			
			});
		}
		
		@Override
		public void setVisible( boolean b )
		{
			// Restore components
			pm.restore( GUI.ExtendedFrame.this );

			// TODO Implement a event that is fired and notifies listeners that
			// want to know about when the window will open.
			
			super.setVisible( b );
		}
		
	}

}
