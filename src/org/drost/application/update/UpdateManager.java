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
package org.drost.application.update;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;

import javax.swing.JOptionPane;

/**
 *
 * @author Thomas Otero H3R3T1C
 */
public class UpdateManager
{	
	/**
	 * The dialog to be shown when the update is performed.
	 */
	UpdateDialog updateDialog = null;
	
	/**
	 * This stores the relative location of the update library. The correct
	 * location is {@value #updateInstallerFile}, otherwise the update could not
	 * be started.
	 */
	public static final String updateInstallerFile = "application.update.installer/ApplicationUpdateInstaller.jar";
	
	/**
	 * Holds the address of the file storing the latest version number.
	 */
    protected String versionURL = null;
    
    /**
	 * Holds the address of the file storing the latest history or changelog.
	 */
    protected String historyURL = null;
    
    /**
	 * Holds the address of the file storing the download link.
	 */
    protected String downloadURL = null;
        
    /**
     * Creates a {@code ApplicationUpdateManager} instance and initializes the 
     * download link attribute.
     * 
     * @param downloadURL
     * @see #downloadURL
     */
    public UpdateManager(String downloadURL)  
    {
    	this(downloadURL, null, null);
    }
    
    public UpdateManager(String downloadURL, String versionURL)
    {
    	this(downloadURL, versionURL, null);
    }
    
    public UpdateManager(String downloadURL, String versionURL, String historyURL)
    {
    	this.downloadURL = downloadURL;
    	this.versionURL = versionURL;
    	this.historyURL = historyURL;
    	
    	try {
			updateDialog = new UpdateDialog(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Returns the latest version extracted from the {@code versionURL}.
     * @return
     * @throws IOException 
     * @throws Exception
     */
    public String getLatestVersion() throws IOException 
    {
    	if(versionURL == null) return null;
    	
        String data = getData(versionURL);
        return data.substring(data.indexOf("[version]")+9,data.indexOf("[/version]"));
    }
    
    
    /**
     * Parses the version string of {@link #getLatestVersion()} to comparable 
     * point number.
     * 
     * @param version A string containing the version
     * @return Returns a point number as a <code>double</code> value.
     */
    public double parseVersionToDouble(String version)
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
    
    
    /**
     * 
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public String getDownloadLink() throws IOException
    {
    	if(downloadURL == null) return null;
    	
		URL url = new URL(downloadURL);

		InputStream html = null;

		html = url.openStream();

		int c = 0;
		StringBuilder buffer = new StringBuilder("");

		while (c != -1) {
			c = html.read();
			buffer.append((char) c);

		}
		return buffer.substring(buffer.indexOf("[url]") + 5, buffer.indexOf("[/url]"));
	}
    
    
    /**
     * 
     * @return
     * @throws IOException 
     * @throws Exception
     */
    public String getReleaseNotes() throws IOException 
    {
    	if(historyURL == null) return null;
    	
        String data = getData(historyURL);
        return data.substring(data.indexOf("[history]")+9,data.indexOf("[/history]"));
    }
    
    
    
    /**
     * Fetches the data of the given address.
     */
    private String getData(String address) throws IOException 
    {
        URL url = new URL(address);
        
        InputStream html = null;

        html = url.openStream();
        
        int c = 0;
        StringBuffer buffer = new StringBuffer("");

		while (c != -1) {
			c = html.read();

			buffer.append((char) c);
		}
        return buffer.toString();
    }
    
    
    /**
     * Starts the update process in an external JVM and closes the current program.
     * The update process requires some presets like the updater library. Because
     * this current JVM launches the {@link #updateInstallerFile} it is necessary to
     * get this file at the right place. 
     * While the execution of the updater produces any errors an exception will be 
     * thrown.
     * 
     * @param restartSystem Indicates if the operating system shall be forced to 
     * restart after the update has finished.
     */
    public final void update(boolean restartSystem)	// TODO This parameter is not visible or accessible to the user.
    {
    	
        try {
        	String mainClassName = null;
        	for (final Map.Entry<String, String> entry : System.getenv().entrySet()) 
    		{
    			if (entry.getKey().startsWith("JAVA_MAIN_CLASS"))
    			{
    				mainClassName = entry.getValue();
    				break;
    			}
    		}
        	
        	Class<?> mainClass = Class.forName( mainClassName );
        	
        	String jarPath = URLDecoder.decode(mainClass.getProtectionDomain()
  				  .getCodeSource()
  				  .getLocation()
  				  .getPath(), "UTF-8");
        	
        	String jarName = new java.io.File(mainClass.getProtectionDomain()
  				  .getCodeSource()
  				  .getLocation()
  				  .getPath())
  				.getName();
        	
        	System.out.println(jarPath);
        	System.out.println(jarName);
        	
        	String[] run = {"java","-jar",updateInstallerFile, "startUpdate", jarPath, getDownloadLink(), String.valueOf(restartSystem)};
            Runtime.getRuntime().exec(run);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Couln'd launch the updater because the file "+updateInstallerFile+" does not exist.");
        }
        System.exit(0);
    }
    
    
    
    /**
	 * Opens a dialog window showing necessary informations related to the
	 * {@code ApplicationUpdateManager}.
	 * <p>
	 * It is possible to initialize the dialog manually but it is recommended to
	 * use this method to show the dialog properly. Only instantiate it yourself
	 * when extending the {@code ApplicationUpdateDialog} to display a custom
	 * UI. For more details on how to implement a custom dialog refer to the
	 * {@link UpdateDialog} class itself.
	 * </p>
	 * 
	 * @see #update(boolean)
	 */
    public void showUpdateDialog()
    {
    	updateDialog.window.setVisible(true);
    }
    
}

