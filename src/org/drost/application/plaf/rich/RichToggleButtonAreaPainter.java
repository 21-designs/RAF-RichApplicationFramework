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
final class RichToggleButtonAreaPainter extends RichButtonAreaPainter
{
	protected RichTheme t = RichLookAndFeel.getCurrentTheme( );
	
	protected boolean selected;
	
	/**
	 * @param pressed
	 * @param enabled
	 * @param mouseOver
	 */
	RichToggleButtonAreaPainter( boolean pressed, boolean enabled, boolean mouseOver, boolean selected )
	{
		super( pressed, enabled, mouseOver );
		
		this.selected = selected;
	}
	
	
	@Override
	public void paint( Graphics2D g, AbstractButton object, int width, int height )
	{
		if(!selected)
		{
			super.paint( g, object, width, height );
		}
		else
		{
			g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
			
			int x = 2, y = 2;
			width = width - 4;
			height = height - 4;

			GradientPaint fill;
			
			if(enabled)
			{
				
				
				if( pressed )
				{
					fill = new GradientPaint(0, 0, t.getBackgroundDarker2( ), 0, height, t.getBackgroundDarker( ));
				}
				else if(mouseOver)
				{
					fill = new GradientPaint(0, 0, t.getBackground( ), 0, height, t.getBackground( ));
				}
				else
				{
					fill = new GradientPaint(0, 0, t.getBackgroundDarker( ), 0, height, t.getBackgroundDarker( ));
				}
				
				g.setColor( t.getShadowDark( ) );
				g.drawRoundRect( x+1, y+1, width-3, height-3, 1, 1 );	
				g.setColor( t.getShadowLight( ) );
				g.drawRoundRect( x+1, y+2, width-3, height-4, 1, 1 );	
				
				g.setPaint( fill );
				g.fillRoundRect( x+1, y+3, width-2, height-4, 3, 3 );
			}
			else
			{
				g.setColor( t.getShadowDark( ) );
				g.drawRoundRect( x+1, y+1, width-3, height-3, 1, 1 );	
				g.setColor( t.getShadowLight( ) );
				g.drawRoundRect( x+1, y+2, width-3, height-4, 1, 1 );
				
				fill = new GradientPaint(0, 0, t.getBackground( ), 0, height, t.getBackground( ));
				g.setPaint( fill );
				g.fillRoundRect( x+1, y+3, width-2, height-4, 3, 3 );
			}
		}
	}

}
