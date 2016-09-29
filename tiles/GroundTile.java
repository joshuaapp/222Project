package tiles;

public class GroundTile implements Tile{
	//type of ground tile it is, e.g GRASS, FLOOR.
	private String type;
	private String image;
	private String item;
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
	public void setItemImage(String i) {
		this.item = i;
	}
	@Override
	public String getItemImage() {
		return this.item;
	}
	@Override public String toString(){
		return " ";
	}

}
