package tiles;

import gameWorld.Position;

public class StartTile implements Tile{

	private Position position;
	
	public StartTile(Position p) {
		this.position = p;
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

	public Position getPosition() {
		return this.position;
	}

}
