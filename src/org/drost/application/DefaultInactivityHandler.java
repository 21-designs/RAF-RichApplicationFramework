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
