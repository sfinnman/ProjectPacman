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

	public void move(double len) {
		int x = (hdg & 1) - ((hdg & 4) >> 2);
		int y = ((hdg & 2) >> 1) - ((hdg & 8) >> 3);
		double xold = this.x;
		double yold = this.y;
		this.x += x * len;
		this.y += y * len;
		if (getx() != (int) (xold + 0.5) || gety() != (int) (yold + 0.5)) {
			EventHandler.triggerEvent("pacman_move", new EventData(this, new Point(getx(), gety())));
		}
		x = this.getx();
		y = this.gety();
		boolean exists = Level.hasIntersection(new Point(x, y));
		if (((int) xold != (int) this.x || (int) yold != (int) this.y || hdg == 0) && exists) {
			double nlen = Math.abs(this.x - x + this.y - y);
			this.x = x;
			this.y = y;
			int hdgsel = Level.getIntersection(new Point(x, y));
			int hdgwant = hdgDecide(hdgsel);
			if ((hdgwant&hdgsel)>0){
				this.hdg = (hdgwant&hdgsel);
			} else {
				this.hdg = (this.hdg&hdgsel);
			}
			x = (hdg & 1) - ((hdg & 4) >> 2);
			y = ((hdg & 2) >> 1) - ((hdg & 8) >> 3);
			this.x += x * nlen;
			this.y += y * nlen;
		}

	}

	abstract protected int hdgDecide(int hdgsel);

	@Override
	public String toString() {
		return String.format(name + " |%d, %d| ", this.getx(), this.gety());
	}

}
