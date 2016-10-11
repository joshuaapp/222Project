package gameWorld;




import java.io.Serializable;
import java.util.ArrayList;

import gameWorld.GameState.direction;
import items.Item;
import ui.RenderPerspective;

public class Player implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 7626039719764482671L;

	private int hp = 15;
	private Position playerPosition;

	direction facing = direction.NORTH;
	private RenderPerspective rp;
	private int level =1;
	private Board board;
	private boolean gotBag = false;
	String name;
	private ArrayList<Item> inven = new ArrayList<Item>();
	private boolean isMonster = false;

	int Xcoord;
	int Ycoord;

	private boolean notSet = true;



	public Player(Board b, String name){
		this.board=b;
		this.name = name;
	}

	public Player(Board b, String name, boolean isMonster){
		this.board=b;
		this.name = name;
		this.isMonster = isMonster;
	}

	public RenderPerspective getRP(){
		return getRp();
	}


	public Board getBoard() {
		return this.board;
	}

	public Position getPosition() {
		return this.playerPosition;
	}

	public void setPosition(Position toSet){
		this.playerPosition = toSet;
		if(notSet && !isMonster){
			setRp(new RenderPerspective(this, board));
			this.notSet = false;
		}
	}


	public direction getDirectionFacing() {
		return this.facing;
	}

	public void setDirectionFacing(direction direction) {
		this.facing = direction;
	}

	public RenderPerspective getRenderPerspective() {
		return this.getRp();
	}

	public ArrayList<Item> getInven(){
		return inven;
	}

	public int getLevel(){
		return level;
	}

	public void setBoard(Board b){
		System.out.println("Setting board of player "+this+" with renderperspective "+getRp()+" to \n"+b);
		getRp().setBoard(b);
		board = b;
	}

	public RenderPerspective getRp() {
		return rp;
	}

	public void setRp(RenderPerspective rp) {
		this.rp = rp;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public boolean isGotBag() {
		return gotBag;
	}

	public void setGotBag(boolean gotBag) {
		this.gotBag = gotBag;
	}

	public boolean isMonster() {
		return isMonster;
	}

	public void setMonster(boolean isMonster) {
		this.isMonster = isMonster;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setInven(ArrayList<Item> inven) {
		this.inven = inven;
	}




}