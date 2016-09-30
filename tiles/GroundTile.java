package tiles;

import items.Item;

public class GroundTile implements Tile{
	//type of ground tile it is, e.g GRASS, FLOOR.
	private String type;
	private String image;
	private Item item;
	private int xPos;
	private int yPos;
	
	public GroundTile(String type, int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.type = type;
		if(type.equals("GRASS")){
			this.image = "placeholder_flat.png";
		}
		else{
			this.image = "placeholder_tile.png";
		}
	}
	public String getTileImage(){
		return this.image;
	}
	
	public String getType(){
		return this.type;
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
		return this.item.getName();
	}
	@Override public String toString(){
		return " ";
	}

}
