package BubbleGame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import BubbleGame.gameObject.Coordinates;
import BubbleGame.gameObject.Player;



public class GamePanel extends JPanel {
	   private Player player1;
	   /**
	    * Create the panel.
	    */
	   
	   //Coordinates coordinates, String dirPath, int playerNumber
	   public GamePanel() {
	      setLayout(null);
		   //Coordinates coordinates, String dirPath, int playerNumber
		  player1= new Player("src/image/player1-move-right", 1);
	      add(player1);
	      
//	      JLabel player = new JLabel(img);
//	      add(player);
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
			   while(true) {
				   try {			
						repaint();
						Thread.sleep(20);
							
					}catch (InterruptedException e) {
							return;
					}
			   }
		   }
	   }
	   @Override
		public void paintComponent(Graphics g) { 
			
	        System.out.println("(x,y) : " + player1.getY()+"," + player1.getX());
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
				if(player1.getAbleToJump())
					player1.setMoveUp(true);
					player1.setJumping(true);
				break;
			case KeyEvent.VK_LEFT:
				player1.setMoveLeft(true);
				System.out.println("왼쪽");
				break;
			case KeyEvent.VK_RIGHT:
				player1.setMoveRight(true);
				System.out.println("오른쪽");
				break;
			
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			}
	
		}
	
		@Override
		public void keyReleased(KeyEvent e) { // Ű�� ����� ��
	
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

