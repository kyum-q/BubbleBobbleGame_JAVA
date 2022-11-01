package BubbleGame.gameObject;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Player {
	private final int playerNumber;
	private double xStartLocation = 10;
	private double yStartLocation = 400;
	private double speed;
	private int score;
	private int lives;
	private SpriteBase spriteBase;
	private double width = 50;
	private double height = 50;
	private Coordinates coordinate;

	
	private boolean isJumping;
    private boolean isFacingRight;
    private int counter;
    private boolean isDelayed;
    private boolean isAbleToJump;
    private boolean isAbleToDoubleJump;
    private boolean isDead;
    
    private boolean isMoveLeft;
    private boolean isMoveRight;
    private boolean isMoveUp;
    private boolean isMoveDown;
    


	public Player(String dirPath, int playerNumber) {
		this.coordinate = new Coordinates(xStartLocation, yStartLocation, 1, 3, 3, 1);
		this.spriteBase = new SpriteBase(dirPath, coordinate);
		this.playerNumber = playerNumber;

		this.spriteBase.setHeight(height);
		this.spriteBase.setWidth(width);
		getImagePaths();
		
		this.isDead = false;
        this.setUp();
		// 사진 목록 받아오기
		

	}
	private void setUp() {
        this.score = 0;
        this.speed = 5;
        this.counter = 31;
        this.isAbleToJump = false;
        this.isAbleToDoubleJump = false;
        this.isJumping = false;
        this.isFacingRight = true;

        Thread moveThread = new moveThread();
        moveThread.start();
    }

	public double getX() {
		return this.spriteBase.getXCoordinate();
		
	}
	public double getY() {
		return this.spriteBase.getYCoordinate();
	}
	
	class moveThread extends Thread {

		@Override
		public void run() {
			while (true) {
				processInput();
				move();
				setDirImage();
				try {		
					Thread.sleep(30);

				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	
	public void processInput() {
		if (isJumping && spriteBase.getDyCoordinate() <= 0) {
			spriteBase.setDyCoordinate(spriteBase.getDyCoordinate() + 0.6);
		} else if (isJumping && spriteBase.getDyCoordinate() > 0) {
			spriteBase.setDyCoordinate(spriteBase.getDyCoordinate() + 0.6);
			setJumping(false);
		} else {
			spriteBase.setDyCoordinate(0);
		}

		moveVertical();
		moveHorizontal();

	}

	 public void setJumping(boolean jumping) {
	        isJumping = jumping;
	    }

	public void setDirImage() {
		if (!isDead) {
			//오른쪽으로 갈 때
			if (isMoveRight) {
				spriteBase.setDirPath("src/image/player1-move-right");
				getImagePaths();
			//왼쪽으로 갈 때
			} else if(isMoveLeft) {
				System.out.println("isMoveLeft");
				spriteBase.setDirPath("src/image/player1-move-left");
				getImagePaths();
			}
		} 
//		else {
//			if (isFacingRight) {
//				spriteBase.setImage("Bub" + playerNumber + "RightDeath.png");
//			} else {
//				spriteBase.setImage("Bub" + playerNumber + "LeftDeath.png");
//			}
//		}
	}

	public void getImagePaths() {
		String dirPath = spriteBase.getDirPath();
		File dir = new File(dirPath);
		
		//이미지 파일이름들 배열에 저장
		this.spriteBase.setImagePaths(dir.list());


	}
	
	public Image getImage() {
		String path = this.spriteBase.getImage();
		Image image = new ImageIcon(path).getImage();
		return image;
	}

	public void move() {
		spriteBase.move();

	}

	public void moveVertical() {
		if (isMoveUp && isAbleToJump) {
			jump();
		}
	}

	private void jump() {
		setAbleToJump(false);
		setJumping(true);
		spriteBase.setDyCoordinate(-30);
	}

	private void setAbleToJump(boolean isAbleToJump) {
		isAbleToJump = isAbleToJump;
		
	}
	/**
	 * This function checks how to move horizontally.
	 */
	private void moveHorizontal() {
		if (isMoveLeft) {
			moveLeft();
		} else if (isMoveRight) {
			moveRight();
		} else {
			spriteBase.setDxCoordinate(0d);
		}
	}

	/**
	 * This function handles moving to the right.
	 */
	private void moveRight() {
		double x = spriteBase.getXCoordinate();
		double y = spriteBase.getYCoordinate();

		spriteBase.setDxCoordinate(speed);
		
		isMoveRight = true;
	}

	/**
	 * This function handles moving to the left.
	 */
	private void moveLeft() {
		double x = spriteBase.getXCoordinate();
		double y = spriteBase.getYCoordinate();

		spriteBase.setDxCoordinate(-speed);
		
		isMoveLeft = true;
	}
	
	public boolean isMoveLeft() {
		return isMoveLeft;
	}
	public void setMoveLeft(boolean isMoveLeft) {
		this.isMoveLeft = isMoveLeft;
	}
	public boolean isMoveRight() {
		return isMoveRight;
	}
	public void setMoveRight(boolean isMoveright) {
		this.isMoveRight = isMoveright;
	}
	public boolean isMoveUp() {
		return isMoveUp;
	}
	public void setMoveUp(boolean isMoveUp) {
		this.isMoveUp = isMoveUp;
	}
	public boolean isMoveDown() {
		return isMoveDown;
	}
	public void setMoveDown(boolean isMoveDown) {
		this.isMoveDown = isMoveDown;
	}

}
