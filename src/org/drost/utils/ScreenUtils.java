package org.drost.utils;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.geom.Area;

public final class ScreenUtils 
{
	private static GraphicsDevice[] screenList  = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices();
	
	
	private ScreenUtils() { }
	
	public static GraphicsDevice[] getScreenList() {
        return screenList;
    }
	
	public static GraphicsDevice getGraphicsDevice()
	{
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	}
	
	
	public static GraphicsDevice getGraphicsDevice(Window window)
	{
		GraphicsDevice currentDevice;
		
		if(window != null)
		{
			GraphicsConfiguration config = window.getGraphicsConfiguration();
			currentDevice = config.getDevice();
		}
		else
		{
			currentDevice = getGraphicsDevice();
		}

        for(GraphicsDevice gd : getScreenList()) 
        {
            if(gd.equals(currentDevice)) 
            {
                currentDevice = gd;
                break;
            }
        }
        
        return currentDevice;
	}
	
	
	public static Rectangle getBounds()
	{
		return getBounds(null);
	}
	
	public static Rectangle getBounds(Window window)
	{
		return getGraphicsDevice(window).getDefaultConfiguration().getBounds();
	}
	
	
	public static Rectangle getSafeBounds()
	{
		return getSafeBounds(null);
	}
	
	
	public static Rectangle getSafeBounds(Window window)
	{
		Rectangle bounds = getBounds(window);
		Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsDevice(window).getDefaultConfiguration());
		
		Rectangle safeBounds = new Rectangle(bounds);
		safeBounds.x += insets.left;
		safeBounds.y += insets.top;
		safeBounds.width -= (insets.left + insets.right);
		safeBounds.height -= (insets.top + insets.bottom);
		
		return safeBounds;
	}
	
	
	public static Rectangle getTrayBounds()
	{
		return getTrayBounds(null);
	}
	
	
	public static Rectangle getTrayBounds(Window window)
	{
		Area area = new Area(getBounds(window));
		area.subtract(new Area(getSafeBounds(window)));
		
		return area.getBounds();
	}
	
}
