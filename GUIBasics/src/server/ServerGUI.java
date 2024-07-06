package server;

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

import client.NetClient;
import main.MainMenu;

public class ServerGUI extends JFrame{
	
	private static JButton connectionButton = null;
	private static NetServer _netServer = null;
	private static JLabel statusLabel = null;
	
	public ServerGUI() {
		
		this.setLayout(new BorderLayout());
		this.setTitle("Server");
		
		//Upper Panel
		JPanel upperPanel = new JPanel();
		JLabel statusText = new JLabel("Status: ");
		statusLabel = new JLabel("Server OFF");
		upperPanel.setLayout(new GridLayout(1,3));
		connectionButton = new JButton("Start Server");
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
		lowerPanel.setLayout(new GridLayout(1, 2));
		JTextField textField = new JTextField(20);
		JButton sendButton = new JButton("Send");
		lowerPanel.add(textField);
		lowerPanel.add(sendButton);
		this.add(lowerPanel, BorderLayout.SOUTH);
		
		//Create new server class to start the server
		_netServer = new NetServer(textArea, textField, sendButton);
		
		//server start and stop button
		connectionButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(_netServer != null) {
					if(_netServer.GetConnectionStatus()) { //if connected -> disconnect
						connectionButton.setEnabled(false);
						connectionButton.setText("...");
						statusLabel.setText("Stoping...");
						_netServer.StopServer();
						statusLabel.setText("Server OFF");
						connectionButton.setEnabled(true);
						connectionButton.setText("Start");
					}else {									//if disconnect -> connected 
						connectionButton.setEnabled(false);
						statusLabel.setText("Starting...");
						connectionButton.setText("...");
						Thread thread = new Thread(new Runnable() {
							public void run() {	
								_netServer.StartServer();
							}
						});
						thread.start();
						statusLabel.setText("Server ON");
						connectionButton.setEnabled(true);
						connectionButton.setText("Stop");
					}
				}
			}
		});
		
		
	}
}
