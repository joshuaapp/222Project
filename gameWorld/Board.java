package gameWorld;

import tiles.Tile;

public class Board {
	
	private Tile[][] board;

	public Board(Tile[][] board) {
		board = new Tile[board.length][board[0].length];
		for(int row=0;row<board.length; row++){
			for(int col=0;col<board[0].length; col++){
				this.board[row][col] = board[row][col];
			}
		}
	}
	

}
