package BubbleGame.BubbleObject;

public class BubbleThread extends Thread {
	private Bubble bubble;
	private int sleepTime = 1000;
	
	/* BubbleThread »ý¼ºÀÚ */
	public BubbleThread(Bubble bubble) {
		this.bubble = bubble;
	}
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	@Override
	public void run() {
		while (true) {
			
			try {
				if (bubble.checkMoveLocation())
					bubble.moveX();
				else
					bubble.moveY();
				
				bubble.repaint();
				Thread.sleep(sleepTime);

			} catch (InterruptedException e) {
				return;
			}
		}
	}
}
