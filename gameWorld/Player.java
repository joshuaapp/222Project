package gameWorld;




import java.io.Serializable;
import java.util.ArrayList;

import gameWorld.Player.Direction;
import items.Item;
import ui.RenderPerspective;

public class Player implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 7626039719764482671L;
	public int hp = 15;
	private Position playerPosition;

	Direction facing = Direction.North;
	private RenderPerspective rp;
	private Board board;
	String name;
	public ArrayList<Item> inven = new ArrayList<Item>();
	public boolean isMonster = false;

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
	}

	public void updateBoard(Board gameBoard) {
		this.board = gameBoard;
	}


}