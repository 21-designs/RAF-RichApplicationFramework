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
final class RichScrollBarThumbPainter extends AbstractPainter<Component>
{
	boolean pressed, mouseOver;
	
	RichScrollBarThumbPainter(boolean mouseOver, boolean pressed)
	{
		this.pressed = pressed;
		this.mouseOver = mouseOver;
	}
	
	@Override
	public void paint( Graphics2D g, Component object, int width, int height )
	{
		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		
		GradientPaint gradient;
		
		if(pressed)
		{
			gradient = new GradientPaint(0, 0, theme.getBackgroundBrighter2( ), 0, height, theme.getBackgroundBrighter( ));
		}
		else if(mouseOver)
		{
			gradient = new GradientPaint(0, 0, theme.getBackgroundBrighter2( ), 0, height, theme.getBackgroundBrighter( ));
		}
		else
		{
			gradient = new GradientPaint(0, 0, theme.getBackgroundBrighter( ), 0, height, theme.getBackground( ));
		}
		
		g.setPaint( gradient );
		g.fillRoundRect( 1, 1, width-2, height-2, 7, 7 );
		
		g.setColor( new Color(12, 12, 12) );
		g.drawRoundRect( 1, 1, width-2, height-2, 7, 7 );
		
		g.drawLine( width/2, 4, width/2, height-4 );
		g.drawLine( width/2-3, 4, width/2-3, height-4 );
		g.drawLine( width/2+3, 4, width/2+3, height-4 );
	}
}
