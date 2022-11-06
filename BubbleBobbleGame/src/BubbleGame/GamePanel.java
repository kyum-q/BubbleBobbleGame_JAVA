package BubbleGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;

import BubbleGame.BubbleObject.Bubble;
import BubbleGame.gameObject.Block;
import BubbleGame.gameObject.Coordinates;
import BubbleGame.gameObject.Player;
import utility.Settings;
import BubbleGame.gameObject.Block;

public class GamePanel extends JPanel {
	private Player player1;
	private Map map;

	/**
	 * Create the panel.
	 */
	public GamePanel() {
		setLayout(null);
		player1 = new Player("src/image/player1-move-right", 1);
		add(player1);

		// 맵 그리기
		map = new Map("src/resource/map2.txt");
		ArrayList<Block> blocks = map.getBlocks();
		for (Block block : blocks)
			this.add(block);
		this.setBackground(Color.black);
		this.addKeyListener(new KeyListener());
		this.requestFocus();
		this.setFocusable(true);

		GameThread gameThread = new GameThread();
		gameThread.start();
	}

	class GameThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					gameControll();
					repaint();
					Thread.sleep(20);

				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	//캐릭터가 벽에 부딪히거나, 몬스터와 충돌하거나, 아이템을 먹는 등 필요한 요소를 체크하는 함수
	public void gameControll() {
		playerWallCrushCheck();
	}
	
	public void playerWallCrushCheck() {
		ArrayList<Block> blocks = map.getBlocks();
		for(Block block : blocks) {
			if(player1.wallCollision(block.getX(), block.getX() + Settings.BLOCK_SIZE,
					block.getY(), block.getY()+Settings.BLOCK_SIZE)) {
				System.out.println("X : " +block.getX() +",Y: " +block.getY() +" 블록 부딪힘");
				player1.setWallCrush(true);
				return;
			}else {
				player1.setWallCrush(false);
			}
		}
	}
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

	}

	class KeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				player1.setMoveDown(true);
				break;
			case KeyEvent.VK_UP:
				if (player1.getAbleToJump()) {
					player1.setMoveUp(true);
					player1.setJumping(true);
				}					
				break;
			case KeyEvent.VK_LEFT:
				player1.setMoveLeft(true);
				break;
			case KeyEvent.VK_RIGHT:
				player1.setMoveRight(true);
				break;
			case KeyEvent.VK_SPACE:
				if (player1.isMoveRight()) {
					add(new Bubble("src/image/bubble-green", player1.getX(), player1.getY(), 1));
				} else if (player1.isMoveLeft()) {
					add(new Bubble("src/image/bubble-green", player1.getX(), player1.getY(), -1));
				} else {
					add(new Bubble("src/image/bubble-green", player1.getX(), player1.getY(), 1));
				}
				break;

			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				player1.setMoveDown(false);
				break;
			case KeyEvent.VK_UP:
				player1.setMoveUp(false);
				break;
			case KeyEvent.VK_LEFT:
				player1.setMoveLeft(false);
				break;
			case KeyEvent.VK_RIGHT:
				player1.setMoveRight(false);
				break;
			}
		}
	}
}
