package org.drost.application.session;

import java.io.Serializable;

public interface PersistenceManager<T> extends Serializable
{
	public void store(T e, String filename);
	
	public T restore(String filename);
}
