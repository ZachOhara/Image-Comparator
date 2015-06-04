package io.github.zachohara.imagecomparator.gui;

import io.github.zachohara.imagecomparator.image.Image;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window {

	private JFrame window;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel leftInfoPanel;
	private JPanel rightInfoPanel;
	private Component rigidAreaLeft;
	private Component rigidAreaRight;

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
		if (this.rigidAreaLeft != null)
			this.leftPanel.remove(this.rigidAreaLeft);
		if (this.rigidAreaRight != null)
			this.rightPanel.remove(this.rigidAreaRight);
		this.rigidAreaLeft = Box.createRigidArea(new Dimension(newWidth / 2, 0));
		this.rigidAreaRight = Box.createRigidArea(new Dimension(newWidth / 2, 0));
		this.leftPanel.add(this.rigidAreaLeft);
		this.rightPanel.add(this.rigidAreaRight);
		this.window.repaint();
	}
	
	public void handleButtonPress(String button) {
		System.out.println(button);
		//TODO: handle button presses
	}
	
	public void setImages(Image left, Image right) {
		this.setLeftImage(left);
		this.setRightImage(right);
	}
	
	public void setLeftImage(Image left) {
		this.leftInfoPanel.removeAll();
		this.leftInfoPanel.add(new JLabel(left.getName()));
		this.leftInfoPanel.add(new JLabel(left.getDimensionString()));
		this.leftPanel.add("Center", left.toComponenet());
		this.leftInfoPanel.repaint();
		this.leftPanel.repaint();
		this.window.repaint();
	}
	
	public void setRightImage(Image right) {
		this.rightInfoPanel.removeAll();
		this.rightInfoPanel.add(new JLabel(right.getName()));
		this.rightInfoPanel.add(new JLabel(right.getDimensionString()));
		this.rightPanel.add("Center", right.toComponenet());
		this.rightInfoPanel.repaint();
		this.rightPanel.repaint();
		this.window.repaint();
	}

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
		this.formatInfoPanels();
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
		this.leftPanel = new JPanel();
		this.rightPanel = new JPanel();
		this.handleWindowResize();
		this.leftPanel.setLayout(new BorderLayout());
		this.rightPanel.setLayout(new BorderLayout());
		this.leftPanel.setBackground(Color.BLUE);
		this.rightPanel.setBackground(Color.RED);
		this.window.add("West", this.leftPanel);
		this.window.add("East", this.rightPanel);
	}
	
	private void formatInfoPanels() {
		this.leftInfoPanel = new JPanel();
		this.rightInfoPanel = new JPanel();
		this.leftInfoPanel.setLayout(new BoxLayout(this.leftInfoPanel, BoxLayout.Y_AXIS));
		this.rightInfoPanel.setLayout(new BoxLayout(this.rightInfoPanel, BoxLayout.Y_AXIS));
		
		this.leftInfoPanel.add(new JLabel("Image Name Here"));
		this.rightInfoPanel.add(new JLabel("Image Name Here"));
		this.leftInfoPanel.add(new JLabel("100x100"));
		this.rightInfoPanel.add(new JLabel("100x100"));
		
		
		this.leftPanel.add("North", this.leftInfoPanel);
		this.rightPanel.add("North", this.rightInfoPanel);
	}
	
	private void formatBottom() {
		JPanel bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
		Button keepBoth = new Button("Keep Both");
		Button deleteBoth = new Button("Delete Both");
		keepBoth.addActionListener(new Listener.ButtonListener(this, "keep both"));
		deleteBoth.addActionListener(new Listener.ButtonListener(this, "delete both"));
		bottom.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		bottom.add(keepBoth);
		bottom.add(deleteBoth);
		this.window.add("South", bottom);
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
