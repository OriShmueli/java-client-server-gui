package main;

import javax.swing.JFrame;

public class Main extends JFrame{
	public static void main(String[] args) {
		
		MainMenu mainMenu = new MainMenu();
		mainMenu.setSize(700, 550);
		mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenu.setVisible(true);
	}
}
