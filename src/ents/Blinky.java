package ents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Blinky extends Ghost{

	protected Blinky(double x, double y, String name, GhostHouse house) {
		super(x, y, name, house);
		hdg = 4;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int hdgDecide(int hdgsel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.RED);
		g2.fillOval((int)(this.x*25), (int)(this.y*25), 25, 25);
	}

	@Override
	protected void jailBreak() {
				
	}

	@Override
	protected void jail() {
		// TODO Auto-generated method stub
		
	}

}
