package BubbleGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import BubbleGame.BubbleObject.Bubble;
import BubbleGame.gameObject.Coordinates;
import BubbleGame.gameObject.Player;

public class GamePanel extends JPanel {
	private Player player1;

	/**
	 * Create the panel.
	 */

	// Coordinates coordinates, String dirPath, int playerNumber
	public GamePanel() {
		setLayout(null);
		
		// Coordinates coordinates, String dirPath, int playerNumber
		player1 = new Player("src/image/player1-move-right", 1);
		add(player1);
		
		DrawThread drawThread = new DrawThread();
		drawThread.start();

		this.setBackground(Color.black);
		this.addKeyListener(new KeyListener());
		this.requestFocus();
		this.setFocusable(true);
	}

	class DrawThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					repaint();
					Thread.sleep(40);

				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

//		Image player1Image = player1.getImage();
//		g.drawImage(player1Image, (int) player1.getX(), (int) player1.getY(), null);
	}

	class KeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) { // 키占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙

			switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				player1.setMoveDown(true);
				break;
			case KeyEvent.VK_UP:
				player1.setMoveUp(true);
				break;
			case KeyEvent.VK_LEFT:
				player1.setMoveLeft(true);
				System.out.println("�쇊履�");
				break;
			case KeyEvent.VK_RIGHT:
				player1.setMoveRight(true);
				System.out.println("�삤瑜몄そ");
				break;
			case KeyEvent.VK_SPACE:
				if(player1.isMoveRight()) {
					add(new Bubble("src/image/bubble-green", player1.getX(), player1.getY(), 1));
				}
				else if(player1.isMoveLeft()) {
					add(new Bubble("src/image/bubble-green", player1.getX(), player1.getY(), -1));
				}
				else {
					add(new Bubble("src/image/bubble-green", player1.getX(), player1.getY(), 1));
				}
				//player1.setJumping(true);
				//player1.setMoveUp(true);
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			}

		}

		@Override
		public void keyReleased(KeyEvent e) { // 키占쏙옙 占쏙옙占쏙옙占� 占쏙옙

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