package gui;

import java.awt.Graphics;
import java.util.List;

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
		Drawable.register(this);
	}
	
	public void removeDraw(){
		Drawable.unregister(this);
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
		for (MenuItem item : items) {
			item.subscribeEvents();
		}
	}
}
