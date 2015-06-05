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

import java.awt.FileDialog;
import java.io.File;
import java.util.concurrent.CancellationException;

import javax.swing.JFrame;

public class FileSelector {
	
	private JFrame window;
	private FileDialog fileChooserAWT;
//	private JFileChooser fileChooserSwing;
	
	public static final String[] ACCEPTED_FILETYPES = {"png", "jpeg", "jpg", "gif"};
	
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

}
