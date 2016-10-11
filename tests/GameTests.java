package tests;

import static org.junit.Assert.*;


import org.junit.Test;
import gameWorld.Board;
import gameWorld.GameState;
import gameWorld.Player;
import gameWorld.Position;
import items.Chest;
import tiles.Tile;
import gameWorld.GameState.direction;
import gameWorld.LevelParser;

public class GameTests {


	@Test
	public void testPlayerPlacedOnBoard() throws Exception {
		Board b = createBoard();
		Player p = new Player(b, "Player1");
		p.setPosition(new Position(3,3));
		b.placePlayerOnBoard(p);
		assertTrue(b.getTile(3, 3).getPlayer().equals(p));		
	}
	
	@Test
	public void testInteractWith(){
		GameState g = new GameState();
		Board b = createBoard();
		Player p = new Player(b,"p1");
		p.setPosition(new Position(14,10));
		p.setDirectionFacing(direction.EAST);
		b.placePlayerOnBoard(p);
		Tile t = g.getLogic().interactWith(p, direction.EAST);
		assertTrue(t.getItem() instanceof Chest);
		
	}
	
	@Test
	public void testLogicIsThereAnItem(){
		Board b = createBoard();
		Player p = new Player(b,"p1");
		p.setPosition(new Position(15,10));
		b.placePlayerOnBoard(p);
		p.setGotBag(true);
		GameState g = new GameState();
		g.getLogic().isThereAnItem(p);
		assertTrue(p.getInven().size() == 1);
	}
	
	@Test
	public void testDropItem(){
		Board b = createBoard();
		Player p = new Player(b,"p1");
		p.setPosition(new Position(5,15));
		b.placePlayerOnBoard(p);
		p.setGotBag(true);
		GameState g = new GameState();
		g.getLogic().isThereAnItem(p);
		assertEquals(1, p.getInven().size());
		g.getLogic().drop(p, "Key");
		assertEquals(p.getInven().size(), 0);

	}
	
	
	@Test public void testGetAndSetHp(){
		Board b = createBoard();
		Player p = new Player(b,"p1");
		p.setHp(15);
		assertTrue(p.getHp() == 15);
	}

	@Test
	public void testIsThereAnItem() throws Exception {
		Board b = createBoard();
		assertTrue(b.getTile(10, 15).getItem() instanceof Chest);
	}

	@Test
	public void testPlayerHasBoard(){
		Board b = createBoard();
		Player p = new Player(b,"p1");
		assertTrue(p.getBoard().equals(b));
	}
	
	@Test 
	public void testRotate(){
		GameState g = new GameState();
		Board b = createBoard();
		Player p = new Player(b,"p1");
		p.setPosition(new Position(3,3));
		direction facingOriginal = p.getDirectionFacing();
		System.out.println(facingOriginal);
		Position expected = new Position(3,3);
		g.getLogic().rotateOrMove(p, "RIGHT");
		assertEquals(expected.getX(), p.getPosition().getX());
		assertEquals(expected.getY(), p.getPosition().getY());
		assertEquals(direction.EAST, p.getDirectionFacing());
	}
	
	@Test
	public void testLegalPlayerMove(){
		GameState g = new GameState();
		Board b = createBoard();
		Player p = new Player(b,"p1");
		p.setPosition(new Position(3,3));
		Position expected = new Position(3,2);
		g.getLogic().legalPlayerMove(p, direction.NORTH);
		assertTrue(p.getPosition().getX() == expected.getX() && p.getPosition().getY() == expected.getY());
	}
	
	
	
	public Board createBoard(){
		LevelParser parser = new LevelParser();
		Board b = parser.buildBoard("level1.txt");
		parser.parseItemsAndAddToBoard("level1Items.txt", b);
		return b;

	}


}
