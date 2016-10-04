package tiles;

import items.Item;

public class WallTile implements Tile{

	private String image;
	private Item item;
	private int xPos;
	private int yPos;

	public WallTile(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.image = "placeholder_wall.png";
	}

	@Override
	public void setTileImage(String i) {
		this.image = i;		
	}

	@Override
	public void setItem(Item i) {
		this.item = i;
	}

	@Override
	public String getItemImage() {
		if(item != null){
			return this.item.getName();
		}
		else return "empty.png";
	}

	@Override
	public String getTileImage() {
		return this.image;
	}

	@Override
	public String toString(){
		return "W";
	}

}
