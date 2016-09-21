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
package org.drost.application.ui;

import java.awt.Component;
import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.drost.application.plaf.rich.RichLookAndFeel;

/**
 * Responsible for all GUI instances and their behavior and their interaction.
 * Manages also the user interaction or authentification
 * 
 * @author kimschorat
 *
 */
@Deprecated // Actually not needed
public class GUIManager
{
	private boolean implicitExit = false;
	
	private List<GUI> guis = new ArrayList<GUI>();
	
	private static GUIManager instance = new GUIManager();
	
	private GUIManager()
	{
		
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static final synchronized GUIManager getGUIManager()
	{
		if(instance == null)
		{
			instance = new GUIManager();
		}
		
		return instance;
	}
	
	
	public List<GUI> getGUIs()
	{
		return guis;
	}
	
	
	public GUI getMainGUI()
	{
		return null;
	}
	
	
	

	/**
	 * When closing the last window the application terminates and shuts down.
	 * 
	 * @param implicit
	 *            Whether to exit the currently running application when the
	 *            last window is closed.
	 */
	public void setImplicitExit(boolean implicit)
	{
		// TODO Ensure the application closes even when the main view is disposed.
		implicitExit = implicit;
	}
	
	
	/**
	 * Returns whether the application terminates if the last open window is
	 * closing.
	 * 
	 * @return Whether the application terminates if the last open window is
	 *         closing.
	 */
	public boolean isImplicitExit()
	{
		return implicitExit;
	}

	/**
	 * Because swing is not thread safe this method ensures that GUI associated
	 * code is run on the event dispatch thread to avoid any unexpected GUI
	 * behavior.
	 * 
	 * @param runnable
	 *            The runnable to be invoked on the EDT.
	 */
	public void runOnEDT(Runnable runnable)
	{
		if (SwingUtilities.isEventDispatchThread()) 
		{
	        runnable.run();
	    } 
		else 
		{
	        SwingUtilities.invokeLater(runnable);
	    }
	}
	
	
	/**
	 * Because swing is not thread safe this method ensures that GUI associated
	 * code is run on the event dispatch thread to avoid any unexpected GUI
	 * behavior.
	 * 
	 * @param runnable
	 *            The runnable to be invoked on the EDT.
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 */
	public void runOnEDTAndWait(Runnable runnable) throws InvocationTargetException, InterruptedException
	{
		if (SwingUtilities.isEventDispatchThread()) 
		{
	        runnable.run();
	    } 
		else 
		{
	        SwingUtilities.invokeAndWait(runnable);
	    }
	}

	
	/**
	 * Sets the applications default {@link RichLookAndFeel} and updates the
	 * current UI components.
	 */
	public void setConsistentLookAndFeel()
	{
		setLookAndFeel( new RichLookAndFeel() );
	}
	
	
	/**
	 * <p>
	 * Applies the specified look and feel to this application, meaning all
	 * associated windows will be updated.
	 * </p>
	 * 
	 * @param qualifiedName
	 *            The name of the <code>LookAndFeel</code>.
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 *             If the L&F is not supported.
	 * @throws InvocationTargetException
	 *             While an exception occurred while running on EDT.
	 * @throws InterruptedException
	 *             While a exception has been thrown while this current thread
	 *             waits for finishing the UI update.
	 * 
	 * @see UIManager#setLookAndFeel(String)
	 * @see SwingUtilities#updateComponentTreeUI(Component)
	 */
	public void setLookAndFeel(String qualifiedName)
	{
		try
		{
			runOnEDTAndWait( new Runnable( )
			{

				@Override
				public void run( )
				{
					try
					{
						UIManager.setLookAndFeel( qualifiedName );
					}
					catch ( ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e )
					{
						e.printStackTrace( );
					}
					for( int i = 0; i < JFrame.getWindows( ).length; i++ )
					{
						Window w = JFrame.getWindows( )[i];
						SwingUtilities.updateComponentTreeUI( w );
					}
				}

			} );
		}
		catch ( InvocationTargetException | InterruptedException e )
		{
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * <p>
	 * This method simply invokes the homonymous method
	 * {@link #setLookAndFeel(String)} passing the class name of the specified class
	 * type.
	 * </p>
	 * 
	 * @param laf
	 *            The <code>LookAndFeel</code> class.
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 */
	public void setLookAndFeel(LookAndFeel laf) 
	{
		setLookAndFeel(laf.getClass().getCanonicalName());
	}
	
	
	
	
	public void repaintAllGUI()
	{
		
	}
	
	
	public void disposeAllGUI()
	{
		
	}
	
	
	
	
}
