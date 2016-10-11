package items;

import java.io.Serializable;

public class Key implements InteractableItem, Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -1623059651144318193L;

	
	private int roomCode;

	public Key(int code){
		this.roomCode = code;
	} 


	@Override
	public boolean isUsable() {
		return true;
	}

	@Override
	public boolean isRetrieveable() {
		return true;
	}

	public String getImageName() {
		return "key_item.png";
	}
	
	public int getCode(){
		return this.roomCode;
	}

	@Override
	public String getName() {
		return "KEY";
		}
}
