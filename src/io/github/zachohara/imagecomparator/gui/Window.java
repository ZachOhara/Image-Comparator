package io.github.zachohara.imagecomparator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window {

	private JFrame window;
	private JPanel leftPanel;
	private JPanel rightPanel;

	public Window() {
		this.initializeWinow();
		this.formatWindow();
	}

	public void setVisible(boolean visible) {
		this.window.setVisible(visible);
	}
	
	public void handleWindowResize() {
		System.out.println("Resize! " + System.currentTimeMillis());
	}

	private void initializeWinow() {
		this.window = new JFrame("Image Comparator by Zach Ohara");
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setSize(1000, 600);
		this.window.setResizable(true);
		this.window.setLocationRelativeTo(null);
		this.window.addComponentListener(new WindowResizeListener(this));
	}

	private void formatWindow() {
		this.window.setLayout(new BorderLayout());
		this.formatTitle();
		this.leftPanel = new JPanel();
		this.rightPanel = new JPanel();
		this.formatSides();
	}

	private void formatTitle() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		JLabel text = new JLabel("Select an image to keep:");
		text.setFont(new Font(text.getFont().getName(), Font.PLAIN, 25));
		topPanel.add(text);
		this.window.add("North", topPanel);
	}
	
	private void formatSides() {
		JPanel[] bothSides = {this.leftPanel, this.rightPanel};
		for (JPanel side : bothSides) {
			int width = this.window.getSize().width / 2;
			side.add(Box.createRigidArea(new Dimension(width, 0)));
		}
		this.leftPanel.setBackground(Color.BLUE);
		this.rightPanel.setBackground(Color.RED);
		this.window.add("West", this.leftPanel);
		this.window.add("East", this.rightPanel);
	}

	public static void main(String[] args) {
		Window w = new Window();
		w.setVisible(true);
	}

}
