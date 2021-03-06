package ents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import game.DEBUG;
import game.GameInfo;
import game.LevelSettings;
import gui.Frame;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Point;

public class PacMan extends Dynamic {

	private int nextmove;

	public PacMan(double x, double y, int hdg) {
		super(x, y, hdg, "pacman");
		nextmove = hdg;
		EventHandler.instance().subscribeEvent("key_arrow", this);
		EventHandler.instance().subscribeEvent("ghost_move", this);
		EventHandler.instance().subscribeEvent("game_think", this);
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int px = (int) (x * 25) - 10;
		int py = (int) (y * 25) - 10;
		g2.setRenderingHints(rh);
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
		double speed = LevelSettings.topSpeed;
		if (frightened) {
			speed *= LevelSettings.pacFrt() * super.tileSpeedMod;
		} else {
			speed *= LevelSettings.pacNrm() * super.tileSpeedMod;
		}
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
				((Ghost) data.src).kill();
			break;

		case "game_think":
			onThink();
			break;
		}
	}

	@Override
	protected void respawn() {
		new PacMan(startx, starty, starthdg);
	}

	@Override
	protected void crossedBorder() {
		EventHandler.instance().triggerEvent("pacman_move", new EventData(this, new Point(getx(), gety())));
		GameInfo.setPacmanPos(new Point(this.getx(), this.gety()));
		GameInfo.setPacmanHdg(hdg);
	}

	@Override
	protected int hdgDecide(int available) {
		return nextmove;
	}

	@Override
	protected void onFrightened() {
		super.frightened = true;
	}

}
