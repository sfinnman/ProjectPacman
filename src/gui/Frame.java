package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Frame extends JPanel {

	public Frame() {
		setFocusable(true);
		setBackground(Color.BLACK);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
