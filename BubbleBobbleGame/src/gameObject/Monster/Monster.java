package gameObject.Monster;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gameObject.Coordinates;
import gameObject.SpriteBase;
import utility.Settings;

public class Monster extends JLabel {
	private Coordinates coordinate; // 위치 설정 도와주는 class
	private SpriteBase spriteBase; // 이미지 설정을 도와주는 class
	private int direction;
	
	private String monsterName;
	private String monsterLeftPath;
	private String monsterRightPath;
	
	public Monster(int x, int y, String monsterName, int direction) {
		super();
		
		this.monsterName = monsterName;
		monsterLeftPath = "src/image/"+monsterName+"-move-left";
		monsterRightPath = "src/image/"+monsterName+"-move-right";
		
		this.coordinate = new Coordinates(x, y, 1, 3, 3, 1);
		this.direction = direction;
		if(this.direction == 1)
			this.spriteBase = new SpriteBase(monsterRightPath, coordinate);
		else
			this.spriteBase = new SpriteBase(monsterLeftPath, coordinate);
		
		this.spriteBase.setHeight(Settings.SPRITE_SIZE);
		this.spriteBase.setWidth(Settings.SPRITE_SIZE);
		this.spriteBase.setOperationTime(10);
		getImagePaths(); // imagePath 알아내기
		this.setBounds(x,y,(int) Settings.SPRITE_SIZE, (int) Settings.SPRITE_SIZE);
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
		this.coordinate.setXCoordinate(getX() + (Settings.MONSTER_SPEED*direction));
	}
	
	private boolean checkWall() {
		return (getX()<=0 || getX()+getWidth()>=this.getParent().getWidth());
	}
	
	public void setDirection() {
		if(direction == 1) {
			spriteBase.setDirPath(monsterLeftPath);
			direction = -1;
		}
		else {
			spriteBase.setDirPath(monsterRightPath);
			direction = 1;
		}
	}
	
	@Override
	public int getX() {
		return (int) this.spriteBase.getXCoordinate();
	}

	@Override
	public int  getY() {
		return (int) this.spriteBase.getYCoordinate();
	}

	@Override
	public void paintComponent(Graphics g) {
		moveMonster();
		super.paintComponent(g);

		Image monster = getImage();
		g.drawImage(monster, 0, 0, this.getWidth(), this.getHeight(), this);

	}
}
