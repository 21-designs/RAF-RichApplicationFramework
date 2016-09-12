package org.drost.application.ext.state;

import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

import org.drost.application.Application;

public class ApplicationState extends AbstractState<Application>
{
	
//	public ApplicationState( String versionID )
//	{
//		super( versionID );
//	}

	@Override
	public boolean hasState( )
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setState( Application state )
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getState( Application o)
	{
		// TODO Auto-generated method stub
	}
	
	
}
