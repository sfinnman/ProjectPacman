package game;

import javax.swing.JFrame;
import gui.Frame;
import utility.EventHandler;
import utility.GameState;
import utility.ResourceLoader;

public class Main extends JFrame{
	
	public Main(){
		add(new Frame());
		setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Frame.WIDTH + 6, Frame.HEIGHT + 20);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
	}
	
	public static void main(String[] args) {
		DEBUG.ENABLED = true;
		EventHandler.init();
		ResourceLoader.init();
		Runnable game = new Runnable(){
			@Override
			public void run() {
				Map.loadMap();
			}
		};
		Runnable gui = new Runnable(){
			@Override
			public void run() {
				new Main();
			}
		};
		GameState.init();
		gui.run();
	}
}
