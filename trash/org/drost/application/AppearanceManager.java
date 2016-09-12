/*
 * This file is part of the application library that simplifies common 
 * initialization and helps setting up any java program.
 * 
 * Copyright (C) 2016 Yannick Drost, all rights reserved.
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.drost.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.FocusManager;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.drost.application.Application;
import org.drost.application.adapter.EdgeSnapAdapter;
import org.drost.application.utils.GraphicsUtils;

// TODO Move to Appearance / Merge with Appearance
@Deprecated
public class AppearanceManager 
{	
	private final EdgeSnapAdapter edgeSnapAdapter;
	
	private final JFrame standalone;
	
	/**
	 * 
	 */
	public AppearanceManager()
	{
		edgeSnapAdapter = new EdgeSnapAdapter();
		
		standalone = new JFrame();
		standalone.getContentPane( ).setBackground( Color.BLACK );
		standalone.setFocusable( false );
		standalone.setFocusableWindowState( false );
		standalone.setUndecorated( true );
		standalone.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		standalone.addWindowListener( new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e)
			{
				standalone.toBack( );
			}
		});
//		standalone.addMouseListener( new MouseAdapter() {
//
//			@Override
//			public void mousePressed( MouseEvent e )
//			{
//				standalone.toBack( );
//			}
//		});
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment( );
		GraphicsDevice gd = ge.getDefaultScreenDevice( );
		
		if( gd.isWindowTranslucencySupported( GraphicsDevice.WindowTranslucency.TRANSLUCENT ) )
		{
			standalone.setOpacity(0.55f);
		}
	}
	
	
	
	public Window getActiveWindow()
	{
		return FocusManager.getCurrentManager().getActiveWindow();
	}
	
	
	public int getWindowCount()
	{
		return Window.getWindows( ).length;
	}
	
	
	/**
	 * 
	 * @param view The window instance
	 * @return the state
	 * 
	 * @see JFrame#getExtendedState()
	 */
	public int getWindowState(Frame view)
	{
		return view.getExtendedState( );
	}
	
	
	public void windowMinimize(Frame view)
	{
		if(view != null )
		{
			view.setExtendedState(Frame.ICONIFIED);
		}
	}
	
	
	public void windowMaximize(Frame view)
	{
		if(view != null )
		{
			view.setExtendedState(Frame.MAXIMIZED_BOTH);
		}
	}
	
	
	public void windowClose(Window view)
	{
		if(view != null && view.isShowing( ))
			view.dispose( );
	}
	
	
	public void windowToFront(Window view)
	{
		if(view != null  )
			view.toFront( );
	}
	
	
	public void windowToBack(Window view)
	{
		if(view != null )
			view.toBack( );
	}
	
	
	public void windowStandAlone(Window w, boolean b)
	{
		if(b)
		{			
			standalone.toFront( );
			standalone.setVisible( b );
			w.toFront( );
		}
		else
		{
			standalone.dispose( );
		}
	}
	
	/**
	 * Listens to keystrokes and for example tries to close when ESC is pressed.
	 * 
	 * @param view
	 */
	public void windowKeyControl(Window view)
	{
		// see Appearance.createSubDialog()
	}
	
	
	public void windowFixSize( Window w, Dimension d )
	{
		w.setMaximumSize( d );
		w.setMaximumSize( d );
		w.setPreferredSize( d );
		if(w instanceof Frame)
		{
			( (Frame) w ).setResizable( false );
		}
		else if(w instanceof Dialog)
		{
			( (Dialog) w ).setResizable( false );
		}
	}
	
	


	/**
	 * Enables or disables the edge snapping functionality by adding or removing
	 * the adapter from the main window. 
	 * 
	 * @param enable
	 *            Enables or disables the snapping functionality.
	 */
	public boolean windowSnap(Window window, boolean enable)
	{
		if(window == null)
			return false;
		
		if(enable)
		{
//			if(!snapEnabled)
//			{
				for(ComponentListener l : window.getComponentListeners( ))
				{
					if(l.equals( edgeSnapAdapter ))
						return false;
				}
				window.addComponentListener(edgeSnapAdapter);
//				snapEnabled = true;
//			}
		}
		else
		{
//			if(snapEnabled)
//			{
				window.removeComponentListener(edgeSnapAdapter);
//				snapEnabled = false;
//			}
		}
		
		return true;
	}
	
	
	public void windowShake(Window view)
	{
		this.windowShake( view, 300 );
	}
	
	
	public void windowShake(Window view, long millis)
	{
		final long startMillis = System.currentTimeMillis( );
		int posX = view.getLocation( ).x;
		int posY = view.getLocation( ).y;
		
		final int DELAY = 5;
		final double CYCLE = 50;
		final int DISTANCE = 10;
		final double PI2 = Math.PI * 2;
		
		double angle;
		int newPosX = 0;
		long elapsedMillis = 0;
		
		while(elapsedMillis < millis)
		{
			elapsedMillis = System.currentTimeMillis( ) - startMillis;
			
			if(elapsedMillis % DELAY == 0)
			{
				angle = (elapsedMillis % CYCLE) / CYCLE * PI2;
				newPosX = (int) ((Math.sin( angle ) * DISTANCE) + posX);
				
				// Perform this on another thread to provide accessibility to the window
				view.setLocation( newPosX, posY );
			}
		}
		
		view.setLocation( posX, posY );
		view.repaint( );
	}
	
	
	public void windowFadeOut(Frame view)
	{
		this.windowFadeOut( view, 500 );
	}
	
	
	public void windowFadeOut(Frame view, long millis)
	{
		// TODO	Due to the Java7 undecorated frame bug
	}
	
	
	public void windowFadeIn(Window view)
	{
		this.windowFadeIn( view, 500 );
	}
	
	
	public void windowFadeIn(Window view, long millis)
	{
		// TODO Due to the Java7 undecorated frame bug
	}
	
	
//	public void windowProcessing(JFrame view, String statusMsg, boolean b)
//	{
////		JPanel overlay = new JPanel( );
//		JPanel overlay = (JPanel) view.getGlassPane();
//		
//		overlay.setLayout( new BorderLayout() );
////		overlay.setOpaque( true );
////		overlay.setBackground( new Color(0,0,0, 0.5f) );
//		overlay.setSize( view.getWidth( ), view.getHeight( ) );
//		overlay = (JPanel) createBlockingComp(overlay);
//		overlay.setVisible( true );
//		
//		
//		ImageIcon loading = new ImageIcon("ajax-loader.gif");
//		
//		JLabel icon = new JLabel(loading, JLabel.CENTER);
//		JLabel msg = new JLabel(statusMsg, JLabel.CENTER);
//		msg.setForeground( Color.GRAY );
//		
//		overlay.add( icon );
//		overlay.add( msg, BorderLayout.SOUTH );
//		
//		view.setGlassPane( overlay );
//		
//	}
	
	
	private Component createBlockingComp(Component c)
	{
		c.addMouseListener( new MouseListener() {

			@Override
			public void mouseClicked( MouseEvent e )
			{
				e.consume( );
			}

			@Override
			public void mousePressed( MouseEvent e )
			{
				e.consume( );
			}

			@Override
			public void mouseReleased( MouseEvent e )
			{
				e.consume( );
			}

			@Override
			public void mouseEntered( MouseEvent e )
			{
				e.consume( );
			}

			@Override
			public void mouseExited( MouseEvent e )
			{
				e.consume( );
			}
			
		});
		c.addKeyListener( new KeyListener() {

			@Override
			public void keyTyped( KeyEvent e )
			{
				e.consume( );
			}

			@Override
			public void keyPressed( KeyEvent e )
			{
				e.consume( );
			}

			@Override
			public void keyReleased( KeyEvent e )
			{
				e.consume( );
			}
			
		});
		return c;
	}
}
