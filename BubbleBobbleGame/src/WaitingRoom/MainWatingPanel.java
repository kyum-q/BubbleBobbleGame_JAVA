package WaitingRoom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import BubbleGame.GamePanel;
import BubbleGame.ScorePanel;
import BubbleGame.gameObject.SpriteBase;
import WaitingRoom.GameInPanel.InitThread;
import WaitingRoom.GameInPanel.StartAction;
import utility.Settings;

public class MainWatingPanel extends JPanel {
	
	private GamePanel gamePanel = null;
	private ScorePanel scorePanel = null;
	private JSplitPane splitpane = null;
	
	public MainWatingPanel() {
		setSplitPane(); 
		setVisible(true);
	}
	
	private void setSplitPane() {
		this.setLayout(new BorderLayout());
		
		splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		scorePanel = new ScorePanel();
		splitpane.setTopComponent(scorePanel);
		
		splitpane.setDividerLocation(55);
		splitpane.setEnabled(false); // splitPane 위치 고정
		splitpane.setDividerSize(0);
		splitpane.setBorder(null);
		
		gamePanel = new GamePanel(scorePanel);
		splitpane.setBottomComponent(gamePanel);
		// setLocationRelativeTo(null); 
		this.add(splitpane, BorderLayout.CENTER);
	}
}
