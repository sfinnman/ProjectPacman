package ents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import utility.DPoint;
import utility.GameInfo;

public class Inky extends Ghost{

	public Inky(double x, double y) {
		super(x, y, "inky");
		jailBreak();
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.CYAN);
		g2.fillOval((int)(this.x*25), (int)(this.y*25), 25, 25);
	}

	@Override
	protected void jailBreak() {
		hdgQueue1.offer(1);
		hdgQueue2.offer(1);
		hdgQueue1.offer(1);
		hdgQueue1.offer(1);
		hdgQueue2.offer(8);
		hdgQueue1.offer(8);
		hdgQueue1.offer(8);
		hdgQueue1.offer(8);
		hdgQueue1.offer(4);
		
	}

	@Override
	protected void jail() {
		hdgQueue1.offer(2);
		hdgQueue2.offer(2);
		hdgQueue1.offer(2);
		hdgQueue1.offer(2);
		hdgQueue2.offer(4);
		hdgQueue1.offer(4);
		hdgQueue1.offer(4);
		hdgQueue1.offer(4);
		hdgQueue1.offer(0);
	}

	@Override
	protected DPoint getTarget() {
		DPoint blinkypos = GameInfo.blinkyPos();
		DPoint pacpos = GameInfo.pacmanPos();
		if (GameInfo.isScatter()){
			return new DPoint(25, 36);
		} else {
			return new DPoint(2*pacpos.x - blinkypos.x, 2*pacpos.y - blinkypos.y);
		}
	}

}
