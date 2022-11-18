package WaitingRoom;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import BubbleGame.Map;
import BubbleGame.ScorePanel;
import BubbleGame.gameObject.Block;
import BubbleGame.gameObject.Player;
import BubbleGame.gameObject.monster.Monster;
import utility.Settings;

public class WatingPanel extends JLayeredPane {
	private ScorePanel scorePanel = null;
	private Map map;
	private ArrayList<Block> blocks = null;
	private Player player1;
	private Player player2;

	public WatingPanel(ScorePanel scorePanel, int playerNum, String playerName) {
		setLayout(null);

		this.scorePanel = scorePanel;

		map = new Map("src/resource/map1.txt");
		blocks = map.getBlocks();
		for (Block block : blocks)
			this.add(block, new Integer(0));

		if (playerNum == 1) {
			player1 = new Player("player1", 1, scorePanel.getScorePanel(1));

			player1.setWallCrush(true);
			player1.setY(517);
			add(player1, new Integer(10));
			scorePanel.setUp1Name(playerName);
		}
		else if (playerNum == 2) {
			player2 = new Player("player2", 2, scorePanel.getScorePanel(2));

			player2.setWallCrush(true);
			player2.setY(517);
			add(player2, new Integer(10));
			scorePanel.setUp2Name(playerName);
		}
		// 배경 색 설정
		setOpaque(true);
		this.setBackground(Color.BLACK);

		InitThread init = new InitThread();
		init.start();
	}

	class InitThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					repaint();
					Thread.sleep(20);
				} catch (InterruptedException e) {
					return;
				}
			}
		}

	}
}
