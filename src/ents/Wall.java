package ents;

import java.awt.image.BufferedImage;
import utility.DrawHandler;
import utility.Point;

public class Wall extends Static {

	public Wall(Point p, BufferedImage img) {
		super(p, "wall", img);
		DrawHandler.instance().register(this);
	}
}
