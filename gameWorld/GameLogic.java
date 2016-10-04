package gameWorld;

import gameWorld.GameState.direction;
import gameWorld.Player.Direction;
import tiles.GroundTile;
import tiles.Tile;

public class GameLogic {
	GameState game;
	public GameLogic(GameState game){
		this.game = game;
	}

	//If an up or down key has been pressed the player will move
	//If a left or right key is pressed, rotate the users direction facing
	public void rotateOrMove(Player p, String movement){
		switch(movement){
		case "UP": 
			legalPlayerMove(p, p.getDirectionFacing());
			break;
		case "DOWN": 
			legalPlayerMove(p, getRightDirection(getRightDirection(p.getDirectionFacing())));
			break;
		case "LEFT": 
			p.setDirectionFacing(getRightDirection(getRightDirection(getRightDirection(p.getDirectionFacing()))));
			break;
		case "RIGHT": 
			p.setDirectionFacing(getRightDirection(p.getDirectionFacing()));
			break;
		}
		p.getRenderPerspective().updatePerspective();
	}

	public void legalPlayerMove(Player player, Direction facing){
		Position playerPos = player.getPosition();
		int playerX = playerPos.getX();
		int playerY = playerPos.getY();
		Board currentBoard = game.curMap;
		switch(facing){
		case North: 
			if(playerY-1 >= 0){
				if(currentBoard.getTile(playerX, playerY-1) instanceof GroundTile){
					actuallyMove(player, facing);
				}
			}
			break;
		case South:
			if(playerY+1 < currentBoard.ROWS){
				if(currentBoard.getTile(playerX, playerY+1) instanceof GroundTile){
					actuallyMove(player, facing);
				}
			}
			break;
		case East:
			if(playerX+1 < currentBoard.COLS){
				if(currentBoard.getTile(playerX+1, playerY) instanceof GroundTile){
					actuallyMove(player, facing);
				}
			}
			break;

		case West:
			if(playerX-1 >= 0){
				if(currentBoard.getTile(playerX-1, playerY) instanceof GroundTile){
					actuallyMove(player, facing);
				}
			}
			break;
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
