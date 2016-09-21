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
	
	

}
