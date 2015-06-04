package io.github.zachohara.imagecomparator.gui;

import io.github.zachohara.imagecomparator.image.Image;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	
	private JPanel infoPanel;
	private JLabel filename;
	private JLabel dimension;
	private JComponent componentImage;
	private Component rigidArea;
	
	
	private static final int FONT_SIZE = 17;
	private static final Font INFO_FONT = new Font("Info panel font", Font.PLAIN, FONT_SIZE);
	
	private static final long serialVersionUID = 1L;

	/*
	 * 
		this.leftPanel = new JPanel();
		this.rightPanel = new JPanel();
		this.handleWindowResize();
		this.leftPanel.setLayout(new BorderLayout());
		this.rightPanel.setLayout(new BorderLayout());
		this.leftPanel.setBackground(Color.BLUE);
		this.rightPanel.setBackground(Color.RED);
		this.window.add("West", this.leftPanel);
		this.window.add("East", this.rightPanel);
	 */
	
	public ImagePanel() {
		super();
		this.setLayout(new BorderLayout());
		this.formatInfoPanel();
	}
	
	public ImagePanel(Color c) {
		this();
		JPanel p = new JPanel();
		p.setBackground(c);
		this.add(p);
		this.repaint();
	}
	
	public void setImage(Image i) {
		this.componentImage = getComponent(i.getImage());
		this.filename.setText(i.getName());
		this.dimension.setText(i.getDimensionString());
		this.add(this.componentImage);
		//this.repaint();
	}
	
	public void resize(int width) {
		if (this.rigidArea != null)
			this.remove(this.rigidArea);
		this.rigidArea = Box.createHorizontalStrut(width);
		this.add(this.rigidArea);
		this.repaint();
	}
	
	private void formatInfoPanel() {
		this.infoPanel = new JPanel();
		this.infoPanel.setLayout(new BoxLayout(this.infoPanel, BoxLayout.Y_AXIS));
		this.filename = new JLabel("<<filename here>>");
		this.dimension = new JLabel("<<dimensions here>>");
		this.formatInfoText(this.filename);
		this.formatInfoText(this.dimension);
		this.add("North", this.infoPanel);
	}
	
	private void formatInfoText(JLabel panel) {
		panel.setFont(INFO_FONT);
		panel.setAlignmentX(CENTER_ALIGNMENT);
		this.infoPanel.add(panel);
	}
	
	public static JComponent getComponent(BufferedImage bi) {
		return new JLabel(new ImageIcon(bi));
	}

	public static void main(String[] args) {
		JFrame win = new JFrame();
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setSize(500, 500);
		win.setLocationRelativeTo(null);
		ImagePanel p = new ImagePanel();
		win.add(p);
		win.setVisible(true);
	}

}
