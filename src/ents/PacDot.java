package ents;

import java.awt.image.BufferedImage;

import utility.DrawHandler;
import utility.EventHandler;
import gui.Drawable;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.Point;

public class PacDot extends Static implements Drawable, Listener {

	public PacDot(Point p, BufferedImage img) {
		super(p, "PacDot", img);
		EventHandler.subscribeEvent("pacman_move", this);
	}

	@Override
	public void onRegister(String key, EventData data) {
		if (key.equals("pacman_move")) {
			if (data.p.equals(this.p)) {
				EventHandler.unsubscribeEvent("pacman_move", this);
				DrawHandler.unregister(this);
				EventHandler.triggerEvent("pacdot_eat", new EventData(this, p));
			}
		}
	}

}
