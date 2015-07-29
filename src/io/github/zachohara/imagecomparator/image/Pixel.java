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

/**
 * The {@code Pixel} class contains static methods for comparing single pixels.
 * 
 * @author Zach Ohara.
 */
public final class Pixel {
	
	/**
	 * The {@code Pixel} class should not be instantiable.
	 */
	private Pixel() {
		
	}

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
	 * 
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
	 * 
	 * @param color the RGB integer to extract from.
	 * @return the red componenet from the color
	 */
	private static int getRed(int color) {
		return ((color / 0x100) / 0x100) % 0x100;
	}

	/**
	 * Extracts the green componenet from the given RGB color integer.
	 * 
	 * @param color the RGB integer to extract from.
	 * @return the green componenet from the color
	 */
	private static int getGreen(int color) {
		return (color / 0x100) % 0x100;
	}

	/**
	 * Extracts the blue componenet from the given RGB color integer.
	 * 
	 * @param color the RGB integer to extract from.
	 * @return the blue componenet from the color
	 */
	private static int getBlue(int color) {
		return color % 0x100;
	}

}
