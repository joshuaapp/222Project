package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import gameWorld.Board;
import gameWorld.Player;
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
	
	private int[] imageXPositions = {0, 212, 424, 824, 1036, 1248,
			1648, 1860, 2072, 2472};
	private int[] screenXPositions = {0, 388, 100};

	//All image sprites are pre loaded when the canvas launches to 
	//speed it up
	public DungeonCanvas(){
		raisedTile = loadImage("placeholder_tile.png");
		wall = loadImage("placeholder_wall.png");
		item = loadImage("placeholder_item.png");
		empty = loadImage("empty.png");
		brick = loadImage("raised_brick.png");
	}
	
	
	@Override
	public Dimension getPreferredSize() {
	    return new Dimension(600, 600);
	}

	@Override
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0,0,getWidth(),getHeight());
		
		if(player != null){	
			rp.updatePerspective();
			Queue<Tile> tiles = rp.getTilesInSight();
			
			int col = 0;
			int count = 0;
			int spriteSize = 212;
			
			//While there is still a tile in the queue adjust the X position
			//and remove the Tile to get the image. 
			while(!tiles.isEmpty()){		
				if(col != 2){
					spriteSize = 212;
				}
				else{spriteSize = 400;}
				
				Tile tile = tiles.remove();
				String tileImageName = tile.getTileImage();	
				Image tileImage = null;
				
				if(tileImageName.equals("GRASS")){
					tileImage = flat;
				}
				else if(tileImageName.equals("WALL")){
					tileImage = wall;
				}
				else if(tileImageName.equals("RAISED")){
					tileImage = raisedTile;
				}
				else if(tileImageName.equals("EMPTY")){
					tileImage = empty;
				}
				else if(tileImageName.equals("BRICK")){
					tileImage = brick;
				}

				g.drawImage(tileImage, screenXPositions[col], 0,screenXPositions[col] + spriteSize, 600, imageXPositions[count],0,
						imageXPositions[count] + spriteSize, 600, null);
				
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
		}
	}

	
	public void drawMap(Graphics g){
		//Gets the entire board as one arrayList of String
		ArrayList<String> map = player.getBoard().getMiniMap();
		
		//As it's just one long arrayList, needs to know how long each line
		//is going to be to cut it properly
		
		int lineLength = player.getBoard().COLS;
		int xPos = 0;
		int yPos = 0;
		int squareWidth = 5;
		
		for(String s : map){
			
			//Everything other than a wall will be blank so find the wall and 
			//Use fillRect as opposed to drawRect
			if(s.equals("w")){
				g.setColor(Color.ORANGE);
				g.fillRect(xPos * squareWidth, yPos * squareWidth, squareWidth, squareWidth);
			}
			else if(s.equals("p")){
				g.setColor(Color.RED);
				g.fillRect(xPos * squareWidth, yPos * squareWidth, squareWidth, squareWidth);
			}
			else{
				g.setColor(Color.GRAY);
				g.drawRect(xPos * squareWidth, yPos * squareWidth, squareWidth, squareWidth);
			}
			
			//Move along in the x Direction and if at the end of the line then
			//set back to 0 and move down in the y Direction
			xPos++;
			if(xPos == lineLength){
				xPos = 0;
				yPos++;

			}			
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
