package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;

import gameWorld.Board;
import gameWorld.GameState;
import gameWorld.Player;

public class ClientListener implements Runnable,Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2207682725561596061L;
	private transient Client client;
	private transient Socket clientSocket;
	private transient PrintWriter outputToServer = null;
	private transient BufferedReader inputFromServer = null;
	private transient ObjectOutputStream objectOutputToServer;
	private transient ObjectInputStream objectInputFromServer;
	private transient String lastRequest;


	public ClientListener(Client client, Socket clientSocket){
		this.client = client;
		this.clientSocket = clientSocket;
		//string output
		try {
			this.outputToServer = new PrintWriter(clientSocket.getOutputStream());
			this.inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//object output and input
			this.objectOutputToServer = new ObjectOutputStream(clientSocket.getOutputStream());
			objectOutputToServer.flush();
			this.objectInputFromServer = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void tellServerImMoving(String movement){
		this.outputToServer.println(movement+" "+this.client.getName());
		this.client.setLastDirectionMoved(movement);
		this.outputToServer.flush();
		this.lastRequest = "MOVE";
	}

	public synchronized void tellServerAction(String action, String item){
		this.outputToServer.println(action+" "+item+" "+this.client.getName());
		this.outputToServer.flush();
		this.lastRequest = "ACTION";
	}

	@Override
	public void run() {
		while(true){
			//wait for and respond to any input
			String input;
			try {
				input = inputFromServer.readLine();
				if(input != null){
					System.out.println(input);
					if(input.equals("GET_CLIENT")){
						outputToServer.println("SENDING_CLIENT");
						outputToServer.flush();
						objectOutputToServer.writeObject(this.client);
						objectOutputToServer.flush();
						//objectOutputToServer.reset();
						if(client.getGameState() == null){
							outputToServer.println("REQUESTING_STATE "+this.client.getName());
							outputToServer.flush();
						}
					}
					else if(input.equals("SENDING_UPDATED_STATE")){
						Object o = objectInputFromServer.readObject();
						if(o instanceof GameState){
							GameState state = (GameState) o;
							this.client.setGameState(state);
							if(this.lastRequest != null){
								if(this.lastRequest.equals("MOVE")){
									//this.client.addPlayer(state.getPlayerOfClient(this.client.getName()));
									//state.getLogic().rotateOrMove(client.getPlayer(), client.getLastDirectionMoved());
//									Board b = (Board) state.getGameBoard();
//									//System.out.println(b);
//									client.setGameState(state);
//									client.getApplicationWindow().repaint();
									Player p = state.getPlayerOfClient(this.client.getName());
									p.createRenderPerspective();
									client.addPlayer(p);
									client.getApplicationWindow().getGameCanvas().setPlayer(p);
									p.getRP().updatePerspective();
//									client.getPlayer().updateBoard(state.getGameBoard());
//									client.getApplicationWindow().getGameCanvas().setPlayer(client.getPlayer());
//									client.getApplicationWindow().getGameCanvas().repaint();
//									client.getPlayer().getRenderPerspective().updatePerspective();
//									this.client.updatePlayerDetails(p);

									}
								else if(this.lastRequest.equals("ACTION")){
									state.logic.isThereAnItem(client.getPlayer());
								}
								//System.out.println(state.getGameBoard());
								//System.out.println(p.getBoard());
							}
						}
						else if(o instanceof Board){
							Board b = (Board) o;
							System.out.println(b);
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
