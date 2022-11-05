package BubbleGame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;

import BubbleGame.BubbleObject.Bubble;
import BubbleGame.gameObject.Coordinates;
import BubbleGame.gameObject.Player;

public class GamePanel extends JPanel {
	   private Player player1;
	   /**
	    * Create the panel.
	    */
	   public GamePanel() {
	      setLayout(null);
		  player1= new Player("src/image/player1-move-right", 1);
	      add(player1);
	      
	      //맵 그리기
	      Map map1 = new Map("src/resource/map4.txt");
	      ArrayList<Block> blocks = map1.getBlocks();
	      for(Block block : blocks)
	    	  this.add(block);
	      this.setBackground(Color.black);
	      this.addKeyListener(new KeyListener());
			this.requestFocus();
			this.setFocusable(true);

		     DrawThread drawThread = new DrawThread();
		      drawThread.start();
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
				break;
			case KeyEvent.VK_RIGHT:
				player1.setMoveRight(true);
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
