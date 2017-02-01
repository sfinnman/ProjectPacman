package utility;

import java.util.Timer;
import java.util.TimerTask;

import game.GameInfo;
import game.Level;
import gui.GameOverlay;

public class Game {

	public static final int UPDATE = 1;
	private static int wait = 0;
	
	private Game(){}
	
	public static void pushView(){
		EventHandler.pushLayer();
		DrawHandler.pushFrame();
	}

	public static void popView(){
		EventHandler.popLayer();
		DrawHandler.popFrame();
	}
	
	public static void init(){
		EventHandler.init();
		DrawHandler.init();
		ResourceLoader.init();
		new Timer().scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run() {
				Game.think();
				EventHandler.triggerEvent("game_tick", null);
			}}, UPDATE, UPDATE);
	}
	
	public static void cleanView(){
		DrawHandler.clear();
		EventHandler.clear();
	}
	
	public static void newGame(){
		Game.cleanView();
		GameInfo.init();
		Level.loadMap();
		new GameOverlay();
		Game.wait(3000);
	}
	
	public static void reloadGame(){
		Game.cleanView();
		Level.loadMap();
		new GameOverlay();
		Game.wait(3000);
	}
	
	private static void think(){
		if (0 == wait){
			EventHandler.triggerEvent("game_think", null);
		} else {
			wait--;
		}
	}
	
	public static void wait(int ticks){
		wait = ticks;
	}
}
