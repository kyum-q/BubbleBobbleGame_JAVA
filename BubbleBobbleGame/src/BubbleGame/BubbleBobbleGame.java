package BubbleGame;
import javax.swing.*;

public class BubbleBobbleGame  extends JFrame {

	   private GamePanel gamePanel = null;

	   /**
	    * Create the frame.
	    */
	   public BubbleBobbleGame() {
	      setTitle("bubble-bobble");
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      setSize(500,500);
	      
	      gamePanel = new GamePanel();
	      setContentPane(gamePanel);
	      //setLocationRelativeTo(null); // 윈도우 가운데 배치
	      setResizable(false); // 창크기 고정
	      setVisible(true);
	   }
	   
	   public void setGame() {
	      
	   }

	}