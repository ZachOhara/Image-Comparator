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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Window extends JFrame {
	
	private volatile boolean isWaitingForSelection;
	private String selection;
	
	private JLabel loadingText;
	private JPanel loadingPanel;
	private JProgressBar loadingProgress;
	private JPanel contentPanel;
	private ImagePanel leftPanel;
	private ImagePanel rightPanel;

	private static final int[] DEFAULT_SIZE = {1000, 600};
	private static final String DEFAULT_LOADING_TEXT = "Loading...";
	private static final String WINDOW_TITLE = "Image Comparator by Zach Ohara";
	private static final String TITLE_INSTRUCTION_TEXT = "Select an image to keep:";
	private static final int BOTTOM_BUTTON_HEIGHT = 50;
	private static final int LOADING_TEXT_SIZE = 20;
	private static final int TITLE_TEXT_SIZE = 25;

	public static final String KEEP_LEFT_LABEL = "Keep Left";
	public static final String KEEP_RIGHT_LABEL = "Keep Right";
	public static final String KEEP_BOTH_LABEL = "Keep Both";
	public static final String DELETE_BOTH_LABEL = "Delete Both";

	private static final long serialVersionUID = 1L;

	public Window() {
		super(WINDOW_TITLE);
		this.initializeAll();
//		this.setIsLoading(true);
	}

	public void handleWindowResize() {
		int newWidth = this.getWidth();
		this.leftPanel.handleResize(newWidth / 2);
		this.rightPanel.handleResize(newWidth / 2);
	}

	public String getChoice() {
		this.selection = "";
		this.isWaitingForSelection = true;
		while (this.isWaitingForSelection) {
			try {
				Thread.sleep(100); //TODO try different values for this
			} catch (InterruptedException ignore) {}
		}
		return this.selection;
	}
	
	public void setLoadingText(String text) {
		this.loadingText.setText(text);
	}
	
	public void setIsLoading(boolean loading) {
		if (loading) {
			this.remove(this.contentPanel);
			this.add(this.loadingPanel);
		} else {
			this.remove(this.loadingPanel);
			this.add(this.contentPanel);
		}
		this.repaint();
	}

	public void handleButtonPress(String button) {
		if (this.isWaitingForSelection) {
			this.selection = button;
			this.isWaitingForSelection = false;
		}
	}

	public void setImages(Image left, Image right) {
		this.setIsLoading(false);
		this.leftPanel.setImage(left);
		this.rightPanel.setImage(right);
		this.contentPanel.repaint();
	}
	
	public JProgressBar getLoadingProgressBar() {
		return this.loadingProgress;
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
	
	/*
	 * Weird window init stuff below:
	 * ==============================
	 */
	
	private void initializeAll() {
		this.initializeFrame();
		this.initializeContentPanel();
		this.initializeLoadingScreen();
	}
	
	private void initializeFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(DEFAULT_SIZE[0], DEFAULT_SIZE[1]);
		//this.setLayout(new FlowLayout());
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.addComponentListener(new Listener.WindowResizeListener(this));
	}

	private void initializeContentPanel() {
		this.contentPanel = new JPanel();
		this.contentPanel.setLayout(new BorderLayout());
		this.formatTitle();
		this.formatSides();
		this.formatBottom();
		this.add(this.contentPanel);
	}

	private void formatTitle() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel text = new JLabel(TITLE_INSTRUCTION_TEXT);
		text.setFont(getFontOfSize(TITLE_TEXT_SIZE));
		topPanel.add(text);
		this.contentPanel.add("North", topPanel);
	}

	private void formatSides() {
		this.leftPanel = this.formatImagePanel(KEEP_LEFT_LABEL, "West");
		this.rightPanel = this.formatImagePanel(KEEP_RIGHT_LABEL, "East");
		this.handleWindowResize();
	}
	
	private ImagePanel formatImagePanel(String label, String side) {
		ImagePanel p = new ImagePanel(this);
		p.addMouseListener(new Listener.MouseClickListener(this, label));
		this.contentPanel.add(side, p);
		return p;
	}
	
	private void formatBottom() {
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(0,1));
		this.formatBottomButton(bottom, KEEP_BOTH_LABEL);
		this.formatBottomButton(bottom, DELETE_BOTH_LABEL);
		this.contentPanel.add("South", bottom);
	}

	private void formatBottomButton(JPanel panel, String name) {
		JButton b = new JButton(name);
		b.setPreferredSize(new Dimension(0, BOTTOM_BUTTON_HEIGHT));
		b.addActionListener(new Listener.ButtonListener(this, name));
		panel.add(b);
	}
	
	private void initializeLoadingScreen() {
		this.loadingPanel = new JPanel();
		this.loadingPanel.setLayout(new BoxLayout(this.loadingPanel, BoxLayout.Y_AXIS));
		this.initializeLoadingText();
		this.initializeLoadingProgressBar();
		this.loadingPanel.add(Box.createVerticalGlue());
		this.loadingPanel.add(this.loadingText);
		this.loadingPanel.add(this.loadingProgress);
		this.loadingPanel.add(Box.createVerticalGlue());
	}
	
	private void initializeLoadingText() {
		this.loadingText = new JLabel();
		this.setLoadingText(DEFAULT_LOADING_TEXT);
		this.loadingText.setFont(getFontOfSize(LOADING_TEXT_SIZE));
		this.loadingText.setAlignmentX(CENTER_ALIGNMENT);
		this.loadingText.setAlignmentY(CENTER_ALIGNMENT);
	}
	
	private void initializeLoadingProgressBar() {
		this.loadingProgress = new JProgressBar(0, 100);
		this.loadingProgress.setValue(0);
		this.loadingProgress.setStringPainted(true);
	}
	
	private static Font getFontOfSize(int size) {
		return new Font("Font size " + size, Font.PLAIN, size);
	}

}
