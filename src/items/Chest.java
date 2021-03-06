package items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Chest implements StorageItem, InteractableItem, Serializable{

	private static final long serialVersionUID = -6084076119986289636L;
	private List<Item> contents;
	private boolean isOpen = false;
	
	public Chest(){
		contents = new ArrayList<Item>();
	}

	public Chest(List<Item> contents){
		this.contents = new ArrayList<Item>();
		this.contents.addAll(0, contents);
	}

	@Override
	public boolean isUsable() {
		return true;
	}

	@Override
	public boolean isRetrieveable() {
		return false;
	}

	/**Add an item into the chest. Can be used for populating or if a player wishes to
	 * store an item inside the chest.
	 *
	 * @param itemToAdd - the item to add to the chest
	 */
	public void addItem(Item itemToAdd){

	}

	public Item getItem(Item itemToGet){
		//TODO: fix this to return appropriate result (maybe need to get item from position?)
		return contents.get(0);
	}

	/**Opens chest to see what is inside, returns list of items that are inside
	 *
	 * @return
	 */
	public List<Item> openChest(){
		if(contents.size() > 0){
			return this.contents;
		}
		else return null;
	}

	@Override
	public String getName() {
		if(isOpen){
			return "CHEST_OPEN";
		}
		return "CHEST";
	}
	
	public boolean isOpen(){
		return isOpen;
	}
	
	public void open(){
		isOpen = true;
	}

}
