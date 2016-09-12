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
import java.awt.Insets;
import java.awt.RenderingHints;
import java.lang.reflect.InvocationTargetException;

import javax.swing.LookAndFeel;
import javax.swing.Painter;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.plaf.synth.SynthLookAndFeel;

/**
 * @author kimschorat
 *
 */
public class DawnLookAndFeel extends NimbusLookAndFeel
{
	public final Color DAWN_FOCUS = new Color(255, 134, 59);
	public final Color DAWN_CONTROL = new Color(51, 51, 51);
	public final Color DAWN_BASE = new Color(12, 12, 12);
	public final Color DAWN_LIGHT_BACKGROUND = new Color(102, 102, 102);
	public final Color DAWN_LIGHT = new Color(189, 189, 189);
	public final Color DAWN_PROGRESSBAR_DISABLED = new Color(249, 227, 213);
	
	public DawnLookAndFeel()
	{
		super();
	}
	
	/*
	 * https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/_nimbusDefaults.html
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.plaf.nimbus.NimbusLookAndFeel#initialize()
	 */
	@Override
	public void initialize()
	{
		super.initialize( );
		
		UIDefaults table = this.getDefaults( );
		Object[] uiDefaults = 
		{
			"control", DAWN_CONTROL,
			"nimbusBase", DAWN_BASE,
			"nimbusFocus", DAWN_FOCUS,
			"nimbusLightBackground", DAWN_LIGHT_BACKGROUND,
			"nimbusSelectionBackground", DAWN_FOCUS,
			"nimbusSelectedText", DAWN_BASE,
			
//			"menu", Color.WHITE,
			
			"ProgressBar[Enabled+Indeterminate].foregroundPainter", new ProgressBarIndeterminatePainter( DAWN_FOCUS.brighter( ).brighter( ), DAWN_FOCUS.darker( )),
			"ProgressBar[Disabled+Indeterminate].foregroundPainter", new ProgressBarIndeterminatePainter( DAWN_PROGRESSBAR_DISABLED, DAWN_PROGRESSBAR_DISABLED.darker( )),
			"ProgressBar.tileWidth", 25,
			"ProgressBar.cycleTime", 750,
			"ProgressBar[Enabled+Indeterminate].progressPadding", 0,
			"ProgressBar[Disabled+Indeterminate].progressPadding", 0,
			
			"ScrollBar:\"ScrollBar.button\"[Disabled].foregroundPainter", new ScrollBarButtonPainter(DAWN_LIGHT_BACKGROUND, DAWN_LIGHT),
			"ScrollBar:\"ScrollBar.button\"[Enabled].foregroundPainter", new ScrollBarButtonPainter(DAWN_LIGHT_BACKGROUND, DAWN_LIGHT),
			"ScrollBar:\"ScrollBar.button\"[MouseOver].foregroundPainter", new ScrollBarButtonPainter(DAWN_LIGHT_BACKGROUND.brighter( ), DAWN_LIGHT.brighter( )),
			"ScrollBar:\"ScrollBar.button\"[Pressed].foregroundPainter", new ScrollBarButtonPainter(DAWN_LIGHT_BACKGROUND.darker( ), DAWN_LIGHT.darker( )),
			"ScrollBar:ScrollBarThumb[Enabled].backgroundPainter", new ScrollBarThumbPainter(DAWN_LIGHT_BACKGROUND),
			"ScrollBar:ScrollBarThumb[MouseOver].backgroundPainter", new ScrollBarThumbPainter(DAWN_LIGHT_BACKGROUND.brighter( )),
			"ScrollBar:ScrollBarThumb[Pressed].backgroundPainter", new ScrollBarThumbPainter(DAWN_LIGHT_BACKGROUND.darker( )),
			"ScrollBar:ScrollBarTrack[Enabled].backgroundPainter", new ScrollBarTrackPainter(DAWN_CONTROL),
			"ScrollBar:ScrollBarTrack[Disabled].backgroundPainter", new ScrollBarTrackPainter(DAWN_CONTROL),
			
			"\"Table.editor\"[Disabled].textForeground", DAWN_LIGHT_BACKGROUND,

			"MenuBar:Menu[Selected].textForeground", DAWN_BASE,
			"MenuBar:Menu[Selected].backgroundPainter", new MenuItemSelectedBackgroundPainter(DAWN_FOCUS),
//			"MenuBar.background", Color.WHITE,
			
			"Menu[Enabled+Selected].textForeground", DAWN_BASE,
			"Menu[Enabled+Selected].backgroundPainter", new MenuItemSelectedBackgroundPainter(DAWN_FOCUS),
			"Menu[Enabled+Selected].arrowIconPainter", table.get( "Menu[Enabled].arrowIconPainter" ),
//			"Menu.background", Color.WHITE,
//			"Menu.disabled", Color.WHITE,
//			"Menu.opaque", true,
			"Menu:MenuItemAccelerator[MouseOver].textForeground", DAWN_BASE,
			
//			"Separator.background", Color.WHITE,
//			"Separator.disabled", Color.WHITE,
//			"Separator.opaque", true,
			
			"MenuItem[MouseOver].textForeground", DAWN_BASE,
			"MenuItem[MouseOver].backgroundPainter", new MenuItemSelectedBackgroundPainter(DAWN_FOCUS),
			"MenuItem:MenuItemAccelerator[MouseOver].textForeground", DAWN_BASE,
//			"MenuItem.background", Color.WHITE,
//			"MenuItem.disabled", Color.WHITE,
//			"MenuItem.opaque", true,
			
			"RadioButtonMenuItem[MouseOver].textForeground", DAWN_BASE,
			"RadioButtonMenuItem[MouseOver].backgroundPainter", new MenuItemSelectedBackgroundPainter(DAWN_FOCUS),
			"RadioButtonMenuItem[MouseOver+Selected].textForeground", DAWN_BASE,
			"RadioButtonMenuItem[MouseOver+Selected].backgroundPainter", new MenuItemSelectedBackgroundPainter(DAWN_FOCUS),
			"RadioButtonMenuItem[MouseOver+Selected].checkIconPainter", table.get( "RadioButtonMenuItem[Enabled+Selected].checkIconPainter" ),
			"RadioButtonMenuItem:MenuItemAccelerator[MouseOver].textForeground", DAWN_BASE,
//			"RadioButtonMenuItem.background", Color.WHITE,
//			"RadioButtonMenuItem.disabled", Color.WHITE,
//			"RadioButtonMenuItem.opaque", true,
			
			"CheckBoxMenuItem[MouseOver+Selected].checkIconPainter", table.get( "CheckBoxMenuItem[Enabled+Selected].checkIconPainter" ),
			"CheckBoxMenuItem:MenuItemAccelerator[MouseOver].textForeground", DAWN_BASE,
			"CheckBoxMenuItem[MouseOver+Selected].textForeground", DAWN_BASE,
			"CheckBoxMenuItem[MouseOver+Selected].backgroundPainter", new MenuItemSelectedBackgroundPainter(DAWN_FOCUS),
			"CheckBoxMenuItem[MouseOver].textForeground", DAWN_BASE,
			"CheckBoxMenuItem[MouseOver].backgroundPainter", new MenuItemSelectedBackgroundPainter(DAWN_FOCUS),
//			"CheckBoxMenuItem.background", Color.WHITE,
//			"CheckBoxMenuItem.disabled", Color.WHITE,
//			"CheckBoxMenuItem.opaque", true,
			
//			"PopupMenu.background", Color.WHITE,
//			"PopupMenu.disabled", Color.WHITE,
			"PopupMenu.opaque", false,
			"PopupMenu[Enabled].backgroundPainter", new PopupMenuPainter(Color.WHITE),
			"PopupMenu[Disabled].backgroundPainter", new PopupMenuPainter(Color.WHITE),	
			
//			"PopupMenuSeparator.background", Color.WHITE,
//			"PopupMenuSeparator.disabled", Color.WHITE,
//			"PopupMenuSeparator.opaque", true,
			
		};
		table.putDefaults( uiDefaults );
	}
	
	@Override
	public void initClassDefaults(UIDefaults table)
	{
		super.initClassDefaults( table );
		
		// Apply some custom UI classes here. { propertyname, classname, ... }
//		Object[] uiDefaults = {
//				
//		};
//		table.putDefaults( uiDefaults );
	}
	
	@Override
	public void initComponentDefaults(UIDefaults table)
	{
		super.initComponentDefaults( table ); 
		
		// Apply some custom UI classes here. { propertyname, classname, ... }
//		Object[] uiDefaults = {
//				
//		};
//		table.putDefaults( uiDefaults );
	}
	
	@Override
	public String getDescription()
	{
		return "Dark Orange LookAndFeel";
	}
	
	@Override
	public String getID()
	{
		return "Dawn";
	}
	
	@Override
	public String getName()
	{
		return "Dawn";
	}
	
	@Override
	public boolean isNativeLookAndFeel( )
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSupportedLookAndFeel( )
	{
		// TODO Auto-generated method stub
		return true;
	}

}
