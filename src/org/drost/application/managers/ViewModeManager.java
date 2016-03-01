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
package org.drost.application.managers;

import java.awt.Window;

import javax.swing.JWindow;

import org.drost.application.adapter.EdgeSnapAdapter;

public class ViewModeManager 
{
	
	private final EdgeSnapAdapter edgeSnapAdapter;
	
	private boolean snapEnabled = false;
	
	public ViewModeManager()
	{
		edgeSnapAdapter = new EdgeSnapAdapter();
	}



	/**
	 * Enables or disables the edge snapping functionality by adding or removing
	 * the adapter from the main view. This method does nothing while no main
	 * view is set or the main view is {@code null}.
	 * 
	 * @param enable
	 *            Enables or disables the snapping functionality.
	 */
	public void enableEdgeSnap(Window window, boolean enable)
	{
		if(window == null)
			return;
		
		if(enable)
		{
			if(!snapEnabled)
			{
				window.addComponentListener(edgeSnapAdapter);
				snapEnabled = true;
			}
		}
		else
		{
			if(snapEnabled)
			{
				window.removeComponentListener(edgeSnapAdapter);
				snapEnabled = false;
			}
		}
	}
	
	
	/**
	 * This field is associated to the main view object. This adapter can be
	 * enabled or disabled and is initially disabled.
	 * 
	 * @return The edge snapping adapter.
	 * 
	 * @see #getMainView()
	 */
	public EdgeSnapAdapter getEdgeSnapAdapter() {
		return edgeSnapAdapter;
	}


	public boolean isEdgeSnapEnabled()
	{
		return snapEnabled;
	}
	
	
	public void windowShake(JWindow view, long millis)
	{
		
	}
	
	
	public void windowFadeOut(JWindow view, long millis)
	{
		
	}
	
	
	public void windowFadeIn(JWindow view, long millis)
	{
		
	}

}
