package tiles;

public class EndTile implements Tile{

	private int xPos;
	private int yPos;
	
	public EndTile(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
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

}
