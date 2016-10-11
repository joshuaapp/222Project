package items;

/**An interactable item is one that can be either pickable, placeable (retrievable) or used in someway. 
 * 
 * @author Joshua
 *
 */
public interface InteractableItem extends Item {
	/**Checks whether an item is retrievable or not. It is retrievable if
	 * it can be picked up and placed down by the player.
	 * 
	 * @return true if player can pickup/drop, false otherwise.
	 */
	public boolean isRetrieveable();
	
	/**Checks whether an item is allowed to be used by the player in order to 
	 * carry out some action. Examples of unusable items are things like furniture.
	 * 
	 * @return true is the player can use an item to perform a task, false otherwise.
	 */
	public boolean isUsable();
}
