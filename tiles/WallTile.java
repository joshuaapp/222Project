package tiles;

import java.io.Serializable;


public class WallTile extends Tile implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 7195002629066785572L;

	public WallTile(String imageName) {
		super(imageName);
		super.setWalkable(false);
	}

	@Override
	public String toString(){
		return "W";
	}

}
