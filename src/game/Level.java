package game;

import utility.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import ents.PacDot;
import ents.PacMan;
import ents.PowerPellet;
import ents.Wall;
import utility.ResourceLoader;

public class Level { //Class for lookup from Pacman and Ghosts!
	private Level(){}
	
	private static Map<Point, Integer> Intersections = new HashMap<>();
	
	public static void loadMap(){
		DEBUG.print("Loading map!");
		File map = new File("src/maps/default.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(map);
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
					} else if (c == 122) {
						new PowerPellet(p, ResourceLoader.getImage(t));
					}
				}
			}
			String[] tokens = sc.nextLine().split(";");
			DEBUG.print(tokens[0]);
			new PacMan(Double.valueOf(tokens[0]), Double.valueOf(tokens[1]), Integer.valueOf(tokens[2]));
			tokens = sc.nextLine().split(";");
			//Jail insert here.
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
		DEBUG.print(Intersections.containsKey(new Point(15, 26)));
		DEBUG.print("Map Loaded!");
	}
	

	public static Integer getIntersection(Point p){
		return Intersections.get(p);
	}
	
	public static boolean hasIntersection(Point p){
		DEBUG.print("performing hasInteraction at: " + p);
		return Intersections.containsKey(p);
	}
	
}
