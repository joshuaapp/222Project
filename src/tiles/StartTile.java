package tiles;

import java.io.Serializable;

import gameWorld.Position;
import items.Item;

public class StartTile extends Tile implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 8703805139182400963L;
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
