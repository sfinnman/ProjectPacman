package ents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import gui.Drawable;
import gui.Frame;
import utility.DrawHandler;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;

public class PacMan extends Dynamic implements Drawable, Listener {
	
	private int nextmove;

	public PacMan(double x, double y, int hdg) {
		super(x, y, "pacman");
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
		int mouthOpen = (int)(Math.sin(Math.PI*((x + y)%1))*45);
		int angStart = 90 - hdg*90 + ((hdg&4)>>2)*90 + mouthOpen;
		int tots = 360 - mouthOpen*2;
		g2.fillArc(px, py, 45, 45, angStart, tots);
		g2.fillArc(px + Frame.WIDTH, py, 45, 45, angStart, tots);
		g2.fillArc(px - Frame.WIDTH, py, 45, 45, angStart, tots);
		g2.fillArc(px, py + Frame.HEIGHT, 45, 45, angStart, tots);
		g2.fillArc(px, py - Frame.HEIGHT, 45, 45, angStart, tots);
	}
	
	private void onThink(){
		go(0.05);
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
