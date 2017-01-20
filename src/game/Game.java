package game;

import utility.EventHandler.EventData;
import utility.GameState;
import gui.PauseMenu;
import utility.DrawHandler;
import utility.EventHandler;
import utility.Listener;

public class Game implements Listener {
	
	public Game(){
		EventHandler.subscribeEvent("esc", this);
	}
	
	@Override
	public void onRegister(String key, EventData src) {
		EventHandler.unsubscribeEvent("esc", this);
		DrawHandler.pushFrame();
		GameState.pushMenu(PauseMenu.instance());
		GameState.stopThink();
	}
	
	public static void dropResources(){
		DrawHandler.init();
		EventHandler.init();
	}

}
