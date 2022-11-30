package BubbleGame.gameObject.bubble;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import BubbleGame.gameObject.Coordinates;
import BubbleGame.gameObject.SpriteBase;
import utility.Settings;

public class Item extends JLabel {
	private Coordinates coordinate; // 위치 설정 도와주는 class
	private SpriteBase spriteBase; // 이미지 설정을 도와주는 class
	
	private String itemPath ="src/image/fruit";
	private boolean isWallCrush = false;
	private Image itemImg;
	private int itemScore = Settings.ITEM_MIN_SCORE;

	public Item(int x, int y, int itemNum, int itemScore) {
		super();
		this.coordinate = new Coordinates(x, y, 1, 0, 0, 1);
		this.spriteBase = new SpriteBase(itemPath, coordinate);

		this.spriteBase.setHeight(Settings.ITEM_SIZE);
		this.spriteBase.setWidth(Settings.ITEM_SIZE);
		getImagePaths(itemNum); // imagePath 알아내기
		this.itemScore = itemScore;
		this.setBounds(x, y, (int) Settings.ITEM_SIZE, (int) Settings.ITEM_SIZE);
	}

	  /* 이미지 Path 설정 */
	   private void getImagePaths(int itemNum) {
	      String dirPath = spriteBase.getDirPath();
	      File dir = new File(dirPath);
	      
	      this.spriteBase.setImagePaths(dir.list());
	      
	      String path = spriteBase.getImage(itemNum);
	      itemImg = new ImageIcon(path).getImage();
	   }
	   

	/* img 알아내기(전달) */
	public Image getImage() {
		String path = this.spriteBase.getImage();
		Image image = new ImageIcon(path).getImage();
		return image;
	}

	public boolean isWallCrush() {
		return isWallCrush;
	}
	
	public void setWallCrush(boolean isWallCrush) {
		this.isWallCrush = isWallCrush;
	}

	public void setWallCrush(boolean isWallCrush,int x, int y) {
		this.isWallCrush = isWallCrush;
		
		coordinate.setXCoordinate(x);
		coordinate.setYCoordinate(y);
	}
	

	public boolean wallCollision(double minX, double maxX, double minY, double maxY) {
		return spriteBase.causesCollision(minX, maxX, minY, maxY);
	}

	public void moveItem() {
		// 벽에 부딪히게 아니고
		if (!isWallCrush) {
			// 떨어지는 중
			spriteBase.setDyCoordinate(Settings.ITEM_DOWN_SPEED);
			spriteBase.setDxCoordinate(0);
			spriteBase.move();
		}
	}
	
	public int getScore() {
		return itemScore;
	}
	
	@Override
	public int getX() {
		return (int) this.spriteBase.getXCoordinate();
	}

	@Override
	public int getY() {
		return (int) this.spriteBase.getYCoordinate();
	}

	@Override
	public void paintComponent(Graphics g) {
		moveItem();
		super.paintComponent(g);

		g.drawImage(itemImg, 0, 0, this.getWidth(), this.getHeight(), this);

	}
}
