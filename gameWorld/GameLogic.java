package gameWorld;

import java.util.Random;

import gameWorld.GameState.direction;
import gameWorld.Player.Direction;
import items.Item;
import items.Key;
import tiles.GroundTile;
import tiles.Tile;

public class GameLogic {
	GameState game;
	int monsterTime = 10;
	public GameLogic(GameState game){
		this.game = game;
	}
	//NEED to implement
	//monsters
	//random tiles have point bubbles
	//chest gives you a bag
	//collect keys to open doors, certain number of keys lets you open doors
	//maze, to get to the chest and some keys
	//avoid standing near monster


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

	}

	public void lowerHP(Player player){
		player.hp--;
		if(player.hp <= 0){
			//player.isMonster = true;
		}
	}

	public void legalPlayerMove(Player player, Direction facing){
		Position playerPos = player.getPosition();
		int playerX = playerPos.getX();
		int playerY = playerPos.getY();
		Board currentBoard = game.getGameBoard();
		if(!player.isMonster){
			monsterTime--;
		}
		switch(facing){
		case North: 
			if(playerY-1 >= 0){
				if(currentBoard.getTile(playerY-1, playerX).isWalkable()  
						&& currentBoard.getTile(playerY-1, playerX).getPlayer() == null){
					actuallyMove(player, facing);
				}
			}
			break;
		case South:
			if(playerY+1 < currentBoard.ROWS){
				if(currentBoard.getTile(playerY+1, playerX).isWalkable() 
						&& currentBoard.getTile(playerY+1, playerX).getPlayer() == null){
					actuallyMove(player, facing);
				}
			}
			break;
		case East:
			if(playerX+1 < currentBoard.COLS){
				if(currentBoard.getTile(playerY, playerX+1).isWalkable() 
						&& currentBoard.getTile(playerY, playerX+1).getPlayer() == null){
					actuallyMove(player, facing);
				}
			}
			break;

		case West:
			if(playerX-1 >= 0){
				if(currentBoard.getTile(playerY, playerX-1).isWalkable()
						&& currentBoard.getTile(playerY, playerX-1).getPlayer() == null){
					actuallyMove(player, facing);
				}
			}
			break;
		default:;
		}
		if(monsterTime == 0){
			Random rand = new Random();
			monsterTime = rand.nextInt(6);
			moveMonsters();
		}
	}


	public void moveMonsters(){
		for(Player m: game.curMonsters){
			Random rand = new Random();
			int rand2 = rand.nextInt(4);
			switch(rand2){
			case 1: m.setDirectionFacing(Direction.North); break;
			case 2: m.setDirectionFacing(Direction.South); break;
			case 3: m.setDirectionFacing(Direction.East); break;
			case 4: m.setDirectionFacing(Direction.West); break;
			}
			legalPlayerMove(m, m.facing);
		}
	}

	public void actuallyMove(Player p, Direction facing){
		Position pos = p.getPosition();
		int y = pos.getY();
		int x = pos.getX();
		switch(facing){
		case North: pos.setY(y-1); break;
		case South: pos.setY(y+1);break;
		case East: pos.setX(x+1); break;
		case West: pos.setX(x-1); break;
		default:
			break;
		}
		Position oldPos = new Position(x,y);
		game.getGameBoard().updatePlayerPos(p, oldPos);

		if(!p.isMonster){
			for(int i = y-1; i<=y+1; i++){
				for(int j = x-1; j<=x+1; j++){
					try{
						Player monster = game.getGameBoard().getTile(i, j).getPlayer();
						if(monster.isMonster){
							lowerHP(p);
							System.out.println("Player HP: "+p.hp);
							return;
						}
					}catch(NullPointerException e){
						continue;
					}
				}
			}
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
			return game.getGameBoard().getBoard()[p.Xcoord][p.Ycoord-1];
		case SOUTH: 
			return game.getGameBoard().getBoard()[p.Xcoord][p.Ycoord+1];
		case EAST: 
			return game.getGameBoard().getBoard()[p.Xcoord][p.Xcoord+1];
		case WEST: 
			return game.getGameBoard().getBoard()[p.Xcoord][p.Xcoord-1];
		default: return null;
		}
	}

	public void pickUp(Player p, Item item){
		p.inven.add(item);
	}

	public void drop(Player player, String item){
		//need to add code to get an item object based on the name of the object which is currently a string
		Item dropit = new Key("YELLOW");
		switch(item){
		case "Key": dropit = new Key("YELLOW");
		//case "empty": dropit = new empty();
		}
		Position playerPos = player.getPosition();
		int playerX = playerPos.getX();
		int playerY = playerPos.getY();
		Board currentBoard = game.getGameBoard();
		if(currentBoard.getTile(playerY, playerX).getItem() == null){
			currentBoard.getTile(playerY, playerX).setItem(dropit);
			for(Item i: player.inven){

				if(i instanceof Key){
					player.inven.remove(i);
					break;
				}
			}
		}

	}

	public void isThereAnItem(Player player) {
		Position playerPos = player.getPosition();
		int playerX = playerPos.getX();
		int playerY = playerPos.getY();
		Board currentBoard = game.getGameBoard();
		if(currentBoard.getTile(playerY, playerX).getItem() != null){
			pickUp(player, currentBoard.getTile(playerY, playerX).getItem());
			currentBoard.getTile(playerY, playerX).setItem(null);
		}
	}
}