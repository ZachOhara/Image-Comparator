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
	private FileDialog dialog;

	public static final String[] ACCEPTED_FILETYPES = {"png", "jpeg", "jpg", "gif"};
	public static final String WINDOW_TITLE = "Select files to compare";

	/**
	 * Constructs a new {@code FileSelector}.
	 */
	public FileSelector() {
		this.window = new JFrame(WINDOW_TITLE);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.dialog = new FileDialog(this.window, WINDOW_TITLE);
		this.dialog.setMode(FileDialog.LOAD);
		this.dialog.setMultipleMode(true);
	}

	/**
	 * Prompts the user to select files to compare, and returns the unfiltered selection.
	 * @return the files selected by the user.
	 * @throws {@code CancellationException} if the user closed the selection dialog.
	 */
	public File[] getFiles() throws CancellationException {
		this.dialog.setVisible(true);
		File[] selected = this.dialog.getFiles();
		if (selected.length != 0)
			return selected;
		throw new CancellationException();
	}

}
