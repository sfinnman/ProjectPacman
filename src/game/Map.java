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
	private Map(){}
	
	public static void loadMap(){
		DEBUG.DEBUG_MSG("Loading map!");
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
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
		DEBUG.DEBUG_MSG("Map Loaded!");
	}
	
	//TODO: Maploader, Data structures for map elements to be used by pacman and ghosts.
}
