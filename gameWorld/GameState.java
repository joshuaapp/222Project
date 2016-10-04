package gameWorld;


import java.util.ArrayList;

import gameWorld.GameState.direction;
import gameWorld.Player.Direction;
import tiles.GroundTile;

public class GameState {
	private Board currentBoard;
	private int level = 1;
	public ArrayList<Player> curPlayers;
	public enum direction {NORTH, SOUTH, EAST, WEST};
	public GameLogic logic;
	
	public GameState(){
		curPlayers = new ArrayList<Player>();
		//curPlayers.add(new Player(curMap, "Hunter"));
	}
	
	public Board getGameBoard(){
		return this.currentBoard;
	}
	
	public void initMap(){
		LevelParser parser = new LevelParser();
		currentBoard = parser.buildBoard(""+level);
	}
	
	public void attachLogic(GameLogic logic){
		this.logic = logic;
	}
	
	public void run(){
		initMap();
		startingPos();
		//new TEMPRENDER().printBoard(curMap, curPlayers);
		//gamePlay();
	}

	/**
	 * Places players at starting locations
	 */
	public void startingPos() {
		int playerindex = 0;

		for (int y = 0; y < currentBoard.getBoard().length; y++) { // Find an empty start locale and place player on it
			for (int x = 0; x < currentBoard.getBoard()[0].length; x++) {
				if (playerindex < curPlayers.size() && currentBoard.getBoard()[y][x].toString().equals("S")) {
					curPlayers.get(playerindex).Xcoord = y;
					curPlayers.get(playerindex).Ycoord = x;
					playerindex++;
				}
			}
		}
	}

	public void levelUp(){
		level++;
		initMap();
	}
	
	public void updatePlayerPosition(Player p, String d){
		logic.rotateOrMove(p, d);
	}

	public void attatchBoard(Board b){
		this.currentBoard = b;
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
