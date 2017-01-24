package gui;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import game.GameListener;
import utility.EventHandler;

public class MenuStack {
	private static Deque<Menu> menu = new LinkedList<>();

	private MenuStack() {
	}
	
	public static Menu currentMenu(){
		return menu.peek();
	}
	
	public static void pushMenu(Menu menu){
		if (currentMenu() != null) {
			currentMenu().free();
		}
		MenuStack.menu.push(menu);
		menu.activate();
	}

	public static void popMenu(){
		currentMenu().free();
		menu.pop();
		Menu lower = menu.peek();
		if (lower != null) {
			lower.activate();
		}
	}

}
