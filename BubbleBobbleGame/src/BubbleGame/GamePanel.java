package BubbleGame;
import javax.swing.*;


public class GamePanel extends JPanel {
	   private ImageIcon img = new ImageIcon("src/image/player-blue-move-left/frame-01.png");
	   
	   /**
	    * Create the panel.
	    */
	   public GamePanel() {
	      //setLayout(null);
	      
	      JLabel label = new JLabel("hello");
	      add(label);
	      
	      JLabel player = new JLabel(img);
	      add(player);
	   }

}
