package gui;

import java.util.ArrayList;
import java.util.List;

import game.DEBUG;
import game.Game;
import game.Level;
import utility.DrawHandler;
import utility.EventHandler;
import utility.GameState;

public class PauseMenu {
	
	public static Menu instance(){
		MenuItem resume = new MenuItem("Resume Game", 50, 100, Frame.WIDTH - 100, 50, true){
			@Override
			void doclick() {
				GameState.startThink();
				GameState.popMenu();
				DrawHandler.popFrame();
			}
		};
		MenuItem reset = new MenuItem("Reset Game", 50, 175, Frame.WIDTH - 100, 50, true){
			@Override
			void doclick() {
				GameState.popMenu();
				Game.dropResources();
				Level.loadMap();
				GameState.startThink();
			}
		};
		MenuItem main = new MenuItem("Main Menu", 50, 250, Frame.WIDTH - 100, 50, true) {
			@Override
			void doclick() {
				GameState.popMenu();
				Game.dropResources();
				GameState.pushMenu(MainMenu.instance());
			}
		};
		List<MenuItem> items = new ArrayList<>();
		items.add(resume);
		items.add(reset);
		items.add(main);
		return new Menu(items){};
	}

}
