package org.drost.application.plaf;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.Painter;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.tree.DefaultMutableTreeNode;

public class NimbusCustomizedViewer extends JFrame
{

	private JButton browse = null;

	public NimbusCustomizedViewer(String title)
	{
		this.setTitle(title);
		this.setSize( new Dimension(500, 600));
		this.setLocationRelativeTo(null);
		
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout( new BorderLayout() );
		
		
		// Add components
		JTabbedPane tabPane = new JTabbedPane();
		this.getContentPane().add(tabPane);
		
		// New tab
		
		JPanel tab1 = new JPanel();
		tab1.setLayout(new BoxLayout(tab1, BoxLayout.Y_AXIS));
		JPanel t11 = new JPanel(new FlowLayout());
		t11.add(this.createProgressBar(true, false, true));
		t11.add(this.createProgressBar(true, false, false));
		tab1.add(t11);
		JPanel t12 = new JPanel(new FlowLayout());
		t12.add(this.createProgressBar(false, false, true));
		t12.add(this.createProgressBar(false, false, false));
		tab1.add(t12);
		JPanel t13 = new JPanel(new FlowLayout());
		t13.add(this.createProgressBar(true, true, true));
		t13.add(this.createProgressBar(true, true, false));
		t13.add(this.createProgressBar(false, true, true));
		t13.add(this.createProgressBar(false, true, false));
		tab1.add(t13);
		tabPane.addTab("Progressbars", tab1);
		
		// New tab
		
		JPanel tab2 = new JPanel();
		tab2.setLayout(new BoxLayout(tab2, BoxLayout.Y_AXIS));
		JPanel t21 = new JPanel(new FlowLayout());
		t21.add(this.createButton("Default", true));
		t21.add(this.createButton("Disabled", false));
		tab2.add(t21);
		
		JPanel t22 = new JPanel(new FlowLayout());
		ButtonGroup gruppe = new ButtonGroup();
		JToggleButton b1 = this.createToggleButton("Toggle Group", true, true);
		JToggleButton b2 = this.createToggleButton("Toggle Group", false, true);
		JToggleButton b3 = this.createToggleButton("Toggle Group", false, false);
		gruppe.add(b1);
        gruppe.add(b2);
        gruppe.add(b3);
        t22.add(b1);
        t22.add(b2);
        t22.add(b3);
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
		t33.add(this.createJScrollPaneWithTextArea(true));
		t33.add(this.createJScrollPaneWithTextArea(false));
		tab3.add(t33);
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
		t62.add(this.createList(true));
		t62.add(this.createList(false));
		tab6.add(t62);
		tabPane.addTab("Tables", tab6);
		
		// New tab

		JPanel tab7 = new JPanel();
		tab7.setLayout(new BoxLayout(tab7, BoxLayout.Y_AXIS));
		JPanel t71 = new JPanel(new FlowLayout());
		t71.add(this.createComboBox(true));
		t71.add(this.createComboBox(false));
		tab7.add(t71);
		
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
		
		
		
//		this.getContentPane().add(this.createButton());
//		this.getContentPane().add(this.createTextField());
//		this.getContentPane().add(this.createPasswordField());
//		this.getContentPane().add(this.createRadioButton());
//		this.getContentPane().add(this.createJScrollPaneWithTextArea());
//		this.getContentPane().add(this.createComboBox());
//		this.getContentPane().add(this.createLabel());
//		this.getContentPane().add(this.createTable());
//		this.getContentPane().add(this.createFileChooserButton() );
//		this.getContentPane().add(this.createCheckBox());
//		this.getContentPane().add(this.createSlider());
//		this.getContentPane().add(this.createProgressBar(true));
//		this.getContentPane().add(this.createTabbedPane());
		
		
		this.setJMenuBar(this.createMenuBar());
		this.setVisible(true);
	}
	
	
	
	private JTextField createTextField(String text, boolean enabled)
	{
		JTextField x = new JTextField(text, 10);
		x.setEnabled(enabled);
		return x;
	}
	
	private JButton createButton(String text, boolean enabled)
	{
		JButton x = new JButton(text);
		x.setEnabled(enabled);
		return x;
	}
	
	private JToggleButton createToggleButton(String text, boolean selected, boolean enabled)
	{
		JToggleButton x = new JToggleButton(text);
		x.setSelected(selected);
		x.setEnabled(enabled);
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
	
	private JScrollPane createJScrollPaneWithTextArea(boolean enabled)
	{
		JTextArea area = new JTextArea("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, \n" +
				"sed diam nonumy eirmod tempor invidunt ut labore et \n" +
				"dolore magna aliquyam erat, sed diam voluptua. At vero \n" +
				"eos et accusam et justo duo dolores et ea rebum. Stet \n" +
				"clita kasd gubergren, no sea takimata sanctus est Lorem \n" +
				"ipsum dolor sit amet.\n", 5, 17);
		area.setEnabled(enabled);
		JScrollPane x = new JScrollPane( area );
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
		// Add the table to a scrolling pane
		JScrollPane scrollPane = new JScrollPane( table );
		
//		topPanel.add( scrollPane, BorderLayout.CENTER );
		scrollPane.setPreferredSize(new Dimension(230,120));
		return scrollPane;
	}
	
	private JList createList(boolean enabled)
	{
		String[] data = {"John", "Mike", "William", "Scott", "Emily"};
		JList x = new JList(data);
		x.setEnabled(enabled);
		
		return x;
	}
	
	private JComboBox createComboBox(boolean enabled)
	{
		String[] bookTitles = new String[] {"Effective Java", "Head First Java",
                "Thinking in Java", "Java for Dummies"};

		JComboBox x = new JComboBox(bookTitles);
		x.setEnabled(enabled);
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
		JInternalFrame x = new JInternalFrame("Frame");
		x.setEnabled( enabled );
		
		return x;
	}
	
	private Canvas createCanvas()
	{
		Canvas x = new Canvas();

	    x.setSize(100, 100);
	    x.setBackground(Color.BLACK);
	    x.setVisible(true);
	    x.setFocusable(false);
	    
	    return x;
	}
	
	private JProgressBar createProgressBar(boolean determinate, boolean vertical, boolean enabled)
	{
		JProgressBar x = new JProgressBar((vertical ? JProgressBar.VERTICAL : JProgressBar.HORIZONTAL));
		x.setValue(50);
		x.setIndeterminate(!determinate);
		x.setStringPainted(true);
		x.setEnabled(enabled);
		return x;
	}
	
	private JMenuBar createMenuBar()
	{
		JMenuBar jmb = new JMenuBar();

	    JMenu jmFile = new JMenu("File");
	    JMenuItem jmiOpen = new JMenuItem("Open");
	    JMenuItem jmiClose = new JMenuItem("Close");
	    JMenuItem jmiSave = new JMenuItem("Save");
	    JMenuItem jmiExit = new JMenuItem("Exit");
	    jmFile.add(jmiOpen);
	    jmFile.add(jmiClose);
	    jmFile.add(jmiSave);
	    jmFile.addSeparator();
	    jmFile.add(jmiExit);
	    jmb.add(jmFile);

	    JMenu jmOptions = new JMenu("Options");
	    JMenu a = new JMenu("A");
	    JMenuItem b = new JMenuItem("B");
	    JMenuItem c = new JMenuItem("C");
	    JMenuItem d = new JMenuItem("D");
	    a.add(b);
	    a.add(c);
	    a.add(d);
	    jmOptions.add(a);

	    JMenu e = new JMenu("E");
	    e.add(new JMenuItem("F"));
	    e.add(new JMenuItem("G"));
	    jmOptions.add(e);

	    jmb.add(jmOptions);

	    JMenu jmHelp = new JMenu("Help");
	    JMenuItem jmiAbout = new JMenuItem("About");
	    jmHelp.add(jmiAbout);
	    jmb.add(jmHelp);
	    
	    return jmb;
	}
	
	/**
	 * @param args
	 * @throws InvocationTargetException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException, InvocationTargetException 
	{
		SwingUtilities.invokeAndWait(new Runnable() {

			@Override
			public void run() {
				try {
				    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
				    }
				} catch (Exception e) {
				    // If Nimbus is not available, you can set the GUI to another look and feel.
				}
			}
			
		});
		
		Color focus = new Color(255, 134, 59);
		Color progressBarDisabled = new Color(249, 227, 213);
		Color control = new Color(51, 51, 51);
		Color light = new Color(102, 102, 102);
		Color bright = new Color(189, 189, 189);
		Color dark = new Color(12, 12, 12);
		
		UIManager.put("control", control);
		UIManager.put("nimbusBase", dark);
		UIManager.put("nimbusFocus", focus);
		UIManager.put("nimbusLightBackground", light);
		UIManager.put("nimbusSelectionBackground", dark);
		UIManager.put("nimbusSelectedText", focus);
		
		// ProgressBar
		UIManager.getLookAndFeelDefaults( ).put("ProgressBar[Enabled+Indeterminate].foregroundPainter", new IndeterminatePainter( focus.brighter( ).brighter( ), focus.darker( )));
		UIManager.getLookAndFeelDefaults( ).put("ProgressBar[Disabled+Indeterminate].foregroundPainter", new IndeterminatePainter( progressBarDisabled, progressBarDisabled.darker( )));
		UIManager.getLookAndFeelDefaults( ).put("ProgressBar.tileWidth", 50);
		UIManager.getLookAndFeelDefaults( ).put("ProgressBar.cycleTime", 750);
		
		// ScrollBar
		UIManager.getLookAndFeelDefaults( ).put("ScrollBar:\"ScrollBar.button\"[Disabled].foregroundPainter", new ScrollBarButtonPainter(light, bright));
		UIManager.getLookAndFeelDefaults( ).put("ScrollBar:\"ScrollBar.button\"[Enabled].foregroundPainter", new ScrollBarButtonPainter(light, bright));
		UIManager.getLookAndFeelDefaults( ).put("ScrollBar:\"ScrollBar.button\"[MouseOver].foregroundPainter", new ScrollBarButtonPainter(light.brighter( ), bright.brighter( )));
		UIManager.getLookAndFeelDefaults( ).put("ScrollBar:\"ScrollBar.button\"[Pressed].foregroundPainter", new ScrollBarButtonPainter(light.darker( ), bright.darker( )));
		UIManager.getLookAndFeelDefaults( ).put("ScrollBar:ScrollBarThumb[Enabled].backgroundPainter", new ScrollBarThumbPainter(light));
		UIManager.getLookAndFeelDefaults( ).put("ScrollBar:ScrollBarThumb[MouseOver].backgroundPainter", new ScrollBarThumbPainter(light.brighter( )));
		UIManager.getLookAndFeelDefaults( ).put("ScrollBar:ScrollBarThumb[Pressed].backgroundPainter", new ScrollBarThumbPainter(light.darker( )));
		UIManager.getLookAndFeelDefaults( ).put("ScrollBar:ScrollBarTrack[Enabled].backgroundPainter", new ScrollBarTrackPainter(control));
		UIManager.getLookAndFeelDefaults( ).put("ScrollBar:ScrollBarTrack[Disabled].backgroundPainter", new ScrollBarTrackPainter(control));
		
		new NimbusCustomizedViewer("Custom Nimbus LAF");
	}
	
	private static class ScrollBarTrackPainter implements Painter
	{
		private Color c;
		
		private ScrollBarTrackPainter(Color c)
		{
			this.c = c;
		}
		
		@Override
		public void paint( Graphics2D g, Object object, int width, int height )
		{
			g.setColor( c );
			g.fillRect( 0, 0, width, height );
			
			g.setColor( new Color(51, 51, 51).darker( ) );
			g.drawLine( 0, 0, width, 0 );
//			g.drawLine( 0, height, width, height );
		}
	}
	
	private static class ScrollBarThumbPainter implements Painter
	{
		private Color c;
		
		private ScrollBarThumbPainter(Color c)
		{
			this.c = c;
		}
		
		@Override
		public void paint( Graphics2D g, Object object, int width, int height )
		{
			g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
			
			GradientPaint gradient = new GradientPaint(0, 0, c, 0, height, c.darker( ));
			g.setPaint( gradient );
			g.fillRoundRect( 1, 1, width-2, height-2, 7, 7 );
			
			g.setColor( new Color(12, 12, 12) );
			g.drawRoundRect( 1, 1, width-2, height-2, 7, 7 );
		}
	}
	
	
	private static class ScrollBarButtonPainter implements Painter
	{
		private Color background, foreground;
		
		private ScrollBarButtonPainter(Color background, Color foreground)
		{
			this.background = background;
			this.foreground = foreground;
		}
		

		@Override
		public void paint( Graphics2D g, Object object, int width, int height )
		{
			width = width/4*3;
			
			g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
			
			GradientPaint gradient = new GradientPaint(0, 0, background, 0, height, background.darker( ));
			g.setPaint( gradient );
			g.fillRoundRect( 1, 1, width-2, height-2, 7, 7 );
			
			g.setColor( new Color(12, 12, 12) );
			g.drawRoundRect( 1, 1, width-2, height-2, 7, 7 );
			
			g.setColor( foreground );
			int[] x = {width/3-1, (width/3)*2, (width/3)*2};
			int[] y = {height/2, height/3-1, (height/3)*2+1};
			
			g.fillPolygon( x, y, 3 );
			
			
//			g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
//			
//			g.setColor( new Color(12, 12, 12) );
//			g.drawLine( 0, 0, width, 0 );
////			g.drawLine( 0, height, width, height );
//			
//			width = width/3*2;
//			
////			g.drawRoundRect( 0, 0, width, height, 5, 5 );
//
//			
//			g.setColor( c.brighter( ) );
//			int[] x = {width/3-1, (width/3)*2, (width/3)*2};
//			int[] y = {height/2, height/3-1, (height/3)*2+1};
//			
//			g.fillPolygon( x, y, 3 );
////			g.setColor( c.darker( ).darker( ) );
////			g.drawPolygon( x, y, 3 );
		}
		
	}
	
	private static class IndeterminatePainter implements Painter
	{

		private Color			light, dark;
		private GradientPaint	gradient;

		public IndeterminatePainter( Color light, Color dark )
		{
			this.light = light;
			this.dark = dark;
		}

		@Override
		public void paint( Graphics2D g, Object object, int width, int height )
		{
			g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
			
			gradient = new GradientPaint( ( width / 2.0f ), 0, light, ( width / 2.0f ), ( height / 2.0f ), dark, true );
			g.setPaint( gradient );
			g.fillRoundRect( 2, 2, width - 5, height - 5, 7, 7 );

			g.setColor( dark.darker( ) );
			g.drawRoundRect( 2, 2, width - 5, height - 5, 7, 7 );

			Color glow = new Color( dark.getRed( ), dark.getGreen( ), dark.getBlue( ), 100 );
			g.setColor( glow );
			g.drawRoundRect( 1, 1, width - 3, height - 3, 7, 7 );

		}
	}

}
