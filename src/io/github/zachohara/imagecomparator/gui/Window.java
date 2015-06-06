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

import io.github.zachohara.imagecomparator.FileSelector;
import io.github.zachohara.imagecomparator.image.Image;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JFrame {
	
	private volatile boolean waitingForSelection;
	private int selection;

	private JPanel contentPanel;
	private ImagePanel leftPanel;
	private ImagePanel rightPanel;

	private static final int[] DEFAULT_SIZE = {1000, 600};
	private static final String WINDOW_TITLE = "Image Comparator by Zach Ohara";

	private static final int BOTTOM_BUTTON_HEIGHT = 50;

	public static final int OPERATION_FAILED = -1;
	public static final int KEEP_LEFT = 1;
	public static final int KEEP_RIGHT = 2;
	public static final int KEEP_BOTH = 3;
	public static final int KEEP_NONE = 4;

	public static final String LEFT_LABEL = "left";
	public static final String RIGHT_LABEL = "right";
	public static final String KEEP_BOTH_LABEL = "Keep Both";
	public static final String DELETE_BOTH_LABEL = "Delete Both";

	private static final long serialVersionUID = 1L;

	public Window() {
		super(WINDOW_TITLE);
		this.initializeWindow();
		this.initializeContentPanel();
	}

	public void handleWindowResize() {
		int newWidth = this.getWidth();
		this.leftPanel.handleResize(newWidth / 2);
		this.rightPanel.handleResize(newWidth / 2);
	}

	public int getChoice() {
		this.selection = 0;
		this.waitingForSelection = true;
		while (this.waitingForSelection) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.selection;
	}
	
	public void setLoadingText(String text) {
		//TODO
	}
	
	public void setIsLoading(boolean loading) {
		//TODO
	}

	public void handleButtonPress(String button) {
		if (this.waitingForSelection) {
			if (button == LEFT_LABEL)
				this.selection = KEEP_LEFT;
			else if (button == RIGHT_LABEL)
				this.selection = KEEP_RIGHT;
			else if (button == KEEP_BOTH_LABEL)
				this.selection = KEEP_BOTH;
			else if (button == DELETE_BOTH_LABEL)
				this.selection = KEEP_NONE;

			if (this.selection != 0)
				this.waitingForSelection = false;
		}
	}

	public void setImages(Image left, Image right) {
		this.leftPanel.setImage(left);
		this.rightPanel.setImage(right);
		this.contentPanel.repaint();
	}
	
	private void initializeWindow() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(DEFAULT_SIZE[0], DEFAULT_SIZE[1]);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.addComponentListener(new Listener.WindowResizeListener(this));
	}

	private void initializeContentPanel() {
		this.contentPanel = new JPanel();
		this.formatWindow();
		this.add(this.contentPanel);
	}

	private void formatWindow() {
		this.contentPanel.setLayout(new BorderLayout());
		this.formatTitle();
		this.formatSides();
		this.formatBottom();
	}

	private void formatTitle() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel text = new JLabel("Select an image to keep:");
		Font titleFont = new Font(text.getFont().getName(), Font.PLAIN, 25);
		text.setFont(titleFont);
		topPanel.add(text);
		this.contentPanel.add("North", topPanel);
	}

	private void formatSides() {
		this.leftPanel = new ImagePanel(this);
		this.rightPanel = new ImagePanel(this);
		this.leftPanel.addMouseListener(new Listener.MouseClickListener(this, LEFT_LABEL));
		this.rightPanel.addMouseListener(new Listener.MouseClickListener(this, RIGHT_LABEL));
		this.handleWindowResize();
		this.contentPanel.add("West", this.leftPanel);
		this.contentPanel.add("East", this.rightPanel);
	}
	
	private void formatBottom() {
		JPanel bottom = new JPanel();
		bottom.setBackground(Color.cyan);
		bottom.setLayout(new GridLayout(0,1));
		bottom.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		this.formatButton(bottom, KEEP_BOTH_LABEL);
		this.formatButton(bottom, DELETE_BOTH_LABEL);
		this.contentPanel.add("South", bottom);
	}

	private void formatButton(JPanel panel, String name) {
		JButton b = new JButton(name);
		b.setPreferredSize(new Dimension(0, BOTTOM_BUTTON_HEIGHT));
		b.setSize(new Dimension(0, BOTTOM_BUTTON_HEIGHT));
		b.setMinimumSize(b.getSize());
		b.addActionListener(new Listener.ButtonListener(this, name));
		b.setAlignmentX(JButton.CENTER_ALIGNMENT);
		b.setAlignmentY(JButton.CENTER_ALIGNMENT);
		panel.add(b);
	}

	public static void main(String[] args) throws IOException {
		Window w = new Window();
		w.setVisible(true);
		FileSelector filedialog = new FileSelector();
		File[] f = filedialog.getFiles();
		Image l = new Image(f[0]);
		Image r = new Image(f[1]);
		w.setImages(l, r);
	}

}
