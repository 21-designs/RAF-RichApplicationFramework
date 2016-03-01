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

import org.drost.application.interference.AbstractExceptionHandler;
import org.drost.application.interference.AbstractInactivityHandler;

/**
 * Contains and provides access to different interference handlers.
 * @author kimschorat
 *
 */
public class StateChangeController
{
	AbstractExceptionHandler exceptionHandler = null;

	AbstractInactivityHandler inactiveHandler = null;

	/**
	 * Returns the applications exception handler to
	 * 
	 * @return The exception handler.
	 */
	public AbstractExceptionHandler getExceptionHandler( )
	{
		return exceptionHandler;
	}

	/**
	 * Sets a new handler for all uncaught exception occuring in this
	 * application.
	 * 
	 * @param handler
	 *            The handler for uncaught exceptions.
	 */
	public void setExceptionHandler( AbstractExceptionHandler handler )
	{
		exceptionHandler = handler;
		if ( !exceptionHandler.isRegistered( ) )
			exceptionHandler.registerHandler( );
	}

	/**
	 * Sets the handler for that case the application/ the user gets inactive.
	 * 
	 * @return The inactive handler.
	 */
	public AbstractInactivityHandler getInactivityHandler( )
	{
		return inactiveHandler;
	}

	/**
	 * Sets a new handler for that case the application/ the user gets inactive.
	 * 
	 * @param handler
	 *            The handler for inactivity.
	 */
	public void setInactivityHandler( AbstractInactivityHandler handler )
	{
		inactiveHandler = handler;
		if ( !inactiveHandler.isRegistered( ) )
			inactiveHandler.registerHandler( );
	}
}
