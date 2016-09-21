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
final class RichProgressBarDeterminatePainter extends AbstractPainter<Component>
{
	private GradientPaint	gradient;
	
	boolean enabled;

	RichProgressBarDeterminatePainter( boolean enabled )
	{
		this.enabled = enabled;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Painter#paint(java.awt.Graphics2D, java.lang.Object, int, int)
	 */
	@Override
	public void paint( Graphics2D g, Component object, int width, int height )
	{
		if(enabled)
		{
			g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
			
			gradient = new GradientPaint( ( width / 2.0f ), 0, theme.getProgressBarEnabled( ), ( width / 2.0f ), ( height / 2.0f ), theme.getProgressBarEnabledDarker( ), true );
			g.setPaint( gradient );
			
			g.fillRect( 0, 2, width, height-4 );
			
			gradient = new GradientPaint( ( width / 2.0f ), 0, theme.getProgressBarEnabledBrighter( ), ( width / 2.0f ), ( height / 2.0f ), theme.getProgressBarEnabled( ), true );
			g.setPaint( gradient );
			
//			int[] poly_x = {0, width/2, width, width/2};
//			int[] poly_y = {height-4, height-4, 2, 2};
			
//			g.fillPolygon( poly_x, poly_y, 4 );
			
			g.setColor( theme.getProgressBarEnabledDarker( ).darker( ) );
			g.drawLine( 0, 2, width, 2 );
			g.setColor( theme.getProgressBarEnabledDarker( ).darker( ) );
			g.drawLine( 0, height-3, width, height - 3 );
			
			Color glow = new Color( theme.getProgressBarEnabledDarker( ).getRed( ), theme.getProgressBarEnabledDarker( ).getGreen( ), theme.getProgressBarEnabledDarker( ).getBlue( ), 100 );
			g.setColor( glow );
			g.drawLine( 0, 1, width-1, 1 );
			g.drawLine( 0, height-2, width-1, height - 2 );
		}
		else
		{
			g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
			
			gradient = new GradientPaint( ( width / 2.0f ), 0, theme.getProgressBarDisabled( ), ( width / 2.0f ), ( height / 2.0f ), theme.getProgressBarDisabledDarker( ), true );
			g.setPaint( gradient );
			
			g.fillRect( 0, 2, width, height-4 );
			
			gradient = new GradientPaint( ( width / 2.0f ), 0, theme.getProgressBarDisabledBrighter( ), ( width / 2.0f ), ( height / 2.0f ), theme.getProgressBarDisabled( ), true );
			g.setPaint( gradient );
			
//			int[] poly_x = {0, width/2, width, width/2};
//			int[] poly_y = {height-4, height-4, 2, 2};
			
//			g.fillPolygon( poly_x, poly_y, 4 );
			
			g.setColor( theme.getProgressBarDisabledDarker( ).darker( ) );
			g.drawLine( 0, 2, width, 2 );
			g.setColor( theme.getProgressBarDisabledDarker( ).darker( ) );
			g.drawLine( 0, height-3, width, height - 3 );
			
			Color glow = new Color( theme.getProgressBarDisabledDarker( ).getRed( ), theme.getProgressBarDisabledDarker( ).getGreen( ), theme.getProgressBarDisabledDarker( ).getBlue( ), 100 );
			g.setColor( glow );
			g.drawLine( 0, 1, width-1, 1 );
			g.drawLine( 0, height-2, width-1, height - 2 );
		}
		
	}

}
