package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import utility.Drawable;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.Point;
import utility.ResourceLoader;

//In order for this to properly work we need to add layering to the EventHandler, similarly the way we did for the DrawHandler... However problems will arise if Event-subscribes have been queued and a new event layer is pushed... this means that the events in the queue will be passed to the new view, creating undefined behaviour... therefore it is important to make sure that queues are cleaned up or stored before switching to a new layer. The new layer should be empty when created and then things will start registering automatically to the new view. This requires alot of legwork for a successful implementation but will ultimately make pretty much everything alot easier. triggers have to be contained inside its own layer. e.g. timers to trigger "game_think" need to be either closed, or only register on its own layer, when its own layer is the top layer. This requires every subscribed event to know to what layer it belongs, and automatically trigger events only in that layer. 

public class LevelEditor implements Drawable, Listener{
	private char[][] map;
	private char selection;
	
	public LevelEditor(){
		this.map = new char[28][31]; //Notice that we dont do 36... This is because we dont support that with the gameoverlay yet.
		EventHandler.subscribeEvent("mouse_moved", this);
		EventHandler.subscribeEvent("mouse_clicked", this);
		EventHandler.subscribeEvent("rmouse_clicked", this);
		
		//lets start by adding items to a toolbar...
		
	}

	@Override
	public void onRegister(String key, EventData src) {
		switch(key) {
		case "mouse_clicked": 
			break;
		}
	}

	@Override
	public void draw(Graphics g) {
		//TODO: draw a grid for the map. Also draw any items in the map.
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		for(int i = 0; i<29; i++) {
			g2.drawLine(i*25, 3*25, i*25, 34*25);
		}
		for(int i = 0; i<32; i++) {
			g2.drawLine(0, 3*25 + i*25, 28*25, 3*25 + i*25);
		}
		//lets look for shit to draw from the resourceLoader... //This will work for all the Walls, pacdots powerpellets, however not for jail and pacman...
		for(int y = 0; y<map[0].length; y++){
			for(int x = 0; x<map.length; x++){
				if (map[x][y] > 0) {
					BufferedImage img = ResourceLoader.getImage(String.valueOf(map[x][y]));
					g.drawImage(img, x*25, y*25 + 75, null);
				}
			}
		}
	}
	
	private static class Toolbar implements Drawable, Listener{

		@Override
		public void onRegister(String key, EventData src) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void draw(Graphics g) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private static class Dropdown implements Drawable, Listener{

		@Override
		public void onRegister(String key, EventData src) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void draw(Graphics g) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private static abstract class Item implements Drawable, Listener{
		protected final Point position;
		protected final Point size;
		
		public Item(Point position, Point size){
			this.position = position;
			this.size = size;
			EventHandler.subscribeEvent("mouse_clicked", this);
		}

		@Override
		public void onRegister(String key, EventData src) {
			switch(key){
			case "mouse_clicked":
				if (src.p.isInside(this.position, this.size)){
					this.doclick();
				}
				break;
			}
		}
		
		public abstract void doclick();
		
	}

}
