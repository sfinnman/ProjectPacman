package ents;


import java.util.LinkedList;
import java.util.Queue;

import gui.Drawable;
import utility.DPoint;
import utility.DrawHandler;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;

abstract class Ghost extends Dynamic implements Listener, Drawable {
	
	protected final GhostHouse house;
	protected boolean dead;
	//private final Point scatterPoint;
	protected DPoint target;
	protected double speed;
	protected Queue<Integer> hdgQueue1 = new LinkedList<>();
	protected Queue<Integer> hdgQueue2 = new LinkedList<>();

	protected Ghost(double x, double y, String name, GhostHouse house) {
		super(x, y, name);
		this.house = house;
		DrawHandler.register(this);
		speed = 0.05;
		EventHandler.subscribeEvent("game_think", this);
		// TODO Auto-generated constructor stub
	}
	
	abstract protected void jailBreak();
	
	abstract protected void jail();
	
	
	@Override
	protected void onMove(){
		super.onMove();
		if (dead && new DPoint(this.x, this.y).equals(house.getSpawn())){
			jail();
		}
		if (!hdgQueue2.isEmpty()){
			hdg = hdgQueue2.poll();
		}
	}

	@Override
	public void onRegister(String key, EventData data) {
		go(speed);		
	}
	
	@Override
	protected void onMidCrossed() {
		if (!hdgQueue1.isEmpty()){
			hdg = hdgQueue1.poll();
		}
	}

}
