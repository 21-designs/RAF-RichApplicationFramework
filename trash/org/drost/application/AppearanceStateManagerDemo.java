package org.drost.application;

import javax.swing.JFrame;

import org.drost.application.ext.state.AppearanceStateManager;

public class AppearanceStateManagerDemo
{

	/*
	 * 
	 */
	public static void main(String[] args)
	{
		Application.launch( "AppearanceDemo" );
		Application.get( ).lockInstance( true );
		
		LocalStorage storage = Application.get( ).getLocalStorage( );
		
		JFrame frame = createFrame();

		AppearanceStateManager asm = new AppearanceStateManager(storage);
		asm.restore( frame );
		
		frame.setVisible( true );
		
		Runtime.getRuntime( ).addShutdownHook( new Thread() {
			
			public void run()
			{
				asm.store( frame );
			}
		});		
	}
	
	
	
	private static JFrame createFrame()
	{
		JFrame frame = new JFrame("Demo");
		frame.setDefaultCloseOperation( 3 );
		
		return frame;
	}
}
