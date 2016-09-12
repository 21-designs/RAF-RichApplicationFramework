package org.drost.application.interfaces;

/**
 * This is a marker interface used to group related files stored to the
 * application associated storage. Declare classes as a configuration that
 * gets located similar to other configuration files since it is saved to
 * the storage.
 * 
 * @author kimschorat
 *
 */
public interface SaveAsConfiguration
{
	/**
	 * The associated directory path.
	 */
	public final String path = "config/";
}
