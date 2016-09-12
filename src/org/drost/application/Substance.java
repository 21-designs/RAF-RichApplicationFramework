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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.drost.application.conflict.AbstractExceptionHandler;
import org.drost.application.conflict.AbstractInactivityHandler;
import org.drost.application.suppliers.PreferencesSupport;
import org.drost.application.suppliers.PropertiesSupport;

/**
 * This class stores application related information about the application
 * context. This includes the local file path and other properties. Also it
 * allows to specify the main window for non command-line applications.
 * 
 * <p>
 * The singleton instance is accessible by the {@code static} method
 * {@link #get()}. While there are relations to the file system and
 * runtime properties this ensures only one state of this session.
 * </p>
 * 
 * @author kimschorat
 * @since 1.0
 *
 */
@Deprecated // All but the service classes has been implemented by ApplicationProfiler 
public class Substance // Or maybe Content
{
	/**
	 * The singleton instance.
	 */
	static Substance substance = null;
	
	private AbstractExceptionHandler exceptionHandler = null;

	private AbstractInactivityHandler inactiveHandler = null;

//	/**
//	 * Handles global notifications like exceptions or the inactivity state.
//	 */
//	ConflictManager stateChangeController = null;	// TODO Move its content in here.

	private final PreferencesSupport preferencesService;

	/**
	 * The PropertiesService is initialized by the Application class.
	 * Application associated properties should be stored in a same-named
	 * .properties file. Thus such a file can be loaded on application startup
	 * and automatically initialize specific parameters.
	 */
	private final PropertiesSupport propertiesService;

//	private Local storage;
//
//	private Appearance view;

	// private LocaleService
	
	// private DatabaseService

	/**
	 * Creates a new context and initializes all class fields.
	 * 
	 * @see PreferencesSupport
	 * @see LocalStorage
	 * @see Appearance
	 * @see PreloaderSupport
	 */
	private Substance( )
	{
//		stateChangeController = new ConflictManager( );
//
//		view = new Appearance( );
//		
//		// Initializes the storage by a specified path.
//		String local = Local.getSystemDefaultDirectory( ) + File.separator + "Applications" + File.separator
//				+ Application.getID( );
//		storage = new Local( local );

		
		// FIXME Use the application id instead of this default class.
		preferencesService = new PreferencesSupport( Application.class );
		
		// Initializes the applications properties. Note this properties are related to the Application.class
		propertiesService = new PropertiesSupport( Application.class );
		
//		if( storage.containsFile( storage.getDirectoryFor( PropertiesManager.class ), propertiesService.getFilename( ) ) )
//		{
//			// Check for preset properties to automatically initialize the application instance.
//			propertiesService.load( storage );
//			
//			Properties p = propertiesService.getProperties( );
//			
//			if(!p.getProperty( "lookandfeel" ).equals( PropertiesManager.PROPERTY_UNDEFINED ))
//			{
//				String property = p.getProperty( "lookandfeel" );
//				try
//				{
//					if( property != null )
//						view.setLookAndFeel( property );
//				}
//				catch ( Exception e )
//				{
//					// String message = "The LookAndFeel " + lnf + " is not supported.";
//
//					String name = UIManager.getSystemLookAndFeelClassName( );
//					try
//					{
//						view.setLookAndFeel( name );
//					}
//					catch ( Exception ignore )
//					{
//
//					}
//				}
//			}
//			
//			if(!p.getProperty( "title" ).equals( PropertiesManager.PROPERTY_UNDEFINED ))
//			{
//				String property = p.getProperty( "title" );
//				if(view.hasMainView( ))
//				{
//					Window w = view.getMainView( );
//					
//					if(w instanceof Frame)
//						( (Frame) w ).setTitle( property );
//					
//					if(w instanceof Dialog)
//						( (Dialog) w ).setTitle( property );
//				}
//			}
//			
//		}
//		else
//		{
//			// Create some default properties to improve the next application launching.
//			Properties p = propertiesService.getProperties( );
//			
//			p.setProperty( "name", PropertiesManager.PROPERTY_UNDEFINED );
//			p.setProperty( "author", PropertiesManager.PROPERTY_UNDEFINED );
//			p.setProperty( "publisher", PropertiesManager.PROPERTY_UNDEFINED );
//			p.setProperty( "company", PropertiesManager.PROPERTY_UNDEFINED );
//			p.setProperty( "website", PropertiesManager.PROPERTY_UNDEFINED );
//			p.setProperty( "description", PropertiesManager.PROPERTY_UNDEFINED );
//
//			p.setProperty( "licence", PropertiesManager.PROPERTY_UNDEFINED );
//			p.setProperty( "copyright", PropertiesManager.PROPERTY_UNDEFINED );
//			p.setProperty( "version", PropertiesManager.PROPERTY_UNDEFINED );
//
//			p.setProperty( "title", PropertiesManager.PROPERTY_UNDEFINED );
//			p.setProperty( "lookandfeel", PropertiesManager.PROPERTY_UNDEFINED );
//			
//			// p.setProperty( "database", PropertiesService.PROPERTY_UNDEFINED ); Support database connection
//			// p.setProperty( "dbuser", PropertiesService.PROPERTY_UNDEFINED ); Support database connection
//			// p.setProperty( "dbpass", PropertiesService.PROPERTY_UNDEFINED ); Support database connection
//			
//			// p.setProperty( "sessionuser", PropertiesService.PROPERTY_UNDEFINED ); Support user session
//			// p.setProperty( "sessionpass", PropertiesService.PROPERTY_UNDEFINED ); Support user session
//			
//
//			propertiesService.save( storage );
//		}

		
	}

	/**
	 * Returns the singleton context instance. Since it is the first call this
	 * method creates the {@code Context} instance and initializes all related
	 * class fields.
	 * 
	 * @return The singleton instance.
	 */
	static synchronized Substance get( )
	{
		if ( substance == null )
		{
			synchronized ( Substance.class )
			{
				if ( substance == null )
				{
					substance = new Substance( );
				}
			}
		}

		return substance;
	}
	

	
	
	/**
	 * Returns the full name of the main class when available.
	 * 
	 * @return The name of the main class.
	 * @throws IllegalStateException
	 *             Thrown whenever no main class could be detected. This could
	 *             be the case in any library that could not be run and is only
	 *             for import purpose.
	 */
	public String getMainClassName()
	{
		for (final Map.Entry<String, String> entry : System.getenv().entrySet()) 
		{             
			if (entry.getKey().startsWith("JAVA_MAIN_CLASS"))
				return entry.getValue();
		}
		throw new IllegalStateException("Cannot determine main class in this application.");
	}
	
	
	/**
	 * Returns the class type containing the <code>main(String[])</code> method.
	 * 
	 * @return The main class.
	 * @throws ClassNotFoundException
	 */
	public Class<?> getMainClass() throws ClassNotFoundException
	{
		return Class.forName( getMainClassName() );
	}
	
	

	
	/**
	 * Returns the name of the executed jar file. This method only returns a
	 * valid jar file name when bundled all classes to a jar file. Otherwise
	 * this method returns the directory of the <code>class</code> files.
	 * 
	 * @return A string representing the jar file name if and only if this
	 *         method is called from within a jar file, otherwise it returns the
	 *         class path.
	 * @throws ClassNotFoundException
	 * 
	 * @see {@link #getAbsolutJarPath()}
	 */
	public String getJarName() throws ClassNotFoundException
	{	
		return new java.io.File(getMainClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
	}
	
	
	/**
	 * Returns the path of the executed jar file.
	 * @return
	 * @throws ClassNotFoundException
	 * @throws UnsupportedEncodingException
	 * 
	 * @see #getJarName()
	 */
	public String getAbsolutJarPath() throws ClassNotFoundException, UnsupportedEncodingException
	{		
		return URLDecoder.decode(getMainClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
	}
	
	
	/**
	 * Checks for the given class object whether it is bundled inside a jar file.
	 * @param externalClass
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public boolean isJarFile() throws ClassNotFoundException
	{
		Class<?> main = getMainClass();
		return main.getResource(main.getSimpleName()+".class").toString().startsWith("jar:");
	}
	

//	public ConflictManager getStateChangeController( )
//	{
//		return stateChangeController;
//	}
//
//	public void setStateChangeController( ConflictManager stateChangeController )
//	{
//		this.stateChangeController = stateChangeController;
//	}

	public PropertiesSupport getPropertiesSupport( )
	{
		return propertiesService;
	}

	// public void setPropertiesService(PropertiesService service) {
	// this.propertiesService = service;
	// }

	public PreferencesSupport getPreferencesSupport( )
	{
		return preferencesService;
	}
	
	
	/**
	 * Returns the applications exception handler to
	 * 
	 * @return The exception handler.
	 */
	public AbstractExceptionHandler getExceptionHandler( )
	{
		return exceptionHandler;
	}

	/**
	 * Sets a new handler for all uncaught exception occuring in this
	 * application.
	 * 
	 * @param handler
	 *            The handler for uncaught exceptions.
	 */
	public void setExceptionHandler( AbstractExceptionHandler handler )
	{
		exceptionHandler = handler;
		if ( !exceptionHandler.isRegistered( ) )
			exceptionHandler.register( );
	}

	/**
	 * Sets the handler for that case the application/ the user gets inactive.
	 * 
	 * @return The inactive handler.
	 */
	public AbstractInactivityHandler getInactivityHandler( )
	{
		return inactiveHandler;
	}

	/**
	 * Sets a new handler for that case the application/ the user gets inactive.
	 * 
	 * @param handler
	 *            The handler for inactivity.
	 */
	public void setInactivityHandler( AbstractInactivityHandler handler )
	{
		inactiveHandler = handler;
		if ( !inactiveHandler.isRegistered( ) )
			inactiveHandler.register( );
	}

	// public void setPreferencesService(PreferencesService preferencesService)
	// {
	// this.preferencesService = preferencesService;
	// }

}
