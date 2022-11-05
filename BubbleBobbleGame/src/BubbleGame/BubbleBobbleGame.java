package BubbleGame;

import javax.swing.*;
import utility.Settings;
public class BubbleBobbleGame extends JFrame {

	private GamePanel gamePanel = null;

	/**
	 * Create the frame.
	 */
	public BubbleBobbleGame() {
		setTitle("bubble-bobble");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(665, 750);
		setSize(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		
		gamePanel = new GamePanel();
		setContentPane(gamePanel);
		//setResizable(false); 
		setVisible(true);
	}



}