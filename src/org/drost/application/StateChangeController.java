package org.drost.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.Timer;

public class StateChangeController
{
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
	public static class Statement<T> implements Serializable
	{
		private final T soure;
		private final Thread owner;
		private final long timestamp;
		
		public Statement(T source, Thread owner)
		{
			this.soure = source;
			this.owner = owner;
			timestamp = System.currentTimeMillis();
		}
		
		
		public Statement(T source, Thread owner, long when)
		{
			this.soure = source;
			this.owner = owner;
			timestamp = when;
		}

		public T getSoure() {
			return soure;
		}

		public Thread getOwner() {
			return owner;
		}
		
		public long getTimestamp() {
			return timestamp;
		}
	}
	
	
	
	/**
	 * This interface provides one single method that each implementing class
	 * type should implement.
	 * 
	 * @author kimschorat
	 *
	 * @param <T>
	 *            The generic source type of the {@link Statement}.
	 */
	public interface StatementHandler<T> extends Serializable
	{
		/**
		 * Handles this invoking notification object specified by every
		 * implementing subclass.
		 * 
		 * @param info
		 *            The notification object with a predefined type parameter.
		 */
		public void handle(final Statement<T> info);
	}
	
	
	
	AbstractExceptionHandler exceptionHandler = null;
	
	AbstractInactivityHandler inactiveHandler = null;
	
	int inactiveIntervaleMinutes = 60;
	
	Timer inactiveTimer = new Timer(0, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(inactiveHandler != null)
			{
				inactive = true;
				inactiveHandler.handle(new Statement<Void>(null, Thread.currentThread()));
			}
		}
	});
	
	/**
	 * Indicates that the user is inactive. This is determined by checking the
	 * user inputs.
	 */
	protected volatile boolean inactive = false;
	

	
	/**
	 * Returns the applications exception handler to
	 * 
	 * @return The exception handler.
	 */
	public AbstractExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}


	/**
	 * Sets a new handler for all uncaught exception occuring in this
	 * application.
	 * 
	 * @param handler
	 *            The handler for uncaught exceptions.
	 */
	public void setExceptionHandler(AbstractExceptionHandler handler) {
		exceptionHandler = handler;
	}


	/**
	 * Sets the handler for that case the application/ the user gets inactive.
	 * 
	 * @return The inactive handler.
	 */
	public AbstractInactivityHandler getInactivityHandler() {
		return inactiveHandler;
	}


	/**
	 * Sets a new handler for that case the application/ the user gets inactive.
	 * 
	 * @param handler
	 *            The handler for inactivity.
	 */
	public void setInactivityHandler(AbstractInactivityHandler handler) {
		inactiveHandler = handler;
	}
	
	
	/**
	 * Sets the delay after a inactivity is suggested.
	 * 
	 * @param minutes
	 *            The delay in minutes.
	 */
	public synchronized void setInactiveIntervalMinutes(int minutes)
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
	 * @see #setInactiveIntervalMinutes(int)
	 */
	public boolean isInactive()
	{
		return inactive;
	}

	
}
