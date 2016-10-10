package main;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import control.Client;
import control.Server;
import gameWorld.Player;
import tiles.StartTile;
import gameWorld.Board;
import gameWorld.GameLogic;
import gameWorld.GameState;
import gameWorld.LevelParser;
import ui.ApplicationWindow;
public class Main {
	public static void main(String[] args) {
		ArrayList<Player> players = new ArrayList<>();
		GameState state = new GameState();
		try{
		Server gameServer = new Server(state);
		new Thread(gameServer).start();
		
		Client c = new Client(state.curPlayers.get(0));
		Client c1 = new Client(state.curPlayers.get(1));
		new Thread(c).start();
		new Thread(c1).start();
		gameServer.addClientToConnectedClients(c);
		gameServer.addClientToConnectedClients(c1);		
		}
		catch(IOException e){
			System.out.println("Error creating server for game: "+e);
		}
		finally{	
		}
	}
}