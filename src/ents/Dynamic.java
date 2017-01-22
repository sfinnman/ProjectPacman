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
	protected final String name;

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
	
	protected void move(double len) {
		int x = (hdg & 1) - ((hdg & 4) >> 2);
		int y = ((hdg & 2) >> 1) - ((hdg & 8) >> 3);
		this.x = (this.x + x * len + 28)%28;
		this.y = (this.y + y * len + 36)%36;
	}
	
	protected boolean borderCrossed(double xold, double yold){
		return getx() != (int) (xold + 0.5) || gety() != (int) (yold + 0.5);
	}
	
	protected void onBorder(){
	}
	
	protected void crossedBorder(){
	}
	
	protected boolean midCrossed(double xold, double yold){
		boolean x = (int)xold != (int)this.x;
		boolean y = (int) yold != (int) this.y;
		return (x || y || hdg == 0);
	}
	
	protected void decideHdg(){
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
		move(len);
		if (borderCrossed(xold, yold)) {
			crossedBorder();
			this.x = Math.round(this.x*2)/2.0;
			this.y = Math.round(this.y*2)/2.0;
			double nlen = len - Math.abs(this.x - xold + this.y - yold);
			onBorder();
			move(nlen);
		}
		if (midCrossed(xold, yold)) {
			onMidCrossed();
			if (Level.hasIntersection(new Point(this.getx(), this.gety()))) {
				double nlen = Math.abs(this.x - this.getx() + this.y - this.gety());
				this.x = this.getx();
				this.y = this.gety();
				decideHdg();
				this.move(nlen);
			}
		}
	}

	abstract protected void onMidCrossed();
	
	abstract protected int hdgDecide(int hdgsel);
	
	@Override
	public String toString() {
		return String.format(name + " |%d, %d| ", this.getx(), this.gety());
	}

}
