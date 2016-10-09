package gameWorld;

import java.io.Serializable;
import java.util.ArrayList;

import tiles.GroundTile;
import tiles.StartTile;
import tiles.Tile;
import tiles.WallTile;

public class Board implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -5079077426270245569L;
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

	public ArrayList<String> getMiniMap(){
		ArrayList<String> map = new ArrayList<String>();

		for(int row=0;row<this.ROWS;row++){
			for(int col=0;col<this.COLS;col++){
				Tile t = gameBoard[row][col];
				if(t.getPlayer()!=null){
					map.add("p");
				}
//				if(t.getPlayer()!=null && t.getPlayer().isMonster){
//					map.add("m");
//				}
				else if(t instanceof WallTile){
					map.add("w");
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