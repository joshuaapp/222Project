package ui;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

import gameWorld.Board;
import gameWorld.GameState.direction;
import gameWorld.Player;
import tiles.Tile;
import tiles.WallTile;
import gameWorld.Position;


public class RenderPerspective implements Serializable{

	private Player player;
	private Board board;
	private Queue<Tile> tilesInSight = new LinkedList<Tile>();

	public RenderPerspective(Player p, Board b){
		player = p;
		board = b;
		updatePerspective();
	}
	
	/**
	 * Called from other classes to fill the players perspective tiles
	 */
	public void updatePerspective(){
		tilesInSight.clear();
		fillTilesInSight();
	}
	
	private void fillTilesInSight(){
		//Depending on the way the player is facing the tiles must be loaded in
		//a different order. Here, it grabs the direction and position of the player
		direction facing = player.getDirectionFacing();
		Position playerPos = player.getPosition();
		int xPos = playerPos.getX();
		int yPos = playerPos.getY();

		if(facing.equals(direction.NORTH)){
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

		if(facing.equals(direction.SOUTH)){
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

		if(facing.equals(direction.EAST)){
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

		if(facing.equals(direction.WEST)){
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
		 if(x < board.COLS && x >= 0){
			 if(y < board.ROWS && y >= 0){
				 t = board.getTile(y, x);
			 }
		 }

		 //If not then creates a transparent wall tile
		 if(t == null){
			t = new WallTile("EMPTY");
		 }

		 //Finally adds the tile to the queue
		 tilesInSight.add(t);
		
	}	
	 
	/**
	* Returns the queue of all the tiles the player can see
	* @return Queue<Tile> 
	*/ 
	 
	public Queue<Tile> getTilesInSight(){
		return tilesInSight;
	}
	
	

	public void setBoard(Board b){
		board =b;
	}
}
