package BubbleGame.gameObject;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import BubbleGame.ScorePanel.ScoreLabel;
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
	private Block crushBlock;

	private double playerMinX;
	private double playerMaxX;
	private double playerMinY;
	private double playerMaxY;
	    
	private boolean isJumping;
    private int counter;
    private boolean isDelayed;
    private boolean isAbleToJump;
    private boolean isDead;
    
    private boolean isMoveLeft;
    private boolean isMoveRight;
    private boolean isMoveUp;
    private boolean isShoot;
   
	private boolean isMoveDown;
    
	private boolean isDirection;
    private boolean isWallCrush;
    private boolean isMonsterCrush;
    
    private ScoreLabel scoreLabel;
    
	public Player(int playerNumber, ScoreLabel scoreLabel) {
		super();
		this.xStartLocation = Settings.SPRITE_SIZE;
        this.yStartLocation = 460;
        this.scoreLabel = scoreLabel;
        
		this.coordinate = new Coordinates(xStartLocation, yStartLocation, 1, 3, 3, 1);
		String path;
		path = "src/image/player"+playerNumber+"-move-left";
		System.out.println("path: "+path);
		this.spriteBase = new SpriteBase(path, coordinate);
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
        this.isDirection = (playerNumber==1);
        this.isWallCrush = false;
        this.isShoot = false;
        this.isMonsterCrush = false;

        //playerMinX = Settings.SPRITE_SIZE / 2;
        playerMinX = 27;
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
				//wallCrush();
				
				try {		
					Thread.sleep(20);

				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	
//	public void wallCrush() {
//		if(isWallCrush) {
//			//떨어지고 있거나, 블록위에 서 있는 상태
//			if( spriteBase.getDyCoordinate()== 0 ) {
//				spriteBase.setDyCoordinate(0);			
//				setJumping(false);
//			}
//		}
//	}

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
		moveVertical();
		moveHorizontal();
		checkBounds() ;
	}

	//벽 위에 있는지 체크
	 public boolean wallCollision(double minX, double maxX, double minY, double  maxY) {
		 	double minX2 = this.getX();
	        double maxX2 = minX2 + getWidth();
	        double minY2 = this.getY()+getHeight() +3;
	        double maxY2 = minY2;
	        return ((minX > minX2 && minX < maxX2)
	                || (maxX > minX2 && maxX < maxX2)
	                || (minX2 > minX && minX2 < maxX)
	                || (maxX2 > minX && maxX2 < maxX))
	                && ((minY > minY2 && minY < maxY2)
	                || (maxY > minY2 && maxY < maxY2)
	                || (minY2 > minY && minY2 < maxY)
	                || (maxY2 > minY && maxY2 < maxY));
		// return spriteBase.causesCollision(minX, maxX, minY, maxY);
	}
	 
	 //몬스터와 부딪혔는지 체크
	 public boolean monsterCollision(double minX, double maxX, double minY, double  maxY) {
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
    
    //양 끝 벽쪽 체크
    public void checkBounds() {
        double x = spriteBase.getXCoordinate();
        double y = spriteBase.getYCoordinate();
        if (x < playerMinX) {
            spriteBase.setXCoordinate(playerMinX);
        } else if (x + width > playerMaxX -Settings.BLOCK_WIDTH ) {
            spriteBase.setXCoordinate(playerMaxX -Settings.BLOCK_WIDTH- width);
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
        }
        else if (y + height > playerMaxY) {
            spriteBase.setYCoordinate(playerMinY);
        }
    }
    
    //상태에 따라 이미지 폴더 변경 
	public void setDirImage() {
		if (!isDead) {
			//버블 발사
			 if(isShoot) {
				if(this.isDirection)
					spriteBase.setDirPath("src/image/player"+playerNumber+"-shoot-right");
				else
					spriteBase.setDirPath("src/image/player"+playerNumber+"-shoot-left");
			}
			 else if(this.isMonsterCrush) {
				 if(this.isDirection)
						spriteBase.setDirPath("src/image/player"+playerNumber+"-death-right");
					else
						spriteBase.setDirPath("src/image/player"+playerNumber+"-death-left");
			 }
			//오른쪽으로 갈 때
			 else if (isMoveRight) {
				//이미지 디렉토리 변경
				spriteBase.setDirPath("src/image/player"+playerNumber+"-move-right");
				//getImagePaths();
			//왼쪽으로 갈 때
			} else if(isMoveLeft) {

				spriteBase.setDirPath("src/image/player"+playerNumber+"-move-left");
				//getImagePaths();
			//가만히 있을 때	
			} else {
				if(this.isDirection && !(spriteBase.getDirPath().equals("src/image/player"+playerNumber+"-move-right")))
					spriteBase.setDirPath("src/image/player"+playerNumber+"-move-right");
				else if(!(this.isDirection) &&!(spriteBase.getDirPath().equals("src/image/player"+playerNumber+"-move-left")))
					spriteBase.setDirPath("src/image/player"+playerNumber+"-move-left");
			}
		} 
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
	
	public void setX(double x) {
		this.spriteBase.setXCoordinate(x);
	}

	public void setY(double y) {
		this.spriteBase.setYCoordinate(y);
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
	public void addScore(int i) {
		scoreLabel.addScore(i);
	}

	public boolean isShoot() {
		return isShoot;
	}
	public void setShoot(boolean isShoot) {
		this.isShoot = isShoot;
	}
	
	public boolean isMonsterCrush() {
		return isMonsterCrush;
	}
	public void setMonsterCrush(boolean isMonsterCrush) {
		this.isMonsterCrush = isMonsterCrush;
	}
	public void setCrushBlock(Block crushBlock) {
		this.crushBlock = crushBlock;
	}
}

