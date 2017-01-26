package game;

import utility.EventHandler.EventData;
import gui.GameOverlay;
import gui.MainMenu;
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
		EventHandler.subscribeEvent("game_win", this);
		EventHandler.subscribeEvent("game_lose", this);
		EventHandler.subscribeEvent("ghost_die", this);
		EventHandler.subscribeEvent("game_rip", this);
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
		case("game_win"):
			GameInfo.stopThink();
			DrawHandler.init();
			EventHandler.init();
			new GameOverlay();
			Level.loadMap();
			GameInfo.thinkTick(3000);
			break;
		case("game_lose"):
			GameInfo.stopThink();
			GameInfo.lose();
			EventHandler.free(this);
			GameInfo.thinkTick(3000);
			EventHandler.show();
			break;
		case("ghost_die"):
			GameInfo.eatGhost();
			break;
		case("game_rip"):
			GameInfo.stopThink();
			EventHandler.init();
			DrawHandler.init();
			MenuStack.pushMenu(MainMenu.instance());
			break;
		}
	} 
}
