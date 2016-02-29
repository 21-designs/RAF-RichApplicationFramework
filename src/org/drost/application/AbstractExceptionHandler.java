package org.drost.application;

import org.drost.application.StateChangeController.Statement;
import org.drost.application.StateChangeController.StatementHandler;

/**
 * Handles all uncaught exceptions application wide. Whenever an exception
 * is thrown because any unexpected state change occurs, this handler will
 * get informed by invoking its {@link #handle(Statement)} method. There
 * is no need to call this method directly.
 * 
 * <p>
 * This class implements the {@link StatementHandler} interface. The
 * generic class parameter is defined by the {@link Throwable} class type.
 * </p>
 * 
 * @author kimschorat
 * @since 1.0
 * 
 * @see StatementHandler
 */
@SuppressWarnings("serial")
public abstract class AbstractExceptionHandler implements StatementHandler<Throwable>
{
	/**
	 * This method is {@code abstract} and does not implement a method body.
	 * 
	 * @param info
	 *            The notification that informs this handler.
	 */
	@Override
	public abstract void handle(Statement<Throwable> info);
}
