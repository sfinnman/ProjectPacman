package ents;

import java.awt.Graphics;

import gui.Drawable;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.Point;

abstract class Ghost extends Dynamic implements Listener, Drawable {
	
	//private final Jail door;
	//private final Point spawn;
	private boolean alive;
	//private final Point scatterPoint;
	private Point target;

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
