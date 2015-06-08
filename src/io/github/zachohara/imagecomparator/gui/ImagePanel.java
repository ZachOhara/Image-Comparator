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

	// TODO CLEAN THIS CLASS

	private JPanel infoPanel;
	private JLabel filename;
	private JLabel dimension;
	private Image image;
	private JComponent componentImage;
	private Dimension imageSize;

	private static final int FONT_SIZE = 17;
	private static final Font INFO_FONT = new Font("Info panel font", Font.PLAIN, FONT_SIZE);
	private static final int INFO_PANEL_HEIGHT = 50;
	private static final int IMAGE_BORDER = 20;

	private static final long serialVersionUID = 1L;

	public ImagePanel(Window owner) {
		super();
		this.setLayout(null);
		this.formatInfoPanel();
	}

	public void setImage(Image i) {
		this.image = i;
		if (this.componentImage != null)
			this.remove(this.componentImage);
		this.imageSize = this.getScaledSize(i.getImage());
		this.componentImage = this.getScaledImage();
		this.filename.setText(i.getName());
		this.dimension.setText(i.getDimensionString());
		this.resizeImage();
		this.add(this.componentImage);
	}

	public void handleResize() {
		this.resizeInfoPanel();
		if (this.image != null)
			this.setImage(this.image);
	}

	private void formatInfoPanel() {
		this.infoPanel = new JPanel();
		this.infoPanel.setLayout(new BoxLayout(this.infoPanel, BoxLayout.Y_AXIS));
		this.infoPanel.setLocation(0, 0);
		this.resizeInfoPanel();
		this.filename = new JLabel("<<filename here>>"); // TODO remove the non-labels
		this.dimension = new JLabel("<<dimensions here>>");
		this.formatInfoText(this.filename);
		this.formatInfoText(this.dimension);
		this.add(this.infoPanel);
	}
	
	private void resizeInfoPanel() {
		this.infoPanel.setSize(this.getWidth(), INFO_PANEL_HEIGHT);
	}

	private void formatInfoText(JLabel panel) {
		panel.setFont(INFO_FONT);
		panel.setAlignmentX(CENTER_ALIGNMENT);
		this.infoPanel.add(panel);
	}
	
	private void resizeImage() {
		this.componentImage.setSize(this.imageSize);
		int imageAreaHeight = this.getHeight() - this.infoPanel.getHeight();
		this.componentImage.setLocation((this.getWidth() / 2) - (this.imageSize.width / 2)
				, (imageAreaHeight / 2) - (this.imageSize.height / 2));
	}
	
	private JComponent getScaledImage() {
		return new JLabel(new ImageIcon(scale(this.image.getImage(), this.imageSize)));
	}
	
	private Dimension getScaledSize(BufferedImage b) {
		double height = b.getHeight();
		double width = b.getWidth();
		double scale = Math.max(height / (this.getHeight() - this.infoPanel.getHeight() - IMAGE_BORDER),
				width / (this.getWidth() - IMAGE_BORDER));
		if (scale > 1) {
			height /= scale;
			width /= scale;
		}
		return new Dimension((int) width, (int) height);
	}

	private static BufferedImage scale(BufferedImage bImg, Dimension dim) {
		int newW = dim.width;
		int newH = dim.height;
		int oldW = bImg.getWidth();
		int oldH = bImg.getHeight();
		BufferedImage db = new BufferedImage(newW, newH, bImg.getType());
		Graphics2D g = db.createGraphics();
		g.drawImage(bImg, 0, 0, newW, newH, 0, 0, oldW, oldH, null);
		g.dispose();
		return db;
	}

}
