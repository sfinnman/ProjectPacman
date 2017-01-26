package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import utility.DPoint;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.Point;

public class GameInfo{
	
	private static Point pacman_pos;
	private static Point blinky;
	private static int pacman_hdg;
	private static DPoint jail_entrance;
	private static boolean ghost_scatter;
	private static int pacman_lives;
	private static int score;
	private static int dots;
	private static int progress;
	private static Timer game_think;
	private static int fright_time;
	private static int ghosts_eaten;

	private GameInfo(){}
	
	public static void load(Point pacman, Point blinky, DPoint jail_entrance, int dots){
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
	}

	public static void init(){
		GameInfo.score = 0;
		GameInfo.pacman_lives = 2;
		GameInfo.progress = 0;
	}
	
	public static void thinkTick(long delay){
		GameInfo.game_think = new Timer();
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				EventHandler.triggerEvent("game_think", null);
				GameInfo.countFright();
			}
		};
		new GameListener();
		game_think.scheduleAtFixedRate(task, delay, 10);
	}

	protected static void stopThink(){
		game_think.cancel();
	}
	
	public static void fright(int time) {
		fright_time = time;
	}
	
	private static void countFright(){
		fright_time--;
		if (fright_time == 0) {
			EventHandler.triggerEvent("unfrightened", null);
		} else if (fright_time < 0){
			ghosts_eaten = 0;
			fright_time = 0;
		}
	}
	
	public static void lose(){
		pacman_lives--;
		ScheduledTask.purgeTasks();
		ghost_scatter = true;
		LevelSettings.loadSettings(progress);
	}
	
	public static void setBlinkyPos(Point p){
		blinky = new Point(p.x, p.y);
	}
	
	public static void setPacmanPos(Point p){
		pacman_pos = new Point(p.x, p.y);
	}

	public static void setPacmanHdg(int hdg){
		pacman_hdg = hdg;
	}
	
	public static void scatter(){
		ghost_scatter = !ghost_scatter;
		EventHandler.triggerEvent("scatter", null);
	}
	
	public static void addScore(int score){
		GameInfo.score += score;
	}
	
	public static void pacmanEat(){
		dots--;
		if (0 == dots) {
			EventHandler.triggerEvent("game_win", null);
		}
	}
	
	public static void eatGhost(){
		score += 200*Math.pow(2, ghosts_eaten);
		ghosts_eaten = (ghosts_eaten+1)%4;
		
	}

	public static DPoint pacmanPos(){
		return new DPoint(pacman_pos.x,pacman_pos.y);
	}
	
	public static int pacmanHdg(){
		return pacman_hdg;
	}
	
	public static DPoint blinkyPos(){
		return new DPoint(blinky.x,blinky.y);
	}
	
	public static DPoint jailPos(){
		return new DPoint(jail_entrance.x, jail_entrance.y);
	}
	
	public static boolean isScatter(){
		return ghost_scatter;
	}
	
	public static int getLevel(){
		return progress;
	}
	
	public static int getScore(){
		return score;
	}
	
	public static int getLives(){
		return pacman_lives;
	}
	
	public static Point calcHdg(int hdg) {
		int x = (hdg & 1) - ((hdg & 4) >> 2);
		int y = ((hdg & 2) >> 1) - ((hdg & 8) >> 3);
		return new Point(x, y);
	}
	
	public static class ScheduledTask implements Listener{
		private static List<ScheduledTask> tasks = new ArrayList<>();
		private int delay;
		private Runnable task;
		
		public ScheduledTask(int delay, Runnable task){
			this.delay = delay;
			this.task = task;
			EventHandler.subscribeEvent("game_think", this);
			tasks.add(this);
		}

		@Override
		public void onRegister(String key, EventData src) {
			delay--;
			if (delay < 1){
				task.run();
				tasks.remove(this);
				EventHandler.unsubscribeEvent("game_think", this);
			}
		}
		
		public static void purgeTasks(){
			for (ScheduledTask task : tasks)
				EventHandler.unsubscribeEvent("game_think", task);
			tasks = new ArrayList<>();
		}
		
	}

}
