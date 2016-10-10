package control;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import gameWorld.GameState;
import gameWorld.Player;
import ui.ApplicationWindow;
public class Client implements Runnable, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7944680494505804624L;
	//private static final long serialVersionUID = 5140068832054479020L;
	private transient ClientListener listenerForServerInput;
	//fields for various socker readers/writers
	private transient Socket clientSocket = null;
	//private boolean closed = false;
	private Player player;
	private int portNum = 8001;
	private String name;
	private String serverIp = "sen-sen.ecs.vuw.ac.nz";
	private transient ApplicationWindow clientsWindow;
	private String lastDirectionMoved;
	private GameState state;


	/**When a client is created with a player it does the following things:
	 * - Creates the connection between client a 	nd server.
	 * - Places the player on the board in a valid starting position.
	 * -
	 */
	public Client(){
		//create client socket and open the input/output readers/writers
		try {
			this.clientSocket = new Socket(serverIp, portNum);
			this.name = clientSocket.toString();
		}
		catch(Exception e){
			System.out.println("Error connecting to host: "+serverIp);
		}
	}

	public void addPlayer(Player p){
		this.player = p;
	}

	public String getLastDirectionMoved(){
		return this.lastDirectionMoved;
	}

	/**Here we want to wait for input from the board and carry out movement based on the key entered.
	 * The client simply hands down the responsibility to the Server which talks to the GameLogic and updates
	 * GameState.
	 *
	 */
	public void run() {
		//want to get state from board and set player pos and place on board
		listenerForServerInput = new ClientListener(this, this.clientSocket);
		new Thread(listenerForServerInput).start();
		//listenerForServerInput.tellServerAction("REQUEST_STATE");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//place player on board.
		this.player = state.getPlayerOfClient(this.name);
		this.player.createRenderPerspective();
		clientsWindow = new ApplicationWindow("Team14's awesome game yeeeh", this);
		clientsWindow.createAndShowGUI();
		clientsWindow.getGameCanvas().setPlayer(player);


	}
	public Player getPlayer() {
		return this.player;
	}

	/**Methods client will need to communicate to server
	 *04/10/16 - Josh
	 */
	/**Client must make a request each time they want to move to see if that move is valid.
	 * This method passes on this request to the server to be processed further down the pipeline
	 * (validity is calculated by the server and executes action if valid. otherwise it does nothing
	 */

	public Socket getSocket() {
		return this.clientSocket;
	}

	public void tellServerImMoving(String direction) {
		this.listenerForServerInput.tellServerImMoving(direction);
	}

	public void tellServerAction(String action, String item) {
		this.listenerForServerInput.tellServerAction(action, item);
	}

	public String getName() {
		return this.name;
	}

	public ApplicationWindow getApplicationWindow() {
		return this.clientsWindow;
	}

	public void setLastDirectionMoved(String direction) {
		this.lastDirectionMoved = direction;
	}

	public GameState getGameState() {
		return this.state;
	}

	public void setGameState(GameState state){
		this.state = state;
	}

	public void updatePlayerDetails(Player p) {
		this.player = p;
	}

	public void shutdown() {
		try {
			this.listenerForServerInput.shutdown();
			this.clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}