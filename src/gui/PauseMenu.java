package gui;

import java.util.ArrayList;
import java.util.List;

import game.GameInfo;
import game.Level;
import utility.DrawHandler;
import utility.EventHandler;

public class PauseMenu {

	public static Menu instance() {
		MenuItem resume = new MenuItem("Resume Game", 50, 100, Frame.WIDTH - 100, 50, true) {
			@Override
			void doclick() {
				GameInfo.thinkTick(10);
				MenuStack.popMenu();
				DrawHandler.popFrame();
			}
		};
		MenuItem reset = new MenuItem("Reset Game", 50, 175, Frame.WIDTH - 100, 50, true) {
			@Override
			void doclick() {
				MenuStack.popMenu();
				DrawHandler.init();
				EventHandler.init();
				new GameOverlay();
				Level.loadMap();
				GameInfo.thinkTick(3000);
			}
		};
		MenuItem main = new MenuItem("Main Menu", 50, 250, Frame.WIDTH - 100, 50, true) {
			@Override
			void doclick() {
				MenuStack.popMenu();
				DrawHandler.init();
				EventHandler.init();
				MenuStack.pushMenu(MainMenu.instance());
			}
		};
		List<MenuItem> items = new ArrayList<>();
		items.add(resume);
		items.add(reset);
		items.add(main);
		return new Menu(items) {
		};
	}

}
