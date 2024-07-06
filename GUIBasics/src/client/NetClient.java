package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NetClient {
	
	private Socket socket = null;
	private InputStreamReader inputStreamReader = null;
	private OutputStreamWriter outputStreamWriter = null;
	private BufferedReader bufferedReader = null;
	private BufferedWriter bufferedWriter = null;
	private boolean connectionStatus = false;
	private JTextArea textArea = null;
	private JTextField textField = null;
	private JButton sendButton = null;
	private StringBuilder text= new StringBuilder();
	
	public boolean GetConnectionStatus() {
		return connectionStatus;
	}

	public JTextField GetTextField() {
		return textField;
	}
	
	public NetClient(JTextArea textArea, JTextField textField, JButton sendButton) {
		this.textArea = textArea;
		this.textField = textField;
		this.sendButton = sendButton;
		connectionStatus = false;
		_initializeComponents();
	}
	
	private void _initializeComponents() {
		sendButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(!GetTextField().getText().isEmpty()) {
					text.append("Client: " + GetTextField().getText());
					text.append("\n");
					sendMessageToServer(GetTextField().getText());					
					textArea.setText(text.toString());
				}
			}
		});
		
		sendButton.setEnabled(false);
		textField.setEnabled(false);
	}
	
	public void Disconnect() {
		connectionStatus = false;
		sendButton.setEnabled(false);
		textField.setEnabled(false);
	}
	
	private void sendMessageToServer(String message) {
		try {
			bufferedWriter.write(message);
			bufferedWriter.newLine(); 
			bufferedWriter.flush();
		}catch (Exception e) {
			System.out.println("Error on message sending button:");
			e.printStackTrace();
		}
		
		textField.setText("");
	}
	
	public void Connect(){
		try {
			connectionStatus = true;
			socket = new Socket("localhost", 1234); 
			inputStreamReader = new InputStreamReader(socket.getInputStream());
			outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
			
			bufferedReader = new BufferedReader(inputStreamReader);
			bufferedWriter = new BufferedWriter(outputStreamWriter);
			
			sendButton.setEnabled(true);
			textField.setEnabled(true);
			
			while(connectionStatus) {
				text.append("Server: " + bufferedReader.readLine());
				text.append("\n");
				textArea.setText(text.toString());
			}
			
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				try {
					if(socket != null) {
						socket.close();
					}
					if(inputStreamReader != null) {
						inputStreamReader.close();
					}
					if(outputStreamWriter != null) {
						outputStreamWriter.close();
					}
					if(bufferedReader != null) {
						bufferedReader.close();
					}
					if(bufferedWriter != null) {
						bufferedWriter.close();
					}
					
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
	}	
}
