package org.drost.application.interfaces;

/**
 * This is a marker interface used to group related files stored to the
 * application associated storage. Declare classes as data that gets located
 * similar to other data files since it is saved to the storage.
 * 
 * @author kimschorat
 *
 */
public interface SaveAsData
{
	/**
	 * The associated directory path.
	 */
	public final String path = "data/";
}
