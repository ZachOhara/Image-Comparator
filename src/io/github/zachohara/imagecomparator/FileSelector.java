package io.github.zachohara.imagecomparator;

import java.awt.FileDialog;
import java.io.File;
import java.io.FilenameFilter;
import java.util.concurrent.CancellationException;

import javax.swing.JFrame;

public class FileSelector {
	
	private JFrame window;
	private FileDialog fileChooserAWT;
//	private JFileChooser fileChooserSwing;
	
	public static final String SUPER_DIRECTORY = "C:/";
	public static final String WINDOW_TITLE = "Select files to compare";
	
	private static final boolean LOAD_WITH_AWT = true;

	public FileSelector() {
		this.window = new JFrame(WINDOW_TITLE);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (LOAD_WITH_AWT) {
			this.fileChooserAWT = new FileDialog(this.window, WINDOW_TITLE);
			this.fileChooserAWT.setMode(FileDialog.LOAD);
			this.fileChooserAWT.setDirectory(SUPER_DIRECTORY);
			this.fileChooserAWT.setMultipleMode(true);
			this.fileChooserAWT.setFilenameFilter(new ImageFilter());
		} else {
			throw new UnsupportedOperationException("Cannot load using swing");
		}
	}
	
	public File[] getFiles() throws CancellationException {
		if (LOAD_WITH_AWT)
			return this.getFilesAWT();
		else
			throw new UnsupportedOperationException("Cannot load using swing");
	}
	
	private File[] getFilesAWT() throws CancellationException {
		this.fileChooserAWT.setVisible(true);
		File[] selected = this.fileChooserAWT.getFiles();
		if (selected.length != 0)
			return selected;
		throw new CancellationException();
	}
	
	private static class ImageFilter implements FilenameFilter {

		@Override
		public boolean accept(File file, String filename) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}
		
	}

}
