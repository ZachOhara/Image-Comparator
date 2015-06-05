package io.github.zachohara.imagecomparator.image;

public abstract class Pixel {
	
	public static double colorDifference(int color1, int color2) {
		double difference = Math.abs(getRed(color1) - getRed(color2))
				+ Math.abs(getGreen(color1) - getGreen(color2))
				+ Math.abs(getBlue(color1) - getBlue(color2));
		return difference / (255 * 3);
	}
	
	private static int getRed(int color) {
		return ((color / 0x100) / 0x100) % 0x100;
	}
	
	private static int getGreen(int color) {
		return (color / 0x100) % 0x100;
	}
	
	private static int getBlue(int color) {
		return color % 0x100;
	}

}
