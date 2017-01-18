package utility;

import java.util.Deque;
import java.util.LinkedList;

import gui.MainMenu;
import gui.Menu;
import utility.EventHandler.EventData;

public class GameState implements Listener {
	private static final GameState self = new GameState();

	private static boolean game_think;
	private static Deque<Menu> menu = new LinkedList<>();
	private static long game_time;
	private static int score;
	private static int lives;

	private GameState() {
	}

	public static void init() {
		menu.push(MainMenu.instance());
		menu.peek().setDraw();
	}
	
	public static void toggleThink(){
		game_think = !game_think;
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
