package tiles;

import java.io.Serializable;

import items.Item;

public class DoorTile extends Tile implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 3621122224997263845L;

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
