package ents;

import utility.Point;

public abstract class Static {
	protected final Point p;
	private final String name;
	
	protected Static(Point p, String name){
		this.p = p;
		this.name = name;
	}
	
	@Override
	public String toString(){
		return String.format(name + " |%d, %d| ", p.x, p.y);
	}
	
}
