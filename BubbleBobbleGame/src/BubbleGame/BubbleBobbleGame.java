package BubbleGame;


import utility.Settings;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.*;
import utility.Settings;

public class BubbleBobbleGame extends JFrame {

	private GamePanel gamePanel = null;
	private ScorePanel scorePanel = null;
	private JSplitPane splitpane = null;
	/**
	 * Create the frame.
	 */
	public BubbleBobbleGame() {
		
		setTitle("bubble-bobble");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((int)Settings.SCENE_WIDTH, (int)Settings.SCENE_HEIGHT);

		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		scorePanel = new ScorePanel();
		splitpane.setTopComponent(scorePanel);
		splitpane.setDividerLocation(50);
		
		gamePanel = new GamePanel();
		splitpane.setBottomComponent(gamePanel);
		//setContentPane(gamePanel);
		// setLocationRelativeTo(null); 
		c.add(splitpane, BorderLayout.CENTER);
		setResizable(false); 

		setVisible(true);
	}



}