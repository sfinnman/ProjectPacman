package ents;

import java.awt.image.BufferedImage;

import game.GameInfo;
import utility.DrawHandler;
import utility.Drawable;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.Point;

public class PacDot extends Static implements Drawable, Listener {

	public PacDot(Point p, BufferedImage img) {
		super(p, "PacDot", img);
		EventHandler.instance().subscribeEvent("pacman_move", this);
	}

	@Override
	public void onRegister(String key, EventData data) {
		if (key.equals("pacman_move")) {
			if (data.p.equals(this.p)) {
				EventHandler.instance().triggerEvent("pacdot_eat", new EventData(this, this.p));
				GameInfo.addScore(10);
				GameInfo.pacmanEat();
				PacMan pac = (PacMan) data.src;
				pac.tileSpeedMod(0.89);
				EventHandler.instance().unsubscribeEvent("pacman_move", this);
				DrawHandler.instance().unregister(this);
			}
		}
	}

}
