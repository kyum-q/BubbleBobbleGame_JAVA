package BubbleGame;

import javax.swing.*;

public class GamePanel extends JPanel {
	private ImageIcon img = new ImageIcon("src/image/bubble-green"+'/'+"frame-01.png");

	/**
	 * Create the panel.
	 */
	public GamePanel() {
		setLayout(null);

		//Player2 player = new Player2(img);
		//add(player);
		
		Bubble b = new Bubble(50,50);
		add(b);
	}

}
