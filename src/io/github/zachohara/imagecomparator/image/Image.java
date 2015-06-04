package io.github.zachohara.imagecomparator.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class Image {

	BufferedImage image;
	String name;

	public Image(File filename) throws IOException {
		this.image = ImageIO.read(filename);
		this.name = filename.getName();
	}
	
	public double percentDifference(Image other) {
		int thisSize = this.image.getHeight() * this.image.getWidth();
		int otherSize = other.image.getHeight() * other.image.getWidth();
		BufferedImage larger;
		BufferedImage smaller;
		if (thisSize <= otherSize) {
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
	
	private static double getPixelDifference(BufferedImage fromImage,
			BufferedImage toImage, int fromRow, int fromCol) {
		int toRow = (int) (((double) fromRow / fromImage.getHeight()) * toImage.getHeight());
		int toCol = (int) (((double) fromCol / fromImage.getWidth()) * toImage.getWidth());
		int fromColor = fromImage.getRGB(fromCol, fromRow);
		int toColor = toImage.getRGB(toCol, toRow);
		return Pixel.colorDifference(fromColor, toColor);
	}
	
	private static double getAverage(double[][] array) {
		double sum = 0;
		double elements = array.length * array[0].length;
		for (double[] row : array)
			for (double d : row)
				sum += d;
		return sum / elements;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDimensionString() {
		return this.image.getWidth() + "x" + this.image.getHeight();
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public JComponent toComponenet() {
		return new JLabel(new ImageIcon(this.image));
	}
	
	@Override
	public String toString() {
		return "\"" + this.name + "\"[" + this.getDimensionString() + "]";
	}

}
