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
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.drost.application.Application;
import org.drost.application.ApplicationViewStateManager;
import org.drost.application.ViewStateManager;
import org.drost.application.ext.state.AppearanceStateManager;
import org.drost.application.listeners.ApplicationAdapter;
import org.drost.application.listeners.ApplicationEvent;
import org.drost.application.session.UIPersistenceManager;

/**
 * @author kimschorat
 *
 */
public class RichLookAndFeelDemo extends JFrame
{
	public RichLookAndFeelDemo(String title)
	{
		this.setTitle(title);
		this.setSize( new Dimension(500, 600));
		this.setLocationRelativeTo(null);
		
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout( new BorderLayout() );
		
		
		// Add components
		JTabbedPane tabPane = new JTabbedPane();
		this.getContentPane().add(tabPane, BorderLayout.CENTER);
		
		
		JPanel tbContainer = new JPanel( );
		tbContainer.setLayout(new BoxLayout(tbContainer, BoxLayout.X_AXIS));
		this.getContentPane().add( tbContainer, BorderLayout.NORTH );
		
		JToolBar tb1 = new JToolBar(JToolBar.HORIZONTAL);		
		tb1.add( this.createToggleButton( UIManager.getLookAndFeelDefaults( ).getIcon( "FileView.floppyDriveIcon" ), "", false, true ) );
		tb1.add( this.createToggleButton( UIManager.getLookAndFeelDefaults( ).getIcon( "FileView.computerIcon" ), "", false, true ) );
		tb1.addSeparator( );
		tb1.add( this.createToggleButton( UIManager.getLookAndFeelDefaults( ).getIcon( "FileView.directoryIcon" ), "", false, true ) );
		tb1.add( this.createToggleButton( UIManager.getLookAndFeelDefaults( ).getIcon( "FileView.fileIcon" ), "", true, true ) );
		tb1.addSeparator( );
		tb1.add( this.createButton( UIManager.getLookAndFeelDefaults( ).getIcon( "FileView.hardDriveIcon" ), "", true ) );
		tb1.add( Box.createHorizontalGlue( ) );
		tb1.add( this.createTextField( "Search", true ) );
		tb1.addSeparator( );
		tb1.add( Box.createHorizontalGlue( ) );
		tbContainer.add( tb1 );
		
		JToolBar tb2 = new JToolBar();		
		tb2.add( this.createButton( UIManager.getLookAndFeelDefaults( ).getIcon( "FileChooser.detailsViewIcon" ), "", true ) );
		tb2.add( this.createButton( UIManager.getLookAndFeelDefaults( ).getIcon( "FileChooser.listViewIcon" ), "", true ) );
		tb2.addSeparator( );
		tb2.add( this.createToggleButton( UIManager.getLookAndFeelDefaults( ).getIcon( "FileChooser.newFolderIcon" ), "", true, true ) );
		tb2.add( this.createToggleButton( UIManager.getLookAndFeelDefaults( ).getIcon( "FileChooser.upFolderIcon" ), "", true, true ) );
		tb2.addSeparator( );
		tb2.add( this.createButton( UIManager.getLookAndFeelDefaults( ).getIcon( "FileChooser.fileIcon" ), "", true ) );
		tbContainer.add( tb2 );		
		
		// New tab
		
		JPanel tab1 = new JPanel();
		tab1.setLayout(new BoxLayout(tab1, BoxLayout.Y_AXIS));
		JPanel t11 = new JPanel(new FlowLayout());
		t11.add(this.createProgressBar(0, true, false, true));
		t11.add(this.createProgressBar(0, true, false, false));
		tab1.add(t11);
		JPanel t11a = new JPanel(new FlowLayout());
		t11a.add(this.createProgressBar(50, true, false, true));
		t11a.add(this.createProgressBar(50, true, false, false));
		tab1.add(t11a);
		JPanel t11b = new JPanel(new FlowLayout());
		t11b.add(this.createProgressBar(100, true, false, true));
		t11b.add(this.createProgressBar(100, true, false, false));
		tab1.add(t11b);
		JPanel t12 = new JPanel(new FlowLayout());
		t12.add(this.createProgressBar(10, false, false, true));
		t12.add(this.createProgressBar(10, false, false, false));
		tab1.add(t12);
		JPanel t12a = new JPanel(new FlowLayout());
		t12a.add(this.createProgressBar(100, false, false, true));
		t12a.add(this.createProgressBar(100, false, false, false));
		tab1.add(t12a);
		JPanel t13 = new JPanel(new FlowLayout());
		t13.add(this.createProgressBar(50, true, true, true));
		t13.add(this.createProgressBar(50, true, true, false));
		t13.add(this.createProgressBar(50, false, true, true));
		t13.add(this.createProgressBar(50, false, true, false));
		tab1.add(t13);
		tabPane.addTab("Progressbars", tab1);
		
		// New tab
		
		JPanel tab2 = new JPanel();
		tab2.setLayout(new BoxLayout(tab2, BoxLayout.Y_AXIS));
		JPanel t21 = new JPanel(new FlowLayout());
		t21.add(this.createButton(UIManager.getLookAndFeelDefaults( ).getIcon( "FileView.directoryIcon" ), "Default", true));
		t21.add(this.createButton(UIManager.getLookAndFeelDefaults( ).getIcon( "FileView.directoryIcon" ), "Disabled", false));
		tab2.add(t21);
		
		JPanel t22 = new JPanel(new FlowLayout());
		ButtonGroup gruppe = new ButtonGroup();
		JToggleButton b1 = this.createToggleButton(null, "Toggle Group", true, true);
		JToggleButton b2 = this.createToggleButton(null, "Toggle Group", false, true);
		JToggleButton b3 = this.createToggleButton(null, "Toggle Group", false, false);
		JToggleButton b4 = this.createToggleButton(null, "Toggle Group", true, false);
		gruppe.add(b1);
        gruppe.add(b2);
        t22.add(b1);
        t22.add(b2);
        t22.add(b3);
        t22.add(b4);
        tab2.add(t22);
		
        JPanel t23 = new JPanel(new FlowLayout());
		t23.add(this.createRadioButton("Selected", true, true));
		t23.add(this.createRadioButton("Default", false, true));
		t23.add(this.createRadioButton("Disabled", true, false));
		tab2.add(t23);
		
		JPanel t24 = new JPanel( new FlowLayout() );
		t24.add(this.createCheckBox("Selected", true, true));
		t24.add(this.createCheckBox("Default", false, true));
		t24.add(this.createCheckBox("Disabled", true, false));
		tab2.add(t24);
		tabPane.addTab("Buttons", tab2);

		// New tab
		
		JPanel tab3 = new JPanel();
		tab3.setLayout(new BoxLayout(tab3, BoxLayout.Y_AXIS));
		JPanel t31 = new JPanel(new FlowLayout());
		t31.add(this.createTextField("Default", true));
		t31.add(this.createTextField("Disabled", false));
		tab3.add(t31);
		
		JPanel t32 = new JPanel(new FlowLayout());
		t32.add(this.createPasswordField("Default", true));
		t32.add(this.createPasswordField("Disabled", false));
		tab3.add(t32);
		
		JPanel t33 = new JPanel(new FlowLayout());
		t33.add(this.createTextArea(true));
		t33.add(this.createTextArea(false));
		tab3.add(t33);
		
		JPanel t34 = new JPanel(new FlowLayout());
		t34.add(this.createScrollPane( createTextArea(true), true ));
		t34.add(this.createScrollPane( createTextArea(false), false ));
		tab3.add(t34);
		tabPane.addTab("Text", tab3);
		
		// New tab
		
		JPanel tab4 = new JPanel();
		tab4.setLayout(new BoxLayout(tab4, BoxLayout.Y_AXIS));
		JPanel t41 = new JPanel(new FlowLayout());
		t41.add(this.createSlider(false, false, false, true));
		t41.add(this.createSlider(false, false, false, false));
		tab4.add(t41);
		
		JPanel t42 = new JPanel(new FlowLayout());
		t42.add(this.createSlider(true, true, false, true));
		t42.add(this.createSlider(true, true, false, false));
		tab4.add(t42);
		
		JPanel t44 = new JPanel(new FlowLayout());
		t44.add(this.createSlider(true, true, true, true));
		t44.add(this.createSlider(true, true, true, false));
		tab4.add(t44);
		tabPane.addTab("Slider", tab4);
		
		// New tab
		
		JPanel tab5 = new JPanel();
		tab5.setLayout(new BoxLayout(tab5, BoxLayout.Y_AXIS));
		JPanel t51 = new JPanel(new FlowLayout());
		t51.add(this.createFileChooserButton("Open"));
		t51.add(this.createColorPicker("Select Color"));
		tab5.add(t51);
		tabPane.addTab("Dialogs", tab5);
		
		// New tab
		
		JPanel tab6 = new JPanel();
		tab6.setLayout(new BoxLayout(tab6, BoxLayout.Y_AXIS));
		JPanel t61 = new JPanel(new FlowLayout());
		t61.add(this.createTable(true));
		t61.add(this.createTable(false));
		tab6.add(t61);
		
		JPanel t62 = new JPanel(new FlowLayout());
		t62.add(this.createScrollPane( createList(true), true ));
		t62.add(this.createScrollPane( createList(false), false ));
		tab6.add(t62);
		tabPane.addTab("Tables", tab6);
		
		// New tab

		JPanel tab7 = new JPanel();
		tab7.setLayout(new BoxLayout(tab7, BoxLayout.Y_AXIS));
		JPanel t71 = new JPanel(new FlowLayout());
		t71.add(this.createComboBox(false, true));
		t71.add( this.createButton( null, "I", true ) );
		t71.add(this.createComboBox(false, false));
		tab7.add(t71);
		
		JPanel t711 = new JPanel(new FlowLayout());
		t711.add(this.createComboBox(true, true));
		t711.add( this.createButton( null, "I", true ) );
		t711.add(this.createComboBox(true, false));
		tab7.add(t711);
		
		JPanel t72 = new JPanel(new FlowLayout());
		t72.add(this.createSpinner(true));
		t72.add(this.createSpinner(false));
		tab7.add(t72);
		tabPane.addTab("Combo", tab7);
		
		// New tab
		
		JPanel tab8 = new JPanel();
		tab8.setLayout(new BoxLayout(tab8, BoxLayout.Y_AXIS));
		JPanel t81 = new JPanel(new FlowLayout());
		t81.add(this.createSplitPane(true, false, true));
		t81.add(this.createSplitPane(true, false, false));
		tab8.add(t81);
		
		JPanel t82 = new JPanel(new FlowLayout());
		t82.add(this.createSplitPane(true, true, true));
		t82.add(this.createSplitPane(true, true, false));
		tab8.add(t82);
		
		JPanel t83 = new JPanel(new FlowLayout());
		t83.add(this.createSplitPane(false, true, true));
		t83.add(this.createSplitPane(false, true, false));
		tab8.add(t83);
		
		tabPane.addTab("SplitPane", tab8);
		
		// New tab
		
		JPanel tab9 = new JPanel();
		tab9.setLayout(new BoxLayout(tab9, BoxLayout.Y_AXIS));
		JPanel t91 = new JPanel(new FlowLayout());
		t91.add(this.createTree(true));
		t91.add(this.createTree(false));
		tab9.add(t91);
		tabPane.addTab("Tree", tab9);
		
		// New tab
		
		JPanel tab10 = new JPanel();
		tab10.setLayout(new BoxLayout(tab10, BoxLayout.Y_AXIS));
		JPanel t101 = new JPanel(new FlowLayout());
		t101.add(this.createLabel( ));
		tab10.add(t101);
		tabPane.addTab("ToolTip", tab10);
		
		// New tab
		
		JPanel tab11 = new JPanel( new BorderLayout() );
		tab11.add(this.createDesktopPane( ), BorderLayout.CENTER);
		tabPane.addTab("Desktop", tab11);
		
		// New tab
		
		JPanel tab12 = new JPanel( new BorderLayout() );
		tab12.add(this.createCanvas( ), BorderLayout.CENTER);
		tabPane.addTab("Canvas", tab12);
		
		
		
		
		this.setJMenuBar(this.createMenuBar());
		this.setVisible(true);
	}
	
	
	
	private JTextField createTextField(String text, boolean enabled)
	{
		JTextField x = new JTextField(text, 10);
		x.setEnabled(enabled);
		return x;
	}
	
	private JButton createButton(Icon icon, String text, boolean enabled)
	{
		JButton x = new JButton(text);
		x.setEnabled(enabled);
		x.setIcon( icon );
		return x;
	}
	
	private JToggleButton createToggleButton(Icon icon, String text, boolean selected, boolean enabled)
	{
		JToggleButton x = new JToggleButton(text);
		x.setSelected(selected);
		x.setEnabled(enabled);
		x.setIcon( icon );
		return x;
	}
	
	private JPasswordField createPasswordField(String text, boolean enabled)
	{
		JPasswordField x = new JPasswordField(text, 10);
		x.setEnabled(enabled);
		return x;
	}
	
	private JRadioButton createRadioButton(String text, boolean selected, boolean enabled)
	{
		JRadioButton x = new JRadioButton(text);
		x.setSelected(selected);
		x.setEnabled(enabled);
		return x;
	}
	
	private JTextArea createTextArea(boolean enabled)
	{
		JTextArea x = new JTextArea("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, \n" +
				"sed diam nonumy eirmod tempor invidunt ut labore et \n" +
				"dolore magna aliquyam erat, sed diam voluptua. At vero \n" +
				"eos et accusam et justo duo dolores et ea rebum. Stet \n" +
				"clita kasd gubergren, no sea takimata sanctus est Lorem \n" +
				"ipsum dolor sit amet.\n", 5, 12);
		x.setEnabled(enabled);
		
		return x;
	}
	
	private JScrollPane createScrollPane( Component c, boolean enabled)
	{
		JScrollPane x = new JScrollPane( c );
		x.setEnabled(enabled);
		
		return x;
	}
	
	private JScrollPane createTable(boolean enabled)
	{
		// Create columns names
		String columnNames[] = { "Column 1", "Column 2", "Column 3" };

		// Create some data
		String dataValues[][] =
		{
			{ "12", "234", "67" },
			{ "-123", "43", "853" },
			{ "93", "89.2", "109" },
			{ "279", "9033", "3092" }
		};

		// Create a new table instance
		JTable table = new JTable( dataValues, columnNames );
		table.setEnabled(enabled);
		table.setAutoCreateRowSorter( true );
		
		
		// Add the table to a scrolling pane
		JScrollPane scrollPane = new JScrollPane( table );
		
//		topPanel.add( scrollPane, BorderLayout.CENTER );
		scrollPane.setPreferredSize(new Dimension(230,120));
		scrollPane.setEnabled( enabled );
		return scrollPane;
	}
	
	private JList createList(boolean enabled)
	{
		String[] data = {"John", "Mike", "William", "Scott", "Emily", "Lukas", "Bill", "Emma", "Josh", "Michael"};
		JList x = new JList(data);
		x.setEnabled(enabled);
		
		return x;
	}
	
	private JComboBox createComboBox(boolean editable, boolean enabled)
	{
		String[] bookTitles = new String[] {"Effective Java", "Head First Java",
                "Thinking in Java", "Java for Dummies"};

		JComboBox x = new JComboBox(bookTitles);
		x.setEnabled(enabled);
		x.setEditable( editable );
		return x;
	}
	
	private JSpinner createSpinner(boolean enabled)
	{
		SpinnerModel m = new SpinnerNumberModel();
		JSpinner x = new JSpinner(m);
		x.setEnabled(enabled);
		((JSpinner.DefaultEditor) x.getEditor()).getTextField().setColumns(10);
		
		return x;
		
	}
	
	private JLabel createLabel()
	{
		JLabel x = new JLabel("Mouse over for ToolTip");
		x.setToolTipText("This is a ToolTip!");
//		x.setEnabled(false);
		return x;
	}
	
	private JTabbedPane createTabbedPane()
	{
		JTabbedPane x = new JTabbedPane();
		x.setMinimumSize(new Dimension(100, 50));
		x.setMaximumSize(new Dimension(100, 50));
		x.setPreferredSize(new Dimension(100, 50));
		x.addTab("Tab1", new JLabel("content 1"));
		x.addTab("Tab2", new JLabel("content 2"));
		x.setToolTipText("This is a ToolTip!");
//		x.setEnabled(false);
		return x;
	}
	
	private JButton createFileChooserButton(String text)
	{
		final JButton browse = new JButton(text);
		browse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(browse);
			}
		});
		
		return browse;
	}
	
	private JButton createColorPicker(String text)
	{
		final JButton browse = new JButton(text);
		browse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JColorChooser.showDialog(browse, "Choose color", Color.WHITE);
			}
		});
		
		return browse;
	}
	
	private JCheckBox createCheckBox(String text, boolean selected, boolean enabled)
	{
		JCheckBox x = new JCheckBox(text);
		x.setSelected(selected);
		x.setEnabled(enabled);
		return x;
	}
	
	private JSlider createSlider(boolean labels, boolean ticks, boolean vertical, boolean enabled)
	{
		JSlider x = new JSlider((vertical ? JProgressBar.VERTICAL : JProgressBar.HORIZONTAL));
		x.setValue(50);
		
		if(labels)
		{
			Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
		    table.put (0, new JLabel("1"));
		    table.put (25, new JLabel("2"));
		    table.put (50, new JLabel("3"));
		    table.put (75, new JLabel("4"));
		    table.put (100, new JLabel("5"));
		    x.setLabelTable (table);
		    x.setPaintLabels(true);
		}
		
		if(ticks)
		{
			x.setMinorTickSpacing(25);
		    x.setMajorTickSpacing(25);
		    x.setPaintTicks(true);
		    x.setSnapToTicks(true);
		}
		x.setEnabled(enabled);
		return x;
	}
	
	private JSplitPane createSplitPane(boolean vertical, boolean oneTouch, boolean enabled)
	{
		JSplitPane x = new JSplitPane((vertical ? JSplitPane.VERTICAL_SPLIT : JSplitPane.HORIZONTAL_SPLIT));
		x.setLeftComponent( new JLabel("Left") );
		x.setRightComponent( new JLabel("Right") );
		x.setMinimumSize(new Dimension(200, 150));
		x.setMaximumSize(new Dimension(200, 150));
		x.setPreferredSize(new Dimension(200, 150));
		x.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		x.setEnabled( enabled );
		x.setContinuousLayout(true);
	    x.setOneTouchExpandable(oneTouch);
		return x;
	}
	
	private JTree createTree(boolean enabled)
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Planets");
	    DefaultMutableTreeNode mercury = new DefaultMutableTreeNode("Mercury");
	    root.insert(mercury, 0);
	    DefaultMutableTreeNode venus = new DefaultMutableTreeNode("Venus");
	    root.insert(venus, 1);
	    DefaultMutableTreeNode mars = new DefaultMutableTreeNode("Mars");
	    root.insert(mars, 2);
	    
	    JTree x = new JTree(root);
	    x.setEnabled( enabled );
	    x.setMinimumSize(new Dimension(200, 150));
		x.setMaximumSize(new Dimension(200, 150));
		x.setPreferredSize(new Dimension(200, 150));

	    return x;
	}
	
	private JInternalFrame createInternalFrame( boolean enabled )
	{
		JInternalFrame x = new JInternalFrame( "Internal Frame", true, true, true, true );
		JLabel reader = new JLabel( "Some content" );
		x.setContentPane( reader );
		x.setSize( 400, 300 );
		x.setLocation( 50, 50 );
		x.setVisible( true );
		x.setEnabled( enabled );
		
		return x;
	}
	
	private JDesktopPane createDesktopPane()
	{
	    JDesktopPane x = new JDesktopPane();
	    x.add(createInternalFrame(true));
//	    x.setMinimumSize(new Dimension(200, 150));
//		x.setMaximumSize(new Dimension(200, 150));
//		x.setPreferredSize(new Dimension(200, 150));
	    
	    return x;
	}
	
	private Canvas createCanvas()
	{
		Canvas x = new Canvas();

	    x.setSize(100, 100);
	    x.setBackground(Color.RED);
	    x.setVisible(true);
	    x.setFocusable(false);
	    
	    return x;
	}
	
	private JProgressBar createProgressBar(int value, boolean determinate, boolean vertical, boolean enabled)
	{
		JProgressBar x = new JProgressBar((vertical ? JProgressBar.VERTICAL : JProgressBar.HORIZONTAL));
		x.setValue(value);
		x.setIndeterminate(!determinate);
		x.setStringPainted(true);
		x.setEnabled(enabled);
		return x;
	}
	
	private JMenuBar createMenuBar()
	{
		JMenuBar jmb = new JMenuBar();

	    JMenu jmFile = new JMenu("File");
	    jmFile.setMnemonic(KeyEvent.VK_F);
	    JMenuItem jmiOpen = new JMenuItem("Open");
	    jmiOpen.setMnemonic(KeyEvent.VK_O);
	    jmiOpen.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    JMenuItem jmiClose = new JMenuItem("Close");
	    JMenuItem jmiSave = new JMenuItem("Save");
	    jmiSave.setMnemonic(KeyEvent.VK_S);
	    jmiSave.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    jmiSave.setEnabled( false );
	    JMenuItem jmiExit = new JMenuItem("Exit");
	    jmFile.add(jmiOpen);
	    jmFile.add(jmiClose);
	    jmFile.add(jmiSave);
	    jmFile.addSeparator();
	    jmFile.add(jmiExit);
	    jmb.add(jmFile);

	    JMenu jmOptions = new JMenu("Options");
	    JMenu a = new JMenu("Alphabet");
	    a.setMnemonic(KeyEvent.VK_A);
	    JMenuItem b = new JRadioButtonMenuItem("Beer");
	    b.setMnemonic(KeyEvent.VK_B);
	    b.setAccelerator(KeyStroke.getKeyStroke('B', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    b.setSelected( true );
	    JMenuItem b2 = new JRadioButtonMenuItem("Beer");
	    b2.setMnemonic(KeyEvent.VK_B);
	    b2.setAccelerator(KeyStroke.getKeyStroke('B', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    b2.setSelected( true );
	    b2.setEnabled( false );
	    JMenuItem c = new JCheckBoxMenuItem("Clown");
	    c.setMnemonic(KeyEvent.VK_C);
	    c.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    c.setSelected( true );
	    JMenuItem c2 = new JCheckBoxMenuItem("Clown");
	    c2.setMnemonic(KeyEvent.VK_C);
	    c2.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    c2.setSelected( true );
	    c2.setEnabled( false );
	    JMenuItem d = new JMenuItem("Donkey");
	    a.add(b);
	    a.add( b2 );
	    a.add(c);
	    a.add( c2 );
	    a.add(d);
	    jmOptions.add(a);

	    JMenu e = new JMenu("Elephant");
	    e.add(new JMenuItem("Flower"));
	    e.add(new JMenuItem("Gunfire"));
	    e.setEnabled( false );
	    jmOptions.add(e);

	    jmb.add(jmOptions);

	    JMenu jmHelp = new JMenu("Help");
	    JMenuItem jmiAbout = new JMenuItem("About");
	    jmHelp.add(jmiAbout);
	    jmb.add(jmHelp);
	    
	    return jmb;
	}
	
	
	
	/*
	 * 
	 */
	public static void main(String[] args) throws InterruptedException, InvocationTargetException 
	{
		Application app = Application.launch( "DawnLAF" );
		app.getGUI( ).setConsistentLookAndFeel( );
		
		RichLookAndFeelDemo view = new RichLookAndFeelDemo("");
		app.getGUI( ).setMainWindow( view );
		app.getGUI( ).setMainWindowConfirmExit( false );
		
//		ApplicationViewStateManager m = new ApplicationViewStateManager(app.getLocalStorage( ));
//		m.restore( view );
//		app.addApplicationListener( new ApplicationAdapter() {
//			@Override
//			public void applicationClosed( ApplicationEvent e )
//			{
//				m.store( view );
//			}
//		});	
		
		UIPersistenceManager p = new UIPersistenceManager();
		p.restore( view );
		app.addApplicationListener( new ApplicationAdapter() {
			@Override
			public void applicationClosing( ApplicationEvent e )
			{
				p.store( view );
			}
		});		
	}
	
	
}
