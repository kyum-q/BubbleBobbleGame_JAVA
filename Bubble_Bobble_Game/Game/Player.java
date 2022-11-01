package Game;

import java.awt.*;
import javax.swing.*;

public class Player extends JLabel {
	private Image img;
	public Player(ImageIcon icon) {
		super();
		img = icon.getImage();
		setBounds(10,10,50,50);
	}
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
