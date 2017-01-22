package game;

import utility.EventHandler.EventData;
import gui.MenuStack;
import gui.PauseMenu;
import utility.DrawHandler;
import utility.EventHandler;
import utility.Listener;

public class EscListener implements Listener {
	
	public EscListener(){
		EventHandler.subscribeEvent("esc", this);
	}
	
	@Override
	public void onRegister(String key, EventData src) {
		EventHandler.unsubscribeEvent("esc", this);
		DrawHandler.pushFrame();
		GameInfo.stopThink();
		MenuStack.pushMenu(PauseMenu.instance());
	}

}
