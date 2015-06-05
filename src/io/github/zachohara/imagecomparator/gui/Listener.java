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

package io.github.zachohara.imagecomparator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class Listener {

	private Window owner;
	private String buttonName;

	public Listener(Window win, String button) {
		this.owner = win;
		this.buttonName = button;
	}

	public void notifyOwner() {
		this.owner.handleButtonPress(buttonName);
	}

	public static class WindowResizeListener extends ComponentAdapter {

		private Window owner;

		public WindowResizeListener(Window win) {
			this.owner = win;
		}

		public void componentResized(ComponentEvent e) {
			this.owner.handleWindowResize();
		}

	}

	public static class ButtonListener extends Listener implements ActionListener {

		public ButtonListener(Window win, String button) {
			super(win, button);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			this.notifyOwner();
		}

	}

	public static class MouseClickListener extends Listener implements MouseListener {

		public MouseClickListener(Window win, String button) {
			super(win, button);
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			this.notifyOwner();
		}

		// Ignore all of these:
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}

	}

}
