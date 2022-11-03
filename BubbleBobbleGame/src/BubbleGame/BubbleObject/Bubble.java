package BubbleGame.BubbleObject;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import BubbleGame.gameObject.Coordinates;
import BubbleGame.gameObject.SpriteBase;

public class Bubble extends JLabel {
   private double xStartLocation; // Player의 X 위치
   private double yStartLocation; // Player의 Y 위치
   private double plusX = 30; // 초기 세팅 X 값
   private double plusY = 0; // 초기 세팅 Y 값
   private double width = 30; // bubble width
   private double height = 30; // bubble height
   private double moveXLimit = 160; // bubble X좌표 최대 움직임
   private int moveDirection; // bubble 움직임 방향 (1: right, -1: left)
   private long beforeTime; // bubble 생성 시간

   private Coordinates coordinate; // 위치 설정 도와주는 class
   private SpriteBase spriteBase; // 이미지 설정을 도와주는 class
   //private BubbleThread bubbleThread; // 버블 움직임을 도와주는 thread

   /* Bubble 생성자 : 기본 설정 및 이미지 설정 --> 스레드 실행 */
   public Bubble(String NomalbubblePath, double xStartLocation, double yStartLocation, int moveDirection) {
      super("Bubble");
      this.moveDirection = moveDirection;
      this.xStartLocation = xStartLocation + plusX * this.moveDirection;
      this.yStartLocation = yStartLocation + plusY;
      this.coordinate = new Coordinates(this.xStartLocation, this.yStartLocation, 1, 3, 3, 1);
      this.spriteBase = new SpriteBase(NomalbubblePath, coordinate);
      
      this.spriteBase.setHeight(height);
      this.spriteBase.setWidth(width);
      this.spriteBase.setOperationTime(10);
      getImagePaths(); // imagePath 알아내기 
      this.setSize((int) width, (int) height);
//      bubbleThread = new BubbleThread(this); // thread 제작 후 실행
//      bubbleThread.start();
      beforeTime = System.currentTimeMillis(); // 코드 실행 전에 시간 받아오기
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

   /* x축(양 옆) 벽과 만났는지 확인(만났으면 true) */
   public boolean checkWallMeetX() {
      if (coordinate.getXCoordinate() <= 0) { // 왼쪽 벽
         this.coordinate.setXCoordinate(0);
         return true;
      } else if (coordinate.getXCoordinate() >= this.getParent().getWidth() - this.width) { // 오른쪽 벽
         this.coordinate.setXCoordinate(this.getParent().getWidth() - this.width);
         return true;
      }
      return false;
   }

   /* 천장 벽과 만났는지 확인(만났으면 true) */
   public boolean checkWallMeetY() {
      if (coordinate.getYCoordinate() <= 0) {
         this.coordinate.setYCoordinate(0);
         return true;
      }
      return false;
   }
   
   public boolean checkMoveLocation() {
      return (!checkWallMeetX() && (moveDirection == 1 && coordinate.getXCoordinate() - xStartLocation < moveXLimit
                  || moveDirection == -1 && xStartLocation - coordinate.getXCoordinate() < moveXLimit));
   }

   /* x 축 움직임 */
   public void moveX() {
      long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
      long secDiffTime = (afterTime - beforeTime) / 1000; // 두 시간에 차 계산
      // System.out.println("시간차이(m) : "+secDiffTime);

      if (secDiffTime < 1) {
         this.coordinate.setXCoordinate(coordinate.getXCoordinate() + 8 * moveDirection);
      }
      if (secDiffTime < 2) {
         this.coordinate.setXCoordinate(coordinate.getXCoordinate() + 6 * moveDirection);
      } else {
         this.coordinate.setXCoordinate(coordinate.getXCoordinate() + 4 * moveDirection);
      }
   }

   /* y 축 움직임 */
   public void moveY() {
      if (!checkWallMeetY()) {
         long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
         long secDiffTime = (afterTime - beforeTime) / 1000; // 두 시간에 차 계산

         this.coordinate.setYCoordinate(coordinate.getYCoordinate() - 3);
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

   int count = 0;
   @Override
   public void paintComponent(Graphics g) {
      if (checkMoveLocation())
         moveX();
      else
         moveY();
      
      // System.out.println("move: "+spriteBase.getXCoordinate()+", "+spriteBase.getYCoordinate());
      
      super.paintComponent(g);
      
      Image bubble = getImage();
      g.drawImage(bubble, 0, 0, this.getWidth(), this.getHeight(), this);
   }
}