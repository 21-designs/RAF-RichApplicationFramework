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
package org.drost.application.plaf.rich;

import java.awt.Color;

/**
 * @author kimschorat
 *
 */
public class LightTheme extends DarkTheme
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3250041295096346459L;
	
	{
		focus = new Color(0, 128, 255);
		transparentFocus = new Color( focus.getRed( ), focus.getGreen( ), focus.getBlue( ), 150);
		
		background = new Color(171, 171, 171);
		backgroundBrighter = background.brighter( );
		backgroundBrighter2 = background.brighter( ).brighter( );
		backgroundDarker = background.darker( );
		backgroundDarker2 = background.darker( ).darker( );
		
		base = new Color(52, 52, 52);
		baseBrighter = base.brighter( );
		baseBrighter2 = base.brighter( );
		baseDarker = base.darker( );
		baseDarker2 = base.darker( ).darker( );
		
		foreground = new Color(10, 10, 10);
		foregroundDisabled = new Color(60, 60, 60);
	}
	
//	public LightTheme()
//	{
//		this.background = new Color(101, 101, 101);
//	}
}
