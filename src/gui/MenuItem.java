package gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;

public abstract class MenuItem implements Listener, Drawable{
	private final String text;
	public final int x;
	public final int y;
	public final int sizex;
	public final int sizey;
	
	
	public MenuItem(String text, int x, int y, int sizex, int sizey){
		this.text = text;
		this.x = x;
		this.y = y;
		this.sizex = sizex;
		this.sizey = sizey; 
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
		int relx = data.x -x;
		int rely = data.y -y;
		if (relx>0 && relx<sizex && rely>0 && rely<sizey){
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
		g2.drawString(text, x + sizex/2 - twidth/2 , y + sizey/2 + theight/2);
		g2.drawRect(x, y, sizex, sizey);
	}
}
