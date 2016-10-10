package main;

import java.io.IOException;
import java.util.ArrayList;

import control.Server;
import gameWorld.GameState;
import gameWorld.Player;

public class ServerMain {
	public static void main(String[] args) {
		ArrayList<Player> players = new ArrayList<>();
		GameState state = new GameState();
		try {
			Server server = new Server(state);
			new Thread(server).start();
		} catch (IOException e) {
			System.out.println("Error creating server!" +e);
		}

//		try{
//		Server gameServer = new Server(state);
//		new Thread(gameServer).start();
//
//		state.curPlayers.get(0).createRenderPerspective();
//		state.curPlayers.get(1).createRenderPerspective();
//		Client c = new Client(state.curPlayers.get(0));
//		Client c1 = new Client(state.curPlayers.get(1));
//		new Thread(c).start();
//		new Thread(c1).start();
//		gameServer.addClientToConnectedClients(c);
//		gameServer.addClientToConnectedClients(c1);
//		}
//		catch(IOException e){
//			System.out.println("Error creating server for game: "+e);
//		}
//		finally{
//		}
	}
}
