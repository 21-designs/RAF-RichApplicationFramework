package org.drost.resources;

import java.io.File;
import java.net.URL;

/**
 * 
 * @author kimschorat
 *
 */
public class ResourceAccess
{
	/**
	 * Returns the URL for a specified file in the resource folder.
	 * 
	 * @param path
	 *            the relative path in respect to the resource folder.
	 * @return The URL for the resource file.
	 */
	public static URL getResource( String path )
	{
		if ( path == null || new File( path ).isAbsolute( ) )
			return null;

		return ResourceAccess.class.getClassLoader( ).getResource( "res/" + path );
	}
	
	
	/**
	 * Returns the absolute path of this internal resource package.
	 * 
	 * @return the absolute path of this internal resource package.
	 */
	public static String getAbsolutePath()
	{
		return ResourceAccess.class.getResource( "res/" ).getPath( );
	}
}
