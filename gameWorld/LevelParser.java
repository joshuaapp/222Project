package gameWorld;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import items.Chest;
import items.Crystal;
import items.Item;
import items.Key;
import tiles.DoorTile;
import tiles.EndTile;
import tiles.GroundTile;
import tiles.StartTile;
import tiles.Tile;
import tiles.WallTile;

public class LevelParser {
	int doorCounter = 0;
	/*Strings of imagenames to refer to*/
	private int keyCounter = 0;


	//The board is stored as a grid of characters which are used to create  
	//specific tiles which are then stored in a 2D array. This is then used
	//to create the board.
	/**
	 * Reads the text file given and parses each character to a tile and places it 
	 * into the board. Once finished, returns the board.
	 * 
	 * @param String
	 * @return Board
	 */
	public Board buildBoard(String boardFile){	
		try {
			//Gets the bounds of the 2D array
			Tile[][] board = getSizedArray(boardFile);

			//Makes a reader
			BufferedReader br = new BufferedReader(new FileReader(boardFile));

			//Splits line into characters
			char[] line = br.readLine().toCharArray();		

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

	private Tile[][] getSizedArray(String boardFile) throws IOException{

		//Makes a reader
		BufferedReader br = new BufferedReader(new FileReader(boardFile));
		//Splits line into characters
		char[] line = br.readLine().toCharArray();		


		int col = line.length;
		int row = 1;

		//Iterates all the way down the file to get the rows
		while(line != null){

			String s = br.readLine();
			if(s != null){
				row++;
				line = s.toCharArray();
			}
			else{line = null;}
		}

		//Returns the correct sized 2D Array
		return new Tile[row][col];
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
		//D represents an UNLOCKED door
		else if(c == 'd'){
			//return new DoorTile(xPos, yPos);
			return new DoorTile("DOOR"); //for now until door img is done
		}
		//D represents LOCKED a door
		else if(c == 'D'){
			//return new DoorTile(xPos, yPos);

			return new DoorTile("DOOR", doorCounter++); //for now until door img is done

		}
		//F represents a raised ground tile (non-grassy)
		else if(c == 'R'){
			return new GroundTile("BRICK");
		}
		//F represents a raised ground tile (non-grassy)
		else if(c == 'T'){
			return new WallTile("TREE");
		}
		//* represents a starting tile
		else if(c == 'S'){
			//return new StartTile(xPos, yPos);
			return new StartTile("START", xPos, yPos); //for now until door img is done
		}
		//~ represents an end tile
		else if(c == 'E'){
			//return new EndTile(xPos, yPos);
			return new EndTile("END"); //for now until door img is done
		}
		//~ represents an pillar tile
		else if(c == 'P'){
			//return new EndTile(xPos, yPos);
			return new WallTile("PILLAR"); //for now until door img is done
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
		case "CHEST": 
			return new Chest();
		case "KEY":
			return new Key(keyCounter++);
		case "CRYSTAL":
			return new Crystal();
		default: return null;
		}
	}
}