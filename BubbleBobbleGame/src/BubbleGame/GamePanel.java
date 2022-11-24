package BubbleGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
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
import WaitingRoom.ChatMsg;
import WaitingRoom.WaitingPanel;
import utility.Settings;

public class GamePanel extends JLayeredPane {

	private Player player1;
	private Player player2;
	
	private Player myself;
	private String userName;

	private int myPlayerNum;
	
	private Map map;
	private ArrayList<Monster> monsters = null;
	private ArrayList<Block> blocks = null;
	private ArrayList<Bubble> bubbles = new ArrayList<Bubble>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ScorePanel scorePanel = null;
	private int score = 0;
	private GameThread gameThread;
	
	public boolean threadFlag = true;
	/**
	 * Create the panel.
	 */
	public GamePanel(ScorePanel scorePanel, Map map) {
		 
		setLayout(null);
		this.scorePanel = scorePanel;
		
		// 배경 색 설정
		setOpaque(true);
		this.setBackground(Color.BLACK);

		player1 = new Player(1, scorePanel.getScorePanel(1));
		player2 = new Player(2, scorePanel.getScorePanel(2));
//		player2.setX(Settings.SCENE_WIDTH-10);
		add(player1,new Integer(10));
		add(player2,new Integer(10));
		
		userName = WaitingPanel.userName;
		myPlayerNum = WaitingPanel.getMyPlayerNum();
		if(WaitingPanel.getMyPlayerNum() == 1) myself = player1;
		else myself = player2;
		
		// 맵 그리기
		//map = new Map("src/resource/map1.txt");
		//blocks = map.getBlocks();

		this.map = map;
		blocks = map.getBlocks();

		for (Block block : blocks)
			this.add(block, new Integer(0));
		monsters = map.getMonster();
		for (Monster monster : monsters)
			this.add(monster, new Integer(5));
		
		this.addKeyListener(new KeyListener());
		this.requestFocus();
		this.setFocusable(true);

		this.gameThread = new GameThread();
		gameThread.start();
	}
	
	public String getUserName() {
		return userName;
	}

	public void addItem(int x, int y) {
		items.add(new Item(x,y));
		add(items.get(items.size() - 1),new Integer(5)); 
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
		player1WallCrushCheck();
		player2WallCrushCheck();
		playerMonsterCrushCheck();
		monsterWallCrushCheck();
		ItemWallCrushCheck();
		checkBubbleMonster();
		
		checkNextStage();
		
		if(!this.threadFlag) {
			this.gameThread.interrupt();
			this.player1.setThreadFlag(false);
		}
			
		
		
	}
	
	public void checkNextStage() {
		if(monsters.size() <= 0) {
			System.out.println("몬스터 모두 처리");
			 this.threadFlag = false;
			BubbleBobbleGame.isChange = true;
			BubbleBobbleGame.isNext = true;
		}
		}
			
//=======
//	public void playerMonsterCrushCheck() {
//		for(Monster m : monsters) {
//			if(myself.monsterCollision(m.getX(), m.getX() + Settings.SPRITE_SIZE,
//					m.getY(), m.getY() + Settings.SPRITE_SIZE)) {
//				//System.out.println("몬스터 충돌");
//				myself.setMonsterCrush(true);
//				return;
//			}else {
//				myself.setMonsterCrush(false);
//			}
//>>>>>>> KM
	
	/*플레이어와 몬스터가 부딪히는지 체크*/
	public void playerMonsterCrushCheck() {
		//플레이어가 무적이 아닐 때만 체크
		if(!player1.isImmortal()) {
			for(Monster m : monsters) {
				if(player1.monsterCollision(m.getX(), m.getX() + Settings.SPRITE_SIZE,
						m.getY(), m.getY() + Settings.SPRITE_SIZE) ) {
					//System.out.println("몬스터 충돌");
					player1.setMonsterCrush(true);
				
				}else {
					player1.setMonsterCrush(false);
				}
				
			}
		}
		if(!player2.isImmortal()) {
			for(Monster m : monsters) {
				if(player2.monsterCollision(m.getX(), m.getX() + Settings.SPRITE_SIZE,
						m.getY(), m.getY() + Settings.SPRITE_SIZE)) {
					//System.out.println("몬스터 충돌");
					player2.setMonsterCrush(true);}
				else {
					player2.setMonsterCrush(false);
				}
			}
		}
		
	}
	/*플레이어1이 벽이 부딪히는지 체크*/
	public void player1WallCrushCheck() {
		ArrayList<Block> blocks = map.getBlocks();
		for (Block block : blocks) {
			if (player1.wallCollision(block.getX(), block.getX() + block.getWidth(), block.getY(),
					block.getY() + block.getHeight())) {
				player1.setWallCrush(true);
				break;
			} else {
				player1.setWallCrush(false);
			}	
		}			
	}
	/*플레이어2가 벽이 부딪히는지 체크*/
	public void player2WallCrushCheck() {
		ArrayList<Block> blocks = map.getBlocks();
		for (Block block : blocks) {
			if (player2.wallCollision(block.getX(), block.getX() + block.getWidth(), block.getY(),
					block.getY() + block.getHeight())) {
				player2.setWallCrush(true);
				return;
			} else {
				player2.setWallCrush(false);
			}
		}
	}

	public void monsterWallCrushCheck() {
		ArrayList<Block> blocks = map.getBlocks();
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
						block.getY())) {
					item.setWallCrush(true);
					break;
				} else {
					item.setWallCrush(false);
				}
			}
			if (item.wallCollision(myself.getX(), myself.getX() + myself.getWidth(), myself.getY(),
					myself.getY() + myself.getHeight())) {
				myself.addScore(item.getScore());
				remove(item);
				break;
			}
			if (item.wallCollision(player2.getX(), player1.getX() + player2.getWidth(), player1.getY(),
					player2.getY() + player2.getHeight())) {
				player2.addScore(item.getScore());
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
			if (bubble.wallCollision (myself.getX(), myself.getX() +myself.getWidth(), myself.getY(),
					myself.getY() + myself.getHeight())) {
				bubble.bubbleMeetPlayer(myself);
				break;
			}
			if (bubble.wallCollision (player2.getX(), player2.getX() +player2.getWidth(), player1.getY(),
					player2.getY() + player2.getHeight())) {
				bubble.bubbleMeetPlayer(player2);
				break;
			}
		}
	}
	
	public void movePlayerTrue(String[] playerInfo) {	
		String KeyCode = playerInfo[1];
		System.out.println("GamePanel ###### "+playerInfo[1]);
		Player other;
		if(playerInfo[0].equals("1")) other = player1;
		else other = player2;
		
		switch (KeyCode) {
		case "VK_DOWN":
			other.setMoveDown(true);
			break;
		case "VK_UP":
			if (other.getAbleToJump()) {
				other.setMoveUp(true);
				other.setJumping(true);
			}
			break;
		case "VK_LEFT":
			other.setMoveLeft(true);
			break;
		case "VK_RIGHT":
			other.setMoveRight(true);
			break;
		case "VK_SPACE":
			other.setShoot(true);
			break;
		case "VK_ESCAPE":
			System.exit(0);
			break;
		}
	}
	
	public void movePlayerFalse(String[] playerInfo) {	
		String KeyCode = playerInfo[1];
		System.out.println("GamePanel ###### "+playerInfo[0]+":"+playerInfo[1]);
		Player other;
		if(Integer.parseInt(playerInfo[0])==1) other = player1;
		else other = player2;
		
		switch (KeyCode) {
		case "VK_DOWN":
			other.setMoveDown(false);
			break;
		case "VK_UP":
			other.setMoveUp(false);
			break;
		case "VK_LEFT":
			other.setMoveLeft(false);
			break;
		case "VK_RIGHT":
			other.setMoveRight(false);
			break;
		case "VK_SPACE":
			other.setShoot(false);
			if (other.isDirection()) {
				bubbles.add(new Bubble(other.getX(), other.getY(), 1));
			} else {
				bubbles.add(new Bubble(other.getX(), other.getY(), -1));
			}
			add(bubbles.get(bubbles.size() - 1), new Integer(5));
			break;
		}
	}
	

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
	}

	class KeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			ChatMsg obcm = null;
			switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				//myself.setMoveDown(true);
				obcm = new ChatMsg(userName, "401", myPlayerNum+"@@VK_DOWN");
				break;
			case KeyEvent.VK_UP:
//				if (myself.getAbleToJump()) {
//					myself.setMoveUp(true);
//					myself.setJumping(true);
//				}
				obcm = new ChatMsg(userName, "401", myPlayerNum+"@@VK_UP");
				break;
			case KeyEvent.VK_LEFT:
				//myself.setMoveLeft(true);
				obcm = new ChatMsg(userName, "401", myPlayerNum+"@@VK_LEFT");
				break;
			case KeyEvent.VK_RIGHT:
				//myself.setMoveRight(true);
				obcm = new ChatMsg(userName, "401", myPlayerNum+"@@VK_RIGHT");
				break;
			case KeyEvent.VK_SPACE:
				//myself.setShoot(true);
				obcm = new ChatMsg(userName, "401", myPlayerNum+"@@VK_SPACE");
				break;
//				if (player1.isDirection()) {
//					bubbles.add(new Bubble(player1.getX(), player1.getY(), 1));
//				} else {
//					bubbles.add(new Bubble(player1.getX(), player1.getY(), -1));
//				}
//				add(bubbles.get(bubbles.size() - 1), new Integer(5));
//				break;
			/*case KeyEvent.VK_S:
				player2.setMoveDown(true);
				break;
			case KeyEvent.VK_W:
				if (player2.getAbleToJump()) {
					player2.setMoveUp(true);
					player2.setJumping(true);
				}
				break;
			case KeyEvent.VK_A:
				player2.setMoveLeft(true);
				break;
			case KeyEvent.VK_D:
				player2.setMoveRight(true);
				break;
			case KeyEvent.VK_SHIFT:
				player2.setShoot(true);
				break;
				*/
			case KeyEvent.VK_ESCAPE:
				//System.exit(0);
				obcm = new ChatMsg(userName, "401", myPlayerNum+"@@VK_ESCAPE");
				break;
			}
			WaitingPanel.SendObject(obcm);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			ChatMsg obcm = null;
			switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				//myself.setMoveDown(false);
				obcm = new ChatMsg(userName, "402", myPlayerNum+"@@VK_DOWN");
				break;
			case KeyEvent.VK_UP:
				//myself.setMoveUp(false);
				obcm = new ChatMsg(userName, "402", myPlayerNum+"@@VK_UP");
				break;
			case KeyEvent.VK_LEFT:
				//myself.setMoveLeft(false);
				obcm = new ChatMsg(userName, "402", myPlayerNum+"@@VK_LEFT");
				break;
			case KeyEvent.VK_RIGHT:
				//myself.setMoveRight(false);
				obcm = new ChatMsg(userName, "402", myPlayerNum+"@@VK_RIGHT");
				break;
			case KeyEvent.VK_SPACE:
//				myself.setShoot(false);
//				if (myself.isDirection()) {
//					bubbles.add(new Bubble(myself.getX(), myself.getY(), 1));
//				} else {
//					bubbles.add(new Bubble(myself.getX(), myself.getY(), -1));
//				}
//				add(bubbles.get(bubbles.size() - 1), new Integer(5));
				obcm = new ChatMsg(userName, "402", myPlayerNum+"@@VK_SPACE");
				break;
				
//			case KeyEvent.VK_S:
//				player2.setMoveDown(false);
//				break;
//			case KeyEvent.VK_W:
//				player2.setMoveUp(false);
//				break;
//			case KeyEvent.VK_A:
//				player2.setMoveLeft(false);
//				break;
//			case KeyEvent.VK_D:
//				player2.setMoveRight(false);
//				break;
//			case KeyEvent.VK_SHIFT:
//				player2.setShoot(false);
//				if (player2.isDirection()) {
//					bubbles.add(new Bubble(player2.getX(), player2.getY(), 1));
//				} else {
//					bubbles.add(new Bubble(player2.getX(), player2.getY(), -1));
//				}
//				add(bubbles.get(bubbles.size() - 1), new Integer(5));
//				break;
			}
			WaitingPanel.SendObject(obcm);
		}
	}

	public ScorePanel getScorePanel() {
		return this.scorePanel;
	}
}
