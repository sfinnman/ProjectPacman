package gui;

import java.util.ArrayList;
import java.util.List;

import game.DEBUG;
import game.GameInfo;
import game.Level;

public class MainMenu {

	public static Menu instance() {
		MenuItem play = new MenuItem("Start Game", 50, 150, Frame.WIDTH - 100, 50, true) {
			@Override
			void doclick() {
				DEBUG.print("Start Game!");
				MenuStack.popMenu();
				Level.loadMap();
				GameInfo.init();
				GameInfo.thinkTick(3000);
			}
		};
		MenuItem highscore = new MenuItem("Highscore", 50, 225, Frame.WIDTH - 100, 50, true) {
			@Override
			void doclick() {
				DEBUG.print("Highscore!");
				MenuStack.pushMenu(Highscore.instance());
			}
		};
		MenuItem exit = new MenuItem("Exit Game", 50, 300, Frame.WIDTH - 100, 50, true) {
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
