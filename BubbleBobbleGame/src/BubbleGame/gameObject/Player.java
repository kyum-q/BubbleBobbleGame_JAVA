package BubbleGame.gameObject;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import utility.Settings;

public class Player extends JLabel{
	private final int playerNumber;
	private double xStartLocation = 10;
	private double yStartLocation = 500;
	private double speed;
	private int score;
	private int lives;
	private SpriteBase spriteBase;
	private double width = Settings.SPRITE_SIZE;
	private double height = Settings.SPRITE_SIZE;

	private Coordinates coordinate;

	private double playerMinX;
	private double playerMaxX;
	private double playerMinY;
	private double playerMaxY;
	    
	private boolean isJumping;
    private boolean isFacingRight;
    private int counter;
    private boolean isDelayed;
    private boolean isAbleToJump;
    private boolean isDead;
    
    private boolean isMoveLeft;
    private boolean isMoveRight;
    private boolean isMoveUp;
   
	private boolean isMoveDown;
    private boolean isDirection;
    private boolean isWallCrush;
    
	public Player(String dirPath, int playerNumber) {
		super();
		this.xStartLocation = Settings.SPRITE_SIZE;
        this.yStartLocation = 600;
        
		this.coordinate = new Coordinates(xStartLocation, yStartLocation, 1, 3, 3, 1);
		this.spriteBase = new SpriteBase(dirPath, coordinate);
		this.playerNumber = playerNumber;

		
		this.spriteBase.setHeight(height);
		this.spriteBase.setWidth(width);
		//getImagePaths();
		
		this.isDead = false;
        this.setUp();
        
        this.setBounds(0,0,(int)width, (int)height);

	}
	//변수들 초기 셋팅
	private void setUp() {
        this.score = 0;
        this.speed = Settings.PLAYER_SPEED;
        this.counter = 31;
        this.isAbleToJump = true;
        this.isJumping = false;
        this.isFacingRight = true;
        this.isDirection = true;
        this.isWallCrush = false;

        playerMinX = Settings.SPRITE_SIZE / 2;
        playerMaxX = Settings.SCENE_WIDTH - Settings.SPRITE_SIZE / 2;
        playerMinY = Settings.SPRITE_SIZE / 2;
        playerMaxY = Settings.SCENE_HEIGHT - Settings.SPRITE_SIZE / 2;

        Thread moveThread = new moveThread();
        moveThread.start();
    }
	
	@Override
	public void paintComponent(Graphics g) { 
		
		Image player1Image = this.getImage();
		g.drawImage(player1Image, 0,0,this.getWidth(), this.getHeight(),this);
				
	}
	
	
	
	//상태변수, 키보드 입력에 따른 좌표 변화 실행
	class moveThread extends Thread {

		@Override
		public void run() {
			while (true) {
				setDirImage();
				processInput();
				move();
				wallCrush();
				
				try {		
					Thread.sleep(20);

				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	
	public void wallCrush() {
		if(isWallCrush) {
			if( spriteBase.getDyCoordinate()== 0 ) {
				spriteBase.setDyCoordinate(0);
				setJumping(false);
			}
		}
	}
	public void processInput() {
		//DY가 음수이면 올라가고 있다는 거
		if (isJumping && spriteBase.getDyCoordinate() <0) {
			spriteBase.setDyCoordinate(spriteBase.getDyCoordinate() + 0.6);
		
		// 양수는 떨어지는 중
		} else if (isJumping && spriteBase.getDyCoordinate() > 0) {
			spriteBase.setDyCoordinate(spriteBase.getDyCoordinate() + 0.6);

			setJumping(false);
		} else {
			spriteBase.setDyCoordinate(0);
		}

		//System.out.println("DY : " + this.spriteBase.getDyCoordinate());
		moveVertical();
		moveHorizontal();
		
		//applyGravity();
		checkBounds() ;
	}

	//minX 들은 자기가 아닌 다른 객체 좌표
	 public boolean wallCollision(double minX, double maxX, double minY,
             double maxY) {
		 
		 return spriteBase.causesCollision(minX, maxX, minY, maxY);
	}
	 public float calculateGravity() {
	        return -Settings.GRAVITY_CONSTANT;
	    }
    /**
     * This function applies gravity.
     */
    public void applyGravity() {
        double x = spriteBase.getXCoordinate();
        double y = spriteBase.getYCoordinate();
        
        //벽에 부딪히게 아니고
        if(!isWallCrush) {
        	//떨어지는 중
            if (!isJumping) {        	
            	//중력 작용
            	spriteBase.setDyCoordinate(-calculateGravity());
            //올라가는 중
            } else {
            	//재점프 불가
                setAbleToJump(false);
            }
          //벽에 부딪히고 올라가는 중이 아니면 
        }else {
        	if (!isJumping) {
              setAbleToJump(true);
         }
        }
      
//        if (!wallCollision(x, x + width,
//                y - calculateGravity(), y + height - calculateGravity()
//           )
//                || wallCollision(x, x + width,
//                y, y + height)) {
//            if (!isJumping) {
//                spriteBase.setDyCoordinate(-calculateGravity());
//            } else {
//                setAbleToJump(false);
//            }
//        } else {
//            if (!isJumping) {
//                setAbleToJump(true);
//            }
//        }
    }
    
    /**
     * This function returns the player if it is out of bounds.
     */
    public void checkBounds() {
        double x = spriteBase.getXCoordinate();
        double y = spriteBase.getYCoordinate();
        if (x < playerMinX) {
            spriteBase.setXCoordinate(playerMinX);
        } else if (x + width > playerMaxX) {
            spriteBase.setXCoordinate(playerMaxX - width);
        }

        if (y < playerMinY) {
        	 spriteBase.setYCoordinate(playerMinY);
        	 if(!isWallCrush)
        		 spriteBase.setYCoordinate(playerMaxY - height);
        	 else
        		 spriteBase.setYCoordinate(playerMinY);
//            if (!wallCollision(x,
//                    x + width,
//                    y,
//                    y + height, levelController)) {
//                spriteBase.setYCoordinate(playerMaxY - height);
//            } else {
//                spriteBase.setYCoordinate(playerMinY);
//            }
        } else if (y + height > playerMaxY) {
            spriteBase.setYCoordinate(playerMinY);
        }
    }
	public void setDirImage() {
		if (!isDead) {
			//오른쪽으로 갈 때
			if (isMoveRight) {
				//이미지 디렉토리 변경
				spriteBase.setDirPath("src/image/player1-move-right");
				//getImagePaths();
			//왼쪽으로 갈 때
			} else if(isMoveLeft) {

				spriteBase.setDirPath("src/image/player1-move-left");
				//getImagePaths();
			}
		} 
		String dirPath = spriteBase.getDirPath();
		File dir = new File(dirPath);
		
		//이미지 파일이름들 배열에 저장
		this.spriteBase.setImagePaths(dir.list());
	}
//

	

	public Image getImage() {
		String path = this.spriteBase.getImage();
		Image image = new ImageIcon(path).getImage();
		return image;
	}

	public void move() {
		applyGravity();
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
		//점프 하는 속도
		spriteBase.setDyCoordinate(-Settings.JUMP_SPEED);
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

		spriteBase.setDxCoordinate(speed);
		
		isMoveRight = true;
		isDirection = true;
	}


	private void moveLeft() {

		spriteBase.setDxCoordinate(-speed);
		
		isMoveLeft = true;
		isDirection = false;
	}
	
	//Player의 현재 X좌표 리턴
	public int getX() {
		return (int)this.spriteBase.getXCoordinate();
		
	}
	
	//Player의 현재 Y좌표 리턴
	public int getY() {
		return (int)this.spriteBase.getYCoordinate();
	}
	public SpriteBase getSpriteBase() {
		return spriteBase;
	}
	public void setSpriteBase(SpriteBase spriteBase) {
		
		this.spriteBase = spriteBase;
	}
	public boolean isAbleToJump() {
		return isAbleToJump;
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
	public void setJumping(boolean jumping) {
        isJumping = jumping;
    }
	public boolean getAbleToJump() {
		return isAbleToJump;
	}
	public void setAbleToJump(boolean ableToJump) {
		isAbleToJump = ableToJump;
	}
	public boolean isDirection() {
		return isDirection;
	}
	public void setDirection(boolean isDirection) {
		this.isDirection = isDirection;
		
	} public boolean isWallCrush() {
		return isWallCrush;
	}
	public void setWallCrush(boolean isWallCrush) {
		this.isWallCrush = isWallCrush;
	}
	public boolean isJumping() {
		return isJumping;
	}
}

