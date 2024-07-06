package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.MainMenu;

public class ClientGUI extends JFrame{
	
	private static JButton connectionButton = null;
	private static NetClient _netClient = null;
	private static JLabel statusLabel = null;
	
	private void returnBack() {
		MainMenu mainMenu = new MainMenu();
		this.dispose();
		mainMenu.setSize(700, 550);
		mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenu.setVisible(true);
		_netClient.Disconnect();
	}
	
	public ClientGUI() {
		
		this.setLayout(new BorderLayout());
		this.setTitle("Client");
		
		//Upper Panel
		JPanel upperPanel = new JPanel();
		JLabel statusText = new JLabel("Status: ");
		statusLabel = new JLabel("...");
		upperPanel.setLayout(new GridLayout(1,3));
		connectionButton = new JButton("Connect");
		upperPanel.add(connectionButton);
		upperPanel.add(statusText);
		upperPanel.add(statusLabel);
		this.add(upperPanel, BorderLayout.NORTH);
		
		//Center Panel
		JPanel centerPanel = new JPanel();
		JTextArea textArea = new JTextArea(28, 43);
		textArea.setEditable(false);
		JScrollPane scrollArea = new JScrollPane(textArea);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		centerPanel.add(scrollArea);
		this.add(centerPanel, BorderLayout.CENTER);
		
		//Lower Panel
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new GridLayout(1, 3));
		JTextField textField = new JTextField(20);
		JButton sendButton = new JButton("Send");
		JButton backButton = new JButton("Back");
		
		backButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				returnBack();
			}
		});
		
		lowerPanel.add(textField);
		lowerPanel.add(sendButton);
		lowerPanel.add(backButton);
		this.add(lowerPanel, BorderLayout.SOUTH);
		
		//Create new client class to connect to the server
		_netClient = new NetClient(textArea, textField, sendButton);
		
		//request connection and disconnection button
		connectionButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(_netClient != null) {
					if(_netClient.GetConnectionStatus()) { //if connected -> disconnect
						connectionButton.setEnabled(false);
						connectionButton.setText("...");
						statusLabel.setText("Disconnecting...");
						_netClient.Disconnect();
						statusLabel.setText("Disconnected");
						connectionButton.setEnabled(true);
						connectionButton.setText("Connect");
					}else {									//if disconnect -> connected 
						connectionButton.setEnabled(false);
						statusLabel.setText("Connecting...");
						connectionButton.setText("...");
						Thread thread = new Thread(new Runnable() {
							
							public void run() {
								
								_netClient.Connect();
							}
						});
						thread.start();
						statusLabel.setText("Connected");
						connectionButton.setEnabled(true);
						connectionButton.setText("Disconnect");
					}
				}
			}
		});
	}
	
	
}
