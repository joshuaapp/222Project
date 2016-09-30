package gameWorld;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import items.Chest;
import items.Item;
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
			return new WallTile(xPos,yPos);
		}
		//'G' represents grassy ground tile
		else if(c == 'G'){
			return new GroundTile("GRASS", xPos,yPos);
		}
		//D represents a door
		else if(c == 'D'){
			//return new DoorTile(xPos, yPos);
			return new GroundTile("FLOOR", xPos,yPos); //for now until door img is done
		}
		//F represents a floor tile (non-grassy)
		else if(c == 'R'){
			return new GroundTile("FLOOR",xPos,yPos);
		}
		//* represents a starting tile
		else if(c == 'S'){
			//return new StartTile(xPos, yPos);
			return new StartTile(new Position(xPos,yPos)); //for now until door img is done
		}
		//~ represents an end tile
		else if(c == 'E'){
			//return new EndTile(xPos, yPos);
			return new GroundTile("FLOOR", xPos,yPos); //for now until door img is done
		}
		//* represents a boundary tile
		else if(c == 'B'){
			return new WallTile(xPos, yPos);
		}
		//* represents a fountain ie decrative tile
		else if(c == 'F'){
			return new WallTile(xPos, yPos);
		}
		//* represents a pillar ie decrative tile
		else if(c == 'P'){
			return new WallTile(xPos, yPos);
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
			Item itemToAddToBoard = parseItem(line[0]);
			Position p = new Position(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
			Tile tileToAddItemTo = board.getTile(p.getY(), p.getX()); //row, col
			tileToAddItemTo.setItem(itemToAddToBoard);
			br.close();

		} catch (IOException e) {
			throw new RuntimeException("file reading failed."+e);
		}

	}

	private Item parseItem(String itemName){
		switch(itemName){
		case "chest": 
			return new Chest();
		default: return null;
		}
	}

		//Reads the text file with all the info on player names, icons and positions.
		//The numPlayers is used to limit how many players are added.
		/**
		 * Returns an ArrayList of players after parsing the specified number of players
		 * from the text file given.
		 * 
		 * @param String
		 * @param int
		 * @return ArrayLice<Player>
		 */
		/*public ArrayList<Player> fillPlayers(String playerFile){	
		try {
			//Makes a reader
			BufferedReader br = new BufferedReader(new FileReader(playerFile));
			String line = br.readLine();
			ArrayList<Player> players = new ArrayList<Player>();

			//iterates from 1 up to the number of players specified
			while(line != null){
				//Creates a scanner for each line
				Scanner scan = new Scanner(line);

				//Adds a player from name, image, x pos and y pos
				players.add(new Player(scan.next(), loadImage(scan.next()), scan.nextInt(), scan.nextInt()));
				line = br.readLine();
				scan.close();
			}
			br.close();
			return players;

		} catch (IOException e) {
			throw new RuntimeException("file reading failed.");
			}


	}*/

		//Reads a file and adds the items to the game level. A card has a specific type
		//that is stored in the card object
		/**
		 * Fills the deck of cards into an ArrayList from the given String
		 * 
		 * @param String
		 * @return ArrayList<Card>
		 */
		/*public ArrayList<Item> fillCards(String file){	
		try {
			//Makes a reader
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			ArrayList<Card> cards = new ArrayList<Card>();

			while(line != null){
				//Scans each line
				Scanner scan = new Scanner(line);
				//Type specifies what type of card it is eg, WEAPON and stores
				//it in the object itself
				String type = scan.next();

				//Following if statements check each card for the type and 
				//add a new Card object based on which type.
				if(type.equals("ROOM")){
					cards.add(new Card(CardType.ROOM, scan.next()));
				}
				else if(type.equals("WEAPON")){
					cards.add(new Card(CardType.WEAPON, scan.next()));
				}
				else if(type.equals("PERSON")){
					cards.add(new Card(CardType.PERSON, scan.next()));
				}
				scan.close();
				line = br.readLine();
			}

			br.close();
			return cards;

		} catch (IOException e) {
			throw new RuntimeException("file reading failed.");
			}
	}*/

		//Fills an array but with only a specific type of card eg, WEAPON.
		//The String input must be in capitals
		/**
		 * Returns an ArrayList of a specific type of card as it parses the String given
		 * looking for only that type
		 * 
		 * @param String
		 * @param String
		 * @return ArrayList<Card>
		 */
		/*public ArrayList<Card> fillCardType(String file, String type){	
		try {
			//Makes a Reader
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			ArrayList<Card> cards = new ArrayList<Card>();

			//Iterates through the entire text file
			while(line != null){
				//Scans each line and finds the type
				Scanner scan = new Scanner(line);
				String cardType = scan.next();

				//If the current line in the text file is of the specified type
				//then it adds it to the array.
				if(type.equals("PERSON")){

					if(cardType.equals(type)){
						cards.add(new Card(CardType.PERSON, scan.next()));
					}
				}
				else if(type.equals("WEAPON")){							
					if(cardType.equals(type)){
						cards.add(new Card(CardType.WEAPON, scan.next()));
					}
				}
				else if(type.equals("ROOM")){

					if(cardType.equals(type)){
						cards.add(new Card(CardType.ROOM, scan.next()));
					}
				}

				scan.close();
				line = br.readLine();
			}

			br.close();
			//Returns an array of only one type of card
			return cards;

		} catch (IOException e) {
			throw new RuntimeException("file reading failed.");
			}
	}
	//Reads a text file of all the coordinates for the doors in the game along
	//with the name of the room associated with that door. Rooms are stored 
	//in the door tile so when a door tile is found it is assigned the name of
	//the room.
	/**
		 * Reads the text file for the coordinates of the doors and finds the Door Tiles
		 * at those locations and adds the Name for those doors to them
		 * 
		 * @param String
		 * @param Board

	public void parseRooms(String roomFile, Board board){			

		try {
			//Makes a reader
			BufferedReader br = new BufferedReader(new FileReader(roomFile));
			String line = br.readLine();
			while(line != null){
				//Creates a scanner
				Scanner scan = new Scanner(line);
				String name = scan.next();

				//There can be multiple doors for one room so iterates all of the coordinates
				//and assigns them all the room name.
				while(scan.hasNext()){
					//Throws away the empty space
					if(scan.hasNext(" ")){
						scan.next();
					}
					int x = scan.nextInt();
					int y = scan.nextInt();

					//Gets the tile at the coordinate
					Tile tile = board.tileAt(x, y);
					if(tile instanceof Door){
						//Checks it is a Door and adds the room name to it
						((Door) tile).addRoomName(name);
					}
				}
				scan.close();
				line = br.readLine();
			}
		br.close();
		} catch (IOException e) {
			throw new RuntimeException("file reading failed.");
			}		
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
}*/
	}