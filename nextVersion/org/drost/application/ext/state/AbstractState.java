package org.drost.application.ext.state;

public abstract class AbstractState<T> implements State<T>
{
	protected final String versionID;
	
	public AbstractState(final String versionID)
	{
		this.versionID = versionID;
	}
	
	public AbstractState( )
	{
		versionID = "" + System.currentTimeMillis( );
	}
	
	
	
	public final String getVersionID()
	{
		return versionID;
	}
}
