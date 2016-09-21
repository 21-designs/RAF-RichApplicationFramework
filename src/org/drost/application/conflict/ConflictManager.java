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
package org.drost.application.conflict;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kimschorat
 *
 */
public class ConflictManager
{
	private static ConflictManager instance = null;
	
	private List<AbstractExceptionHandler> exceptionHandlers = new ArrayList<AbstractExceptionHandler>();
	
	private List<AbstractInactivityHandler> inactivityHandlers = new ArrayList<AbstractInactivityHandler>();
	
	private List<AbstractMemoryExhaustedHandler> lowMemoryHandlers = new ArrayList<AbstractMemoryExhaustedHandler>();
	
	private List<AbstractJavaVersionHandler> javaVersionHandler = new ArrayList<AbstractJavaVersionHandler>();
	
	// Hidden
	private ConflictManager() {}
	
	/**
	 * 
	 * @return
	 */
	public static final synchronized ConflictManager getConflictManager()
	{
		if(instance == null)
		{
			instance = new ConflictManager();
		}
		
		return instance;
	}
	
	
	
	public List<AbstractInactivityHandler> getInactivityHandlers()
	{
		return inactivityHandlers;
	}
	
	public List<AbstractExceptionHandler> getExceptionHandlers()
	{
		return exceptionHandlers;
	}
	
	public AbstractExceptionHandler getCurrentExceptionHandler()
	{
		for(AbstractExceptionHandler eh : getExceptionHandlers( ))
		{
			if(eh.isRegistered( ))
				return eh;
		}
		
		return null;
	}
	
	public List<AbstractMemoryExhaustedHandler> getMemoryExhaustedHandlers()
	{
		return lowMemoryHandlers;
	}
	
	public AbstractMemoryExhaustedHandler getCurrentMemoryExhaustedHandler()
	{
		for(AbstractMemoryExhaustedHandler eh : getMemoryExhaustedHandlers( ))
		{
			if(eh.isRegistered( ))
				return eh;
		}
		
		return null;
	}
	
	
	public List<AbstractJavaVersionHandler> getJavaVersionHandlers()
	{
		return javaVersionHandler;
	}
	
	public AbstractJavaVersionHandler getCurrentJavaVersionHandlers()
	{
		for(AbstractJavaVersionHandler eh : getJavaVersionHandlers( ))
		{
			if(eh.isRegistered( ))
				return eh;
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public List<ConflictHandler> getServerDisconnectHandlers()
	{
		return null;
	}
	
	public List<ConflictHandler> getDatabaseDisconnectHandlers()
	{
		return null;
	}
}
