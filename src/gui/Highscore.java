package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import game.DEBUG;
import utility.Game;

public class Highscore{

	public static void instance(){
		new MenuItem("Back", 50, 100, Frame.WIDTH - 100, 50, 40){
			@Override
			void doclick() {
				Game.popView();
			}
		};
		File f = new File("src/highscores/list.txt");
		try {
			Scanner sc = new Scanner(f);
			for (int i = 1; i<11 && sc.hasNextLine(); i++){
				new MenuItem(i + ": " + sc.nextLine(), 50, 200 + i*50, Frame.WIDTH - 100, 50, 40){
					@Override
					public void subscribeEvents(){ //this removes clickability!
					}
					
					@Override
					void doclick() {
					}
				};
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			DEBUG.print(e);
		}
		
	}

}
