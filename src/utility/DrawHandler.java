package utility;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class DrawHandler {
	private final  static Deque<List<Drawable>> frames = new LinkedList<>();
	
	public static void clear(){
		frames.peek().clear();
	}
	
	public static void pushFrame(){
		frames.push(new ArrayList<>());
	}
	
	public static void popFrame(){
		frames.pop();
	}
	
	public static void register(Drawable d){
		frames.peek().add(d);
	}
	
	public static void unregister(Drawable d){
		frames.peek().remove(d);
	}
	
	public static void drawAll(Graphics g) {
		for(int i = 0; i<frames.peek().size(); i++){
			frames.peek().get(i).draw(g);
		}
	}
	
	public static void drawSqAt(Point p, BufferedImage img, Graphics g){
		int px = p.x*25;
		int py = p.y*25;
		g.drawImage(img, px, py, null);
	}

}
