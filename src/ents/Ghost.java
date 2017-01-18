package ents;

import java.awt.Graphics;

import gui.Drawable;
import utility.EventHandler.EventData;
import utility.Listener;

abstract class Ghost extends Dynamic implements Listener, Drawable {

	protected Ghost(double x, double y, String name) {
		super(x, y, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(String key, EventData data) {
		// TODO Auto-generated method stub
		
	}

}
