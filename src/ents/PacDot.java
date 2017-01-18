package ents;

import utility.EventHandler;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import gui.Drawable;
import utility.EventData;
import utility.Listener;

public class PacDot extends Static implements Drawable, Listener{
	private boolean active;
	private final BufferedImage img;

	public PacDot(int x, int y, BufferedImage img){
		super(x, y);
		this.active = true;
		this.img = img;
		EventHandler.subscribeEvent("pacman_move", this);
	}
	
	@Override
	public void draw(Graphics2D g2) {
		//Do Background
		if (active){
			//Do Draw
		}
	}

	@Override
	public void onRegister(String key, EventData data) {
		if (key.equals("pacman_move")) {
			// Put logic here.
		}
	}
	
}
