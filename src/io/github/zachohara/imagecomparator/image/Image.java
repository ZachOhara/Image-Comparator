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

package io.github.zachohara.imagecomparator.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * An {@code Image} object contains a {@code BufferedImage} object and a {@code File} that
 * the image was loaded from. It contains methods used for comparing two different images,
 * and other methods that are used for displaying the images in a window.
 * 
 * @author Zach Ohara
 */
public class Image {

	/**
	 * The underlying {@code BufferedImage} for this image.
	 */
	BufferedImage image;

	/**
	 * The {@code File} that this image was loaded from.
	 */
	File file;

	/**
	 * Creates an image from a given filename.
	 * 
	 * @param filename the file path of the image
	 * @throws IOException if the given file doesn't exist, is not an image, or otherwise
	 * cannot be loaded as a {@code BufferedImage}.
	 */
	public Image(File filename) throws IOException {
		this.file = filename;
		this.image = ImageIO.read(filename);
	}

	/**
	 * Returns the percent difference between the pixels of this image and of another
	 * image.
	 * 
	 * @param other the {@code Image} to compare with this image.
	 * @return the difference between the two images.
	 * @see Pixel#colorDifference(int, int)
	 */
	public double percentDifference(Image other) {
		int thisArea = this.image.getHeight() * this.image.getWidth();
		int otherArea = other.image.getHeight() * other.image.getWidth();
		BufferedImage larger;
		BufferedImage smaller;
		if (thisArea <= otherArea) {
			larger = other.image;
			smaller = this.image;
		} else {
			larger = this.image;
			smaller = other.image;
		}
		double[][] pixelDifferences = new double[smaller.getHeight()][smaller.getWidth()];
		for (int row = 0; row < smaller.getHeight(); row++) {
			for (int col = 0; col < smaller.getWidth(); col++) {
				pixelDifferences[row][col] = getPixelDifference(smaller, larger, row, col);
			}
		}
		return getAverage(pixelDifferences);
	}

	/**
	 * Returns the color difference between two pixels after scaling the coordinates
	 * relative to the rest of the image.
	 * 
	 * @param fromImage the image for coordinates to be scaled from.
	 * @param toImage the image for coordinates to be scaled to.
	 * @param fromRow the row of a pixel in {@code fromImage}.
	 * @param fromCol the column of a pixel in {@code fromImage}.
	 * @return the color difference between the relative pixels.
	 */
	private static double getPixelDifference(BufferedImage fromImage,
			BufferedImage toImage, int fromRow, int fromCol) {
		int toRow = (int) (((double) fromRow / fromImage.getHeight()) * toImage.getHeight());
		int toCol = (int) (((double) fromCol / fromImage.getWidth()) * toImage.getWidth());
		int fromColor = fromImage.getRGB(fromCol, fromRow);
		int toColor = toImage.getRGB(toCol, toRow);
		return Pixel.colorDifference(fromColor, toColor);
	}

	/**
	 * Deletes the file represented by this object.
	 */
	public void delete() {
		this.file.delete();
	}

	/**
	 * Returns the name of the file. More specifically, return the last part of the
	 * fully-qualified name of the file, after the final directory delimiter.
	 * 
	 * @return the name of the file.
	 */
	public String getName() {
		return this.file.getName();
	}

	/**
	 * Returns a string representation of the size of this image.
	 * 
	 * @return the size of this image.
	 */
	public String getDimensionString() {
		return this.image.getWidth() + "x" + this.image.getHeight();
	}

	/**
	 * Returns a {@code BufferedImage} of this image.
	 * 
	 * @return the {@code BufferedImage} of this image.
	 */
	public BufferedImage getImage() {
		return this.image;
	}

	/**
	 * Returns the mean of all values in an array of doubles.
	 * 
	 * @param array the array to find the mean value of.
	 * @return the mean value of the array.
	 */
	private static double getAverage(double[][] array) {
		double sum = 0;
		double elements = array.length * array[0].length;
		for (double[] row : array)
			for (double d : row)
				sum += d;
		return sum / elements;
	}

}
