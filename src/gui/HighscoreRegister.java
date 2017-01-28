package gui;

import java.util.ArrayList;
import java.util.List;

import game.DEBUG;
import game.GameInfo;
import game.Level;

public class HighscoreRegister {

	public static Menu instance() {
		
		MenuItem title = new MenuItem("PacMan", 50, 50, Frame.WIDTH - 100, 50, 90){
			@Override
			public void subscribeEvents(){ //this removes clickability!
			}
			
			@Override
			void doclick() {
			}
		};
		
		MenuItem play = new MenuItem("Start Game", 50, 150, Frame.WIDTH - 100, 75, 40) {
			@Override
			void doclick() {
				DEBUG.print("Start Game!");
				MenuStack.popMenu();
				GameInfo.init();
				Level.loadMap();
				new GameOverlay();
				GameInfo.thinkTick(3000);
			}
		};
		
		List<MenuItem> items = new ArrayList<>();
		items.add(title);
		items.add(play);
		return new Menu(items) {
		};
	}

}
