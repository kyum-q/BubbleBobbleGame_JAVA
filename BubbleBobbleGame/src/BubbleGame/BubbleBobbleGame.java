package BubbleGame;


import utility.Settings;

import java.awt.BorderLayout;
import java.awt.Color;
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

		setSplitPane();
		setResizable(false); 
		setVisible(true);
	}
	
	private void setSplitPane() {
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		scorePanel = new ScorePanel();
		splitpane.setTopComponent(scorePanel);
		
		splitpane.setDividerLocation(55);
		splitpane.setEnabled(false); // splitPane 위치 고정
		splitpane.setDividerSize(0);
		splitpane.setBorder(null);
		
		gamePanel = new GamePanel(scorePanel);
		splitpane.setBottomComponent(gamePanel);
		// setLocationRelativeTo(null); 
		c.add(splitpane, BorderLayout.CENTER);
	}

	public ScorePanel getScorePanel() {
		return scorePanel;
	}

	public void setScorePanel(ScorePanel scorePanel) {
		this.scorePanel = scorePanel;
	}

}