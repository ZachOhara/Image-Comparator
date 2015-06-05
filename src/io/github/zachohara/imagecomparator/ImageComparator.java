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

public class ImageComparator {

	public Window window;
	private List<Image> imageList;
	
	private static final double COMPARISON_THRESHHOLD = 0.15;

	public ImageComparator(File[] fileArr, Window w) {
		this.window = w;
		List<File> fileList = this.generateFileList(fileArr);
		this.populateImageList(fileList);
	}

	public void compareAll() {
		for (int i = 0; i < imageList.size(); i++) {
			for (int j = i + 1; j < imageList.size(); j++) {
				Image left = this.imageList.get(i);
				Image right = this.imageList.get(j);
				if (left.percentDifference(right) < COMPARISON_THRESHHOLD) {
					this.window.setImages(left, right);
					int result = this.window.getChoice();
					if (result == Window.KEEP_LEFT || result == Window.KEEP_NONE) {
						right.delete();
						imageList.remove(right);
						j--;
					} else if (result == Window.KEEP_RIGHT || result == Window.KEEP_NONE) {
						right.delete();
						imageList.remove(left);
						i--;
						break;
					}
				}
//				System.out.println(this.window.getChoice());
//				System.out.println("Comparing " + imageList.get(i) + " and " + imageList.get(j));
//				System.out.println(imageList.get(i).percentDifference(imageList.get(j)) + ", "
//						+ imageList.get(j).percentDifference(imageList.get(i)));
			}
		}
	}

	private List<File> generateFileList(File[] filelist) {
		List<File> acceptedFiles = new ArrayList<File>();
		for (File f : filelist) {
			String name = f.getName();
			boolean accepted = false;
			for (String type : FileSelector.ACCEPTED_FILETYPES) {
				if (name.endsWith("." + type)) {
					acceptedFiles.add(f);
					accepted = true;
				}
			}
			if (!accepted) {
				warnInvalidType(name);
			}
		}
		System.out.println(acceptedFiles);
		return acceptedFiles;
	}

	private void populateImageList(List<File> fileList) {
		this.imageList = new ArrayList<Image>();
		for (int i = 0; i < fileList.size(); i++) {
			try {
				this.imageList.add(new Image(fileList.get(i)));
			} catch (IOException e) {
				e.printStackTrace();
				warnLoadError(fileList.get(i).getName());
			}
		}
	}

	private static void warnInvalidType(String filename) {
		JOptionPane.showMessageDialog(null, filename + " is not an image, and will not be loaded.",
				"Warning", JOptionPane.WARNING_MESSAGE);
	}

	private static void warnLoadError(String filename) {
		JOptionPane.showMessageDialog(null, "An error occured while trying to open " + filename +".",
				"Error", JOptionPane.ERROR_MESSAGE);
	}

}
