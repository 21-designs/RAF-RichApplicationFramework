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
package org.drost.application.suppliers;

import org.drost.application.Application;
import org.drost.application.StateChangeController.Statement;
import org.drost.application.StateChangeController.StatementHandler;

/**
 * Handles a certain state when the user seems to be inactive. This is
 * always a suggestion and is done by listening to the mouse or keyboard
 * inputs.
 * 
 * @author kimschorat
 * @since 1.0
 * 
 * @see StatementHandler
 * @see Application
 */
@SuppressWarnings("serial")
public abstract class AbstractInactivityHandler implements StatementHandler<Void>
{
	/**
	 * Prints out the owning thread that caused this exception and additional
	 * the stack trace of the associated exception.
	 * 
	 * @param info
	 *            The notification that informs this handler.
	 */
	@Override
	public abstract void handle(Statement<Void> info);
	
}