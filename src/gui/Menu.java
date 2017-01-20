package gui;

import java.awt.Graphics;
import java.util.List;

import utility.DrawHandler;
import utility.EventHandler;

public abstract class Menu implements Drawable{
	private List<MenuItem> items;
	
	public Menu(List<MenuItem> items) {
		this.items = items;
	}
	
	public boolean containsItem(MenuItem item){
		return items.contains(item);
	}
	
	public void setDraw(){
		DrawHandler.register(this);
	}
	
	public void removeDraw(){
		DrawHandler.unregister(this);
	}

	@Override
	public void draw(Graphics g) {
		for(MenuItem item : items) {
			item.draw(g);
		}
	}
	
	public void free(){
		this.removeDraw();
		for (MenuItem item : items) {
			EventHandler.free(item);
		}
	}
	
	public void reactivate(){
		this.setDraw();
		for (MenuItem item : items) {
			item.subscribeEvents();
		}
	}
}
