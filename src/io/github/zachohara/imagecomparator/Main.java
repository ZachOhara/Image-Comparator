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

package io.github.zachohara.imagecomparator;

import java.util.concurrent.CancellationException;

import io.github.zachohara.imagecomparator.gui.Window;

/**
 * The {@code Main} class acts as the entry point for the application.
 * 
 * @author Zach Ohara
 */
public class Main {

	/**
	 * The main procedure for entire application.
	 * 
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		FileSelector filedialog = new FileSelector();
		Window win = new Window();
		ImageComparator comparator = null;
		try {
			comparator = new ImageComparator(filedialog.getFiles(), win);
		} catch (CancellationException e) {
			System.exit(1);
		}
		comparator.compareAll();
	}

}
