package gameWorld;

import java.awt.Point;
import java.util.ArrayList;

import tiles.PlayerTile;
import tiles.StartTile;
import tiles.Tile;
import tiles.WallTile;

public class Board {

	private Tile[][] templateBoard;
	private Tile[][] gameBoard;
	public final int ROWS = 20;
	public final int COLS = 30;
	
	public Board(Tile[][] newBoard) {
		//board = new Tile[board.length][board[0].length];
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
		return templateBoard[row][col]; //row col
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
	
	public ArrayList<String> getMiniMap(){
		ArrayList<String> map = new ArrayList<String>();
		
		for(int row=0;row<this.ROWS;row++){
			for(int col=0;col<this.COLS;col++){
				Tile t = templateBoard[row][col];
				if(t instanceof WallTile){
					map.add("w");
				}
				else if(t instanceof PlayerTile){
					map.add("p");
				}
				else{
					map.add("_");
				}
			}
		}
		return map;
	}
	
	
	@Override
	public String toString(){
		String toReturn = "";
		for(int row=0;row<this.ROWS;row++){
			for(int col=0;col<this.COLS;col++){
				toReturn+=templateBoard[row][col].toString();
			}
			toReturn+="\n";
		}
		return toReturn;
	}

	public void updatePlayerPos(Player player) {
		int oldRow = -1;
		int oldCol = -1;
		PlayerTile pt = null;
		Position newPos = null;
		outer :
		for(int row=0;row<this.ROWS;row++){
			for(int col=0;col<this.COLS;col++){
				Player p = gameBoard[row][col].getPlayer();
				if(p != null){
					pt = (PlayerTile) gameBoard[row][col];
					if(pt.getPlayer().equals(player)){
						oldRow = row;
						oldCol = col;
						newPos = player.getPosition();
						break outer;						
					}
				}
			}
		}
		if(oldRow >0 && oldCol >0){
			gameBoard[oldRow][oldCol] = templateBoard[oldRow][oldCol];
			int row = newPos.getY();
			int col = newPos.getX();
			gameBoard[row][col] = pt;
		}
	}
}
