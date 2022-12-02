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
import java.util.Random;

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
	private SendThread sendThread;
	Image heartImg = new ImageIcon("src/image/heart.png").getImage();
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
		add(player1, new Integer(10));
		add(player2, new Integer(10));

		userName = WaitingPanel.userName;
		myPlayerNum = WaitingPanel.getMyPlayerNum();
		if (WaitingPanel.getMyPlayerNum() == 1)
			myself = player1;
		else
			myself = player2;

		// 맵 그리기
		// map = new Map("src/resource/map1.txt");
		// blocks = map.getBlocks();

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

//		this.sendThread = new SendThread();
//		sendThread.start();
	}

	public String getUserName() {
		return userName;
	}
	
	public void meetBubbleMonster(int bubbleNum, int monsterNum, int x, int y) {

		ChatMsg obcm = new ChatMsg(userName, "501", bubbleNum + "," + monsterNum + "," + x + "," + y);
//		System.out
//		.println("!!!!!!!!!!! send items : " + bubbleNum + "," +monsterNum + "," + x + "," + y);
		if(obcm != null)
			WaitingPanel.SendObject(obcm);
	}
	
	public void SocketMeetBubbleMonster(String[] s) {
//		System.out
//		.println("!!!!!!!!!!! items :"+ s[0] + "," + s[1] + "," + s[2] + "," + s[3]);
		if(bubbles.size() > Integer.parseInt(s[0]) && monsters.size() > Integer.parseInt(s[1])) {
			Bubble b = bubbles.get(Integer.parseInt(s[0]));
			b.monsterCatch(monsters.get(Integer.parseInt(s[1])), (Integer.parseInt(s[2])), (Integer.parseInt(s[3])));
		}
	}
	//bubble.monsterCatch(monster);

	public void addItem(Item item) {
		items.add(item);
		add(items.get(items.size() - 1), new Integer(5));
	}

	public void addItemSet(int bubbleNum, int playerNum) {
		Random rand = new Random();
		int itemScore = rand.nextInt((Settings.ITEM_MAX_SCORE) + Settings.ITEM_MIN_SCORE) / 100 * 100;
		int itemNum = rand.nextInt(Settings.ITEM_NUM);
		ChatMsg obcm = new ChatMsg(userName, "601", playerNum + "," + bubbleNum + "," + itemNum + "," + itemScore);
//		System.out
//		.println("!!!!!!!!!!! send items : " + playerNum + "," +bubbleNum + "," + itemNum + "," + itemScore);
		if(obcm != null)
			WaitingPanel.SendObject(obcm);
	}

	public void SocketChangeItem(String[] s) {
		if(bubbles.size() > Integer.parseInt(s[1])) {
			Bubble b = bubbles.get(Integer.parseInt(s[1]));
		
			if (s[0].equals("1"))
				b.bubbleBomb(player1, (Integer.parseInt(s[2])), (Integer.parseInt(s[3])));
			else
				b.bubbleBomb(player2, (Integer.parseInt(s[2])), (Integer.parseInt(s[3])));
		}
	}
	
	public void setItemLocation(int itemNum, int x, int y) {
		ChatMsg obcm = new ChatMsg(userName, "602", itemNum + "," + x + "," + y);
		if(obcm != null) WaitingPanel.SendObject(obcm);
	}
	
	public void SocketItemLocation(String[] s) {
		//System.out.println("!!!!!!!!!!! items :"+ s[0] + "," + s[1] + "," + s[2]);
		if(items.size() > Integer.parseInt(s[0])) {
			Item i = items.get(Integer.parseInt(s[0]));
			i.setWallCrush(true, (Integer.parseInt(s[1])), (Integer.parseInt(s[2])));
			//System.out.println("######################## item Location");
		}
	}
	
	public void incrementScore(int playerNum, int itemNum) {
		ChatMsg obcm = new ChatMsg(userName, "603", playerNum + "," + itemNum);
		if(obcm != null) WaitingPanel.SendObject(obcm);
	}
	
	public void SocketIncrementScore(String[] s) {
		if(items.size() > Integer.parseInt(s[1])) {
			Item i = items.get(Integer.parseInt(s[1]));
			//System.out.println("!!!!!!!!!!! items :"+ s[0] + "," + s[1]);
			if (s[0].equals("1"))
				player1.addScore(i.getScore());
			else
				player2.addScore(i.getScore());
			
			items.remove(i);
			remove(i);
	
		}
	}

	public void addMonster(int x, int y, String name, int i) {
		monsters.add(new Monster(x, y, name, i));
		add(monsters.get(monsters.size() - 1));
	}

	@Override
	public void remove(Component comp) {
		super.remove(comp);

		// bubble객체일 경우, bubble Arraylist객체에서도 삭제
		if (comp instanceof Bubble) {
			//bubbles.remove(comp);
		}
		// moster객체일 경우, moster Arraylist객체에서도 삭제
		else if (comp instanceof Monster) {
			monsters.remove(comp);
		} else if (comp instanceof Item) {
			//items.remove(comp);
		}
	}

	public void sendRandomBubbleMove(Bubble b) {
		Random rand = new Random();
		int moveDirection = rand.nextInt(5);

		ChatMsg obcm = new ChatMsg(WaitingPanel.userName, "502",
				bubbles.indexOf(b) + "," + b.getX() + "," + b.getY() + "," + ((moveDirection > 1) ? 1 : -1));
		if(obcm != null) WaitingPanel.SendObject(obcm);
	}

	public void SocketbubbleMove(String[] s) {
//		System.out
//				.println("%%%%%%%%%%%%%%%%%%%%%%%%%%% " + s.length + "," + s[0] + "," + s[1] + "," + s[2] + "," + s[3]);
		if(bubbles.size() > Integer.parseInt(s[0])) {
			Bubble b = bubbles.get(Integer.parseInt(s[0]));
			b.topWallMove(Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
		}
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

	class SendThread extends Thread {
		@Override
		public void run() {
			while (true) {
				ChatMsg obcm1 = null;
				try {
					String s = myPlayerNum + "@@";

					// player 좌표를 보냄
					int x = myself.getX();
					int y = myself.getY();
					obcm1 = new ChatMsg(userName, "403", myPlayerNum + "@@" + x + "," + y);
					if(obcm1 != null) WaitingPanel.SendObject(obcm1);

					Thread.sleep(300);
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
		player1MonsterCrushCheck();
		player2MonsterCrushCheck();
		monsterWallCrushCheck();
		ItemWallCrushCheck();
		checkBubbleMonster();

		checkNextStage();

		if (!this.threadFlag) {
			this.gameThread.interrupt();
			//this.sendThread.interrupt();
			this.player1.setThreadFlag(false);
			this.player2.setThreadFlag(false);
		}

	}

	int count = 0;
	boolean isChangeStage = false;

	public void checkNextStage() {
		if (myPlayerNum == 1 && monsters.size() <= 0 && !isChangeStage) {
			count++;
			if (count > 200) {
				isChangeStage = true;
				ChatMsg obcm = new ChatMsg(userName, "300", "nextStage");
				if(obcm != null) WaitingPanel.SendObject(obcm);
			}
		}
	}
	
	public void SocketNextStge() {
		//System.out.println("몬스터 모두 처리");
		this.threadFlag = false;
		BubbleBobbleGame.isChange = true;
		BubbleBobbleGame.isNext = true;
		//sendThread.interrupt();
		//gameThread.interrupt();
	}
	/* 플레이어와 몬스터가 부딪히는지 체크 */
	public void player2MonsterCrushCheck() {
		if (!player2.isImmortal()) {
			for (Monster m : monsters) {
				if (player2.monsterCollision(m.getX(), m.getX() + Settings.SPRITE_SIZE, m.getY(),
						m.getY() + Settings.SPRITE_SIZE)) {
					System.out.println("player2222 몬스터 충돌");
					player2.setMonsterCrush(true);
					return;
				} else {
					player2.setMonsterCrush(false);
				}
			}
		}
	}

	/* 플레이어와 몬스터가 부딪히는지 체크 */
	public void player1MonsterCrushCheck() {
		// 플레이어가 무적이 아닐 때만 체크
		if (!player1.isImmortal()) {
			for (Monster m : monsters) {
				if (player1.monsterCollision(m.getX(), m.getX() + Settings.SPRITE_SIZE, m.getY(),
						m.getY() + Settings.SPRITE_SIZE)) {
					System.out.println("player11111 몬스터 충돌");
					player1.setMonsterCrush(true);
					return;
				} else {
					player1.setMonsterCrush(false);
				}

			}
		}

	}

	/* 플레이어1이 벽이 부딪히는지 체크 */
	public void player1WallCrushCheck() {
		ArrayList<Block> blocks = map.getBlocks();
		for (Block block : blocks) {
			if (player1.wallCollision(block.getX(), block.getX() + block.getWidth(), block.getY(),
					block.getY() + block.getHeight())) {
				player1.setWallCrush(true);
				if(myPlayerNum == 1 && myself.isJumping() && myself.getSpriteBase().getDyCoordinate() > 0) {
					int x = myself.getX();
					int y = myself.getY();
					ChatMsg obcm1 = new ChatMsg(userName, "403", myPlayerNum + "@@" + x + "," + y);
					if(obcm1 != null) WaitingPanel.SendObject(obcm1);
				}
				break;
			} else {
				player1.setWallCrush(false);
			}
		}
	}

	/* 플레이어2가 벽이 부딪히는지 체크 */
	public void player2WallCrushCheck() {
		ArrayList<Block> blocks = map.getBlocks();
		for (Block block : blocks) {
			if (player2.wallCollision(block.getX(), block.getX() + block.getWidth(), block.getY(),
					block.getY() + block.getHeight())) {
				player2.setWallCrush(true);
				if(myPlayerNum == 2 && myself.isJumping() && myself.getSpriteBase().getDyCoordinate() > 0) {
					int x = myself.getX();
					int y = myself.getY();
					ChatMsg obcm1 = new ChatMsg(userName, "403", myPlayerNum + "@@" + x + "," + y);
					if(obcm1 != null) WaitingPanel.SendObject(obcm1);
				}
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
					monster.setWallCrush(true);
					break;
				} else {
					monster.setWallCrush(false);
				}
			}
		}
	}

	
	
	public void ItemWallCrushCheck() {
		// ArrayList<Block> blocks = map.getBlocks();
		for (Item item : items) {
			if(this.myPlayerNum == 1 && !item.isWallCrush()) {
				for (Block block : blocks) {
					if (item.wallCollision(block.getX(), block.getX() + Settings.BLOCK_WIDTH, block.getY(), block.getY())) {
						setItemLocation(items.indexOf(item), item.getX(), item.getY());
						break;
					} else {
						item.setWallCrush(false);
					}
				}
			}
			if (this.myPlayerNum == 1 && item.wallCollision(player1.getX(), player1.getX() + player1.getWidth(), player1.getY(),
					player1.getY() + player1.getHeight())) {
				incrementScore(1, items.indexOf(item));
				break;
			}
			if (this.myPlayerNum == 2 && item.wallCollision(player2.getX(), player2.getX() + player2.getWidth(), player2.getY(),
					player2.getY() + player2.getHeight())) {
				incrementScore(2, items.indexOf(item));
				break;
			}
		}
	}

	private void checkBubbleMonster() {
		// bubble에 monster 닿았는지 체크 후 닿았으면 제거
		for (Bubble bubble : bubbles) { // 버블
			for (Monster monster : monsters) { // 몬스터
				// 버블하고 몬스터 만났는지 확인
				if (this.myPlayerNum == 1 && bubble.wallCollision(monster.getX(), monster.getX() + monster.getWidth(), monster.getY(),
						monster.getY() + monster.getHeight())) {
					meetBubbleMonster(bubbles.indexOf(bubble), monsters.indexOf(monster), bubble.getX(), bubble.getY());
					//bubble.monsterCatch(monster);
					break;
				}
			}
			if (this.myPlayerNum == 1 && bubble.wallCollision(player1.getX(), player1.getX() + player1.getWidth(), player1.getY(),
					player1.getY() + player1.getHeight())) {
				if (bubble.bubbleMeetPlayer(player1)) {
					addItemSet(bubbles.indexOf(bubble), 1);
					break;
				}
			}
			if (this.myPlayerNum == 1 && bubble.wallCollision(player2.getX(), player2.getX() + player2.getWidth(), player1.getY(),
					player2.getY() + player2.getHeight())) {
				if (bubble.bubbleMeetPlayer(player2)) {
					addItemSet(bubbles.indexOf(bubble), 2);
					break;
				}
			}
		}
	}

	public void movePlayerPosition(String[] playerInfo) {
		String[] position = playerInfo[1].split(",");
		double x = Integer.parseInt(position[0]);
		double y = Integer.parseInt(position[1]);
		//System.out.println("GamePanel position ###### " + x + ":" + y);

		Player other;
		if (Integer.parseInt(playerInfo[0]) == 1)
			other = player1;
		else
			other = player2;

		other.setX(x);
		other.setY(y);

	}

	public void movePlayerTrue(String[] playerInfo) {
		String KeyCode = playerInfo[1];
		//System.out.println("GamePanel ###### " + playerInfo[1]);

		Player other;
		if (playerInfo[0].equals("1"))
			other = player1;
		else
			other = player2;

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
		//System.out.println("GamePanel ###### " + playerInfo[0] + ":" + playerInfo[1]);
		Player other;
		if (Integer.parseInt(playerInfo[0]) == 1)
			other = player1;
		else
			other = player2;

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
		
		if(! BubbleBobbleGame.player1Dead) {
			for(int i=0; i< Player.player1Live; i++) {
				g.drawImage(heartImg, 20 + 45*i, (int) (Settings.SCENE_HEIGHT -140), myself);
			}
		}
		if(! BubbleBobbleGame.player2Dead) {
			for(int i=0; i< Player.player2Live; i++) {
				g.drawImage(heartImg, (int) ((Settings.SCENE_WIDTH) - 180 + 20 + 45*i),  (int) (Settings.SCENE_HEIGHT -140), myself);
			}
		}	
	}

	class KeyListener extends KeyAdapter {
		public boolean plag = true;
		@Override
		public void keyPressed(KeyEvent e) {
			 {
			if(myPlayerNum == 1 ) plag = BubbleBobbleGame.player1Dead;
			else plag = BubbleBobbleGame.player2Dead;
			if(!plag) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_DOWN:
					WaitingPanel.SendObject(new ChatMsg(userName, "401", myPlayerNum + "@@VK_DOWN"));
					break;
				case KeyEvent.VK_UP:
					WaitingPanel.SendObject(new ChatMsg(userName, "401", myPlayerNum + "@@VK_UP"));
					break;
				case KeyEvent.VK_LEFT:
					WaitingPanel.SendObject(new ChatMsg(userName, "401", myPlayerNum + "@@VK_LEFT"));
					break;
				case KeyEvent.VK_RIGHT:
					WaitingPanel.SendObject(new ChatMsg(userName, "401", myPlayerNum + "@@VK_RIGHT"));
					break;
				case KeyEvent.VK_SPACE:
					WaitingPanel.SendObject(new ChatMsg(userName, "401", myPlayerNum + "@@VK_SPACE"));
					break;
				case KeyEvent.VK_ESCAPE:
					WaitingPanel.SendObject(new ChatMsg(userName, "401", myPlayerNum + "@@VK_ESCAPE"));
					break;
				}
			}
			
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
			if(myPlayerNum == 1 ) plag = BubbleBobbleGame.player1Dead;
			else plag = BubbleBobbleGame.player2Dead;
			if(!plag) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_DOWN:
					WaitingPanel.SendObject(new ChatMsg(userName, "402", myPlayerNum + "@@VK_DOWN"));
					break;
				case KeyEvent.VK_UP:
					WaitingPanel.SendObject(new ChatMsg(userName, "402", myPlayerNum + "@@VK_UP"));
					break;
				case KeyEvent.VK_LEFT:
					WaitingPanel.SendObject(new ChatMsg(userName, "402", myPlayerNum + "@@VK_LEFT"));
					break;
				case KeyEvent.VK_RIGHT:
					WaitingPanel.SendObject(new ChatMsg(userName, "402", myPlayerNum + "@@VK_RIGHT"));
					break;
				case KeyEvent.VK_SPACE:
					WaitingPanel.SendObject(new ChatMsg(userName, "402", myPlayerNum + "@@VK_SPACE"));
					break;
				}
			}
			
		}
	}

	public ScorePanel getScorePanel() {
		return this.scorePanel;
	}
}
