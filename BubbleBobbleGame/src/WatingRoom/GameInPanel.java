package WatingRoom;



import java.awt.Color;

import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import BubbleGame.BubbleBobbleGame;
import utility.Settings;

public class GameInPanel extends JPanel {
	
	private Image img = null;
	private String path[];
	private JComboBox serverComboBox;
	private JTextField txtUserName;
	private JButton startText;
	private Image image;
	private BubbleBobbleGame bubbleGame;
	public GameInPanel(BubbleBobbleGame bubbleGame) {
		setLayout(null);

		setOpaque(true);
		this.setBackground(Color.BLACK);
		
		this.bubbleGame = bubbleGame;

		StartAction startAction = new StartAction();
		String serverList[] = {"ROOM 1","ROOM 2","ROOM 3"};
		
		Font font = new Font ("HBIOS-SYS", Font.PLAIN, 20);
		serverComboBox = new JComboBox<String>(serverList);
		serverComboBox.setFont(font);
		//serverComboBox.setBackground(Color.BLACK);
		//serverComboBox.setForeground(Color.gray.brighter());
		serverComboBox.setAlignmentX(JTextField.CENTER);
		//serverComboBox.setAlignmentY(JTextField.CENTER);
		serverComboBox.setBounds((int)Settings.SCENE_WIDTH/2-(100/2), 100, 100, 30);
		add(serverComboBox);

		font = new Font ("HBIOS-SYS", Font.PLAIN, 30);
		
		JLabel nameLabel = new JLabel("UserName");
		nameLabel.setBounds((int)Settings.SCENE_WIDTH/2-(200/2), 420, 200, 40);
		nameLabel.setFont(font);
		nameLabel.setForeground(new Color(255, 186, 0));
		nameLabel.setHorizontalAlignment(JTextField.CENTER);
		add(nameLabel);
		
		txtUserName = new JTextField(10);
		//txtUserName.setBackground(Color.BLACK);
		//txtUserName.setForeground(Color.WHITE);
		txtUserName.setBounds((int)Settings.SCENE_WIDTH/2-(200/2), 460, 200, 30);
		txtUserName.setFont(font);
		txtUserName.setHorizontalAlignment(JTextField.CENTER);
		txtUserName.setBorder(BorderFactory.createCompoundBorder( null, null)); //new LineBorder(Color.gray.brighter(),1)
		add(txtUserName);
		
		font = new Font ("HBIOS-SYS", Font.PLAIN, 50);
		startText = new JButton("START");
		startText.setBounds((int)Settings.SCENE_WIDTH/2-(370/2), 500, 370, 40);
		//startText.setBounds((int)Settings.SCENE_WIDTH/2-(370/2), 540, 370, 40);
		startText.setHorizontalAlignment(JLabel.CENTER);
		startText.setFont(font);
		startText.setForeground(Color.RED);
		startText.setOpaque(true);
		startText.setBackground(Color.BLACK);
		startText.setBorder(BorderFactory.createCompoundBorder( null, null));
		add(startText);
		
		startText.addActionListener(startAction);
		
		JLabel back = new JLabel("");
		back.setBounds((int)Settings.SCENE_WIDTH/2-(450/2), 420, 450, 100);
		back.setOpaque(true);
		back.setBackground(Color.BLACK);
		add(back);
		
		image = Toolkit.getDefaultToolkit().createImage("src/image/intro.gif"); 
		
		//this.requestFocusInWindow();
		//getTextField().setFocusable(true);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		if (image != null) {  
		      g.drawImage(image, -100, 60, this.getWidth()+200, this.getHeight()-100, this);
		    }  
	}
	
	class StartAction implements ActionListener // �궡遺��겢�옒�뒪濡� �븸�뀡 �씠踰ㅽ듃 泥섎━ �겢�옒�뒪
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = txtUserName.getText().trim();
			String ip_addr = "127.0.0.1";
			String port_no = "30000";
			
			//JavaObjClientView view = new JavaObjClientView(username, ip_addr, port_no);			
			
			bubbleGame.setPane(new WatingPanel(username, ip_addr, port_no));
			setVisible(false);
		}
	}


}


