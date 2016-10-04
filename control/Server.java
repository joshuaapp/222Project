package control;
//
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Server {
//
//	/**Main method to intialize and run server. Arguments are the port
//	 * number that the server will be running on. 
//	 * 
//	 *@param args
//	 *@Author Josh
//	 */
//
//	private List<Socket> clients;
//	private ServerSocket serverSock;
//	public Server(int portNum){
//		clients = new ArrayList<Socket>();
//		try{
//			//create socket for server
//			this.serverSock = new ServerSocket(portNum);
//		}
//		catch(Exception e){
//			System.out.println("Error creating server socket! "+e);
//		}
//
//	}
//
//	public void run(){
//		try{
//
//
//			//wait for client to connect
//			Socket client = serverSock.accept();
//			clients.add(client);
//			//create readers and writers for communication between client/server
//			BufferedReader clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//			PrintWriter clientWriter = new PrintWriter(client.getOutputStream(), true);
//			String inputString;
//			String outputString;
//			CommunicationProtocol protocol = new CommunicationProtocol();
//			outputString = protocol.processInput(null);
//			clientWriter.println(outputString);
//
//			while((inputString = clientReader.readLine()) != null){
//				outputString = protocol.processInput(inputString);
//				outputString += (" " +clients.indexOf(client));
//				clientWriter.println(outputString);
//			}
//		}
//		catch(java.io.IOException e){
//			System.out.println("Error communicating with client! "+e);
//		}
//	}
//
//	public static void main(String args[]){
//		try{
//			if(args.length != 1){
//				System.out.println("Error! Usage: java Server <port number>");
//				System.exit(1);
//			}
//			else{
//				//clients = new ArrayList<Client>();
//				int portNum = Integer.parseInt(args[0]);
//				Server s = new Server(portNum);
//				s.run();
//			}
//		}
//		catch(NullPointerException e){
//			System.out.println("Invalid port number specified! Please specify valid port number for server to use");
//		}
//
//	}
//
//	public static class CommunicationProtocol {
//		private static final int WAITING=0;
//		private static final int SENTHELLO=1;
//		private static final int SENTQUESTION=2;
//
//		private int currentState = WAITING;
//		public String processInput(String input){
//			String output="";
//			switch(currentState){
//			case 0:
//				output = "Hello there!";
//				currentState = SENTHELLO;
//				break;
//			case 1:
//				output = "How are you today?";
//				currentState = SENTQUESTION;
//				break;
//			case 2:
//				output = "That's interesting!";
//				break;
//			}
//			return output;
//		}
//
//	}
//
//}

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import gameWorld.GameState;
import gameWorld.Player;
import gameWorld.Player.Direction;
import ui.ApplicationWindow;

import java.net.ServerSocket;

/**This is the sever application which allows up to maxNumClients to connect to the serverSocket.
 * It communicates between the clients and the game logic/game state in order to perform game actions.
 * 
 * @author Joshua
 *
 */
public class Server implements Runnable{
	
	private GameState currentGameState;
	private ServerSocket serverSocket = null;
	private BufferedReader inputFromClient;
	private PrintWriter outputToClient;
	private final int maxClientsCount = 3;
	private final int portNum = 8001;
	//all the threads (clients) that are connected
	private List<Client> clients;
	
	public Server(GameState gameState) throws IOException{
		this.currentGameState = gameState;
		//attempt to create server socket
				try {
					serverSocket = new ServerSocket(portNum);
					clients = new ArrayList<Client>();
				}
				/*In this case there is already an existing server running */
				catch (java.net.BindException e){
					System.out.println("Server already running");
				} 
				catch (IOException e) {
					throw new IOException("Error creating server socket: "+e);
				}			
	}
	
	public synchronized void run(){
		while (true) {
			try {
				Socket client = serverSocket.accept();
				//inputFromClient = new BuferedReader(new InputStreamReader(client.getInputStream()));
				//System.out.println("Connected to "+client.getRemoteSocketAddress());
				ServerHelper helper = new ServerHelper(this, client);
				new Thread(helper).start();

//				int i = 0;
//				for (i = 0; i < maxClientsCount; i++) {
//					if (threads[i] == null) {
//						//(threads[i] = new clientThread(clientSocket, threads)).start();
//						break;
//					}
//				}
//				if (i == maxClientsCount) {
//					PrintStream os = new PrintStream(clientSocket.getOutputStream());
//					os.println("Server too busy. Try later.");
//					os.close();
//					clientSocket.close();
//				}
//				CommunicationProtocol cp = new CommunicationProtocol();
//				String inputLine,outputLine;
//				outputLine = cp.processInput(null);
//				outputToClient.println(outputLine);
//				while((inputLine = inputFromClient.readLine()) != null){
//					outputLine = cp.processInput(inputLine);
//					System.out.println(outputLine);
//				}
				//cp.run();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
	
	//JOSH NEEDS TO WRITE A METHOD THAT
	//Recieves a message from the client
	//From this he needs to get a string saying "UP", "DOWN"," "LEFT", "RIGHT"
	//and he also needs to find out which player the client who sendt that message is
	//This is then sent to updateGameStatePlayerPositions
	
	/**Method to update player positions in game state
	 * This method should recieve an input from the client over its socket,
	 * it will reiceve a string form the player indicating which button it has pressed
	 */
	
	public void updateGameStatePlayerPositions(String s, Player p){
		currentGameState.updatePlayerPosition(p, s);
	}
	
	public void addClientToConnectedClients(Client c){
		clients.add(c);
	}

	public List<Client> getClients() {
		return clients;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void processClientMovementRequest(String direction, String clientObjectAsString) {
		System.out.println("Processing client movement request");
		Player toMove = null;
		for(Client c : clients){
			if(c.toString().equals(clientObjectAsString)){
				toMove = c.getPlayer();
			}
		}
		updateGameStatePlayerPositions(direction, toMove);
	}
	
}

/*
 * The chat client thread. This client thread opens the input and the output
 * streams for a particular client, ask the client's name, informs all the
 * clients connected to the server about the fact that a new client has joined
 * the chat room, and as long as it receive data, echos that data back to all
 * other clients. The thread broadcast the incoming messages to all clients and
 * routes the private message to the particular client. When a client leaves the
 * chat room this thread informs also all the clients about that and terminates.
 *
class clientThread extends Thread {
	
	private Player clientThreadPlayer = null;
	private String clientName = null;
	private BufferedReader input = null;
	private PrintStream os = null;
	private Socket clientSocket = null;
	private final clientThread[] threads;
	private int maxClientsCount;

	public clientThread(Socket clientSocket, clientThread[] threads) {
		this.clientSocket = clientSocket;
		this.threads = threads;
		maxClientsCount = threads.length;
	}

	public void run() {
		
		int maxClientsCount = this.maxClientsCount;
		clientThread[] threads = this.threads;
		
		ApplicationWindow clientsWindow = new ApplicationWindow("TEAM14'S wicked game!");
		clientsWindow.createAndShowGUI();
/*
		try {
			/*
			 * Create input and output streams for this client.
			 *
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			os = new PrintStream(clientSocket.getOutputStream());
			String name;
			while (true) {
				os.println("Enter your name.");
				name = input.readLine().trim();
				if (name.indexOf('@') == -1) {
					break;
				} else {
					os.println("The name should not contain '@' character.");
				}
			}

			/* Welcome the new the client. 
			os.println("Welcome " + name
					+ " to our chat room.\nTo leave enter /quit in a new line.");
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] == this) {
						clientName = "@" + name;
						break;
					}
				}
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] != this) {
						threads[i].os.println("*** A new user " + name
								+ " entered the chat room !!! ***");
					}
				}
			}
			/* Start the conversation. 
			while (true) {
				String line = input.readLine();
				if (line.startsWith("/quit")) {
					break;
				}
				/* If the message is private sent it to the given client. 
				if (line.startsWith("@")) {
					String[] words = line.split("\\s", 2);
					if (words.length > 1 && words[1] != null) {
						words[1] = words[1].trim();
						if (!words[1].isEmpty()) {
							synchronized (this) {
								for (int i = 0; i < maxClientsCount; i++) {
									if (threads[i] != null && threads[i] != this
											&& threads[i].clientName != null
											&& threads[i].clientName.equals(words[0])) {
										threads[i].os.println("<" + name + "> " + words[1]);
										/*
										 * Echo this message to let the client know the private
										 * message was sent.
										 
										this.os.println(">" + name + "> " + words[1]);
										break;
									}
								}
							}
						}
					}
				} else {
					/* The message is public, broadcast it to all other clients. 
					synchronized (this) {
						for (int i = 0; i < maxClientsCount; i++) {
							if (threads[i] != null && threads[i].clientName != null) {
								threads[i].os.println("<" + name + "> " + line);
							}
						}
					}
				}
			}
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] != this
							&& threads[i].clientName != null) {
						threads[i].os.println("*** The user " + name
								+ " is leaving the chat room !!! ***");
					}
				}
			}
			os.println("*** Bye " + name + " ***");

			/*
			 * Clean up. Set the current thread variable to null so that a new client
			 * could be acepted by the server.
			 
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] == this) {
						threads[i] = null;
					}
				}
			}
			/*
			 * Close the output stream, close the input stream, close the socket.
			 
			input.close();
			os.close();
			clientSocket.close();
		} catch (IOException e) {
		}
		*
	}
}*/
