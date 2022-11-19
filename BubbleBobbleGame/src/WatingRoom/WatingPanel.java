package WatingRoom;

import java.awt.Color;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import utility.Settings;

public class WatingPanel extends JLayeredPane {
	private String userName;
	private Image image;
	
	private JLabel nameLabel;
	
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	
	private Socket socket; // 연결소켓
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public WatingPanel(String userName, String ip_addr, String port_no) {
		setLayout(null);
		this.userName = userName;
		
		Font font = new Font ("HBIOS-SYS", Font.PLAIN, 90);
		
		JLabel joinPlayer = new JLabel("1 / 2");
		joinPlayer.setBounds((int)Settings.SCENE_WIDTH/2-(300/2), 200, 300, 100);
		joinPlayer.setFont(font);
		joinPlayer.setForeground(new Color(255, 186, 0));
		joinPlayer.setHorizontalAlignment(JTextField.CENTER);
		add(joinPlayer);
	
		font = new Font ("HBIOS-SYS", Font.PLAIN, 30);
		nameLabel = new JLabel(userName);
		nameLabel.setBounds((int)Settings.SCENE_WIDTH/2-(200/2), 420, 200, 40);
		nameLabel.setFont(font);
		nameLabel.setForeground(new Color(255, 186, 0));
		nameLabel.setHorizontalAlignment(JTextField.CENTER);
		add(nameLabel);
		
		image = Toolkit.getDefaultToolkit().createImage("src/image/player1-waiting.gif"); 
		
		// 배경 색 설정
		setOpaque(true);
		this.setBackground(Color.BLACK);
		
		connectSet(ip_addr, port_no);
	}
	
	private void connectSet(String ip_addr, String port_no) {
		AppendText("User " + userName + " connecting " + ip_addr + " " + port_no);
		
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
//			is = socket.getInputStream();
//			dis = new DataInputStream(is);
//			os = socket.getOutputStream();
//			dos = new DataOutputStream(os);

			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			//SendMessage("/login " + userName);
			ChatMsg obcm = new ChatMsg(userName, "100", "Hello");
			SendObject(obcm);
			
			ListenNetwork net = new ListenNetwork();
			net.start();
			//TextSendAction action = new TextSendAction();
			//btnSend.addActionListener(action);
			//txtInput.addActionListener(action);
			//txtInput.requestFocus();
			//ImageSendAction action2 = new ImageSendAction();
			//imgBtn.addActionListener(action2);

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppendText("connect error");
		}
	}
	

	// Server Message를 수신해서 화면에 표시
	class ListenNetwork extends Thread {
		public void run() {
			while (true) {
				try {
					// String msg = dis.readUTF();
//					byte[] b = new byte[BUF_LEN];
//					int ret;
//					ret = dis.read(b);
//					if (ret < 0) {
//						AppendText("dis.read() < 0 error");
//						try {
//							dos.close();
//							dis.close();
//							socket.close();
//							break;
//						} catch (Exception ee) {
//							break;
//						}// catch문 끝
//					}
//					String	msg = new String(b, "euc-kr");
//					msg = msg.trim(); // 앞뒤 blank NULL, \n 모두 제거

					Object obcm = null;
					String msg = null;
					ChatMsg cm;
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
					if (obcm == null)
						break;
					if (obcm instanceof ChatMsg) {
						cm = (ChatMsg) obcm;
						msg = String.format("[%s] %s", cm.getId(), cm.getData());
					} else
						continue;
					switch (cm.getCode()) {
					case "200": // chat message
						AppendText(msg);
						break;
					case "300": // Image 첨부
						AppendText("[" + cm.getId() + "]");
						AppendImage(cm.img);
						break;
					}
				} catch (IOException e) {
					AppendText("ois.readObject() error");
					try {
//						dos.close();
//						dis.close();
						ois.close();
						oos.close();
						socket.close();

						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝

			}
		}
	}

	public void AppendIcon(ImageIcon icon) {
//		int len = textArea.getDocument().getLength();
//		// 끝으로 이동
//		textArea.setCaretPosition(len);
//		textArea.insertIcon(icon);
	}

	// 화면에 출력
	public void AppendText(String msg) {
		// textArea.append(msg + "\n");
		//AppendIcon(icon1);
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
//		int len = textArea.getDocument().getLength();
//		// 끝으로 이동
//		textArea.setCaretPosition(len);
//		textArea.replaceSelection(msg + "\n");
	}

	public void AppendImage(ImageIcon ori_icon) {
//		int len = textArea.getDocument().getLength();
//		textArea.setCaretPosition(len); // place caret at the end (with no selection)
		Image ori_img = ori_icon.getImage();
		int width, height;
		double ratio;
		width = ori_icon.getIconWidth();
		height = ori_icon.getIconHeight();
		// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
		if (width > 200 || height > 200) {
			if (width > height) { // 가로 사진
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // 세로 사진
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(new_img);
//			textArea.insertIcon(new_icon);
		} //else
//			textArea.insertIcon(ori_icon);
//		len = textArea.getDocument().getLength();
//		textArea.setCaretPosition(len);
//		textArea.replaceSelection("\n");
	}

	// Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
	public byte[] MakePacket(String msg) {
		byte[] packet = new byte[BUF_LEN];
		byte[] bb = null;
		int i;
		for (i = 0; i < BUF_LEN; i++)
			packet[i] = 0;
		try {
			bb = msg.getBytes("euc-kr");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		for (i = 0; i < bb.length; i++)
			packet[i] = bb[i];
		return packet;
	}

	// Server에게 network으로 전송
	public void SendMessage(String msg) {
		try {
			// dos.writeUTF(msg);
//			byte[] bb;
//			bb = MakePacket(msg);
//			dos.write(bb, 0, bb.length);
			ChatMsg obcm = new ChatMsg(userName, "200", msg);
			oos.writeObject(obcm);
		} catch (IOException e) {
			// AppendText("dos.write() error");
			AppendText("oos.writeObject() error");
			try {
//				dos.close();
//				dis.close();
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(0);
			}
		}
	}

	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			// textArea.append("메세지 송신 에러!!\n");
			AppendText("SendObject Error");
		}
	}
	
	public void paintComponent(Graphics g) {  
	    super.paintComponent(g);  
	    if (image != null) {  
	      g.drawImage(image, (int)Settings.SCENE_WIDTH/2-(50/2), 360, 50, 50, this);
	    }  
	  } 
	

}
