package control;
//
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//
//public class Client implements Runnable{
//
//	private static Socket client = null;
//	private static PrintWriter output = null;
//	private static BufferedReader serverInput = null;
//	private static BufferedReader userInput = null;
//	private static boolean closed = false;
//
//
//	public static void main(String args[]){
//		if(args.length != 2){
//			System.out.println("Error! Usage: java Client <host name> <port number>");
//			System.exit(1);
//		}
//		String host = args[0];
//		int portNum = Integer.parseInt(args[1]);
//
//		try{
//			client = new Socket(host, portNum);
//			output = new PrintWriter(client.getOutputStream(), true);
//			serverInput = new BufferedReader(new InputStreamReader(client.getInputStream()));
//			userInput = new BufferedReader(new InputStreamReader(System.in));
//
//			if(client != null && output != null && serverInput != null){
//				new Thread(new Client()).start();
//				while(!closed){
//					output.println(userInput.readLine());
//				}
//
//				output.close();
//				serverInput.close();
//				client.close();
//			}
//		}
//		catch(IOException e){
//			System.out.println("Error: "+e);
//			System.exit(1);
//		}
//	}
//
//	@Override
//	public void run() {
//		try{
//			String messageFromServer;
//			String messageToSend;
//			while((messageFromServer = serverInput.readLine()) != null){
//				System.out.println("Server said: "+messageFromServer);
//				messageToSend = userInput.readLine();
//				if(messageToSend != null){
//					System.out.println("Client said: "+messageToSend);
//					output.println(messageToSend);
//				}
//			}
//		}
//		catch(IOException e){
//
//		}
//	}
//
//}


import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import gameWorld.Player;
import ui.ApplicationWindow;

public class Client implements Runnable {

	//fields for various socker readers/writers
	private static Socket clientSocket = null;
	private static PrintWriter output = null;
	private static BufferedReader serverInput = null;
	private static BufferedReader userInput = null;
	private static boolean closed = false;
	private Player player;
	
	private int portNum = 8001;
	private String host = "localhost";

	
	public Client(Player player){
		this.player = player;
		//create client socket and open the input/output readers/writers
		try {
			clientSocket = new Socket(host, portNum);
			//for user input
			//userInput = new BufferedReader(new InputStreamReader(System.in));
			//for server input
			serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//output to server
			output = new PrintWriter(clientSocket.getOutputStream());
		} catch (UnknownHostException e) {
			System.err.println("Unknown host " + host);
		} catch (IOException e) {
			System.err.println("I/O Error: "+e+" on host "+host);
		}
		//In theory, everything should be initialised. 
//		if (clientSocket != null && output != null && serverInput != null) {
//			try {
//				//new Thread(new Client(this.player)).start();
//				while (!closed) {
//					output.println(userInput.readLine().trim());
//				}
//				//close all resources now that thread is terminated
//				output.close();
//				serverInput.close();
//				clientSocket.close();
//			} catch (IOException e) {
//				System.err.println("IOException:  " + e);
//			}
//		}
	}
	
/**Here we want to wait for input from the board and carry out movement based on the key entered.
 * The client simply hands down the responsibility to the Server which talks to the GameLogic and updates
 * GameState. 
 * 
 */
	public void run() {
		System.out.println("Hello, client is running and connected to server");
		ApplicationWindow clientsWindow = new ApplicationWindow("Team14's awesome game yeeeh");
		clientsWindow.createAndShowGUI();
		clientsWindow.getGameCanvas().setPlayer(player);

//		String responseLine;
//		try {
//			while ((responseLine = serverInput.readLine()) != null) {
//				System.out.println(responseLine);
//				if (responseLine.indexOf("*** Bye") != -1)
//					break;
//			}
//			closed = true;
//		} catch (IOException e) {
//			System.err.println("I/O Error:  " + e);
//		}
	}

	public Player getPlayer() {
		return this.player;
	}
}
