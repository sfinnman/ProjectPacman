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
		EventHandler.subscribeEvent("powerpellet_eat", this);
	}
	
	@Override
	public void onRegister(String key, EventData src) {
		switch(key){
		case("esc"):
			EventHandler.free(this);
			DrawHandler.pushFrame();
			GameInfo.stopThink();
			MenuStack.pushMenu(PauseMenu.instance());
			break;
		case("pacdot_eat"):
			GameInfo.addScore(10);
			GameInfo.pacmanEat();
			break;
		case("powerpellet_eat"):
			GameInfo.addScore(50);
			GameInfo.pacmanEat();
			break;
		}
	} 
}
