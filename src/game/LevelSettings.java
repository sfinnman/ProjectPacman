package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import game.GameInfo.ScheduledTask;

public class LevelSettings {
	public static double topSpeed; //DO NOT EXCEED 0.5 STEPS, GAME BREAKS
	private static double ghostNrm;
	private static double ghostFrt;
	private static double ghostTnl;
	private static double pacNrm;
	private static double pacFrt;
	
	public static void loadSettings(int progress) {
		topSpeed = GameInfo.UPDATE/100.0;
		Scanner sc = null;
		File chasePatterns = new File("src/map/chasePatterns.txt");
		File speedSettings = new File("src/map/speedSettings.txt");
		try{
			sc = new Scanner(speedSettings);
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				String[] tokens = line.split(";");
				if (Integer.valueOf(tokens[0]) <= progress && Integer.valueOf(tokens[1])+1 >= progress) {
					ghostNrm = Double.valueOf(tokens[2]);
					ghostFrt = Double.valueOf(tokens[3]);
					ghostTnl = Double.valueOf(tokens[4]);
					pacNrm = Double.valueOf(tokens[5]);
					pacFrt = Double.valueOf(tokens[6]);
					break;
				}
			}
			sc.close();
			sc = new Scanner(chasePatterns);
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				String[] tokens = line.split(";");
				if (Integer.valueOf(tokens[0]) <= progress && Integer.valueOf(tokens[1]) >= progress) {
					int time = 0;
					for(int i = 2; i<tokens.length; i++){
						time += Integer.valueOf(tokens[i])*(10.0/GameInfo.UPDATE);
						new ScheduledTask(time, new Runnable(){
							@Override
							public void run() {
								GameInfo.scatter();
							}
						});
					}
					break;
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
		DEBUG.print("speeds: " + ghostNrm + ", " + ghostFrt + ", " + ghostTnl + ", " + pacNrm + ", " + pacFrt);
	}
	
	public static double ghostNrmMlt(){
		return ghostNrm;
	}
	
	public static double ghostFrtMlt(){
		return ghostFrt;
	}
	
	public static double ghostTnlMlt(){
		return ghostTnl;
	}
	
	public static double pacNrm(){
		return pacNrm;
	}
	
	public static double pacFrt(){
		return pacFrt;
	}
}
