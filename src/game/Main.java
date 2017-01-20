package game;

import javax.swing.JFrame;
import gui.Frame;
import utility.DrawHandler;
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
		DrawHandler.init();
		GameState.init();
		new Main();
	}
}
