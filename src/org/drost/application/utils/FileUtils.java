package org.drost.application.utils;

import java.io.File;
import java.util.stream.Stream;

public class FileUtils
{
	public static String fixFileExtension(String filename, String extension)
	{
		if(filename == null || extension == null)
			throw new IllegalArgumentException("Null argument. Cannot fix filename.");
		
		if(filename.isEmpty( ) || extension.isEmpty( ))
			throw new IllegalArgumentException("Empty string. Cannot operate on empty filename or file extension.");
		
		if(!extension.startsWith( "." ))
			extension = "." + extension;
		
		return (filename.endsWith( extension )) ? filename : filename + extension;
	}
	
	
	public static String createPath(String parent, String directory) throws IllegalArgumentException
	{		
		if(parent == null || directory == null)
			throw new IllegalArgumentException("Null argument");
		
		if(parent.length( ) == 0 || directory.length( ) == 0)
			throw new IllegalArgumentException("Empty argument");
		
		String[] a = parent.split( File.separator );
		String[] b = directory.split( File.separator );
		
		// FIXME Requires Java 8
		String[] concat = Stream.of(a, b).flatMap(Stream::of).toArray(String[]::new);
		
		for(String dir : concat)
		{
//			System.out.println(dir);
			if(!isValidDirectoryName( dir ))
				throw new IllegalArgumentException("The path name contains invalid characters.");
		}
		
	
		if(directory.startsWith( File.separator ))
		{
			directory.substring( File.separator.length( ) );
		}
		
		return (parent.endsWith( File.separator )) ? parent + directory : parent + File.separator + directory;
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isValidDirectoryName(String name)
	{
		final String[] invalidSymbols = {"\\", "/", ":", "*", "?", "\"", "<", ">", "|"};

		if ( name != null )
		{
			for(String s : invalidSymbols)
			{
				if(name.contains( s ))
					return false;
			}
			return true;
		}
		else
		{
			return false;
		}
	}
}
