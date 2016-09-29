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
import gameWorld.Player.Direction;
import gameWorld.Position;


public class RenderPerspective {
	
	private Player player;
	private Board board;
	private Queue<String> tilesInSight = new LinkedList<String>();
	private Queue<String> itemsInSight = new LinkedList<String>();
	
	public RenderPerspective(Player p, Board b){
		player = p;
		board = b;
		updatePerspective();
	}
	
	public void updatePerspective(){
		tilesInSight.clear();
		fillTilesInSight();
	}
	
	private void fillTilesInSight(){
		Direction facing = player.facing;
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
		if(x >= 0 && x < board.ROWS){
			if(y >= 0 && y < board.COLS){
				Tile t = board.getTile(y, x);
				if(t.getTileImage() != null){
					tilesInSight.add(t.getTileImage());
				}
				else{
					tilesInSight.add("empty.png");
				}
				if(t.getItemImage() != null){
					itemsInSight.add(t.getItemImage());
				}
				else{
					itemsInSight.add("empty.png");
				}
			}
			else{tilesInSight.add("empty.png");itemsInSight.add("empty.png");}
		}
		else{tilesInSight.add("empty.png");itemsInSight.add("empty.png");}
	}	

	
	public Queue<String> getTilesInSight(){
		return tilesInSight;
	}
	
	public Queue<String> getItemsInSight(){
		return itemsInSight;
	}
}
