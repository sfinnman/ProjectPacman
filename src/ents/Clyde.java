package ents;

import game.GameInfo;
import utility.DPoint;
import utility.EventHandler;
import utility.EventHandler.EventData;

public class Clyde extends Ghost{
	
	private int release;

	public Clyde(double x, double y) {
		super(x, y, 0, "clyde");
		release = 50;
		EventHandler.subscribeEvent("pacdot_eat", this);
	}

	@Override
	protected void jailBreak() {
		hdgQueue.offer(4);
		hdgQueue.offer(4);
		hdgQueue.offer(4);
		hdgQueue.offer(4);
		hdgQueue.offer(4);
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
		hdgQueue.offer(1);
		hdgQueue.offer(1);
		hdgQueue.offer(1);
		hdgQueue.offer(1);
		hdgQueue.offer(0);
	}
	
	@Override
	protected void respawn(){
		new Clyde(startx, starty);
	}

	@Override
	protected DPoint getTarget() {
		DPoint pacpos = GameInfo.pacmanPos();
		if (!GameInfo.isScatter() && pacpos.distance(new DPoint(this.x + 0.5, this.y + 0.5))>8){
			return pacpos;
		} else {
			return new DPoint(0, 35);
		}
		
	}
	
	@Override
	public void onRegister(String key, EventData data){
		super.onRegister(key, data);
		if (key.equals("pacdot_eat") && (--release)==0){
			jailBreak();
			EventHandler.unsubscribeEvent("pacdot_eat", this);
		}
	}

}
