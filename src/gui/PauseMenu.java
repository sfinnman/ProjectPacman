package gui;

import java.util.ArrayList;
import java.util.List;

public class PauseMenu {
	
	public static Menu instance(){
		MenuItem resume = new MenuItem("Resume Game", 50, 100, 400, 50){
			@Override
			void doclick() {
				//Start game clock, close menu etc.
			}
		};
		MenuItem reset = new MenuItem("Reset Game", 50, 175, 400, 50){
			@Override
			void doclick() {
				//Goto highscore Menu (Layer it on top in Gamestate)
			}
		};
		MenuItem main = new MenuItem("Main Menu", 50, 250, 400, 50) {
			@Override
			void doclick() {
				//Reverse back to main menu
			}
		};
		List<MenuItem> items = new ArrayList<>();
		items.add(resume);
		items.add(reset);
		items.add(main);
		return new Menu(items){};
	}

}
