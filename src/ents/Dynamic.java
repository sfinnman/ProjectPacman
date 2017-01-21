package ents;

import game.DEBUG;
import game.Level;
import utility.EventHandler;
import utility.Point;
import utility.EventHandler.EventData;

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

	public int getx() {
		return (int) (x + 0.5);
	}

	public int gety() {
		return (int) (y + 0.5);
	}
	
	private void move(double len) {
		int x = (hdg & 1) - ((hdg & 4) >> 2);
		int y = ((hdg & 2) >> 1) - ((hdg & 8) >> 3);
		this.x = (this.x + x * len + 28)%28;
		this.y = (this.y + y * len + 36)%36;
	}
	
	private boolean midPointCrossed(double xold, double yold){
		return getx() != (int) (xold + 0.5) || gety() != (int) (yold + 0.5);
	}
	
	private void onMove(){
		EventHandler.triggerEvent(name+"_move", new EventData(this, new Point(getx(), gety())));
	}
	
	private boolean crossedIntersection(double xold, double yold){
		Point p = new Point(this.getx(), this.gety());
		boolean exists = Level.hasIntersection(p);
		boolean x = (int)xold != (int)this.x;
		boolean y = (int) yold != (int) this.y;
		return (x || y || hdg == 0) && exists;
	}
	
	private void decideHdg(){
		int hdgsel = Level.getIntersection(new Point(x, y));
		int hdgwant = hdgDecide(hdgsel);
		if ((hdgwant&hdgsel)>0){
			this.hdg = (hdgwant&hdgsel);
		} else {
			this.hdg = (this.hdg&hdgsel);
		}
	}

	public void go(double len) {
		double xold = this.x;
		double yold = this.y;
		this.move(len);
		if (this.midPointCrossed(xold, yold)) {
			this.onMove();
		}
		if (crossedIntersection(xold, yold)) {
			double nlen = Math.abs(this.x - this.getx() + this.y - this.gety());
			this.x = this.getx();
			this.y = this.gety();
			decideHdg();
			this.move(nlen);
		}
	}

	abstract protected int hdgDecide(int hdgsel);

	@Override
	public String toString() {
		return String.format(name + " |%d, %d| ", this.getx(), this.gety());
	}

}
