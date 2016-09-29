package gameWorld;

import java.awt.Point;
import java.util.ArrayList;

import tiles.PlayerTile;
import tiles.StartTile;
import tiles.Tile;

public class Board {

	private Tile[][] templateBoard;
	private Tile[][] gameBoard;
	public final int ROWS = 20;
	public final int COLS = 30;
	private String flat = "placeholder_flat.png";
	private String tile = "placeholder_tile.png";
	private String wall = "placeholder_wall.png";
	private String item = "placeholder_item.png";
	
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
	
	public Tile getTile(int x, int y){
		return templateBoard[x][y];
	}
	
	public void placePlayerOnBoard(Player p){
		Position playerPos = p.getPosition();
		int row = playerPos.getY();
		int col = playerPos.getX();
		if((row>0 && row<this.ROWS) && (col>0 && col<this.COLS)){
			gameBoard[row][col] = new PlayerTile(p);
		}
	}
	
	public ArrayList<String> getBoardAsListOfStrings(){
		ArrayList<String> toReturn = new ArrayList<String>();
		String toAdd = "";
		for(int row=0;row<this.ROWS;row++){
			for(int col=0;col<this.COLS;col++){
				toAdd+=gameBoard[row][col].toString();
			}
			toReturn.add(toAdd);
			toAdd = "";
		}
		return toReturn;
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
				if(gameBoard[row][col] instanceof PlayerTile){
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

/*
package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Board {

	


	public Board(){
		fillBoard();
	}

	public void fillBoard(){

		for(int i = 0; i < height; i++){

			for(int j = 0; j < width; j++){
				board[i][j] = new Tile();
				board[i][j].setTileImage(flat);
			}

		}

		board[5][3].setTileImage(tile);
		board[5][3].setItemImage(item);

		board[3][0].setItemImage(item);

		board[4][3].setTileImage(tile);

		for(Tile t : board[0]){
			t.setTileImage(wall);

		}
		board[2][1].setTileImage(wall);
		board[4][1].setTileImage(wall);
		board[2][2].setTileImage(wall);
		board[4][2].setTileImage(wall);
		board[2][3].setTileImage(wall);
	}





	public static Image loadImage(String filename) {
		// using the URL means the image loads when stored
		// in a jar or expanded into individual files.

		try {
			Image img = ImageIO.read(new File(filename));


			return img;
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + filename);
		}
	}

}


 */