package tiles;

import items.Item;

public class WallTile extends Tile{

	public WallTile(String imageName) {
		super(imageName);
		super.setWalkable(false);
	}
	
	@Override
	public String toString(){
		return "W";
	}

}
