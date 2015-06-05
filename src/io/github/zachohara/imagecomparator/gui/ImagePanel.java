/*
 *  Copyright (C) 2015 Zach Ohara
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.zachohara.imagecomparator.gui;

import io.github.zachohara.imagecomparator.image.Image;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	//private int width;

	private JPanel infoPanel;
	private JLabel filename;
	private JLabel dimension;
	private Image image;
	private JComponent componentImage;
//	private Component rigidArea;

	private static final int FONT_SIZE = 17;
	private static final Font INFO_FONT = new Font("Info panel font", Font.PLAIN, FONT_SIZE);

	private static final long serialVersionUID = 1L;

	public ImagePanel(Window owner) {
		super();
		this.setLayout(new BorderLayout());
		this.formatInfoPanel();
	}

	public void setImage(Image i) {
//		if (this.componentImage != null)
//			this.remove(this.componentImage);
		this.image = i;
		JComponent newImage = getScaledImage(i.getImage());
		this.filename.setText(i.getName());
		this.dimension.setText(i.getDimensionString());
		newImage.setAlignmentX(CENTER_ALIGNMENT);
		newImage.setAlignmentY(CENTER_ALIGNMENT);
		this.add(newImage);
		if (this.componentImage != null)
			this.remove(this.componentImage);
		this.componentImage = newImage;
	}

	public void handleResize(int width) {
//		if (this.rigidArea != null)
//			this.remove(this.rigidArea);
//		this.rigidArea = Box.createHorizontalStrut(width);
		this.setPreferredSize(new Dimension(width, this.getHeight()));
//		this.add(this.rigidArea);
		if (this.image != null)
			this.setImage(this.image);
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

	private JComponent getScaledImage(BufferedImage b) {
		double height = b.getHeight();
		double width = b.getWidth();
		double scale = Math.max(height / (this.getHeight() - this.infoPanel.getHeight()),
				width / this.getWidth());
		if (scale > 1) {
			height /= scale;
			width /= scale;
		}
		return new JLabel(new ImageIcon(scale(b, (int) width, (int) height)));
	}

	private static BufferedImage scale(BufferedImage b, int newW, int newH) {
		int oldW = b.getWidth();
		int oldH = b.getHeight();
		BufferedImage db = new BufferedImage(newW, newH, b.getType());
		Graphics2D g = db.createGraphics();
		g.drawImage(b, 0, 0, newW, newH, 0, 0, oldW, oldH, null);
		g.dispose();
		return db;
	}

}
