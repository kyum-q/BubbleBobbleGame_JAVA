package BubbleGame;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import BubbleGame.gameObject.Block;
import utility.Settings;



public class Map {

	final static public int BLOCK_WIDTH_LENGTH = 26; //한 줄에 26개
	
	public ArrayList<Block> blocks =new ArrayList<>();
	public String path;
	ArrayList<String> packet = new ArrayList<>();
	//public String[] map_arr = new String[packet.size()];
	
	
	public Map(String path) {
		String[] map_arr  = setMapArr(path);
		setBlockObjects(map_arr);
		
	}

	public void setBlocks(ArrayList<Block> blocks) {
		this.blocks = blocks;
	}

	//map.txt 파일을 읽어 내용을 담은 문자열 배열 생성
	//map_arr[0] = "1 1 1 1 1 1 ... 1 1"
	public String[] setMapArr(String path) {
		String[] map_arr = new String[packet.size()];
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(path)));
			String s;			
			while (( s = br.readLine()) != null) {
				String sc[] = s.split(" ");
				for(int i=0; i< sc.length;i++)
					packet.add(sc[i]);
			}
			map_arr = packet.toArray(map_arr);
			
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if( br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
		}
		return map_arr;	
	}
	
	//배열 내용으로 Block 객체 생성
	public void setBlockObjects(String [] map_arr) {
		
		for(int i=0; i< map_arr.length; i++) {
			int state = Integer.parseInt(map_arr[i]);
			if(state>0) {
				int x = (i%BLOCK_WIDTH_LENGTH)* Settings.BLOCK_WIDTH;
				int y = (i/BLOCK_WIDTH_LENGTH)* Settings.BLOCK_Height;
				Block block = new Block(x,y);
				blocks.add(block);
			}
		}
	}
	
	public ArrayList getBlocks() {
		return blocks;
	}
	
}
