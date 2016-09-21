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

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.drost.application.Application;
import org.drost.application.plaf.rich.RichLookAndFeel;
import org.drost.application.ui.GUI;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JSpinner;
import java.awt.Dimension;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JProgressBar;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 * @author kimschorat
 *
 */
public class ExampleGUIApplication extends JFrame
{
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main( String[] args )
	{
		
		EventQueue.invokeLater( new Runnable( )
		{
			public void run( )
			{
				try
				{
					ExampleGUIApplication frame = new ExampleGUIApplication( );
					frame.setVisible( true );
				}
				catch ( Exception e )
				{
					e.printStackTrace( );
				}
			}
		} );
	}

	/**
	 * Create the frame.
	 * @throws UnsupportedLookAndFeelException 
	 */
	public ExampleGUIApplication( ) throws UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel( new RichLookAndFeel() );
		
//		Application.launch( "ExampleApplication" );
//		GUI gui = Application.get( ).getGUI( );
//		gui.setConsistentLookAndFeel( );
//		
		
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setBounds( 100, 100, 883, 626 );
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenu mnWindow = new JMenu("Window");
		menuBar.add(mnWindow);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		contentPane = new JPanel( );
		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		contentPane.setLayout( new BorderLayout( 0, 0 ) );
		setContentPane( contentPane );
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JProgressBar progressBar = new JProgressBar();
		panel_1.add(progressBar);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_1);
		
		JLabel lblSegmentationTool = new JLabel("Segmentation Tool");
		panel_1.add(lblSegmentationTool);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut);
		
		JSlider slider_1 = new JSlider();
		panel_1.add(slider_1);
		
		JLabel lblFps = new JLabel("56/60 fps");
		panel_1.add(lblFps);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setOneTouchExpandable(true);
		splitPane.setRightComponent(splitPane_1);
		
		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"RoundRect", "RoundRect2", "Oval", "EllipseShadow", "Rectangle", "Line1", "Line2", "Line3", "Polygon"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		splitPane_1.setLeftComponent(list);
		splitPane_1.setDividerLocation(200);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane.setLeftComponent(splitPane_2);
		
		JPanel panel = new JPanel();
		splitPane_2.setLeftComponent(panel);
		panel.setLayout(new GridLayout(10, 1, 0, 0));
		
		JLabel lblTransformation = new JLabel("Transformation");
		panel.add(lblTransformation);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblX = new JLabel("X");
		panel_2.add(lblX);
		
		JSpinner spinner = new JSpinner();
		spinner.setPreferredSize(new Dimension(55, 26));
		panel_2.add(spinner);
		
		JLabel lblY = new JLabel("Y");
		panel_2.add(lblY);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setPreferredSize(new Dimension(55, 26));
		panel_2.add(spinner_1);
		
		JLabel lblZ = new JLabel("Z");
		panel_2.add(lblZ);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setPreferredSize(new Dimension(55, 26));
		panel_2.add(spinner_2);
		
		JLabel lblColor_1 = new JLabel("Color");
		lblColor_1.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panel.add(lblColor_1);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JLabel label = new JLabel("Color");
		label.setOpaque(true);
		label.setForeground(Color.CYAN);
		label.setBackground(Color.CYAN);
		panel_3.add(label);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"None"}));
		panel_3.add(comboBox_3);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setModel(new DefaultComboBoxModel(new String[] {"Primary"}));
		panel_3.add(comboBox_4);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		
		JLabel label_1 = new JLabel("0%");
		panel_4.add(label_1);
		
		JSlider slider = new JSlider();
		slider.setPreferredSize(new Dimension(150, 29));
		panel_4.add(slider);
		
		JLabel label_2 = new JLabel("100%");
		panel_4.add(label_2);
		
		JLabel lblCursorSettings = new JLabel("Cursor Settings");
		lblCursorSettings.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panel.add(lblCursorSettings);
		
		JPanel panel_8 = new JPanel();
		panel.add(panel_8);
		
		JLabel lblDefault = new JLabel("default:");
		panel_8.add(lblDefault);
		
		JButton btnAuto = new JButton("Auto");
		panel_8.add(btnAuto);
		
		JCheckBox chckbxVisible = new JCheckBox("Visible");
		panel_8.add(chckbxVisible);
		
		JLabel lblGeometricShape = new JLabel("Geometric Shape");
		lblGeometricShape.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panel.add(lblGeometricShape);
		
		JPanel panel_9 = new JPanel();
		panel.add(panel_9);
		
		JCheckBox chckbxRenderShape = new JCheckBox("Show");
		panel_9.add(chckbxRenderShape);
		
		JButton btnDelete = new JButton("Delete");
		panel_9.add(btnDelete);
		
		JButton btnDuplicate = new JButton("Copy");
		panel_9.add(btnDuplicate);
		
		JCheckBox chckbxRenderMaterial = new JCheckBox("Render Material");
		panel.add(chckbxRenderMaterial);
		
		JPanel panel_5 = new JPanel();
		splitPane_2.setRightComponent(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6, BorderLayout.SOUTH);
		
		JButton button = new JButton("100%");
		panel_6.add(button);
		
		JLabel lblAvailable = new JLabel("Available");
		panel_6.add(lblAvailable);
		
		JCheckBox chckbxIncludeAssets = new JCheckBox("Include Assets");
		panel_6.add(chckbxIncludeAssets);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel_5.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_7 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_7, null);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_7.add(scrollPane, BorderLayout.CENTER);
		
		JTextArea txtrThis = new JTextArea();
		txtrThis.setText("/*\n * This file is part of the application library that simplifies common\n * initialization and helps setting up any java program.\n * \n * Copyright (C) 2016 Yannick Drost, all rights reserved.\n * \n * This program is free software: you can redistribute it and/or modify it under\n * the terms of the GNU General Public License as published by the Free Software\n * Foundation, either version 3 of the License, or (at your option) any later\n * version.\n * \n * This program is distributed in the hope that it will be useful, but WITHOUT\n * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS\n * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more\n * details.\n * \n * You should have received a copy of the GNU General Public License along with\n * this program. If not, see <http://www.gnu.org/licenses/>.\n */");
		scrollPane.setViewportView(txtrThis);
		
		JCheckBox chckbxCollapseBracketGroups = new JCheckBox("Collapse Bracket Groups");
		scrollPane.setColumnHeaderView(chckbxCollapseBracketGroups);
		
		JPanel panel_10 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_10, null);
		panel_10.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_11 = new JPanel();
		panel_10.add(panel_11, BorderLayout.NORTH);
		
		JLabel lblConsole = new JLabel("Console");
		panel_11.add(lblConsole);
		
		JButton btnInfo = new JButton("Info");
		panel_11.add(btnInfo);
		
		JButton btnWarnings = new JButton("Warnings");
		panel_11.add(btnWarnings);
		
		JButton btnErrors = new JButton("Errors");
		panel_11.add(btnErrors);
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setModel(new DefaultComboBoxModel(new String[] {"List View"}));
		panel_10.add(comboBox_5, BorderLayout.SOUTH);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setCorner(JScrollPane.LOWER_RIGHT_CORNER, new JToggleButton());
		panel_10.add(scrollPane_1, BorderLayout.CENTER);
		
		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setModel(new DefaultComboBoxModel(new String[] {"Syntax Highlighting", "Plain", "Nothing"}));
		scrollPane_1.setColumnHeaderView(comboBox_6);
		
		JTextArea txtrPublicClassExampleguiapplication = new JTextArea();
		txtrPublicClassExampleguiapplication.setText("public class ExampleGUIApplication extends JFrame\n{\n\tprivate JPanel contentPane;\n\n\t/**\n\t * Launch the application.\n\t */\n\tpublic static void main( String[] args )\n\t{\n\t\t\n\t\tEventQueue.invokeLater( new Runnable( )\n\t\t{\n\t\t\tpublic void run( )\n\t\t\t{\n\t\t\t\ttry\n\t\t\t\t{\n\t\t\t\t\tExampleGUIApplication frame = new ExampleGUIApplication( );\n\t\t\t\t\tframe.setVisible( true );\n\t\t\t\t}\n\t\t\t\tcatch ( Exception e )\n\t\t\t\t{\n\t\t\t\t\te.printStackTrace( );\n\t\t\t\t}\n\t\t\t}\n\t\t} );\n\t}\n}");
		scrollPane_1.setViewportView(txtrPublicClassExampleguiapplication);
		splitPane_2.setDividerLocation(250);
		splitPane.setDividerLocation(700);
		
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JLabel lblT = new JLabel("T");
		toolBar.add(lblT);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Sans Serif", "Arial"}));
		comboBox.setEditable(true);
		toolBar.add(comboBox);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		toolBar.add(verticalStrut);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Regular"}));
		comboBox_1.setEditable(true);
		toolBar.add(comboBox_1);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		toolBar.add(verticalStrut_1);
		
		JLabel lblAa = new JLabel("aA");
		toolBar.add(lblAa);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"12p", "14p", "18p", "24p", "32p"}));
		comboBox_2.setEditable(true);
		toolBar.add(comboBox_2);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator);
		
		JToggleButton tglbtnL = new JToggleButton("L");
		toolBar.add(tglbtnL);
		
		JToggleButton tglbtnM = new JToggleButton("M");
		toolBar.add(tglbtnM);
		
		JToggleButton tglbtnR = new JToggleButton("R");
		toolBar.add(tglbtnR);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator_1);
		
		JLabel lblColor = new JLabel("Color");
		lblColor.setForeground(Color.CYAN);
		lblColor.setBackground(Color.CYAN);
		lblColor.setOpaque(true);
		toolBar.add(lblColor);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator_2);
		
		JButton btnNewButton = new JButton("Apply");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		toolBar.add(btnNewButton);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator_3);
	}

}
