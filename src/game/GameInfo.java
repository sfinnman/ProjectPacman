package game;

import java.util.Timer;
import java.util.TimerTask;

import utility.DPoint;
import utility.EventHandler;
import utility.Point;

public class GameInfo{
	
	private GameInfo(){}
	
	private static Point pacman_pos;
	private static Point blinky;
	private static int pacman_hdg;
	private static DPoint jail_entrance;
	private static boolean ghost_scatter;
	private static int pacman_lives;
	private static int score;
	private static int dots;
	private static int eaten;
	private static int level;
	private static double speed = 0.05;
	private static Timer game_think;
	
	public static void init(Point pacman, Point blinky, DPoint jail_entrance, int dots){
		GameInfo.pacman_pos = pacman;
		GameInfo.pacman_hdg = 1;
		GameInfo.ghost_scatter= true;
		GameInfo.blinky = blinky;
		GameInfo.jail_entrance = jail_entrance;
		GameInfo.dots = dots;
		GameInfo.eaten = 0;
		GameInfo.score = 0;
		GameInfo.pacman_lives = 3;
		GameInfo.level = 0;
		new GhostStateSwitcher();		
	}
	
	public static void thinkTick(long delay){
		GameInfo.game_think = new Timer();
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				EventHandler.triggerEvent("game_think", null);
			}
		};
		new GameListener();
		game_think.scheduleAtFixedRate(task, delay, 10);
	}

	protected static void stopThink(){
		game_think.cancel();
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
		if (!ghost_scatter)
			EventHandler.triggerEvent("scatter", null);
		ghost_scatter = !ghost_scatter;
	}
	
	public static void addScore(int score){
		GameInfo.score += score;
	}
	
	public static void pacmanEat(){
		eaten++;
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
	
	public static int getScore(){
		return score;
	}
	
	public static int getEaten(){
		return eaten;
	}
	
	public static double getSpeed(){
		return speed;
	}
	
	public static Point calcHdg(int hdg) {
		int x = (hdg & 1) - ((hdg & 4) >> 2);
		int y = ((hdg & 2) >> 1) - ((hdg & 8) >> 3);
		return new Point(x, y);
	}

}
