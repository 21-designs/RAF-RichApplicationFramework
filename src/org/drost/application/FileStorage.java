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
package org.drost.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;
import java.util.prefs.Preferences;

import org.drost.utils.PlatformUtils;

/**
 * Defines a local file storage interface, This class allows to define a local
 * directory to store all application related data in. Two methods
 * {@code save(Object, String)} and {@code load(String)} are the most important
 * ones to write and and read data from the file system.
 * <p>
 * It is also possible to move or change the current directory while still
 * working on.
 * </p>
 * 
 * @author kimschorat
 * @since 1.0
 *
 */
public final class FileStorage 
{
	/**
	 * Represents a directory for storing application specific data. This path
	 * is initialized by the default application data folder depending on the
	 * underlying operating system. 
	 * 
	 * @see #getDirectory()
	 */
	private String currentDirectory = null;
	
	/**
	 * The system default directory.
	 * 
	 * @see #getSystemDefaultDirectory()
	 */
	private final String defaultDirectory;
	
	/**
	 * The shut down hook that deletes empty application directories.
	 */
	private final Removal deleteDir = new Removal();
	
	
	/**
	 * This special thread checks if the currently used directory is empty. If
	 * it is, this directory will be deleted. Simply add an instance as an shut
	 * down hook to the {@code Runtime} to delete an empty directory on
	 * application exit.
	 * 
	 * @author kimschorat
	 *
	 */
	private class Removal extends Thread
	{
		@Override
		public void run()
		{
			File dir = new File(currentDirectory);
			
			for(String s : dir.list())
			{
				File f = new File(s);
				if(f.isDirectory())
					f.delete();
			}
			
			while(dir != null && dir.exists() && dir.isDirectory() && dir.list().length == 0)
			{
				dir.delete();
				dir = dir.getParentFile();
			}
		}
	}
	
	
	/**
	 * This is a marker interface used to group related files stored to the
	 * application associated storage. Declare classes as a configuration that
	 * gets located to other configuration files since it is saved to the
	 * storage.
	 * 
	 * @author kimschorat
	 *
	 */
	public static interface SaveAsConfiguration
	{
		/**
		 * The associated directory path.
		 */
		public final String path = "config/";
	}
	
	
	/**
	 * This is a marker interface used to group related files stored to the
	 * application associated storage. Declare classes as a resource that
	 * gets located to other resource files since it is saved to the
	 * storage.
	 * 
	 * @author kimschorat
	 *
	 */
	public static interface SaveAsResource
	{
		/**
		 * The associated directory path.
		 */
		public final String path = "resource/";
	}
	
	
	/**
	 * The directory map contains pairs of classes of type {@code Class<?>[]}
	 * and their related directory of type {@code String}. Several classes may
	 * be mapped to one directory to group several related types.
	 * 
	 * @see SaveAsConfiguration
	 * @see SaveAsResource
	 */
	protected HashMap<Class<?>[], String> directoryMap;
	
	
	/**
	 * Creates an object that represents the local storage for the underlying
	 * application.
	 * 
	 * @param directoryPath
	 *            The path to the specified directory. The directory to store
	 *            related files and resources.
	 */
	public FileStorage(String directoryPath)
	{
		directoryMap = new HashMap<Class<?>[], String>();
		directoryMap.put( new Class<?>[] { SaveAsConfiguration.class, Properties.class, Preferences.class },
				SaveAsConfiguration.path );
		directoryMap.put( new Class<?>[] { SaveAsResource.class }, SaveAsResource.path );

		defaultDirectory = getSystemDefaultDirectory();
		
		currentDirectory = directoryPath;
		
		if(!createDirectories(currentDirectory))
		{
			currentDirectory = defaultDirectory;
			createDirectories(currentDirectory);
		}
		
		deleteEmptyDirectoryOnExit(true);
	}	
	
	
	
	/**
	 * Creates all directories of this path if they do not already exist.
	 * Returns {@code false} on any error occurs.
	 * 
	 * @param path
	 *            The directory path to be created.
	 * @return {@code true} if the directories has been created, otherwise
	 *                {@code false}.
	 * 
	 * @see Files#createDirectories(Path,
	 *      java.nio.file.attribute.FileAttribute...)
	 */
	public boolean createDirectories(String path)
	{
		if(pathExists(path))
			return false;
		
		Path pathToFile = Paths.get(path);
		try {
			Files.createDirectories(pathToFile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/**
	 * Checks whether this path already exists and returns {@code false} if it
	 * does not exist. This method does not check if one of the path segments is
	 * a file instead of a directory.
	 * 
	 * @param path
	 *            The directoy path.
	 * @return {@code true} if the directories already exist, otherwise
	 *         {@code false}.
	 */
	public boolean pathExists(String path)
	{
		File f = new File(path);
		if(f.exists())
			return true;
		return false;
	}
	
	
	
	/**
	 * Returns the current directory to store application specific data. By
	 * default this directory is OS specific, mostly where all applications
	 * store the data. While this class depends on the {@code Application}
	 * instance, the directory path is appended with a folder named by the
	 * applications ID. The structure should be similar to:
	 * <pre>
	 * 	.../defaultAppDataFolder/applicationID/
	 * </pre>
	 * 
	 * @return The current directory.
	 * @see #changeDirectory(String)
	 * @see #moveDirectory(String)
	 */
	public String getDirectory() 
	{
		return currentDirectory;
	}
	
	
	/**
	 * Returns a corresponding directory path for the specified class type or
	 * the default predefined storage directory if the argument is {@code null}
	 * or does not match any of the mappings.
	 * 
	 * @param ioClass
	 *            The class that either stores or is stored to the file system.
	 *            The class serves as an selector.
	 * @return The associated sub directory of the storage or the main directory
	 *         while the the argument is unspecified or {@code null}.
	 * 
	 * @see #directoryMap
	 */
	public String getDirectoryFor(Class<?> ioClass)
	{
		String dir = currentDirectory;
		
		if(!dir.endsWith(File.separator))
			dir += File.separator;
		
		for( Class<?>[] classes : directoryMap.keySet() )
		{
			for( Class<?> c : classes )
			{
				if(c.getName().equals(ioClass.getName()))
				{
					return directoryMap.get(classes);
				}
			}
		}
		
		return getDirectory();
	}


	/**
	 * Returns the directory map that is used to resolves several class types to
	 * their proper sub directory paths.
	 * 
	 * @return The directory map.
	 * 
	 * @see #directoryMap
	 */
	public HashMap<Class<?>[], String> getDirectoryMap() {
		return directoryMap;
	}


	/**
	 * Sets the directory map that is used to resolves several class types to
	 * their proper sub directory paths.
	 * 
	 * @param The
	 *            directory map.
	 * 
	 * @see #directoryMap
	 */
	public void setDirectoryMap(HashMap<Class<?>[], String> directoryMap) {
		this.directoryMap = directoryMap;
	}
	
	
	/**
	 * Sets a new map entry to define class specific location rule. This is used
	 * to store class type related content to a certain sub folder in the
	 * storage.
	 * 
	 * @param classes
	 *            The key to resolve class types to their associated sub folder.
	 * @param subDirectory
	 *            The sub folder for the key types in the map.
	 * @throws IllegalArgumentException
	 *             while the arguments are {@code null} or empty.
	 */
	public void putDirectoryMapEntry(Class<?>[] classes, String subDirectory)
	{
		if(classes == null || classes.length == 0)
			throw new IllegalArgumentException("Key argument cannot be null or empty. Specify at least one class.");
		
		if(subDirectory == null || subDirectory.isEmpty())
			throw new IllegalArgumentException("Value argument cannot be null or empty. Declare a valid sub directory path.");
		
		directoryMap.put(classes, subDirectory);
	}



	/**
	 * Sets a new directory for the local data storage. If it does not exist yet
	 * it will be created. All files created before will stay untouched other
	 * than using {@code moveDirectory(String)}.
	 * 
	 * @param directory
	 *            The new application directory for storing data.
	 * @return {@code true} if the new directory has been set, otherwise
	 *         {@code false}.
	 * @see #moveDirectory(File)
	 */
	public boolean changeDirectory(String directory) 
	{
		if(createDirectories(directory))
		{
			this.currentDirectory = directory;
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Chooses and creates if necessary a new directory for the application
	 * specific data. This method copies all files created so far to the new
	 * location and finally deletes the previous directory from disk.
	 * 
	 * @param directory
	 *            The target location to move to.
	 * @return {@code true} since all files has been moved, otherwise
	 *         {@code false}.
	 */
	public boolean moveDirectory(String directory)
	{	
		if(!pathExists(directory))
		{
			if(!createDirectories(directory))
			{
				// This returns false if an exception occurred.
				return false;
			}
		}
		
		this.currentDirectory = directory;
		
		try 
		{
			copyFiles(new File(currentDirectory), directory);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		deleteDirectory();
		changeDirectory(directory);
		return true;
	}
	
	
	/**
	 * Copies a file or a directory and traversing recursively the sub files.
	 * 
	 * @param file
	 *            The source file
	 * @param destinationDirectory
	 *            The target directory
	 * @throws IOException
	 */
	private void copyFiles(File file, String destinationDirectory) throws IOException
    {
        File[]files = file.listFiles();
        for(File ff:files)
        {
        	if(ff.isDirectory()){
				new File(destinationDirectory + File.separator + ff.getName()).mkdir();
				copyFiles(ff, destinationDirectory + File.separator + ff.getName());
            }
            else
            {
				copy(ff.getAbsolutePath(), destinationDirectory + File.separator + ff.getName());
            }
        }
    }
    
    
    /**
	 * Copies a single source file to a specified target destination.
	 * 
	 * @param srFile
	 *            The source file.
	 * @param dtFile
	 *            The destination file.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void copy(String srFile, String dtFile) throws FileNotFoundException, IOException 
	{
		File source = new File(srFile);
		File target = new File(dtFile);
		
		if(!createDirectories(target.getParent()))
			return;

		InputStream in = new FileInputStream(source);

		OutputStream out = new FileOutputStream(target);

		byte[] buffer = new byte[1024];
		int length;
		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		in.close();
		out.close();
	}

	
	/**
	 * Checks whether the specified directory contains the given file.
	 * 
	 * @param filename
	 *            The file to be checked.
	 * @return Whether the directory contains that file.
	 */
	public boolean containsFile(String directory, String filename)
	{
		if(directory == null || directory.length() == 0)
			return false;
		
		if(filename == null || filename.length() == 0)
			return false;
		
		File file = new File(filename);
		if(!file.isAbsolute())
		{
			filename = new File(directory, filename).getAbsolutePath();
			file = new File(filename);
		}
		
		if(file.exists())
			return true;
		return false;
		
	}
	
	
	/**
	 * @see File#deleteOnExit()
	 */
	public void deleteEmptyDirectoryOnExit(boolean b)
	{
		if(b)
			Runtime.getRuntime().addShutdownHook(deleteDir);
		else
			Runtime.getRuntime().removeShutdownHook(deleteDir);
	}
	
	
	/**
	 * Deletes the current directory and all of its content files. 
	 */
	public void deleteDirectory()
	{
		File dir = new File(currentDirectory);
		delete(dir);
	}
	
	
	/**
	 * This simply invokes {@link #delete(File)} using the specified file name.
	 * 
	 * @param file
	 *            The specified file.
	 */
	public static void delete(String file)
	{
		delete(new File(file));
	}
	
	
	/**
	 * Deletes the file specified by the string whether it is a file or a
	 * directory. This method should be used with care because the file gets
	 * deleted even when it still contains any subfiles. All those files also
	 * will be deleted in a recursively way.
	 * 
	 * @param file
	 *            The specified file.
	 */
	public static void delete(File file) 
	{
		if(!file.exists())
			return;
		
		// The isDirectory() test is done in the listFiles() method.
	    File[] contents = file.listFiles(); 
	    if (contents != null) {
	        for (File f : contents) {
	        	delete(f);
	        }
	    }
	    file.delete();
	}



	/**
	 * Returns a directory file of the location to store application related data.
	 * This may be different on different OS.
	 * 
	 * @return The directory file to store application data in.
	 */
	public static String getSystemDefaultDirectory()
	{
		String home = System.getProperty("user.home");
		File parent = new File(home).getParentFile();

		if (PlatformUtils.isWindowsNT()) // NT/2000/XP
		{
			// C:\Documents and Settings\All Users\Application Data
			// Surmise that the "All Users" folder will be a child of the
			// parent of the current user's home folder:
			File folder = new File(parent, "All Users\\Application Data");
			if (folder.canRead() && folder.canWrite())
				return folder.getAbsolutePath();
		}

		else if (PlatformUtils.isWindows9X()) // 95/98/ME
		{
			// C:\Windows
			File folder = new File(home);
			if (folder.canRead() && folder.canWrite())
				return folder.getAbsolutePath();
		}

		else if (PlatformUtils.isWindowsVista()) 
		{
			// C:\ProgramData
			File folder = new File(parent.getParentFile(), "ProgramData");
			if (folder.canRead() && folder.canWrite())
				return folder.getAbsolutePath();

			// C:\Users\Public\AppData
			folder = new File(parent, "Public\\AppData");
			if (folder.canRead() && folder.canWrite())
				return folder.getAbsolutePath();
		}
		else if (PlatformUtils.isMac()) 
		{
			File folder = new File("/Library/Application Support");
			
			// Access denied on default
			if (folder.canRead() && folder.canWrite())
			{
				return folder.getAbsolutePath();
			}
		}
		else 
		{
			File folder = new File("/var/local");
			if (folder.canRead() && folder.canWrite())
				return folder.getAbsolutePath();
			
			folder = new File("/var");
			if (folder.canRead() && folder.canWrite())
				return folder.getAbsolutePath();
		}

		return new File(home).getAbsolutePath();
	}
	
	
	/**
	 * Sets the default system user directory to the specified application data
	 * folder. This method overwrites the current directory that is equal to
	 * {@code System.getProperty("user.dir")}. It checks whether the new path
	 * exists or if it can be created.
	 * <p>
	 * Once this method is called there is no way to undo this. Maybe store the
	 * previous directory for further use.
	 * </p>
	 * 
	 * @return {@code true} if the working directory has been set, otherwise
	 *         {@code false}.
	 */
	public boolean setToWorkingDirectory()
    {
        boolean result = false;  // Boolean indicating whether directory was set
        File userdir = new File(currentDirectory).getAbsoluteFile();
        
        if (userdir.exists() || userdir.mkdirs())
        {
            result = (System.setProperty("user.dir", userdir.getAbsolutePath()) != null);
        }

        return result;
    }
	
	
	
	/**
	 * Writes the {@code object} to the specified file. The object must
	 * implement the {@link Serializable} interface. Since this class represents
	 * the application specific data storage all files are saved relative to a
	 * predefined local directory ({@link FileStorage#getDirectory()}
	 * ). This local storage keeps data all at one place, thus specifying the
	 * save file only a relative path is necessary. This is similar to the
	 * {@code File} class that handles a relative path argument related to the
	 * current working directory.
	 * 
	 * <p>
	 * Usually applications store their data at one place in the file system but
	 * to use this method to write data outside the specified directory use an
	 * absolute path when calling this method. The directory for the application
	 * data is depending on the underlying operating system and can be changed
	 * by {@link #changeDirectory(File)}.
	 * </p>
	 * 
	 * @param object
	 *            The object to write to a file.
	 * @param filename
	 *            The path of the file to write in.
	 * @return {@code true} when the object has been written to the file and
	 *         {@code false} in case of any error.
	 * 
	 * @see #getSystemDefaultDirectory()
	 * @see #getDirectory()
	 * @see #load(String)
	 */
	public boolean save(Serializable object, String filename)
	{
		// Prepend the directory path while this file is not absolute
		if(!new File(filename).isAbsolute())
		{
			filename = new File(currentDirectory, filename).getAbsolutePath();
		}
		
		File file = new File(filename);
		// If the associated directories does not exist create the parent directories
		if( !file.getParentFile().exists() )
		{
			if( !createDirectories(file.getParent()) )
				return false;
		}
		
		FileOutputStream fos;
		try 
		{
			fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);   
			
			oos.writeObject(object);
			
			oos.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Reads input from the specified file into an object. If the
	 * {@code filename} is not an absolute file path the file will be looked up
	 * relative to the local data directory.
	 * 
	 * @param filename
	 *            The file to read from.
	 * @return The stored object or {@code null} in any error case.
	 * 
	 * @see #getSystemDefaultDirectory()
	 * @see #getDirectory()
	 * @see #save(Object, String)
	 */
	public Serializable load(String filename)
	{
		if(!new File(filename).isAbsolute())
		{
			filename = new File(currentDirectory, filename).getAbsolutePath();
		}
		
		FileInputStream fin;
		try 
		{
			fin = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fin);   
			Object obj = ois.readObject();
			ois.close();
			
			return (Serializable) obj;
		} 
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	


}
