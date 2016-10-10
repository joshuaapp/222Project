package gameWorld;
import java.awt.Point;
import java.util.ArrayList;

import tiles.DoorTile;
import tiles.GroundTile;
import tiles.PlayerTile;
import tiles.StartTile;
import tiles.Tile;
import tiles.WallTile;
public class Board {
	private Tile[][] templateBoard;
	private Tile[][] gameBoard;
	public int ROWS;
	public int COLS;
	
	//Board is reated in the parser and handed a 2D Tile array
	public Board(Tile[][] newBoard) {
		
		ROWS = newBoard.length;
		COLS = newBoard[0].length;
		
		templateBoard = new Tile[ROWS][COLS];
		for(int row=0;row<templateBoard.length; row++){
			for(int col=0;col<newBoard[0].length; col++){
				this.templateBoard[row][col] = newBoard[row][col];
			}
		}
		gameBoard = new Tile[ROWS][COLS];
		for(int i = 0; i < templateBoard.length; i++){
		    gameBoard[i] = templateBoard[i].clone();
		}
	}
	
	public Tile[][] getBoard(){
		return gameBoard;
	}
	
	public Tile getTile(int row, int col){
		return gameBoard[row][col]; //row col
	}
	
	public void placePlayerOnBoard(Player p){
		Position playerPos = p.getPosition();
		int row = playerPos.getY();
		int col = playerPos.getX();
		if((row>=0 && row<this.ROWS) && (col>=0 && col<this.COLS)){
			gameBoard[row][col].setPlayer(p);
		}
	}
	
	public ArrayList<StartTile> getStartingTiles(){
		ArrayList<StartTile> toReturn = new ArrayList<>();
		for(int row=0;row<this.ROWS;row++){
			for(int col=0;col<this.COLS;col++){
				if(templateBoard[row][col] instanceof StartTile){
					toReturn.add((StartTile) templateBoard[row][col]);
				}
			}
			
		}
		return toReturn;
	}
	
	public ArrayList<Position> getMonsterStartingTiles(){
		ArrayList<Position> toReturn = new ArrayList<>();
		for(int row=0;row<this.ROWS;row++){
			for(int col=0;col<this.COLS;col++){
				if(templateBoard[row][col] instanceof GroundTile){
					toReturn.add(new Position(col, row));
				}
			}
		}
		return toReturn;
	}
	
	public ArrayList<String> getMiniMap(Player player){
		
		//'map' contains a list of the tiles around the player.
		ArrayList<String> map = new ArrayList<String>();
		
		//The top left position is set. MapSize is for on either side of the player
		//ie, 5 means 5 squares on either side of the player.
		Position pos = player.getPosition();
		int mapSize = 5;
		int col = pos.getX() - mapSize;
		int row = pos.getY() - mapSize;
		
		//numSquaresInMap counts the square on either side + the player, squared.
		int count = 0;
		int numSquaresInMap = ((mapSize*2) + 1) * ((mapSize*2) + 1);
		
		//Continues until is has drawn every square
		while(count < numSquaresInMap){
			
			//Checks withen bounds. If not the a throw away string is set
			if(col >= 0 && col < COLS && row >= 0 && row < ROWS){
				
				Tile t = getTile(row,col);
				if(t == null){
					System.out.println("It's null jim -------------------");
				}
				
				if(t.getPlayer()!=null && t.getPlayer().isMonster){
					map.add("m");
				}
				else if(t.getPlayer()!=null){
					map.add("p");
				}
				else if(t instanceof WallTile){
					map.add("w");
				}
				else if(t instanceof DoorTile){
					map.add("d");
				}
				else{
					map.add("_");
				}
			}
			else{
				map.add("=");
			}
			
			//Counts up. If the col is at the end of the line then resets
			//it back to the left and moves the row down.
			count++;
			col++;
			if(col == pos.getX() + mapSize+1){
				col = pos.getX() - mapSize;
				row++;
				
			}
		}
		
		return map;
	}
	
	
	@Override
	public String toString(){
		String toReturn = "";
		for(int row=0;row<this.ROWS;row++){
			for(int col=0;col<this.COLS;col++){
				if(gameBoard[row][col].getPlayer()==null){
				toReturn+=gameBoard[row][col].toString();
				}
				else{
					toReturn+="*";
				}
			}
			toReturn+="\n";
		}
		return toReturn;
	}
	public void updatePlayerPos(Player player, Position oldPos) {
		
		//
		gameBoard[oldPos.getY()][oldPos.getX()].setPlayer(null);
		//gameBoard[oldPos.getY()][oldPos.getX()] = templateBoard[oldPos.getY()][oldPos.getX()];
		
		Position newPos = player.getPosition();
		gameBoard[newPos.getY()][newPos.getX()].setPlayer(player);
		gameBoard[newPos.getY()][newPos.getX()] = templateBoard[newPos.getY()][newPos.getX()];
		
	}
}