package tiles;

import java.io.Serializable;

import items.Item;

public class DoorTile extends Tile implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -1166679445072491217L;

	public DoorTile(String imageName) {
		super(imageName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString(){
		return "|";
	}

}
