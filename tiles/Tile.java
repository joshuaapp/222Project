package tiles;

import items.Item;

public interface Tile {
	public void setTileImage(String i);
	public void setItem(Item i);	
	public String getItemImage();
	public String getTileImage();

	}
