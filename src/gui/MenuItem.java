package gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.Point;

public abstract class MenuItem implements Listener, Drawable{
	private final String text;
	public final Point p;
	public final Point size;
	
	
	public MenuItem(String text, int x, int y, int sizex, int sizey){
		this.text = text;
		this.p = new Point(x, y);
		this.size = new Point(sizex, sizey);
		subscribeEvents();
	}
	
	public void subscribeEvents(){
		EventHandler.subscribeEvent("mouse_clicked", this);
	}
	
	public void unsubscribeEvents(){
		EventHandler.unsubscribeEvent("mouse_clicked", this);
	}
	
	abstract void doclick();
	
	@Override
	public void onRegister(String key, EventData data) {
		if (data.p.isInside(p, size)){
			doclick();
		}
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		FontMetrics fm = g2.getFontMetrics();
		int twidth = fm.stringWidth(text);
		int theight = fm.getHeight();
		g2.drawString(text, p.x + size.x/2 - twidth/2 , p.y + size.y/2 + theight/2);
		g2.drawRect(p.x, p.y, size.x, size.y);
	}
}
