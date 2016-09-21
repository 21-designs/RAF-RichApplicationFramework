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

import javax.swing.JProgressBar;
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
final class RichProgressBarBackgroundPainter extends AbstractPainter<JProgressBar>
{
	boolean enabled;
	
	RichProgressBarBackgroundPainter(boolean enabled)
	{
		this.enabled = enabled;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Painter#paint(java.awt.Graphics2D, java.lang.Object, int, int)
	 */
	@Override
	public void paint( Graphics2D g, JProgressBar object, int width, int height )
	{
		// Background
		if(enabled)
		{
			g.setColor( theme.getBackgroundDarker( ) );
			g.fillRect( 1, 3, width-2, height-6 );
		}
		else
		{
			g.setColor( theme.getBackgroundBrighter( ) );
			g.fillRect( 1, 3, width-2, height-6 );
		}
		
		// Border
		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

		if(enabled)
		{
			g.setColor( theme.getBackgroundDarker2( ) );
			g.drawRoundRect( 1, 2, width-3, height-5, 3, 3 );
			
			g.setColor( theme.getShadowDark( ) );
			g.drawLine( 1, 3, width-2, 3 );
			
			g.setColor( theme.getShadowLight( ) );
			g.drawLine( 1, 4, width-2, 4 );
			
			g.setColor( theme.getBackgroundBrighter( ) );
			g.drawLine( 2, height-2, width-3, height-2 );
		}
		else
		{
			g.setColor( theme.getShadowLight( ) );
			g.drawLine( 1, 3, width-2, 3 );
			
			GradientPaint border = new GradientPaint(0, 0, theme.getBackgroundDarker( ), 0, height, theme.getBackgroundBrighter( ));
			g.setPaint( border );
			g.drawRoundRect( 1, 2, width-3, height-5, 3, 3 );
		}
	}

}
