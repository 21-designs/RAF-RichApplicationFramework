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
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.Window;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.Painter;
import javax.swing.UIManager;

/**
 * This extends the {@code AbstractPainter<T>} class and implements the
 * {@code paint}-method of the {@link Painter<T>} interface.
 * <p>
 * It is used by the {@link RichLookAndFeel} to render all necessary UI
 * components. This class is responsible for painting the associated component
 * having the same name in the defaults table, accessible by
 * {@code UIManager#getDefaults()}.
 * 
 * @author Yannick Drost
 * 
 * @see AbstractPainter
 * @see Painter
 *
 */
@SuppressWarnings( "rawtypes" )
final class RichComboBoxBorderPainter extends AbstractPainter<JComboBox>
{
	private boolean enabled, pressed;
	
	RichComboBoxBorderPainter(boolean enabled, boolean pressed)
	{
		this.enabled = enabled;
		this.pressed = pressed;
	}
	
	@Override
	public void paint( Graphics2D g, JComboBox object, int width, int height )
	{
		if(object.getBorder( ).equals( UIManager.get( "Button.border" ) ))
			return;
		
//		Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
//		if( c != null && c.equals( object ))
//		{
//			System.out.println( "has focus" );
//		}
//		if(object.hasFocus( ))
//		{
//			System.out.println( "has focus" );
//		}
//		if(object.isFocusOwner( ))
//		{
//			System.out.println( "has focus" );
//		}
		
		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		
		int x = 2, y = 2;
		width = width - 4;
		height = height - 4;
		
		if(enabled)
		{
			// Reflection
			g.setColor( theme.getBackgroundBrighter( ) );
			g.drawRoundRect( x, y+1, width-1, height-1, 5, 5 );
			
			if(pressed)
			{
				g.setColor( theme.getBorder( ) );
			}
			else
			{
				GradientPaint border = new GradientPaint(0, 0, theme.getBorder( ), 0, height, theme.getBorderBrighter( ));
				g.setPaint( border );
			}
			
			g.drawRoundRect( x, y, width-1, height-1, 5, 5 );
		}
		else
		{
			g.setColor( theme.getBackgroundDarker( ) );
			g.drawRoundRect( x, y, width-1, height-1, 5, 5 );
		}

	}
}
