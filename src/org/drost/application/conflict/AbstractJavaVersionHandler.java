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
package org.drost.application.conflict;

import java.awt.Desktop;
import java.net.URI;

import org.drost.application.Application;
import org.drost.application.ApplicationProfiler;
import org.drost.application.RuntimeEnvironmentUtils;
import org.drost.application.listeners.ApplicationAdapter;
import org.drost.application.listeners.ApplicationEvent;

/**
 * @author kimschorat
 *
 */
public abstract class AbstractJavaVersionHandler implements ConflictHandler<Double>
{
	/**
	 * Stores the minimum required java version to run properly. Since this is a
	 * <code>double</code> value and not every java version consists of numeric
	 * symbols it needs to get parsed.
	 * <p>
	 * <b>Parsing the required java version number</b>
	 * <p>
	 * First of all take a look at the java version releases. The version mostly
	 * consists out of a major and a minor version. For example the version
	 * {@code 1.4.2_12} is set to the minimum required version where
	 * {@code 1.4.2} is the major version and {@code _12} is the minor
	 * reversion. The string gets parsed as follows:
	 * 
	 * <pre>
	 * 	1.4.2_12 -> 1.4212
	 * </pre>
	 * </p>
	 * To parse the version string to a proper and comparable value refer to
	 * {@code parseJavaVersion(String)}.
	 * </p>
	 * 
	 * @see #parseJavaVersion(String)
	 */
	public double minimumRequiredJavaVersion = 1.0;
	
	
	private boolean registered = false;

	private ApplicationAdapter adapter;
	
	@Override
	public void register( )
	{
		if(isRegistered())
			return;
		
		if(!Application.running( ))
		{
			throw new IllegalStateException("Application needs to get launched before this handler gets registered.");
		}
		
		adapter = new ApplicationAdapter() 
		{
			@Override
			public void applicationLaunched(ApplicationEvent e)
			{
				if( !isValidJavaVersion() )
				{
					handle(new ConflictInfo<Double>(parseJavaVersion(ApplicationProfiler.CURRENT_JAVA_VERSION), Thread.currentThread()));
				}
			}
		};
		
		for( AbstractJavaVersionHandler vh : ConflictManager.getConflictManager( ).getJavaVersionHandlers( ) )
		{
			vh.unregister( );
		}
		
		ConflictManager.getConflictManager( ).getJavaVersionHandlers( ).add( this );
		
		Application.get( ).addApplicationListener( adapter );
		
		registered = true;
	}


	@Override
	public void unregister( )
	{
		if(!Application.running( ))
		{
			throw new IllegalStateException("Application needs to get launched before this handler gets unregistered.");
		}
		
		Application.get( ).removeApplicationListener( adapter );
		
		registered = false;
	}


	@Override
	public boolean isRegistered( )
	{
		return registered;
	}
	
	
	
	

	/**
	 * <p>
	 * Parses the java version string to proper decimal value to compare with
	 * the required minimum version. For example the version {@code 1.4.2_12} is
	 * given as the argument where {@code 1.4.2} is the major version and
	 * {@code _12} is the minor reversion. This method will sequentially remove
	 * all non numeric symbols from the string except for the first decimal
	 * place appearance.
	 * <p>
	 * Accordingly the version {@code 1.4.2_12} will result in {@code 1.4212}
	 * where the second dot symbol and the underline symbol is removed. While
	 * the major version number has a higher priority than the revision,
	 * {@code 1.3126} is lower than {@code 1.4212}.
	 * </p>
	 * </p>
	 * 
	 * @param version
	 *            The java version formatted as string.
	 * @return The version with all non numeric characters removed
	 * 
	 * @see #minimumRequiredJavaVersion
	 * @see SysUtils#CURRENT_JAVA_VERSION
	 */
	public double parseJavaVersion(String version)
	{
		boolean foundDecimalPlace = false;
		
		for(int i = 0; i < version.length(); i++)
		{
			int c = ((int)version.charAt(i));
			
			if( c < 48 || c > 57 )
			{
				if( c == 46 && !foundDecimalPlace)
				{
					foundDecimalPlace = true;
					continue;
				}
				version = version.substring(0, i) + version.substring(i+1);
			}			
		}
		
		return Double.parseDouble(version);
	}
	
//	/**
//	 * 
//	 * @throws InadequateJavaVersionException
//	 */
//	protected void alertForJavaUpdate() 
//	{
//		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
//	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
//	        try {
//	            desktop.browse(new URI("http://java.com/download"));
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	    }
//		
//		throw new RuntimeException("\nYour java version " + ApplicationProfiler.CURRENT_JAVA_VERSION 
//				+ " is too low. Please install " + minimumRequiredJavaVersion 
//				+ " or higher. We recomment to update to " + ApplicationProfiler.LATEST_JAVA_VERSION_AND_REVISION);
//	}
	
	/**
	 * 
	 * @return
	 */
	protected boolean isValidJavaVersion()
	{
		return (parseJavaVersion(ApplicationProfiler.CURRENT_JAVA_VERSION) < minimumRequiredJavaVersion);
	}
	

	/**
	 * Returns the minimum required java version to run this application.
	 * @return The minimum required java version needed.
	 */
	public double getMinimumRequiredJavaVersion() {
		return minimumRequiredJavaVersion;
	}


	/**
	 * Sets the minimum required java version to run this application.
	 * 
	 * @param minimumRequiredJavaVersion Set the lowest java version needed 
	 * to run this application
	 */
	public void setMinimumRequiredJavaVersion(double minimumRequiredJavaVersion) {
		this.minimumRequiredJavaVersion = minimumRequiredJavaVersion;
	}

}
