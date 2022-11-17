package BubbleGame;


import utility.Settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.*;

import WaitingRoom.GameInPanel;
import utility.Settings;

public class BubbleBobbleGame extends JFrame {

	private GameInPanel inPanel;
	private MainGamePanel mainGamePanel;
	
	public BubbleBobbleGame() {
		
		setTitle("bubble-bobble");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((int)Settings.SCENE_WIDTH, (int)Settings.SCENE_HEIGHT);

		//this.setContentPane(new MainGamePanel());
		inPanel = new GameInPanel(this);
		add(inPanel);
		setResizable(false); 
		setVisible(true);
	}
	
	public void setPane(JPanel panel) {
		mainGamePanel = (MainGamePanel) panel;
		add(panel);
	}

}