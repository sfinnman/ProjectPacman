package ents;

import utility.EventHandler;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gui.Drawable;
import utility.EventData;
import utility.Listener;

public class PacDot extends Static implements Drawable, Listener{
	private final BufferedImage img;

	public PacDot(int x, int y, BufferedImage img){
		super(x, y);
		this.img = img;
		EventHandler.subscribeEvent("pacman_move", this);
		Drawable.register(this);
	}
	
	@Override
	public void draw(Graphics g) {
		Drawable.drawSqAt(x, y, img, g);
	}

	@Override
	public void onRegister(String key, EventData data) {
		if (key.equals("pacman_move")) {
			if (data.x == this.x && data.y == this.y) {
				EventHandler.unsubscribeEvent("pacman_move", this);
				Drawable.unregister(this);
				EventHandler.triggerEvent("pacdot_eat", new EventData(this, x, y));
			}
		}
	}
	
}
