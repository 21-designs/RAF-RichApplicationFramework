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
package org.drost.application.conflict;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.drost.application.Application;

/**
 * Handles a certain state when the user seems to be inactive. This is
 * always a suggestion and is done by listening to the mouse or keyboard
 * inputs.
 * 
 * @author kimschorat
 * @since 1.0
 * 
 * @see ConflictHandler
 * @see Application
 */
@SuppressWarnings("serial")
public abstract class AbstractInactivityHandler implements ConflictHandler<Object>
{
	boolean registered = false;
	
	int inactiveIntervaleMinutes = 60;
	
	Timer inactiveTimer = new Timer(0, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			inactive = true;
			handle(new ConflictInfo<Object>(e.getSource( ), Thread.currentThread(), System.currentTimeMillis( )));
		}
	});
	
	private AWTEventListener inactivityListener = null;
	
	/**
	 * Indicates that the user is inactive. This is determined by checking the
	 * user inputs.
	 */
	protected volatile boolean inactive = false;
	

	
	
	/**
	 * Prints out the owning thread that caused this exception and additional
	 * the stack trace of the associated exception.
	 * 
	 * @param info
	 *            The notification that informs this handler.
	 */
	@Override
	public abstract void handle(ConflictInfo<Object> info);
	


	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void register( )
	{
		if(isRegistered())
			return;
		
		/*
		 * Adds a global mouse and keyboard event listener to the EDT. This is
		 * used to determine the users inactivity after no event has been fired
		 * for a certain time.
		 */
		long eventMask = AWTEvent.MOUSE_EVENT_MASK + AWTEvent.MOUSE_MOTION_EVENT_MASK + AWTEvent.MOUSE_WHEEL_EVENT_MASK
				+ AWTEvent.KEY_EVENT_MASK;
		
		inactivityListener = new AWTEventListener( )
		{
			public void eventDispatched( AWTEvent e )
			{
				// While the timer is not running the user has not made any
				// interaction or the user is inactive.
				if ( !inactiveTimer.isRunning( ) )
				{
					initializeTimer( );
				}

				if ( inactiveTimer.isRunning( ) )
					inactiveTimer.restart( );
			}

			private void initializeTimer( )
			{
				inactive = false;
				inactiveTimer.setInitialDelay( 60000 * inactiveIntervaleMinutes );
				inactiveTimer.setRepeats( false );
				inactiveTimer.start( );
			}

		}; 
		

		Toolkit.getDefaultToolkit( ).addAWTEventListener( inactivityListener, eventMask );

		ConflictManager.getConflictManager( ).getInactivityHandlers( ).add( this );
		
		registered = true;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unregister( )
	{
		Toolkit.getDefaultToolkit( ).removeAWTEventListener( inactivityListener );

//		ConflictManager.getConflictManager( ).getInactivityHandlers( ).add( this );
		
		registered = false;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRegistered()
	{
		return registered;
	}
	

	
	/**
	 * Sets the delay after a inactivity is suggested.
	 * 
	 * @param minutes
	 *            The delay in minutes.
	 */
	public synchronized void setInactiveInterval(int minutes)
	{
		inactiveIntervaleMinutes = minutes;
		
		int millis = minutes * 60000;
		inactiveTimer.setInitialDelay(millis);
		inactiveTimer.setDelay(millis);
		
		if(inactiveTimer.isRunning())
		{
			System.out.println("timer was still running");
			inactiveTimer.restart();
			inactive = false;
		}
	}
	
	
	/**
	 * Sets the delay after a inactivity is suggested.
	 * 
	 * @return minutes The delay in minutes.
	 */
	public int getInactiveIntervaleMinutes()
	{
		return inactiveIntervaleMinutes;
	}
	
	
	/**
	 * Returns whether the inactive state has been entered.
	 * 
	 * @return whether the inactive state has been entered.
	 * 
	 * @see #setInactiveInterval(int)
	 */
	public boolean isInactive()
	{
		return inactive;
	}

	
	
}