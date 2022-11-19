package BubbleGame;


import utility.Settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import WaitingRoom.GameInPanel;
import utility.Settings;

public class BubbleBobbleGame extends JFrame {

	private GameInPanel inPanel;
	private MainGamePanel mainGamePanel;
	private String player1Name;
	private String player2Name;

	private GamePanel gamePanel = null;
	private ScorePanel scorePanel = null;
	private JSplitPane splitpane = null;
	
	private Timer loadingTimer = new Timer();
	private TimerTask loadingTask;
	
	public static boolean isMain;
	public static boolean isGame;
	public static boolean isChange;
	public static boolean isNext;
	public static boolean isIn;
	GameProcessThread gameThread;
	int check = 0;
	public int stage =1;
	/**
	 * Create the frame.
	 */

	public BubbleBobbleGame() {
		
		setTitle("bubble-bobble");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((int)Settings.SCENE_WIDTH, (int)Settings.SCENE_HEIGHT);
		
		//시작하고 싶은 flag 하나면 true 시키면 됨, isChange는 꼭 true
		init();
		
		gameThread = new GameProcessThread();
		gameThread.start();
		setResizable(false); 	
		setVisible(true);
	}
	public void init() {	
		isMain = false;
		isGame = true;
		isChange= true;
		isIn = true;
	}

	public void setPane(JPanel panel) {
		//mainGamePanel = (MainGamePanel) panel;
		add(panel);
}
	//game panel
	 private void setGamePanel(GamePanel gamePanel) {

	      Container c = getContentPane();
	      c.setLayout(new BorderLayout());
	    //  c.add(gamePanel, BorderLayout.CENTER);
	      
	      splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	      //scorePanel = new ScorePanel();
	      //splitpane.setTopComponent(gamePanel.getScorePanel());
	      splitpane.setTopComponent(gamePanel.getScorePanel());
	      
	      splitpane.setDividerLocation(55);
	      splitpane.setEnabled(false); // splitPane 위치 고정
	      splitpane.setDividerSize(0);
	      splitpane.setBorder(null);
	      
	      splitpane.setBottomComponent(gamePanel);
	      setLocationRelativeTo(null); 
	      c.add(splitpane, BorderLayout.CENTER);
	      this.isGame = false;
	      	  
	      repaint();
	      //이거 안주면 안보임 왜 안보이지..??
	      setVisible(true);
	      //포커스 지정
	      gamePanel.setFocusable(true);
	      gamePanel.requestFocus();
	   }

	

	class GameProcessThread extends Thread {
		public void run() {
			while (true) {
				if (isChange) {
					isChange = false;
					gameMode();
				}
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
	//패널간 이동이 생길 때 띄어주는 loading창
	public void changePanelTimer(int delay, GamePanel gamePanel) {
		LoadingPanel loadingPanel = new LoadingPanel();
		Container c =this.getContentPane() ;

		c.add(loadingPanel);
		loadingTimer = new Timer();
		loadingTask = new TimerTask() {
			@Override
			public void run() { 
				this.cancel() ;
				c.remove(loadingPanel);
				setGamePanel(gamePanel);		
			}
		};
		loadingTimer.schedule(loadingTask, delay);// 
	}
	
	//변화가 있을 때만 호출
	public void gameMode() { 
		this.isChange = false;	
		if (isMain) {
			isMain = false;		
		}
		else if (isNext) {			
			 System.out.println("			isNext");
	         //game panel 중단하고 새로운 game panel 생성
	         isNext = false;
	         this.stage++;
	         this.getContentPane().remove(this.splitpane);
	         
	         ScorePanel scorePanel = gamePanel.getScorePanel();
	         Map map = new Map(this.stage);
	         GamePanel gamePeanl = new GamePanel(scorePanel, map);
	         changePanelTimer(1000, gamePeanl);
		}		
		else if(isGame) {
			 System.out.println("			IsGame");  
	         ScorePanel scorePanel = new ScorePanel();
	         Map map = new Map(1);
	         this.gamePanel = new GamePanel(scorePanel, map);
	         setGamePanel(gamePanel);
	         isGame= false;
		}else if(isIn) {
			inPanel = new GameInPanel(this);
			add(inPanel);
		}
	

	
	
	}

	public ScorePanel getScorePanel() {
		return scorePanel;
	}

	public void setScorePanel(ScorePanel scorePanel) {
		this.scorePanel = scorePanel;

	}

}