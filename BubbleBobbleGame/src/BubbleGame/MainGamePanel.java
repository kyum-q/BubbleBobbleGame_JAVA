package BubbleGame;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import WaitingRoom.WaitingPanel;
import utility.Settings;

public class MainGamePanel extends JLayeredPane {

	private GamePanel gamePanel = null;
	private ScorePanel scorePanel = null;
	private JSplitPane splitpane = null;

	public MainGamePanel(WaitingPanel waitingPanel) {
		scorePanel = new ScorePanel();
		scorePanel.setUp1Name(waitingPanel.getP1Name());
		scorePanel.setUp2Name(waitingPanel.getP2Name());
		gamePanel = new GamePanel(scorePanel, waitingPanel);
		setSplitPane(); 
		setVisible(true);
	}
	
	private void setSplitPane() {
		this.setLayout(new BorderLayout());
		
		splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitpane.setTopComponent(scorePanel);
		
		splitpane.setDividerLocation(55);
		splitpane.setEnabled(false); // splitPane 위치 고정
		splitpane.setDividerSize(0);
		splitpane.setBorder(null);
		
		splitpane.setBottomComponent(gamePanel);
		// setLocationRelativeTo(null); 
		this.add(splitpane, BorderLayout.CENTER);
	}

	public ScorePanel getScorePanel() {
		return scorePanel;
	}

	public void setScorePanel(ScorePanel scorePanel) {
		this.scorePanel = scorePanel;
	}
}
