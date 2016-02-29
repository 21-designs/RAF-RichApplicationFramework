/*
 * This file is part of the application library that simplifies common 
 * initialization and helps setting up any java program.
 * 
 * Copyright (C) 2016 Yannick Drost, all rights reserved.
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.drost.application;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import org.drost.utils.ScreenUtils;

/**
 * Adds a functionality to specified {@code Window} object to snap to the screen
 * edges when moved close to. This class extends {@code ComponentAdapter} and
 * checks the current position of the associated component against the screen
 * edges in respect to the offset. The offset is modifiable and describes the
 * distance to every screen edge where within the components location will be
 * set. The component in this case should be an extending class of window or a
 * {@code Window} instance itself.
 * 
 * @author kimschorat
 * @since 1.0
 */
public class EdgeSnapAdapter extends ComponentAdapter
{
	private int pixelOffset;
	
	private boolean snapped;
	
	/**
	 * Creates an instance with the default pixel offset.
	 */
	public EdgeSnapAdapter()
	{
		init();
	}
	
	
	/**
	 * Creates an instance with an initial pixel offset.
	 * 
	 * @param pixelOffset
	 *            The offset to the edges.
	 */
	public EdgeSnapAdapter(int pixelOffset)
	{
		this.pixelOffset = pixelOffset;
		
		init();
	}
	
	
	/**
	 * Initializes all class fields. 
	 */
	private void init()
	{
		snapped = false;
		pixelOffset = 25;
	}
	
	
	/**
	 * Checks whether this associated component is already snapped to the screen
	 * edges.
	 */
	@Override
	public void componentShown(ComponentEvent e)
	{
		Component c = e.getComponent();
		
		int oldX = c.getX();
		int oldY = c.getY();
		
		Window window = new Window((Frame) c);
		Rectangle safeBounds = ScreenUtils.getSafeBounds(window);
		
		int x = safeBounds.x;
		int y = safeBounds.y;
		
		int width = (int) safeBounds.getWidth();
		int height = (int) safeBounds.getHeight();
		
		if(oldX > -pixelOffset && oldX < pixelOffset+x)
		{
			snapped = true;
		}
		if(oldX + c.getWidth() > width-pixelOffset + x && oldX + c.getWidth() < width+pixelOffset + x )
		{
			snapped = true;
		}
		
		if(oldY > -pixelOffset && oldY < pixelOffset+y)
		{
			snapped = true;
		}
		if(oldY + c.getHeight()> height-pixelOffset + y  && oldY + c.getHeight() < height+pixelOffset + y )
		{
			snapped = true;
		}
	}
	
	
	/**
	 * Checks the current position against all screen edges. If the window or
	 * component is within the offset area it snaps to the nearby edge. This
	 * method also considers the system tray size.
	 * 
	 * @see #setPixelOffset(int)
	 */
	@Override
	public void componentMoved(ComponentEvent e)
	{
		// While the associated component is not of type component.
		if(e == null)
			return;
		
		snapped = false;
		
		Component c = e.getComponent();
		
		int oldX = c.getX();
		int oldY = c.getY();
		
		int newX = oldX;
		int newY = oldY;

		Window window = new Window((Frame) c);
		Rectangle safeBounds = ScreenUtils.getSafeBounds(window);
		
		int x = safeBounds.x;
		int y = safeBounds.y;
		
		int width = (int) safeBounds.getWidth();
		int height = (int) safeBounds.getHeight();
		
		if(oldX > -pixelOffset && oldX < pixelOffset+x)
		{
			newX = x;
			snapped = true;
		}
		if(oldX + c.getWidth() > width-pixelOffset + x && oldX + c.getWidth() < width+pixelOffset + x )
		{
			newX = width - c.getWidth() + x;
			snapped = true;
		}
		
		if(oldY > -pixelOffset && oldY < pixelOffset+y)
		{
			newY = y;
			snapped = true;
		}
		if(oldY + c.getHeight()> height-pixelOffset + y  && oldY + c.getHeight() < height+pixelOffset + y )
		{
			newY = height - c.getHeight() + y;
			snapped = true;
		}
			
		c.setLocation(newX, newY);
	}
	
	
	/**
	 * Returns whether this associated window or component is snapped to the
	 * screen edges. Initial this value is set to {@code false} but is checked
	 * whenever the associated window is shown.
	 * 
	 * @return {@code true} while the window is snapped to the window edges,
	 *         otherwise {@code false}.
	 */
	public boolean isSnapped()
	{
		return snapped;
	}
	
	
	/**
	 * Returns the offset to the edge to force the window to snap to the related
	 * screen edge. The default value is {@code 25} pixels. This value also
	 * considers the tray size.
	 * 
	 * @return The offset in pixels.
	 */
	public int getPixelOffset() {
		return pixelOffset;
	}

	
	/**
	 * Sets the offset to the edge to force the window to snap to the related
	 * screen edge. The default value is {@code 25} pixels. This value also
	 * considers the tray size.
	 * 
	 * @param pixelOffset The offset in pixels.
	 */
	public void setPixelOffset(int pixelOffset) {
		this.pixelOffset = pixelOffset;
	}
}
