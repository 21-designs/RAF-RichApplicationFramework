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
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * @author kimschorat
 *
 */
public class ApplicationProfiler
{
	private static ApplicationProfiler instance = new ApplicationProfiler( );

	private final long applicationStartTime;

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

	// Platform analysis

	// Java Runtime environment

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
