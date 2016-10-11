package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import gameWorld.GameState;
import gameWorld.Player;

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

	public void tellServerImMoving(String movement) throws IOException{
		this.objectOutputToServer.writeObject(movement+" "+this.client.getName());
		this.client.setLastDirectionMoved(movement);
		this.objectOutputToServer.flush();
		this.lastRequest = "MOVE";
	}

	public void tellServerAction(String action, String item) throws IOException{
		this.objectOutputToServer.writeObject(action+" "+item+" "+this.client.getName());
		this.objectOutputToServer.flush();
		this.lastRequest = "ACTION";
	}

	@Override
	public void run() {
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
										client.getApplicationWindow().updateAll();
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
			} catch (ClassNotFoundException e1) {
				System.out.println("CLIENT: Class not found when reading object from server, error "+e1.getStackTrace());
			}
		}
	}

	public void shutdown() throws IOException {
		this.running = false;
		this.objectOutputToServer.writeObject("DISCONNECTING "+this.client.getName());
		this.objectOutputToServer.flush();
		this.clientSocket.close();
	}

}
