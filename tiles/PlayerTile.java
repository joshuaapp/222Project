package tiles;

import gameWorld.Player;
import items.Item;

public class PlayerTile implements Tile {
	
	
	private Player player;

	public PlayerTile(Player p) {
		this.player = p;
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
	
	@Override 
	public String toString(){
		return "*";
	}

	public Player getPlayer() {
		return this.player;
	}

}
