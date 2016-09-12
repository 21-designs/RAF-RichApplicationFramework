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
package org.drost.application.plaf.dawn;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Painter;

/**
 * @author kimschorat
 *
 */
class ProgressBarIndeterminatePainter implements Painter<Component>
{

	private Color			light, dark;
	private GradientPaint	gradient;

	public ProgressBarIndeterminatePainter( Color light, Color dark )
	{
		this.light = light;
		this.dark = dark;
	}

	@Override
	public void paint( Graphics2D g, Component object, int width, int height )
	{
		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		
		gradient = new GradientPaint( ( width / 2.0f ), 0, light, ( width / 2.0f ), ( height / 2.0f ), dark, true );
		g.setPaint( gradient );
		
		g.fillRect( 0, 2, width, height-4 );
		
		gradient = new GradientPaint( ( width / 2.0f ), 0, light.brighter( ), ( width / 2.0f ), ( height / 2.0f ), dark.brighter( ), true );
		g.setPaint( gradient );
		
		int[] x = {0, width/2, width, width/2};
		int[] y = {height-4, height-4, 2, 2};
		
		g.fillPolygon( x, y, 4 );
		
		g.setColor( dark.darker( ) );
		g.drawLine( 0, 2, width, 2 );
		g.setColor( dark.darker( ) );
		g.drawLine( 0, height-3, width, height - 3 );
		
		Color glow = new Color( dark.getRed( ), dark.getGreen( ), dark.getBlue( ), 100 );
		g.setColor( glow );
		g.drawLine( 0, 1, width-1, 1 );
		g.drawLine( 0, height-2, width-1, height - 2 );
	}
}
