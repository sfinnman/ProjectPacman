package ents;

import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.Point;

public class SlowTile extends Static implements Listener{
	
	public final double mult;
	
	public SlowTile(Point p, double mult){
		super(p, "slowtile", null);
		this.mult = mult;
		EventHandler.subscribeEvent("ghost_move", this);
	}


	@Override
	public void onRegister(String key, EventData src) {
		if (key.equals("ghost_move") && src.p.equals(this.p)){
			Ghost g = (Ghost)src.src;
			g.speedMult(mult);
		}
		
	}}
