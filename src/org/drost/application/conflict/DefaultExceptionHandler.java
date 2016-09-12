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

/**
 * Simply implements the abstract method {@link #handle(ConflictInfo)} as
 * declared in {@link ConflictHandler}.
 * 
 * @author kimschorat
 * @since 1.0
 * 
 * @see AbstractExceptionHandler
 */
@SuppressWarnings("serial")
public class DefaultExceptionHandler extends AbstractExceptionHandler
{
	/**
	 * Prints out the owning thread that caused this exception and additional
	 * the stack trace of the associated exception.
	 * 
	 * @param info
	 *            The notification that informs this handler.
	 */
	@Override
	public void handle(ConflictInfo<Throwable> info) 
	{
		// System.err.println("An exception occurred thrown by: " + info.getOwner().toString() + "\r\n");
		Object source = info.getSoure();
		if(source instanceof Throwable)
			( (Throwable) source ).printStackTrace();
	}
}
