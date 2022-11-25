package BubbleGame.gameObject.monster;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import BubbleGame.gameObject.Coordinates;
import BubbleGame.gameObject.SpriteBase;
import utility.Settings;

public class Monster extends JLabel {
	private Coordinates coordinate; // 위치 설정 도와주는 class
	private SpriteBase spriteBase; // 이미지 설정을 도와주는 class
	private int direction;

	private String monsterName;
	private String monsterLeftPath;
	private String monsterRightPath;
	private boolean isWallCrush = false;

	public Monster(int x, int y, String monsterName, int direction) {
		super();

		this.monsterName = monsterName;
		monsterLeftPath = "src/image/" + monsterName + "-move-left";
		monsterRightPath = "src/image/" + monsterName + "-move-right";

		this.coordinate = new Coordinates(x, y, 1, Settings.MONSTER_SPEED, Settings.MONSTER_SPEED, 1);
		this.direction = direction;
		if (this.direction == 1)
			this.spriteBase = new SpriteBase(monsterRightPath, coordinate);
		else
			this.spriteBase = new SpriteBase(monsterLeftPath, coordinate);

		this.spriteBase.setHeight(Settings.SPRITE_SIZE);
		this.spriteBase.setWidth(Settings.SPRITE_SIZE);
		this.spriteBase.setOperationTime(10);
		getImagePaths(); // imagePath 알아내기
		this.setBounds(x, y, (int) Settings.SPRITE_SIZE, (int) Settings.SPRITE_SIZE);
	}

	/* 이미지 Path 설정 */
	private void getImagePaths() {
		String dirPath = spriteBase.getDirPath();
		File dir = new File(dirPath);

		this.spriteBase.setImagePaths(dir.list());
	}

	/* img 알아내기(전달) */
	public Image getImage() {
		String path = this.spriteBase.getImage();
		Image image = new ImageIcon(path).getImage();
		return image;
	}

	public String getMonsterName() {
		return monsterName;
	}

	private void moveMonster() {
		if (checkWall()) {
			setDirection();
		}
		applyGravity();
		//this.coordinate.setXCoordinate(getX() + (coordinate.getDXCoordinate() * direction));
	}
	
	
	public void setX(double x) {
		this.spriteBase.setXCoordinate(x);
	}

	public void setY(double y) {
		this.spriteBase.setYCoordinate(y);
	}
	
	private boolean checkWall() {
		return (getX() <= 0 + Settings.BLOCK_WIDTH  || getX() + getWidth() >= this.getParent().getWidth()-Settings.BLOCK_WIDTH);
	}

	public void setDirection() {
		if (direction == 1) {
			spriteBase.setDirPath(monsterLeftPath);
			direction = -1;
		} else {
			spriteBase.setDirPath(monsterRightPath);
			direction = 1;
		}
	}

	public boolean isWallCrush() {
		return isWallCrush;
	}

	public void setWallCrush(boolean isWallCrush) {
		this.isWallCrush = isWallCrush;
	}

	public boolean wallCollision(double minX, double maxX, double minY, double maxY) {
		return spriteBase.causesCollision(minX, maxX, minY, maxY);
	}

	public void applyGravity() {
		// 벽에 부딪히게 아니고
		if (!isWallCrush) {
			// 떨어지는 중
			spriteBase.setDyCoordinate(Settings.GRAVITY_CONSTANT);
			spriteBase.setDxCoordinate(0);
			spriteBase.move();
		}
		else {
			spriteBase.setDxCoordinate(Settings.MONSTER_SPEED * direction);
			spriteBase.setDyCoordinate(0);
			spriteBase.move();
		}
	}

	@Override
	public int getX() {
		return (int) this.spriteBase.getXCoordinate();
	}

	@Override
	public int getY() {
		return (int) this.spriteBase.getYCoordinate();
	}
	
	public void setX(int x) {
		this.spriteBase.setXCoordinate(x);
	}

	public void setY(int y) {
		this.spriteBase.setYCoordinate(y);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		moveMonster();
		super.paintComponent(g);

		Image monster = getImage();
		g.drawImage(monster, 0, 0, this.getWidth(), this.getHeight(), this);

	}
}
