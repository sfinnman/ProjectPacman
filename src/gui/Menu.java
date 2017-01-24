package gui;

import java.awt.Graphics;
import java.util.List;

import utility.DrawHandler;
import utility.Drawable;
import utility.EventHandler;

public abstract class Menu implements Drawable{
	private List<MenuItem> items;
	
	public Menu(List<MenuItem> items) {
		this.items = items;
	}
	
	public boolean containsItem(MenuItem item){
		return items.contains(item);
	}

	@Override
	public void draw(Graphics g) {
		for(MenuItem item : items) {
			item.draw(g);
		}
	}
	
	public void free(){
		DrawHandler.unregister(this);
		for (MenuItem item : items) {
			EventHandler.free(item);
		}
	}
	
	public void activate(){
		DrawHandler.register(this);
		for (MenuItem item : items) {
			item.subscribeEvents();
		}
	}
}
