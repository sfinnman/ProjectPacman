package ents;

import game.GameInfo;
import utility.DPoint;
import utility.Point;

public class Blinky extends Ghost{

	public Blinky(double x, double y) {
		super(x, y, 4, "blinky");
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
