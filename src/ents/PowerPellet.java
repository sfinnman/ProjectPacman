package ents;

import utility.EventHandler;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import gui.Drawable;
import utility.EventData;
import utility.Listener;

public class PowerPellet extends Static implements Drawable, Listener{
	private boolean active;
	private final BufferedImage img;
	
	public PowerPellet(int x, int y, BufferedImage img){
		super(x, y);
		this.active = true;
		this.img = img;
		EventHandler.subscribeEvent("pacman_move", this);
	}
	
	@Override
	public void onRegister(String key, EventData data) {
		if (key.equals("pacman_move")) {
			if (data.x == this.x && data.y == this.y) {
				active = false;
				EventHandler.unsubscribeEvent("pacman_move", this);
				EventHandler.triggerEvent("powerpellet_eat", new EventData(this, x, y));
			}
		}
		
	}
	@Override
	public void draw(Graphics2D g2) {
		if (active) {
			//Draw pellet here...
		}
	}
	
	
}
