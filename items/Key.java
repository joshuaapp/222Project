package items;

public class Key implements InteractableItem{

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
	
	

}
