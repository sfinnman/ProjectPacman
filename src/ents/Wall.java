package ents;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import gui.Drawable;
import utility.Point;

public class Wall extends Static implements Drawable{
	//TODO: Make all different walls!
	private final BufferedImage img;
	
	public Wall(Point p, BufferedImage img){
		super(p, "wall");
		this.img = img;
		Drawable.register(this);
	}

	@Override
	public void draw(Graphics g) {
		Drawable.drawSqAt(p, img, g);
	}
}
