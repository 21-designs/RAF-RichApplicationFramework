/*
 * This file is part of the application library that simplifies common
 * initialization and helps setting up any java program.
 * 
 * Copyright (C) 2016 Yannick Drost, all rights reserved.
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.drost.application;

import java.awt.AWTEvent;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.event.EventListenerList;

import org.drost.application.listeners.ApplicationAdapter;
import org.drost.application.listeners.ApplicationEvent;
import org.drost.application.listeners.ApplicationListener;
import org.drost.application.plaf.rich.RichLookAndFeel;
import org.drost.application.suppliers.PropertiesSupport;
import org.drost.application.ui.GUI;

/**
 * A bundles of most common features related to a basic application.
 * 
 * <p>
 * This class is used for setting up and further more managing an applications
 * life cycle.
 * <ul>
 * <li>The {@code launch} method initializes the application instance. This is
 * only called once. This method is {@code static} and defines the main entry
 * point for the usage of the {@code Application} class.</li>
 * <li>{@code Application.get()} returns the current instance and gains access
 * to application related features. Most of them are bundled in the
 * {@link Substance} singleton. This includes handling resources, support for
 * GUI applications and defining application related properties.</li>
 * <li>To shut down the application the {@code exit} methods is called. Beside
 * cleaning up any resources it also performs a check if the application is
 * allowed to shut down or.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * To initialize an application simply call the
 * {@code Application.launch(String)} method. The following example shows a
 * basic life cycle of every application setup:
 * 
 * <pre>
 * public class ApplicationExample
 * {
 * 	public static void main( String[] args )
 * 	{
 * 		// Initialize the application by using a proper class name
 * 		Application.launch( ApplicationExample.getClass( ).getSimpleName( ) );
 * 
 * 		// Another way to initialize the application is to use an identifier:
 * 		// Application.launch( "MyApplication" );
 * 
 * 		// Your application logic here ...
 * 
 * 		// Closes the application and shuts down the JVM
 * 		Application.exit( );
 * 	}
 * }
 * </pre>
 * 
 * Get the application instance by {@code Application.get()} to gain access to
 * all of the application features.
 * </p>
 * 
 * The uniqueness of this singleton class is imposed by using a private
 * constructor. This prevent subclasses to implement a public constructor and to
 * create multiple instances.
 * 
 * @author Yannick Drost
 * @author <a href="mailto:drost.yannick@googlemail.com">drost.
 *         yannick@googlemail.com</a>
 * @version 1.0
 * 
 * @see #launch(String)
 * @see #shutdown()
 *
 */
// Rename to RichApplication
public class Application // Implement 'Observable' or add event listener system
{
	/**
	 * The unique identifier for this application instance that is used to
	 * initialize the application. Its value is set when the application gets
	 * launched. Since this ID is unique there is no possibility to reset it
	 * even in no subclass.
	 * <p>
	 * The ID may be well defined because it serves as a namesake or even
	 * placeholder for several application related class types. A common pattern
	 * is to initialize the application by using a representing class name.
	 * </p>
	 * 
	 * <pre>
	 * public class MyAwesomeProgram
	 * {
	 * 	public static void main( String[] args )
	 * 	{
	 * 		Application.launch( MyAwesomeProgram.getClass( ).getSimpleName( ) );
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @see Application#launch(String)
	 * @see Application#getID()
	 */
	protected final String id;

	/**
	 * A list holding all the application listeners of type
	 * {@code ApplicationListener}.
	 */
	private EventListenerList applicationListeners;

	/**
	 * Temporary file that is created when ever the application is restarted.
	 * Indicates a performed restart for the next process that will delete it
	 * after the restart.
	 */
	private static File restartFile = null;

	/**
	 * The file name of the restart file. Does not contain an extension.
	 */
	private final static String RESTART_FILE_NAME = "restart-app";

	private File		lockFile		= null;
	private FileChannel	lockFileChannel	= null;
	private FileLock	lock			= null;
	
	
	private boolean createPropertyFileOnShutdown = false;
	
	/**
	 * Avoids infinity loops invoking shutdown() more than once.
	 */
	private boolean shutdownAlreadyInitiated = false;
	
	/**
	 * The context of this application instance that stores some application
	 * related content.
	 */
	private Substance substance;

	/**
	 * Manages the application associated local storage.
	 */
	private LocalStorage localStorage;

	/**
	 * Provides functionality to manage the application GUI.
	 */
	private GUI gui;

	/**
	 * Wraps the singleton instance and prevents the usage of double-checked
	 * locking. The performance of this pattern is not necessarily better than
	 * the {@code volatile} implementation.
	 * 
	 * @author Yannick Drost
	 * @since 1.0
	 */
	protected static final class InstanceWrapper
	{
		/**
		 * The singleton instance wrapped in this container class. Since it
		 * stores a singleton this field has a {@code final} modifier.
		 */
		public final Application instance;

		/**
		 * Create a new wrapper instance containing the applications singleton.
		 * 
		 * @param value
		 *            The application instance.
		 */
		public InstanceWrapper( final Application value )
		{
			this.instance = value;
		}
	}

	/**
	 * Wrapper object for the singleton instance.
	 */
	protected static InstanceWrapper instanceWrapper;

	/**
	 * Initializes the application instance by a unique identifier.
	 */
	private Application( final String ID )
	{
		if( !isValidID( ID ) )
			throw new IllegalArgumentException( "Invalid argument. The unique identifer for the application cannot be empty or null." );

		{
			// Initializes the substance singleton.
			substance = Substance.get( );

			// Initializes the applications local storage.
			try
			{
				localStorage = new LocalStorage( ID );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}

			// Initializes the applications appearance.
			gui = new GUI( null );
		}

		{
			id = ID;

			// Initializes the application listeners list.
			applicationListeners = new EventListenerList( );

			restartFile = new File( localStorage.getDirectory( ) + File.separator + RESTART_FILE_NAME );
		}

	}

	/**
	 * Returns whether this application has been initialized.
	 * 
	 * @return Whether the application has been initialized.
	 * @see #get()
	 */
	public static boolean running( )
	{
		if( instanceWrapper == null )
			return false;
		else
			return ( instanceWrapper.instance != null );
	}

	/**
	 * Checks whether this given application identifier is valid, means if the
	 * ID is not {@code null} and has a length > 0. Further more there are some
	 * invalid symbols which are not allowed in the identifier.
	 * 
	 * @param appID
	 *            The unique identifier for this application instance.
	 * @return {@code true} if the strings length is greater than 0 and not
	 *         {@code null}, otherwise {@code false}.
	 */
	public static boolean isValidID( String appID )
	{
		final String[] invalidSymbols = { "\\", "/", ":", "*", "?", "\"", "<", ">", "|" };

		if( appID != null && appID.length( ) != 0 )
		{
			for( String s : invalidSymbols )
			{
				if( appID.contains( s ) )
					return false;
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Returns the ID of the application.
	 * 
	 * @return The unique identifier of this application.
	 * 
	 * @throw IllegalStateException If the application has not been initialized
	 *        yet.
	 */
	public String getID( )
	{
		if( id == null )
			throw new IllegalStateException( "No ID available. The application needs to be initialized." );
		return id;
	}

	/**
	 * Returns the application instance since it has been launched previously.
	 * While it has not been launched yet and the application is undefined it
	 * throws an adequate {@code IllegalStateException}.
	 * 
	 * @return The application if already initialized.
	 * 
	 * @see #launch(String)
	 */
	public static synchronized Application get( )
	{
		if( Application.running( ) )
		{
			return instanceWrapper.instance;
		}
		else
		{
			// Maybe return null instead of throwing an exception.
			throw new IllegalStateException( "The application is not running at this time. Cannot get an undefined instance." );
		}
	}

	/**
	 * Launches and creates an application instance by the specified identifier.
	 * This method is usually called within the main method. It must not be
	 * called more than once or an exception will be thrown.
	 * 
	 * <p>
	 * This method is thread-save and ensures that this method is called twice
	 * by different threads.
	 * </p>
	 * 
	 * @param ID
	 *            The unique identifier
	 * @return returns the initialized Application instance.
	 * 
	 * @throws RuntimeException
	 *             While the application already has been launched.
	 * @throws IllegalArgumentException
	 *             If the ID is invalid. Because this value is used for namesake
	 *             in other related fields it needs to meet some requirements.
	 * 
	 * @see Application#isValidID(String)
	 */
	public final static synchronized Application launch( final String ID )
	{
		if( Application.running( ) )
			throw new RuntimeException( "The application has already been initialized." );

		if( !isValidID( ID ) )
			throw new IllegalArgumentException( "Invalid argument. The unique identifer for the application cannot be empty or null." );

		create( ID );

		// Fires an application restarted event.
		if( restarted( ) )
		{
			get( ).fireApplicationRestarted( new ApplicationEvent( get( ), ApplicationEvent.APPLICATION_RESTARTED ) );
		}

		Substance substance = get( ).getSubstance( );
		LocalStorage localStorage = get( ).getLocalStorage( );
		GUI appearance = get( ).getGUI( );

		PropertiesSupport ps = substance.getPropertiesSupport( );

		if( localStorage.containsFile( localStorage.getDirectoryFor( PropertiesSupport.class ), ps.getFilename( ) ) )
		{
			// Check for preset properties to automatically initialize the
			// application instance.
			ps.load( localStorage );

			Properties p = ps.getProperties( );

			if( !p.getProperty( "lookandfeel" ).equals( PropertiesSupport.PROPERTY_UNDEFINED ) )
			{
				String property = p.getProperty( "lookandfeel" );
				
				try
				{
					if( property != null && !property.equals( "" ))
					{
						if( property == "auto" )
							property = RichLookAndFeel.class.getCanonicalName();
						
						appearance.setLookAndFeel( property );
					}
					
				}
				catch ( Exception e )
				{
					String name = UIManager.getSystemLookAndFeelClassName( );
					
					try
					{
						appearance.setLookAndFeel( name );
					}
					catch ( Exception ignore )
					{
						// Could not set the default LookAndFeel
					}
				}
			}
			
			if( !p.getProperty( "name" ).equals( PropertiesSupport.PROPERTY_UNDEFINED ) )
			{
				String property = p.getProperty( "name" );
				System.setProperty( "program.name", property );
			}

			// FIXME Doesn't has any main view at this point
			if( !p.getProperty( "title" ).equals( PropertiesSupport.PROPERTY_UNDEFINED ) )
			{
				String property = p.getProperty( "title" );
				if( appearance.hasMainWindow( ) )
				{
					Window w = appearance.getMainWindow( );

					if( w instanceof Frame )
						( (Frame) w ).setTitle( property );

					if( w instanceof Dialog )
						( (Dialog) w ).setTitle( property );
				}
			}

		}
		else
		{
			// Create some default properties to improve the next application
			// launching.
			Properties p = ps.getProperties( );

			p.setProperty( "name", PropertiesSupport.PROPERTY_UNDEFINED );
			p.setProperty( "author", PropertiesSupport.PROPERTY_UNDEFINED );
			p.setProperty( "publisher", PropertiesSupport.PROPERTY_UNDEFINED );
			p.setProperty( "company", PropertiesSupport.PROPERTY_UNDEFINED );
			p.setProperty( "website", PropertiesSupport.PROPERTY_UNDEFINED );
			p.setProperty( "description", PropertiesSupport.PROPERTY_UNDEFINED );

			p.setProperty( "licence", PropertiesSupport.PROPERTY_UNDEFINED );
			p.setProperty( "copyright", PropertiesSupport.PROPERTY_UNDEFINED );
			p.setProperty( "version", PropertiesSupport.PROPERTY_UNDEFINED );

			p.setProperty( "title", PropertiesSupport.PROPERTY_UNDEFINED );
			p.setProperty( "lookandfeel", PropertiesSupport.PROPERTY_UNDEFINED );

			// p.setProperty( "database", PropertiesService.PROPERTY_UNDEFINED
			// ); Support database connection
			// p.setProperty( "dbuser", PropertiesService.PROPERTY_UNDEFINED );
			// Support database connection
			// p.setProperty( "dbpass", PropertiesService.PROPERTY_UNDEFINED );
			// Support database connection

			// p.setProperty( "sessionuser",
			// PropertiesService.PROPERTY_UNDEFINED ); Support user session
			// p.setProperty( "sessionpass",
			// PropertiesService.PROPERTY_UNDEFINED ); Support user session
		}
		
		

		/*
		 * Adds a global window event listener to the EDT. This is used to shut
		 * down the application while implicit exit, meaning when the last
		 * window is closed the application exits.
		 */
		long eventMask = AWTEvent.WINDOW_EVENT_MASK;
		
		AWTEventListener listener = new AWTEventListener( )
		{
			int visibleWindows = 0;
			
			public void eventDispatched( AWTEvent e )
			{				
				if( e.getID( ) == ( WindowEvent.WINDOW_CLOSING ) )
				{
					visibleWindows--;

					if(visibleWindows == 0 && substance != null && appearance != null && appearance.isImplicitExit( ))
					{
						if( Application.running( ) )
							get().shutdown( );
					}
				}
				
				if( e.getID( ) == ( WindowEvent.WINDOW_OPENED ) )
				{
					visibleWindows++;
				}

			}
		};
		Toolkit.getDefaultToolkit( ).addAWTEventListener( listener, eventMask );

		Runtime.getRuntime( ).addShutdownHook( new Thread( )
		{
			public void run( )
			{
				if( Application.running( ) )
				{
					// DO NOT CALL shutdown() to prevent an application crash
					// due to an infinity loop!
					get( ).close( );
				}
			}
		} );

		get().fireApplicationLaunched( new ApplicationEvent( get(), ApplicationEvent.APPLICATION_LAUNCHED ) );
		
		return Application.get( );
	}

	// /**
	// * This is a cover for {@link #launch(String)} method in which the whole
	// * initialization is run on the EventDispatchThread. While the current
	// * thread is not the DispatchThread this is done by
	// * {@link SwingUtilities#invokeAndWait(Runnable)}.
	// *
	// * @param ID
	// * The unique identifier.
	// * @return The application instance.
	// *
	// * @see #launch(String)
	// */
	// @Deprecated // Only needed if there is code that runs on the EDT. If
	// // the user implements an abstract method for example.
	// public static Application launchOnEDT( String ID )
	// {
	// /*
	// * While the current thread is not the DispatchThread and the
	// * initialization of this application includes any GUI manipulation use
	// * SwingUtilities.invokeAndWait(...)
	// */
	// if ( EventQueue.isDispatchThread( ) )
	// {
	// return launch( ID );
	// }
	// else
	// {
	// try
	// {
	// SwingUtilities.invokeAndWait( new Runnable( )
	// {
	// public void run( )
	// {
	// launch( ID );
	// }
	// } );
	// }
	// catch ( Exception e )
	// {
	// throw new RuntimeException( e );
	// }
	// }
	//
	// return get( );
	// }

	/**
	 * Creates the singleton instance in the most safest way. This ensures that
	 * no second instance might be created at the same time using multi-threaded
	 * applications.
	 * <p>
	 * The {@code ID} parameter is not checked whether it is valid or invalid so
	 * this method will not throw any exceptions.
	 * </p>
	 * 
	 * @param ID
	 *            The unique application ID.
	 * @return The singleton application instance.
	 */
	private static synchronized Application create( final String ID )
	{
		InstanceWrapper wrapper = instanceWrapper;

		if( wrapper == null )
		{
			synchronized ( Application.class )
			{
				if( instanceWrapper == null )
				{
					Application instance = new Application( ID );
					instanceWrapper = new InstanceWrapper( instance );
				}
				wrapper = instanceWrapper;
			}
		}

		return wrapper.instance;
	}

	/**
	 * Checks whether the application launches after a restart has been
	 * initiated.
	 * 
	 * @return
	 */
	private static boolean restarted( )
	{
		if( restartFile.exists( ) )
		{
			restartFile.delete( );
			return true;
		}
		else
			return false;
	}

	/**
	 * Gracefully shuts down the application and closes the JVM. This method is
	 * used to free resources as well as to check if the application is able to
	 * exit or whether there are untreated dependencies.
	 */
	public synchronized void shutdown( )
	{		
		if(shutdownAlreadyInitiated)
		{
			return;
		}
		else
		{
			shutdownAlreadyInitiated = true;
		}
		
		close();
		
		Runtime.getRuntime( ).exit( 0 );
	}
	
	
	/**
	 * 
	 */
	private synchronized void close( )
	{
		// Check if application is able or allowed to exit
		
		
		
		
		
		// Fire closing event
		get( ).fireApplicationClosing( new ApplicationEvent( get( ), ApplicationEvent.APPLICATION_CLOSING ) );
		
		if(createPropertyFileOnShutdown)
			get().getSubstance( ).getPropertiesSupport( ).save( get().getLocalStorage( ) );
		
		
		
		// Finally close streams and free resources
		instanceWrapper = null;
		Substance.substance = null;
		
		applicationListeners = null;
				
	}


	/**
	 * This method is basically used for tests to reset a previously initialized
	 * application instance.
	 */
	void reset( )
	{
		if( running( ) )
		{
			// id = null;

			if( get( ).isLocked( ) )
				get( ).unlock( );
			lockFile = null;
			lockFileChannel = null;
			lock = null;

			Substance.substance = null;
			// substance = null;

			instanceWrapper = null;
		}

	}

	/**
	 * Directly restarts the current program and opens in a new process. After
	 * executed the launch it terminates the old process.
	 * <p>
	 * This method has a synchronized modifier due to it creates a temporary 
	 * file to indicate a performed restart for the next application launch.
	 * 
	 * @param args
	 *            The arguments for the application.
	 */
	public synchronized void restart( String... args )
	{
		StringBuilder command = new StringBuilder( );
		command.append( System.getProperty( "java.home" ) + File.separator + "bin" + File.separator + "java " );

		for( String jvmArg : ManagementFactory.getRuntimeMXBean( ).getInputArguments( ) )
		{
			command.append( jvmArg + " " );
		}

		command.append( "-cp " ).append( ManagementFactory.getRuntimeMXBean( ).getClassPath( ) ).append( " " );
		command.append( ApplicationProfiler.getProfiler( ).getMainClassName( ) ).append( " " );

		for( String arg : args )
		{
			command.append( arg ).append( " " );
		}

		// TODO Remove lock file before launching the new process!
		try
		{
			// Creates a temporary file that indicates a restart
			if( restartFile.createNewFile( ) )
				Runtime.getRuntime( ).exec( command.toString( ) );
			else
			{
				restartFile.delete( );
				throw new IllegalStateException(
						"Couldn't restart the application. There already exists a restart file." );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace( );
		}
		System.exit( 0 );
	}


	/**
	 * Locks this application and marks it as a single instance application.
	 * This prevents multiple instantiations of this program by creating a
	 * temporary lock file. The path of this file depends on the current
	 * {@link LocalStorage}.
	 * 
	 * @return {@code true} if the application instance has been locked,
	 *         otherwise {@code false}.
	 * @throws NullPointerException
	 * @throws RuntimeException
	 */
	@SuppressWarnings( "resource" )
	public boolean lockInstance( boolean b )
	{
		if(!b)
		{
			return unlock();
		}
		else
		{
			try
			{
				if( localStorage != null )
				{
					// TODO Name the file similar to the process id and when
					// launching a second instance check if that process really
					// exists. This should avoid blocking a launch while the 
					// previous one was not shut down properly.

					// FIXME Place lock file outside the storage so that it is not
					// moved when the storage is moved to another path. That way the
					// second launch is still able to check against that file.
					lockFile = new File( localStorage.getDirectory( ) + File.separator + "Application.lock" );

					if( !lockFile.getParentFile( ).exists( ) )
						lockFile.getParentFile( ).mkdirs( );
				}
				else
				{
					throw new NullPointerException(
							"Storage needs to be initialized before you can register an instance." );
				}

				// TODO getAllProcesses() returns null! System.out.println(
				// ProcessProfiler.isProcessRunning(ProcessProfiler.getProcessId())
				// );

				lockFileChannel = new RandomAccessFile( lockFile, "rw" ).getChannel( );

				try
				{
					lock = lockFileChannel.tryLock( );
				}
				catch ( OverlappingFileLockException e )
				{
					e.printStackTrace( );
				}

				if( lock == null )
				{
					lockFileChannel.close( );
					throw new RuntimeException( "Only one instance of this program can be run at the same time." );
				}

				Thread shutdown = new Thread( new Runnable( )
				{
					@Override
					public void run( )
					{
						unlock( );
					}
				} );

				Runtime.getRuntime( ).addShutdownHook( shutdown );
			}
			catch ( IOException e )
			{
				throw new RuntimeException( "Could not lock process", e );
			}

			fireApplicationLocked( new ApplicationEvent( get( ), ApplicationEvent.APPLICATION_LOCKED ) );
			return true;
		}
		
	}

	/**
	 * Checks whether this application instance has been registered and if a
	 * relating lock file exists.
	 * 
	 * @return
	 */
	public boolean isLocked( )
	{
		if( lockFile == null )
			return false;

		return ( lockFile.exists( ) && lock != null );
	}

	/**
	 * Unlocks the single instance and enables multiple launches of the
	 * application.
	 * 
	 * @throws IOException
	 */
	private boolean unlock( )
	{
		try
		{
			if( lock != null )
				lock.release( );

			if( lockFileChannel != null )
				lockFileChannel.close( );

			if( lockFile != null )
				lockFile.delete( );

			fireApplicationUnlocked( new ApplicationEvent( get( ), ApplicationEvent.APPLICATION_UNLOCKED ) );
			
			return true;
		}
		catch ( IOException ignore )
		{
			// Nothing
		}

		return false;
	}
	
	
	/**
	 * Writing out several application related properties helps initializing this application the next time it is launched. 
	 * It is also possible to fill in any related properties manually
	 * @param b
	 */
	public void createPropertyFileOnShutdown(boolean b)
	{
		createPropertyFileOnShutdown = b;
	}

	/**
	 * Returns the application context that groups several application related
	 * properties.
	 * 
	 * @return The application context.
	 */
	public Substance getSubstance( )
	{
		return substance;
	}

	/**
	 * Returns the application data storage that provides access to the
	 * associated local data.
	 * 
	 * @return The local storage instance.
	 */
	public LocalStorage getLocalStorage( )
	{
		return localStorage;
	}

	/**
	 * Sets the local storage field. Thus the local storage can be quickly
	 * exchanged and be swapped between other storages.
	 * 
	 * @param local
	 *            The new local storage.
	 */
	public void setLocalStorage( LocalStorage local )
	{
		localStorage = local;
	}

	/**
	 * Returns the appearance object handling the main view of the underlying
	 * application.
	 * 
	 * @return The appearance object holding viewable data.
	 */
	public GUI getGUI( )
	{
		return gui;
	}

	/**
	 * Sets the appearance field.
	 * 
	 * @param appearance
	 *            The new appearance object.
	 */
	public void setGUI( GUI appearance )
	{
		this.gui = appearance;
	}

	//////////////////////////////////////////////////////////////////////////////
	//
	// Eventlistener
	//
	//////////////////////////////////////////////////////////////////////////////

	public ApplicationListener[] getApplicationListeners( )
	{
		return applicationListeners.getListeners( ApplicationListener.class );
	}

	public void addApplicationListener( ApplicationListener listener )
	{
		applicationListeners.add( ApplicationListener.class, listener );
	}

	public void removeApplicationListener( ApplicationListener listener )
	{
		applicationListeners.remove( ApplicationListener.class, listener );
	}

	protected void fireApplicationLaunched( ApplicationEvent e )
	{
		ApplicationListener[] listeners = applicationListeners.getListeners( ApplicationListener.class );

		for( ApplicationListener l : listeners )
		{
			l.applicationLaunched( e );
		}
	}

	protected void fireApplicationClosing( ApplicationEvent e )
	{
		ApplicationListener[] listeners = applicationListeners.getListeners( ApplicationListener.class );

		for( ApplicationListener l : listeners )
		{
			l.applicationClosing( e );
		}
	}

	protected void fireApplicationUpdated( ApplicationEvent e )
	{
		ApplicationListener[] listeners = applicationListeners.getListeners( ApplicationListener.class );

		for( ApplicationListener l : listeners )
		{
			l.applicationUpdated( e );
		}
	}

	protected void fireApplicationLocked( ApplicationEvent e )
	{
		ApplicationListener[] listeners = applicationListeners.getListeners( ApplicationListener.class );

		for( ApplicationListener l : listeners )
		{
			l.applicationLocked( e );
		}
	}

	protected void fireApplicationUnlocked( ApplicationEvent e )
	{
		ApplicationListener[] listeners = applicationListeners.getListeners( ApplicationListener.class );

		for( ApplicationListener l : listeners )
		{
			l.applicationUnlocked( e );
		}
	}

	protected void fireApplicationRestarted( ApplicationEvent e )
	{
		ApplicationListener[] listeners = applicationListeners.getListeners( ApplicationListener.class );

		for( ApplicationListener l : listeners )
		{
			l.applicationRestarted( e );
		}
	}

	/**
	 * Sums several information describing this application instance.
	 * 
	 * @return A string describing the associated application instance.
	 */
	@Override
	public String toString( )
	{
		StringBuffer sb = new StringBuffer( );
		sb.append( this.getClass( ).getName( ) );

		sb.append( " id=" + id + " locked=" + isLocked( ) + " platform=" + ApplicationProfiler.OS );
		return sb.toString( );
	}
}
