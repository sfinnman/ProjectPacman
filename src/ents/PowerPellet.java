package ents;

import java.awt.image.BufferedImage;

import game.GameInfo;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.Point;
import utility.DrawHandler;
import utility.EventHandler;

public class PowerPellet extends Static implements Listener {

	public PowerPellet(Point p, BufferedImage img) {
		super(p, "powerpellet", img);
		EventHandler.instance().subscribeEvent("pacman_move", this);
	}

	@Override
	public void onRegister(String key, EventData data) {
		if (key.equals("pacman_move")) {
			if (p.equals(data.p)) {
				EventHandler.instance().triggerEvent("powerpellet_eat", new EventData(this, p));
				GameInfo.addScore(50);
				GameInfo.pacmanEat();
				EventHandler.instance().unsubscribeEvent("pacman_move", this);
				GameInfo.fright(500);
				DrawHandler.instance().unregister(this);
			}
		}
	}

}
