package tiles;

import items.Item;

public class DoorTile extends Tile{
	
	public DoorTile(String imageName) {
		super(imageName);
	}
	
	public DoorTile(String imageName, boolean state) {
		super(imageName);
		super.setWalkable(state);
	}
	
	public void unlock(){
		super.setWalkable(true);
	}
	
	@Override
	public String toString(){
		return "|";
	}

}
