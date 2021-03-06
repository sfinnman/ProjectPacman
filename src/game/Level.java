package game;

import utility.DPoint;
import utility.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ents.Blinky;
import ents.Clyde;
import ents.Inky;
import ents.PacDot;
import ents.PacMan;
import ents.Pinky;
import ents.PowerPellet;
import ents.TunnelTile;
import ents.Wall;
import utility.ResourceLoader;

public class Level { //Class for lookup from Pacman and Ghosts!
	private Level(){}
	
	private static Map<Point, Integer> Intersections;
	protected static List<Integer> times;
	
	public static void loadMap(){
		Intersections = new HashMap<>();
		times = new ArrayList<>();
		
		DEBUG.print("Loading map!");
		File map = new File("src/map/default.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(map);
			int dots = 0;
			Point house = new Point(0,0);
			for(int y = 0; sc.hasNextLine(); y++) {
				String line = sc.nextLine();
				if (line.equals("\\n\\")) break;
				for(int x = 0; x<line.length(); x++) {
					char c = line.charAt(x);
					String t = String.valueOf(c);
					Point p = new Point(x, y);
					if (c<121 && c>96) {
						new Wall(p, ResourceLoader.getImage(t));
					} else if (c == 121) {
						new PacDot(p, ResourceLoader.getImage(t));
						dots++;
					} else if (c == 122) {
						dots++;
						new PowerPellet(p, ResourceLoader.getImage(t));
					} else if (c == 123) {
						new Wall(p, ResourceLoader.getImage(t));
						house = p;
					} else if (c == 49 || c == 50) {
						new TunnelTile(p);
					}
				}
			} //All tiles loaded!
			
			new Blinky(house.x + 3.5, house.y-1);
			new Pinky(house.x + 3.5, house.y+2);
			new Inky(house.x + 1.5, house.y+2);
			new Clyde(house.x + 5.5, house.y+2);
			//Ghosts spawned
			
			String[] tokens = sc.nextLine().split(";");
			DEBUG.print(tokens[0]);
			double x = Double.valueOf(tokens[0]);
			double y = Double.valueOf(tokens[1]);
			new PacMan(x, y, Integer.valueOf(tokens[2]));
			//Pacman spawned
			
			Point blinky = new Point(house.x + 3.5, house.y-1);
			Point pacman = new Point(x, y);
			DPoint jail = new DPoint(house.x + 3.5, house.y - 1);
			GameInfo.load(pacman, blinky, jail, dots);
			//GameInfo loaded
			
			tokens = sc.nextLine().split(";");
			String[] Intersections = sc.nextLine().replaceAll("[\\[\\]\\s]", "").split(",");
			for (String Intersection : Intersections) {
				tokens = Intersection.split(";");
				Level.Intersections.put(new Point(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1])), Integer.valueOf(tokens[2]));
			}
			//Intersections loaded
			
			DEBUG.print(Level.Intersections.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
		DEBUG.print("Map Loaded!");
	}
	

	public static Integer getIntersection(Point p){
		return Intersections.get(p);
	}
	
	public static boolean hasIntersection(Point p){
		return Intersections.containsKey(p);
	}
	
}
