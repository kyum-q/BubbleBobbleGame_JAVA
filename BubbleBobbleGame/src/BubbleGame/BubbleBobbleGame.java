package BubbleGame;


import utility.Settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import main.BackgroundPanel;
import main.Cookie;
import main.CookieGame;
import main.GameOverPanel;
import main.GamePanel;
import main.LankingPanel;
import main.LoadingPanel;
import main.MainPanel;
import main.Monster;
import main.WordPanel;
import utility.Settings;

public class BubbleBobbleGame extends JFrame {

	private GamePanel gamePanel = null;
	private ScorePanel scorePanel = null;
	private JSplitPane splitpane = null;
	
	private Timer loadingTimer = new Timer();
	private TimerTask loadingTask;
	
	public static boolean isMain;
	public static boolean isGame;
	public static boolean isChange;
	public static boolean isNext;
	
	public int stage =1;
	/**
	 * Create the frame.
	 */
	public BubbleBobbleGame() {
		
		setTitle("bubble-bobble");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((int)Settings.SCENE_WIDTH, (int)Settings.SCENE_HEIGHT);

		init();
		setSplitPane();
		setResizable(false); 
		setVisible(true);
	}
	
	//game panel
	private void setSplitPane(GamePanel gamePanel) {
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		scorePanel = new ScorePanel();
		splitpane.setTopComponent(scorePanel);
		
		splitpane.setDividerLocation(55);
		splitpane.setEnabled(false); // splitPane 위치 고정
		splitpane.setDividerSize(0);
		splitpane.setBorder(null);
		
		splitpane.setBottomComponent(gamePanel);
		// setLocationRelativeTo(null); 
		c.add(splitpane, BorderLayout.CENTER);
		this.isGame = false;
	}
	public void init() {	
		isMain = false;
		isGame = true;
		isChange= false;
	}

	class GameProcessThread extends Thread {
		public void run() {
			while (true) {
				if (isChange) {
					isChange = false;
					gameMode();
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
	public void changePanelTimer(int delay) {
		
		setContentPane(new LoadingPanel());
		loadingTimer = new Timer();
		loadingTask = new TimerTask() {
			@Override
			public void run() { 
				this.cancel() ;
				//setContentPane(contentPane);			
			}
		};
		loadingTimer.schedule(loadingTask, delay);// 1 �� �ڿ�WordPanel �гη� ��ü
	}
	
	//변화가 있을 때만 호출
public void gameMode() { 
		
		//cleanPanel();		
		if (isMain) {
			isMain = false;		
		}
		else if (isNext) {				
			changePanelTimer(1000);
			//game panel 중단하고 새로운 game panel 생성
			isNext = false;
			this.stage++;
			//gamePanel = new GamePanel();
		}
		
		else if(isGame) {
			
			changePanelTimer(1000);	
			System.out.println("�ε���");
			gamePanel = new GamePanel();
		}
	

	
	
	}

	public ScorePanel getScorePanel() {
		return scorePanel;
	}

	public void setScorePanel(ScorePanel scorePanel) {
		this.scorePanel = scorePanel;
	}

}