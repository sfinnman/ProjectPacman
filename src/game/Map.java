package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ents.PacDot;
import ents.PowerPellet;
import ents.Wall;
import utility.ResourceLoader;

public class Map { //Class for lookup from Pacman and Ghosts!
	
	private static List<Wall> walls = new ArrayList<>();
	private static List<PacDot> pacdots = new ArrayList<>();
	private static List<PowerPellet> powerpellets = new ArrayList<>();
	
	private static int pacspawnx;
	private static int pacspawny;
	
	private Map(){}
	
	public static void loadMap(){
		DEBUG.DEBUG_MSG("Loading map!");
		File map = new File("src/maps/default.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(map);
			for(int y = 0; sc.hasNextLine(); y++) {
				String line = sc.nextLine();
				String[] tokens = line.split("\\s");
				for(int x = 0; x<tokens.length; x++) {
					String token = tokens[x];
					switch(token){
					case("11"):
					case("12"):
					case("13"):
					case("14"):
					case("21"):
					case("22"):
					case("23"):
					case("24"):
					case("31"):
					case("32"):
					case("33"):
					case("34"):
					case("41"):
					case("42"):
					case("43"):
					case("44"):
					case("51"):
					case("52"):
					case("53"):
					case("54"):
					case("61"):
					case("62"):
					case("63"):
					case("64"):
						walls.add(new Wall(x, y, ResourceLoader.getImage(token)));
						break;
					case("71"):
						pacdots.add(new PacDot(x, y, ResourceLoader.getImage(token)));
					break;
					case("72"):
						powerpellets.add(new PowerPellet(x, y, ResourceLoader.getImage(token)));
					break;
					case("81"):
					case("00"):
						continue;
					default:
						continue;
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
		DEBUG.DEBUG_MSG(walls.toString());
		DEBUG.DEBUG_MSG(pacdots.toString());
		DEBUG.DEBUG_MSG(powerpellets.toString());
		DEBUG.DEBUG_MSG("Map Loaded!");
	}
	
	//TODO: Maploader, Data structures for map elements to be used by pacman and ghosts.
}
