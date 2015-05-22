package io.github.zachohara.imagecomparator;

import java.awt.FileDialog;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Launcher {
	
	public static final String SUPER_DIRECTORY = "C:/";

	public static void main(String[] args) {
		String directory = getDirectorySwing();
		System.out.println(directory);
		System.exit(0);
	}
	
	public static String getDirectory() {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FileDialog chooser = new FileDialog(window);
		throw new UnsupportedOperationException();
	}
	
	public static String getDirectorySwing() {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFileChooser dialog = new JFileChooser(SUPER_DIRECTORY);
		dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		dialog.setVisible(true);
		// window.setVisible(true);
		int status = dialog.showSaveDialog(window);
		//return dialog.getDirectory();
		String result = null;
		if (status == JFileChooser.APPROVE_OPTION)
			result = dialog.getSelectedFile().getPath();
		else
			System.exit(1);
		window.setVisible(false);
		return result;
	}

}
