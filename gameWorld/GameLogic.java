package gameWorld;

import gameWorld.GameState.direction;
import tiles.GroundTile;
import tiles.Tile;

public class GameLogic {
	GameState game;
	public GameLogic(GameState game){
		game = new GameState();
	}
	
	public boolean legalPlayerMove(Player p, direction dir){
		switch(dir){
		case NORTH: 
			if(game.curMap.getBoard()[p.Xcoord][p.Ycoord-1] instanceof GroundTile){
				return true;
			};
		case SOUTH: 
			if(game.curMap.getBoard()[p.Xcoord][p.Ycoord+1] instanceof GroundTile){
				return true;
			};
		case EAST: 
			if(game.curMap.getBoard()[p.Xcoord][p.Xcoord+1] instanceof GroundTile){
				return true;
			};
		case WEST: 
			if(game.curMap.getBoard()[p.Xcoord][p.Xcoord-1] instanceof GroundTile){
			return true;
		};
		default: return false;
		}
	}
	
	//method to return what the player is attempting to interact with
	public Tile interactWith(Player p, direction facing){
		switch(facing){
		case NORTH: 
			return game.curMap.getBoard()[p.Xcoord][p.Ycoord-1];
		case SOUTH: 
			return game.curMap.getBoard()[p.Xcoord][p.Ycoord+1];
		case EAST: 
			return game.curMap.getBoard()[p.Xcoord][p.Xcoord+1];
		case WEST: 
			return game.curMap.getBoard()[p.Xcoord][p.Xcoord-1];
		default: return null;
		}
	}
	
	public void pickUp(Player p){
		//
	}
}
