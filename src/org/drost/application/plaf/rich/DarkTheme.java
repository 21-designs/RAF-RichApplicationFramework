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

/**
 * DarkTheme is an adapted theme for the {@code RichLookAndFeel} with clearly
 * highlighting. This class extends the abstract {@code RichTheme} and
 * implements the specified abstract methods.
 * <p>
 * Every color in this theme is taken from a very dark color pattern except the
 * foreground and the focus color. Similar dark colors keeps this theme very
 * minimal and focus on text currently highlighted elements.
 * 
 * @author Yannick Drost
 *
 * @see RichTheme
 */
public class DarkTheme extends RichTheme
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1568618345152940596L;

	/**
	 * The focus color to highlight elements.
	 */
	protected Color focus = new Color(255, 106, 84);	// Orange
//	protected Color focus = new Color(254, 46, 100);	// Magenta
	
	/**
	 * A more transparent focus color used for glow effect.
	 */
	protected Color transparentFocus = new Color( focus.getRed( ), focus.getGreen( ), focus.getBlue( ), 150);

	/**
	 * Default background color.
	 */
	protected Color background = new Color(51, 51, 51);
	protected Color backgroundBrighter = background.brighter( );
	protected Color backgroundBrighter2 = background.brighter( ).brighter( );
	protected Color backgroundDarker = background.darker( );
	protected Color backgroundDarker2 = background.darker( ).darker( );
	
	/**
	 * The text color.
	 */
	protected Color foreground = new Color(211, 224, 232);
	
	/**
	 * The disabled text color.
	 */
	protected Color foregroundDisabled = new Color(100, 100, 100);
	
	/**
	 * The base color mainly responsible for borders and shadows.
	 */
	protected Color base = new Color(12, 12, 12);
	protected Color baseBrighter = base.brighter( ).brighter( );
	protected Color baseBrighter2 = base.brighter( ).brighter( ).brighter( ).brighter( );
	protected Color baseDarker = base.darker( ).darker( );
	protected Color baseDarker2 = base.darker( ).darker( ).darker( ).darker( );
	
	/**
	 * All menus background color
	 */
	protected Color menuBackground = Color.WHITE;
	
	/**
	 * A menus border color.
	 */
	protected Color menuBorder = menuBackground.darker( ).darker( );

	/**
	 * The main font used by this theme.
	 */
	protected Font font = new Font("SansSerif", Font.PLAIN, 11);
	
	
	
	
	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getFocus()
	 */
	@Override
	protected Color getFocus( )
	{
		return focus;
	}
	
	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getBackgroundEnabled()
	 */
	@Override
	protected Color getBackground( )
	{
		return background;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getBackgroundLowered()
	 */
	@Override
	protected Color getBackgroundDarker( )
	{
		return backgroundDarker;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getBackgroundRaised()
	 */
	@Override
	protected Color getBackgroundBrighter( )
	{
		return backgroundBrighter;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getBackgroundDarker2()
	 */
	@Override
	protected Color getBackgroundDarker2( )
	{
		return backgroundDarker2;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getBackgroundBrighter2()
	 */
	@Override
	protected Color getBackgroundBrighter2( )
	{
		return backgroundBrighter2;
	}


	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getForeground()
	 */
	@Override
	protected Color getForeground( )
	{
		return foreground;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getForegroundDisabled()
	 */
	@Override
	protected Color getForegroundDisabled( )
	{
		return foregroundDisabled;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getBorder()
	 */
	@Override
	protected Color getBorder( )
	{
		return base;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getBorderLowered()
	 */
	@Override
	protected Color getBorderDarker( )
	{
		return baseDarker;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getBorderRaised()
	 */
	@Override
	protected Color getBorderBrighter( )
	{
		return baseBrighter;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getBorderDarker()
	 */
	@Override
	protected Color getBorderDarker2( )
	{
		return baseDarker2;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getBorderBrighter()
	 */
	@Override
	protected Color getBorderBrighter2( )
	{
		return baseBrighter2;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getProgressBarDisabled()
	 */
	@Override
	protected Color getProgressBarDisabled( )
	{
		return getBackgroundBrighter( );
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getProgressBarEnabled()
	 */
	@Override
	protected Color getProgressBarEnabled( )
	{
		return getFocus( );
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getMenuBackground()
	 */
	@Override
	protected Color getMenuBackground( )
	{
		return menuBackground;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getMenuBorder()
	 */
	@Override
	protected Color getMenuBorder( )
	{
		return menuBorder;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getName()
	 */
	@Override
	public String getName( )
	{
		return "DarkTheme";
	}
	
	@Override
	boolean isSystemTheme()
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getSelectionBackground()
	 */
	@Override
	protected Color getSelectionBackground( )
	{
		return getFocus( );
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getSelectionForeground()
	 */
	@Override
	protected Color getSelectionForeground( )
	{
		return base;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getFont()
	 */
	@Override
	protected Font getFont( )
	{
		return font;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.plaf.rich.RichTheme#getTransparentFocus()
	 */
	@Override
	public Color getTransparentFocus( )
	{
		return transparentFocus;
	}

}
