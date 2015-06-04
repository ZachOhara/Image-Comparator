package io.github.zachohara.imagecomparator.gui;

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

}
