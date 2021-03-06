package ents;

import utility.Point;
import utility.ResourceLoader;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import game.DEBUG;
import game.Game;
import game.GameInfo;
import game.LevelSettings;
import gui.GameOverlay;
import utility.DPoint;
import utility.EventHandler;
import utility.EventHandler.EventData;

abstract class Ghost extends Dynamic {
	protected boolean dead;
	protected Queue<Integer> hdgQueue = new LinkedList<>();

	protected Ghost(double x, double y, int hdg, String name) {
		super(x, y, hdg, name);
		EventHandler.instance().subscribeEvent("game_think", this);
		EventHandler.instance().subscribeEvent("pacman_move", this);
		EventHandler.instance().subscribeEvent("scatter", this);
	}

	@Override
	public void onRegister(String key, EventData data) { // Make sure to hook
															// relevant events!
		super.onRegister(key, data);
		switch (key) {
		case ("game_think"):
			onthink();
			break;
		case ("scatter"):
			reverse();
			break;
		case ("pacman_move"):
			if (data.p.equals(new Point(this.getx(), this.gety())))
				this.kill();
			break;
		}
	}

	private void onthink() {
		double speed = LevelSettings.topSpeed;
		if (!dead) {
			if (frightened) {
				speed *= LevelSettings.ghostFrtMlt();
			} else {
				if (this.tileSpeedMod != 1) {
					speed *= this.tileSpeedMod;
				} else {
					speed *= LevelSettings.ghostNrmMlt();
				}
			}
		}
		go(speed);
	}

	private void reverse() {
		if (hdgQueue.isEmpty() && !dead)
			hdg = (hdg << 2) % 15;
	}

	protected void kill() {
		if (frightened) {
			int score = GameInfo.eatGhost();
			GameOverlay.showScore(new DPoint(this.x, this.y), score);
			dead = true;
			frightened = false;
			DPoint target = GameInfo.jailPos();
			int hdgsel = hdg + (hdg << 2) % 15;
			hdg = optimalHdg(hdgsel, target);
		} else if (!dead) {
			DEBUG.print("LOSE!");
			EventHandler.instance().killCurrent(); // Make sure pacman doesnt
													// eat whatever
			// is in this tile. Kill all current
			// executions and call for a game loss!
			EventHandler.instance().triggerEvent("game_lose", null);
		}
	}

	@Override
	protected void onFrightened() {
		reverse();
		if (!dead) {
			super.frightened = true;
		}
	}

	@Override
	protected void onBorder() {
		super.onBorder();
		if (dead && new DPoint(this.x, this.y).equals(GameInfo.jailPos())) {
			hdg = 2;
			jail();
		}
		doHdgQueue();
	}

	@Override
	protected void crossedBorder() {
		EventHandler.instance().triggerEvent("ghost_move", new EventData(this, new Point(this.getx(), this.gety())));
	}

	@Override
	protected void onMidCrossed() {
		doHdgQueue();
	}

	@Override
	protected int hdgDecide(int hdgsel) {
		hdgsel -= (hdg << 2) % 15;
		if (frightened) {
			return randomHdg(hdgsel);
		}
		DPoint target = (dead) ? GameInfo.jailPos() : getTarget();
		return optimalHdg(hdgsel, target);
	}

	private void doHdgQueue() {
		if (!hdgQueue.isEmpty()) {
			hdg = hdgQueue.poll();
			if (hdgQueue.isEmpty() && dead) {
				dead = false;
				jailBreak();
			}
		}
	}

	private int optimalHdg(int hdgsel, DPoint target) {
		double dist = 255;
		double compare = 0;
		int hdg = 0;
		for (int i = 1; i < 9; i *= 2) {
			if ((hdgsel & i) > 0) {
				Point p = GameInfo.calcHdg(i);
				compare = new DPoint(this.x + p.x, this.y + p.y).distance(target);
				if (compare < dist) {
					dist = compare;
					hdg = i;
				}
			}
		}
		return hdg;
	}

	private int randomHdg(int hdgsel) {
		Random r = new Random();
		List<Integer> list = new ArrayList<>();
		for (int i = 1; i < 9; i *= 2)
			if ((hdgsel & i) > 0)
				list.add(i);
		return list.get(r.nextInt(list.size()));
	}

	abstract protected DPoint getTarget();

	abstract protected void jailBreak();

	abstract protected void jail();

	@Override
	public void draw(Graphics g) { // Renders ghosts!
		int x = (int) (this.x * 25) - 10;
		int y = (int) (this.y * 25) - 10;
		if (!dead) {
			BufferedImage ghost = ResourceLoader.getImage(name);
			BufferedImage fright = ResourceLoader.getImage("frightened");
			int time_left = GameInfo.getFrightTime();
			if (time_left < (2000 / Game.UPDATE) && ((time_left / 250) * Game.UPDATE) % 2 == 1) {
				fright = ResourceLoader.getImage("time");
			}
			g.drawImage((frightened) ? fright : ghost, x, y, null);
		}
		if (!frightened) {
			Graphics2D g2 = (Graphics2D) g;
			Point hdg = GameInfo.calcHdg(this.hdg);
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHints(rh);
			g2.setColor(Color.WHITE);
			g2.fillOval(x + 10, y + 11, 10, 12);
			g2.fillOval(x + 25, y + 11, 10, 12);
			g2.setColor(Color.BLACK);
			g2.fillOval(x + 12 + hdg.x * 2, y + 13 + hdg.y * 2, 6, 8);
			g2.fillOval(x + 27 + hdg.x * 2, y + 13 + hdg.y * 2, 6, 8);
		}
	}

}
