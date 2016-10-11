package tiles;

import java.io.Serializable;

public class GroundTile extends Tile implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 3544133279158885605L;

	public GroundTile(String imageName) {
		super(imageName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString(){
		return " ";
	}
}