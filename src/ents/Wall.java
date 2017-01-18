package ents;

import gui.Drawable;

public class Wall extends Static implements Drawable{
	//TODO: Make all different walls!
	
	private final String texture;
	
	public Wall(int x, int y, String texture){
		super(x, y);
		this.texture = texture;
	}

	@Override
	public void draw() {
		
		
	}
}
