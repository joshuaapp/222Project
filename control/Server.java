package control;

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
import items.Item;
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
	private ArrayList<Client> clients;

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

	public void run(){
		while (true) {
			try {
				Socket client = serverSocket.accept();
				ServerHelper helper = new ServerHelper(this, client);
				new Thread(helper).start();

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

	public  void updateGameStatePlayerPositions(String s, Player p){
		currentGameState.updatePlayerPosition(p, s);
	}

	public void addClientToConnectedClients(Client c){
		this.currentGameState.addClient(c);
		clients.add(c);
	}
	public ArrayList<Client> getClients() {
		return clients;
	}
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	public  void processClientMovementRequest(String direction, String clientObjectAsString) {
		Player toMove = null;
		for(Client c : clients){
			if(c.getName().equals(clientObjectAsString)){
				toMove = c.getPlayer();
			}
		}
		updateGameStatePlayerPositions(direction, toMove);
	}
	public  void processClientActionRequest(String action, String item, String clientObjectAsString) {
		Player toAct = null;
		for(Client c : clients){
			if(c.getName().equals(clientObjectAsString)){
				toAct = c.getPlayer();
			}
		}
		updateGameStatePlayerAction(action, toAct, item);
	}

	public  void updateGameStatePlayerAction(String s, Player p, String item){
		currentGameState.updatePlayerAct(p, s, item);
	}

	public GameState getCurrentGameState() {
		return this.currentGameState;
	}

	public Client getClientFromName(String clientName) {
		for(Client c : this.clients){
			if(c.getName().equals(clientName)){
				return c;
			}
		}
		return null;

	}



}