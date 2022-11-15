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

import BubbleGame.gameObject.Coordinates;
import javax.swing.*;

import BubbleGame.gameObject.*;
import BubbleGame.gameObject.Block;
import BubbleGame.gameObject.Coordinates;
import BubbleGame.gameObject.Player;
import BubbleGame.gameObject.bubble.Bubble;
import BubbleGame.gameObject.bubble.Item;
import BubbleGame.gameObject.monster.Monster;
import utility.Settings;

public class GamePanel extends JLayeredPane {
	private Player player1;
	private Map map;
	private ArrayList<Monster> monsters = null;
	private ArrayList<Block> blocks = null;
	private ArrayList<Bubble> bubbles = new ArrayList<Bubble>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ScorePanel scorePanel = null;
	private int score = 0;

	/**
	 * Create the panel.
	 */
	public GamePanel(ScorePanel scorePanel) {
		setLayout(null);
		
		this.scorePanel = scorePanel;

		// 배경 색 설정
		setOpaque(true);
		this.setBackground(Color.BLACK);

		player1 = new Player("src/image/player1-move-right", 1, scorePanel.getScorePanel(1));
		add(player1,new Integer(10));

		// 맵 그리기
		//map = new Map("src/resource/map1.txt");
		//blocks = map.getBlocks();

		map = new Map("src/resource/map1.txt");
		blocks = map.getBlocks();



		for (Block block : blocks)
			this.add(block, new Integer(0));
		monsters = map.getMonster();
		for (Monster monster : monsters)
			this.add(monster, new Integer(5));
		this.setBackground(Color.black);
		this.addKeyListener(new KeyListener());
		this.requestFocus();
		this.setFocusable(true);

		GameThread gameThread = new GameThread();
		gameThread.start();
	}
	
	public void addItem(int x, int y) {
		items.add(new Item(x,y));
		add(items.get(items.size() - 1));
	}
	
	public void addMonster(int x, int y, String name, int i) {
		monsters.add(new Monster(x, y, name, i));
		add(monsters.get(monsters.size() - 1));
	}

	@Override
	public void remove(Component comp) {
		super.remove(comp);

		// bubble객체일 경우, bubble Arraylist객체에서도 삭제
		if (comp instanceof Bubble)
			bubbles.remove(comp);
		// moster객체일 경우, moster Arraylist객체에서도 삭제
		else if (comp instanceof Monster)
			monsters.remove(comp);
		else if (comp instanceof Item)
			items.remove(comp);
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

	// 캐릭터가 벽에 부딪히거나, 몬스터와 충돌하거나, 아이템을 먹는 등 필요한 요소를 체크하는 함수
	public void gameControll() {
		playerWallCrushCheck();
		playerMonsterCrushCheck();
		monsterWallCrushCheck();
		ItemWallCrushCheck();
		checkBubbleMonster();
	}
	/*플레이어와 몬스터가 부딪히는지 체크*/
	public void playerMonsterCrushCheck() {
		//플레이어가 무적이 아닐 때만 체크
		if(!player1.isImmortal()) {
			for(Monster m : monsters) {
				if(player1.monsterCollision(m.getX(), m.getX() + Settings.SPRITE_SIZE,
						m.getY(), m.getY() + Settings.SPRITE_SIZE)) {
					//System.out.println("몬스터 충돌");
					player1.setMonsterCrush(true);
					return;
				}else {
					player1.setMonsterCrush(false);
				}
			}
		}
		
	}
	/*플레이어와 벽이 부딪히는지 체크*/
	public void playerWallCrushCheck() {
		//ArrayList<Block> blocks = map.getBlocks();
		for (Block block : blocks) {
			if (player1.wallCollision(block.getX(), block.getX() + block.getWidth(), block.getY(),
					block.getY() + block.getHeight())) {
				// System.out.println("X : " +block.getX() +",Y: " +block.getY() +" 블록 부딪힘");
				player1.setWallCrush(true);
				return;
			} else {
				player1.setWallCrush(false);
			}
		}
		
		
	}

	public void monsterWallCrushCheck() {
		//ArrayList<Block> blocks = map.getBlocks();
		for (Monster monster : monsters) {
			for (Block block : blocks) {
				if (monster.wallCollision(block.getX(), block.getX() + block.getWidth(), block.getY(),
						block.getY() + block.getHeight())) {
					// System.out.println("X : " + block.getX() + ",Y: " + block.getY() + " 블록
					// 부딪힘");
					monster.setWallCrush(true);
					break;
				} else {
					monster.setWallCrush(false);
				}
			}
		}
	}

	public void ItemWallCrushCheck() {
		//ArrayList<Block> blocks = map.getBlocks();
		for (Item item : items) {
			for (Block block : blocks) {
				if (item.wallCollision(block.getX(), block.getX() + Settings.BLOCK_WIDTH, block.getY(),
						block.getY() + Settings.BLOCK_HEIGHT)) {
					item.setWallCrush(true);
					break;
				} else {
					item.setWallCrush(false);
				}
			}
			if (item.wallCollision(player1.getX(), player1.getX() + player1.getWidth(), player1.getY(),
					player1.getY() + player1.getHeight())) {
				player1.addScore(item.getScore());
				remove(item);
				break;
			}
		}
	}

	private void checkBubbleMonster() {
		// bubble에 monster 닿았는지 체크 후 닿았으면 제거
		for (Bubble bubble : bubbles) { // 버블
			for (Monster monster : monsters) { // 몬스터
				// 버블하고 몬스터 만났는지 확인
				if (bubble.wallCollision (monster.getX(), monster.getX() +monster.getWidth(), monster.getY(),
						monster.getY() + monster.getHeight())) {
					bubble.monsterCatch(monster);
					break;
				}
			}
			if (bubble.wallCollision (player1.getX(), player1.getX() +player1.getWidth(), player1.getY(),
					player1.getY() + player1.getHeight())) {
				bubble.bubbleMeetPlayer(player1);
				break;
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
				player1.setShoot(true);
				if (player1.isDirection()) {
					bubbles.add(new Bubble(player1.getX(), player1.getY(), 1));
				} else {
					bubbles.add(new Bubble(player1.getX(), player1.getY(), -1));
				}
				add(bubbles.get(bubbles.size() - 1), new Integer(5));
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
			case KeyEvent.VK_SPACE:
				player1.setShoot(false);
			}
		}
	}
}
