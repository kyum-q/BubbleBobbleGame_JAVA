package WaitingRoom;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.Settings;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class WaitingRoom extends JFrame {
	WaitingPanel serverPanel = null;
	public WaitingRoom() {
		setTitle("bubble-server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((int)Settings.SCENE_WIDTH, (int)Settings.SCENE_HEIGHT);

		serverPanel = new WaitingPanel();
		
		this.setContentPane(serverPanel);
		
		
		setResizable(false); 
		setVisible(true);
	}
}
