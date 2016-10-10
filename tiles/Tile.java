package tiles;


import java.io.Serializable;

import gameWorld.Player;
import items.Item;


/*
 * The plan here is to make a tile as simple as possible by having them only store
 * an Item, Player and their own image. The subclasses can change certain fields
 * such as walkable but the way we will show a different tile is when created in
 * the parser eg, if we want a raised brick tile then we will use:
 *  'new GroundTile("BRICK")'.
 */
public abstract class Tile implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -1787271942572195028L;
	private String image;
	private Item item;
	private Boolean walkable = true;
	private Player player = null;

	public Tile(String imageName){
		image = imageName;
	}

	public void setItem(Item i){
		item = i;
	}

	public String getItemImage(){
		if(item != null){
			return this.item.getName();
		}
		else return "";
	}

	public Item getItem(){
		if(item !=null){
			return this.item;
		}
		return null;
	}

	public String getTileImage(){
		return image;
	}

	public boolean isWalkable(){
		return walkable;
	}

	public void setWalkable(Boolean b){
		walkable = b;
	}

	public void setPlayer(Player p){
		player = p;
	}

	public Player getPlayer(){
		return player;
	}

	}
