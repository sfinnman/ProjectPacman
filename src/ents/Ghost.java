package ents;

import utility.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import game.DEBUG;
import game.GameInfo;
import game.LevelSettings;
import utility.DPoint;
import utility.DrawHandler;
import utility.Drawable;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;

abstract class Ghost extends Dynamic {
	protected boolean dead;
	protected Queue<Integer> hdgQueue = new LinkedList<>();

	protected Ghost(double x, double y, int hdg, String name) {
		super(x, y, hdg, name);
		EventHandler.subscribeEvent("game_think", this);
		EventHandler.subscribeEvent("pacman_move", this);
		EventHandler.subscribeEvent("scatter", this);
	}

	private void onthink() {
		double speed = LevelSettings.topSpeed;
		if (!dead) {
			if (frightened) {
				speed *= LevelSettings.ghostFrtMlt();
			} else {
				if (this.tileSpeedMod != 1) {
					speed *= LevelSettings.ghostTnlMlt();
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

	@Override
	protected void onFrightened() {
		reverse();
		if (!dead) {
			super.frightened = true;
		}
	}

	abstract protected void jailBreak();

	abstract protected void jail();

	@Override
	protected void onBorder() {
		super.onBorder();
		if (dead && new DPoint(this.x, this.y).equals(GameInfo.jailPos())) {
			hdg = 2;
			jail();
		}
		if (!hdgQueue.isEmpty()) {
			hdg = hdgQueue.poll();
			if (hdgQueue.isEmpty() && dead) {
				dead = false;
				jailBreak();
			}
		}
	}

	@Override
	protected void crossedBorder() {
		EventHandler.triggerEvent("ghost_move", new EventData(this, new Point(this.getx(), this.gety())));
	}

	@Override
	public void onRegister(String key, EventData data) {
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

	@Override
	protected void onMidCrossed() {
		if (!hdgQueue.isEmpty()) {
			hdg = hdgQueue.poll();
			DEBUG.print(this.toString() + " choosing hdg: " + hdg);
			if (hdgQueue.isEmpty() && dead) {
				dead = false;
				jailBreak();
			}
		}
	}

	@Override
	protected int hdgDecide(int hdgsel) {
		hdgsel -= (hdg << 2) % 15;
		if (frightened) {
			return randomHdg(hdgsel);
		}
		DPoint target = (dead) ? GameInfo.jailPos() : getTarget();
		double dist = 255;
		double compare = 0;
		int hdg = 0;
		for (int i = 1; i < 9; i *= 2) {
			if ((hdgsel & i) > 0) {
				int x = (i & 1) - ((i & 4) >> 2);
				int y = ((i & 2) >> 1) - ((i & 8) >> 3);
				compare = new DPoint(this.x + x, this.y + y).distance(target);
				if (compare < dist) {
					dist = compare;
					hdg = i;
				}
			}
		}
		return hdg;
	}

	protected void kill() {
		if (frightened) {
			EventHandler.triggerEvent("ghost_die", new EventData(this, new Point(this.getx(), this.gety())));
			dead = true;
			frightened = false;
			DPoint target = GameInfo.jailPos();
			Point p = GameInfo.calcHdg(hdg);
			if (target.distance(new DPoint(x + p.x, y + p.y)) > target.distance(new DPoint(x-p.y, y-p.y))){
				hdg = (hdg<<2)%15;
			}
		} else if (!dead) {
			DEBUG.print("LOSE!");
			if (GameInfo.getLives() == 0){
				EventHandler.triggerEvent("game_rip", null);
			} else {
				EventHandler.triggerEvent("game_lose", null);
			}
		}
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

}
