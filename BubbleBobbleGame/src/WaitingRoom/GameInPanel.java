package WaitingRoom;



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
	private JButton joinBtn;
	private Image image;
	public GameInPanel() {
		setLayout(null);
		setOpaque(true);
		this.setBackground(Color.BLACK);
		
		String serverList[] = {"1","2","3"};
		
		Font font = new Font ("HBIOS-SYS", Font.PLAIN, 20);
		serverComboBox = new JComboBox<String>(serverList);
		serverComboBox.setFont(font);
		//serverComboBox.setBackground(Color.BLACK);
		//serverComboBox.setForeground(Color.gray.brighter());
		serverComboBox.setAlignmentX(JTextField.CENTER);
		//serverComboBox.setAlignmentY(JTextField.CENTER);
		serverComboBox.setBounds((int)Settings.SCENE_WIDTH/2-(100/2), 120, 100, 30);
		add(serverComboBox);

		font = new Font ("HBIOS-SYS", Font.PLAIN, 30);
		
		JLabel nameLabel = new JLabel("UserName");
		nameLabel.setBounds((int)Settings.SCENE_WIDTH/2-(200/2), 440, 200, 40);
		nameLabel.setFont(font);
		nameLabel.setForeground(new Color(255, 186, 0));
		nameLabel.setHorizontalAlignment(JTextField.CENTER);
		add(nameLabel);
		
		txtUserName = new JTextField(10);
		//txtUserName.setBackground(Color.BLACK);
		//txtUserName.setForeground(Color.WHITE);
		txtUserName.setBounds((int)Settings.SCENE_WIDTH/2-(200/2), 480, 200, 30);
		txtUserName.setFont(font);
		txtUserName.setHorizontalAlignment(JTextField.CENTER);
		txtUserName.setBorder(BorderFactory.createCompoundBorder( null, null)); //new LineBorder(Color.gray.brighter(),1)
		add(txtUserName);
		
		font = new Font ("HBIOS-SYS", Font.PLAIN, 50);
		joinBtn = new JButton("JOIN");
		joinBtn.setBounds((int)Settings.SCENE_WIDTH/2-(370/2), 520, 370, 40);
		//startText.setBounds((int)Settings.SCENE_WIDTH/2-(370/2), 540, 370, 40);
		joinBtn.setHorizontalAlignment(JLabel.CENTER);
		joinBtn.setFont(font);
		joinBtn.setOpaque(true);
		joinBtn.setBackground(Color.BLACK);
		joinBtn.setForeground(Color.RED);
		joinBtn.setBorderPainted(false);
		joinBtn.setFocusPainted(false);
		add(joinBtn);
		
		JoinAction joinAction = new JoinAction();
		joinBtn.addActionListener(joinAction);
		
		JLabel back = new JLabel("");
		back.setBounds((int)Settings.SCENE_WIDTH/2-(450/2), 440, 450, 100);
		back.setOpaque(true);
		back.setBackground(Color.BLACK);
		add(back);
		
		image = Toolkit.getDefaultToolkit().createImage("src/image/intro.gif"); 
		
		//this.requestFocusInWindow();
		//getTextField().setFocusable(true);
	}
	
	public String getServerNum() {
		return serverComboBox.getSelectedItem().toString();
	}
	
	public String getUserName() {
		return txtUserName.getText();
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		if (image != null) {  
		      g.drawImage(image, -100, 60, this.getWidth()+200, this.getHeight()-100, this);
		    }  
	}
	
	class JoinAction implements ActionListener // �궡遺��겢�옒�뒪濡� �븸�뀡 �씠踰ㅽ듃 泥섎━ �겢�옒�뒪
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = txtUserName.getText().trim();

			//JavaObjClientView view = new JavaObjClientView(username, ip_addr, port_no);			
			
			BubbleBobbleGame.isChange = true;
			BubbleBobbleGame.isWaitingRoom = true;
			//bubbleGame.setPane(new WaitingPanel(username, serverComboBox.getSelectedItem().toString(), bubbleGame));
			setVisible(false);
		}
	}


}


