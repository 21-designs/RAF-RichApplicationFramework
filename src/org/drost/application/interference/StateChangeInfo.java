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
package org.drost.application.interference;

import java.io.Serializable;

/**
 * A generic notification class that informs existing handlers. The
 * available constructors force any instantiation to set important class
 * fields. A notification stores some information about the {@code source}
 * object that created a notification object. The {code owner} Thread that
 * keeps this object.
 * 
 * @author kimschorat
 *
 * @param <T>
 *            The generic source type.
 */
@SuppressWarnings("serial")
public class StateChangeInfo<T> implements Serializable
{
	private final T soure;
	private final Thread owner;
	private final long timestamp;
	
	/**
	 * Creates a info object that can be used to inform interference handler.
	 * @param source
	 * @param owner
	 */
	public StateChangeInfo(final T source, final Thread owner)
	{
		this.soure = source;
		this.owner = owner;
		timestamp = System.currentTimeMillis();
	}
	
	
	public StateChangeInfo(T source, Thread ownerThread, long when)
	{
		this.soure = source;
		this.owner = ownerThread;
		timestamp = when;
	}

	public T getSoure() {
		return soure;
	}

	public Thread getOwnerThread() {
		return owner;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
}


