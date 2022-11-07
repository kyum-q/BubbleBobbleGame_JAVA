package BubbleGame;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

import gameObject.Coordinates;
import gameObject.Player;
import gameObject.Bubble.Bubble;
import gameObject.Monster.Monster;

public class GamePanel extends JPanel {
	   private Player player1;
	   private ArrayList<Monster> monster = new ArrayList<Monster>();
	   private ArrayList<Bubble> bubble = new ArrayList<Bubble>();
	   private int score = 0;
	   
	   //Coordinates coordinates, String dirPath, int playerNumber
	   public GamePanel() {
	      setLayout(null);
		   //Coordinates coordinates, String dirPath, int playerNumber
		  player1= new Player("src/image/player1-move-right", 1);
	      add(player1);
	      
	      monster.add(new Monster(400,400,"zenchan",1));
	      add(monster.get(monster.size()-1));
	      
	      DrawThread drawThread = new DrawThread();
	      drawThread.start();
	      
	      this.setBackground(Color.black);
	      this.addKeyListener(new KeyListener());
			this.requestFocus();
			this.setFocusable(true);
	   }
	   
	   public void addScore(int score) {
		   this.score += score;
		   System.out.println("score: "+score);
	   }
	   
	   @Override
	   public void remove(Component comp) {
		   super.remove(comp);
		   
		   // bubble객체일 경우, bubble Arraylist객체에서도 삭제
		    if(comp instanceof Bubble) 
			   bubble.remove(comp);
		   //moster객체일 경우, moster Arraylist객체에서도 삭제
		   else if(comp instanceof Monster) 
			   monster.remove(comp);
		   
	   }
	   
	   class DrawThread extends Thread {
		   @Override
		   public void run() {
			   while(true) {
				   try {			
						repaint();
						checkBubbleMonster();
						Thread.sleep(20);
							
					} catch (InterruptedException e) {
							return;
					}
			   }
		   }
		   
		   private void checkBubbleMonster() {
			// bubble에 monster 닿았는지 체크 후 닿았으면 제거
				Iterator<Bubble> bu = bubble.iterator();
				while(bu.hasNext()) { // 버블
					Bubble b = bu.next();
					Iterator<Monster> mo = monster.iterator();
					while(mo.hasNext()) { // 몬스터
						Monster m = mo.next();		
						// 버블하고 몬스터 만났는지 확인
						if(b.checkBubbleMit(m)) { 
							remove(m); //몬스터 삭제
							break;
						}
					}
					if(b.checkBubbleMit(player1)) {
						
					}
				}
		   }
	   }
	   @Override
		public void paintComponent(Graphics g) { 
	        //System.out.println("(x,y) : " + player1.getY()+"," + player1.getX());
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
			case KeyEvent.VK_SPACE:
				if(player1.isDirection()) {
					bubble.add(new Bubble(player1.getX(), player1.getY(), 1));
				}
				else {
					bubble.add(new Bubble(player1.getX(), player1.getY(), -1));
				}
				add(bubble.get(bubble.size()-1));
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
