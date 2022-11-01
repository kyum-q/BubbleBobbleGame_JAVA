package Game;

import java.awt.*;
import javax.swing.*;
public class GamePanel extends JPanel {

	private ImageIcon img = null;
	private Player player = null;
	
	/**
	 * Create the panel.
	 */
	public GamePanel() {
		//setLayout(null);
		
		JLabel label = new JLabel("hello");
		add(label);
	}

}
