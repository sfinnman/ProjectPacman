package ents;

abstract class Dynamic {
	protected int hdgy;
	protected int hdgx;
	
	protected double x;
	protected double y;
	
	protected Dynamic(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public int getx(){
		return (int)x;
	}
	
	public int gety(){
		return (int)y;
	}
	
}
