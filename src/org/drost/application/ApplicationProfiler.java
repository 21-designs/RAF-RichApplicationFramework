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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

/**
 * @author kimschorat
 *
 */
public class ApplicationProfiler
{
	
	
	// Java runtime environment
	
//	private static String fullVersion = getFullVersion();
//	
//	private static URL getVersionURL()
//	{
//		try
//		{
//			// FIXME URL is dead
//			return new URL( "http://java.com/applet/JreCurrentVersion2.txt" );
//		}
//		catch ( MalformedURLException e )
//		{
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
//	
//	private static String getFullVersion()
//	{
//		try
//		{
//			return new BufferedReader(
//					new InputStreamReader( getVersionURL().openStream( ) ) )
//					.readLine( );
//		}
//		catch ( IOException e )
//		{
//			e.printStackTrace();
//		}
//		
//		return "1.8";
//	}
	
//	static {
//		
//		boolean successfullyRetrievedCurrentVersion = false;
//		
//		while(successfullyRetrievedCurrentVersion)
//		{
//			try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL("http://java.com/applet/JreCurrentVersion2.txt").openStream()))) 
//			{
//				fullVersion = br.readLine();
//				successfullyRetrievedCurrentVersion = true;
//			} catch (IOException e) {
//				// FIXME e.printStackTrace(); sometimes throw a java.lang.ExceptionInInitializerError
////				fullVersion = "_";	// Undefined
//			}
//		}
//		
//	}

//	/** Stores the identifier of the latest java version. */
//	public static final String LATEST_JAVA_VERSION_AND_REVISION = fullVersion;
//	
//	/** Stores the number of the current java version. */
//	public static final String LATEST_JAVA_VERSION = fullVersion.split("_")[0];
//	
//	/** Stores the number of the latest java reversion. */
//	public static final String LATEST_JAVA_REVISION = fullVersion.split("_")[1];

	/** Stores the identifier of the current java version. */
	public static final String CURRENT_JAVA_VERSION = System.getProperty("java.version");	
	
	/** True if running Java 1.3 or later. */
	public static final boolean IS_JAVA_13_OR_LATER = System.getProperty("java.version").compareTo("1.3") >= 0;

	/** True if running Java 1.4 or later. */
	public static final boolean IS_JAVA_14_OR_LATER = System.getProperty("java.version").compareTo("1.4") >= 0;

	/** True if running Java 1.5 or later. */
	public static final boolean IS_JAVA_15_OR_LATER = System.getProperty("java.version").compareTo("1.5") >= 0;

	/** True if running Java 1.6 or later. */
	public static final boolean IS_JAVA_16_OR_LATER = System.getProperty("java.version").compareTo("1.6") >= 0;

	/** True if running Java 1.7 or later. */
	public static final boolean IS_JAVA_17_OR_LATER = System.getProperty("java.version").compareTo("1.7") >= 0;
	
	/** True if running Java 1.8 or later. */
	public static final boolean IS_JAVA_18_OR_LATER = System.getProperty("java.version").compareTo("1.8") >= 0;

	
	
	
	
	
	
	
	
	// Platform analyzer
	
	/** Stores the name of the current operating system. */
	public static final String OS = System.getProperty("os.name").toLowerCase();
	
	/** Indicates whether you are running Mac OS */
	public static final boolean IS_MAC_OS = (OS.indexOf("mac") >= 0);
	
	/** Covers several Windows OS. Returns true for one these three, otherwise false */
	public static final boolean IS_WINDOWS_OS = (OS.indexOf("win") >= 0);
	
	/** Covers Unix, Linux and Solaris. Returns true for one these three, otherwise false */
	public static final boolean IS_UNIX_OS = (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	
	/** Indicates whether you are running Solaris */
	public static final boolean IS_SOLARIS_OS = (OS.indexOf("sol") >= 0);
	
	public static boolean isWindows() 
	{
		return IS_WINDOWS_OS;
	}
	
	public static boolean isWindows9X() 
	{
		return (OS.indexOf("95") >= 0 || OS.indexOf("98") >= 0 || OS.indexOf("me") >= 0);
	}
	
	public static boolean isWindows95() 
	{
		return (OS.indexOf("95") >= 0);
	}
	
	public static boolean isWindows98() 
	{
		return (OS.indexOf("98") >= 0);
	}
	
	public static boolean isWindowsME() 
	{
		return (OS.indexOf("me") >= 0);
	}
	
	/**
	 * Returns <code>true</code> while the underlying operating system belongs 
	 * to the NT series (Windows New Technology).
	 * @return <code>true</code> if the operating system is Windows NT, otherwise <code>false</code>.
	 */
	public static boolean isWindowsNT() 
	{
		return (OS.indexOf("NT") >= 0 || OS.indexOf("2000") >= 0 || OS.indexOf("2003") >= 0
		        || OS.indexOf("XP") >= 0);
	}
	
	public static boolean isWindows2000() 
	{
		return (OS.indexOf("2000") >= 0);
	}
	
	public static boolean isWindows2003() 
	{
		return (OS.indexOf("2003") >= 0);
	}
	
	public static boolean isWindowsXP() 
	{
		return (OS.indexOf("XP") >= 0);
	}
	
	public static boolean isWindowsVista() 
	{
		return (OS.indexOf("Vista") >= 0);
	}
	
	public static boolean isLinux()
	{
		return (OS.indexOf("nux") >= 0);
	}

	public static boolean isMac() 
	{
		return IS_MAC_OS;
	}
	
	public static boolean isMacOSX() 
	{
		return (OS.indexOf("os x") >= 0);
	}

	public static boolean isUnix() 
	{
		return IS_UNIX_OS;
	}

	public static boolean isSolaris() 
	{
		return IS_SOLARIS_OS;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * The singleton instance.
	 */
	private static ApplicationProfiler instance = new ApplicationProfiler( );

	/**
	 * The startup time of the application.
	 */
	private final long applicationStartTime;

	/*
	 * Creates a new instance with the current time stamp. Because it is a
	 * static field this time stamp is equal to the applications startup time.
	 */
	private ApplicationProfiler( )
	{
		applicationStartTime = System.currentTimeMillis( );
	}

	/**
	 * Create or return a previous initialized instance.
	 * 
	 * @return The Profiler singleton
	 */
	public static final ApplicationProfiler getProfiler( )
	{
		return instance;
	}

	// Overview

	/**
	 * Returns a time stamp when this application has been launched. The
	 * returned time value is measured in milliseconds.
	 * 
	 * @return
	 */
	public long getApplicationStartTime( )
	{
		return applicationStartTime;
	}

	/**
	 * Returns the time this application is already running since it has been
	 * launched. The returned time value is measured in milliseconds.
	 * 
	 * @return
	 */
	public long getApplicationTotalTime( )
	{
		return ( System.currentTimeMillis( ) - applicationStartTime );
	}

	/**
	 * 
	 * @return
	 */
	public long getProcessID( )
	{

		return Long.parseLong( getProcess( ).split( "@" )[0] );
	}

	public String getProcessName( )
	{
		return getProcess( ).split( "@" )[1];
	}

	private String getProcess( )
	{
		return ManagementFactory.getRuntimeMXBean( ).getName( );
	}

	/**
	 * Fetches the command line arguments previously passed to the Java virtual
	 * machine on startup. This arguments can be defined by the application that
	 * is starting the JVM.
	 * 
	 * @return
	 */
	public String[] getJVMArguments( )
	{
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean( );
		List<String> args = runtime.getInputArguments( );

		return (String[]) args.toArray( );
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
	public String getMainClassName( )
	{
		for( final Map.Entry<String, String> entry : System.getenv( ).entrySet( ) )
		{
			if( entry.getKey( ).startsWith( "JAVA_MAIN_CLASS" ) )
				return entry.getValue( );
		}
		throw new IllegalStateException( "Cannot determine main class in this application." );
	}

	/**
	 * Returns the class type containing the <code>main(String[])</code> method.
	 * 
	 * @return The main class.
	 * @throws ClassNotFoundException
	 */
	public Class<?> getMainClass( ) throws ClassNotFoundException
	{
		return Class.forName( getMainClassName( ) );
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
	public String getJarName( ) throws ClassNotFoundException
	{
		return new java.io.File( getMainClass( ).getProtectionDomain( ).getCodeSource( ).getLocation( ).getPath( ) )
				.getName( );
	}

	/**
	 * Returns the path of the executed jar file.
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws UnsupportedEncodingException
	 * 
	 * @see #getJarName()
	 */
	public String getAbsolutJarPath( ) throws ClassNotFoundException, UnsupportedEncodingException
	{
		return URLDecoder.decode( getMainClass( ).getProtectionDomain( ).getCodeSource( ).getLocation( ).getPath( ),
				"UTF-8" );
	}

	/**
	 * Checks for the given class object whether it is bundled inside a jar
	 * file.
	 * 
	 * @param externalClass
	 * @return
	 * @throws ClassNotFoundException
	 */
	public boolean isLaunchedFromJarFile( ) throws ClassNotFoundException
	{
		Class<?> main = getMainClass( );
		return main.getResource( main.getSimpleName( ) + ".class" ).toString( ).startsWith( "jar:" );
	}

	
	// Disk statistics
	
	/**
	 * Returns the number of free <i>and</i> usable space on the hard drive.
	 * Some operating systems allocate blocks on the disk that are actual free
	 * but not usable by the user.
	 * 
	 * @return
	 */
	public long getFreeDiskSpace()
	{
		return FileSystemView.getFileSystemView().getRoots()[0].getUsableSpace( );
	}
	
	/**
	 * Returns the total number of bytes on the hard drive.
	 * @return
	 */
	public long getTotalDiskSpace()
	{
		return FileSystemView.getFileSystemView().getRoots()[0].getTotalSpace( );
	}
	
	/**
	 * Returns the number of used space on the hard drive.
	 * @return
	 */
	public long getUsedDiskSpace()
	{
		return getTotalDiskSpace() - getFreeDiskSpace();
	}
	
	
	
	
	// Memory statistics

	public long getTotalMemory( )
	{
		return Runtime.getRuntime( ).totalMemory( );
	}

	public long getFreeMemory( )
	{
		return Runtime.getRuntime( ).freeMemory( );
	}

	public long getMaximumMemory( )
	{
		return Runtime.getRuntime( ).maxMemory( );
	}

	public long getUsedMemory( )
	{
		return getTotalMemory( ) - getFreeMemory( );
	}
	
	
	
	// Database connection

	/**
	 * Wrapper method for DataBaseManager
	 * 
	 * @return
	 */
	public int getDBAverageExecutionTime( )
	{
		return 0;
	}

	/**
	 * Wrapper method for DataBaseManager
	 * 
	 * @return
	 */
	public double getDBExecutionsPerMinute( )
	{
		return 0;
	}

}
