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
package org.drost.application.suppliers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.drost.application.LocalStorage;
import org.drost.application.interfaces.SaveAsConfiguration;

public class PropertiesSupport implements SaveAsConfiguration
{
	public static final String DEFAULT_FILE = "Application.properties";

	public static final String PROPERTY_UNDEFINED = "undefined";

	protected Properties properties = null;

	protected Class<?> associatedClass = null;

	protected String filename;

	private String filepath = null;
	
	/**
	 * 
	 * @param associatedClass
	 */
	public PropertiesSupport(Class<?> associatedClass)
	{
		properties = new Properties();
		
		this.associatedClass = associatedClass;
		filename = ((associatedClass == null)) ? DEFAULT_FILE : this.associatedClass.getSimpleName()+".properties";
	}
	
	
	
	public String getFilename( )
	{
		return filename;
	}



	public void setFilename( String filename )
	{
		this.filename = filename;
	}



	public Properties getProperties() {
		return properties;
	}


	public void setProperties(Properties properties) {
		this.properties = properties;
	}


	public void save(LocalStorage storage)
	{
		if(storage == null)
			return;
		save(storage.getDirectory());
	}
	
	
	private void save(String directoryPath)
	{
	    try 
	    {
	    	if(!directoryPath.endsWith(File.separator))
			{
				directoryPath += File.separator;
			}
	    	
	    	filepath = directoryPath + filename;
	    	
			FileOutputStream fos = new FileOutputStream(filepath);
			
			properties.store(fos, null);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void load(LocalStorage storage)
	{
		if(storage == null)
			return;
		load(storage.getDirectory());
	}

	
	private void load(String directoryPath)
	{
		try
		{
			if(!directoryPath.endsWith(File.separator))
			{
				directoryPath += File.separator;
			}
			
		    FileInputStream fis = new FileInputStream(directoryPath + filename);

		    properties.load(fis);
		    fis.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Deletes the last saved file while the properties has been saved to a
	 * file.
	 * 
	 * @return {@code true} if and only if the file has been deleted
	 *         successfully, otherwise {@code false}.
	 * 
	 * @see File#delete()
	 */
	public boolean deleteFile()
	{
		if(filepath != null)
			return LocalStorage.implicitDelete(filepath);
		return false;
	}
	
	
	/**
	 * 
	 * @param directoryPath
	 */
	protected void deleteFile(String directoryPath)
	{
		if(directoryPath == null)
			return;
		
		if(!directoryPath.endsWith(File.separator))
		{
			directoryPath += File.separator;
		}
		
		if(new File(directoryPath).exists())
		{
			LocalStorage.implicitDelete(directoryPath + filename);
		}		
	}
	

}
