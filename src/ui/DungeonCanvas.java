package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import gameWorld.Player;
import tiles.DoorTile;
import tiles.EndTile;
import tiles.StartTile;
import tiles.Tile;
public class DungeonCanvas extends JPanel{
	private static final long serialVersionUID = 1L;
	private Player player;
	private RenderPerspective rp;
	private Image flat;
	private Image raisedTile;
	private Image wall;
	private Image empty;
	private Image brick;
	private Image players;
	private Image monster;
	private Image door;
	private Image back;
	private Image tree;
	private Image key;
	private Image chest;
	private Image start;
	private Image chestopen;
	private Image fog;
	private Image pillar;
	private Image crystal;
	private Image stars;
	private Image blacktile;
	private Image end;
	private Image crystalglow;

	public int level = 1;
	private final int squareWidth = 10;
	private int[] imageXPositions = {0, 212, 424, 824, 1036, 1248,
			1648, 1860, 2072, 2472};
	private int[] screenXPositions = {0, 388, 100};

	private final int thinSpriteSection = 212;
	private final int wideSpriteSection = 400;
	private double thinSection;
	private double wideSection;
	//All image sprites are pre loaded when the canvas launches to 
	//speed it up

	public DungeonCanvas(){

		if(player != null){
			level = player.level;
		}
		loadImages();
	}
	/**
	 * Loads all images for the current level
	 */

	//In order to speed up the game images are only loaded on 
	//launch and level change. Levels change the images so need
	//to be reloaded when switched.

	public void loadImages(){
		wall = loadImage("src/assets/wall"+level+".png");
		empty = loadImage("src/assets/empty.png");
		brick = loadImage("src/assets/raised_brick.png");
		flat = loadImage("src/assets/longgrass"+level+".png");
		players = loadImage("src/assets/placeholder_player.png");
		monster = loadImage("src/assets/monster"+level+".png");
		door = loadImage("src/assets/placeholder_door.png");
		back = loadImage("src/assets/back"+level+".png");
		tree = loadImage("src/assets/tree.png");
		chest = loadImage("src/assets/chest.png");
		chestopen = loadImage("src/assets/chest_open.png");
		key = loadImage("src/assets/key_item.png");
		start = loadImage("src/assets/start.png");
		fog = loadImage("src/assets/backfog.png");
		pillar = loadImage("src/assets/pillar.png");
		crystal = loadImage("src/assets/crystal.png");
		stars = loadImage("src/assets/stars.png");
		blacktile = loadImage("src/assets/blacktiles.png");
		end = loadImage("src/assets/endtile.png");
		crystalglow = loadImage("src/assets/crystal_glow.png");
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
	}

	private void updateScreenPositions(){
		//The screen is split along the x axis into two thin sections on either
		//side. In the middle is a wider section. Here the section dimensions are
		//found and the position to be drawn to screen is found. NEEDED FOR SCALING.
		
		thinSection = (int) (this.getWidth()*0.3533);
		wideSection = (int) (this.getWidth()*(2.0/3.0));

		screenXPositions[1] = (int) (this.getWidth()-thinSection);
		screenXPositions[2] = (int) (this.getWidth()*0.167);
	}

	
	
	@Override
	public void paint(Graphics g){
		
		drawBackground(g);

		if(player != null){	
			
			//Ensures that the canvas is using the updated images incase the user
			//has changed levels
			if(player.getLevel() != level){
				this.level = player.getLevel();
				loadImages();
			}
			
			//Gets the most recent perspective
			rp.updatePerspective();
			Queue<Tile> tiles = rp.getTilesInSight();

			//Ensures that if the screen has been adjusted the perspectives are right
			updateScreenPositions();

			int col = 0;
			int count = 0;
			int spriteSize = 212;
			int scaleSize;
			//While there is still a tile in the queue adjust the X position
			//and remove the Tile to get the image. 
			while(!tiles.isEmpty()){

				//The screen is split into 3 columns, the two on the left and right
				//are 212 and the one in the center is 400. This is used when snipping
				//the correct sized image off the sprite sheet.

				if(col != 2){
					spriteSize = thinSpriteSection;
					scaleSize = (int) thinSection;
				}
				else{
					spriteSize = wideSpriteSection;
					scaleSize = (int) wideSection;
				}

				//Pop the top tile off and get the Tile and Item images
				Tile tile = tiles.remove();
				Image tileImage = getTileImage(tile.getTileImage());
				Image itemImage = getItemImage(tile.getItemImage());
				
				//Draws the Tile first 
				g.drawImage(tileImage, screenXPositions[col], 0,screenXPositions[col] + scaleSize, getHeight(), imageXPositions[count],0,
						imageXPositions[count] + spriteSize, 600, null);
				//If there is a player then draws them
				if(tile.getPlayer() != null && !tile.getPlayer().isMonster){
					g.drawImage(players, screenXPositions[col], 0,screenXPositions[col] + scaleSize, getHeight(), imageXPositions[count],0,
							imageXPositions[count] + spriteSize, 600, null);
				}
				if(tile.getPlayer() != null && tile.getPlayer().isMonster){
					g.drawImage(monster, screenXPositions[col], 0,screenXPositions[col] + scaleSize, getHeight(), imageXPositions[count],0,
							imageXPositions[count] + spriteSize, 600, null);
				}
				//Finally draws any items on top of the player
				if(itemImage != null){
					g.drawImage(itemImage, screenXPositions[col], 0,screenXPositions[col] + scaleSize, getHeight(), imageXPositions[count],0,
							imageXPositions[count] + spriteSize, 600, null);
				}
				//Move RIGHT and count up to 9
				col++;
				count++;
				
				//If already drawn all three then go back left
				if(col == 3){
					col = 0;
				}
				if(count == 9){
					count = 0;
				}
			}
			drawMap(g);
			healthBar(g);
		}
	}	
	
	private void drawBackground(Graphics g){
		
		//Checks the tile the player is on and draws the background accordingly 
		Tile currentTile = player.getBoard().getTile(player.getPosition().getY(), player.getPosition().getX());
		
		if(currentTile instanceof EndTile){
			stars = stars.getScaledInstance(this.getWidth(), this.getHeight(), 0);
			g.drawImage(stars, 0, 0, null);
		}
		else if(currentTile.getTileImage().equals("BRICK")
				|| currentTile instanceof StartTile
				|| currentTile instanceof DoorTile){
			fog = fog.getScaledInstance(this.getWidth(), this.getHeight(), 0);
			g.drawImage(fog, 0, 0, null);
		}
		else{
			back = back.getScaledInstance(this.getWidth(), this.getHeight(), 0);
			g.drawImage(back, 0, 0, null);
		}
	}

	private Image getTileImage(String tileImageName){
		//Images are pre loaded and stored in fields so when a new tile
		//is used the appropriate image is returned
		if(tileImageName.equals("GRASS")){
			return flat;
		}
		if(tileImageName.equals("TREE")){
			return tree;
		}
		else if(tileImageName.equals("WALL")){
			return wall;
		}
		else if(tileImageName.equals("RAISED")){
			return raisedTile;
		}
		else if(tileImageName.equals("EMPTY")){
			return empty;
		}
		else if(tileImageName.equals("BRICK")){
			return brick;
		}
		else if(tileImageName.equals("DOOR")){
			return door;
		}
		else if(tileImageName.equals("START")){
			return start;
		}
		else if(tileImageName.equals("PILLAR")){
			return pillar;
		}
		else if(tileImageName.equals("END")){
			return end;
		}
		else{
			return flat;
		}
	}

	private Image getItemImage(String itemImageName){
		//Images are pre loaded and stored in fields so when a new image
		//is used the appropriate image is returned
		if(itemImageName.equals("KEY")){
			return key;
		}
		if(itemImageName.equals("CHEST")){
			return chest;
		}
		if(itemImageName.equals("CRYSTAL")){
			return crystal;
		}
		if(itemImageName.equals("CRYSTAL_GLOW")){
			return crystalglow;
		}
		if(itemImageName.equals("CHEST_OPEN")){
			return chestopen;
		}
		return null;
	}

	private void healthBar(Graphics g){
		//Draws the health bar at the top of the screen
		
		Color cur;
		int health = player.hp;
		int bar = 0;
		int xPos = 215;
		int yPos = 5;
		int squareWidth = 25;
		int squareHeight = 15;
		
		//Works it's way up to the current users health bar, changing the colour
		//depending on number
		while(bar < health){
			int R = 255 - (15*health);
			int G = (10*bar) + 50;
			cur = new Color(R, G, 0);
			g.setColor(cur);
			g.fillRect(xPos, yPos, squareWidth, squareHeight);
			g.setColor(Color.white);
			g.drawRect(xPos, yPos, squareWidth, squareHeight);
			bar++;
			xPos = xPos+squareWidth;
		}
	}
	private void drawMap(Graphics g){
		//Gets the entire board as one arrayList of String
		ArrayList<String> map = player.getBoard().getMiniMap(player);
		//As it's just one long arrayList, needs to know how long each line
		//is going to be to cut it properly
		int xPos = 0;
		int yPos = 0;
		int lineLength = 11;

		//Size the miniMap draws as
		for(String s : map){
			//Every character represents a different thing.
			//Wall
			if(s.equals("w")){
				g.setColor(Color.BLUE);
				g.fillRect(xPos * squareWidth, yPos * squareWidth, squareWidth, squareWidth);
			}
			//Player
			else if(s.equals("p")){
				g.setColor(Color.GREEN);
				g.fillRect(xPos * squareWidth, yPos * squareWidth, squareWidth, squareWidth);

			}
			//Item
			else if(s.equals("i")){
				g.setColor(Color.MAGENTA);
				g.fillRect(xPos * squareWidth, yPos * squareWidth, squareWidth, squareWidth);

			}
			//Player standing on an item
			else if(s.equals("o")){
				g.setColor(Color.GREEN);
				g.fillRect(xPos * squareWidth, yPos * squareWidth, squareWidth, squareWidth);
				g.setColor(Color.MAGENTA);
				g.fillRect((xPos*squareWidth)+(squareWidth/5),(yPos*squareWidth)+(squareWidth/5),
						squareWidth - ((squareWidth/5) * 2),squareWidth - ((squareWidth/5) * 2));
			}
			//Monster
			else if(s.equals("m")){
				g.setColor(Color.RED);
				g.fillRect(xPos * squareWidth, yPos * squareWidth, squareWidth, squareWidth);
			}
			//Anything else, used for grid lines
			else if(s.equals("_")){
				g.setColor(Color.BLACK);
				g.drawRect(xPos * squareWidth, yPos * squareWidth, squareWidth, squareWidth);
			}
			//Door
			else if(s.equals("d")){
				g.setColor(Color.orange);
				g.fillRect(xPos * squareWidth, yPos * squareWidth, squareWidth, squareWidth);
			}
			//Anything outside the map
			else if(s.equals("=")){
				g.setColor(Color.DARK_GRAY);
				g.fillRect(xPos * squareWidth, yPos * squareWidth, squareWidth, squareWidth);
			}
			xPos++;
			if(xPos == lineLength){
				xPos = 0;
				yPos++;
			}
		}

		//Passes in the xPos that is half the length + 1 which is usually gonna be 6
		//unless we change the size. 
		drawFacingPlayer(g,(lineLength/2)*squareWidth,(lineLength/2)*squareWidth);

		//Draws the border around the map
		g.setColor(Color.ORANGE);
		g.drawRect(0, 0, lineLength*squareWidth, lineLength*squareWidth);
	}

	private void drawFacingPlayer(Graphics g, int x, int y){

		//Size of the white rec for facing direction
		int recSize = squareWidth/3;
		g.setColor(Color.white);

		//Adjust the placement of the white rec depending on where the player is facing
		switch(player.getDirectionFacing()){
		case NORTH:
			g.fillRect(x, y, squareWidth, recSize);
			break;
		case EAST:
			g.fillRect((x + squareWidth)-recSize, y, recSize, squareWidth);
			break;
		case SOUTH:
			g.fillRect(x, (y + squareWidth)-recSize, squareWidth, recSize);
			break;
		case WEST:
			g.fillRect(x, y, recSize, squareWidth);
			break;
		}
	}
	
	public void setPlayer(Player p){
		player= p;
		rp = p.getRP();
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * Loads an image from a file and returns it
	 * 
	 * @param String
	 * @return Image
	 */
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