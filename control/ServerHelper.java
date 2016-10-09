package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import gameWorld.Player;
import gameWorld.Player.Direction;
import tiles.StartTile;

public class ServerHelper implements Runnable{

	private Socket clientSocket;
	private Server server;
	private Client client;
	private BufferedReader inputFromClient;
	private PrintWriter outputToClient;
	private ObjectOutputStream objectOutputToClient;
	private ObjectInputStream objectInputFromClient;

	public ServerHelper(Server server, Socket clientSocket){
		this.server = server;
		this.clientSocket = clientSocket;
		try{
			this.inputFromClient = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			this.outputToClient = new PrintWriter(this.clientSocket.getOutputStream());
			this.objectOutputToClient = new ObjectOutputStream(this.clientSocket.getOutputStream());
			this.objectOutputToClient.flush();
			this.objectInputFromClient = new ObjectInputStream(this.clientSocket.getInputStream());

		}
		catch(IOException e){
			System.out.println(e);
		}
	}

	@Override
	public synchronized void run() {
		String clientRequest = null;
		String[] brokenRequest = new String[3];
		//get client to add to list of servers clients
		this.outputToClient.println("GET_CLIENT");
		this.outputToClient.flush();
		try{
			while(true){
				clientRequest = this.inputFromClient.readLine();
				if(clientRequest != null){
					brokenRequest = clientRequest.split(" ");
					if(brokenRequest[0].equals("UP") || brokenRequest[0].equals("DOWN") ||
							brokenRequest[0].equals("LEFT") ||brokenRequest[0].equals("RIGHT")){
						this.server.processClientMovementRequest(brokenRequest[0], brokenRequest[1]);
						//send updates back to client
						sendGameState(brokenRequest[1]);

					}
					else if(brokenRequest[0].equals("PICK") || brokenRequest[0].equals("DROP")){
						this.server.processClientActionRequest(brokenRequest[0], brokenRequest[1], brokenRequest[2]);
					}
					else if(brokenRequest[0].equals("SENDING_CLIENT")){
						try{
							Object o = objectInputFromClient.readObject();
							if(o instanceof Client){
								Client client  = (Client) o;
								server.addClientToConnectedClients(client);
							}
						}
						catch (IOException e){
							System.out.println("IOException reading object: "+e);
						} catch (ClassNotFoundException e) {
							System.out.println("Classnotfound exception reading object");
						}
					}
					else if(brokenRequest[0].equals("REQUESTING_STATE")){
						for(Client c : server.getClients()){
							if(c.getName().equals(brokenRequest[1])){
								sendGameState(c.getName());
							}
						}

					}
				}
				//check for objects being sent

			}
		}
		catch(IOException e){
			System.out.println(e);
		}
	}

	public void processClientMovementRequest(String direction, String clientObjectAsString) {
//		Player toMove = null;
//		for(Client c : clients){
//			if(c.getName().equals(clientObjectAsString)){
//				toMove = c.getPlayer();
//			}
//		}
//		updateGameStatePlayerPositions(direction, toMove);
	}

	public void processClientActionRequest(String action, String clientObjectAsString) {
//		Player toAct = null;
//		for(Client c : clients){
//			if(c.toString().equals(clientObjectAsString)){
//				toAct = c.getPlayer();
//			}
//		}
//		updateGameStatePlayerAction(action, toAct);
	}


	public void updateGameStatePlayerAction(String s, Player p){
		//currentGameState.updatePlayerAct(p, s);
	}

	/**
	 * Will need to send updated board and rederperspective to client with name clientName+
	 * @param clientName
	 */
	public void sendGameState(String clientName) {
		for(Client c : this.server.getClients()){
			if(c.getName().equals(clientName)){
				outputToClient.println("SENDING_UPDATED_STATE");
				outputToClient.flush();
				try {
					objectOutputToClient.reset();
					objectOutputToClient.writeObject(this.server.getCurrentGameState());
					objectOutputToClient.flush();
				} catch (IOException e) {
					System.out.println("Error writing board back to client: "+e);
				}
			}
		}
	}

}
