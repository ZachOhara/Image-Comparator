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
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public abstract class Listener {

	private Window owner;
	private String buttonName;

	/**
	 * Constructs a listener with the given owner and button.
	 * @param win the window that all information should be returned to.
	 * @param button the label of the button that this is registered to.
	 */
	public Listener(Window win, String button) {
		this.owner = win;
		this.buttonName = button;
	}

	/**
	 * Notifies the owner of this listener that the registered button has been pressed.
	 */
	public void notifyOwner() {
		this.owner.handleButtonPress(buttonName);
	}

	/**
	 * A listener that listens for the user resizing a window.
	 */
	public static class WindowResizeListener extends ComponentAdapter implements WindowStateListener {

		private Window owner;

		public WindowResizeListener(Window win) {
			this.owner = win;
		}

		@Override
		public void componentResized(ComponentEvent e) {
			this.owner.handleWindowResize();
		}

		@Override
		public void windowStateChanged(WindowEvent e) {
			this.owner.handleWindowResize();
		}

	}

	/**
	 * A listener that listens for when a button is clicked on.
	 */
	public static class ButtonListener extends Listener implements ActionListener {

		public ButtonListener(Window win, String button) {
			super(win, button);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			this.notifyOwner();
		}

	}

	/**
	 * A listener that listens for the user clicking on a panel or frame with the mouse.
	 */
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
