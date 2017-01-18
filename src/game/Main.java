package game;

import javax.swing.JFrame;
import gui.Frame;
import utility.EventHandler;
import utility.ResourceLoader;

public class Main extends JFrame{
	
	public Main(){
		add(new Frame());
		setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(706, 920);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
	}
	
	public static void main(String[] args) {
		DEBUG.ENABLED = true;
		Runnable game = new Runnable(){
			@Override
			public void run() {
				EventHandler.init();
				ResourceLoader.init();
				Map.loadMap();
			}
		};
		Runnable gui = new Runnable(){
			@Override
			public void run() {
				new Main();
			}
		};
		game.run();
		gui.run();
	}
}
