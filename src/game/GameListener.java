package game;

import utility.EventHandler.EventData;
import gui.PauseMenu;
import utility.EventHandler;
import utility.Listener;

public class GameListener implements Listener {
	
	public GameListener(){
		EventHandler.subscribeEvent("esc", this);
		EventHandler.subscribeEvent("game_win", this);
		EventHandler.subscribeEvent("game_lose", this);
		EventHandler.subscribeEvent("game_think", this);
	}
	
	@Override
	public void onRegister(String key, EventData src) {
		switch(key){
		case("esc"):
			Game.pushView();
			PauseMenu.instance();
			break;
		case("game_win"):
			Game.reloadGame();
			break;
		case("game_lose"):
			GameInfo.lose();
			break;
		case("game_think"):
			GameInfo.countFright();
			break;
		}
	} 
}
