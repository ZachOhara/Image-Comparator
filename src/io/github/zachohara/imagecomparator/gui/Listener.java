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

package io.github.zachohara.imagecomparator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

/**
 * The {@code Listener} class contains other static classes that act as window listeners.
 * It also acts as an abstract class that contains functionality for notifying a
 * {@code Window} of an event. Most, but not all, of the listener classes inside
 * {@code Listener} will listen to a single button and report button presses.
 *
 * @author Zach Ohara
 */
public abstract class Listener {

	/**
	 * The {@code Window} that 'owns' this listener, and that all window events should be
	 * reported back to.
	 */
	private Window owner;

	/**
	 * The tag associated with the type of event that will be reported to the owner. If
	 * this listener is a button listener, this string is the label text on the button.
	 */
	private String buttonName;

	/**
	 * Constructs a new {@code Listener} with the given owner and button.
	 *
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
		this.owner.handleButtonPress(this.buttonName);
	}

	/**
	 * A listener that listens for the user resizing a window.
	 */
	public static class WindowResizeListener extends ComponentAdapter implements WindowStateListener {

		/**
		 * The {@code Window} that 'owns' this listener, and that all window events should
		 * be reported back to.
		 */
		private Window owner;

		/**
		 * Constructs a new {@code WindowResizeListener} with the given owner.
		 *
		 * @param win the window that all information should be returned to.
		 */
		public WindowResizeListener(Window win) {
			this.owner = win;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void componentResized(ComponentEvent e) {
			this.owner.handleWindowResize();
		}

		/**
		 * {@inheritDoc}
		 */
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
	 * A listener that listens for the user clicking on a panel or frame with the mouse. As
	 * per specification by the {@code MouseListener}, this listener must register events
	 * when the mouse enters or exits the specified area, when the mouse is pressed or is
	 * released, and when the mouse is clicked. For the purposes of this listener, action
	 * is only taken when the mouse is clicked. All other mouse events are ignored.
	 */
	public static class MouseClickListener extends Listener implements MouseListener {

		/**
		 * Constructs a new listener with the given owner and button name.
		 *
		 * @param win the window that all information should be returned to.
		 * @param button the label of the button or panel that this is registered to.
		 */
		public MouseClickListener(Window win, String button) {
			super(win, button);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void mouseClicked(MouseEvent arg0) {
			this.notifyOwner();
		}

		// Ignore all of these:
		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

	}

}
