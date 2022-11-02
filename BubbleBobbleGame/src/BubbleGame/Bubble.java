package BubbleGame;

import java.awt.*;
import java.io.File;

import javax.swing.*;

public class Bubble extends JLabel {
	private int startX, startY;
	private int width, height;
	protected Image[] bubbleGreen;
	protected Image[] bubbleRed;
	protected Image[] bubbleBurst;
	private Image img;
	private int bubbleState = 0;  // bubble ป๓ลย (0: greenBubble, 1: redBubble, 2: bubbleBurst)
	private String imagePath[] = new String[] { "src/image/bubble-green", "src/image/bubble-red",
			"src/image/bubble-green-burst" };

	public Bubble(int startX, int startY) {
		this.startX = startX;
		this.startY = startY;
		this.width = 30;
		this.height = 30;
		
		//ImageIcon icon = new ImageIcon("src/image/bubble-green"+'/'+"frame-01.png");
		
		//img = icon.getImage();
		
		
		imageSet();
		//this.setBounds(startX, startY, width, height);
		this.setBounds(50, 400, 40, 40);
	}
	
	

	private void imageSet() {
		for (int j = 0; j < imagePath.length; j++) {
			File f = new File(imagePath[0]);
			String[] bubblePaths;
			if (f.isDirectory()) {
				bubblePaths = f.list();
				int i = 0;
				for (String str : bubblePaths) {
					// System.out.println(str);
					ImageIcon icon = new ImageIcon(imagePath[0]+'/'+str);
					if (j == 0) {
						bubbleGreen = new Image[bubblePaths.length];
						img = icon.getImage();
						//System.out.println(img);
					}
					else if (j == 1) {
						bubbleRed = new Image[bubblePaths.length];
						bubbleRed[i] = icon.getImage();
					}
					else if (j == 2) {
						bubbleBurst = new Image[bubblePaths.length];
						bubbleBurst[i] = icon.getImage();
					}
					i++;
				}
			}
		}
		img = bubbleGreen[0];
		System.out.println(bubbleGreen);
	}
	

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
