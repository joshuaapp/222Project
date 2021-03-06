package control;
import java.io.*;
import java.net.Socket;
public class ServerHelper implements Runnable{
	private Socket clientSocket;
	private Server server;
	private ObjectOutputStream objectOutputToClient;
	private ObjectInputStream objectInputFromClient;
	private boolean running;
	
	/**
	 * 
	 * @author Josh Apperley
	 *
	 */
	
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
	
	/**Continuously reads output from the client and carries out the appropriate server side
	 * action. Then if need be, sends back an updated state to update client.
	 * 
	 */
	@Override
	public void run() {
		System.out.println("Server is running!");
		String clientRequest = null;
		String[] brokenRequest = new String[3];
		//get client to add to list of servers clients
		try{
			this.objectOutputToClient.writeObject("GET_CLIENT");
			this.objectOutputToClient.flush();
			while(running){
				Object clientOutput = this.objectInputFromClient.readObject();
				if(clientOutput instanceof String){
					clientRequest = (String) clientOutput;
					brokenRequest = clientRequest.split(" ");
				}
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
						this.objectOutputToClient.writeObject("ACKNOWLEDGED");
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

	/**Sends the updated state back to the client so they can update their window appropriately
	 * @param clientName
	 */
	public synchronized void sendGameState(String clientName) {
		for(Client c : this.server.getClients()){
			if(c.getName().equals(clientName)){
				try {
					//reset so that we can overwrite existing data in the output stream
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
	}
}