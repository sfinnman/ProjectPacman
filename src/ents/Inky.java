package ents;

import game.GameInfo;
import utility.DPoint;
import utility.EventHandler;
import utility.EventHandler.EventData;

public class Inky extends Ghost{

	private int release;
	
	public Inky(double x, double y) {
		super(x, y, 0, "inky");
		release = 30;
		EventHandler.subscribeEvent("pacdot_eat", this);
	}
	
	@Override
	protected void jailBreak() {
		hdgQueue.offer(1);
		hdgQueue.offer(1);
		hdgQueue.offer(1);
		hdgQueue.offer(1);
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
		hdgQueue.offer(4);
		hdgQueue.offer(4);
		hdgQueue.offer(4);
		hdgQueue.offer(4);
		hdgQueue.offer(4);
		hdgQueue.offer(0);
	}
	
	@Override
	protected void respawn(){
		new Inky(startx, starty);
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
	
	@Override
	public void onRegister(String key, EventData data){
		super.onRegister(key, data);
		if (key.equals("pacdot_eat") && (--release) == 0){
			jailBreak();
			EventHandler.unsubscribeEvent("pacdot_eat", this);
		}
	}

}
