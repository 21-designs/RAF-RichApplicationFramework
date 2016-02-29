package org.drost.application;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.net.URLDecoder;
import java.util.Map;

public class ApplicationBuild
{	
	/**
	 * Creates a object to hold some of the 
	 * @param app
	 */
	ApplicationBuild()
	{
		
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
	public String getMainClassName()
	{
		for (final Map.Entry<String, String> entry : System.getenv().entrySet()) 
		{             
			if (entry.getKey().startsWith("JAVA_MAIN_CLASS"))
				return entry.getValue();
		}
		throw new IllegalStateException("Cannot determine main class in this application.");
	}
	
	
	/**
	 * Returns the class type containing the <code>main(String[])</code> method.
	 * 
	 * @return The main class.
	 * @throws ClassNotFoundException
	 */
	public Class<?> getMainClass() throws ClassNotFoundException
	{
		return Class.forName( getMainClassName() );
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
	public String getJarName() throws ClassNotFoundException
	{	
		return new java.io.File(getMainClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
	}
	
	
	/**
	 * Returns the path of the executed jar file.
	 * @return
	 * @throws ClassNotFoundException
	 * @throws UnsupportedEncodingException
	 * 
	 * @see #getJarName()
	 */
	public String getAbsolutJarPath() throws ClassNotFoundException, UnsupportedEncodingException
	{		
		return URLDecoder.decode(getMainClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
	}
	
	
	/**
	 * Checks for the given class object whether it is bundled inside a jar file.
	 * @param externalClass
	 * @return
	 */
	public boolean isJar(Class<?> externalClass)
	{
		return externalClass.getResource(externalClass.getSimpleName()+".class").toString().startsWith("jar:");
	}
	

	

	/**
	 * Directly restarts the current program and opens in a new process. After
	 * executed the launch it terminates the old process.
	 * 
	 * @param args The arguments for the application.
	 */
	public void restart(String[] args) 
	{
		StringBuilder command = new StringBuilder();
		command.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
		
		for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
			command.append(jvmArg + " ");
		}
				
		command.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
		command.append( getMainClassName() ).append(" ");

		for (String arg : args) {
			command.append(arg).append(" ");
		}
		
		try {
			Runtime.getRuntime().exec(command.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	
	/**
	 * Directly restarts the program without any arguments. It simply invokes 
	 * <code>restart(String[])</code> with an empty array.
	 * 
	 * @see #restart(String[])
	 */
	public void restart()
	{
		restart(new String[0]);
	}
}
