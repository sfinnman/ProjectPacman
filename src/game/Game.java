package game;

import java.util.Timer;
import java.util.TimerTask;

import gui.GameOverlay;
import utility.DrawHandler;
import utility.EventHandler;
import utility.ResourceLoader;

public class Game {

	public static final int UPDATE = 1;
	private static int wait = 0;

	private Game() {
	}

	public static void pushView() {
		EventHandler.instance().pushLayer();
		DrawHandler.instance().pushFrame();
	}

	public static void popView() {
		EventHandler.instance().popLayer();
		DrawHandler.instance().popFrame();
	}

	public static void init() {
		ResourceLoader.init();
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Game.think();
				EventHandler.instance().triggerEvent("game_tick", null);
			}
		}, UPDATE, UPDATE);
	}

	public static void cleanView() {
		DrawHandler.instance().clear();
		EventHandler.instance().clear();
	}

	public static void newGame() {
		Game.cleanView();
		GameInfo.init();
		Level.loadMap();
		new GameOverlay();
		Game.wait(3000);
		GameOverlay.gameStart();
	}

	public static void reloadGame() {
		Game.cleanView();
		Level.loadMap();
		new GameOverlay();
		Game.wait(3000);
		GameOverlay.gameReload(GameInfo.getLevel());
	}

	private static void think() {
		if (0 == wait) {
			EventHandler.instance().triggerEvent("game_think", null);
		} else {
			wait--;
		}
	}

	public static void wait(int ticks) {
		wait = ticks;
	}
}
