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

package io.github.zachohara.imagecomparator;

import io.github.zachohara.imagecomparator.gui.Window;
import io.github.zachohara.imagecomparator.image.Image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class ImageComparator {

	private Window window;
	private JProgressBar progressBar;
	private List<Image> imageList;

	private static final double COMPARISON_THRESHHOLD = 0.15;

	/**
	 * Constructs a new {@code ImageComparator} that will compare the given files with the
	 * given window.
	 * @param fileList the list of files to be checked.
	 * @param win the window to display the images in.
	 */
	public ImageComparator(File[] fileList, Window win) {
		this.window = win;
		this.progressBar = this.window.getLoadingProgressBar();
		this.populateImageList(fileList);
	}

	/**
	 * Iterates through all images and compares them. Can be thought of as a 'main' method
	 * for this class.
	 */
	public void compareAll() {
		this.window.setLoadingText("Looking for matches...");
		this.window.setIsLoading(true);
		this.progressBar.setMaximum(sumOfN(this.imageList.size()));
		this.progressBar.setValue(0);
		for (int i = 0; i < imageList.size(); i++) {
			for (int j = i + 1; j < imageList.size(); j++) {
				Image left = this.imageList.get(i);
				Image right = this.imageList.get(j);
				if (left.percentDifference(right) < COMPARISON_THRESHHOLD) {
					this.window.setImages(left, right);
					String result = this.window.getChoice();
					if (result.equals(Window.KEEP_LEFT_LABEL) || result == Window.DELETE_BOTH_LABEL) {
						right.delete();
						imageList.remove(right);
						j--;
					} else if (result.equalsIgnoreCase(Window.KEEP_RIGHT_LABEL) || result.equals(Window.DELETE_BOTH_LABEL)) {
						right.delete();
						imageList.remove(left);
						i--;
						this.incrementProgressBar(imageList.size() - j - 1);
						this.window.setIsLoading(true);
						break;
					}
					this.window.setIsLoading(true);
				}
				this.incrementProgressBar(1);
			}
			this.window.setIsLoading(true);
		}
		this.progressBar.setValue(this.progressBar.getMaximum());
		this.window.setLoadingText("Done! All remaining images are unique.");
	}

	/**
	 * Constructs and populates a list of {@code Image} objects from an array of {@code File}
	 * objects.
	 * @param files the array of {@code File}s to use
	 */
	private void populateImageList(File[] files) {
		this.window.setLoadingText("Loading images...");
		this.progressBar.setMaximum(files.length);
		this.progressBar.setValue(0);
		this.window.setIsLoading(true);
		this.window.setVisible(true);
		this.imageList = new ArrayList<Image>();
		for (File f : files) {
			String name = f.getName();
			boolean accepted = false;
			for (String acceptableType : FileSelector.ACCEPTED_FILETYPES) {
				if (name.toLowerCase().endsWith("." + acceptableType)) {
					accepted = true;
					break;
				}
			}
			if (accepted) {
				try {
					this.imageList.add(new Image(f));
				} catch (IOException e) {
					warnLoadError(name);
				}
			} else
				warnInvalidType(name);
			this.incrementProgressBar(1);
		}
	}

	/**
	 * Warns the user that a selected file is not a real image file.
	 * @param filename the name of the invalid file.
	 */
	private static void warnInvalidType(String filename) {
		JOptionPane.showMessageDialog(null, filename + " is not an image, and will not be loaded.",
				"Warning", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Warns the user that a selected file could not be loaded for an unknown reason.
	 * @param filename the name of the unloadable file.
	 */
	private static void warnLoadError(String filename) {
		JOptionPane.showMessageDialog(null, "An error occured while trying to open " + filename +".",
				"Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Increments the progress bar by n.
	 * @param n the increment of the progress bar
	 */
	private void incrementProgressBar(int n) {
		this.progressBar.setValue(this.progressBar.getValue() + 1);
	}

	/**
	 * Returns the sum of the nth consecutive integers
	 * @param n the integer to be summed
	 * @return the sum of the nth consecutive integers
	 */
	private static int sumOfN(int n) {
		int sum = 0;
		for (int i = 1; i <= n; i++)
			sum += i;
		return sum;
	}

}
