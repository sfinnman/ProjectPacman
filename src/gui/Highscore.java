package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import game.DEBUG;
import utility.EventHandler;

public class Highscore{

	public static Menu instance(){
		MenuItem back = new MenuItem("Back", 50, 100, Frame.WIDTH - 100, 50, 40){
			@Override
			void doclick() {
				MenuStack.popMenu();
			}
		};
		File f = new File("src/highscores/list.txt");
		List<MenuItem> scores = new ArrayList<>();
		try {
			Scanner sc = new Scanner(f);
			for (int i = 1; i<11 && sc.hasNextLine(); i++){
				scores.add(new MenuItem(i + ": " + sc.nextLine(), 50, 200 + i*50, Frame.WIDTH - 100, 50, 40){
					@Override
					public void subscribeEvents(){ //this removes clickability!
					}
					
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
