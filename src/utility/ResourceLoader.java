package utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import game.DEBUG;

public class ResourceLoader {
	private static Map<String, BufferedImage> images = new HashMap<>();
	private ResourceLoader(){}
	
	public static void init(){
		DEBUG.DEBUG_MSG("ResourceLoader initializing!");
		final File location = new File("src/resources");
		File[] resources = location.listFiles();
		for (File image : resources) {
			try {
				images.put(image.getName().replace(".png", ""), ImageIO.read(image));
				DEBUG.DEBUG_MSG(image.getName().replace(".png", ""));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		DEBUG.DEBUG_MSG("ResourceLoader initialized!");
	}
	
	public static BufferedImage getImage(String file) {
		return images.get(file);
	}
}
