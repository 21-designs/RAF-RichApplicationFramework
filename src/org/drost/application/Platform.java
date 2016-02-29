package org.drost.application;

/**
 * Gains access to several platform related information like the current
 * operating system. It allows to differ between most common OS names like
 * several Unix and Windows systems. This class cannot be instantiated to an
 * object.
 * 
 * @author kimschorat
 * @since 1.0
 *
 */
public class Platform 
{
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
	
	
	
	
	private Platform() {}

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

}
