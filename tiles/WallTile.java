package tiles;

public class WallTile implements Tile{
	
	private String image;
	private String item;
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
	public void setItemImage(String i) {
		this.item = i;
	}

	@Override
	public String getItemImage() {
		return this.item;
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
