package gameWorld;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import control.Client;
import gameWorld.GameState.direction;
import gameWorld.Player.Direction;
import items.Item;
import tiles.GroundTile;
import tiles.StartTile;

public class GameState implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 8625004722083450882L;
	private Board currentBoard;
	private int level = 2;
	public ArrayList<Player> curPlayers;
	public ArrayList<Player> curMonsters;
	public ArrayList<Client> clients;
	public enum direction {NORTH, SOUTH, EAST, WEST};
	public GameLogic logic;
	private int numPlayers = 0;

	public GameState(){
		curPlayers = new ArrayList<Player>();
		curMonsters = new ArrayList<Player>();
		clients = new ArrayList<Client>();
		run();
	}

	public void run(){
		initMap();
		addMonsters();
	}

	/**
	 * Places players at starting locations
	 */
	public void addPlayer(Client c) {
		numPlayers++;
		Player p1 = new Player(currentBoard, "Player"+numPlayers);
		curPlayers.add(p1);
		StartTile t = currentBoard.getStartingTiles().get(curPlayers.indexOf(p1));
		p1.setPosition(t.getStartPosition());
		currentBoard.placePlayerOnBoard(p1);
		ArrayList<StartTile> startTiles = currentBoard.getStartingTiles();
		c.addPlayer(p1);
	}

	public void addMonsters(){
		Player monster = new Player(currentBoard, "Monster");
		Player monster1 = new Player(currentBoard, "Monster2");
		Player monster2 = new Player(currentBoard, "Monster3");
		monster.isMonster = true;
		monster1.isMonster = true;
		monster2.isMonster = true;
		curMonsters.add(monster);
		curMonsters.add(monster1);
		curMonsters.add(monster2);

		ArrayList<Position> monTiles = currentBoard.getMonsterStartingTiles();

		if(curMonsters.size() <= monTiles.size()){
			for(int i=0;i<curMonsters.size();i++){
				Random rand = new Random();
				int ran2 = rand.nextInt(monTiles.size());
				curMonsters.get(i).setPosition(monTiles.get(ran2));
				currentBoard.placePlayerOnBoard(curMonsters.get(i));
			}
		}
	}

	public Board getGameBoard(){
		return this.currentBoard;
	}

	public void initMap(){
		attachLogic(new GameLogic(this));
		LevelParser parser = new LevelParser();
		currentBoard = parser.buildBoard("level"+getLevel()+".txt");
		parser.parseItemsAndAddToBoard("level"+getLevel()+"Items.txt", currentBoard);
	}

	public void attachLogic(GameLogic logic){
		this.logic = logic;
	}

	public void levelUp(){
		setLevel(getLevel() + 1);
		initMap();
	}

	public void updatePlayerPosition(Player p, String d){
		logic.rotateOrMove(p, d);
	}

	public void attatchBoard(Board b){
		this.currentBoard = b;
	}

	public void updatePlayerAct(Player p, String a, String item) {
		if(a.equals("PICK")){
			logic.isThereAnItem(p);
		}
		else if(a.equals("DROP")){
			logic.drop(p, item);
		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	/**Adds a client to the game state of the server.
	 * Whenever a client is added it must create the player for that client
	 * and add them to the state of the game
	 * @param c
	 */
	public void addClient(Client c) {
		this.clients.add(c);
		addPlayer(c);
	}

	public Player getPlayerOfClient(String name){
		for(Client c : this.clients){
			if(c.getName().equals(name)){
				return c.getPlayer();
			}
		}
		return null;
	}

	public GameLogic getLogic() {
		return this.logic;
	}
}