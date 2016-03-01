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

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.io.File;
import java.util.Properties;

import javax.swing.UIManager;

import org.drost.application.suppliers.PreferencesSupport;
import org.drost.application.suppliers.PreloaderSupport;
import org.drost.application.suppliers.PropertiesSupport;

/**
 * This class stores application related information about the application
 * context. This includes the local file path and other properties. Also it
 * allows to specify the main window for non command-line applications.
 * 
 * <p>
 * The singleton instance is accessible by the {@code static} method
 * {@link #getContext()}. While there are relations to the file system and
 * runtime properties this ensures only one state of this session.
 * </p>
 * 
 * @author kimschorat
 * @since 1.0
 *
 */
public class Context
{
	/**
	 * The singleton instance.
	 */
	static Context instance = null;

	/**
	 * Handles global notifications like exceptions or the inactivity state.
	 */
	StateChangeController stateChangeController = null;

	private final PreferencesSupport preferencesService;

	/**
	 * The PropertiesService is initialized by the Application class.
	 * Application associated properties should be stored in a same-named
	 * .properties file. Thus such a file can be loaded on application startup
	 * and automatically initialize specific parameters.
	 */
	private final PropertiesSupport propertiesService;

	private FileStorage storage;

	private View view;

	private PreloaderSupport preloader;

	// private LocaleService
	
	// private DatabaseService

	/**
	 * Creates a new context and initializes all class fields.
	 * 
	 * @see PreferencesSupport
	 * @see FileStorage
	 * @see View
	 * @see PreloaderSupport
	 */
	private Context( )
	{
		stateChangeController = new StateChangeController( );

		view = new View( );

		preloader = new PreloaderSupport( );
		
		// Initializes the storage by a specified path.
		String local = FileStorage.getSystemDefaultDirectory( ) + File.separator + "Applications" + File.separator
				+ Application.getID( );
		storage = new FileStorage( local );

		// Initializes the applications properties. Note this properties are related to the Application.class
		propertiesService = new PropertiesSupport( Application.class );
		
		if( storage.containsFile( storage.getDirectoryFor( PropertiesSupport.class ), propertiesService.getFilename( ) ) )
		{
			// Check for preset properties to automatically initialize the application instance.
			propertiesService.load( storage );
			
			Properties p = propertiesService.getProperties( );
			
			if(!p.getProperty( "lookandfeel" ).equals( PropertiesSupport.PROPERTY_UNDEFINED ))
			{
				String property = p.getProperty( "lookandfeel" );
				try
				{
					if( property != null )
						view.setLookAndFeel( property );
				}
				catch ( Exception e )
				{
					// String message = "The LookAndFeel " + lnf + " is not supported.";

					String name = UIManager.getSystemLookAndFeelClassName( );
					try
					{
						view.setLookAndFeel( name );
					}
					catch ( Exception ignore )
					{

					}
				}
			}
			
			if(!p.getProperty( "title" ).equals( PropertiesSupport.PROPERTY_UNDEFINED ))
			{
				String property = p.getProperty( "title" );
				if(view.hasMainView( ))
				{
					Window w = view.getMainView( );
					
					if(w instanceof Frame)
						( (Frame) w ).setTitle( property );
					
					if(w instanceof Dialog)
						( (Dialog) w ).setTitle( property );
				}
			}
			
		}
		else
		{
			// Create some default properties to improve the next application launching.
			Properties p = propertiesService.getProperties( );
			
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
			
			// p.setProperty( "database", PropertiesService.PROPERTY_UNDEFINED ); Support database connection
			// p.setProperty( "dbuser", PropertiesService.PROPERTY_UNDEFINED ); Support database connection
			// p.setProperty( "dbpass", PropertiesService.PROPERTY_UNDEFINED ); Support database connection
			
			// p.setProperty( "sessionuser", PropertiesService.PROPERTY_UNDEFINED ); Support user session
			// p.setProperty( "sessionpass", PropertiesService.PROPERTY_UNDEFINED ); Support user session
			

			propertiesService.save( storage );
		}

		// FIXME Use the application id instead of this default class.
		preferencesService = new PreferencesSupport( Application.class );

	}

	/**
	 * Returns the singleton context instance. Since it is the first call this
	 * method creates the {@code Context} instance and initializes all related
	 * class fields.
	 * 
	 * @return The singleton instance.
	 */
	static synchronized Context getContext( )
	{
		if ( instance == null )
		{
			synchronized ( Context.class )
			{
				if ( instance == null )
				{
					instance = new Context( );
				}
			}
		}

		return instance;
	}

	public StateChangeController getStateChangeController( )
	{
		return stateChangeController;
	}

	public void setStateChangeController( StateChangeController stateChangeController )
	{
		this.stateChangeController = stateChangeController;
	}

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

	// public void setPreferencesService(PreferencesService preferencesService)
	// {
	// this.preferencesService = preferencesService;
	// }

	public FileStorage getFileStorage( )
	{
		return storage;
	}

	public void setFileStorage( FileStorage fileStorage )
	{
		this.storage = fileStorage;
	}

	public View getView( )
	{
		return view;
	}

	public void setView( View view )
	{
		this.view = view;
	}

	public PreloaderSupport getPreloaderSupport( )
	{
		return preloader;
	}

	public void setPreloaderSupport( PreloaderSupport preloader )
	{
		this.preloader = preloader;
	}

}
