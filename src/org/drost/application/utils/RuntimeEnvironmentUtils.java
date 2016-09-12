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
package org.drost.application.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 
 * @author kimschorat
 * @since 1.0
 *
 */
public class RuntimeEnvironmentUtils 
{
	private static String fullVersion = "";
	
	static {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL("http://java.com/applet/JreCurrentVersion2.txt").openStream()))) 
		{
			fullVersion = br.readLine();
		} catch (IOException e) {
			// FIXME e.printStackTrace(); sometomes throw a java.lang.ExceptionInInitializerError
			fullVersion = "_";	// Undefined
		}
	}

	/** Stores the identifier of the latest java version. */
	public static final String LATEST_JAVA_VERSION_AND_REVISION = fullVersion;
	
	/** Stores the number of the current java version. */
	public static final String LATEST_JAVA_VERSION = fullVersion.split("_")[0];
	
	/** Stores the number of the latest java reversion. */
	public static final String LATEST_JAVA_REVISION = fullVersion.split("_")[1];

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

	
	private RuntimeEnvironmentUtils() {}
}
