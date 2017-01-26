package ents;

import game.GameInfo;
import utility.DPoint;
import utility.Point;

public class Pinky extends Ghost{
	
	public Pinky(double x, double y) {
		super(x, y, 0, "pinky");
		jailBreak();
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
		new Pinky(startx, starty);
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
