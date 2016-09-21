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
final class RichSpinnerFormattedTextFieldPainter extends AbstractPainter<Component>
{
	boolean enabled, focused;
	
	RichSpinnerFormattedTextFieldPainter( boolean enabled, boolean focused )
	{
		this.enabled = enabled;
		this.focused = focused;
	}
	
	@Override
	public void paint( Graphics2D g, Component object, int width, int height )
	{
		Color border;
		
		if(enabled)
		{
			g.setColor( theme.getBackgroundDarker( ) );
			g.fillRect( 3, 2, width-2, height-5 );

			g.setColor( theme.getShadowDark( ) );
			g.drawLine( 3, 3, width-1, 3 );
			
			g.setColor( theme.getShadowLight( ) );
			g.drawLine( 3, 4, width-1, 4 );
			
			g.setColor( theme.getBackgroundBrighter( ) );
			g.drawLine( 4, height-2, width, height-2 );
			
			border = theme.getBackgroundDarker2( );
		}
		else
		{
			g.setColor( theme.getBackgroundBrighter( ) );
			g.fillRect( 3, 2, width-2, height-5 );

			g.setColor( theme.getShadowLight( ) );
			g.drawLine( 3, 3, width-1, 3 );
			
			border = theme.getBackgroundDarker( );
		}
		
		
		g.setColor( border );
		g.drawRoundRect( 2, 2, width, height-5, 3, 3 );
		
		if(enabled)
		{
			if(focused)
			{
				// Focus border
				g.setColor( theme.getFocus( ) );
				g.drawRoundRect( 1, 1, width+1, height-3, 5, 5 );
				
				g.setColor( theme.getTransparentFocus( ) );
				g.drawRoundRect( 0, 0, width+3, height-1, 8, 8 );
			}
			
		}
	}
}
