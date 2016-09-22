package items;

public class Button implements InteractableItem {

	@Override
	public boolean isRetrieveable() {
		return false;
	}

	@Override
	public boolean isUsable() {
		return true;
	}

}
