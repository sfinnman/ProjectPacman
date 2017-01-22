package ents;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utility.DrawHandler;
import utility.Drawable;
import utility.Point;

public abstract class Static implements Drawable{
	private final BufferedImage img;
	protected final Point p;
	private final String name;
	
	protected Static(Point p, String name, BufferedImage img){
		this.p = p;
		this.name = name;
		this.img = img;
		DrawHandler.register(this);
	}
	
	@Override
	public String toString(){
		return String.format(name + " |%d, %d| ", p.x, p.y);
	}
	
	@Override
	public void draw(Graphics g) {
		DrawHandler.drawSqAt(p, img, g);
	}
	
}
