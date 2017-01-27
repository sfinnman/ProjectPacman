package gui;

import java.util.ArrayList;
import java.util.List;

import game.DEBUG;
import game.GameInfo;
import game.Level;

public class MainMenu {

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
		MenuItem highscore = new MenuItem("Highscore", 50, 250, Frame.WIDTH - 100, 75, 40) {
			@Override
			void doclick() {
				DEBUG.print("Highscore!");
				MenuStack.pushMenu(Highscore.instance());
			}
		};
		MenuItem exit = new MenuItem("Exit Game", 50, 350, Frame.WIDTH - 100, 75, 40) {
			@Override
			void doclick() {
				DEBUG.print("Start Game!");
				System.exit(0);
			}
		};
		List<MenuItem> items = new ArrayList<>();
		items.add(title);
		items.add(play);
		items.add(highscore);
		items.add(exit);
		return new Menu(items) {
		};
	}

}
