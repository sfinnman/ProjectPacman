package ents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import utility.DPoint;
import utility.GameInfo;
import utility.Point;

public class Blinky extends Ghost{

	public Blinky(double x, double y) {
		super(x, y, "blinky");
		hdg = 4;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.RED);
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
	protected void crossedBorder(){
		GameInfo.setBlinkyPos(new Point(this.getx(), this.gety()));
	}

	@Override
	protected DPoint getTarget() {
		if (GameInfo.isScatter()){
			return new DPoint(22, 0);
		} else {
			return GameInfo.pacmanPos();
		}
	}

}
