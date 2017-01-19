package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import game.DEBUG;
import utility.Point;

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
		for(int i = 0; i<library.size(); i++){
			library.get(i).draw(g);
		}
	}
	
	public static void drawSqAt(Point p, BufferedImage img, Graphics g){
		int px = p.x*25;
		int py = p.y*25;
		g.drawImage(img, px, py, null);
	}
}
