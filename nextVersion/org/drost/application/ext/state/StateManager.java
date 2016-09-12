package org.drost.application.ext.state;

import java.io.Serializable;

/**
 * Provides methods to store and restore any object states.
 * @author kimschorat
 *
 * @param <T> 
 */
public interface StateManager<T extends Serializable>
{
	/**
	 * Store a state of the specified object of type {@code T}.
	 * @param e
	 */
	public void store( T e );

	/**
	 * Restore a state of the specified object of type {@code T}.
	 * @param e
	 */
	public void restore( T e );
}
