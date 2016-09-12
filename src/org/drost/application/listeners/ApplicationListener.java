package org.drost.application.listeners;

import java.util.EventListener;

public interface ApplicationListener extends EventListener
{
	public void applicationLaunched(ApplicationEvent e);
		
	public void applicationClosed(ApplicationEvent e);
	
	public void applicationUpdated(ApplicationEvent e);
	
	public void applicationLocked(ApplicationEvent e);
	
	public void applicationUnlocked(ApplicationEvent e);
	
	public void applicationRestarted(ApplicationEvent e);
}
