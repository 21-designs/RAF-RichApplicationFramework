package org.drost.application.listeners;

import java.io.Serializable;
import java.util.EventObject;

@SuppressWarnings( "serial" )
public class ApplicationEvent extends EventObject implements Serializable, Comparable<ApplicationEvent>
{	
	public static final int APPLICATION_LAUNCHED = 0x01;
	
	public static final int APPLICATION_CLOSED = 0x02;
	
	public static final int APPLICATION_UPDATED = 0x04;
	
	public static final int APPLICATION_LOCKED = 0x08;
	
	public static final int APPLICATION_UNLOCKED = 0x10;
	
	public static final int APPLICATION_RESTARTED = 0x20;
	
	/**
	 * The time when the event has been created in milliseconds.
	 */
	final long when;
	
	/**
	 * The event type.
	 */
	final int type;
	
	/**
	 * The application instance since the application has been launched, otherwise {@code null}.
	 */
//	final Application application;
	
	public ApplicationEvent(Object source, int type)
	{
		this(source, type, System.currentTimeMillis( ));
	}
	
	public ApplicationEvent(Object source, int type, long when)
	{
		super(source);
		
		this.type = type;
		
		this.when = when;
//		application = Application.isApplication( ) ? Application.get( ) : null;
	}
	
	

	public int getType( )
	{
		return type;
	}

	public long getWhen( )
	{
		return when;
	}

//	public Application getApplication( )
//	{
//		return application;
//	}

	/**
	 * Compares two events depending on their instantiation time stamp.
	 */
	@Override
	public int compareTo( ApplicationEvent e )
	{
		if(e.getWhen( ) > when)
			return 1;
		else if(e.getWhen( ) < when)
			return -1;
		else
			return 0;		
	}
	
}
