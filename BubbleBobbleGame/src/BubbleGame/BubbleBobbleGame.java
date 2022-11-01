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
	      setSize(600,600);
	      
	      gamePanel = new GamePanel();
	      setContentPane(gamePanel);
	      //setLocationRelativeTo(null); // ������ ��� ��ġ
	      setResizable(false); // âũ�� ����
	      setVisible(true);
	   }
	   
	   public void setGame() {
	      
	   }

	}