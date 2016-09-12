/*
 * This file is part of the yannick drost java accumulation that is divided into
 * different modules.
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
package org.drost.application.adapter;

import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.drost.application.Application;

/**
 * Apply this adapter to a window object to allow keyboard control for this window.
 * 
 * @author kimschorat
 *
 */
public class KeyControlAdapter implements KeyListener
{
	private Window r;

	/**
	 * Creates a new instance with a reference to its window. Cannot be null and
	 * cannot be the main view of this application because it uses
	 * {@code dispose()} on the associated window object.
	 * 
	 * @param reference
	 */
	public KeyControlAdapter( Window reference )
	{
		if(reference == null)
			throw new IllegalArgumentException("Null argument");
		
		try
		{
			if(Application.get( ).getAppearance( ).getMainView( ).equals( reference ))
				throw new IllegalArgumentException("The window cannot be the main view of this application");
		}
		catch(Exception e)
		{
			// ignore
		}
		
		this.r = reference;
	}

	@Override
	public void keyTyped( KeyEvent e )
	{
		
	}

	@Override
	public void keyPressed( KeyEvent e )
	{
		if( e.getKeyCode( ) == KeyEvent.VK_ESCAPE )
			r.dispose( );
	}

	@Override
	public void keyReleased( KeyEvent e )
	{
		// TODO Auto-generated method stub

	}

}
