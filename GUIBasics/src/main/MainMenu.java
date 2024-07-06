package main;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.ClientGUI;
import server.ServerGUI;

public class MainMenu extends JFrame{
	
	public MainMenu() {
		
		JPanel upperPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel LowerPanel = new JPanel();
		
		this.setLayout(new GridLayout(3,1));
		this.add(upperPanel);
		this.add(centerPanel);
		this.add(LowerPanel);
		JButton serverButton = new JButton("Server");
		centerPanel.setLayout(new GridLayout(1,2));
		centerPanel.add(serverButton);
		serverButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) { 
				createNewWindow(new ServerGUI(), false);
			}
		});
		
		JButton clientButton = new JButton("Client");
		centerPanel.add(clientButton);
		clientButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				createNewWindow(new ClientGUI(), false);				
			}
		});
	}
	
	private void createNewWindow(JFrame newWindow, boolean createNew) {
		if(!createNew)
			this.dispose();
		
		newWindow.setSize(700, 550);
		newWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newWindow.setVisible(true);
	}
}
