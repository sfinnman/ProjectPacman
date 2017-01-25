package ents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game.GameInfo;
import utility.DPoint;
import utility.Point;

public class Blinky extends Ghost{

	public Blinky(double x, double y) {
		super(x, y, 4, "blinky");
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor((frightened)?Color.BLUE:Color.RED);
		g2.fillOval((int)(this.x*25), (int)(this.y*25), 25, 25);
	}

	@Override
	protected void jailBreak() {
		hdgQueue.offer(8);
		hdgQueue.offer(8);
		hdgQueue.offer(8);
		hdgQueue.offer(8);
		hdgQueue.offer(8);
		hdgQueue.offer(8);
		hdgQueue.offer(8);
		hdgQueue.offer(4);
	}

	@Override
	protected void jail() {
		hdgQueue.offer(2);
		hdgQueue.offer(2);
		hdgQueue.offer(2);
		hdgQueue.offer(2);
		hdgQueue.offer(2);
		hdgQueue.offer(2);
		hdgQueue.offer(0);
	}
	
	@Override
	protected void respawn(){
		new Blinky(startx, starty);
	}
	
	@Override
	protected void crossedBorder(){
		super.crossedBorder();
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
