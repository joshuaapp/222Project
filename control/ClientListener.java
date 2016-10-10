package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
	private transient boolean running;


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
		this.running = true;
	}

	public void tellServerImMoving(String movement){
		this.outputToServer.println(movement+" "+this.client.getName());
		this.client.setLastDirectionMoved(movement);
		this.outputToServer.flush();
		this.lastRequest = "MOVE";
	}

	public void tellServerAction(String action, String item){
		this.outputToServer.println(action+" "+item+" "+this.client.getName());
		this.outputToServer.flush();
		this.lastRequest = "ACTION";
	}

	@Override
	public void run() {
		while(running){
			String input;
			try {
				input = inputFromServer.readLine();
				if(input != null){
					System.out.println(input);
					if(input.equals("GET_CLIENT")){
						outputToServer.println("SENDING_CLIENT");
						outputToServer.flush();
						objectOutputToServer.reset();
						objectOutputToServer.flush();
						objectOutputToServer.writeObject(this.client);
						objectOutputToServer.flush();
						if(client.getGameState() == null){
							outputToServer.println("REQUESTING_STATE "+this.client.getName());
							outputToServer.flush();
						}
					}
					else if(input.equals("SENDING_UPDATED_STATE")){
						try{
							System.out.println("Trying to read object...");
							Object o = objectInputFromServer.readObject();
							System.out.println("Passed object read");
							if(o instanceof GameState){
								GameState state = (GameState) o;
								this.client.setGameState(state);
								if(this.lastRequest != null){
									if(this.lastRequest.equals("MOVE") || this.lastRequest.equals("ACTION")){
										Player p = state.getPlayerOfClient(this.client.getName());
										p.createRenderPerspective();
										client.addPlayer(p);
										client.getApplicationWindow().getGameCanvas().setPlayer(p);
										p.getRP().updatePerspective();
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
			catch(Exception e){
				System.out.println(e);
			}

			//							o = objectInputFromServer.readObject();
			//							System.out.println("Read object: "+o);
			//							if(!(o instanceof GameState)){
			//								System.out.println("Server sent through some buuuu-shiet");
			//								//objectInputFromServer = new ObjectInputStream(clientSocket.getInputStream());
			//								while(input != null){
			//									if(input.equals("SENDING_UPDATED_STATE")){
			//										System.out.println("Wow we got somewhere");
			//										Object o2 = objectInputFromServer.readObject();
			//										if(o2 instanceof GameState){
			//											this.client.setGameState((GameState) o2);
			//											System.out.println("Other gamestate read attempt: "+o2);
			//										}
			//									}
			//									else{
			//										input = inputFromServer.readLine();
			//										outputToServer.println("REQUESTING_STATE "+this.client.getName());
			//										outputToServer.flush();
			//									}
			//									//inputFromServer.close();
			//									//inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//								}
			//							}
			//						}
			//						catch(Exception e){
			//							System.out.println("caught something");
			//							//something is wrong with the streams
			//							//objectInputFromServer.close();
			//							//objectInputFromServer = new ObjectInputStream(clientSocket.getInputStream());
			//							while(input != null){
			//								if(input.equals("SENDING_UPDATED_STATE")){
			//									System.out.println("Wow we got somewhere");
			//									try {
			//										Object o2 = objectInputFromServer.readObject();
			//
			//										if(o2 instanceof GameState){
			//											this.client.setGameState((GameState) o2);
			//											System.out.println("Other gamestate read attempt: "+o2);
			//										}
			//									}
			//									catch (ClassNotFoundException e1) {
			//										// TODO Auto-generated catch block
			//										e1.printStackTrace();
			//									}
			//								}
			//								else{
			//									input = inputFromServer.readLine();
			//									outputToServer.println("REQUESTING_STATE "+this.client.getName());
			//									outputToServer.flush();
			//								}
			//inputFromServer.close();
			//inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//	}
			//
			//
			//							//System.out.println("Exception :"+e);
			//						}
			//						if(o instanceof GameState){
			//							GameState state = (GameState) o;
			//							this.client.setGameState(state);
			//							if(this.lastRequest != null){
			//								if(this.lastRequest.equals("MOVE") || this.lastRequest.equals("ACTION")){
			//									Player p = state.getPlayerOfClient(this.client.getName());
			//									p.createRenderPerspective();
			//									client.addPlayer(p);
			//									client.getApplicationWindow().getGameCanvas().setPlayer(p);
			//									p.getRP().updatePerspective();
			//								}
			//							}
			//						}
			//					}
			//				}
			//				//objectOutputToServer.reset();
			//			} catch (IOException e) {
			//				if(e instanceof StreamCorruptedException){
			//					System.out.println("Stream was corrupted!");
			//
			//				}
			//				else{
			//					e.printStackTrace();
			//				}
			//			}
			//
			//		}


		}
	}

	public void shutdown() throws IOException {
		this.running = false;
		outputToServer.println("DISCONNECTING "+this.client.getName());
		outputToServer.flush();
		this.clientSocket.close();
	}

}
