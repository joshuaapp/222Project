package items;

import java.io.Serializable;

public class Crystal implements InteractableItem, Serializable{

	private boolean onEnd = false;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3600155571600030864L;

	@Override
	public String getName() {
		if(onEnd){
			return "CRYSTAL_GLOW";
		}
		return "CRYSTAL";
	}
	
	public void placeOnEnd() {
		onEnd = true;
	}

	@Override
	public boolean isRetrieveable() {
		return true;
	}

	@Override
	public boolean isUsable() {
		return true;
	}

}
