package gameWorld;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import control.Client;
import items.Item;
import tiles.StartTile;
import tiles.Tile;
public class GameState implements Serializable{

	private static final long serialVersionUID = 6924348678532121507L;
	private Board currentBoard;
	private int level = 1;
	public ArrayList<Client> curUsers = new ArrayList<Client>();
	public Player[] curPlayers;
	public ArrayList<Player> curMonsters;
	public ArrayList<Client> clients;

	public enum direction {NORTH, SOUTH, EAST, WEST};
	public GameLogic logic;
	private int numPlayers = 0;

	public GameState(){
		attachLogic(new GameLogic(this)); //Create a gamelogic object to update gamestate
		curPlayers = new Player[5];
		curMonsters = new ArrayList<Player>();
		clients = new ArrayList<Client>();
		run();
	}

	public void run(){ //Initiliase gameState with a map and NPCs
		initMap();
		addMonsters();
	}

	/**
	 * Places players at starting locations
	 * @param client the client to add a player too
	 */
	public void addPlayer(Client c) {
		numPlayers++;
		Player p1 = new Player(currentBoard, "Player"+numPlayers);
		int pos = findAvailableSpaceInCurrentPlayers(); //Check the array isnt full
		curPlayers[pos] = p1; //Add this new player into GameStates store of them
		StartTile t = currentBoard.getStartingTiles().get(pos);
		p1.setPosition(t.getStartPosition());
		currentBoard.placePlayerOnBoard(p1);
		c.addPlayer(p1); //Offer client its player object
	}

	/**
	 * Places players at starting locations
	 * @param client the client who is quitting game
	 */
	public void removePlayer(Client c){
		Player toRemove = c.getPlayer();
		Position playerPos = toRemove.getPosition();
		int index = -1;
		Tile t = this.currentBoard.getTile(playerPos.getY(), playerPos.getX());
		t.setPlayer(null);
		for(int i=0; i<curPlayers.length; i++){
			if(curPlayers[i] != null){
				if(curPlayers[i].equals(toRemove)){
					index = i;
					break;
				}
			}
		}
		curPlayers[index] = null;
		numPlayers--;
	}

	/**
	 * Places players at starting locations
	 * @return int number of current players
	 */
	public int findAvailableSpaceInCurrentPlayers(){
		for(int i=0;i<curPlayers.length; i++){
			if(curPlayers[i] == null){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Add NPC monsters to the map
	 */
	public void addMonsters(){
		curMonsters.removeAll(curMonsters);
		Player monster = new Player(currentBoard, "Monster", true);
		Player monster1 = new Player(currentBoard, "Monster1", true);
		Player monster2 = new Player(currentBoard, "Monster2", true);
		Player monster3 = new Player(currentBoard, "Monster3", true);
		curMonsters.add(monster);
		curMonsters.add(monster1);
		curMonsters.add(monster2);
		curMonsters.add(monster3);

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

	/**
	 * Build a map, by parsing through a text file
	 */
	public void initMap(){
		LevelParser parser = new LevelParser();
		currentBoard = parser.buildBoard("src/assets/level"+getLevel()+".txt");
		parser.parseItemsAndAddToBoard("src/assets/level"+getLevel()+"Items.txt", currentBoard);
	}

	public void attachLogic(GameLogic logic){
		this.logic = logic;
	}

	/**
	 * Level up 
	 */
	public void levelUp(){
		setLevel(getLevel() + 1);
		LevelParser parser = new LevelParser();
		currentBoard = parser.buildBoard("src/assets/level"+getLevel()+".txt");
		parser.parseItemsAndAddToBoard("src/assets/level"+getLevel()+"Items.txt", currentBoard);
		levelPushToPlayers();
	}

	public void resetLevel(){
		LevelParser parser = new LevelParser();
		currentBoard = parser.buildBoard("src/assets/level"+getLevel()+".txt");
		parser.parseItemsAndAddToBoard("src/assets/level"+getLevel()+"Items.txt", currentBoard);
		levelPushToPlayers();
	}

	public void levelPushToPlayers(){
		for(Player p: curPlayers){
			if(p != null){
				p.setLevel(level);
				p.setBoard(currentBoard);
			p.setInven(new ArrayList<Item>());
			}
		}
		ArrayList<StartTile> startTiles = currentBoard.getStartingTiles();
		for(int i=0;i<curPlayers.length;i++){
			if(curPlayers[i] != null){
				StartTile t = startTiles.get(i);
				curPlayers[i].setPosition(t.getStartPosition());
				currentBoard.placePlayerOnBoard(curPlayers[i]);
			}
		}
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

	public void setClient(Client c){
		curUsers.add(c);
	}

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