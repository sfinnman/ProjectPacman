package gui;

import utility.Game;

public class PauseMenu {

	public static void instance() {
		new MenuItem("Resume Game", 50, 100, Frame.WIDTH - 100, 75, 40) {
			@Override
			void doclick() {
				Game.popView();
			}
		};
		new MenuItem("Reset Game", 50, 200, Frame.WIDTH - 100, 75, 40) {
			@Override
			void doclick() {
				Game.popView();
				Game.newGame();
			}
		};
		new MenuItem("Main Menu", 50, 300, Frame.WIDTH - 100, 75, 40) {
			@Override
			void doclick() {
				Game.popView();
				Game.popView();
			}
		};
	}

}
