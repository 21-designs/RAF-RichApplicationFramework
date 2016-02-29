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
package org.drost.application;

import java.io.File;
import java.util.Properties;

/**
 * This class stores application related information about the application
 * context. This includes the local file path and other properties. Also it
 * allows to specify the main window for non command line applications.
 * 
 * @author kimschorat
 * @since 1.0
 *
 */
public class Context 
{
	static Context instance = null;
	
	
	private PreferencesService preferencesService;
	
	private PropertiesService propertiesService;
	
	private FileStorage fileStorage;
	
	private View view;
		
	private ApplicationBuild build;
	
	// private LocaleService
	
	// private ResourceBundle
	
	
	/**
	 * Creates a new context and initializes all class fields.
	 * 
	 * @see PreferencesService
	 * @see FileStorage
	 * @see View
	 * @see ApplicationBuild
	 */
	private Context() 
	{
		String local = FileStorage.getSystemDefaultDirectory() + File.separator + Application.getID();
		fileStorage = new FileStorage(local);
		
		// Search for a predefined properties file
		propertiesService = new PropertiesService(Application.class);
		
		// Use the application id instead of this default class.
		preferencesService = new PreferencesService(Application.class);
		
		view = new View();
				
		build = new ApplicationBuild();	
	}
	
	
	
	
	static synchronized Context get()
	{
		if(instance == null)
		{
			synchronized(Context.class)
			{
				if(instance == null)
				{
					instance = new Context();
				}
			}
		}
		
		return instance;
	}




	public PropertiesService getPropertiesService() {
		return propertiesService;
	}




	public void setPropertiesService(PropertiesService service) {
		this.propertiesService = service;
	}




	public PreferencesService getPreferencesService() {
		return preferencesService;
	}




	public void setPreferencesService(PreferencesService preferencesService) {
		this.preferencesService = preferencesService;
	}




	public FileStorage getFileStorage() {
		return fileStorage;
	}




	public void setFileStorage(FileStorage fileStorage) {
		this.fileStorage = fileStorage;
	}




	public View getView() {
		return view;
	}




	public void setView(View view) {
		this.view = view;
	}




	public ApplicationBuild getBuild() {
		return build;
	}




	public void setBuild(ApplicationBuild build) {
		this.build = build;
	}
	
	
	

}
