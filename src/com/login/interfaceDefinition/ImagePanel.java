package com.login.interfaceDefinition;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4651617693004709644L;
	private Image img;
	
	public ImagePanel(String img){
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img) {
		// TODO Auto-generated constructor stub
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(img, 0,0, null);
	}
	
	public Image changeImage(String img){
		Image image = new ImageIcon(img).getImage();
		return image;
	}
	
}
