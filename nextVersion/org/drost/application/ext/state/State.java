package org.drost.application.ext.state;

import java.io.Serializable;

/**
 * Extends the {@code Serializable} interface. 
 * @author kimschorat
 *
 * @param <T>
 */
public interface State<T> extends Serializable
{
	/**
	 * Returns whether the state has been set.
	 * @return {@code true} if the state has been set, otherwise {@code false}.
	 */
    public boolean hasState();

    /**
     * Set the associated state.
     * @param state The state object.
     */
    public void setState(T object);
    
    /**
     * Returns the associated state object.
     * @return the state.
     */
    public void getState(T object);
	
}
