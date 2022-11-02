package BubbleGame;

import java.awt.*;

import javax.swing.*;

public class Player2 extends JLabel {
	protected Image img;

	public Player2(ImageIcon icon) {
		img = icon.getImage();
		this.setBounds(50, 400, 40, 40);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}