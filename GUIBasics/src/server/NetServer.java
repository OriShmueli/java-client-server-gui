package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NetServer {
	
	private Socket socket = null;
	private InputStreamReader inputStreamReader = null;
	private OutputStreamWriter outputStreamWriter = null;
	private boolean serverStarted = false;
	private BufferedReader bufferedReader = null; //input
	private BufferedWriter bufferedWriter = null; //output
	private ServerSocket serverSocket = null; //wait for requests to come in over the network.
	private JTextArea textArea = null;
	private JTextField textField = null;
	private JButton sendButton = null;
	private StringBuilder text= new StringBuilder();
	
	public NetServer(JTextArea textArea, JTextField textField, JButton sendButton) {
		this.textArea = textArea;
		this.textField = textField;
		this.sendButton = sendButton;
		serverStarted = false;
		initializeComponents();
		sendButton.setEnabled(false);
		textField.setEnabled(false);
	}
	
	public void StopServer() {
		serverStarted = false;
		sendButton.setEnabled(false);
		textField.setEnabled(false);
	}
	
	public void StartServer() {
		sendButton.setEnabled(true);
		textField.setEnabled(true);
		serverStarted = true;
		
		try {
			serverSocket = new ServerSocket(1234);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(serverStarted) {
			try {
				socket = serverSocket.accept();//wait for client to connect. and then return a new Socket object.
				inputStreamReader = new InputStreamReader(socket.getInputStream());
				outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
				
				bufferedReader = new BufferedReader(inputStreamReader);
				bufferedWriter = new BufferedWriter(outputStreamWriter);
				
				while(serverStarted) {
					text.append("Client: " + bufferedReader.readLine());
					text.append("\n");
					textArea.setText(text.toString());
				}
				
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
				
			}finally {
				
				//Close Socket
				try {
					if(serverSocket != null) {
						serverSocket.close();
					}
				}catch(IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public boolean GetConnectionStatus() {
		return serverStarted;
	}

	public JTextField GetTextField() {
		return textField;
	}
	
	private void initializeComponents() {
		sendButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(!GetTextField().getText().isEmpty()) {
					text.append("Server: " + GetTextField().getText());
					text.append("\n");
					sendMessageToServer(GetTextField().getText());
					textArea.setText(text.toString());
				}
			}
		});
		
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
}
