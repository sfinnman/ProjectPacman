package utility;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import game.DEBUG;

public class ResourceLoader {
	private static Map<String, BufferedImage> images = new HashMap<>();
	private static Map<String, Font> fonts = new HashMap<>();
	private ResourceLoader(){}
	
	public static void init(){
		DEBUG.print("ResourceLoader initializing!");
		final File location = new File("src/resources");
		File[] resources = location.listFiles();
		for (File resource : resources) {
			try {
				String extension = resource.getAbsolutePath().replaceAll(".*(?=\\.)", "");
				switch(extension) {
				case(".png"):
					images.put(resource.getName().replace(extension, ""), ImageIO.read(resource));
					DEBUG.print(resource.getName().replace(extension, ""));
					break;
				case(".ttf"):
					Font f = Font.createFont(Font.TRUETYPE_FONT, resource);
					GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
					ge.registerFont(f);
					fonts.put(resource.getName().replace(extension, ""),  f);
					break;
				}
			} catch (IOException | FontFormatException e) {
				e.printStackTrace();
			}
		}
		DEBUG.print("ResourceLoader initialized!");
	}
	
	public static BufferedImage getImage(String name) {
		return images.get(name);
	}
	
	public static Font getFont(String name, float size){
		return fonts.get(name).deriveFont(size);
	}
}
