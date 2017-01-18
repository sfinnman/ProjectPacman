package game.entities;

import game.utility.Drawable;
import game.utility.EventHandler;
import game.utility.Listener;

public class PacDot implements Drawable, Listener{
	private final int x;
	private final int y;
	private boolean active;

	public PacDot(int x, int y){
		this.x = x;
		this.y = y;
		this.active = true;
		EventHandler.listenEvent("PacMan_move", this);
	}
	
	@Override
	public void draw() {
		//Do Background
		if (active){
			//Do Draw
		}
	}

	@Override
	public void onRegister(String key, Object src) {
		if (key.equals("PacMan_move")) {
			// Put logic here.
		}
	}
	
}
