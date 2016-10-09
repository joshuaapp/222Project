package items;

import java.io.Serializable;

public class Key implements InteractableItem, Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 3776014565470932941L;
	private String color;

	public Key(String color){
		this.color = color;
	}

	@Override
	public boolean isUsable() {
		return true;
	}

	@Override
	public boolean isRetrieveable() {
		return true;
	}

	@Override
	public String getName() {
		//return "key";
		return "key_item.png";
	}

	public String getColor(){
		return color;
	}

}
