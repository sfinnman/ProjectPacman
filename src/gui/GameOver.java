package gui;

import game.GameInfo;
import utility.Game;

public class GameOver {

	public static void instance(int score) {
		new MenuItem("Game Over", 50, 100, Frame.WIDTH - 100, 50, 40) {
			@Override
			public void subscribeEvents() { // this removes clickability!
			}

			@Override
			protected void doclick() {
			}
		};

		new MenuItem("Your score was: " + score, 50, 200, Frame.WIDTH - 100, 50, 30) {
			@Override
			public void subscribeEvents() { // this removes clickability!
			}

			@Override
			protected void doclick() {
			}
		};

		new TextInputItem("Enter your name:", 50, 300, Frame.WIDTH - 100, 110, 30){

			@Override
			void onchange(String text) {
				GameInfo.setPlayerName(text);
			}
			
		};

		new MenuItem("Continue...", 50, 600, Frame.WIDTH - 100, 50, 40) {

			@Override
			protected void doclick() {
				GameInfo.save();
				Game.popView();
			}
		};

	}
}
