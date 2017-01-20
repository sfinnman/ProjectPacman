package utility;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import gui.Drawable;

public class DrawHandler {
	public static Deque<List<Drawable>> frames;
	public static List<Drawable> library;
	
	public static void init(){
		frames = new LinkedList<>();
		library = new ArrayList<>();
		frames.push(library);
	}
	
	public static void pushFrame(){
		library = new ArrayList<>();
		frames.push(library);
	}
	
	public static void popFrame(){
		frames.pop();
		library = frames.peek();
	}
	
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
