package ents;

import java.util.Timer;
import java.util.TimerTask;

import game.DEBUG;
import game.GameInfo;
import game.Level;
import game.LevelSettings;
import utility.EventHandler.EventData;
import utility.DrawHandler;
import utility.Drawable;
import utility.EventHandler;
import utility.Listener;
import utility.Point;

public abstract class Dynamic implements Listener, Drawable{
	protected int hdg;
	protected double x;
	protected double y;
	protected final double startx;
	protected final double starty;
	protected final int starthdg;
	protected final String name;
	protected double tileSpeedMod;
	protected boolean frightened;
	
	protected Dynamic(double x, double y, int hdg, String name) {
		this.x = x;
		this.y = y;
		this.hdg = hdg;
		this.startx = x;
		this.starty = y;
		this.starthdg = hdg;
		this.name = name;
		this.frightened = false;
		this.tileSpeedMod = 1;
		DrawHandler.register(this);
		EventHandler.subscribeEvent("game_lose", this);
		EventHandler.subscribeEvent("powerpellet_eat", this);
		EventHandler.subscribeEvent("unfrightened", this);
	}
	
	public void onRegister(String key, EventData data){
		switch(key){
		case("game_lose"):
			EventHandler.free(this);
			DrawHandler.unregister(this);
			respawn();
			break;
		case("powerpellet_eat"):
			onFrightened();
			break;
		case("unfrightened"):
			frightened = false;
			break;
		}
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
	
	protected void tileSpeedMod(double mod){ // Landed on a tile with a speedMod!
		tileSpeedMod = mod;
	}
	
	private boolean midCrossed(double xold, double yold){
		boolean x = (int)xold != (int)this.x;
		boolean y = (int) yold != (int) this.y;
		return (x || y || hdg == 0);
	}
	
	protected boolean borderCrossed(double xold, double yold){
		return getx() != (int) (xold + 0.5) || gety() != (int) (yold + 0.5);
	}
	
	protected void decideHdg(){
		int hdgsel = Level.getIntersection(new Point(x, y)); //Gets which directions the current tile supports
		int hdgwant = hdgDecide(hdgsel); //This method is required by extensions, main movement mode
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
			tileSpeedMod = 1;
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

	abstract protected void respawn();
	
	protected void onFrightened(){}

	protected void onMidCrossed(){}
	
	protected void onBorder(){}
	
	protected void crossedBorder(){}
	
	abstract protected int hdgDecide(int hdgsel);
	
	@Override
	public String toString() {
		return String.format(name + " |%d, %d| ", this.getx(), this.gety());
	}
	
}
