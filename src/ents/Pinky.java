package ents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game.GameInfo;
import utility.DPoint;
import utility.Point;

public class Pinky extends Ghost{
	
	public Pinky(double x, double y) {
		super(x, y, "pinky");
		this.jailBreak();
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.PINK);
		g2.fillOval((int)(this.x*25), (int)(this.y*25), 25, 25);
	}

	@Override
	protected void jailBreak() {
		hdgQueue1.offer(8);
		hdgQueue1.offer(8);
		hdgQueue1.offer(8);
		hdgQueue1.offer(8);
		hdgQueue1.offer(4);
	}

	@Override
	protected void jail() {
		hdgQueue1.offer(2);
		hdgQueue1.offer(2);
		hdgQueue1.offer(2);
		hdgQueue1.offer(2);
		hdgQueue1.offer(0);
	}

	@Override
	protected DPoint getTarget() {
		if (GameInfo.isScatter()){
			return new DPoint(2, 0);
		} else {
			Point hdg = GameInfo.calcHdg(GameInfo.pacmanHdg());
			DPoint pacpos = GameInfo.pacmanPos();
			return new DPoint(pacpos.x + hdg.x*4, pacpos.y + hdg.y*4);
		}
	}

}
