package org.drost.application;

import org.drost.application.StateChangeController.Statement;
import org.drost.application.StateChangeController.StatementHandler;

/**
 * Handles a certain state when the user seems to be inactive. This is
 * always a suggestion and is done by listening to the mouse or keyboard
 * inputs.
 * 
 * @author kimschorat
 * @since 1.0
 * 
 * @see StatementHandler
 * @see Application
 */
@SuppressWarnings("serial")
public abstract class AbstractInactivityHandler implements StatementHandler<Void>
{
	/**
	 * Prints out the owning thread that caused this exception and additional
	 * the stack trace of the associated exception.
	 * 
	 * @param info
	 *            The notification that informs this handler.
	 */
	@Override
	public abstract void handle(Statement<Void> info);
	
}