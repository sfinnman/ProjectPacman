package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game.GameInfo;
import utility.DrawHandler;
import utility.Drawable;

public class GameOverlay implements Drawable{
	
	public GameOverlay(){
		DrawHandler.register(this);
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.drawString("Score: " + GameInfo.getScore(), 10, 35*25);
		g2.drawString("Level: " + GameInfo.getLevel(), 300, 35*25);
		g2.drawString("Lives: " + GameInfo.getLives(), 500, 35*25);
	}

}
