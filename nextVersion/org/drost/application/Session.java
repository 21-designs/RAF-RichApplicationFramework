package org.drost.application;

public class Session 
{
	protected int usercount = 0;
	
	
	
	public void start()
	{
		usercount++;
	}
	
	
	public void end()
	{
		--usercount;
	}
}
