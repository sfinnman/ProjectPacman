package ents;

import java.awt.image.BufferedImage;

import utility.DPoint;
import utility.Point;

public class GhostHouse extends Static{
	
	public GhostHouse(Point pos, BufferedImage img){
		super(pos, "ghosthouse", img);
		new Blinky(p.x + 3.5, p.y-1, "blinky", this);
		new Pinky(p.x + 3.5, p.y+2, "pinky", this);
		new Inky(p.x + 1.5, p.y+2, "inky", this);
		new Clyde(p.x + 5.5, p.y+2, "clyde", this);
	}
	
	public DPoint getSpawn(){
		return new DPoint(p.x + 3.5, p.y-1);
	}
	
	public void takeGhost(Ghost g){
		
	}
	 
}
