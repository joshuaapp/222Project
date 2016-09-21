package items;

public class Door implements InteractableItem{

	@Override
	public boolean isUsable() {
		return true;
	}

	@Override
	public boolean isRetrieveable() {
		return false;
	}

}
