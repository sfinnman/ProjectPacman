package game;

import utility.EventHandler.EventData;
import gui.MenuStack;
import gui.PauseMenu;
import utility.DrawHandler;
import utility.EventHandler;
import utility.Listener;

public class GameListener implements Listener {
	
	public GameListener(){
		EventHandler.subscribeEvent("esc", this);
		EventHandler.subscribeEvent("pacdot_eat", this);
	}
	
	@Override
	public void onRegister(String key, EventData src) {
		if (key.equals("pacdot_eat")){
			GameInfo.pacmanEat();
		}
		if (key.equals("esc")){
			EventHandler.unsubscribeEvent("esc", this);
			DrawHandler.pushFrame();
			GameInfo.stopThink();
			MenuStack.pushMenu(PauseMenu.instance());
		}
	}

}
