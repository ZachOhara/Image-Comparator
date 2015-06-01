package io.github.zachohara.imagecomparator;

import io.github.zachohara.imagecomparator.image.Image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ImageComparator {

	private Image[] imageList; 

	public ImageComparator(File[] fileArr) {
		List<File> fileList = this.generateFileList(fileArr);
		this.populateImageList(fileList);
	}

	public void compareAll() {
		for (int i = 0; i < imageList.length; i++) {
			for (int j = i + 1; j < imageList.length; j++) {
				//TODO: compare the images in a GUI
				System.out.println("Comparing " + imageList[i] + " and " + imageList[j]);
				System.out.println(imageList[i].percentDifference(imageList[j]) + ", "
						+ imageList[j].percentDifference(imageList[i]));
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
		this.imageList = new Image[fileList.size()];
		for (int i = 0; i < fileList.size(); i++) {
			try {
				this.imageList[i] = new Image(fileList.get(i));
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
