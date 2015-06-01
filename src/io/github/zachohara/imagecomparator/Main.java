package io.github.zachohara.imagecomparator;


public class Main {

	public static void main(String[] args) {
		FileSelector filedialog = new FileSelector();
		ImageComparator comparator = new ImageComparator(filedialog.getFiles());
		comparator.compareAll();
		System.exit(0);
	}

}
