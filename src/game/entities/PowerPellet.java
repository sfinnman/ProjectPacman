package game.entities;

import game.utility.Drawable;
import game.utility.Listener;

public class PowerPellet implements Drawable, Listener{
	private int x;
	private int y;
	private boolean active;
	
	public PowerPellet(int x, int y){
		this.x = x;
		this.y = y;
		this.active = true;
	}
	
	@Override
	public void onRegister(String key, Object src) {
		if (key.equals("PacMan_move")) {
			// What to do here???? :D
		}
		
	}
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}
	
	
}
