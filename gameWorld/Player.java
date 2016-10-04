package gameWorld;



import gameWorld.Player.Direction;
import ui.RenderPerspective;

public class Player {
	
	private Position playerPosition;
	private Direction facing = Direction.North;
	private RenderPerspective rp;
	private Board board;
	
	public enum Direction{
		North,
		South,
		East,
		West;
	}
	
	public Player(Board b){
		this.board=b;
	}
	
	public void createRenderPerspective(){
		rp = new RenderPerspective(this, board);		
	}
	
	public void move(Player.Direction dir){
		if(dir.equals(Direction.North)){
			int oldY = playerPosition.getY();
			playerPosition.setY(oldY-1);
		}
		if(dir.equals(Direction.South)){
			int oldY = playerPosition.getY();
			playerPosition.setY(oldY+1);
		}
		if(dir.equals(Direction.East)){
			int oldX = playerPosition.getX();
			playerPosition.setX(oldX+1);
		}
		if(dir.equals(Direction.West)){
			int oldX = playerPosition.getX();
			playerPosition.setX(oldX-1);
		}
		board.updatePlayerPos(this);
	}
	
	public void parseMove(int move){	
		if(move == 0){
			move(facing);
		}
		if(move == 1){
			facing = getRightDirection(facing);
			
		}
		if(move == 2){
			move(getRightDirection(getRightDirection(facing)));
		}
		if(move == 3){
			facing = getRightDirection(getRightDirection(getRightDirection(facing)));
		}
		rp.updatePerspective();
	}

	public RenderPerspective getRP(){
		return rp;
	}
	
	private Direction getRightDirection(Direction dir){
		if(dir.equals(Direction.North)){return Direction.East;}
		else if(dir.equals(Direction.East)){return Direction.South;}
		else if(dir.equals(Direction.South)){return Direction.West;}
		else{return Direction.North;}
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
	
}
