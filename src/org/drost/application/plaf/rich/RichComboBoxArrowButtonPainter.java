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

import javax.swing.JComboBox;
import javax.swing.Painter;

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
final class RichComboBoxArrowButtonPainter extends AbstractPainter<Component>
{
	boolean editable; 
	boolean enalbed;
	boolean focused;
	boolean mouseOver;
	boolean pressed;

	RichComboBoxArrowButtonPainter(boolean editable, boolean enabled, boolean focused, boolean mouseOver, boolean pressed)
	{
		this.editable = editable;
		this.enalbed = enabled;
		this.focused = focused;
		this.mouseOver = mouseOver;
		this.pressed = pressed;
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.Painter#paint(java.awt.Graphics2D, java.lang.Object, int, int)
	 */
	@Override
	public void paint( Graphics2D g, Component object, int width, int height )
	{
		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

		GradientPaint fill, shine, border;
		
		if(enalbed)
		{
			// Reflection
			g.setColor( theme.getBackgroundBrighter( ) );
			g.drawRoundRect( -1, 3, width-2, height-5, 5, 5 );
			
			// First: Draw the border
			if(pressed || mouseOver)
			{
				border = new GradientPaint(0, 0, theme.getBorder( ), 0, height, theme.getBorder( ));
				g.setPaint( border );
			}
			else
			{
				border = new GradientPaint(0, 0, theme.getBorder( ), 0, height, theme.getBorderBrighter( ));
				g.setPaint( border );
			}
			// Draw rounded border
			g.drawRoundRect( 0, 2, width-3, height-5, 4, 4 );
			// Draw left edge and overwrite the left rounded corners
			g.drawLine( 0, 2, 0, height-3 );
			
			if(focused)
			{
				// Focus border
				g.setColor( theme.getFocus( ) );
				g.drawRoundRect( 0, 2, width-5, height-3, 6, 6 );
				
				g.setColor( theme.getTransparentFocus() );
				g.drawRoundRect( 0, 1, width+5, height-1, 8, 8 );
			}
			
			
			
			// Second: Fill the button
			if(pressed)
			{
				shine = new GradientPaint(0, 0, theme.getBackgroundDarker2( ), 0, height, theme.getBackgroundDarker( ) );
				fill = new GradientPaint(0, 0, theme.getBackgroundDarker2( ), 0, height, theme.getBackgroundDarker( ) );
			}
			else if(mouseOver)
			{
				shine = new GradientPaint(0, 0, theme.getBackgroundBrighter2( ), 0, height, theme.getBorderBrighter2( ) );
				fill = new GradientPaint(0, 0, theme.getBackgroundBrighter2( ), 0, height, theme.getBackgroundBrighter( ) );
			}
			else
			{
				shine = new GradientPaint(0, 0, theme.getBackgroundBrighter2( ), 0, height, theme.getBorderBrighter2( ));
				fill = new GradientPaint(0, 0, theme.getBackgroundBrighter( ), 0, height, theme.getBackground( ));
			}
			
			g.setPaint( shine );
			g.drawRoundRect( 0, 3, width-4, height-7, 3, 3 );		
			
			g.setPaint( fill );
			g.fillRoundRect( 0, 4, width-4, height-8, 1, 1 );
			
			if(editable)
			{
				// Apply a line border that separates from the input field
				g.setPaint( border );
			}
			else
			{
				g.setPaint( fill );
			}
			
			g.drawLine( 0, 3, 0, height-4 );
		}
		else
		{
			// First: Border
			g.setColor( theme.getBackgroundDarker( ) );
			g.drawRoundRect( 0, 2, width-3, height-5, 4, 4 );
			// Draw left edge and overwrite the left rounded corners
			g.drawLine( 0, 2, 0, height-3 );
			
			// Second: Area
			fill = new GradientPaint(0, 0, theme.getBackgroundBrighter( ), 0, height, theme.getBackground( ));
			g.setPaint( fill );
			g.fillRoundRect( 1, 3, width-4, height-7, 3, 3 );
		}
	}

}
