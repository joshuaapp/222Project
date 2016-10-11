package main;
import java.io.IOException;
import java.util.ArrayList;
import control.Server;
import gameWorld.GameState;
import gameWorld.Player;

/**Main method which runs a Server for clients to connect to.
 *
 * @author apperljosh
 *
 */
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
	}
}