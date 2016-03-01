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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

import org.drost.application.FileStorage;
import org.drost.utils.ByteArrayUtils;

/**
 * 
 * @author kimschorat
 *
 */
public class PreferencesSupport 
{
	public static final String DEFAULT_FILE = "prefs.xml";
	
	protected final String CHUNK_NUM_SEPARATOR = "###";
	
	protected Preferences preferences = null;
	
	protected Class<?> associatedClass = null;
	
	protected String filename;

	/**
	 * Creates a new service that is associated to a specified class.
	 * 
	 * @param associatedClass 
	 */
	public PreferencesSupport(Class<?> associatedClass)
	{
		this.associatedClass = associatedClass;
		
		filename = ((associatedClass == null)) ? DEFAULT_FILE : this.associatedClass.getSimpleName()+".xml";
		
		initializePreferences();
	}
	
	
	
	protected void initializePreferences()
	{
		if(associatedClass == null)
			preferences = Preferences.userRoot();
		else
			preferences = Preferences.userNodeForPackage(associatedClass);
	}
	
	
	public void setValue(String key, String value)
	{
		preferences.put(key, value);
	}
	
	
	public void setValue(String key, int value)
	{
		preferences.putInt(key, value);
	}
	
	
	public void setValue(String key, long value)
	{
		preferences.putLong(key, value);
	}
	
	
	public void setValue(String key, double value)
	{
		preferences.putDouble(key, value);
	}
	
	
	public void setValue(String key, boolean value)
	{
		preferences.putBoolean(key, value);
	}
	
	
	/**
	 * 
	 * 
	 * @param key The identifier for the value.
	 * @param value The data to be stored to this value.
	 */
	public void setValue(String key, float value)
	{
		preferences.putFloat(key, value);
	}
	
	
	/**
	 * In contrast to the actual <code>Preferences</code> API this is
	 * a support for any subclasses extending the <code>Object</code>
	 * type. It converts the state of the object to a <code>byte</code>
	 * array and stores it as a value. Because this is similar to the
	 * common serialization of an object it is important that each 
	 * <code>value</code> type implements the <code>Serializable</code> 
	 * interface.
	 * <p>
	 * Avoid separating key words by {@link #CHUNK_NUM_SEPARATOR} or 
	 * numbers as suffix like
	 * <pre>
	 * "keyword#1", "keyword#2", "keyword#3".
	 * </pre>
	 * This is used to access several chunk when invoked 
	 * {@link #setPreferenceValue(String, Object)} with an large object.
	 * </p>
	 * @param key The identifier for the value.
	 * @param value The data to be stored to this value.
	 */
	public void setValue(String key, Object value)
	{
		byte[] array = ByteArrayUtils.objectToBytesArray(value);
		
		if(array.length > Preferences.MAX_VALUE_LENGTH * 3 / 4)
		{
			byte[][] chunks = ByteArrayUtils.divideByteArrayToChunks(array, (Preferences.MAX_VALUE_LENGTH * 3 / 4));
			for(int i = 0; i < chunks.length; i++)
			{
				byte[] chunk = chunks[i];
				preferences.putByteArray(key + CHUNK_NUM_SEPARATOR + i, chunk);
			}
		}
		else
		{
			preferences.putByteArray(key, array);
		}
	}
	
	
	
	
	
	
	
	public String getValue(String key, String def)
	{
		return preferences.get(key, def);
	}
	
	
	public int getValue(String key, int def)
	{
		return preferences.getInt(key, def);
	}
	
	
	public double getValue(String key, double def)
	{
		return preferences.getDouble(key, def);
	}
	
	
	public float getValue(String key, float def)
	{
		return preferences.getFloat(key, def);
	}
	
	
	public long getValue(String key, long def)
	{
		return preferences.getLong(key, def);
	}
	
	
	public boolean getValue(String key, boolean def)
	{
		return preferences.getBoolean(key, def);
	}
	
	
	
	
	
	/**
	 * 
	 * @param key
	 * @param def
	 * @return
	 */
	public Object getValue(String key, Object def)
	{
		Object o = null;
				
		try 
		{
			String[] keys = filterStringArray(preferences.keys(), key);
			
			if(keys.length == 0)
			{
				setValue(key, def);
				return def;
			}
			
			List<byte[]> chunks = new ArrayList<byte[]>();
			
			for(int i = 0; i < keys.length; i++)
			{
				chunks.add(preferences.getByteArray(key, new byte[0]));
			}
			
			byte[] combinedArray = ByteArrayUtils.combineChunksToByteArray( (byte[][]) chunks.toArray() );
			o = ByteArrayUtils.byteArrayToObject(combinedArray);
		} 
		catch (BackingStoreException e) {
			e.printStackTrace();
		}
		
		return o;
	}
	
	
	
	
	
	
	
	public void remove(String key)
	{
		preferences.remove(key);
	}

	
	public Preferences getPreferences() {
		return preferences;
	}


	public void setPreferences(Preferences prefs) {
		this.preferences = prefs;
	}
	
	
	
	
	
	/////////////////////////////////////////////////////////////////
	//							HELPER
	/////////////////////////////////////////////////////////////////
	



	/**
	 * 
	 * @param array
	 * @param filter
	 * @return
	 */
	private String[] filterStringArray(String[] array, String filter)
	{
		List<String> result = new ArrayList<String>();
		
        for (String key : array) {
            if (key.equals(filter) || (key.contains(filter) && key.contains(CHUNK_NUM_SEPARATOR)) )
            {
            	result.add(key);
            }
        }
        Collections.sort(result);
        
        return (String[]) result.toArray();
	}
	
	
	public void save(FileStorage storage)
	{
		if(storage == null)
			return;
		save(storage.getDirectory());
	}
	
	
	public void save(String dirPath)
	{
		try
		{
			String filename = ((associatedClass != null)) ? DEFAULT_FILE : associatedClass.getSimpleName()+".xml";
		    FileOutputStream fos = new FileOutputStream(dirPath+File.separator+filename);

		    preferences.exportSubtree(fos);
		    fos.close();
		}
		catch (IOException | BackingStoreException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void load(FileStorage storage)
	{
		if(storage == null)
			return;
		load(storage.getDirectory());
	}
	
	
	public void load(String dirPath)
	{
		try
		{
		    FileInputStream fis = new FileInputStream(dirPath+File.separator + filename);

		    Preferences.importPreferences(fis);
		    fis.close();
		}
		catch (IOException | InvalidPreferencesFormatException e)
		{
			e.printStackTrace();
		}
	}
	
	
}
