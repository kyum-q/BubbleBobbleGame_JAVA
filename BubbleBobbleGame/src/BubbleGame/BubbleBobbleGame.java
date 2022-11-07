package BubbleGame;


import javax.swing.JFrame;
import utility.Settings;
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
		setSize((int)Settings.SCENE_WIDTH, (int)Settings.SCENE_HEIGHT);

		gamePanel = new GamePanel();
		setContentPane(gamePanel);
		// setLocationRelativeTo(null); 
		setResizable(false); 

		setVisible(true);
	}



}