package ents;

import utility.EventHandler;
import gui.Drawable;
import utility.EventData;
import utility.Listener;

public class PowerPellet extends Static implements Drawable, Listener{
	private boolean active;
	
	public PowerPellet(int x, int y){
		super(x, y);
		this.active = true;
		EventHandler.subscribeEvent("pacman_move", this);
	}
	
	@Override
	public void onRegister(String key, EventData data) {
		if (key.equals("pacman_move")) {
			if (data.x == this.x && data.y == this.y) {
				active = false;
				EventHandler.unsubscribeEvent("pacman_move", this);
				EventHandler.triggerEvent("powerpellet_eat", new EventData(this, x, y));
			}
		}
		
	}
	@Override
	public void draw() {
		//Draw background here...
		if (active) {
			//Draw pellet here...
		}
	}
	
	
}
