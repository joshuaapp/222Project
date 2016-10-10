package control;
import java.io.PrintWriter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import gameWorld.Player;
import gameWorld.Player.Direction;
import ui.ApplicationWindow;
public class Client implements Runnable {
	//fields for various socker readers/writers
	private Socket clientSocket = null;
	private PrintWriter outputToServer = null;
	private BufferedReader inputFromServer = null;
	private boolean closed = false;
	private Player player;
	private int portNum = 8001;
	private String host = "localhost";
	public ApplicationWindow clientsWindow;
	
	public Client(Player player){
		this.player = player;
		//create client socket and open the input/output readers/writers
		try {
			this.clientSocket = new Socket(host, portNum);
			//for user input
			//userInput = new BufferedReader(new InputStreamReader(System.in));
			//for server input
			//inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//output to server
			this.outputToServer = new PrintWriter(clientSocket.getOutputStream());
		} catch (UnknownHostException e) {
			System.err.println("Unknown host " + host);
		} catch (IOException e) {
			System.err.println("I/O Error: "+e+" on host "+host);
		}
	}
	public synchronized void tellServerImMoving(String movement){
		this.outputToServer.println(movement+" "+this.toString());
		this.outputToServer.flush();
	}
	
	public synchronized void tellServerAction(String action, String item){
		this.outputToServer.println(action+" "+this.toString()+" "+item);
		this.outputToServer.flush();
	}
	
/**Here we want to wait for input from the board and carry out movement based on the key entered.
 * The client simply hands down the responsibility to the Server which talks to the GameLogic and updates
 * GameState. 
 * 
 */
	public synchronized void run() {
		clientsWindow = new ApplicationWindow("Team14's awesome game yeeeh", this);
		clientsWindow.attatchClientToWindow(this);
		clientsWindow.createAndShowGUI();
		clientsWindow.getGameCanvas().setPlayer(player);
	}
	public Player getPlayer() {
		return this.player;
	}
	
	public PrintWriter getClientOutputWriter(){
		return this.outputToServer;
	}
	/**Methods client will need to communicate to server
	 *04/10/16 - Josh 
	 */ 
	/**Client must make a request each time they want to move to see if that move is valid.
	 * This method passes on this request to the server to be processed further down the pipeline
	 * (validity is calculated by the server and executes action if valid. otherwise it does nothing
	 */
	public synchronized void requestMove(Direction directionToMove){
		this.outputToServer.println(directionToMove.toString() + " "+this.toString());
		this.outputToServer.flush();
	}
	public BufferedReader getClientBufferedReader() {
		return inputFromServer;
	}
	
	public Socket getSocket() {
		return this.clientSocket;
	}
}