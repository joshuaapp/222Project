package gameWorld;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import gameWorld.GameState.direction;
import items.Chest;
import items.Crystal;
import items.Item;
import items.Key;
import tiles.DoorTile;
import tiles.EndTile;
import tiles.Tile;

/**
 * 
 * @author Hunter Lindsay
 *
 */


public class GameLogic implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5285703342967448744L;
	GameState game;
	int monsterTime = 10;
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

	}

	/**
	 *Lowers the life of a player
	 *@param player to lose health
	 */
	public void lowerHP(Player player){

		player.setHp(player.getHp()-1);
		player.getRenderPerspective().updatePerspective();
		if(player.getHp() <= 0){
			for(Player p: game.curPlayers){
				if(p!=null){
					p.setHp(15);
				}
			}
			game.resetLevel();
		}
	}

	/**
	 *Ensures player is allowed to move in disired way
	 *@param player player to move
	 *@param direction facing
	 */
	public void legalPlayerMove(Player player, direction facing){
		Position playerPos = player.getPosition();
		int playerX = playerPos.getX();
		int playerY = playerPos.getY();
		Board currentBoard = game.getGameBoard();
		if(!player.isMonster()){
			monsterTime--;
		}

		Tile newTile = null;
		switch(facing){
		case NORTH:
			if(playerY-1 >= 0){
				newTile = currentBoard.getTile(playerY-1, playerX);
			}
			break;
		case SOUTH:
			if(playerY+1 < currentBoard.ROWS){
				newTile = currentBoard.getTile(playerY+1, playerX);
			}
			break;
		case EAST:
			if(playerX+1 < currentBoard.COLS){
				newTile = currentBoard.getTile(playerY, playerX+1);
			}
			break;

		case WEST:
			if(playerX-1 >= 0){
				newTile = currentBoard.getTile(playerY, playerX-1);
			}
			break;
		default:;
		}

		if(newTile != null){
			if(newTile.isWalkable() && newTile.getPlayer() == null){
				actuallyMove(player, facing);
			}
			else if(newTile instanceof DoorTile){
				DoorTile doorTile = (DoorTile)newTile;
				ArrayList<Item> inven = player.getInven();
				for(int i = 0; i <inven.size(); i++){
					if(inven.get(i) instanceof Key){
						Key key = (Key)inven.get(i);
						if(key.getCode() == doorTile.getDoorCode()){

							inven.remove(i);

							((DoorTile) newTile).unlock();
							actuallyMove(player, facing);
						}
					}
				}
			}
		}

		if(monsterTime == 0){
			Random rand = new Random();
			monsterTime = rand.nextInt(6);
			moveMonsters();
		}
	}

	/**
	 **Serves as AI for monsters, they choose a random direction and move there after
	 *the player has moved a random amount of steps
	 */
	public void moveMonsters(){
		for(Player m: game.curMonsters){
			Random rand = new Random();
			int rand2 = rand.nextInt(4);
			switch(rand2){
			case 0: m.setDirectionFacing(direction.NORTH); break;
			case 1: m.setDirectionFacing(direction.SOUTH); break;
			case 2: m.setDirectionFacing(direction.EAST); break;
			case 3: m.setDirectionFacing(direction.WEST); break;
			}
			legalPlayerMove(m, m.facing);
		}
	}

	/**
	 *Performs the actual movement of a player
	 *@param player player to move
	 *@param direction facing
	 */
	public void actuallyMove(Player p, direction facing){
		Position pos = p.getPosition();
		int y = pos.getY();
		int x = pos.getX();
		switch(facing){
		case NORTH: pos.setY(y-1); break;
		case SOUTH: pos.setY(y+1);break;
		case EAST: pos.setX(x+1); break;
		case WEST: pos.setX(x-1); break;
		default:
			break;
		}
		Position oldPos = new Position(x,y);
		game.getGameBoard().updatePlayerPos(p, oldPos);
		if(!p.isMonster()){
			for(int i = y-1; i<=y+1; i++){
				for(int j = x-1; j<=x+1; j++){
					if(game.getGameBoard().getTile(i, j).getPlayer() != null){
						Player player = game.getGameBoard().getTile(i, j).getPlayer();
						if(player.isMonster()){
							lowerHP(p);
							return;
						}
					}
				}
			}
		}
	}


	//This rotates the users view to right 90 degrees
	private direction getRightDirection(direction dir){
		if(dir.equals(direction.NORTH)){return direction.EAST;}
		else if(dir.equals(direction.EAST)){return direction.SOUTH;}
		else if(dir.equals(direction.SOUTH)){return direction.WEST;}
		else{return direction.NORTH;}
	}

	/**
	 *An old method that was designed to check what aplayer was trying to interact with
	 *@param player player to move
	 *@param direction facing
	 */
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

	/**
	 *Allows players to pick up
	 *@param player player
	 *@param item item the player is interacting with
	 */
	public void pickUp(Player p, Item item){
		if(item.equals("CRYSTAL")){
			((Crystal) item).removeFromEnd();
		}
		if(p.isGotBag() == true && p.getInven().size() < 5){
			p.getInven().add(item);
		}
		if(item instanceof Chest){
			p.setGotBag(true);
		}


	}

	/**
	 *Takes an item form player inventory  and puts it on the map
	 *@param player player
	 *@param item string of item to be dropped
	 */
	public void drop(Player player, String item){
		Position playerPos = player.getPosition();
		int playerX = playerPos.getX();
		int playerY = playerPos.getY();
		Board currentBoard = game.getGameBoard();
		Tile currentTile = currentBoard.getTile(playerY, playerX);
		if(currentTile.getItem() == null && !(currentTile instanceof EndTile)){ //Check if there is room to put an item down
			for(Item i: player.getInven()){
				if(i.getName().equals(item)){
					currentTile.setItem(i);
					player.getInven().remove(i);
					break;
				}
			}		
		}

		else if(currentTile instanceof EndTile){ //Deals with putting the crystal on the end tile to level up
			if(item.equals("CRYSTAL")){
				for(Item i: player.getInven()){
					if(i.getName().equals("CRYSTAL")){
						((Crystal) i).placeOnEnd();
						currentTile.setItem(i);
						player.getInven().remove(i);						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						game.levelUp();
						break;
					}	
				}

			}
		}



	}

	/**
	 *Checks if there is an item in front of the player
	 *@param player player to move
	 */
	public void isThereAnItem(Player player) {
		Position playerPos = player.getPosition();
		int playerX = playerPos.getX();
		int playerY = playerPos.getY();
		Board currentBoard = game.getGameBoard();
		if(currentBoard.getTile(playerY, playerX).getItem() != null&& player.getInven().size() < 5){
			pickUp(player, currentBoard.getTile(playerY, playerX).getItem());
			if(player.isGotBag()){ //Ensure player has a bag
				if(currentBoard.getTile(playerY, playerX).getItem() instanceof Chest){
					Chest ch = (Chest)currentBoard.getTile(playerY, playerX).getItem();
					ch.open();
					currentBoard.getTile(playerY, playerX).setItemImage("/chest_open.png"); //Update chest image to open
					return;
				}
				currentBoard.getTile(playerY, playerX).setItem(null); //Delete item form map
			}
		}
	}
}