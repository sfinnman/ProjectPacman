package gui;

import game.DEBUG;
import game.Game;

public class MainMenu {

	public static void instance() {
		new MenuItem("PacMan", 50, 50, Frame.WIDTH - 100, 50, 90){
			@Override
			public void subscribeEvents(){ //this removes clickability!
			}
			@Override
			protected void doclick() {
			}
		};
		
		new MenuItem("Start Game", 50, 150, Frame.WIDTH - 100, 75, 40) {
			@Override
			protected void doclick() {
				DEBUG.print("Start Game!");
				Game.pushView();
				Game.newGame();
			}
		};
		new MenuItem("Highscore", 50, 250, Frame.WIDTH - 100, 75, 40) {
			@Override
			protected void doclick() {
				DEBUG.print("Highscore!");
				Game.pushView();
				Highscore.instance();
			}
		};
		new MenuItem("Exit Game", 50, 350, Frame.WIDTH - 100, 75, 40) {
			@Override
			protected void doclick() {
				System.exit(0);
			}
		};
	}

}
