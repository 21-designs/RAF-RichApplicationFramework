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
package org.drost.application;

import javax.swing.JFrame;

import org.drost.application.listeners.ApplicationAdapter;
import org.drost.application.listeners.ApplicationEvent;

public class ApplicationDemo
{
	protected static Application app;

	/**
	 * Launches the application instance.
	 * @param args
	 */
	public static void main( String[] args )
	{
		Application.launch( ApplicationDemo.class.getSimpleName( ) );
		
		app = Application.get( );
		
//		app.lockInstance( );
		
		app.addApplicationListener( new ApplicationAdapter() {
			
			@Override
			public void applicationClosed(ApplicationEvent e)
			{
				if(app.isLocked( ))
					app.lockInstance( false );
			}
		});
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation( 3 );
		frame.setVisible( true );
	}

}
