package WaitingRoom;

import java.awt.Color;

import javax.swing.JLayeredPane;

import BubbleGame.Map;
import BubbleGame.ScorePanel;
import BubbleGame.gameObject.Block;
import BubbleGame.gameObject.Player;
import BubbleGame.gameObject.monster.Monster;

public class WatingPanel extends JLayeredPane {
	private ScorePanel scorePanel = null;
	public WatingPanel(ScorePanel scorePanel) {
		setLayout(null);
		
		this.scorePanel = scorePanel;

		// 배경 색 설정
		setOpaque(true);
		this.setBackground(Color.BLACK);
	}
}
