package ents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game.DEBUG;
import game.GameInfo;
import gui.Frame;
import utility.DPoint;
import utility.DrawHandler;
import utility.Drawable;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.Point;

public class PacMan extends Dynamic{

	private int nextmove;

	public PacMan(double x, double y, int hdg) {
		super(x, y, hdg, "pacman");
		nextmove = hdg;
		EventHandler.subscribeEvent("key_arrow", this);
		EventHandler.subscribeEvent("ghost_move", this);
		EventHandler.subscribeEvent("game_think", this);
		this.speed = GameInfo.getSpeed();
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int px = (int) (x * 25) - 10;
		int py = (int) (y * 25) - 10;
		g2.setColor(Color.YELLOW);
		int mouthOpen = (int) (Math.sin(Math.PI * ((x + y) % 1)) * 45);
		int angStart = 90 - hdg * 90 + ((hdg & 4) >> 2) * 90 + mouthOpen;
		int tots = 360 - mouthOpen * 2;
		g2.fillArc(px, py, 45, 45, angStart, tots);
		g2.fillArc(px + Frame.WIDTH, py, 45, 45, angStart, tots);
		g2.fillArc(px - Frame.WIDTH, py, 45, 45, angStart, tots);
		g2.fillArc(px, py + Frame.HEIGHT, 45, 45, angStart, tots);
		g2.fillArc(px, py - Frame.HEIGHT, 45, 45, angStart, tots);
	}

	private void onThink() {
		go(speed);
	}

	@Override
	public void onRegister(String key, EventData data) {
		super.onRegister(key, data);
		switch (key) {
		case "key_arrow":
			nextmove = data.p.x;
			if (nextmove == (hdg << 2) % 15) {
				hdg = data.p.x;
			}
			break;
		case "ghost_move":
			if (new Point(this.getx(), this.gety()).equals(data.p))
				((Ghost)data.src).kill();
			break;

		case "game_think":
			onThink();
			break;
		}
	}
	
	@Override
	protected void respawn(){
		new PacMan(startx, starty, starthdg);
	}

	@Override
	protected void crossedBorder() {
		EventHandler.triggerEvent("pacman_move", new EventData(this, new Point(getx(), gety())));
		GameInfo.setPacmanPos(new Point(this.getx(), this.gety()));
		GameInfo.setPacmanHdg(hdg);
	}

	@Override
	protected int hdgDecide(int available) {
		return nextmove;
	}

	@Override
	protected void onMidCrossed() {
	}

}
