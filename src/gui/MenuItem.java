package gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import utility.DrawHandler;
import utility.Drawable;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.Point;
import utility.ResourceLoader;

public abstract class MenuItem implements Listener, Drawable{
	private final String text;
	public final Point p;
	public final Point size;
	private boolean focus;
	private float fsize;
	
	
	public MenuItem(String text, int x, int y, int sizex, int sizey, float fontSize){
		this.text = text;
		this.p = new Point(x, y);
		this.size = new Point(sizex, sizey);
		this.fsize = fontSize;
		subscribeEvents();
		DrawHandler.register(this);
	}
	
	protected void subscribeEvents(){
		EventHandler.subscribeEvent("mouse_clicked", this);
		EventHandler.subscribeEvent("mouse_moved", this);
	}
	
	abstract protected void doclick();
	
	@Override
	public void onRegister(String key, EventData data) {
		switch(key) {
		case ("mouse_clicked"):
			if (data.p.isInside(p, size)) doclick();
			break;
		case ("mouse_moved"):
			focus = data.p.isInside(p, size);
			break;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		float text_size = (focus)?fsize + 6:fsize;
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.setFont(ResourceLoader.getFont("crackman", text_size));
		FontMetrics fm = g2.getFontMetrics();
		int twidth = fm.stringWidth(text);
		int theight = fm.getHeight();
		g2.drawString(text, p.x + size.x/2 - twidth/2 , p.y + size.y/2 + theight/3);
	}
}
