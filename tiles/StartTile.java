package tiles;

import gameWorld.Position;
import items.Item;

public class StartTile extends Tile{

	private Position pos;
	
	public StartTile(String imageName, int x, int y) {
		super(imageName);
		pos = new Position(x,y);
	}
	
	public Position getStartPosition(){
		return pos;
	}
	

}
