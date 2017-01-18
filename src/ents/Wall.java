package ents;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import gui.Drawable;

public class Wall extends Static implements Drawable{
	//TODO: Make all different walls!
	private final BufferedImage img;
	
	public Wall(int x, int y, BufferedImage img){
		super(x, y, "wall");
		this.img = img;
		Drawable.register(this);
	}

	@Override
	public void draw(Graphics g) {
		Drawable.drawSqAt(x, y, img, g);
	}
}
