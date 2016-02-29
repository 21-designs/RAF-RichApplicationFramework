package org.drost.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 * 
 * @author kimschorat
 *
 */
public abstract class SplashScreen extends SwingWorker<Object, Object>
{
	private BufferedImage image = null;
		
	private JDialog dialog = null;
	
	public SplashScreen(Image splashScreen)
	{
		dialog = new JDialog();
		
		image = toBufferedImage(splashScreen);
		
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 0));
        dialog.setAlwaysOnTop(false);
        dialog.setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
        dialog.setSize(new Dimension(image.getWidth(null), image.getHeight(null)));
        dialog.setLocationRelativeTo(null);    
		
		dialog.getContentPane().setLayout(new BorderLayout());
				
		ImagePane imagePanel = new ImagePane(image);
		dialog.getContentPane().add(imagePanel, BorderLayout.CENTER);
		
		dialog.setVisible(true);
	}
	
	
	/**
	 * 
	 * @param image
	 * @return
	 */
	private BufferedImage toBufferedImage(Image image)
	{
		BufferedImage bImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = bImage.createGraphics();
		
		setRenderingHints(g2d);
		
	    g2d.drawImage(image, 0, 0, null);
	    g2d.dispose();
	    
	    return bImage;
	}
	
	
	/**
	 * 
	 * @param g
	 */
	private void setRenderingHints(Graphics g)
	{
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	
	/**
	 * 
	 * @author kimschorat
	 *
	 */
	private class ImagePane extends JPanel
	{
		private Image image = null;
		
		public ImagePane(Image image)
		{
			this.image = image;
		}
		
		@Override
		protected void paintComponent(Graphics g)
		{
			if(getWidth() != 0 && getHeight() != 0)
				image = image.getScaledInstance( getWidth(), getHeight(), Image.SCALE_SMOOTH );
			
			g.drawImage(image, 0, 0, null);
		}

		public Image getImage() {
			return image;
		}

		public void setImage(Image image) {
			this.image = image;
			repaint();
		}
	}
	
	
	/**
	 * Returns the splash window.
	 * @return
	 */
	public JDialog getDialog() {
		return dialog;
	}
	
	
	@Override
	protected void done()
	{
		dialog.dispose();
		
		onClosing();
	}
	
	/**
	 * Invoked when the <code>doInBackground</code> method has completed
	 * calculations. Override this method to implement a closing behavior.
	 */
	public abstract void onClosing();
	
	
	/**
	 * 
	 */
	public void startResourceInitiation()
	{
		this.execute();
	}
}
