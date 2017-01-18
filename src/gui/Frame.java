package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import game.DEBUG;
import utility.EventHandler;
import utility.EventHandler.EventData;

public class Frame extends JPanel implements MouseListener, KeyListener{
	
	public static final int HEIGHT = 900;
	public static final int WIDTH = 700;
	private Timer t;
	
	public Frame() {
		setFocusable(true);
		setBackground(Color.BLACK);
		addMouseListener(this);
		addKeyListener(this);
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				repaint();				
			}
		};
		t = new Timer();
		t.schedule(task, 50, 50);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Drawable.drawAll(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		DEBUG.print("Mouse clicked in screen! at coord: " + e.getX() + ", " + e.getY());
		EventHandler.triggerEvent("mouse_clicked", new EventData(this, e.getX(), e.getY()));
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case(KeyEvent.VK_UP):
			EventHandler.triggerEvent("key_arrow", new EventData(this, 8, 0));
		break;
		case(KeyEvent.VK_RIGHT):
			EventHandler.triggerEvent("key_arrow", new EventData(this, 1, 0));
		break;
		case(KeyEvent.VK_DOWN):
			EventHandler.triggerEvent("key_arrow", new EventData(this, 2, 0));
		break;
		case(KeyEvent.VK_LEFT):
			EventHandler.triggerEvent("key_arrow", new EventData(this, 4, 0));
		break;
		case(KeyEvent.VK_ESCAPE):
			EventHandler.triggerEvent("esc", null);
		break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
