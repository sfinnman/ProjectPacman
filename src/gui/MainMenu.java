package gui;

import java.util.ArrayList;
import java.util.List;

import game.DEBUG;
import game.Level;
import utility.GameState;

public class MainMenu {

	public static Menu instance() {
		MenuItem play = new MenuItem("Start Game", 50, 150, Frame.WIDTH - 100, 50) {
			@Override
			void doclick() {
				DEBUG.print("Start Game!");
				GameState.popMenu();
				Level.loadMap();
				GameState.startThink();
			}
		};
		MenuItem highscore = new MenuItem("Highscore", 50, 225, Frame.WIDTH - 100, 50) {
			@Override
			void doclick() {
				DEBUG.print("Highscore!");
				GameState.pushMenu(Highscore.instance());
			}
		};
		MenuItem exit = new MenuItem("Exit Game", 50, 300, Frame.WIDTH - 100, 50) {
			@Override
			void doclick() {
				DEBUG.print("Start Game!");
				System.exit(0);
			}
		};
		List<MenuItem> items = new ArrayList<>();
		items.add(play);
		items.add(highscore);
		items.add(exit);
		return new Menu(items) {
		};
	}

}
