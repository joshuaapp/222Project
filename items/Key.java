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

	public String getImageName() {
		return "key_item.png";
	}

	@Override
	public String getName() {
		return "KEY";
	}

}
