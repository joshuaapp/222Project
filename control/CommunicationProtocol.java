package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CommunicationProtocol{

	private Socket client;
	private Server server;
	private BufferedReader inputFromClient;
	private PrintWriter outputToClient;

	public CommunicationProtocol(){
//		this.client = client;
//		this.server = server;
//		try{
//			this.inputFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
//			this.outputToClient = new PrintWriter(client.getOutputStream());
//		}
//		catch(IOException e){
//			System.out.println("Error in creation of communication protocol: "+e);
//		}
	}

	public String processInput(String input) {
		String output = null;
		System.out.println("Processing input");
		output = "done";
		return output;
	}

//	@Override
//	public void run() {
//		String clientRequest;
//		try{
//			while((clientRequest = inputFromClient.readLine())!=null){
//				System.out.println(clientRequest);
//			}
//		}
//		catch(IOException e){
//			System.out.println("Error trying to communicate between server/client");
//		}
//	}



}
