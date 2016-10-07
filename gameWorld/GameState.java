package gameWorld;


import java.util.ArrayList;
import java.util.Random;

import gameWorld.GameState.direction;
import gameWorld.Player.Direction;
import items.Item;
import tiles.GroundTile;
import tiles.StartTile;

public class GameState {
	private Board currentBoard;
	private int level = 2;
	public ArrayList<Player> curPlayers;
	public ArrayList<Player> curMonsters;
	public enum direction {NORTH, SOUTH, EAST, WEST};
	public GameLogic logic;
	
	public GameState(){
		curPlayers = new ArrayList<Player>();
		curMonsters = new ArrayList<Player>();
		run();
	}
	
	public void run(){
		initMap();
		addPlayers();
		addMonsters();
	}
	
	/**
	 * Places players at starting locations
	 */
	public void addPlayers() {
		Player p1 = new Player(currentBoard, "Player1");
		Player p2 = new Player(currentBoard, "Player2");
		curPlayers.add(p1);	
		curPlayers.add(p2);
		
		ArrayList<StartTile> startTiles = currentBoard.getStartingTiles();
		if(curPlayers.size() <= startTiles.size()){
			for(int i=0;i<curPlayers.size();i++){
				StartTile t = startTiles.get(i);
				curPlayers.get(i).setPosition(t.getStartPosition());
				currentBoard.placePlayerOnBoard(curPlayers.get(i));
			}
		}
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
	

//	public void move(Player.Direction dir){
//		if(dir.equals(Direction.North)){
//			int oldY = playerPosition.getY();
//			playerPosition.setY(oldY-1);
//		}
//		if(dir.equals(Direction.South)){
//			int oldY = playerPosition.getY();
//			playerPosition.setY(oldY+1);
//		}
//		if(dir.equals(Direction.East)){
//			int oldX = playerPosition.getX();
//			playerPosition.setX(oldX+1);
//		}
//		if(dir.equals(Direction.West)){
//			int oldX = playerPosition.getX();
//			playerPosition.setX(oldX-1);
//		}
//		board.updatePlayerPos(this);
//	}

//	public boolean movePlayer(Player p, direction dir){
//		if(logic.legalPlayerMove(p, dir)){
//			switch(dir){
//			case NORTH: p.Ycoord = p.Ycoord - 1; return true;
//			case SOUTH: p.Ycoord = p.Ycoord + 1; return true;
//			case EAST: p.Xcoord = p.Xcoord + 1; return true;
//			case WEST: p.Xcoord = p.Xcoord - 1; return true;
//			default: return false;
//			}
//		}
//		return false;
//	}
}