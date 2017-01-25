package ents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game.GameInfo;
import utility.DPoint;
import utility.EventHandler;
import utility.EventHandler.EventData;

public class Clyde extends Ghost{
	
	private final int release;

	public Clyde(double x, double y) {
		super(x, y, 0, "clyde");
		release = 50;
		EventHandler.subscribeEvent("pacdot_eat", this);
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor((frightened)?Color.BLUE:Color.ORANGE);
		g2.fillOval((int)(this.x*25), (int)(this.y*25), 25, 25);
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
		if (key.equals("pacdot_eat") && GameInfo.getEaten()>release){
			jailBreak();
			EventHandler.unsubscribeEvent("pacdot_eat", this);
		}
	}

}
