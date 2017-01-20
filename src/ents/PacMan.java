package ents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import gui.Drawable;
import utility.DrawHandler;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;

public class PacMan extends Dynamic implements Drawable, Listener {
	
	private int nextmove;

	public PacMan(double x, double y, int hdg) {
		super(x, y, "Pacman");
		super.hdg = hdg;
		nextmove = hdg;
		DrawHandler.register(this);
		EventHandler.subscribeEvent("key_arrow", this);
		EventHandler.subscribeEvent("ghost_move", this);
		EventHandler.subscribeEvent("game_think", this);
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int px = (int) (x * 25) - 10;
		int py = (int) (y * 25) - 10;
		g2.setColor(Color.YELLOW);
		g2.fillArc(px, py, 45, 45, 30, 300);
	}
	
	private void onThink(){
		move(0.05);
	}

	@Override
	public void onRegister(String key, EventData data) {
		switch (key) {
		case "key_arrow":
				nextmove = data.p.x;
				if (nextmove == (hdg<<2)%15){
					hdg = data.p.x;
				}
			break;
		case "ghost_move":
			break;

		case "game_think":
				onThink();
			break;
		}
	}

	@Override
	protected int hdgDecide(int available) {
		return nextmove;
	}

}
