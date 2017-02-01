package game;

import javax.swing.JFrame;
import gui.Frame;
import gui.MainMenu;
import utility.Game;

public class Main extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Main(){
		add(new Frame());
		setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Frame.WIDTH + 6, Frame.HEIGHT + 26);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
	}
	
	public static void main(String[] args) {
		DEBUG.ENABLED = true;
		Game.init();
		MainMenu.instance();
		new Main();
	}
}
