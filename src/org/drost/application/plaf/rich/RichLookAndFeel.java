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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.LookAndFeel;
import javax.swing.Painter;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.plaf.synth.SynthLookAndFeel;

/**
 * The Rich Look and Feel class, short RLAF.
 * <p>
 * This LAF uses {@code Painter} objects for rendering the UI because it derives
 * the default table of the {@code NimbusLookAndFeel}.
 * <p>
 * All fonts and colors are obtained by the current installed theme, known by
 * the {@code MetalLookAndFeel}. That way it offers the advantage that it is
 * highly customizable. The default theme is the {@link DarkTheme} but can be
 * changed using {@code #setCurrentTheme(RichTheme)}. For creating a custom
 * theme or to modify an existing one, refer to the {@link RichTheme} for
 * details.
 * 
 * 
 * @author Yannick Drost
 * 
 * @see RichTheme
 * @see DarkTheme
 *
 */
public final class RichLookAndFeel extends NimbusLookAndFeel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7102852649004957021L;
	
	/**
	 * The current theme of this LookAndFeel. A theme contains the main colors
	 * used by this LookAndFeel implementation.
	 * <p>
	 * This field has a {@code static} modifier so that it is accessible by all
	 * the painters used by this Look and Feel, but its visibility is restricted
	 * to the package only.
	 * 
	 * @see RichTheme
	 * @see #setCurrentTheme(RichTheme)
	 * @see #getCurrentTheme()
	 */
	static RichTheme theme = new DarkTheme();
	
	/**
	 * Create a new instance of this Java Look and Feel.
	 */
	public RichLookAndFeel()
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
		
		RichButtonFocusPainter buttonFocusedP = new RichButtonFocusPainter();
		RichButtonBorderPainter buttonBorderEnabledP = new RichButtonBorderPainter(true, false);
		RichButtonBorderPainter buttonBorderEnabledPressedP = new RichButtonBorderPainter(true, true);
		RichButtonBorderPainter buttonBorderDisabledP = new RichButtonBorderPainter(false, false);
		RichButtonAreaPainter buttonEnabledP = new RichButtonAreaPainter(false, true, false);
		RichButtonAreaPainter buttonPressedP = new RichButtonAreaPainter(true, true, false);
		RichButtonAreaPainter buttonMouseOverP = new RichButtonAreaPainter(false, true, true);
		RichButtonAreaPainter buttonDisabledP = new RichButtonAreaPainter(false, false, false);
		
		RichToggleButtonAreaPainter toggleButtonEnabledSelectedP = new RichToggleButtonAreaPainter(false, true, false, true);
		RichToggleButtonAreaPainter toggleButtonPressedSelectedP = new RichToggleButtonAreaPainter(true, true, false, true);
		RichToggleButtonAreaPainter toggleButtonMouseOverSelectedP = new RichToggleButtonAreaPainter(false, true, true, true);
		RichToggleButtonAreaPainter toggleButtonDisabledSelectedP = new RichToggleButtonAreaPainter(false, false, false, true);
		
		final RichButtonPainter buttonDisabled = new RichButtonPainter(null, buttonBorderDisabledP, buttonDisabledP);
		final RichButtonPainter buttonEnabled = new RichButtonPainter(null, buttonBorderEnabledP, buttonEnabledP);
		final RichButtonPainter buttonPressed = new RichButtonPainter(null, buttonBorderEnabledPressedP, buttonPressedP);
		final RichButtonPainter buttonFocused = new RichButtonPainter(buttonFocusedP, buttonBorderEnabledP, buttonEnabledP);
		final RichButtonPainter buttonMouseOver = new RichButtonPainter(null, buttonBorderEnabledPressedP, buttonMouseOverP);
		final RichButtonPainter buttonFocusedMouseOver = new RichButtonPainter(buttonFocusedP, buttonBorderEnabledPressedP, buttonMouseOverP);
		final RichButtonPainter buttonFocusedPressed = new RichButtonPainter(buttonFocusedP, buttonBorderEnabledPressedP, buttonPressedP);
		
		final RichButtonPainter toggleButtonSelected = new RichButtonPainter(null, buttonBorderEnabledP, toggleButtonEnabledSelectedP);
		final RichButtonPainter toggleButtonSelectedMouseOver = new RichButtonPainter(null, buttonBorderEnabledPressedP, toggleButtonMouseOverSelectedP);
		final RichButtonPainter toggleButtonSelectedPressed = new RichButtonPainter(null, buttonBorderEnabledPressedP, toggleButtonPressedSelectedP);
		final RichButtonPainter toggleButtonSelectedFocused = new RichButtonPainter(buttonFocusedP, buttonBorderEnabledP, toggleButtonEnabledSelectedP);
		final RichButtonPainter toggleButtonSelectedFocusedMouseOver = new RichButtonPainter(buttonFocusedP, buttonBorderEnabledPressedP, toggleButtonMouseOverSelectedP);
		final RichButtonPainter toggleButtonSelectedFocusedPressed = new RichButtonPainter(buttonFocusedP, buttonBorderEnabledPressedP, toggleButtonPressedSelectedP);
		final RichButtonPainter toggleButtonSelectedDisabled = new RichButtonPainter(null, buttonBorderDisabledP, toggleButtonDisabledSelectedP);
		
		final RichComboBoxFocusPainter comboboxFocusedP = new RichComboBoxFocusPainter();
		final RichComboBoxBorderPainter comboboxBorderEnabledP = new RichComboBoxBorderPainter(true, false);
		final RichComboBoxBorderPainter comboboxBorderEnabledPressedP = new RichComboBoxBorderPainter(true, true);
		final RichComboBoxBorderPainter comboboxBorderDisabledP = new RichComboBoxBorderPainter(false, false);
		final RichComboBoxAreaPainter comboboxEnabledP = new RichComboBoxAreaPainter(false, true, false);
		final RichComboBoxAreaPainter comboboxPressedP = new RichComboBoxAreaPainter(true, true, false);
		final RichComboBoxAreaPainter comboboxMouseOverP = new RichComboBoxAreaPainter(false, true, true);
		final RichComboBoxAreaPainter comboboxDisabledP = new RichComboBoxAreaPainter(false, false, false);
		
		final RichComboBoxPainter comboboxDisabled = new RichComboBoxPainter(null, comboboxBorderDisabledP, comboboxDisabledP);
		final RichComboBoxPainter comboboxEnabled = new RichComboBoxPainter(null, comboboxBorderEnabledP, comboboxEnabledP);
		final RichComboBoxPainter comboboxPressed = new RichComboBoxPainter(null, comboboxBorderEnabledPressedP, comboboxPressedP);
		final RichComboBoxPainter comboboxFocused = new RichComboBoxPainter(comboboxFocusedP, comboboxBorderEnabledP, comboboxEnabledP);
		final RichComboBoxPainter comboboxMouseOver = new RichComboBoxPainter(null, comboboxBorderEnabledPressedP, comboboxMouseOverP);
		final RichComboBoxPainter comboboxFocusedMouseOver = new RichComboBoxPainter(comboboxFocusedP, comboboxBorderEnabledPressedP, comboboxMouseOverP);
		final RichComboBoxPainter comboboxFocusedPressed = new RichComboBoxPainter(comboboxFocusedP, comboboxBorderEnabledPressedP, comboboxPressedP);
		
		
		final RichTextComponentBorderPainter textPaneBorderEnabledP = new RichTextComponentBorderPainter(false, true);
		final RichTextComponentBorderPainter textPaneBorderDisabledP = new RichTextComponentBorderPainter(false, false);
		final RichTextComponentBorderPainter textPaneBorderFocusedP = new RichTextComponentBorderPainter(true, true);
		final RichTextComponentBackgroundPainter textPaneDisabledP = new RichTextComponentBackgroundPainter(false);
		final RichTextComponentBackgroundPainter textPaneEnabledP = new RichTextComponentBackgroundPainter(true);
		
		final RichTextAreaBackgroundPainter textAreaDisabledP = new RichTextAreaBackgroundPainter(false);
		final RichTextAreaBackgroundPainter textAreaEnabledP = new RichTextAreaBackgroundPainter(true);
		
		final RichToolTipBackgroundPainter tooltipPainter = new RichToolTipBackgroundPainter();
		
		final RichTableHeaderPainter tableHeaderEnabled = new RichTableHeaderPainter( false, true, false, false );
		final RichTableHeaderPainter tableHeaderDisabled = new RichTableHeaderPainter( false, false, false, false );
		final RichTableHeaderPainter tableHeaderMouseOver = new RichTableHeaderPainter( false, true, true, false );
		final RichTableHeaderPainter tableHeaderPressed = new RichTableHeaderPainter( false, true, false, true );
		final RichTableHeaderPainter tableHeaderFocused = new RichTableHeaderPainter( true, true, false, false );
		
		final RichTabPaneTabPainter tabpaneTabEnabled = new RichTabPaneTabPainter( false, true, false, false, false );
		final RichTabPaneTabPainter tabpaneTabDisabled = new RichTabPaneTabPainter( false, false, false, false, false );
		final RichTabPaneTabPainter tabpaneTabDisabledSelected = new RichTabPaneTabPainter( false, false, false, false, true );
		final RichTabPaneTabPainter tabpaneTabMouseOver = new RichTabPaneTabPainter( false, true, true, false, false );
		final RichTabPaneTabPainter tabpaneTabPressed = new RichTabPaneTabPainter( false, true, false, true, false );
		final RichTabPaneTabPainter tabpaneTabSelected = new RichTabPaneTabPainter( false, true, false, false, true );
		final RichTabPaneTabPainter tabpaneTabFocusedMouseOverSelected = new RichTabPaneTabPainter( true, true, true, false, true );
		final RichTabPaneTabPainter tabpaneTabFocusedPressedSelected = new RichTabPaneTabPainter( true, true, false, true, true );
		final RichTabPaneTabPainter tabpaneTabFocusedSelected = new RichTabPaneTabPainter( true, true, false, false, true );
		final RichTabPaneTabPainter tabpaneTabMouseOverSelected = new RichTabPaneTabPainter( false, true, true, false, true );
		final RichTabPaneTabPainter tabpaneTabPressedSelected = new RichTabPaneTabPainter( false, true, false, true, true );
		
		// Get UI defaults
		UIDefaults table = this.getDefaults( );
		
		// Set the theme font
		Enumeration<Object> keys = table.keys( );
		while ( keys.hasMoreElements( ) )
		{
			Object key = keys.nextElement( );
			Object value = UIManager.get( key );
			if( value != null && value instanceof javax.swing.plaf.FontUIResource )
				UIManager.put( key, theme.getFont( ) );
		}
		
		// Set other UI elements
		Object[] uiDefaults = 
		{
			"control", theme.getBackground( ),
			"nimbusBase", theme.getBorder( ),
			"nimbusFocus", theme.getFocus( ),
			"nimbusLightBackground", theme.getBackgroundBrighter( ),
			"nimbusSelectionBackground", theme.getFocus( ),
			"nimbusSelectedText", theme.getBorder( ),
			"text", theme.getForeground( ),
			
			"ToolBar[Enabled].handleIconPainter", new RichToolBarHandleIconPainter(),
			"ToolBar[North].borderPainter", new RichToolBarNorthBorderPainter(),
//			"ToolBar.contentMargins", new Insets(50,50,50,50),
			"ToolBar:Button.contentMargins", new Insets(7,7,7,7),
			"ToolBar:ToggleButton.contentMargins", new Insets(7,7,7,7),
			"ToolBar:Button[Focused+MouseOver].backgroundPainter", buttonBorderEnabledP,
			"ToolBar:Button[Focused+Pressed].backgroundPainter", buttonPressed,
			"ToolBar:Button[Focused].backgroundPainter", buttonBorderEnabledP,
			"ToolBar:Button[MouseOver].backgroundPainter", buttonBorderEnabledP,
			"ToolBar:Button[Pressed].backgroundPainter", buttonPressed,
			"ToolBar:ToggleButton[Disabled+Selected].backgroundPainter", toggleButtonSelectedDisabled,
			"ToolBar:ToggleButton[Focused+MouseOver+Selected].backgroundPainter", toggleButtonSelected,
			"ToolBar:ToggleButton[Focused+MouseOver].backgroundPainter", buttonBorderEnabledP,
			"ToolBar:ToggleButton[Focused+Pressed+Selected].backgroundPainter", toggleButtonSelectedPressed,
			"ToolBar:ToggleButton[Focused+Pressed].backgroundPainter", buttonPressed,
			"ToolBar:ToggleButton[Focused+Selected].backgroundPainter", toggleButtonSelected,
			"ToolBar:ToggleButton[Focused].backgroundPainter", buttonBorderEnabledP,
			"ToolBar:ToggleButton[MouseOver+Selected].backgroundPainter", toggleButtonSelected,
			"ToolBar:ToggleButton[MouseOver].backgroundPainter", buttonBorderEnabledP,
			"ToolBar:ToggleButton[Pressed+Selected].backgroundPainter", toggleButtonSelectedPressed,
			"ToolBar:ToggleButton[Pressed].backgroundPainter", buttonPressed,
			"ToolBar:ToggleButton[Selected].backgroundPainter", toggleButtonSelected,
			
			
			"ToolTip[Enabled].backgroundPainter", tooltipPainter,
			"ToolTip.foreground", theme.getBackgroundBrighter( ),
			
			"TabbedPane:TabbedPaneTabArea[Disabled].backgroundPainter", new RichTabPaneTabAreaPainter( false, false, false ),
			"TabbedPane:TabbedPaneTabArea[Enabled+MouseOver].backgroundPainter", new RichTabPaneTabAreaPainter( true, true, false ),
			"TabbedPane:TabbedPaneTabArea[Enabled+Pressed].backgroundPainter", new RichTabPaneTabAreaPainter( true, false, true ),
			"TabbedPane:TabbedPaneTabArea[Enabled].backgroundPainter", new RichTabPaneTabAreaPainter( true, false, false ),
			"TabbedPane:TabbedPaneTab[Disabled+Selected].backgroundPainter", tabpaneTabDisabledSelected,
			"TabbedPane:TabbedPaneTab[Disabled].backgroundPainter", tabpaneTabDisabled,
			"TabbedPane:TabbedPaneTab[Enabled+MouseOver].backgroundPainter", tabpaneTabMouseOver,
			"TabbedPane:TabbedPaneTab[Enabled+Pressed].backgroundPainter", tabpaneTabPressed,
			"TabbedPane:TabbedPaneTab[Enabled].backgroundPainter", tabpaneTabEnabled,
			"TabbedPane:TabbedPaneTab[Focused+MouseOver+Selected].backgroundPainter", tabpaneTabFocusedMouseOverSelected,
			"TabbedPane:TabbedPaneTab[Focused+Pressed+Selected].backgroundPainter", tabpaneTabFocusedPressedSelected,
			"TabbedPane:TabbedPaneTab[Focused+Selected].backgroundPainter", tabpaneTabFocusedSelected,
			"TabbedPane:TabbedPaneTab[MouseOver+Selected].backgroundPainter", tabpaneTabMouseOverSelected,
			"TabbedPane:TabbedPaneTab[Pressed+Selected].backgroundPainter", tabpaneTabPressedSelected,
			"TabbedPane:TabbedPaneTab[Selected].backgroundPainter", tabpaneTabSelected,
			
			"SplitPane:SplitPaneDivider[Enabled].backgroundPainter", new RichSplitPaneDividerPainter(false),
			"SplitPane:SplitPaneDivider[Focused].backgroundPainter", new RichSplitPaneDividerPainter(true),
			"SplitPane:SplitPaneDivider[Enabled+Vertical].foregroundPainter", new RichSplitPaneDividerForegroundPainter(true),
			"SplitPane:SplitPaneDivider[Enabled].foregroundPainter", new RichSplitPaneDividerForegroundPainter(false),
			
//			"Slider:SliderTrack[Disabled].backgroundPainter", new RichSliderTrackPainter(false),
//			"Slider:SliderTrack[Enabled].backgroundPainter", new RichSliderTrackPainter(true),
			
			"TableHeader:\"TableHeader.renderer\"[Disabled+Sorted].backgroundPainter", tableHeaderDisabled,
			"TableHeader:\"TableHeader.renderer\"[Disabled].backgroundPainter", tableHeaderDisabled,
			"TableHeader:\"TableHeader.renderer\"[Enabled+Focused+Sorted].backgroundPainter", tableHeaderFocused,
			"TableHeader:\"TableHeader.renderer\"[Enabled+Focused].backgroundPainter", tableHeaderFocused,
			"TableHeader:\"TableHeader.renderer\"[Enabled+Sorted].backgroundPainter", tableHeaderEnabled,
			"TableHeader:\"TableHeader.renderer\"[Enabled].backgroundPainter", tableHeaderEnabled,
			"TableHeader:\"TableHeader.renderer\"[MouseOver].backgroundPainter", tableHeaderMouseOver,
			"TableHeader:\"TableHeader.renderer\"[Pressed].backgroundPainter", tableHeaderPressed,
			"TableHeader.disabledText", theme.getBackgroundBrighter( ),
			"TableHeader.disabled", theme.getBackgroundBrighter( ),
			
			"Table.showGrid", false,
			
			"Spinner:Panel:\"Spinner.formattedTextField\"[Disabled].backgroundPainter", new RichComboBoxTextFieldPainter(false),
			"Spinner:Panel:\"Spinner.formattedTextField\"[Enabled].backgroundPainter", new RichComboBoxTextFieldPainter(true),
			"Spinner:Panel:\"Spinner.formattedTextField\"[Focused+Selected].backgroundPainter", new RichSpinnerFormattedTextFieldPainter( true, true ),
			"Spinner:Panel:\"Spinner.formattedTextField\"[Focused].backgroundPainter", new RichSpinnerFormattedTextFieldPainter( true, true ),
			"Spinner:Panel:\"Spinner.formattedTextField\"[Selected].backgroundPainter", new RichComboBoxTextFieldPainter(true),
			
			"ComboBox:\"ComboBox.arrowButton\"[Disabled+Editable].backgroundPainter", new RichComboBoxArrowButtonPainter( true, false, false, false, false ),
			"ComboBox:\"ComboBox.arrowButton\"[Editable+Enabled].backgroundPainter", new RichComboBoxArrowButtonPainter( true, true, false, false, false ),
			"ComboBox:\"ComboBox.arrowButton\"[Editable+MouseOver].backgroundPainter", new RichComboBoxArrowButtonPainter( true, true, false, true, false ),
			"ComboBox:\"ComboBox.arrowButton\"[Editable+Pressed].backgroundPainter", new RichComboBoxArrowButtonPainter( true, true, false, false, true ),
			"ComboBox:\"ComboBox.arrowButton\"[Editable+Selected].backgroundPainter", new RichComboBoxArrowButtonPainter( true, true, false, false, true ),
			"ComboBox:\"ComboBox.textField\"[Disabled].backgroundPainter", new RichComboBoxTextFieldPainter(false),
			"ComboBox:\"ComboBox.textField\"[Enabled].backgroundPainter", new RichComboBoxTextFieldPainter(true),
			"ComboBox:\"ComboBox.textField\"[Selected].backgroundPainter", new RichComboBoxTextFieldPainter(true),
//			"ComboBox[Disabled+Editable].backgroundPainter", comboboxDisabled,
			"ComboBox[Disabled+Pressed].backgroundPainter", comboboxDisabled,
			"ComboBox[Disabled].backgroundPainter", comboboxDisabled,
//			"ComboBox[Editable+Enabled].backgroundPainter", ,
			"ComboBox[Editable+Focused].backgroundPainter", comboboxFocused,
//			"ComboBox[Editable+MouseOver].backgroundPainter", ,
//			"ComboBox[Editable+Pressed].backgroundPainter", ,
			"ComboBox[Enabled+Selected].backgroundPainter", comboboxEnabled,
			"ComboBox[Enabled].backgroundPainter", comboboxEnabled,
			"ComboBox[Focused+MouseOver].backgroundPainter", comboboxFocusedMouseOver,
			"ComboBox[Focused+Pressed].backgroundPainter", comboboxFocusedPressed,
			"ComboBox[Focused].backgroundPainter", comboboxFocused,
			"ComboBox[MouseOver].backgroundPainter", comboboxMouseOver,
			"ComboBox[Pressed].backgroundPainter", comboboxPressed,
			"ComboBox.padding", new Insets (3,6,3,6),
			"ComboBox:\"ComboBox.textField\".contentMargins", new Insets (3,8,3,6),
			"ComboBox.contentMargins", new Insets (1,0,1,0),	
			
			"Button.foreground", theme.getForeground( ), 
			"Button[Default+Focused+MouseOver].backgroundPainter", buttonFocusedMouseOver, 
			"Button[Default+Focused+Pressed].backgroundPainter", buttonFocusedPressed,
			"Button[Default+Focused].backgroundPainter", buttonFocused,
			"Button[Default+MouseOver].backgroundPainter", buttonMouseOver,
			"Button[Default+Pressed].backgroundPainter", buttonPressed,
			"Button[Default+Pressed].textForeground", Color.WHITE,
			"Button[Default].backgroundPainter", buttonEnabled,
			"Button[Disabled].backgroundPainter", buttonDisabled,
			"Button[Enabled].backgroundPainter", buttonEnabled,
			"Button[Focused+MouseOver].backgroundPainter", buttonFocusedMouseOver,
			"Button[Focused+Pressed].backgroundPainter", buttonFocusedPressed,
			"Button[Focused].backgroundPainter", buttonFocused,
			"Button[MouseOver].backgroundPainter", buttonMouseOver,
			"Button[Pressed].backgroundPainter", buttonPressed,
			
			"ToggleButton[Disabled+Selected].backgroundPainter", toggleButtonSelectedDisabled,
			"ToggleButton[Disabled].backgroundPainter", buttonDisabled,
			"ToggleButton[Enabled].backgroundPainter", buttonEnabled,
			"ToggleButton[Focused+MouseOver+Selected].backgroundPainter", toggleButtonSelectedFocusedMouseOver,
			"ToggleButton[Focused+MouseOver].backgroundPainter", buttonFocusedMouseOver,
			"ToggleButton[Focused+Pressed+Selected].backgroundPainter", toggleButtonSelectedFocusedPressed,
			"ToggleButton[Focused+Pressed].backgroundPainter", buttonFocusedPressed,
			"ToggleButton[Focused+Selected].backgroundPainter", toggleButtonSelectedFocused,
			"ToggleButton[Focused].backgroundPainter", buttonFocused,
			"ToggleButton[MouseOver+Selected].backgroundPainter", toggleButtonSelectedMouseOver,
			"ToggleButton[MouseOver].backgroundPainter", buttonMouseOver,
			"ToggleButton[Pressed+Selected].backgroundPainter", toggleButtonSelectedPressed,
			"ToggleButton[Pressed].backgroundPainter", buttonPressed,
			"ToggleButton[Selected].backgroundPainter", toggleButtonSelected,
			
//			"TextPane[Disabled].backgroundPainter", textPaneDisabledP,
						
			"TextArea[Disabled+NotInScrollPane].backgroundPainter", textPaneDisabledP,
			"TextArea[Disabled+NotInScrollPane].borderPainter", textPaneBorderDisabledP, 
			"TextArea[Disabled].backgroundPainter", textAreaDisabledP,
			"TextArea[Enabled+NotInScrollPane].backgroundPainter", textPaneEnabledP,
			"TextArea[Enabled+NotInScrollPane].borderPainter", textPaneBorderEnabledP,
			"TextArea[Enabled].backgroundPainter", textAreaEnabledP,
			"TextArea[Focused+NotInScrollPane].borderPainter", textPaneBorderFocusedP,
			"TextArea[Selected].backgroundPainter", textAreaEnabledP,
			
			"PasswordField[Disabled].backgroundPainter", textPaneDisabledP,
			"PasswordField[Disabled].borderPainter", textPaneBorderDisabledP,
			"PasswordField[Enabled].backgroundPainter", textPaneEnabledP, 
			"PasswordField[Enabled].borderPainter", textPaneBorderEnabledP,
			"PasswordField[Focused].borderPainter", textPaneBorderFocusedP,
//			
			"TextField[Disabled].backgroundPainter", textPaneDisabledP,
			"TextField[Enabled].backgroundPainter", textPaneEnabledP,
			"TextField[Disabled].borderPainter", textPaneBorderDisabledP,
			"TextField[Enabled].borderPainter", textPaneBorderEnabledP,
			"TextField[Focused].borderPainter", textPaneBorderFocusedP,
			
			"FormattedTextField[Disabled].backgroundPainter", textPaneDisabledP,
			"FormattedTextField[Disabled].borderPainter", textPaneBorderDisabledP, 
			"FormattedTextField[Enabled].backgroundPainter", textPaneEnabledP, 
			"FormattedTextField[Enabled].borderPainter", textPaneBorderEnabledP, 
			"FormattedTextField[Focused].borderPainter", textPaneBorderFocusedP, 
			
			"ProgressBar[Enabled+Indeterminate].foregroundPainter", new RichProgressBarIndeterminatePainter( true ),
			"ProgressBar[Disabled+Indeterminate].foregroundPainter", new RichProgressBarIndeterminatePainter( false ),
			"ProgressBar[Enabled+Indeterminate].progressPadding", 0,
			"ProgressBar[Disabled+Indeterminate].progressPadding", 0,
			"ProgressBar[Disabled+Finished].foregroundPainter", new RichProgressBarDeterminatePainter(false),
			"ProgressBar[Disabled].foregroundPainter", new RichProgressBarDeterminatePainter(false),
			"ProgressBar[Enabled+Finished].foregroundPainter", new RichProgressBarDeterminatePainter(true),
			"ProgressBar[Enabled].foregroundPainter", new RichProgressBarDeterminatePainter(true),
			"ProgressBar[Enabled].backgroundPainter", new RichProgressBarBackgroundPainter(true),
			"ProgressBar[Disabled].backgroundPainter", new RichProgressBarBackgroundPainter(false),
			"ProgressBar.tileWhenIndeterminate", true,
//			"ProgressBar[Disabled+Indeterminate].progressPadding", 10,
			"ProgressBar.tileWidth", 25,
			"ProgressBar.cycleTime", 750,
//			"ProgressBar.contentMargins", new Insets(2,3,2,3),
			// "ProgressBar.selectionForeground", DAWN_BASE,
			// "ProgressBar.disabledText", DAWN_BASE,
			// FIXME Not working: "ProgressBar.foreground", DAWN_BASE,
			
			
			"ScrollBar:\"ScrollBar.button\"[Disabled].foregroundPainter", new RichScrollBarButtonPainter(false),
			"ScrollBar:\"ScrollBar.button\"[Enabled].foregroundPainter", new RichScrollBarButtonPainter(false),
			"ScrollBar:\"ScrollBar.button\"[MouseOver].foregroundPainter", new RichScrollBarButtonPainter(true),
			"ScrollBar:\"ScrollBar.button\"[Pressed].foregroundPainter", new RichScrollBarButtonPainter(true),
			"ScrollBar:ScrollBarThumb[Enabled].backgroundPainter", new RichScrollBarThumbPainter(false, false),
			"ScrollBar:ScrollBarThumb[MouseOver].backgroundPainter", new RichScrollBarThumbPainter(true, false),
			"ScrollBar:ScrollBarThumb[Pressed].backgroundPainter", new RichScrollBarThumbPainter(false, true),
			"ScrollBar:ScrollBarTrack[Enabled].backgroundPainter", new RichScrollBarTrackPainter(),
			"ScrollBar:ScrollBarTrack[Disabled].backgroundPainter", new RichScrollBarTrackPainter(),
			
			"List.background", theme.getBackground( ),
			"List.disabled", theme.getBackgroundBrighter( ),
			"List[Selected].textBackground", theme.getBackgroundDarker( ),
			"List[Selected].textForeground", theme.getFocus( ), // Maybe not...
			
			
			"\"Table.editor\"[Disabled].textForeground", theme.getBackgroundBrighter( ),

			"MenuBar:Menu[Selected].textForeground", theme.getBorder( ),
			"MenuBar:Menu[Selected].backgroundPainter", new RichMenuSelectedBackgroundPainter(),
			
			"Menu[Enabled+Selected].textForeground", theme.getBorder( ),
			"Menu[Enabled+Selected].backgroundPainter", new RichMenuSelectedBackgroundPainter(),
			"Menu[Enabled+Selected].arrowIconPainter", table.get( "Menu[Enabled].arrowIconPainter" ),
			"Menu:MenuItemAccelerator[MouseOver].textForeground", theme.getBorder( ),
			
			"MenuItem[MouseOver].textForeground", theme.getBorder( ),
			"MenuItem[MouseOver].backgroundPainter", new RichMenuSelectedBackgroundPainter(),
			"MenuItem:MenuItemAccelerator[MouseOver].textForeground", theme.getBorder( ),
			
			"RadioButtonMenuItem[MouseOver].textForeground", theme.getBorder( ),
			"RadioButtonMenuItem[MouseOver].backgroundPainter", new RichMenuSelectedBackgroundPainter(),
			"RadioButtonMenuItem[MouseOver+Selected].textForeground", theme.getBorder( ),
			"RadioButtonMenuItem[MouseOver+Selected].backgroundPainter", new RichMenuSelectedBackgroundPainter(),
			"RadioButtonMenuItem[MouseOver+Selected].checkIconPainter", table.get( "RadioButtonMenuItem[Enabled+Selected].checkIconPainter" ),
			"RadioButtonMenuItem:MenuItemAccelerator[MouseOver].textForeground", theme.getBorder( ),
			
			"CheckBoxMenuItem[MouseOver+Selected].checkIconPainter", table.get( "CheckBoxMenuItem[Enabled+Selected].checkIconPainter" ),
			"CheckBoxMenuItem:MenuItemAccelerator[MouseOver].textForeground", theme.getBorder( ),
			"CheckBoxMenuItem[MouseOver+Selected].textForeground", theme.getBorder( ),
			"CheckBoxMenuItem[MouseOver+Selected].backgroundPainter", new RichMenuSelectedBackgroundPainter(),
			"CheckBoxMenuItem[MouseOver].textForeground", theme.getBorder( ),
			"CheckBoxMenuItem[MouseOver].backgroundPainter", new RichMenuSelectedBackgroundPainter(),
			
			"PopupMenu.opaque", false,
			"PopupMenu[Enabled].backgroundPainter", new RichPopupMenuPainter(),
			"PopupMenu[Disabled].backgroundPainter", new RichPopupMenuPainter(),	
			
		};
		table.putDefaults( uiDefaults );
	}
	
	/**
	 * Returns the current theme of this Look and Feel. It returns always the
	 * currently applied theme, cause this is static.
	 * 
	 * @return
	 */
	public static RichTheme getCurrentTheme( )
	{
		return theme;
	}

	/**
	 * Sets the current theme of the Look and Feel. While this is a static
	 * method the theme change is applied to all LAF instances.
	 * 
	 * @throw an IllegalArgumentException while the theme implementation has a
	 *        method returning a null value.
	 * @param theme
	 * @see RichTheme
	 */
	public static void setCurrentTheme( RichTheme theme )
	{
		if( theme == null )
			throw new IllegalArgumentException("Null Argument. Make a reference to a non-null theme.");
		
		if( !(theme.verifyTheme( )) )
			throw new IllegalArgumentException("Unverified theme. Make sure every specified method of the RichTheme returns a non-null value.");
		
		RichLookAndFeel.theme = theme;
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
		return "Rich LookAndFeel, RLAF";
	}
	
	@Override
	public String getID()
	{
		return "Rich";
	}
	
	@Override
	public String getName()
	{
		return "Rich";
	}
	
	@Override
	public boolean isNativeLookAndFeel( )
	{
		return false;
	}

	@Override
	public boolean isSupportedLookAndFeel( )
	{
		return true;
	}

}
