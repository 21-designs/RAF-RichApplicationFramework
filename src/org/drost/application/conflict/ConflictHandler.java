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

import java.io.Serializable;

/**
 * This interface provides one single method that each implementing class
 * type should implement.
 * 
 * @author kimschorat
 *
 * @param <T>
 *            The generic source type of the {@link ConflictInfo}.
 */
public interface ConflictHandler<T> extends Serializable
{
	/**
	 * Handles this invoking notification object specified by every
	 * implementing subclass.
	 * 
	 * @param info
	 *            The notification object with a predefined type parameter.
	 */
	public void handle(final ConflictInfo<T> info);
	
	
	
	/**
	 * This method is called to register the specific handler instance. That
	 * means this handler starts listening to associated state changes related
	 * to this application.
	 */
	public void register();
	
	
	/**
	 * Unregisters this handler and stops listening to associated state changes 
	 * related to the specific implementation of this interface.
	 */
	public void unregister();
	
	/**
	 * Returns whether this handler has been registered to the application.
	 * 
	 * @return whether this handler has been registered to the application.
	 * 
	 * @see #register()
	 */
	public boolean isRegistered();
}
