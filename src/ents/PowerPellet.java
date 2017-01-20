package ents;

import utility.DrawHandler;
import utility.EventHandler;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gui.Drawable;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.Point;

public class PowerPellet extends Static implements Drawable, Listener{
	private final BufferedImage img;
	
	public PowerPellet(Point p, BufferedImage img){
		super(p, "powerpellet");
		this.img = img;
		EventHandler.subscribeEvent("pacman_move", this);
		DrawHandler.register(this);
	}
	
	@Override
	public void onRegister(String key, EventData data) {
		if (key.equals("pacman_move")) {
			if (p.equals(data.p)) {
				EventHandler.unsubscribeEvent("pacman_move", this);
				DrawHandler.unregister(this);
				EventHandler.triggerEvent("powerpellet_eat", new EventData(this, p));
			}
		}
	}
	@Override
	public void draw(Graphics g) {
		DrawHandler.drawSqAt(p, img, g);
	}
	
	
}
