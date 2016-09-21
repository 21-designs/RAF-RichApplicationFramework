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

import javax.swing.Painter;

/**
 * This is the super class of all painters used by the {@code RichLookAndFeel}.
 * All subclasses need to implement the {@code paint}-method of the
 * {@link Painter} interface.
 * <p>
 * This class simply provides a reference to the currently installed theme
 * containing all fonts and colors of the {@code RichLookAndFeel}.
 * 
 * @author Yannick Drost
 * 
 * @see RichTheme
 * @see RichLookAndFeel#getCurrentTheme()
 *
 */
public abstract class AbstractPainter<T> implements Painter<T>
{
	/**
	 * A reference to the current theme of the RichLookAndFeel. This is static
	 * and always non-null. The theme maintains the color palette and the fonts
	 * for this Look and Feel.
	 */
	protected RichTheme theme = RichLookAndFeel.getCurrentTheme( );
	
}
