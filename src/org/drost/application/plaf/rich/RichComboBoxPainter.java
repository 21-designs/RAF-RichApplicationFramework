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
final class RichComboBoxPainter extends AbstractPainter<JComboBox>
{
	private Painter<JComboBox> focusPainter;
	private Painter<JComboBox> borderPainter;
	private Painter<JComboBox> areaPainter;
	
	RichComboBoxPainter(Painter<JComboBox> focusPainter, Painter<JComboBox> borderPainter, Painter<JComboBox> areaPainter)
	{
		this.focusPainter = focusPainter;
		this.borderPainter = borderPainter;
		this.areaPainter = areaPainter;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Painter#paint(java.awt.Graphics2D, java.lang.Object, int, int)
	 */
	@Override
	public void paint( Graphics2D g, JComboBox object, int width, int height )
	{
		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		
		if(borderPainter != null) borderPainter.paint( g, object, width, height );
		if(areaPainter != null) areaPainter.paint( g, object, width, height );
		
		if(focusPainter != null) focusPainter.paint( g, object, width, height );
	}

}
