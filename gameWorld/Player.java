package gameWorld;

import java.util.ArrayList;
import java.util.List;

import items.Item;

public class Player {
	
	private String name;
	private List<Item> inventory;
	private enum direction {NORTH, SOUTH, EAST, WEST};
	private direction directionFacing = direction.NORTH; //have player facing north initially

	
	public Player(String name){
		this.name = name;
		this.inventory = new ArrayList<Item>();
	}
	
	public void move(String direction){
		
	}
	
	public void pickupItem(Item toPickup){
		
	}
	
	public void dropItem(Item toDrop){
		
	}
	
	public void interactWithItem(Item toInteractWith){
		
	}

}
