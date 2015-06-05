package io.github.zachohara.imagecomparator;

import io.github.zachohara.imagecomparator.gui.Window;


public class Main {

	public static void main(String[] args) {
		FileSelector filedialog = new FileSelector();
		Window win = new Window();
		ImageComparator comparator = new ImageComparator(filedialog.getFiles(), win);
		win.setVisible(true);
		comparator.compareAll();
		System.exit(0);
	}

}
