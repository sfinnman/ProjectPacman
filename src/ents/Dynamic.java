package ents;

public abstract class Dynamic {
	protected int hdg;
	
	protected double x;
	protected double y;
	
	private final String name;
	
	protected Dynamic(double x, double y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	public int getx(){
		return (int)x;
	}
	
	public int gety(){
		return (int)y;
	}
	
	@Override
	public String toString(){
		return String.format(name + " |%d, %d| ", (int)x, (int)y);
	}
	
	
}
