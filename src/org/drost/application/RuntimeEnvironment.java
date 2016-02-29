package org.drost.application;

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
public class RuntimeEnvironment 
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

	
	private RuntimeEnvironment() {}
}
