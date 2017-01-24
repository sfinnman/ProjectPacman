package ents;

import utility.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import game.GameInfo;
import utility.DPoint;
import utility.DrawHandler;
import utility.Drawable;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;

abstract class Ghost extends Dynamic implements Listener, Drawable {
	protected boolean frightened;
	protected boolean dead;
	protected Queue<Integer> hdgQueue = new LinkedList<>();

	protected Ghost(double x, double y, String name) {
		super(x, y, name);
		speed = GameInfo.getSpeed() * 0.5;
		DrawHandler.register(this);
		EventHandler.subscribeEvent("game_think", this);
		EventHandler.subscribeEvent("powerpellet_eat", this);
		EventHandler.subscribeEvent("pacman_move", this);
		EventHandler.subscribeEvent("scatter", this);
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
		EventHandler.triggerEvent("ghost_move", new EventData(this, new Point(this.x, this.y)));
	}

	@Override
	public void onRegister(String key, EventData data) {
		if (key.equals("game_think")) {
			go(speed * 0.95 * ((frightened) ? 0.7 : 1));

		} else if (key.equals("scatter") && hdgQueue.isEmpty() ) {
			hdg = (hdg << 2) % 15;
		} else if (key.equals("powerpellet_eat")) {
			if (hdgQueue.isEmpty())
				hdg = (hdg << 2) % 15;
			speed *= 0.7;
			setFrightened();
		} else if (key.equals("pacman_move") && data.p.equals(new Point(this.getx(), this.gety()))) {
			this.kill();
		}
	}

	@Override
	protected void onMidCrossed() {
		if (!hdgQueue.isEmpty()) {
			hdg = hdgQueue.poll();
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
			dead = true;
			frightened = false;
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

	private void setFrightened() {
		frightened = true;
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				frightened = false;

			}
		};
		Timer timer = new Timer();
		timer.schedule(task, 7000);
	}

}
