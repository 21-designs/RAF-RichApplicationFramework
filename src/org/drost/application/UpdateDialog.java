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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

import javax.swing.JScrollPane;

/**
 * 
 * @author kimschorat
 * @version 1.0
 *
 */
class UpdateDialog
{
	JDialog window;
		
	private UpdateManager manager = null;
	
	private boolean restartComputer = false;
	
	@SuppressWarnings("unused")
	private Icon icon = null;

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public UpdateDialog(UpdateManager manager) throws IOException 
	{
		this.manager = manager;
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException 
	{
		window = new JDialog();
		window.setTitle("Software Update");
		window.setBounds(100, 100, 447, 377);
		window.setLocationRelativeTo(null);
//		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			window.getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Remind Me Later");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						UpdateDialog.this.window.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("Install Update");
				okButton.requestFocus();
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						update();
					}
				});
				okButton.setActionCommand("OK");
				okButton.requestFocus();
				buttonPane.add(okButton);
				window.getRootPane().setDefaultButton(okButton);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(10, 10, 10, 10));
			window.getContentPane().add(panel, BorderLayout.NORTH);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblIcon = new JLabel();
				// TODO create resource directory
//				String resourcePath = ApplicationUpdatePane.class
//						.getResource("downloads-icon.png")
//						.getPath();
//				icon = new ImageIcon(resourcePath);
//				lblIcon.setIcon(icon);
				panel.add(lblIcon, BorderLayout.WEST);
				lblIcon.setPreferredSize(new Dimension(64, 64));
				lblIcon.setMinimumSize(new Dimension(64, 64));
			}
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1, BorderLayout.CENTER);
				panel_1.setLayout(new BorderLayout(0, 0));
				{
					JLabel lblHeadline = new JLabel("New software update is available.");
					lblHeadline.setFont(new Font("Lucida Grande", Font.BOLD, 13));
					lblHeadline.setBorder(new EmptyBorder(10, 10, 5, 10));
					panel_1.add(lblHeadline, BorderLayout.NORTH);
				}
				{
					JLabel lblDescription = new JLabel("If you don't want to install now, you can check for updates later.");
					lblDescription.setBorder(new EmptyBorder(5, 10, 5, 10));
					lblDescription.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
					panel_1.add(lblDescription, BorderLayout.CENTER);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(5, 84, 10, 10));
			window.getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
//				JScrollPane scrollPane = new JScrollPane();
//				panel.add(scrollPane, BorderLayout.CENTER);
//				{
//					JTextArea textArea = new JTextArea();
//					textArea.setLineWrap(true);
//					textArea.setEditable(false);
//					textArea.setText(releaseNotes);
//					textArea.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
//					scrollPane.setViewportView(textArea);					
//				}
//				{
//					JLabel lblReleaseNotes_1 = new JLabel("Release Notes:");
//					lblReleaseNotes_1.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
//					scrollPane.setColumnHeaderView(lblReleaseNotes_1);
//				}
				
				JEditorPane infoPane = new JEditorPane();
		        infoPane.setContentType("text/html");
		        infoPane.setEditable(false);
		        infoPane.setText(manager.getReleaseNotes());

		        JScrollPane scp = new JScrollPane();
		        scp.setViewportView(infoPane);
		        panel.add(scp, BorderLayout.CENTER);
			}
			{
				if(restartComputer)
				{
					JLabel lblRestartComputer = new JLabel("You must restart your computer after installation.");
					lblRestartComputer.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
					panel.add(lblRestartComputer, BorderLayout.SOUTH);
				}
			}
		}
		
//		frame.setVisible(true);
	}
	
	
	
	/**
	 * VERY IMPORTANT: For all this to work when you are ready to package your
	 * java program to distribute make sure that you include the folder updater
	 * with the update.jar in it for this to properly work! So the final paths
	 * should look like this:
	 * 
	 * <pre>
	 * ../my_app.jar 
	 * ../updater/update.jar
	 * </pre>
	 */
	public void update()
    {
		manager.update(false);
    }
	
	/**
	 * Shows the updater window frame.
	 * @param visible
	 */
	public void setVisible(boolean visible)
	{
		if(window != null) window.setVisible(visible);
	}
	
	
	/**
	 * Sets the displayed icon on the update window. Note that the icon is
	 * scaled to a size of 64x64.
	 * 
	 * @param icon
	 */
	public void setIcon(Icon icon)
	{
		Image image = iconToImage(icon);
		image = image.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
		this.icon = new ImageIcon(image);
	}
	
	
	private Image iconToImage(Icon icon)
	{
		if(icon == null) return null;
		
		if (icon instanceof ImageIcon) {
			return ((ImageIcon) icon).getImage();
		} else {
			int w = icon.getIconWidth();
			int h = icon.getIconHeight();
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gd.getDefaultConfiguration();
			BufferedImage image = gc.createCompatibleImage(w, h);
			Graphics2D g = image.createGraphics();
			icon.paintIcon(null, g, 0, 0);
			g.dispose();
			return image;
		}
	}

}
