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
	private boolean focus;
	private int alpha;
	private boolean clickable;
	
	
	public MenuItem(String text, int x, int y, int sizex, int sizey, boolean clickable){
		this.text = text;
		this.p = new Point(x, y);
		this.size = new Point(sizex, sizey);
		this.clickable = clickable;
		subscribeEvents();
	}
	
	public void subscribeEvents(){
		EventHandler.subscribeEvent("mouse_clicked", this);
		EventHandler.subscribeEvent("mouse_moved", this);
	}
	
	public void unsubscribeEvents(){
		EventHandler.unsubscribeEvent("mouse_clicked", this);
		EventHandler.unsubscribeEvent("mouse_moved", this);
	}
	
	abstract void doclick();
	
	@Override
	public void onRegister(String key, EventData data) {
		if (key.equals("mouse_clicked")){
			if (data.p.isInside(p, size)){
				doclick();
			}
		}
		if (key.equals("mouse_moved")){
			focus = data.p.isInside(p, size)&&clickable;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		if (focus) {
			if (alpha < 235) {
				alpha += 20;
			} else {
				alpha = 255;
			}
		} else {
			if (alpha > 20) {
				alpha -= 20;
			} else {
				alpha = 0;
			}
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		FontMetrics fm = g2.getFontMetrics();
		int twidth = fm.stringWidth(text);
		int theight = fm.getHeight();
		g2.drawString(text, p.x + size.x/2 - twidth/2 , p.y + size.y/2 + theight/2);
		g2.setColor(new Color(255, 255, 255, alpha));
		g2.drawRect(p.x, p.y, size.x, size.y);
	}
}
