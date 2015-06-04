package io.github.zachohara.imagecomparator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public abstract class Listener {

	public static class WindowResizeListener extends ComponentAdapter {

		private Window owner;

		public WindowResizeListener(Window win) {
			this.owner = win;
		}

		public void componentResized(ComponentEvent e) {
			this.owner.handleWindowResize();
		}

	}
	
	public static class ButtonListener implements ActionListener {
		
		private Window owner;
		private String buttonName;
		
		public ButtonListener(Window win, String button) {
			this.owner = win;
			this.buttonName = button;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			this.owner.handleButtonPress(this.buttonName);
		}
		
	}

}
