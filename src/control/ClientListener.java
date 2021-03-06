package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import gameWorld.GameState;
import gameWorld.Player;

/**
 * 
 * @author Josh Apperley
 *
 */

public class ClientListener implements Runnable,Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2207682725561596061L;
	private transient Client client;
	private transient Socket clientSocket;
	private transient ObjectOutputStream objectOutputToServer;
	private transient ObjectInputStream objectInputFromServer;
	private transient String lastRequest;
	private transient boolean running;


	/**Create a client and connect to server
	 * 
	 * @param client
	 * @param clientSocket
	 */
	public ClientListener(Client client, Socket clientSocket){
		this.client = client;
		this.clientSocket = clientSocket;
		try {
			//object output and input
			this.objectOutputToServer = new ObjectOutputStream(clientSocket.getOutputStream());
			objectOutputToServer.flush();
			this.objectInputFromServer = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.running = true;
	}

	/**Sends a movement request to the server based on the application window key press
	 * 
	 * @param movement
	 * @throws IOException
	 */
	public void tellServerImMoving(String movement) throws IOException{
		this.objectOutputToServer.writeObject(movement+" "+this.client.getName());
		this.client.setLastDirectionMoved(movement);
		this.objectOutputToServer.flush();
		this.lastRequest = "MOVE";
	}

	/**Sends an action to the server that the client wants to perform. 
	 * 
	 * @param action
	 * @param item
	 * @throws IOException
	 */
	public void tellServerAction(String action, String item) throws IOException{
		this.objectOutputToServer.writeObject(action+" "+item+" "+this.client.getName());
		this.objectOutputToServer.flush();
		this.lastRequest = "ACTION";
	}

	/**The run method constantly loops, asking for input from the server and performing 
	 * whatever actions necessary to update the application window based on the server output.
	 * 
	 */
	@Override
	public void run() {
		outer :
		while(running){
			try {
				Object serverOutput = objectInputFromServer.readObject();
				if(serverOutput instanceof String){
					String input = (String) serverOutput;
					if(input != null){
						if(input.equals("GET_CLIENT")){
							this.objectOutputToServer.writeObject("SENDING_CLIENT");
							this.objectOutputToServer.flush();

							objectOutputToServer.writeObject(this.client);
							objectOutputToServer.flush();
							if(client.getGameState() == null){
								this.objectOutputToServer.writeObject("REQUESTING_STATE "+this.client.getName());
								this.objectOutputToServer.flush();
							}
						}
						else if(input.equals("SENDING_UPDATED_STATE")){
							try{
								Object o = objectInputFromServer.readObject();
								if(o instanceof GameState){
									GameState state = (GameState) o;
									this.client.setGameState(state);
									if(this.lastRequest != null){
										Player p = state.getPlayerOfClient(this.client.getName());
										client.addPlayer(p);
										client.getApplicationWindow().getGameCanvas().setPlayer(p);
										p.getRP().updatePerspective();
										if(p.isGotBag()){
											//update game text
											List<String> textToUpdate = Arrays.asList("You found a bag! You can now pick up keys. Keys unlock doors. I heard there is a magic crystal floating around in a room somewhere....".split(" "));	
											this.client.getApplicationWindow().getGameCanvas().updateCanvasText(textToUpdate);
										}
									}
								}
							}

							catch(Exception e){
								System.out.println(e);
							}
						}
					}
				}
			}
			catch (IOException e) {
				System.out.println("CLIENT IOException when reading object from server, error "+e.getStackTrace());
				//server has shutdown therefore stop client
				System.exit(1);
				break outer;
			} catch (ClassNotFoundException e1) {
				System.out.println("CLIENT: Class not found when reading object from server, error "+e1.getStackTrace());
			}
		}
	}
	
	/**Shuts down the client listener and disconnects the client from server
	 * 
	 * @throws IOException
	 */
	public void shutdown() throws IOException {
		this.running = false;
		this.objectOutputToServer.writeObject("DISCONNECTING "+this.client.getName());
		this.objectOutputToServer.flush();
		this.clientSocket.close();
	}

}
