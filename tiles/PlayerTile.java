package tiles;

import gameWorld.Player;

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
	public void setItemImage(String i) {
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