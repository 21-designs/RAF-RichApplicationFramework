package org.drost.application.ext.state;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;

public class WindowState extends AbstractState<Window>
{
	private Rectangle bounds = null;
	private int frameState = Frame.NORMAL;
	
	public static boolean fitToScreenSize = true;
	
	/*
	 * Append this class by a field that stores on which screen the frame 
	 * is shown (for devices using more than one screen).
	 * 
	 * private GraphicsDevice screenDevice;
	 */
	
//	/**
//	 * Creates a new window state instance with initial bounds properties.
//	 * 
//	 * @param bounds
//	 *            The bounds of the window component.
//	 */
//	public WindowState(Rectangle bounds)
//	{
//		this.bounds = bounds;
//	}
//	
//	
//	/**
//	 * Creates a new window state instance with initial bounds and frame
//	 * state properties.
//	 * 
//	 * @param bounds
//	 *            The bounds of the window component.
//	 * @param frameState
//	 *            The current state of this component while it is of type
//	 *            {@code Frame}
//	 * 
//	 * @see Frame#getExtendedState()
//	 */
//	public WindowState(Rectangle bounds, int frameState)
//	{
//		this.bounds = bounds;
//		this.frameState = frameState;
//	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public int getFrameState() {
		return frameState;
	}
	
	public void setFrameState(int frameState) {
		this.frameState = frameState;
	}


	/**
	 * Returns whether the window position should be limited to the actual
	 * screen size when restoring the window properties.
	 * 
	 * @return Returns {@code true} if the window position is limited to the
	 *         current screen size, otherwise {@code false}.
	 */
	public static boolean isLimitedToScreenSize() {
		return fitToScreenSize;
	}

	/**
	 * When set to {@code true} this limits the window bounds to the screen
	 * size. When the state of the related window is restored and the window
	 * object has been outside the current screen size, it is moved into the
	 * displayable area of the current graphics device.
	 * <p>
	 * This may helpful when the graphics device has been replaced by
	 * another one thats resolution is smaller than before.
	 * </p>
	 * 
	 * @param fitToScreenSize
	 *            {@code true} to limit the windows position to the current
	 *            screen size, otherwise {@code false}.
	 */
	public static void setLimitedToScreenSize(boolean fitToScreenSize) {
		WindowState.fitToScreenSize = fitToScreenSize;
	}

	@Override
	public boolean hasState( )
	{
		return (bounds != null);
	}

	@Override
	public void setState( Window o )
	{
		this.bounds = new Rectangle(o.getX(), o.getY(), o.getWidth(), o.getHeight());
		this.frameState = (o instanceof Frame) ? ((Frame) o).getExtendedState() : Frame.NORMAL;
	}

	@Override
	public void getState( Window c )
	{
		if(c == null)
		{
			throw new IllegalArgumentException("The argument cannot be null.");
		}
		
		// The following sets the previous window bounds considering a
		// change of the screen device during two launches of the program.
		if(c instanceof Window )
		{
			if(getBounds() != null)
			{
				if(WindowState.isLimitedToScreenSize())
				{
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					double width = screenSize.getWidth();
					double height = screenSize.getHeight();
					
					Rectangle bounds = getBounds();
					
					// While the previous window bounds were outside the current screen device
					if(bounds.x + bounds.width < 0 || bounds.x > width || bounds.y + bounds.height < 0 || bounds.y > height)
					{
						// Places the window in respect to any OS related menu bars 
						c.setBounds( new Rectangle(50, 50, (int) (width-100), (int) (height-100)) );
					}
					
					// While a part of the window has been outside the current screen.
					double cutoffX = bounds.x + bounds.width - width;
					double cutoffY = bounds.y + bounds.height - height;
					
					c.setBounds( new Rectangle(
							(bounds.x < 0) ? 0 : bounds.x, 
							(bounds.y < 0) ? 0 : bounds.y, 
							(cutoffX > 0) ? (int) (bounds.width - cutoffX) : bounds.width, 
							(cutoffY > 0) ? (int) (bounds.height - cutoffY) : bounds.height) 
							);
				}
				else
				{
					c.setBounds(getBounds());
				}
			}
			
							
			
			if( c instanceof Frame )
				((Frame) c).setExtendedState( getFrameState() );
		}
		else
		{
			throw new IllegalArgumentException("The argument has the wrong class type " + c.getClass( ).getName( ) + " but expected " + Window.class.getName( ));
		}
	}

}
