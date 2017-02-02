package game;

import utility.EventHandler.EventData;
import gui.PauseMenu;
import utility.EventHandler;
import utility.Listener;

public class GameListener implements Listener {

	public GameListener() {
		EventHandler.instance().subscribeEvent("esc", this);
		EventHandler.instance().subscribeEvent("game_win", this);
		EventHandler.instance().subscribeEvent("game_lose", this);
		EventHandler.instance().subscribeEvent("game_think", this);
	}

	@Override
	public void onRegister(String key, EventData src) {
		switch (key) {
		case ("esc"):
			Game.pushView();
			PauseMenu.instance();
			break;
		case ("game_win"):
			Game.reloadGame();
			break;
		case ("game_lose"):
			GameInfo.lose();
			break;
		case ("game_think"):
			GameInfo.countFright();
			break;
		}
	}
}
