package WaitingRoom;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import BubbleGame.Map;
import BubbleGame.gameObject.Block;
import BubbleGame.gameObject.Player;
import BubbleGame.gameObject.SpriteBase;
import BubbleGame.gameObject.monster.Monster;

public class WaitingPanel extends JPanel {
	Image img = null;
	String path[];
	private SpriteBase spriteBase; // 이미지 설정을 도와주는 class
	
	public WaitingPanel() {
		setLayout(null);

		// 배경 색 설정
				setOpaque(true);
				this.setBackground(Color.BLACK);

		//"src/image/intro";
		
		this.spriteBase = new SpriteBase("src/image/intro");
		String dirPath = spriteBase.getDirPath();
		File dir = new File(dirPath);

		this.spriteBase.setImagePaths(dir.list());
		
		//this.requestFocus();
		//this.setFocusable(true);
		
		InitThread initThread = new InitThread();
		initThread.start();
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
					Thread.sleep(20);
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
		g.drawImage(img, 0,90, this.getWidth(), this.getHeight()-180, null);
	}

}
