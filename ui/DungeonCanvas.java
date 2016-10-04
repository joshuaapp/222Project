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
	
	private int[] imageXPositions = {0, 212, 424, 824, 1036, 1248,
			1648, 1860, 2072, 2472};
	private int[] screenXPositions = {0, 388, 100};

	//All image sprites are pre loaded when the canvas launches to 
	//speed it up
	public DungeonCanvas(){
		flat = loadImage("src/placeholder_flat.png");
		raisedTile = loadImage("src/placeholder_tile.png");
		wall = loadImage("src/placeholder_wall.png");
		item = loadImage("src/placeholder_item.png");
		empty = loadImage("src/empty.png");
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
				
				if(tileImageName.equals("placeholder_flat.png")){
					tileImage = flat;
				}
				else if(tileImageName.equals("placeholder_wall.png")){
					tileImage = wall;
				}
				else if(tileImageName.equals("placeholder_tile.png")){
					tileImage = raisedTile;
				}
				else if(tileImageName.equals("empty.png")){
					tileImage = empty;
				}
				
				//System.out.println("Currently at count: " + count);
				//System.out.println("Currently at x position: " + col);
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
		ArrayList<String> board = player.getBoard().getBoardAsListOfStrings();
		g.setColor(Color.white);
		int y =10;
		g.setFont(new Font("Monospaced", Font.PLAIN, 8));
		for(String s : board){
			g.drawString(s, 10, y);
			y+=10;
		}
		g.drawString(player.getDirectionFacing().name(), 180, 10);
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
