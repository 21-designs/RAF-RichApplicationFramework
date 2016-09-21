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
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

/**
 * @author kimschorat
 *
 */
public class ExtensiveComponentViewer
{
	JFrame frame;
	private JPanel contentPane;
	private JLabel lblEnabled;
	private JLabel lblDisabled;
	private JButton btnDefault;
	private JButton btnDefault_1;
	private JButton btnFocused;
	private JButton btnFocused_1;
	private JButton btnBorderless;
	private JButton btnBorderless_1;
	private JButton btnBla;
	private JButton btnNewButton;
	private JButton btnBackground;
	private JButton btnBackground_1;
	private JButton btnPressed;
	private JButton btnPressed_1;
	private JButton btnMouseover;
	private JButton btnMouseover_1;
	private JButton btnNoContentarea;
	private JButton btnNoContentarea_1;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JLabel lblEnabled_1;
	private JLabel lblDisabled_1;
	private JToggleButton tglbtnDefault;
	private JToggleButton tglbtnDefault_1;
	private JToggleButton tglbtnPressed;
	private JToggleButton tglbtnPressed_1;
	private JToggleButton tglbtnMouseover;
	private JToggleButton tglbtnMouseover_1;
	private JToggleButton tglbtnSelected;
	private JToggleButton tglbtnSelected_1;
	private JToggleButton tglbtnBorderless;
	private JToggleButton tglbtnBorderless_1;
	private JToggleButton tglbtnFocused;
	private JToggleButton tglbtnFocused_1;
	private JToggleButton tglbtnBorder;
	private JToggleButton tglbtnBorder_1;
	private JToggleButton tglbtnNoContentarea;
	private JToggleButton tglbtnNoContentarea_1;
	private JToggleButton tglbtnBackground;
	private JToggleButton tglbtnBackground_1;
	private JToggleButton tglbtnSelectedmouseover;
	private JToggleButton tglbtnSelectedmouseover_1;
	private JPanel panel_4;
	private JLabel lblEnabled_2;
	private JLabel lblDisabled_2;
	private JRadioButton rdbtnDefault;
	private JRadioButton rdbtnDefault_1;
	private JRadioButton rdbtnPressed;
	private JRadioButton rdbtnPressed_1;
	private JRadioButton rdbtnMouseover;
	private JRadioButton rdbtnMouseover_1;
	private JRadioButton rdbtnSelected;
	private JRadioButton rdbtnSelected_1;
	private JRadioButton rdbtnSelectedmouseover;
	private JRadioButton rdbtnSelectedmouseover_1;
	private JRadioButton rdbtnBorder;
	private JRadioButton rdbtnBorder_1;
	private JRadioButton rdbtnBackground;
	private JRadioButton rdbtnBackground_1;
	private JPanel panel_5;
	private JPanel panel_6;
	private JCheckBox chckbxDefault;
	private JCheckBox chckbxDefault_1;
	private JCheckBox chckbxPressed;
	private JCheckBox chckbxPressed_1;
	private JCheckBox chckbxMouseover;
	private JCheckBox chckbxMouseover_1;
	private JCheckBox chckbxSelected;
	private JCheckBox chckbxSelected_1;
	private JCheckBox chckbxSelectedmouseover;
	private JCheckBox chckbxSelectedmouseover_1;

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
					UIManager.setLookAndFeel( new RichLookAndFeel() );
//					UIManager.setLookAndFeel( new NimbusLookAndFeel() );
					ExtensiveComponentViewer viewer = new ExtensiveComponentViewer( );
					viewer.frame.setVisible( true );
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
	 */
	public ExtensiveComponentViewer( )
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setBounds( 100, 100, 450, 380 );
		contentPane = new JPanel( );
		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		contentPane.setLayout( new BorderLayout( 0, 0 ) );
		frame.setContentPane( contentPane );
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		panel_2 = new JPanel();
		tabbedPane.addTab("ToggleButtons", null, new JScrollPane( panel_2 ), null);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblEnabled_1 = new JLabel("Enabled");
		panel_3.add(lblEnabled_1);
		
		lblDisabled_1 = new JLabel("Disabled");
		panel_3.add(lblDisabled_1);
		
		tglbtnDefault = new JToggleButton("Default");
		panel_3.add(tglbtnDefault);
		
		tglbtnDefault_1 = new JToggleButton("Default");
		tglbtnDefault_1.setEnabled(false);
		panel_3.add(tglbtnDefault_1);
		
		tglbtnPressed = new JToggleButton("Pressed")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setPressed( true );
				this.getModel( ).setArmed( true );
				super.paintComponent( g );
			}
		};
		panel_3.add(tglbtnPressed);
		
		tglbtnPressed_1 = new JToggleButton("Pressed")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setPressed( true );
				super.paintComponent( g );
			}
		};
		tglbtnPressed_1.setEnabled(false);
		panel_3.add(tglbtnPressed_1);
		
		tglbtnMouseover = new JToggleButton("MouseOver")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setRollover( true );
				super.paintComponent( g );
			}
		};
		panel_3.add(tglbtnMouseover);
		
		tglbtnMouseover_1 = new JToggleButton("MouseOver")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setRollover( true );
				super.paintComponent( g );
			}
		};
		tglbtnMouseover_1.setEnabled(false);
		panel_3.add(tglbtnMouseover_1);
		
		tglbtnSelected = new JToggleButton("Selected")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setSelected( true );
				super.paintComponent( g );
			}
		};
		panel_3.add(tglbtnSelected);
		
		tglbtnSelected_1 = new JToggleButton("Selected")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setSelected( true );
				super.paintComponent( g );
			}
		};
		tglbtnSelected_1.setEnabled(false);
		panel_3.add(tglbtnSelected_1);
		
		tglbtnSelectedmouseover = new JToggleButton("Selected+MouseOver")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setSelected( true );
				this.getModel( ).setRollover( true );
				super.paintComponent( g );
			}
		};
		panel_3.add(tglbtnSelectedmouseover);
		
		tglbtnSelectedmouseover_1 = new JToggleButton("Selected+MouseOver")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setSelected( true );
				this.getModel( ).setRollover( true );
				super.paintComponent( g );
			}
		};
		tglbtnSelectedmouseover_1.setEnabled( false );
		panel_3.add(tglbtnSelectedmouseover_1);
		
		tglbtnFocused = new JToggleButton("Focused");
		panel_3.add(tglbtnFocused);
		
		tglbtnFocused_1 = new JToggleButton("Focused");
		tglbtnFocused_1.setEnabled(false);
		panel_3.add(tglbtnFocused_1);
		
		tglbtnBorderless = new JToggleButton("Borderless");
		tglbtnBorderless.setBorderPainted( false );
		panel_3.add(tglbtnBorderless);
		
		tglbtnBorderless_1 = new JToggleButton("Borderless");
		tglbtnBorderless_1.setEnabled(false);
		tglbtnBorderless_1.setBorderPainted( false );
		panel_3.add(tglbtnBorderless_1);
		
		tglbtnBorder = new JToggleButton("Border");
		tglbtnBorder.setBorder(new LineBorder(Color.RED));
		panel_3.add(tglbtnBorder);
		
		tglbtnBorder_1 = new JToggleButton("Border");
		tglbtnBorder_1.setBorder(new LineBorder(Color.RED));
		tglbtnBorder_1.setEnabled(false);
		panel_3.add(tglbtnBorder_1);
		
		tglbtnBackground = new JToggleButton("Background");
		tglbtnBackground.setBackground(Color.RED);
		panel_3.add(tglbtnBackground);
		
		tglbtnBackground_1 = new JToggleButton("Background");
		tglbtnBackground_1.setBackground(Color.RED);
		panel_3.add(tglbtnBackground_1);
		
		tglbtnNoContentarea = new JToggleButton("No ContentArea");
		tglbtnNoContentarea.setContentAreaFilled( false );
		panel_3.add(tglbtnNoContentarea);
		
		tglbtnNoContentarea_1 = new JToggleButton("No ContentArea");
		tglbtnNoContentarea_1.setEnabled(false);
		tglbtnNoContentarea_1.setContentAreaFilled( false );
		panel_3.add(tglbtnNoContentarea_1);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel container = new JPanel( new BorderLayout() );
		container.add( panel, BorderLayout.NORTH );
		tabbedPane.addTab("Buttons", null, new JScrollPane( container ), null);
		
		
		lblEnabled = new JLabel("Enabled");
		panel.add(lblEnabled);
		
		lblDisabled = new JLabel("Disabled");
		panel.add(lblDisabled);
		
		btnDefault = new JButton("Default");
		System.out.println( btnDefault.getBorder( ) );
		panel.add(btnDefault);
		
		btnDefault_1 = new JButton("Default");
		btnDefault_1.setEnabled(false);
		panel.add(btnDefault_1);
		
		btnFocused = new JButton("Focused");
		btnFocused.setFocusPainted( true );
		btnFocused.grabFocus( );
		btnFocused.requestFocusInWindow( );
		
		btnPressed = new JButton("Pressed")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setPressed( true );
				this.getModel( ).setArmed( true );
				super.paintComponent( g );
			}
		};
		panel.add(btnPressed);
		
		btnPressed_1 = new JButton("Pressed")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setPressed( true );
				super.paintComponent( g );
			}
		};
		btnPressed_1.setEnabled(false);
		panel.add(btnPressed_1);
		
		btnMouseover = new JButton("MouseOver")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setRollover( true );
				super.paintComponent( g );
			}
		};
		panel.add(btnMouseover);
		
		btnMouseover_1 = new JButton("MouseOver")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setRollover( true );
				super.paintComponent( g );
			}
		};
		btnMouseover_1.setEnabled(false);
		panel.add(btnMouseover_1);
		frame.getRootPane().setDefaultButton(btnFocused);
		panel.add(btnFocused);
		
		btnFocused_1 = new JButton("Focused");
		btnFocused_1.setEnabled(false);
		panel.add(btnFocused_1);
		
		btnBorderless = new JButton("Borderless");
		btnBorderless.setBorderPainted( false );
		panel.add(btnBorderless);
		
		btnBorderless_1 = new JButton("Borderless");
		btnBorderless_1.setEnabled(false);
		btnBorderless_1.setBorderPainted( false );
		panel.add(btnBorderless_1);
		
		btnBla = new JButton("Border");
		btnBla.setBorder( BorderFactory.createLineBorder( Color.RED ) );
		panel.add(btnBla);
		
		btnNewButton = new JButton("Border");
		btnNewButton.setBorder(new LineBorder(Color.RED));
		btnNewButton.setEnabled(false);
		panel.add(btnNewButton);
		
		btnBackground = new JButton("Background");
		btnBackground.setBackground( Color.RED );
		panel.add(btnBackground);
		
		btnBackground_1 = new JButton("Background");
		btnBackground_1.setBackground(Color.RED);
		btnBackground_1.setEnabled(false);
		panel.add(btnBackground_1);
		
		btnNoContentarea = new JButton("No ContentArea");
		btnNoContentarea.setContentAreaFilled( false );
		panel.add(btnNoContentarea);
		
		btnNoContentarea_1 = new JButton("No ContentArea");
		btnNoContentarea_1.setEnabled(false);
		btnNoContentarea_1.setContentAreaFilled( false );
		panel.add(btnNoContentarea_1);
		
		panel_1 = new JPanel();
		tabbedPane.addTab("RadioButtons", null, new JScrollPane( panel_1 ), null);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		panel_4 = new JPanel();
		panel_1.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblEnabled_2 = new JLabel("Enabled");
		panel_4.add(lblEnabled_2);
		
		lblDisabled_2 = new JLabel("Disabled");
		panel_4.add(lblDisabled_2);
		
		rdbtnDefault = new JRadioButton("Default");
		panel_4.add(rdbtnDefault);
		
		rdbtnDefault_1 = new JRadioButton("Default");
		rdbtnDefault_1.setEnabled(false);
		panel_4.add(rdbtnDefault_1);
		
		rdbtnPressed = new JRadioButton("Pressed")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setPressed( true );
				this.getModel( ).setArmed( true );
				super.paintComponent( g );
			}
		};
		panel_4.add(rdbtnPressed);
		
		rdbtnPressed_1 = new JRadioButton("Pressed")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setPressed( true );
				this.getModel( ).setArmed( true );
				super.paintComponent( g );
			}
		};
		rdbtnPressed_1.setEnabled(false);
		panel_4.add(rdbtnPressed_1);
		
		rdbtnMouseover = new JRadioButton("MouseOver")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setRollover( true );
				super.paintComponent( g );
			}
		};
		panel_4.add(rdbtnMouseover);
		
		rdbtnMouseover_1 = new JRadioButton("MouseOver")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setRollover( true );
				super.paintComponent( g );
			}
		};
		rdbtnMouseover_1.setEnabled(false);
		panel_4.add(rdbtnMouseover_1);
		
		rdbtnSelected = new JRadioButton("Selected")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setSelected( true );
				super.paintComponent( g );
			}
		};
		panel_4.add(rdbtnSelected);
		
		rdbtnSelected_1 = new JRadioButton("Selected")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setSelected( true );
				super.paintComponent( g );
			}
		};
		rdbtnSelected_1.setEnabled(false);
		panel_4.add(rdbtnSelected_1);
		
		rdbtnSelectedmouseover = new JRadioButton("Selected+MouseOver")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setSelected( true );
				this.getModel( ).setRollover( true );
				super.paintComponent( g );
			}
		};
		panel_4.add(rdbtnSelectedmouseover);
		
		rdbtnSelectedmouseover_1 = new JRadioButton("Selected+MouseOver")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setSelected( true );
				this.getModel( ).setRollover( true );
				super.paintComponent( g );
			}
		};
		rdbtnSelectedmouseover_1.setEnabled(false);
		panel_4.add(rdbtnSelectedmouseover_1);
		
		rdbtnBorder = new JRadioButton("Border");
		rdbtnBorder.setBorder(new LineBorder(Color.RED));
		panel_4.add(rdbtnBorder);
		
		rdbtnBorder_1 = new JRadioButton("Border");
		rdbtnBorder_1.setBorder(new LineBorder(Color.RED));
		panel_4.add(rdbtnBorder_1);
		
		rdbtnBackground = new JRadioButton("Background");
		rdbtnBackground.setBackground(Color.RED);
		panel_4.add(rdbtnBackground);
		
		rdbtnBackground_1 = new JRadioButton("Background");
		rdbtnBackground_1.setBackground(Color.RED);
		panel_4.add(rdbtnBackground_1);
		
		panel_5 = new JPanel();
		tabbedPane.addTab("CheckBoxes", null, new JScrollPane( panel_5 ), null);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		panel_6 = new JPanel();
		panel_5.add(panel_6, BorderLayout.NORTH);
		panel_6.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblEnabled_3 = new JLabel("Enabled");
		panel_6.add(lblEnabled_3);
		
		JLabel lblDisabled_3 = new JLabel("Disabled");
		panel_6.add(lblDisabled_3);
		
		chckbxDefault = new JCheckBox("Default");
		panel_6.add(chckbxDefault);
		
		chckbxDefault_1 = new JCheckBox("Default");
		chckbxDefault_1.setEnabled(false);
		panel_6.add(chckbxDefault_1);
		
		chckbxPressed = new JCheckBox("Pressed")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setPressed( true );
				this.getModel( ).setArmed( true );
				super.paintComponent( g );
			}
		};
		panel_6.add(chckbxPressed);
		
		chckbxPressed_1 = new JCheckBox("Pressed")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setPressed( true );
				this.getModel( ).setArmed( true );
				super.paintComponent( g );
			}
		};
		chckbxPressed_1.setEnabled(false);
		panel_6.add(chckbxPressed_1);
		
		chckbxMouseover = new JCheckBox("MouseOver")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setRollover( true );
				super.paintComponent( g );
			}
		};
		panel_6.add(chckbxMouseover);
		
		chckbxMouseover_1 = new JCheckBox("MouseOver")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setRollover( true );
				super.paintComponent( g );
			}
		};
		chckbxMouseover_1.setEnabled(false);
		panel_6.add(chckbxMouseover_1);
		
		chckbxSelected = new JCheckBox("Selected")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setSelected( true );
				super.paintComponent( g );
			}
		};
		chckbxSelected.setSelected(true);
		panel_6.add(chckbxSelected);
		
		chckbxSelected_1 = new JCheckBox("Selected")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setSelected( true );
				super.paintComponent( g );
			}
		};
		chckbxSelected_1.setSelected(true);
		chckbxSelected_1.setEnabled(false);
		panel_6.add(chckbxSelected_1);
		
		chckbxSelectedmouseover = new JCheckBox("Selected+MouseOver")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setSelected( true );
				this.getModel( ).setRollover( true );
				super.paintComponent( g );
			}
		};
		chckbxSelectedmouseover.setSelected(true);
		panel_6.add(chckbxSelectedmouseover);
		
		chckbxSelectedmouseover_1 = new JCheckBox("Selected+MouseOver")
		{
			@Override
			public void paintComponent(Graphics g)
			{
				this.getModel( ).setSelected( true );
				this.getModel( ).setRollover( true );
				super.paintComponent( g );
			}
		};
		chckbxSelectedmouseover_1.setEnabled(false);
		chckbxSelectedmouseover_1.setSelected(true);
		panel_6.add(chckbxSelectedmouseover_1);
	}

}
