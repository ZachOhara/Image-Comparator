package io.github.zachohara.imagecomparator.image;

public abstract class Pixel {
	
	/**
	 * Compares two 8-bit RGB color integers and finds the difference between them. The returned
	 * value is a decimal between zero and one that represents the percent of difference in
	 * the two colors. This number is calculated using the following formula:
	 * <pre>
	 * {@code
	 * int redDifference = Math.abs(red1 - red2);
	 * int blueDifference = Math.abs(blue1 - blue2);
	 * int greenDifference = Math.abs(green1 - green2);
	 * int sumDifference = redDifference + blueDifference + greenDifference;
	 * double percentDifference = (double) sumDifference / (255 * 3);
	 * }
	 * </pre>
	 * @param color1 an RGB color integer to be compared
	 * @param color2 another RGB color integer to be compared
	 * @return a number between  0 and 1 representing the difference between the colors.
	 */
	public static double colorDifference(int color1, int color2) {
		double difference = Math.abs(getRed(color1) - getRed(color2))
				+ Math.abs(getGreen(color1) - getGreen(color2))
				+ Math.abs(getBlue(color1) - getBlue(color2));
		return difference / (255 * 3);
	}
	
	/**
	 * Extracts the red componenet from the given RGB color integer.
	 * @param color the RGB integer to extract from.
	 * @return the red componenet from the color
	 */
	private static int getRed(int color) {
		return ((color / 0x100) / 0x100) % 0x100;
	}

	/**
	 * Extracts the green componenet from the given RGB color integer.
	 * @param color the RGB integer to extract from.
	 * @return the green componenet from the color
	 */
	private static int getGreen(int color) {
		return (color / 0x100) % 0x100;
	}

	/**
	 * Extracts the blue componenet from the given RGB color integer.
	 * @param color the RGB integer to extract from.
	 * @return the blue componenet from the color
	 */
	private static int getBlue(int color) {
		return color % 0x100;
	}

}
