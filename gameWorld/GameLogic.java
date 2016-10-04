package gameWorld;

import gameWorld.GameState.direction;
import gameWorld.Player.Direction;
import tiles.GroundTile;
import tiles.Tile;

public class GameLogic {
	GameState game;
	public GameLogic(GameState game){
		game = new GameState();
	}

	//If an up or down key has been pressed the player will move
	//If a left or right key is pressed, rotate the users direction facing
	public void rotateOrMove(Player p, String movement){
		switch(movement){
		case "UP": 
			legalPlayerMove(p, p.getDirectionFacing());
		case "DOWN": 
			legalPlayerMove(p, getRightDirection(getRightDirection(p.getDirectionFacing())));
		case "LEFT": 
			p.setDirectionFacing(getRightDirection(getRightDirection(getRightDirection(p.getDirectionFacing()))));
		case "RIGHT": 
			p.setDirectionFacing(getRightDirection(p.getDirectionFacing()));
		}
		p.getRenderPerspective().updatePerspective();
	}

	public void legalPlayerMove(Player player, Direction facing){
		switch(facing){
		case North: 
			if(game.curMap.getBoard()[player.Xcoord][player.Ycoord-1] instanceof GroundTile){
				actuallyMove(player, facing);
			};
		case South: 
			if(game.curMap.getBoard()[player.Xcoord][player.Ycoord+1] instanceof GroundTile){
				actuallyMove(player, facing);
			};
		case East: 
			if(game.curMap.getBoard()[player.Xcoord+1][player.Ycoord] instanceof GroundTile){
				actuallyMove(player, facing);
			};
		case West: 
			if(game.curMap.getBoard()[player.Xcoord-1][player.Ycoord] instanceof GroundTile){
				actuallyMove(player, facing);
			};
		default:;
		}
	}
	
	public void actuallyMove(Player p, Direction facing){
		switch(facing){
		case North: p.Ycoord = p.Ycoord -1;
		case South: p.Ycoord = p.Ycoord +1;
		default:
			break;
		}
	}

	//This rotates the users view to right 90 degrees
	private Direction getRightDirection(Direction dir){
		if(dir.equals(Direction.North)){return Direction.East;}
		else if(dir.equals(Direction.East)){return Direction.South;}
		else if(dir.equals(Direction.South)){return Direction.West;}
		else{return Direction.North;}
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
