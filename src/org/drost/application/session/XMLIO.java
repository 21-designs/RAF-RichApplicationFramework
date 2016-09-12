package org.drost.application.session;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.drost.application.utils.FileUtils;

public class XMLIO
{
	/**
	 * 
	 * @param o
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public static void save(Object o, String filename) throws FileNotFoundException
	{
		if(o == null || filename == null || filename.isEmpty( ))
			throw new IllegalArgumentException("Invalid argument. Cannot serialize object.");
		
		String file = FileUtils.fixFileExtension(filename, "xml");
		
		FileOutputStream fos = new FileOutputStream( file );
		BufferedOutputStream bos = new BufferedOutputStream( fos );
		XMLEncoder x = new XMLEncoder( bos );
		x.writeObject( o );
		x.close( );
	}
	
	
	/**
	 * 
	 * @param filename
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Object load(String filename) throws FileNotFoundException
	{
		if(filename == null || filename.isEmpty( ))
			throw new IllegalArgumentException("Invalid argument. Cannot serialize object.");
		
		String file = FileUtils.fixFileExtension(filename, "xml");
		
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		XMLDecoder x = new XMLDecoder(bis);
		Object o = x.readObject( );
		x.close( );

		return o;
	}
}
