package gameWorld;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import items.Button;
import items.Chest;
import items.Door;
import items.Item;
import items.Key;
import tiles.DoorTile;
import tiles.EndTile;
import tiles.GroundTile;
import tiles.StartTile;
import tiles.Tile;
import tiles.WallTile;
public class LevelParser {

	/*Strings of imagenames to refer to*/

	//The board is stored as a grid of characters which are used to create  
	//specific tiles which are then stored in a 2D array. This is then used
	//to create the board.

	//to begin with we will have a fixed width and height so we don't have to parse the file for it. 
	private final int COLS = 30;
	private final int ROWS = 20;
	/**
	 * Reads the text file given and parses each character to a tile and places it 
	 * into the board. Once finished, returns the board.
	 * 
	 * @param String
	 * @return Board
	 */
	public Board buildBoard(String boardFile){	
		try {
			//Makes a reader
			BufferedReader br = new BufferedReader(new FileReader(boardFile));
			//Splits line into characters
			char[] line = br.readLine().toCharArray();
			Tile[][] board = new Tile[ROWS][COLS];
			//int y is used to store the info for which line is in use
			// this is mainly used for assigning the tile to board
			int row = 0;
			while(line != null){
				//Iterates through the line and creates a tile for each character
				for(int col = 0 ; col < line.length; col++){
					Tile t = parseTile(line[col], col, row);
					board[row][col] = t;
				}
				//Moves to the nest line in the text file
				String s = br.readLine();
				if(s != null){
					row++;
					line = s.toCharArray();
				}
				else{line = null;}

			}
			br.close();
			return new Board(board);

		} catch (IOException e) {
			throw new RuntimeException("file reading failed."+e);
		}


	}

	/**
	 * Parses the tile from the character given
	 * 
	 * @param Character
	 * @param int
	 * @param int
	 * @return Tile
	 */
	private Tile parseTile(Character c, int xPos, int yPos){

		//W represents a wall
		if(c == 'W'){
			return new WallTile("WALL");
		}
		//B represents a wall
		if(c == 'B'){
			return new WallTile("WALL");
		}
		//'G' represents grassy ground tile
		else if(c == 'G'){
			return new GroundTile("GRASS");
		}
		//D represents a door
		else if(c == 'D'){
			//return new DoorTile(xPos, yPos);
			return new GroundTile("FLOOR"); //for now until door img is done
		}
		//F represents a raised ground tile (non-grassy)
		else if(c == 'R'){
			return new GroundTile("BRICK");
		}
		
		//* represents a starting tile
		else if(c == 'S'){
			//return new StartTile(xPos, yPos);
			return new StartTile("START", xPos, yPos); //for now until door img is done

		}
		//~ represents an end tile
		else if(c == 'E'){
			//return new EndTile(xPos, yPos);

			return new GroundTile("FLOOR"); //for now until door img is done
		}
		//* represents a fountain ie decrative tile
		else if(c == 'F'){
			return new WallTile("WALL");
		}
		//* represents a pillar ie decrative tile
		else if(c == 'P'){
			return new WallTile("WALL");

		}
		return null;
	}

	/**Parse file containing information about items in a level.
	 * format: type_position, e.g: 
	 * 
	 * @param filename
	 */
	public void parseItemsAndAddToBoard(String filename, Board board){
		try {
			//Makes a reader
			BufferedReader br = new BufferedReader(new FileReader(filename));
			//Splits line into characters
			String[] line = br.readLine().split(" ");
			//int y is used to store the info for which line is in use
			// this is mainly used for assigning the tile to board
			while(line != null){
				Item itemToAddToBoard = parseItem(line[0]);
				Position p = new Position(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
				Tile tileToAddItemTo = board.getTile(p.getY(), p.getX()); //row, col
				tileToAddItemTo.setItem(itemToAddToBoard);
				String s = br.readLine();
				if(s != null){
					line = s.split(" ");
				}
				else break;
			}
			br.close();

		} catch (IOException e) {
			throw new RuntimeException("file reading failed."+e);
		}

	}

	private Item parseItem(String itemName){
		switch(itemName){
		case "chest": 
			return new Chest();
		case "key":
			return new Key();
		case "door":
			return new Door();
		case "button":
			return new Button();
		default: return null;
		}
	}
}