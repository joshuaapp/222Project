package items;

import java.io.Serializable;

public class Crystal implements InteractableItem, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3600155571600030864L;

	@Override
	public String getName() {
		return "CRYSTAL";
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
