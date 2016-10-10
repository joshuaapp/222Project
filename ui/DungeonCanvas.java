package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import gameWorld.Board;
import gameWorld.Player;
import tiles.DoorTile;
import tiles.StartTile;
import tiles.Tile;
public class DungeonCanvas extends JPanel{
	private static final long serialVersionUID = 1L;
	private Player player;
	private RenderPerspective rp;
	
	private Image flat;
	private Image raisedTile;
	private Image wall;
	private Image item;
	private Image empty;
	private Image brick;
	private Image players;
	private Image monster;
	private Image door;
	public Image back;
	private Image tree;
	
	public int level = 1;
	private final int squareWidth = 10;
	private int[] imageXPositions = {0, 212, 424, 824, 1036, 1248,
			1648, 1860, 2072, 2472};
	private int[] screenXPositions = {0, 388, 100};
	//All image sprites are pre loaded when the canvas launches to 
	//speed it up
	public DungeonCanvas(){
		if(player != null){
			level = player.level;
		}
		raisedTile = loadImage("placeholder_tile.png");
		wall = loadImage("wall"+level+".png");
		//item = loadImage("placeholder_item.png");
		//All items display as keys because i cant figure out how this code works
		item = loadImage("key_item.png");
		empty = loadImage("empty.png");
		brick = loadImage("raised_brick.png");
		flat = loadImage("flat"+level+".png");
		players = loadImage("placeholder_player.png");
		monster = loadImage("monster.png");
		door = loadImage("placeholder_door.png");
		back = loadImage("back"+level+".png");
		tree = loadImage("tree.png");
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
	}
	@Override
	public void paint(Graphics g){
		level = player.level;
		if((player.getBoard().getTile(player.getPosition().getY(), player.getPosition().getX()).getTileImage().equals("BRICK"))
				|| (player.getBoard().getTile(player.getPosition().getY(), player.getPosition().getX()) instanceof StartTile)
				|| (player.getBoard().getTile(player.getPosition().getY(), player.getPosition().getX()) instanceof DoorTile)){
			g.setColor(Color.BLACK);
			g.fillRect(0,0,getWidth(),getHeight());
		}
		else{
			back = back.getScaledInstance(600, 600, 0);
			g.drawImage(back, 0, 0, null);
		}
		if(player != null){	
			rp.updatePerspective();
			Queue<Tile> tiles = rp.getTilesInSight();
			int col = 0;
			int count = 0;
			int spriteSize = 212;
			//While there is still a tile in the queue adjust the X position
			//and remove the Tile to get the image. 
			while(!tiles.isEmpty()){	
				//The screen is split into 3 columns, the two on the left and right
				//are 212 and the one in the center is 400. This is used when snipping
				//the correct sized image off the sprite sheet.
				if(col != 2){
					spriteSize = 212;
				}
				else{spriteSize = 400;}
				Tile tile = tiles.remove();
				Image tileImage = getTileImage(tile.getTileImage());
				Image itemImage = getItemImage(tile.getItemImage());
				//Draws the Tile first 
				g.drawImage(tileImage, screenXPositions[col], 0,screenXPositions[col] + spriteSize, 600, imageXPositions[count],0,
						imageXPositions[count] + spriteSize, 600, null);
				//If there is a player then draws them
				if(tile.getPlayer() != null && !tile.getPlayer().isMonster){
					g.drawImage(players, screenXPositions[col], 0,screenXPositions[col] + spriteSize, 600, imageXPositions[count],0,
							imageXPositions[count] + spriteSize, 600, null);
				}
				if(tile.getPlayer() != null && tile.getPlayer().isMonster){
					g.drawImage(monster, screenXPositions[col], 0,screenXPositions[col] + spriteSize, 600, imageXPositions[count],0,
							imageXPositions[count] + spriteSize, 600, null);
				}
				//Finally draws any items on top of the player
				if(itemImage != null){
					g.drawImage(item, screenXPositions[col], 0,screenXPositions[col] + spriteSize, 600, imageXPositions[count],0,
							imageXPositions[count] + spriteSize, 600, null);
				}
				col++;
				count++;
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
	private Image getTileImage(String tileImageName){
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
		else{
			return flat;
		}
	}
	private Image getItemImage(String tileImageName){
		if(!tileImageName.equals("")){
			return item;
		}
		else{
			return null;
		}
	}
	public void healthBar(Graphics g){
		Color cur;
		int health = player.hp;
		int bar = 0;
		int xPos = 215;
		int yPos = 5;
		int squareWidth = 25;
		int squareHeight = 15;
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
			//Everything other than a wall will be blank so find the wall and 
			//Use fillRect as opposed to drawRect
			if(s.equals("w")){
				g.setColor(Color.BLUE);
				g.fillRect(xPos * squareWidth, yPos * squareWidth, squareWidth, squareWidth);
			}
			else if(s.equals("p")){
				g.setColor(Color.GREEN);
				g.fillRect(xPos * squareWidth, yPos * squareWidth, squareWidth, squareWidth);
				
			}
			else if(s.equals("m")){
				g.setColor(Color.RED);
				g.fillRect(xPos * squareWidth, yPos * squareWidth, squareWidth, squareWidth);
			}
			else if(s.equals("_")){
				g.setColor(Color.BLACK);
				g.drawRect(xPos * squareWidth, yPos * squareWidth, squareWidth, squareWidth);
			}
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
		
		switch(player.getDirectionFacing()){
		case North:
			g.fillRect(x, y, squareWidth, recSize);
			break;
		case East:
			g.fillRect((x + squareWidth)-recSize, y, recSize, squareWidth);
			break;
		case South:
			g.fillRect(x, (y + squareWidth)-recSize, squareWidth, recSize);
			break;
		case West:
			g.fillRect(x, y, recSize, squareWidth);
			break;
		}
	}
	public void setPlayer(Player p){
		player= p;
		rp = p.getRP();
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
	public Player getPlayer() {
		return this.player;
	}
}