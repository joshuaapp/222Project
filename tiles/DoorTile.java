package tiles;

import items.Item;

public class DoorTile implements Tile{

	private int xPos;
	private int yPos;
	
	public DoorTile(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	@Override
	public void setTileImage(String i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setItem(Item i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getItemImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTileImage() {
		// TODO Auto-generated method stub
		return null;
	}

}
