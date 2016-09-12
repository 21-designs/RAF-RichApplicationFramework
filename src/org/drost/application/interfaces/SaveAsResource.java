package org.drost.application.interfaces;

/**
 * This is a marker interface used to group related files stored to the
 * application associated storage. Declare classes as a resource that gets
 * located similar to other resource files since it is saved to the storage.
 * 
 * @author kimschorat
 *
 */
public interface SaveAsResource
{
	/**
	 * The associated directory path.
	 */
	public final String path = "resource/";
}
