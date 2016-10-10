package items;

public class Key implements InteractableItem{
	
	private String name;

	public Key(String name){
		this.name = name;
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
		//return "key";
		return "key_item.png";
	}
	
	public String getName(){
		return name;
	}

}
