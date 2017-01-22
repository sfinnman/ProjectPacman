package ents;

import java.util.LinkedList;
import java.util.Queue;

import game.DEBUG;
import game.GameInfo;
import utility.DPoint;
import utility.DrawHandler;
import utility.Drawable;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.Point;

abstract class Ghost extends Dynamic implements Listener, Drawable {
	protected double speed;
	protected boolean frightened;
	protected boolean dead;
	protected Queue<Integer> hdgQueue1 = new LinkedList<>();
	protected Queue<Integer> hdgQueue2 = new LinkedList<>();

	protected Ghost(double x, double y, String name) {
		super(x, y, name);
		DrawHandler.register(this);
		this.speed = 0.05;
		EventHandler.subscribeEvent("game_think", this);
	}

	abstract protected void jailBreak();

	abstract protected void jail();

	@Override
	protected void onBorder() {
		super.onBorder();
		if (dead && new DPoint(this.x, this.y).equals(GameInfo.jailPos())) {
			jail();
		}
		if (!hdgQueue2.isEmpty()) {
			hdg = hdgQueue2.poll();
		}
	}

	@Override
	public void onRegister(String key, EventData data) {
		if (key.equals("game_think")) {
			go(speed);
		}
	}

	@Override
	protected void onMidCrossed() {
		if (!hdgQueue1.isEmpty()) {
			hdg = hdgQueue1.poll();
		}
	}

	@Override
	protected int hdgDecide(int hdgsel) {
		hdgsel -= (hdg << 2) % 15;
		DPoint target = getTarget();
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
	
	abstract protected DPoint getTarget();

}
