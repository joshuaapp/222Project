package main;

import java.io.IOException;

import control.Client;
import control.Server;
import gameWorld.GameState;

public class JointMain {
	public static void main(String args[]){
		GameState state = new GameState();
		try {
			Server server = new Server(state);
			new Thread(server).start();
			//create a new client
			Client client = new Client();
			//start the client thread
			new Thread(client).start();
			//add shutdown hook to properly close down client and disconnect from server when application is exited
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				public void run() {
					client.shutdown();
					return;
				}
			}, "Shutdown-thread"));

		} catch (IOException e) {
			System.out.println("Error creating server!" +e);
		}
	}
}
