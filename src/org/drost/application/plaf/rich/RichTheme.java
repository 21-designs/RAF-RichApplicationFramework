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
import java.awt.Font;
import java.io.Serializable;
import java.lang.reflect.Method;

import javax.swing.UIDefaults;

/**
 * {@code RichTheme} provides the color palette and fonts used by the Java Look
 * and Feel.
 * <p>
 * {@code RichTheme} is abstract, see {@code DarkTheme} for a concrete
 * implementations.
 * <p>
 * {@code RichLookAndFeel} maintains the current theme that contains all
 * necessary style settings. Refer to {@link RichLookAndFeel#setTheme
 * RichLookAndFeel.setTheme(RichTheme)} for details on changing the current
 * theme.
 * <p>
 * A subclass need only override the protected abstract methods, although a
 * subclass may override other non abstract methods for more control. All
 * implementations of {@code RichTheme} must return {@code non-null} values from
 * all methods. When applying an implementation of {@code RichTheme} to the
 * {@code RichLookAndFeel} it automatically checks whether one of the specified
 * methods is returning {@code null}. This will cause an {@code Exception} and
 * incorrect behavior.
 * <p>
 * It is recommended that all returned colors of the abstract methods are
 * completely opaque to avoid any visual artifacts.
 * 
 * @author Yannick Drost
 *
 */
public abstract class RichTheme implements Serializable
{
	/**
	 * A light transparent dark shadow color.
	 */
	private final Color shadowDark = new Color( Color.black.getRed( ), Color.black.getGreen( ), Color.black.getBlue( ), 200 );
	
	/**
	 * A more transparent shadow color.
	 */
	private final Color shadowLight = new Color( Color.black.getRed( ), Color.black.getGreen( ), Color.black.getBlue( ), 90 );
	
	/**
	 * Returns the focus color.
	 * @return
	 */
	protected abstract Color getFocus( );
	
	/**
	 * Returns a transparent version of the focus color.
	 * @return
	 */
	public abstract Color getTransparentFocus( );

	/**
	 * Returns the background color for enabled components.
	 * @return
	 */
	protected abstract Color getBackground( );
	
	/**
	 * Returns the background color for darker elements.
	 * @return
	 */
	protected abstract Color getBackgroundDarker( );
	
	/**
	 * Returns the background color for brighter elements.
	 * @return
	 */
	protected abstract Color getBackgroundBrighter( );
	
	/**
	 * Returns the background color for more darker elements.
	 * @return
	 */
	protected abstract Color getBackgroundDarker2( );
	
	/**
	 * Returns the background color for more brighter elements.
	 * @return
	 */
	protected abstract Color getBackgroundBrighter2( );
	
	/**
	 * Returns the system foreground color.
	 * @return
	 */
	protected abstract Color getForeground( );
	
	/**
	 * Returns the foreground color for disabled text.
	 * @return
	 */
	protected abstract Color getForegroundDisabled( );
	
	/**
	 * Returns the primary border color.
	 * @return
	 */
	protected abstract Color getBorder( );
	
	/**
	 * Returns a darker border color.
	 * @return
	 */
	protected abstract Color getBorderDarker( );
	
	/**
	 * Returns a brighter border color.
	 * @return
	 */
	protected abstract Color getBorderBrighter( );
	
	/**
	 * Returns the darker border color.
	 * @return
	 */
	protected abstract Color getBorderDarker2( );
	
	/**
	 * Returns the brighter border color.
	 * @return
	 */
	protected abstract Color getBorderBrighter2( );
	
	/**
	 * Returns the primary shadow color.
	 * @return
	 */
	protected final Color getShadowDark( )
	{
		return shadowDark;
	}
	
	/**
	 * Returns the secondary shadow color.
	 * @return
	 */
	protected final Color getShadowLight( )
	{
		return shadowLight;
	}
	
	/**
	 * Returns the main font used for this Look and Feel.
	 * @return
	 */
	protected abstract Font getFont( );
	
	/**
	 * Returns the main color for a disabled progress bar.
	 * @return
	 */
	protected abstract Color getProgressBarDisabled( );
	
	/**
	 * Returns a darker version of the disabled progress bar color.
	 * @return
	 */
	public Color getProgressBarDisabledDarker( )
	{
		return getProgressBarDisabled().darker( );
	}
	
	/**
	 * Returns a darker version of the disabled progress bar color.
	 * @return
	 */
	public Color getProgressBarDisabledBrighter( )
	{
		return getProgressBarDisabled().brighter( );
	}
	
	/**
	 * Returns the main color for an enabled progress bar.
	 * @return
	 */
	protected abstract Color getProgressBarEnabled( );
	
	/**
	 * Returns a darker version of the disabled progress bar color.
	 * @return
	 */
	public Color getProgressBarEnabledDarker( )
	{
		return getProgressBarEnabled().darker( );
	}
	
	/**
	 * Returns a darker version of the disabled progress bar color.
	 * @return
	 */
	public Color getProgressBarEnabledBrighter( )
	{
		return getProgressBarEnabled().brighter( );
	}
	
	/**
	 * Returns the main color for all menus.
	 * @return
	 */
	protected abstract Color getMenuBackground( );
	
	/**
	 * Returns the border color for all menus.
	 * @return
	 */
	protected abstract Color getMenuBorder( );
	
	/**
	 * Returns the selection background color.
	 * @return
	 */
	protected abstract Color getSelectionBackground( );

	/**
	 * Returns the selection foreground color.
	 * @return
	 */
	protected abstract Color getSelectionForeground( );
	
//	/**
//	 * 
//	 * @param table
//	 */
//	public void addCustomEntriesToTable(UIDefaults table) 
//	{
//		// Empty body
//	}
	
	/**
	 * Returns the name of this theme.
	 * @return
	 */
	public abstract String getName( );
	
	/**
	 * Checks every output of the supported method whether it is valid. If any
	 * of these methods return {@code null} this method will return
	 * {@code false}, otherwise {@code true}.
	 * <p>
	 * This method is called each time a theme is applied to the current LAF to
	 * avoid any {@link NullPointerException}s.
	 * </p>
	 * 
	 * @return {@code false} if any of the supported methods returns
	 *         {@code null}, otherwise {@code true}.
	 */
	final boolean verifyTheme( )
	{
		// Iterate all specified methods
		if(getFocus() == null) return false;
		if(getTransparentFocus() == null) return false;
		if(getBackground() == null) return false;
		if(getBackgroundDarker() == null) return false;
		if(getBackgroundBrighter() == null) return false;
		if(getBackgroundDarker2() == null) return false;
		if(getBackgroundBrighter2() == null) return false;
		if(getForeground() == null) return false;
		if(getForegroundDisabled() == null) return false;
		if(getBorder() == null) return false;
		if(getBorderDarker() == null) return false;
		if(getBorderBrighter() == null) return false;
		if(getBorderDarker2() == null) return false;
		if(getBorderBrighter2() == null) return false;
		if(getShadowDark() == null) return false;
		if(getShadowLight() == null) return false;
		if(getFont() == null) return false;
		if(getProgressBarDisabled() == null) return false;
		if(getProgressBarDisabledDarker() == null) return false;
		if(getProgressBarDisabledBrighter() == null) return false;
		if(getProgressBarEnabled() == null) return false;
		if(getProgressBarEnabledDarker() == null) return false;
		if(getProgressBarEnabledBrighter() == null) return false;
		if(getMenuBackground() == null) return false;
		if(getMenuBorder() == null) return false;
		if(getSelectionForeground() == null) return false;
		if(getName() == null) return false;
		
		return true;
	}
	
	/**
	 * Returns {@code true} only if this is a default core theme.
	 * @return
	 */
	boolean isSystemTheme() 
	{
        return false;
    }
}
