package BubbleGame;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {
	public Clip backgroundMusic, menuMusic, gameMusic2;
	public Clip effect1, effect2, effect3;
	
	public Music() {
		
		load();
	}
	
	public void load() {
		try {
			backgroundMusic = AudioSystem.getClip();
			File audioFile = new File("src/resource/music/main.wav");
			AudioInputStream audioStream1 = AudioSystem.getAudioInputStream(audioFile);
			backgroundMusic.open(audioStream1);
			
			menuMusic = AudioSystem.getClip();
			audioFile = new File("src/resource/music/menu.wav");
			AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(audioFile);
			menuMusic.open(audioStream2);
//			
//			gameMusic2 = AudioSystem.getClip();
//			audioFile = new File("src/music/game2.wav");
//			AudioInputStream audioStream3 = AudioSystem.getAudioInputStream(audioFile);
//			gameMusic2.open(audioStream3);
//			
//			effect1 = AudioSystem.getClip();
//			audioFile = new File("src/music/3.wav");
//			AudioInputStream audioStream4 = AudioSystem.getAudioInputStream(audioFile);
//			effect1.open(audioStream4);
//			
//			effect2 = AudioSystem.getClip();
//			audioFile = new File("src/music/2.wav");
//			AudioInputStream audioStream5 = AudioSystem.getAudioInputStream(audioFile);
//			effect2.open(audioStream5);
//			
//			effect3 = AudioSystem.getClip();
//			audioFile = new File("src/music/1.wav");
//			AudioInputStream audioStream6 = AudioSystem.getAudioInputStream(audioFile);
//			effect3.open(audioStream6);
		}
		catch(LineUnavailableException e) {e.printStackTrace();} 
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paly(String list) {
		
		switch(list) {
		case "background":
			backgroundMusic.setFramePosition(0);
			backgroundMusic.start();
			break;
		case "menu":
			menuMusic.setFramePosition(0);
			menuMusic.start();
			break;
//		case "game2":
//			gameMusic2.setFramePosition(0);
//			gameMusic2.start();
//			break;
//		case "effect1":
//			effect1.setFramePosition(0);
//			effect1.start();
//			break;
//		case "effect2":
//			effect2.setFramePosition(0);
//			effect2.start();
//			break;
//		case "effect3":
//			effect3.setFramePosition(0);
//			effect3.start();
//			break;
		}
	}
	
	public void allStop() {
		backgroundMusic.stop();
		menuMusic.stop();
//		gameMusic2.stop();
	}
	
	public void stop(String list) {
		
		switch(list) {
		case "background":
			backgroundMusic.stop();
			break;
		case "menu":
			menuMusic.stop();
//		case "game2":
//			gameMusic2.stop();
		
		}
	}
	
}
