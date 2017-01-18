package ents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import gui.Drawable;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;

public class PacMan extends Dynamic implements Drawable, Listener {

	public PacMan(double x, double y, int hdg) {
		super(x, y, "Pacman");
		super.hdg = hdg;
		Drawable.register(this);
		EventHandler.subscribeEvent("key_arrow", this);
		EventHandler.subscribeEvent("ghost_move", this);
		EventHandler.subscribeEvent("game_think", this);
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// Let pacman be 45 px large (He is fat)
		int px = (int) (x * 25) - 10;
		int py = (int) (y * 25) - 10; // Top right corner of percmuns current
										// position!
		g2.setColor(Color.YELLOW);
		g2.fillArc(px, py, 45, 45, 30, 300);
	}
	
	private void onThink(){
		
	}

	@Override
	public void onRegister(String key, EventData data) {
		switch (key) {
		case "key_arrow":

			break;
		case "ghost_move":
			break;

		case "game_think":
				onThink();
			break;
		}
	}

}
