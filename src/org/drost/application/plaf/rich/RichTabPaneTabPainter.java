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

import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

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
final class RichTabPaneTabPainter extends AbstractPainter<Component>
{
	boolean focused, enabled, mouseOver, pressed, selected;
	
	RichTabPaneTabPainter(boolean focused, boolean enabled, boolean mouseOver, boolean pressed, boolean selected)
	{
		this.focused = focused;
		this.enabled = enabled;
		this.mouseOver = mouseOver;
		this.pressed = pressed;
		this.selected = selected;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Painter#paint(java.awt.Graphics2D, java.lang.Object, int, int)
	 */
	@Override
	public void paint( Graphics2D g, Component object, int width, int height )
	{
		GradientPaint shine;
		GradientPaint fill;
		
		if(enabled)
		{

			if(focused)
			{
				g.setColor( theme.getFocus( ) );
			}
			else
			{
				g.setColor( theme.getBorder( ) );
			}
			
			g.drawRoundRect( 0, 0, width-1, height-1, 3, 3 );
			g.drawLine( 0, height-1, width-1, height-1 );
			
			if( pressed )
			{
				shine = new GradientPaint(0, 0, theme.getBackgroundDarker2( ), 0, height, theme.getBackgroundDarker( ) );
				fill = new GradientPaint(0, 0, theme.getBackgroundDarker2( ), 0, height, theme.getBackgroundDarker( ) );
			}
			else if(mouseOver)
			{
				shine = new GradientPaint(0, 0, theme.getBackgroundBrighter2( ).brighter( ), 0, height, theme.getBackgroundBrighter( ) );
				fill = new GradientPaint(0, 0, theme.getBackgroundBrighter2( ), 0, height, theme.getBackgroundBrighter( ) );
			}
			else if(selected)
			{
				shine = new GradientPaint(0, 0, theme.getBackgroundBrighter2( ).brighter( ), 0, height, theme.getBackgroundBrighter( ));
				fill = new GradientPaint(0, 0, theme.getBackgroundBrighter2( ), 0, height, theme.getBackgroundBrighter( ));
			}
			else
			{
				shine = new GradientPaint(0, 0, theme.getBackgroundBrighter2( ), 0, height, theme.getBackground( ));
				fill = new GradientPaint(0, 0, theme.getBackgroundBrighter( ), 0, height, theme.getBackground( ));
			}
			
			g.setPaint( shine );
			g.drawRoundRect( 1, 1, width-3, height-2, 3, 3 );		
			
			g.setPaint( fill );
			g.fillRoundRect( 2, 2, width-4, height-3, 2, 2 );
			g.drawLine( 1, height-1, width-2, height-1 );
			
			if(selected)
			{
				g.drawLine( 1, height, width-2, height );
			}
		}
		else
		{
			if(selected)
			{
				fill = new GradientPaint(0, 0, theme.getBackgroundBrighter2( ), 0, height, theme.getBackgroundBrighter( ));
			}
			else
			{
				fill = new GradientPaint(0, 0, theme.getBackgroundBrighter( ), 0, height, theme.getBackground( ));
			}
			
			g.setPaint( fill );
			g.fillRoundRect( 1, 1, width-3, height, 2, 2 );
			
			g.setColor( theme.getBackgroundDarker( ) );
			g.drawRoundRect( 0, 0, width-1, height+2, 3, 3 );	
		}
	}
}
