/* Copyright (C) 2015 Zach Ohara
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.zachohara.imagecomparator.gui;

import io.github.zachohara.imagecomparator.image.Image;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * An {@code ImagePanel} is a {@code JPanel} that displays an image, and the name and
 * dimensions of that image. There are two {@code ImagePanel}s in the selection screen in
 * the main application {@code Window}.
 *
 * @author Zach Ohara
 */
public class ImagePanel extends JPanel {

	/**
	 * The {@code JPanel} that displays the information about the image in this panel,
	 * including dimensions and filename.
	 */
	private JPanel infoPanel;

	/**
	 * The filename of the image in this panel.
	 */
	private JLabel filename;

	/**
	 * The dimensions of the image in this panel.
	 */
	private JLabel dimension;

	/**
	 * The image in this panel.
	 */
	private Image image;

	/**
	 * The image in this panel, represented in a form that can be directly displayed in a
	 * {@code JPanel}.
	 */
	private JComponent componentImage;

	/**
	 * The size of this image.
	 */
	private Dimension imageSize;

	/**
	 * The font size of the image information (dimensions and filename).
	 */
	private static final int FONT_SIZE = 17;

	/**
	 * The {@code Font} for the image information (dimensions and filename).
	 */
	private static final Font INFO_FONT = new Font("Info panel font", Font.PLAIN, ImagePanel.FONT_SIZE);

	/**
	 * The height of the information panel.
	 */
	private static final int INFO_PANEL_HEIGHT = 50;

	/**
	 * The amount of pixels to use as a border between the edge of the image and the space
	 * available to this panel.
	 */
	private static final int IMAGE_BORDER = 20;

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code ImagePanel} without any image.
	 */
	public ImagePanel() {
		super();
		this.setLayout(null);
		this.formatInfoPanel();
	}

	/**
	 * Sets the image that should be displayed in this panel. This method will also update
	 * the dimension and filename information in this panel.
	 *
	 * @param i the new {@code Image} for this panel.
	 */
	public void setImage(Image i) {
		this.image = i;
		if (this.componentImage != null) {
			this.remove(this.componentImage);
		}
		this.imageSize = this.getScaledSize(i.getImage());
		this.componentImage = this.getScaledImage();
		this.filename.setText(i.getName());
		this.dimension.setText(i.getDimensionString());
		this.resizeImage();
		this.add(this.componentImage);
	}

	/**
	 * Resizes this panel based on the current size of the containing window.
	 */
	public void handleResize() {
		this.resizeInfoPanel();
		if (this.image != null) {
			this.setImage(this.image);
		}
	}

	/**
	 * Initializes the information panel.
	 */
	private void formatInfoPanel() {
		this.infoPanel = new JPanel();
		this.infoPanel.setLayout(new BoxLayout(this.infoPanel, BoxLayout.Y_AXIS));
		this.infoPanel.setLocation(0, 0);
		this.resizeInfoPanel();
		this.filename = new JLabel("-");
		this.dimension = new JLabel("-");
		this.formatInfoText(this.filename);
		this.formatInfoText(this.dimension);
		this.add(this.infoPanel);
	}

	/**
	 * Resizes the information panel based on the current size of the containing window.
	 */
	private void resizeInfoPanel() {
		this.infoPanel.setSize(this.getWidth(), ImagePanel.INFO_PANEL_HEIGHT);
	}

	/**
	 * Formats the text in the information panel.
	 *
	 * @param panel the panel to format.
	 */
	private void formatInfoText(JLabel panel) {
		panel.setFont(ImagePanel.INFO_FONT);
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.infoPanel.add(panel);
	}

	/**
	 * Resizes the image based on the current size of the containing window.
	 */
	private void resizeImage() {
		this.componentImage.setSize(this.imageSize);
		int imageAreaHeight = this.getHeight() - this.infoPanel.getHeight();
		this.componentImage.setLocation((this.getWidth() / 2) - (this.imageSize.width / 2),
				(imageAreaHeight / 2) - (this.imageSize.height / 2));
	}

	/**
	 * Converts the current {@code Image} object into a {@code JComponent} that can be
	 * added to the window. This method will also resize the image so that it fits within
	 * the available space.
	 *
	 * @return a {@code JComponent} object that can be added to the window.
	 */
	private JComponent getScaledImage() {
		return new JLabel(new ImageIcon(ImagePanel.scale(this.image.getImage(), this.imageSize)));
	}

	/**
	 * Gets the size that the given image should be scaled to so that both its width and
	 * height fit inside this panel, and so that the ratio between the width and height of
	 * the image remains constant.
	 *
	 * @param image the image to scale.
	 * @return the {@code Dimension} of the ideal scaled size of the given image.
	 */
	private Dimension getScaledSize(BufferedImage image) {
		double height = image.getHeight();
		double width = image.getWidth();
		double scale =
				Math.max(height / (this.getHeight() - this.infoPanel.getHeight() - ImagePanel.IMAGE_BORDER),
						width / (this.getWidth() - ImagePanel.IMAGE_BORDER));
		if (scale > 1) {
			height /= scale;
			width /= scale;
		}
		return new Dimension((int) width, (int) height);
	}

	/**
	 * Scales the given {@code BufferedImage} to the size of the given {@code Dimension}.
	 *
	 * @param image the image to be scaled.
	 * @param dim the size to scale the image to.
	 * @return the scaled image.
	 */
	private static BufferedImage scale(BufferedImage image, Dimension dim) {
		int newW = dim.width;
		int newH = dim.height;
		int oldW = image.getWidth();
		int oldH = image.getHeight();
		BufferedImage db = new BufferedImage(newW, newH, image.getType());
		Graphics2D g = db.createGraphics();
		g.drawImage(image, 0, 0, newW, newH, 0, 0, oldW, oldH, null);
		g.dispose();
		return db;
	}

}
