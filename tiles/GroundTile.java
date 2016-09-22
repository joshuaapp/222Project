package tiles;

public class GroundTile extends Tile{
	//type of ground tile it is, e.g GRASS, FLOOR.
	private String type;
	public GroundTile(String type, int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.type = type;
	}
	
	public String getType(){
		return this.type;
	}

}
