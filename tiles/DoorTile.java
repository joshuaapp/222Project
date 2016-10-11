package tiles;

import java.io.Serializable;

import items.Item;


public class DoorTile extends Tile implements Serializable{

	private static final long serialVersionUID = 3621122224997263845L;
	private int doorCode;

	public DoorTile(String imageName) {
		super(imageName);
	}

	public DoorTile(String imageName, int code) {

		super(imageName);
		this.setDoorCode(code);

		super.setWalkable(false);
	}

	public void unlock(){
		super.setWalkable(true);
	}

	@Override
	public String toString(){
		return "|";
	}

	public int getDoorCode() {
		return doorCode;
	}

	public void setDoorCode(int doorCode) {
		this.doorCode = doorCode;
	}

}
