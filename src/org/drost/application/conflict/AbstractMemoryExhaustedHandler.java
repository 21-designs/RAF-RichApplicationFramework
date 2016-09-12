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

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryNotificationInfo;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;

import javax.management.ListenerNotFoundException;
import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;

import org.drost.application.conflict.manager.ConflictManager;

/**
 * @author kimschorat
 *
 */
public abstract class AbstractMemoryExhaustedHandler implements ConflictHandler<MemoryPoolMXBean>
{
	boolean registered = false;
	
	private MemoryPoolMXBean memoryPool = null;
	
	private NotificationListener listener = null;
	
	@Override
	public void register( )
	{
		if(isRegistered())
			return;
		
		MemoryMXBean m = ManagementFactory.getMemoryMXBean( );
		
		listener = new NotificationListener( )
		{
			@Override
			public void handleNotification( Notification n, Object o )
			{
				if( n.getType( ).equals( MemoryNotificationInfo.MEMORY_THRESHOLD_EXCEEDED ) )
				{
					AbstractMemoryExhaustedHandler.this.handle( new ConflictInfo<MemoryPoolMXBean>(getMemoryPool(), Thread.currentThread( ), n.getTimeStamp( )) );
				}
			}
		};
		
		( (NotificationEmitter) m ).addNotificationListener( listener, null, null );

		for( AbstractMemoryExhaustedHandler mh : ConflictManager.getConflictManager( ).getMemoryExhaustedHandlers( ) )
		{
			mh.unregister( );
		}

		ConflictManager.getConflictManager( ).getMemoryExhaustedHandlers( ).add( this );
		
		registered = true;
	}

	
	@Override
	public void unregister( )
	{
		MemoryMXBean m = ManagementFactory.getMemoryMXBean( );
		try
		{
			( (NotificationEmitter) m ).removeNotificationListener( listener );
		}
		catch ( ListenerNotFoundException e )
		{

		}
		
		registered = false;
	}

	@Override
	public boolean isRegistered( )
	{
		return registered;
	}
	
	
	public void setPercentageThreshold( double percent )
	{
		if (percent >= 0.0 && percent < 1.0) 
		{
			long threshold = getMemoryPool().getUsage().getMax();
	        threshold *= (long) percent;
	        getMemoryPool().setUsageThreshold(threshold);
        }
		else
			throw new IllegalArgumentException("Value out of range");
	}
	
	
	private MemoryPoolMXBean getMemoryPool( )
	{
		if(memoryPool == null)
		{
			for( MemoryPoolMXBean m : ManagementFactory.getMemoryPoolMXBeans( ) )
			{
				if( m.isUsageThresholdSupported( ) && m.getType( ) == MemoryType.HEAP )
				{
					memoryPool = m;
					break;
				}
			}
			
			if(memoryPool == null)
				throw new IllegalStateException("Unable to find a MemoryPoolMXBean supporting thresholds.");
		}
		return memoryPool;
	}

}
