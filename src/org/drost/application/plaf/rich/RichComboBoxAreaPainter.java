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
import java.awt.RenderingHints;

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
final class RichComboBoxAreaPainter extends AbstractPainter<JComboBox>
{
	protected boolean pressed, enabled, mouseOver;
	
	
	RichComboBoxAreaPainter( boolean pressed, boolean enabled, boolean mouseOver )
	{
		this.pressed = pressed;
		this.enabled = enabled;
		this.mouseOver = mouseOver;
	}
	
	@Override
	public void paint( Graphics2D g, JComboBox object, int width, int height )
	{
		
		if(object.getBorder( ).equals( UIManager.get( "ComboBox.border" ) ))
			return;
		
		int x = 2, y = 2;
		width = width - 4;
		height = height - 4;

		if(object.getBackground( ).equals( UIManager.getColor( "Button.background" ) ))
		{
			g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
			
			GradientPaint shine;
			GradientPaint fill;
			
			if(enabled)
			{
				
				if( pressed )
				{
					shine = new GradientPaint(0, 0, theme.getBackgroundDarker2( ), 0, height, theme.getBackgroundDarker( ) );
					fill = new GradientPaint(0, 0, theme.getBackgroundDarker2( ), 0, height, theme.getBackgroundDarker( ));
				}
				else if(mouseOver)
				{
					// light
					shine = new GradientPaint(0, 0, theme.getBackgroundBrighter2( ).brighter( ), 0, height, theme.getBorderBrighter2( ));
					fill = new GradientPaint(0, 0, theme.getBackgroundBrighter2( ), 0, height, theme.getBackgroundBrighter( ));
					
					// darker
					// shine = new GradientPaint(0, 0, control.darker( ).darker( ), 0, height, control);
					// fill = new GradientPaint(0, 0, control.darker( ), 0, height, control);
					
				}
				else
				{
					shine = new GradientPaint(0, 0, theme.getBackgroundBrighter2( ), 0, height, theme.getBorderBrighter2( ));
					fill = new GradientPaint(0, 0, theme.getBackgroundBrighter( ), 0, height, theme.getBackground( ));
				}
				
				g.setPaint( shine );
				g.drawRoundRect( x+1, y+1, width-3, height-3, 5, 5 );		
				
				g.setPaint( fill );
				g.fillRoundRect( x+2, y+2, width-4, height-4, 3, 3 );
			}
			else
			{
				fill = new GradientPaint(0, 0, theme.getBackgroundBrighter( ), 0, height, theme.getBackground( ));
				g.setPaint( fill );
				g.fillRoundRect( x+1, y+1, width-3, height-3, 5, 5 );
			}
		}
		else
		{
			g.setColor( object.getBackground( ) );
			g.fillRoundRect( x+1, y+1, width-3, height-3, 5, 5 );
		}
		
		
	}
}
