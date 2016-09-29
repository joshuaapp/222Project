package main;
import java.io.IOException;

import javax.swing.SwingUtilities;

import control.Client;
import control.Server;
import gameWorld.Player;
import gameWorld.Board;
import gameWorld.GameState;
import gameWorld.LevelParser;
import ui.ApplicationWindow;

public class Main {

	public static void main(String[] args) {
		GameState state = new GameState();
		LevelParser parser = new LevelParser();
		Board b = parser.buildBoard("src/level1.txt");
		//System.out.println(b.toString());
		//state.ensureStateIsAtBeginning();
		try{
		Server gameServer = new Server(state);
		new Thread(gameServer).start();
		//gameServer.run();
		Player p1 = new Player(5,5,b);
		Client c = new Client(p1);
		new Thread(c).start();
		Player p2 = new Player(8,8,b);
		Client c1 = new Client(p2);
		new Thread(c1).start();
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
	}
}
