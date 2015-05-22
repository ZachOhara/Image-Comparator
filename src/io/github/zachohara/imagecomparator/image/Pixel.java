package io.github.zachohara.imagecomparator.image;

import java.awt.Color;

public class Pixel implements Comparable<Pixel >{
	
	private int red;
	private int green;
	private int blue;
	
	public Pixel(int colorInt) {
		this(new Color(colorInt));
	}
	
	public Pixel(Color c) {
		this(c.getRed(), c.getGreen(), c.getBlue());
	}
	
	public Pixel(int r, int g, int b) {
		this.red = r;
		this.green = g;
		this.blue = b;
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}
	
	@Override
	public int compareTo(Pixel other) {
		return Math.abs(this.getRed() - other.getRed())
				+ Math.abs(this.getGreen() - other.getGreen())
				+ Math.abs(this.getBlue() - other.getBlue());
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof Pixel && this.compareTo((Pixel)other) == 0;
	}

}
