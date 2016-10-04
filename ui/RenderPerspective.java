package ui;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

import gameWorld.Board;
import gameWorld.Player;
import tiles.Tile;
import tiles.WallTile;
import gameWorld.Player.Direction;
import gameWorld.Position;


public class RenderPerspective {
	
	private Player player;
	private Board board;
	private Queue<Tile> tilesInSight = new LinkedList<Tile>();
	
	public RenderPerspective(Player p, Board b){
		System.out.println("made");
		player = p;
		board = b;
		updatePerspective();
	}
	
	public void updatePerspective(){
		tilesInSight.clear();
		fillTilesInSight();
	}
	
	private void fillTilesInSight(){
		Direction facing = player.getDirectionFacing();
		Position playerPos = player.getPosition();
		int xPos = playerPos.getX();
		int yPos = playerPos.getY();
		
		if(facing.equals(Direction.North)){
			addTileImageToSight(xPos-1, yPos-2);
			addTileImageToSight(xPos+1, yPos-2);
			addTileImageToSight(xPos, yPos-2);
			addTileImageToSight(xPos-1, yPos-1);
			addTileImageToSight(xPos+1, yPos-1);
			addTileImageToSight(xPos, yPos-1);
			addTileImageToSight(xPos-1, yPos);
			addTileImageToSight(xPos+1, yPos);
			addTileImageToSight(xPos, yPos);
			
		}
		
		if(facing.equals(Direction.South)){
			addTileImageToSight(xPos+1, yPos+2);
			addTileImageToSight(xPos-1, yPos+2);
			addTileImageToSight(xPos, yPos+2);
			addTileImageToSight(xPos+1, yPos+1);
			addTileImageToSight(xPos-1, yPos+1);
			addTileImageToSight(xPos, yPos+1);
			addTileImageToSight(xPos+1, yPos);
			addTileImageToSight(xPos-1, yPos);
			addTileImageToSight(xPos, yPos);
			
		}
		
		if(facing.equals(Direction.East)){
			addTileImageToSight(xPos+2, yPos-1);
			addTileImageToSight(xPos+2, yPos+1);
			addTileImageToSight(xPos+2, yPos);
			addTileImageToSight(xPos+1, yPos-1);
			addTileImageToSight(xPos+1, yPos+1);
			addTileImageToSight(xPos+1, yPos);
			addTileImageToSight(xPos, yPos-1);
			addTileImageToSight(xPos, yPos+1);
			addTileImageToSight(xPos, yPos);
			
		}
		
		if(facing.equals(Direction.West)){
			addTileImageToSight(xPos-2, yPos+1);
			addTileImageToSight(xPos-2, yPos-1);
			addTileImageToSight(xPos-2, yPos);
			addTileImageToSight(xPos-1, yPos+1);
			addTileImageToSight(xPos-1, yPos-1);
			addTileImageToSight(xPos-1, yPos);
			addTileImageToSight(xPos, yPos+1);
			addTileImageToSight(xPos, yPos-1);
			addTileImageToSight(xPos, yPos);
			
		}
		
	}
	
	 private void addTileImageToSight(int x, int y){
		 
		 Tile t = null;
		 
		 //Checks to see if the tile is on the board
		 if(x < board.ROWS && x >= 0){
			 if(y < board.COLS && y >= 0){
				 t = board.getTile(y, x);
			 }
		 }
		 
		 //If not then creates a transparent wall tile 
		 if(t == null){
			t = new WallTile(x,y);
			t.setTileImage("empty.png");
		 }
		 
		 //Finally adds the tile to the queue
		 tilesInSight.add(t);
		
	}	
	 
	public Queue<Tile> getTilesInSight(){
		return tilesInSight;
	}
}
