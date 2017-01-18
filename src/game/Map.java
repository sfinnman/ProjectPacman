package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ents.PacDot;
import ents.PacMan;
import ents.PowerPellet;
import ents.Wall;
import utility.ResourceLoader;

public class Map { //Class for lookup from Pacman and Ghosts!
	private Map(){}
	
	public static List<Intersection> Intersections = new ArrayList<>();
	
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
					if (c<121) {
						new Wall(x, y, ResourceLoader.getImage(t));
					} else if (c == 121) {
						new PacDot(x, y, ResourceLoader.getImage(t));
					} else if (c == 122) {
						new PowerPellet(x, y, ResourceLoader.getImage(t));
					}
				}
			}
			String[] tokens = sc.nextLine().split(";");
			new PacMan(Double.valueOf(tokens[0]), Double.valueOf(tokens[1]), Integer.valueOf(tokens[2]));
			tokens = sc.nextLine().split(";");
			//Jail insert here.
			String[] Intersections = sc.nextLine().replaceAll("[\\[\\]\\s]", "").split(",");
			for (String Intersection : Intersections) {
				tokens = Intersection.split(";");
				Map.Intersections.add(new Intersection(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]), Integer.valueOf(tokens[2])));
			}
			DEBUG.print(Map.Intersections.toString());
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
	
	private static class Intersection{
		public final int x;
		public final int y;
		public final int z;
		private Intersection(int x, int y, int z){
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		@Override
		public String toString(){
			return String.format("%d;%d;%d", x, y, z);
		}
	}
}
