package WaitingRoom;

import java.awt.Color;
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
import BubbleGame.GamePanel;
import BubbleGame.MainGamePanel;
import BubbleGame.Map;
import BubbleGame.gameObject.Block;
import BubbleGame.gameObject.Player;
import BubbleGame.gameObject.SpriteBase;
import BubbleGame.gameObject.monster.Monster;
import utility.Settings;

public class GameInPanel extends JPanel {
	private BubbleBobbleGame bubbleBobbleGame;
	
	private Image img = null;
	private String path[];
	private SpriteBase spriteBase; // 이미지 설정을 도와주는 class

	private JComboBox serverComboBox;
	private JTextField txtUserName;
	private JButton startText;
	
	public GameInPanel(BubbleBobbleGame bubbleBobbleGame) {
		
		this.bubbleBobbleGame = bubbleBobbleGame;
		
		setLayout(null);

		// 배경 색 설정
		setOpaque(true);
		this.setBackground(Color.BLACK);

		this.spriteBase = new SpriteBase("src/image/intro");
		String dirPath = spriteBase.getDirPath();
		File dir = new File(dirPath);

		this.spriteBase.setImagePaths(dir.list());
		
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
		
		InitThread initThread = new InitThread();
		initThread.start();
		
		//this.requestFocusInWindow();
		//getTextField().setFocusable(true);
	}

	/* img 알아내기(전달) */
	public Image getImage() {
		String path = this.spriteBase.getImage();
		Image image = new ImageIcon(path).getImage();
		return image;
	}
	

	class InitThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					repaint();
					Thread.sleep(30);
					
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Image img = getImage();
		g.drawImage(img, -100, 60, this.getWidth()+200, this.getHeight()-100, null);
	}
	
	class StartAction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = txtUserName.getText().trim();
			String ip_addr = "127.0.0.1";
			String port_no = "30000";
			
			//JavaObjClientView view = new JavaObjClientView(username, ip_addr, port_no);			
			//bubbleBobbleGame.setPane(new MainWatingPanel(username, ip_addr, port_no));
			
			bubbleBobbleGame.setPane(new MainGamePanel());
			setVisible(false);
		}
	}


}


