package game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import gui.GameOver;
import utility.DPoint;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.Point;

public class GameInfo {
	private static Point pacman_pos;
	private static Point blinky;
	private static int pacman_hdg;
	private static DPoint jail_entrance;
	private static boolean ghost_scatter;
	private static int pacman_lives;
	private static int score;
	private static int dots;
	private static int progress;
	private static int fright_time;
	private static int ghosts_eaten;
	private static String playername = "anon";

	private GameInfo() {
	}

	public static void load(Point pacman, Point blinky, DPoint jail_entrance, int dots) {
		GameInfo.pacman_pos = pacman;
		GameInfo.pacman_hdg = 1;
		GameInfo.blinky = blinky;
		GameInfo.jail_entrance = jail_entrance;
		GameInfo.dots = dots;
		GameInfo.fright_time = 0;
		ghost_scatter = true;
		progress++;
		ScheduledTask.purgeTasks();
		LevelSettings.loadSettings(progress);
		new GameListener();
	}

	public static void init() {
		GameInfo.score = 0;
		GameInfo.pacman_lives = 5;
		GameInfo.progress = 0;
	}

	public static void fright(int time) {
		fright_time = (int) (time * (10.0 / Game.UPDATE));
	}

	protected static void countFright() {
		fright_time--;
		if (fright_time == 0) {
			EventHandler.instance().triggerEvent("unfrightened", null);
		} else if (fright_time < 0) {
			ghosts_eaten = 0;
			fright_time = 0;
		}
	}

	public static void lose() {
		if (pacman_lives == 0) {
			Game.cleanView();
			GameOver.instance(score);
		}
		pacman_lives--;
		ScheduledTask.purgeTasks();
		ghost_scatter = true;
		LevelSettings.loadSettings(progress);
		Game.wait(3000);
	}

	public static void setPlayerName(String plyName) {
		playername = plyName;
	}

	public static void save() {
		File f = new File("src/highscores/list.txt");
		try {
			Scanner sc = new Scanner(f);
			List<AbstractMap.SimpleEntry<String, Integer>> scores = new ArrayList<>();
			while (sc.hasNextLine()) {
				String[] tokens = sc.nextLine().split(";");
				if (tokens.length == 2) {
					scores.add(new SimpleEntry<String, Integer>(tokens[0], Integer.valueOf(tokens[1])));
				}
			}
			sc.close();
			scores.add(new SimpleEntry<String, Integer>(playername, score));
			scores.sort(new Comparator<SimpleEntry<String, Integer>>() {
				@Override
				public int compare(SimpleEntry<String, Integer> o1, SimpleEntry<String, Integer> o2) {
					return o2.getValue() - o1.getValue();
				}
			});
			FileWriter fw = new FileWriter(f);
			StringBuilder sb = new StringBuilder();
			for (SimpleEntry<String, Integer> score : scores) {
				sb.append(score.getKey() + ";" + score.getValue());
				sb.append(System.lineSeparator());
			}
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setBlinkyPos(Point p) {
		blinky = new Point(p.x, p.y);
	}

	public static void setPacmanPos(Point p) {
		pacman_pos = new Point(p.x, p.y);
	}

	public static void setPacmanHdg(int hdg) {
		pacman_hdg = hdg;
	}

	public static void scatter() {
		ghost_scatter = !ghost_scatter;
		EventHandler.instance().triggerEvent("scatter", null);
	}

	public static void addScore(int score) {
		GameInfo.score += score;
	}

	public static void pacmanEat() {
		dots--;
		if (0 == dots) {
			EventHandler.instance().triggerEvent("game_win", null);
		}
	}

	public static int eatGhost() {
		int add = (int) (200 * Math.pow(2, ghosts_eaten));
		score += add;
		ghosts_eaten = (ghosts_eaten + 1) % 4;
		return add;

	}

	public static DPoint pacmanPos() {
		return new DPoint(pacman_pos.x, pacman_pos.y);
	}

	public static int pacmanHdg() {
		return pacman_hdg;
	}

	public static DPoint blinkyPos() {
		return new DPoint(blinky.x, blinky.y);
	}

	public static DPoint jailPos() {
		return new DPoint(jail_entrance.x, jail_entrance.y);
	}

	public static boolean isScatter() {
		return ghost_scatter;
	}

	public static int getLevel() {
		return progress;
	}

	public static int getFrightTime() {
		return fright_time;
	}

	public static int getScore() {
		return score;
	}

	public static int getLives() {
		return pacman_lives;
	}

	public static Point calcHdg(int hdg) {
		int x = (hdg & 1) - ((hdg & 4) >> 2);
		int y = ((hdg & 2) >> 1) - ((hdg & 8) >> 3);
		return new Point(x, y);
	}

	public static class ScheduledTask implements Listener {
		private static List<ScheduledTask> tasks = new ArrayList<>();
		private int delay;
		private Runnable task;

		public ScheduledTask(int delay, Runnable task) {
			this.delay = delay;
			this.task = task;
			EventHandler.instance().subscribeEvent("game_think", this);
			tasks.add(this);
		}

		@Override
		public void onRegister(String key, EventData src) {
			delay--;
			if (delay < 1) {
				task.run();
				tasks.remove(this);
				EventHandler.instance().unsubscribeEvent("game_think", this);
			}
		}

		public static void purgeTasks() {
			for (ScheduledTask task : tasks)
				EventHandler.instance().unsubscribeEvent("game_think", task);
			tasks = new ArrayList<>();
		}
	}
}
