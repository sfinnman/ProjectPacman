package ents;

import utility.DrawHandler;
import utility.EventHandler;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gui.Drawable;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.Point;

public class PacDot extends Static implements Drawable, Listener{
	private final BufferedImage img;

	public PacDot(Point p, BufferedImage img){
		super(p, "PacDot");
		this.img = img;
		EventHandler.subscribeEvent("pacman_move", this);
		DrawHandler.register(this);
	}
	
	@Override
	public void draw(Graphics g) {
		DrawHandler.drawSqAt(p, img, g);
	}

	@Override
	public void onRegister(String key, EventData data) {
		if (key.equals("pacman_move")) {
			if (data.p.equals(this.p)) {
				EventHandler.unsubscribeEvent("pacman_move", this);
				DrawHandler.unregister(this);
				EventHandler.triggerEvent("pacdot_eat", new EventData(this, p));
			}
		}
	}
	
}
