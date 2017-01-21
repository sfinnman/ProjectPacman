package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import game.DEBUG;
import utility.GameState;

public class Highscore{

	public static Menu instance(){
		MenuItem back = new MenuItem("Back", 50, 100, Frame.WIDTH - 100, 50, true){
			@Override
			void doclick() {
				GameState.popMenu();
			}
		};
		File f = new File("src/highscores/list.txt");
		List<MenuItem> scores = new ArrayList<>();
		try {
			Scanner sc = new Scanner(f);
			for (int i = 0; i<10 && sc.hasNextLine(); i++){
				scores.add(new MenuItem(sc.nextLine(), 50, 175 + i*75, Frame.WIDTH - 100, 50, false){
					@Override
					void doclick() {
					}
				});
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			DEBUG.print(e);
		}
		
		
		List<MenuItem> items = new ArrayList<>();
		items.add(back);
		items.addAll(scores);
		return new Menu(items){};
	}

}
