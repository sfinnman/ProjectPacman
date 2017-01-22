package game;

import utility.DPoint;
import utility.GameInfo;
import utility.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import ents.Blinky;
import ents.Clyde;
import ents.Inky;
import ents.PacDot;
import ents.PacMan;
import ents.Pinky;
import ents.PowerPellet;
import ents.Wall;
import utility.ResourceLoader;

public class Level { //Class for lookup from Pacman and Ghosts!
	private Level(){}
	
	private static Map<Point, Integer> Intersections;
	
	public static void loadMap(){
		Intersections = new HashMap<>();
		DEBUG.print("Loading map!");
		File map = new File("src/maps/default.txt");
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
					if (c<121) {
						new Wall(p, ResourceLoader.getImage(t));
					} else if (c == 121) {
						new PacDot(p, ResourceLoader.getImage(t));
						dots++;
					} else if (c == 122) {
						new PowerPellet(p, ResourceLoader.getImage(t));
					} else if (c == 123) {
						new Wall(p, ResourceLoader.getImage(t));
						house = p;
					}
				}
			}
			new Blinky(house.x + 3.5, house.y-1);
			new Pinky(house.x + 3.5, house.y+2);
			new Inky(house.x + 1.5, house.y+2);
			new Clyde(house.x + 5.5, house.y+2);
			String[] tokens = sc.nextLine().split(";");
			DEBUG.print(tokens[0]);
			double x = Double.valueOf(tokens[0]);
			double y = Double.valueOf(tokens[1]);
			new PacMan(x, y, Integer.valueOf(tokens[2]));
			Point blinky = new Point(house.x + 3.5, house.y-1);
			Point pacman = new Point(x, y);
			DPoint jail = new DPoint(house.x + 3.5, house.y - 1);
			GameInfo.init(pacman, blinky, jail, dots);
			tokens = sc.nextLine().split(";");
			String[] Intersections = sc.nextLine().replaceAll("[\\[\\]\\s]", "").split(",");
			for (String Intersection : Intersections) {
				tokens = Intersection.split(";");
				Level.Intersections.put(new Point(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1])), Integer.valueOf(tokens[2]));
			}
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
