package ents;

import gui.Drawable;
import utility.EventData;
import utility.Listener;

public class PacMan extends Dynamic implements Drawable, Listener {

	protected PacMan(double x, double y) {
		super(x, y);
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegister(String key, EventData data) {
		switch(key){
		case "key_arrow":
			
			break;
		case "ghost_move":
			break;
		}
		
	}

}
