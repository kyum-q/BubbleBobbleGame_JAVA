package BubbleGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utility.Settings;

public class EndingPanel extends JPanel {
	private Image img = null;
	private String path[];
	private JTextField txtUserName;
	private JButton joinBtn;
	private Image image;
	public EndingPanel(String endingText1, String endingText2, Color endingColor) {
		setLayout(null);
		setOpaque(true);
		this.setBackground(Color.BLACK);
		
		Font font = new Font ("던파 비트비트체 TTF", Font.PLAIN, 50);
		JLabel gameEnding = new JLabel(endingText1);
		gameEnding.setBounds((int)Settings.SCENE_WIDTH/2-(500/2), 150, 500, 40);
		gameEnding.setHorizontalAlignment(JLabel.CENTER);
		gameEnding.setFont(font);
		gameEnding.setForeground(endingColor);
		add(gameEnding);
		
		font = new Font ("던파 비트비트체 TTF", Font.PLAIN, 40);
		JLabel happy1 = new JLabel(endingText2);
		happy1.setBounds((int)Settings.SCENE_WIDTH/2-300, 300, 200, 40);
		happy1.setHorizontalAlignment(JLabel.CENTER);
		happy1.setFont(font);
		happy1.setForeground(new Color(254,0,118));
		add(happy1);
		
		JLabel happy2 = new JLabel(endingText2);
		happy2.setBounds((int)Settings.SCENE_WIDTH/2+70, 300, 200, 40);
		happy2.setHorizontalAlignment(JLabel.CENTER);
		happy2.setFont(font);
		happy2.setForeground(new Color(254,0,118));
		add(happy2);
		
		JLabel end1 = new JLabel("END !!");
		end1.setBounds((int)Settings.SCENE_WIDTH/2-300, 340, 200, 40);
		end1.setHorizontalAlignment(JLabel.CENTER);
		end1.setFont(font);
		end1.setForeground(new Color(254,0,118));
		add(end1);
		
		JLabel end2 = new JLabel("END !!");
		end2.setBounds((int)Settings.SCENE_WIDTH/2+70, 340, 200, 40);
		end2.setHorizontalAlignment(JLabel.CENTER);
		end2.setFont(font);
		end2.setForeground(new Color(254,0,118));
		add(end2);
	
		image = Toolkit.getDefaultToolkit().createImage("src/image/ending-heart.gif"); 
		
		//this.requestFocusInWindow();
		//getTextField().setFocusable(true);
	}
	
	public String getUserName() {
		return txtUserName.getText();
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		if (image != null) {  
		      g.drawImage(image, this.getWidth()/2-(150/2), 240, 150, 140, this);
		    }  
	}
}
