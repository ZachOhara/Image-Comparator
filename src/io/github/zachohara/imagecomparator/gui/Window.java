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

import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

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
	private JLabel titleText;
	private JPanel titlePanel;
	private JProgressBar loadingProgress;
	private JPanel contentPanel;
	private ImagePanel leftPanel;
	private ImagePanel rightPanel;
	private JButton keepBothButton;
	private JButton deleteBothButton;

	private static final int[] DEFAULT_SIZE = {1000, 600};
	private static final String DEFAULT_LOADING_TEXT = "Loading...";
	private static final String WINDOW_TITLE = "Image Comparator by Zach Ohara";
	private static final String TITLE_INSTRUCTION_TEXT = "Select an image to keep:";
	private static final int BOTTOM_BUTTON_HEIGHT = 50;
	private static final int LOADING_TEXT_SIZE = 20;
	private static final int TITLE_TEXT_SIZE = 25;
	private static final int TOP_PANEL_HEIGHT = 40;
	private static final int BOTTOM_CUTOFF_CORRECTION = 38;

	public static final String KEEP_LEFT_LABEL = "Keep Left";
	public static final String KEEP_RIGHT_LABEL = "Keep Right";
	public static final String KEEP_BOTH_LABEL = "Keep Both";
	public static final String DELETE_BOTH_LABEL = "Delete Both";

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs and formats a new Window.
	 */
	public Window() {
		super(WINDOW_TITLE);
		this.initializeAll();
	}
	
	/**
	 * Resizes all elemtents inside the window according to the new size.
	 */
	public void handleWindowResize() {
		this.resizeContentPanel();
		this.resizeTitle();
		this.resizeBottom();
		this.resizeBothSides();
		this.resizeLoadingPanel();
	}

	/**
	 * Pauses until the user makes a descision for the current prompt, then
	 * returns that descision.
	 * @return the user descision for the current prompt.
	 */
	public String getChoice() {
		this.selection = "";
		this.isWaitingForSelection = true;
		while (this.isWaitingForSelection) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException ignore) {}
		}
		return this.selection;
	}
	
	/**
	 * Changes the text that is displayed above the progress bar in the loading
	 * screen.
	 * @param text the text that should be displayed in the loading screen.
	 */
	public void setLoadingText(String text) {
		this.loadingText.setText(text);
	}
	
	/**
	 * Changes between the loading screen and the prompt screen being shown.
	 * @param loading {@code true} if the loading screen should be displayed,
	 * or {@code false} if the prompt screen should be displayed.
	 */
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

	/**
	 * Determines what to do when any button is pressed in the GUI. 
	 * @param button the name of the button that was pressed.
	 */
	public void handleButtonPress(String button) {
		if (this.isWaitingForSelection) {
			this.selection = button;
			this.isWaitingForSelection = false;
		}
	}

	/**
	 * Changes the prompt screen to show a set of two images.
	 * @param left the {@code Image} that should display on the left side.
	 * @param right the {@code Image} that should display on the right side.
	 */
	public void setImages(Image left, Image right) {
		this.setIsLoading(false);
		this.leftPanel.setImage(left);
		this.rightPanel.setImage(right);
		this.contentPanel.repaint();
	}
	
	/**
	 * Returns the {@code JProgressBar} object that represents the progress bar
	 * that appears on the loading screen.
	 * @return the progress bar object from the loading screen.
	 */
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
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Weird window init stuff below:
	 * ==============================
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	/**
	 * Initializes this window.
	 */
	private void initializeAll() {
		this.initializeFrame();
		this.initializeContentPanel();
		this.initializeLoadingScreen();
	}
	
	/**
	 * Initializes the {@code JFrame} that contains this entire window.
	 */
	private void initializeFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(DEFAULT_SIZE[0], DEFAULT_SIZE[1]);
		this.setLayout(null);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		Listener.WindowResizeListener listener = new Listener.WindowResizeListener(this);
		this.addComponentListener(listener);
		this.addWindowStateListener(listener);
	}

	/**
	 * Initializes the content panel {@code JPanel}, which contains elements
	 * from the prompt screen.
	 */
	private void initializeContentPanel() {
		this.contentPanel = new JPanel();
		this.contentPanel.setLayout(null);
		this.contentPanel.setLocation(0, 0);
		this.resizeContentPanel();
		this.formatTitle();
		this.formatSides();
		this.formatBottom();
		this.add(this.contentPanel);
	}
	
	/**
	 * Resizes the content panel based on the current size of the window.
	 */
	private void resizeContentPanel() {
		this.contentPanel.setSize(this.getSize());
	}

	/**
	 * Formats the title of the window.
	 */
	private void formatTitle() {
		this.titlePanel = new JPanel();
		this.titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.titleText = new JLabel(TITLE_INSTRUCTION_TEXT);
		this.titleText.setFont(getFontOfSize(TITLE_TEXT_SIZE));
		this.titleText.setLocation(0, 0);
		this.resizeTitle();
		this.titlePanel.add(this.titleText);
		this.contentPanel.add(this.titlePanel);
	}
	
	/**
	 * Resizes the title panel based on the current size of the window.
	 */
	private void resizeTitle() {
		this.titlePanel.setSize(this.getWidth(), TOP_PANEL_HEIGHT);
	}

	/**
	 * Formats both of the side panels in this window.
	 */
	private void formatSides() {
		this.leftPanel = this.formatImagePanel(KEEP_LEFT_LABEL);
		this.rightPanel = this.formatImagePanel(KEEP_RIGHT_LABEL);
		this.resizeBothSides();
	}
	
	/**
	 * Formats a single side panel in this window.
	 * @param label the label of the side panel
	 * @return the formatted, registered {@code ImagePanel} object.
	 */
	private ImagePanel formatImagePanel(String label) {
		ImagePanel p = new ImagePanel();
		p.addMouseListener(new Listener.MouseClickListener(this, label));
		this.contentPanel.add(p);
		return p;
	}
	
	/**
	 * Resizes the side panels based on the current size of the window.
	 */
	private void resizeBothSides() {
		this.resizeSide(this.leftPanel);
		this.resizeSide(this.rightPanel);
		this.leftPanel.setLocation(0, this.titlePanel.getHeight());
		this.rightPanel.setLocation(this.getWidth() / 2, this.titlePanel.getHeight());
	}
	
	/**
	 * Resizes a single side panel based on the current size of the window.
	 * @param p the panel to be resized.
	 */
	private void resizeSide(ImagePanel p) {
		p.setSize(this.getWidth() / 2,
				this.getHeight() - this.titlePanel.getHeight() - (2 * BOTTOM_BUTTON_HEIGHT) - BOTTOM_CUTOFF_CORRECTION);
		p.handleResize();
	}
	
	/**
	 * Formats the two buttons on the bottom of the window.
	 */
	private void formatBottom() {
		this.keepBothButton = this.formatBottomButton(KEEP_BOTH_LABEL);
		this.deleteBothButton = this.formatBottomButton(DELETE_BOTH_LABEL);
		this.resizeBottom();
	}

	/**
	 * Formats a single button on the bottom of the window.
	 * @param name the text to display on the button.
	 * @return the formatted, registered, {@code JButton} object.
	 */
	private JButton formatBottomButton(String name) {
		JButton b = new JButton(name);
		b.addActionListener(new Listener.ButtonListener(this, name));
		this.contentPanel.add(b);
		return b;
	}
	
	/**
	 * Resizes the bottom buttons based on the current size of the window.
	 */
	private void resizeBottom() {
		this.resizeBottomButton(2, this.keepBothButton);
		this.resizeBottomButton(1, this.deleteBothButton);
	}
	
	/**
	 * Resizes a single bottom button based on the current size of the window.
	 * @param posInStack the order of the button from the bottom of the window.
	 * @param button the button to be resized.
	 */
	private void resizeBottomButton(int posInStack, JButton button) {
		button.setSize(this.getWidth(), BOTTOM_BUTTON_HEIGHT);
		button.setLocation(0, this.getHeight() - (posInStack * BOTTOM_BUTTON_HEIGHT) - BOTTOM_CUTOFF_CORRECTION);
	}
	
	/**
	 * Initializes the loading screen.
	 */
	private void initializeLoadingScreen() {
		this.loadingPanel = new JPanel();
		this.loadingPanel.setLayout(new BoxLayout(this.loadingPanel, BoxLayout.Y_AXIS));
		this.loadingPanel.setLocation(0, 0);
		this.resizeLoadingPanel();
		this.initializeLoadingText();
		this.initializeLoadingProgressBar();
		this.loadingPanel.add(Box.createVerticalGlue());
		this.loadingPanel.add(this.loadingText);
		this.loadingPanel.add(this.loadingProgress);
		this.loadingPanel.add(Box.createVerticalGlue());
	}
	
	/**
	 * Resizes the loading panel based on the current size of the window.
	 */
	private void resizeLoadingPanel() {
		this.loadingPanel.setSize(this.getSize());
	}
	
	/**
	 * Initializes the text that is shown on the loading screen.
	 */
	private void initializeLoadingText() {
		this.loadingText = new JLabel();
		this.setLoadingText(DEFAULT_LOADING_TEXT);
		this.loadingText.setFont(getFontOfSize(LOADING_TEXT_SIZE));
		this.loadingText.setAlignmentX(CENTER_ALIGNMENT);
		this.loadingText.setAlignmentY(CENTER_ALIGNMENT);
	}
	
	/**
	 * Initializes the progress bar that is shown on the loading screen.
	 */
	private void initializeLoadingProgressBar() {
		this.loadingProgress = new JProgressBar(0, 100);
		this.loadingProgress.setValue(0);
		this.loadingProgress.setStringPainted(true);
	}
	
	/**
	 * Get a {@code Font} object that represents the standard font in the 
	 * specified size.
	 * @param size the size of the font.
	 * @return 
	 */
	private static Font getFontOfSize(int size) {
		return new Font("Font size " + size, Font.PLAIN, size);
	}

}
