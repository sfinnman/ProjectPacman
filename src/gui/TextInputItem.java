package gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import utility.DrawHandler;
import utility.Drawable;
import utility.EventHandler;
import utility.Point;
import utility.ResourceLoader;
import utility.EventHandler.EventData;
import utility.Listener;

public abstract class TextInputItem implements Listener, Drawable {
	private final String headertext;
	private String text;
	public final Point p;
	public final Point size;
	private boolean focus;
	private float fsize;
	private int counter = 0;
	private boolean textBar = false;

	public TextInputItem(String text, int x, int y, int sizex, int sizey, float fontSize) {
		this.headertext = text;
		this.text = "";
		this.p = new Point(x, y);
		this.size = new Point(sizex, sizey);
		this.fsize = fontSize;
		subscribeEvents();
		DrawHandler.register(this);
	}

	protected void subscribeEvents() {
		EventHandler.subscribeEvent("mouse_clicked", this);
		EventHandler.subscribeEvent("game_tick", this);
		EventHandler.subscribeEvent("key_typed", this);
		EventHandler.subscribeEvent("key_backspace", this);
	}
	
	abstract void onchange(String text);

	@Override
	public void onRegister(String key, EventData data) {
		switch (key) {
		case ("mouse_clicked"):
			if (data.p.isInside(p, size)) {
				focus = true;
			} else {
				focus = false;
			}
			break;
		case ("game_tick"):
			counter = (counter + 1) % 300;
			if (counter == 0) {
				textBar = !textBar;
			}
			break;
		case ("key_typed"):
			if (focus) {
				this.text += String.valueOf((char) (data.p.x));
				onchange(this.text);
			}
			break;
		case ("key_backspace"):
			if (focus && this.text.length() > 0) {
				this.text = this.text.substring(0, this.text.length() - 1);
				onchange(this.text);
			}
			break;
		}
	}

	@Override
	public void draw(Graphics g) {
		float text_size = (focus) ? fsize + 6 : fsize;
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.setFont(ResourceLoader.getFont("crackman", text_size));
		FontMetrics fm = g2.getFontMetrics();
		int twidth = fm.stringWidth(headertext);
		int theight = fm.getHeight();
		g2.drawString(headertext, p.x + size.x / 2 - twidth / 2, p.y + 5 + theight);
		g2.drawLine(p.x, p.y + theight + 15, p.x + size.x, p.y + theight + 15);
		twidth = fm.stringWidth(text);
		g2.drawString(text + ((focus && textBar) ? "_" : ""), p.x + size.x / 2 - twidth / 2, p.y + size.y - 5);

	}
}
