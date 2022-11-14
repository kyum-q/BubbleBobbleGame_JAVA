package BubbleGame.gameObject;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import utility.Settings;

public class Block extends JLabel{
	Image img = new ImageIcon("src/image/wall.png").getImage();
	int x, y, w, h;
	public Block() {}
	
	public Block(int x, int y) {
		this.x= x;
		this.y= y;
		this.w = Settings.BLOCK_WIDTH;
		this.h = Settings.BLOCK_HEIGHT;
		this.setBounds(x,y, w, h);
	}
	
	public void paintComponent(Graphics g) {
		setLocation(x,y);
		g.drawImage(img, 0, 0, w,  h, this);
	}
}
