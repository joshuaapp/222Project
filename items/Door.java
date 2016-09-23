package items;

public class Door implements InteractableItem{
	
	private boolean isLocked;
	private Key associatedKey;
	
	/**Constructor for a locked door, which possibly takes a key to unlock it
	 * 
	 * @param locked
	 */
	public Door(boolean locked, Key associatedKey){ //possibly have string linking door to room/location?
		this.isLocked = locked;
		this.associatedKey = associatedKey;
	}
	
	/**Constructor for an unlocked door
	 * 
	 */
	public Door(){
		
	}
	
	@Override
	public boolean isUsable() {
		return true;
	}

	@Override
	public boolean isRetrieveable() {
		return false;
	}
	
	/**Method to open an unlocked door **/
	public void openDoor(){
		
	}
	
	/**Method to open a door which requires a key**/
	public void openDoor(Key unlockKey){
		
	}

}
