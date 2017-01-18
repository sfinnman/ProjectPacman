package ents;

import utility.Drawable;
import utility.EventHandler;
import utility.EventData;
import utility.Listener;

public class PacDot extends Static implements Drawable, Listener{
	private boolean active;

	public PacDot(int x, int y){
		super(x, y);
		this.active = true;
		EventHandler.subscribeEvent("PacMan_move", this);
	}
	
	@Override
	public void draw() {
		//Do Background
		if (active){
			//Do Draw
		}
	}

	@Override
	public void onRegister(String key, EventData data) {
		if (key.equals("PacMan_move")) {
			// Put logic here.
		}
	}
	
}
