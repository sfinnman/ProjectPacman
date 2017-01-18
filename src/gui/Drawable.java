package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import game.DEBUG;

public interface Drawable {
	public static List<Drawable> library = new ArrayList<>();
	public void draw(Graphics g);
	
	public static void register(Drawable d){
		library.add(d);
	}
	
	public static void unregister(Drawable d){
		library.remove(d);
	}
	
	public static void drawAll(Graphics g) {
		for(Drawable toDraw : library) {
			toDraw.draw(g);
		}
	}
	
	public static void drawSqAt(double x, double y, BufferedImage img, Graphics g){
		int px = (int)x*25;
		int py = (int)y*25;
		g.drawImage(img, px, py, null);
	}
}
