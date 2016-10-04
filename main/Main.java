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
		state.attachLogic(new GameLogic(state));
		LevelParser parser = new LevelParser();
		Board b = parser.buildBoard("level1.txt");
		parser.parseItemsAndAddToBoard("level1Items.txt", b);
		state.attatchBoard(b);

		try{
		Server gameServer = new Server(state);
		new Thread(gameServer).start();
		//gameServer.run();
		Player p1 = new Player(b, "Player1");
		Player p2 = new Player(b, "Player2");
		players.add(p1);	
		players.add(p2);
		ArrayList<StartTile> startTiles = b.getStartingTiles();
		if(players.size() < startTiles.size()){
			for(int i=0;i<players.size();i++){
				StartTile t = startTiles.get(i);
				players.get(i).setPosition(t.getStartPosition());
				b.placePlayerOnBoard(players.get(i));
			}
		}
		p1.createRenderPerspective();
		p2.createRenderPerspective();
		Client c = new Client(p1);
		Client c1 = new Client(p2);
		new Thread(c).start();
		new Thread(c1).start();
		gameServer.addClientToConnectedClients(c);
		gameServer.addClientToConnectedClients(c1);
		
		//Player p2 = new Player(10,10,b);
		//Client c2 = new Client(p2);		
		}
		catch(IOException e){
			System.out.println("Error creating server for game: "+e);
		}
		/*SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	GameState gs = new GameState();
            	//Server server = new Server(gs);
            	//server.start();
            	LevelParser lp = new LevelParser();
        		Board b = lp.buildBoard("src/level1.txt");
        		Player player = new Player(5, 5, b);
        		ApplicationWindow appWind = new ApplicationWindow("TEAM 14 SWEN GAME");
        		appWind.createAndShowGUI();
        		appWind.getGameCanvas().setPlayer(player);  		
                
            }
        });*/
		finally{
			
		}
	}
}
