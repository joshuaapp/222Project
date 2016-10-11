package control;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import gameWorld.GameState;
import gameWorld.Player;
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

	/**This is the running method for the server which simply waits for a client to connect to it.
	 * The server creates a new serverhelper as soon as a client has made a successful connection.
	 * The serverhelper thread talks between the client and server and runs seperate to the Server
	 * so the server can constantly be waiting for new clients to connect.
	 *
	 */
	public void run(){
		while (true) {
			try {
				//only accept clients if there is room left
				if(this.clients.size() < maxClientsCount){
					Socket client = serverSocket.accept();
					ServerHelper helper = new ServerHelper(this, client);
					new Thread(helper).start();
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	/**Adds a client to the current list of clients connected to the server.
	 *
	 * @param c - client to add
	 */
	public void addClientToConnectedClients(Client c){
		this.currentGameState.addClient(c);
		clients.add(c);
	}

	/**Gets the list of connected clients to the server
	 *
	 * @return ArrayList<Client>
	 */
	public ArrayList<Client> getClients() {
		return clients;
	}

	/**This method is called when a server has requested to move. It takes the movement request
	 * and asks the gameState to update player positions based on the movement request.
	 * 
	 * @param direction
	 * @param clientObjectAsString
	 */
	public  void processClientMovementRequest(String direction, String clientObjectAsString) {
		Player toMove = null;
		for(Client c : clients){
			if(c.getName().equals(clientObjectAsString)){
				toMove = c.getPlayer();
			}
		}
		currentGameState.updatePlayerPosition(toMove, direction);
	}
	
	/**This method takes an action request from the client and asks the gameState to update depending on the action.
	 * 
	 * @param action 
	 * @param item - item to perform action on (only used when dropping)
	 * @param clientObjectAsString
	 */
	public  void processClientActionRequest(String action, String item, String clientObjectAsString) {
		Player toAct = null;
		for(Client c : clients){
			if(c.getName().equals(clientObjectAsString)){
				toAct = c.getPlayer();
			}
		}
		currentGameState.updatePlayerAct(toAct, action, item);
	}
	
	/**Returns the current game state of server
	 * 
	 * @return
	 */
	public GameState getCurrentGameState() {
		return this.currentGameState;
	}
	
	/**Gets the name of a client from the list of clients connected to the server
	 * 
	 * @param clientName
	 * @return
	 */
	public Client getClientFromName(String clientName) {
		for(Client c : this.clients){
			if(c.getName().equals(clientName)){
				return c;
			}
		}
		return null;
	}
}