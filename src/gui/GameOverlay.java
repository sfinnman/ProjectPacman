package gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game.GameInfo;
import utility.DrawHandler;
import utility.Drawable;
import utility.ResourceLoader;

public class GameOverlay implements Drawable{
	
	public GameOverlay(){
		DrawHandler.register(this);
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(ResourceLoader.getFont("crackman", 30));
		FontMetrics fm = g2.getFontMetrics();
		g2.setColor(Color.WHITE);
		String score = "Score: " + GameInfo.getScore();
		int scorex = 10;
		String level = "Level: " + GameInfo.getLevel();
		int levelx = Frame.WIDTH/2 - fm.stringWidth(level)/2;
		String lives = "Lives: " + GameInfo.getLives();
		int livesx = Frame.WIDTH - fm.stringWidth(lives) - 10;
		g2.drawString(score, scorex, 890);
		g2.drawString(level, levelx, 890);
		g2.drawString(lives, livesx, 890);
		
		g2.setFont(ResourceLoader.getFont("crackman", 50));
		fm = g2.getFontMetrics();
		String title = "PacMan";
		int titlex = Frame.WIDTH/2 - fm.stringWidth(title)/2;
		g2.drawString(title, titlex, 65);
	}

}
