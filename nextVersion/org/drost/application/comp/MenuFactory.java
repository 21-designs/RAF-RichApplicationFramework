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
package org.drost.application.comp;

import java.awt.AWTEvent;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenu;

/**
 * @author kimschorat
 *
 */
public class MenuFactory
{
//	private Window ref;
//	
//	
//	public MenuFactory(Window owner)
//	{
//		ref = owner;
//		
//		/*
//		 * When parameter is null all menu actions are applied to the current
//		 * active window. Actually a menu bar can only be added to one frame
//		 */
//		if(ref == null)
//		{
//			long eventMask = AWTEvent.WINDOW_FOCUS_EVENT_MASK;
//
//			Toolkit.getDefaultToolkit().addAWTEventListener( new AWTEventListener()
//			{
//			    public void eventDispatched(AWTEvent e)
//			    {
//			        ref = (Window) e.getSource( );
//			    }
//			}, eventMask);
//			
//		}
//	}
	
	
	
	
	public JMenu createWindowMenu()
	{
		return null;
	}
}
