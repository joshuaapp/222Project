package tiles;

import gameWorld.Position;
import items.Item;

public class StartTile extends Tile{

	private Position pos;
	
	/*
	 * Start tiles are different as they store their own position which is needed
	 * on launch to set the players to the tile. 
	 * 
	 */
	public StartTile(String imageName, int x, int y) {
		super(imageName);
		pos = new Position(x,y);
	}
	
	public Position getStartPosition(){
		return pos;
	}
	@Override
	public String toString(){
		return "@";
	}
}
