package io.github.zachohara.imagecomparator.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window {

	private JFrame window;
	private ImagePanel leftPanel;
	private ImagePanel rightPanel;
	
	private static final int BOTTOM_BUTTON_HEIGHT = 50;

	public Window() {
		this.initializeWinow();
		this.formatWindow();
	}

	public void setVisible(boolean visible) {
		this.window.setVisible(visible);
	}
	
	public void handleWindowResize() {
		System.out.println("Resize! " + System.currentTimeMillis());
		int newWidth = this.window.getSize().width;
		this.leftPanel.resize(newWidth / 2);
		this.rightPanel.resize(newWidth / 2);
		this.window.repaint();
	}
	
	public void handleButtonPress(String button) {
		System.out.println(button);
		//TODO: handle button presses
	}
	
//	public void setImages(Image left, Image right) {
//		this.setLeftImage(left);
//		this.setRightImage(right);
//	}
//	
//	public void setLeftImage(Image left) {
//		this.leftInfoPanel.removeAll();
//		this.leftInfoPanel.add(new JLabel(left.getName()));
//		this.leftInfoPanel.add(new JLabel(left.getDimensionString()));
//		this.leftPanel.add("Center", left.toComponenet());
//		this.leftInfoPanel.repaint();
//		this.leftPanel.repaint();
//		this.window.repaint();
//	}
//	
//	public void setRightImage(Image right) {
//		this.rightInfoPanel.removeAll();
//		this.rightInfoPanel.add(new JLabel(right.getName()));
//		this.rightInfoPanel.add(new JLabel(right.getDimensionString()));
//		this.rightPanel.add("Center", right.toComponenet());
//		this.rightInfoPanel.repaint();
//		this.rightPanel.repaint();
//		this.window.repaint();
//	}

	private void initializeWinow() {
		this.window = new JFrame("Image Comparator by Zach Ohara");
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setSize(1000, 600);
		this.window.setResizable(true);
		this.window.setLocationRelativeTo(null);
		this.window.addComponentListener(new Listener.WindowResizeListener(this));
	}

	private void formatWindow() {
		this.window.setLayout(new BorderLayout());
		this.formatTitle();
		this.formatSides();
		this.formatBottom();
	}

	private void formatTitle() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel text = new JLabel("Select an image to keep:");
		text.setFont(new Font(text.getFont().getName(), Font.PLAIN, 25));
		topPanel.add(text);
		this.window.add("North", topPanel);
	}
	
	private void formatSides() {
		this.leftPanel = new ImagePanel(Color.GREEN);
		this.rightPanel = new ImagePanel(Color.PINK);
		this.handleWindowResize();
		this.window.add("West", this.leftPanel);
		this.window.add("East", this.rightPanel);
	}
	
	private void formatBottom() {
		JPanel bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
		bottom.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		this.formatButton(bottom, "Keep Both");
		this.formatButton(bottom, "Delete Both");
		this.window.add("South", bottom);
	}
	
	private void formatButton(JPanel panel, String name) {
		Button b = new Button(name);
		b.setPreferredSize(new Dimension(0, BOTTOM_BUTTON_HEIGHT));
		b.addActionListener(new Listener.ButtonListener(this, name));
		panel.add(b);
	}

	public static void main(String[] args) {
		Window w = new Window();
		w.setVisible(true);
	}
	
//	public static void main(String[] args) throws IOException {
//		Window w = new Window();
//		w.setVisible(true);
//		FileSelector filedialog = new FileSelector();
//		File[] f = filedialog.getFiles();
//		Image l = new Image(f[0]);
//		Image r = new Image(f[1]);
//		w.setImages(l, r);
//	}

}
