package org.drost.application;

import org.drost.application.StateChangeController.Statement;

/**
 * Simply implements the abstract method {@link #handle(Statement)} as
 * declared in {@link NotificationHandler}.
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
	public void handle(Statement<Throwable> info) 
	{
		// System.err.println("An exception occurred thrown by: " + info.getOwner().toString() + "\r\n");
		info.getSoure().printStackTrace();
	}
}
