package gameWorld;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import tiles.DoorTile;
import tiles.GroundTile;
import tiles.PlayerTile;
import tiles.StartTile;
import tiles.Tile;
import tiles.WallTile;
public class Board implements Serializable {
	/**
	 *Board is created in the Parser and initialized with a 2D Array of Tiles,
	 *it maintains both an original version of Game board and a template board
	 */
	private static final long serialVersionUID = -6287684435326608226L;
	private Tile[][] templateBoard;
	private Tile[][] gameBoard;
	public int ROWS;
	public int COLS;

	public Board(Tile[][] newBoard) {

		ROWS = newBoard.length;
		COLS = newBoard[0].length;

		templateBoard = new Tile[ROWS][COLS];
		for(int row=0;row<templateBoard.length; row++){				//Copying values into the tempBoard
			for(int col=0;col<newBoard[0].length; col++){
				this.templateBoard[row][col] = newBoard[row][col];
			}
		}
		gameBoard = new Tile[ROWS][COLS];							//Cloning tempBoard into gameBoard
		for(int i = 0; i < templateBoard.length; i++){
		    gameBoard[i] = templateBoard[i].clone();
		}
	}

	public Tile[][] getBoard(){
		return gameBoard;
	}

	public Tile getTile(int row, int col){
		return gameBoard[row][col]; 
	}
	
	/**
	 * Taking a player as input, x and y coordinates are extracted and translated 
	 * to refer to a position on the gameBoard. That position has the player added to it.
	 * @param p - The player to place on the board
	 */
	public void placePlayerOnBoard(Player p){
		Position playerPos = p.getPosition();
		int row = playerPos.getY();
		int col = playerPos.getX();
		if((row>=0 && row<this.ROWS) && (col>=0 && col<this.COLS)){
			gameBoard[row][col].setPlayer(p);
		}
	}

	/**
	 * Iterates through the templateBoard and finds instances of StartTile, 
	 * which are then added to and array and returned.
	 * 
	 * @return ArrayList - instances of StartTile
	 */
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

	/**
	 * Collects positions that could be monster starting tiles, 
	 * checks if they are an instance of ground tile and not a brick texture.  
	 * @return ArrayList - Start tiles for monsters
	 */
	public ArrayList<Position> getMonsterStartingTiles(){
		ArrayList<Position> toReturn = new ArrayList<>();
		for(int row=0;row<this.ROWS;row++){
			for(int col=0;col<this.COLS;col++){
				Tile cur = templateBoard[row][col];
				if(cur instanceof GroundTile && !cur.getTileImage().equals("BRICK")){
					toReturn.add(new Position(col, row));
				}
			}
		}
		return toReturn;
	}

	/**
	 * Creates and returns our current 'MiniMap', the diagram that appears in the top corner 
	 * of the GUI and shows the players immediate surroundings
	 * @param player - current player
	 * @return ArrayList - The 'MiniMap'
	 */
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

				if(t.getPlayer()!=null && t.getPlayer().isMonster){
					map.add("m");
				}
				//Player
				else if(t.getPlayer()!=null){
					//{Player and Item
					if(t.getItem()!=null){
						map.add("o");
					}
					else{map.add("p");}
				}
				//Item
				else if(t.getItem()!=null){
					map.add("i");
				}
				//Wall
				else if(t instanceof WallTile){
					map.add("w");
				}
				//Door
				else if(t instanceof DoorTile){
					map.add("d");
				}
				//Anything else IN the map
				else{
					map.add("_");
				}
			}
			//Anything else OUT of the map
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