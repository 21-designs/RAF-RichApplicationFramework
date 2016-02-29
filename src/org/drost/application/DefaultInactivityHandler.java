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

import org.drost.application.StateChangeController.Statement;

/**
 * Simply implements the abstract method {@link #handle(Statement)} as
 * declared in {@link NotificationHandler}.
 * 
 * @author kimschorat
 * @since 1.0
 * 
 * @see AbstractInactivityHandler
 */
@SuppressWarnings("serial")
public class DefaultInactivityHandler extends AbstractInactivityHandler 
{
	
	/**
	 * Prints a notification message to the console that the user seems to
	 * be inactive and invokes a garbage collection. The possible
	 * performance loss should not be recognizable if the user is truly
	 * inactive.
	 * 
	 * @param info
	 *            The notification that informs this handler.
	 *            
	 * @see System#gc()
	 */
	@Override
	public void handle(Statement<Void> info) 
	{
		System.out.println("The user seems inactive thus the application starts a manual garbage collection. This might result in performance loss.");
		System.gc();
	}
}
