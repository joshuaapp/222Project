package items;

public class Key implements InteractableItem{

	
	private int roomCode;
	private String color;

	public Key(int code, String color){
		this.roomCode = code;
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
