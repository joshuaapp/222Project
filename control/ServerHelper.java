package control;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import gameWorld.Player;
import gameWorld.Player.Direction;
import tiles.StartTile;
public class ServerHelper implements Runnable{
	private Socket clientSocket;
	private Server server;
	private Client client;
	private ObjectOutputStream objectOutputToClient;
	private ObjectInputStream objectInputFromClient;
	private boolean running;
	public ServerHelper(Server server, Socket clientSocket){
		this.server = server;
		this.clientSocket = clientSocket;
		try{
			this.objectOutputToClient = new ObjectOutputStream(this.clientSocket.getOutputStream());
			this.objectOutputToClient.flush();
			this.objectInputFromClient = new ObjectInputStream(this.clientSocket.getInputStream());
		}
		catch(IOException e){
			System.out.println(e);
		}
		this.running = true;
	}
	@Override
	public void run() {
		String clientRequest = null;
		String[] brokenRequest = new String[3];
		//get client to add to list of servers clients
		try{
			System.out.println("SERVER requesting to get client");
			this.objectOutputToClient.writeObject("GET_CLIENT");
			this.objectOutputToClient.flush();
			while(running){
				System.out.println("SERVER trying to read an object");
				Object clientOutput = this.objectInputFromClient.readObject();
				if(clientOutput instanceof String){
					System.out.println("SERVER read a string object: "+clientOutput);
					clientRequest = (String) clientOutput;
					brokenRequest = clientRequest.split(" ");
				}
				if(clientRequest != null){
					brokenRequest = clientRequest.split(" ");
					if(brokenRequest[0].equals("UP") || brokenRequest[0].equals("DOWN") ||
							brokenRequest[0].equals("LEFT") ||brokenRequest[0].equals("RIGHT")){
						this.server.processClientMovementRequest(brokenRequest[0], brokenRequest[1]);
						System.out.println("Server processed movement request");
						//send updates back to client
						sendGameState(brokenRequest[1]);
					}
					else if(brokenRequest[0].equals("PICK") || brokenRequest[0].equals("DROP")){
						System.out.println("Processing pickup or drop request");
						this.server.processClientActionRequest(brokenRequest[0], brokenRequest[1], brokenRequest[2]);
						System.out.println("Sending state back. Does player have bag? "+server.getClientFromName(brokenRequest[2]).getPlayer().gotBag);
						sendGameState(brokenRequest[2]);
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
					else if(brokenRequest[0].equals("DISCONNECTING")){
						Client toRemove = this.server.getClientFromName(brokenRequest[1]);
						System.out.println("Removing "+toRemove+" from connected clients");
						this.server.getCurrentGameState().removePlayer(toRemove);
						this.server.getClients().remove(toRemove);
						this.running = false;
					}
				}
				//check for objects being sent
			}
			objectOutputToClient.reset();
		}
		catch(IOException | ClassNotFoundException e){
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
	public synchronized void sendGameState(String clientName) {
		//		ReadWriteLock lock = new ReentrantReadWriteLock();
		//		Lock writeLock = lock.writeLock();
		//		writeLock.lock();
		for(Client c : this.server.getClients()){
			if(c.getName().equals(clientName)){
				try {
					//reset
					this.objectOutputToClient.reset();
					this.objectOutputToClient.writeObject("SENDING_UPDATED_STATE");
					this.objectOutputToClient.flush();
					objectOutputToClient.writeObject(this.server.getCurrentGameState());
					objectOutputToClient.flush();
				} catch (IOException e) {
					System.out.println("Error writing board back to client: "+e);
				}
			}
		}
		//		writeLock.unlock();
	}
}