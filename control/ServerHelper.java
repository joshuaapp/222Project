package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import gameWorld.Player.Direction;

public class ServerHelper implements Runnable{

	private Socket clientSocket;
	private Server server;
	private Client client;
	private BufferedReader inputFromClient;
	private PrintWriter outputToClient;

	public ServerHelper(Server server, Socket clientSocket){
		this.server = server;
		this.clientSocket = clientSocket;
		try{
			inputFromClient = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			outputToClient = new PrintWriter(this.clientSocket.getOutputStream());
		}
		catch(IOException e){
			System.out.println(e);
		}
	}

	@Override
	public synchronized void run() {	
		String clientRequest = null;
		String[] brokenRequest = new String[2];
		try{
			while(true){
				clientRequest = this.inputFromClient.readLine();
				if(clientRequest != null){
					System.out.println(clientRequest);
					//request client id
					brokenRequest = clientRequest.split(" ");				
					if(brokenRequest[0].equals("UP") || brokenRequest[0].equals("DOWN") ||
						brokenRequest[0].equals("LEFT") ||brokenRequest[0].equals("RIGHT")){
						this.server.processClientMovementRequest(brokenRequest[0], brokenRequest[1]);
					}
					else if(brokenRequest[0].equals("PICK") || brokenRequest[0].equals("DROP")){
						this.server.processClientActionRequest(brokenRequest[0], brokenRequest[1]);
					}
				}
			}
		}
		catch(IOException e){
			System.out.println(e);
		}

	}

}
