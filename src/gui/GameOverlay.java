package gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game.GameInfo;
import utility.DPoint;
import utility.DrawHandler;
import utility.Drawable;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.ResourceLoader;

public class GameOverlay implements Drawable {

	public GameOverlay() {
		DrawHandler.instance().register(this);
	}

	public static void gameStart() {
		new GameMessage(3000) {
			@Override
			public void draw(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.WHITE);
				g2.setFont(ResourceLoader.getFont("crackman", 25));
				g2.drawString("GAME START!", 25 * 11 - 10, 25 * 21);
			}
		};
	}

	public static void gameReload(int level) {
		new GameMessage(3000) {
			@Override
			public void draw(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.WHITE);
				g2.setFont(ResourceLoader.getFont("crackman", 25));
				g2.drawString("LEVEL " + level, 25 * 11 + 20, 25 * 21);
			}
		};
	}
	
	public static void showScore(DPoint p, int score) {
		new GameMessage(1000) {
			@Override
			public void draw(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.WHITE);
				g2.setFont(ResourceLoader.getFont("crackman", 25));
				FontMetrics fm = g2.getFontMetrics();
				g2.drawString(""+score, (int) (p.x * 25) + 12 - fm.stringWidth("" + score) / 2,
						(int) (p.y * 25) + fm.getHeight() + 12);
			}
		};
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
		int levelx = Frame.WIDTH / 2 - fm.stringWidth(level) / 2;
		String lives = "Lives: " + GameInfo.getLives();
		int livesx = Frame.WIDTH - fm.stringWidth(lives) - 10;
		g2.drawString(score, scorex, 890);
		g2.drawString(level, levelx, 890);
		g2.drawString(lives, livesx, 890);

		g2.setFont(ResourceLoader.getFont("crackman", 50));
		fm = g2.getFontMetrics();
		String title = "PacMan";
		int titlex = Frame.WIDTH / 2 - fm.stringWidth(title) / 2;
		g2.drawString(title, titlex, 65);
	}

	public abstract static class GameMessage implements Drawable, Listener {
		private int lifetime;

		public GameMessage(int lifetime) {
			this.lifetime = lifetime;
			EventHandler.instance().subscribeEvent("game_tick", this);
			DrawHandler.instance().register(this);
		}

		@Override
		public void onRegister(String key, EventData src) {
			if (lifetime == 0) {
				EventHandler.instance().unsubscribeEvent("game_tick", this);
				DrawHandler.instance().unregister(this);
			}
			lifetime--;
		}
	}

}
