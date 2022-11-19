package BubbleGame;


import utility.Settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.*;

import WaitingRoom.WaitingPanel;
import utility.Settings;

public class BubbleBobbleGame extends JFrame {

	private WaitingRoom.GameInPanel inPanel;
	private MainGamePanel mainGamePanel;
	private String player1Name;
	private String player2Name;
	
	public BubbleBobbleGame() {
		
		setTitle("bubble-bobble");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((int)Settings.SCENE_WIDTH, (int)Settings.SCENE_HEIGHT);

		//this.setContentPane(new MainGamePanel());
		inPanel = new WaitingRoom.GameInPanel(this);
		add(inPanel);
		setResizable(false); 
		setVisible(true);
	}
	
	public void setPane(JLayeredPane panel) {
		//mainGamePanel = (MainGamePanel) panel;
		add(panel);
	}

}