package ents;

import java.awt.Graphics;

import gui.Drawable;
import utility.EventHandler.EventData;
import utility.Listener;

public class PacMan extends Dynamic implements Drawable, Listener {

	public PacMan(double x, double y, int hdg) {
		super(x, y);
		super.hdg = hdg;
	}

	@Override
	public void draw(Graphics g) {
		
	}

	@Override
	public void onRegister(String key, EventData data) {
		switch(key){
		case "key_arrow":
			
			break;
		case "ghost_move":
			break;
		}
		
	}

}
