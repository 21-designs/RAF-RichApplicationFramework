package org.drost.application;

import java.awt.Window;

import javax.swing.JWindow;

public class ViewModeManager 
{
	
	private final EdgeSnapAdapter edgeSnapAdapter;
	
	private boolean snapEnabled = false;
	
	public ViewModeManager()
	{
		edgeSnapAdapter = new EdgeSnapAdapter();
	}



	/**
	 * Enables or disables the edge snapping functionality by adding or removing
	 * the adapter from the main view. This method does nothing while no main
	 * view is set or the main view is {@code null}.
	 * 
	 * @param enable
	 *            Enables or disables the snapping functionality.
	 */
	public void enableEdgeSnap(Window window, boolean enable)
	{
		if(window == null)
			return;
		
		if(enable)
		{
			if(!snapEnabled)
			{
				window.addComponentListener(edgeSnapAdapter);
				snapEnabled = true;
			}
		}
		else
		{
			if(snapEnabled)
			{
				window.removeComponentListener(edgeSnapAdapter);
				snapEnabled = false;
			}
		}
	}
	
	
	/**
	 * This field is associated to the main view object. This adapter can be
	 * enabled or disabled and is initially disabled.
	 * 
	 * @return The edge snapping adapter.
	 * 
	 * @see #getMainView()
	 */
	public EdgeSnapAdapter getEdgeSnapAdapter() {
		return edgeSnapAdapter;
	}


	public boolean isEdgeSnapEnabled()
	{
		return snapEnabled;
	}
	
	
	public void windowShake(JWindow view, long millis)
	{
		
	}
	
	
	public void windowFadeOut(JWindow view, long millis)
	{
		
	}
	
	
	public void windowFadeIn(JWindow view, long millis)
	{
		
	}

}
