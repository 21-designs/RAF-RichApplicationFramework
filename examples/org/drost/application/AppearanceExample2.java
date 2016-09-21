package org.drost.application;
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;

import org.drost.application.ui.GUI;

/**
 * @author kimschorat
 *
 */
public class AppearanceExample2 extends AbstractExample
{

	/**
	 * @param subclass
	 */
	public AppearanceExample2( )
	{
		super(ID);
	}

	/* (non-Javadoc)
	 * @see org.drost.application.AbstractExample#getName()
	 */
	@Override
	public String getName( )
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.drost.application.AbstractExample#getInformation()
	 */
	@Override
	public String getInformation( )
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static void main(String[] args)
	{
		new AppearanceExample2();
		
		JFrame f = APPLICATION.getGUI( ).createMainWindow( );
		f.setSize( new Dimension(300, 200) );
		f.setLocationRelativeTo( null );
		
		APPLICATION.getGUI( ).setConsistentLookAndFeel( );
		
		GUI a = Application.get( ).getGUI( );
		a.enableEdgeSnap( f, true );
		a.setMainWindowConfirmExit( true );
		
		JButton b0 = new JButton("Dialog");
		b0.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog d = new JDialog(f);
				d.setLocationRelativeTo( f );
				d.setSize( 200, 150 );
				d.setVisible( true );
				a.enableEdgeSnap( d, true );
			}
		});
		JButton b1 = new JButton("Shake");
		b1.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e )
			{
				a.shake( f );
			}
		});
		JButton b2 = new JButton("Focus");
		b2.addActionListener( new ActionListener() {
			
			boolean isAlone = false;
			
			@Override
			public void actionPerformed( ActionEvent e )
			{
				if(!isAlone)
				{
					a.spotlight( f, true );
				}
				else
				{
					a.spotlight( f, false );
				}
				
				isAlone = !isAlone;
			}
		});
		JButton b3 = new JButton("Loading");
		b3.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e )
			{
//				am.windowProcessing( f, "loading...", true );
			}
		});
		
		
		
		f.setLayout( new FlowLayout() );
		f.getContentPane( ).add( b0 );
		f.getContentPane( ).add( b1 );
		f.getContentPane( ).add( b2 );
		f.getContentPane( ).add( b3 );
		
		f.setDefaultCloseOperation( 3 );
		f.setVisible( true );		
	}

}
