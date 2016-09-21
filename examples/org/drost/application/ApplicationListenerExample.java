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

import org.drost.application.listeners.ApplicationEvent;
import org.drost.application.listeners.ApplicationListener;

/**
 * @author kimschorat
 *
 */
public class ApplicationListenerExample extends AbstractExample
{

	/**
	 * @param subclass
	 */
	public ApplicationListenerExample( )
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
		new ApplicationListenerExample();
		
		ApplicationListener l = new ApplicationListener()
		{

			@Override
			public void applicationLaunched( ApplicationEvent e )
			{
				// Done before the listener is added to the application. Invoked after restarting.
			}

			@Override
			public void applicationClosing( ApplicationEvent e )
			{
				System.out.println("closed");
			}

			@Override
			public void applicationUpdated( ApplicationEvent e )
			{
				System.out.println("updated");
			}

			@Override
			public void applicationLocked( ApplicationEvent e )
			{
				System.out.println("locked");
			}

			@Override
			public void applicationUnlocked( ApplicationEvent e )
			{
				System.out.println("unlocked");
			}

			@Override
			public void applicationRestarted( ApplicationEvent e )
			{
				// Will end in an infinite loop
			}
			
		};
		APPLICATION.addApplicationListener( l );
		APPLICATION.lockInstance( true );
		APPLICATION.lockInstance( false );
		APPLICATION.shutdown( );
		
		// TODO update app
		
		APPLICATION.removeApplicationListener( l );
		
	}

}
