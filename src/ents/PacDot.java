package ents;

import utility.EventHandler;
import gui.Drawable;
import utility.EventData;
import utility.Listener;

public class PacDot extends Static implements Drawable, Listener{
	private boolean active;

	public PacDot(int x, int y){
		super(x, y);
		this.active = true;
		EventHandler.subscribeEvent("pacman_move", this);
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
		if (key.equals("pacman_move")) {
			// Put logic here.
		}
	}
	
}
