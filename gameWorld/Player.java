package gameWorld;

import java.io.Serializable;
import java.util.ArrayList;

import gameWorld.Player.Direction;
import items.Item;
import ui.RenderPerspective;

public class Player implements Serializable{
	
	private Position playerPosition;

	Direction facing = Direction.North;
	private transient RenderPerspective rp;
	private Board board;
	String name;
	public transient ArrayList<Item> inven;
	
	int Xcoord;
	int Ycoord;
	
	public enum Direction{
		North,
		South,
		East,
		West;
	}
	

	public Player(Board b, String name){
		this.board=b;
		this.name = name;
	}
	
	public void createRenderPerspective(){
		rp = new RenderPerspective(this, board);		
	}


	public RenderPerspective getRP(){
		return rp;
	}


	public Board getBoard() {
		return this.board;
	}

	public Position getPosition() {
		return this.playerPosition;
	}	
	
	public void setPosition(Position toSet){
		this.playerPosition = toSet;
	}


	public Direction getDirectionFacing() {
		return this.facing;
	}

	public void setDirectionFacing(Direction direction) {
		this.facing = direction;
	}

	public RenderPerspective getRenderPerspective() {
		return this.rp;
		//this.rp.updatePerspective();
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	
}