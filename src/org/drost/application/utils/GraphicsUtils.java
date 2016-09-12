package org.drost.application.utils;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public final class GraphicsUtils 
{
	private static GraphicsDevice[] screenList  = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices();
	
	
	private GraphicsUtils() { }
	
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
	
	
	// Rendering hints
	
	public static void setTextAntiAliasing(Graphics g, boolean enable) 
	{
		Object obj = enable ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;

		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, obj);
	}

	
	public static void setAntiAliasing(Graphics g, boolean enable) 
	{
		Object obj = enable ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF;

		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, obj);
	}
	
	
	// Window and component related
	
	public static BufferedImage componentToImage(Component c)
	{
		BufferedImage image = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
		c.paint(image.getGraphics());
		return image;
	}
	
	
	// Screen and graphics device related
	
	public static Rectangle getSceenBounds()
	{
		return getScreenBounds(null);
	}
	
	public static Rectangle getScreenBounds(Window window)
	{
		return getGraphicsDevice(window).getDefaultConfiguration().getBounds();
	}
	
	
	public static Rectangle getSafeScreenBounds()
	{
		return getSafeScreenBounds(null);
	}
	
	
	public static Rectangle getSafeScreenBounds(Window window)
	{
		Rectangle bounds = getScreenBounds(window);
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
		Area area = new Area(getScreenBounds(window));
		area.subtract(new Area(getSafeScreenBounds(window)));
		
		return area.getBounds();
	}
	
}
