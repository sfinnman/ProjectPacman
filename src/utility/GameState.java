package utility;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import gui.MainMenu;
import gui.Menu;
import utility.EventHandler.EventData;

public class GameState implements Listener {
	private static final GameState self = new GameState();

	private static Timer game_think;
	private static Deque<Menu> menu = new LinkedList<>();
	private static long game_time;
	private static int score;
	private static int lives;

	private GameState() {
	}

	public static void init() {
		menu.push(MainMenu.instance());
		menu.peek().setDraw();
		game_think = new Timer();
	}
	
	public static void startThink(){
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				EventHandler.triggerEvent("game_think", null);
			}
		};
		game_think.scheduleAtFixedRate(task, 10, 10);
	}
	
	public static void stopThink(){
		game_think.cancel();
	}
	
	public static Menu currentMenu(){
		return menu.peek();
	}
	
	public static void pushMenu(Menu menu){
		currentMenu().free();
		GameState.menu.push(menu);
		menu.setDraw();
	}

	public static void popMenu(){
		currentMenu().free();
		menu.pop();
		Menu lower = menu.peek();
		if (lower != null) {
			lower.reactivate();
		}
	}
	
	@Override
	public void onRegister(String key, EventData src) {
		// TODO Auto-generated method stub

	}

}
