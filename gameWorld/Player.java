package gameWorld;



import ui.RenderPerspective;

public class Player {
	
	private Position playerPosition;
	public Direction facing = Direction.North;
	public RenderPerspective rp;
	private Board board;
	String name;
	
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
	
}
