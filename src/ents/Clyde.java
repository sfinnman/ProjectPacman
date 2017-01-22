package ents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Clyde extends Ghost{

	protected Clyde(double x, double y, String name, GhostHouse house) {
		super(x, y, name, house);
		jailBreak();
	}
	
	@Override
	protected int hdgDecide(int hdgsel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.ORANGE);
		g2.fillOval((int)(this.x*25), (int)(this.y*25), 25, 25);
	}

	@Override
	protected void jailBreak() {
		hdgQueue1.offer(4);
		hdgQueue2.offer(4);
		hdgQueue1.offer(4);
		hdgQueue2.offer(4);
		hdgQueue1.offer(4);
		hdgQueue2.offer(8);
		hdgQueue1.offer(8);
		hdgQueue1.offer(8);
		hdgQueue1.offer(8);
		hdgQueue1.offer(4);
	}

	@Override
	protected void jail() {
		// TODO Auto-generated method stub
		
	}

}
