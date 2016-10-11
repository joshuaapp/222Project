package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import gameWorld.GameLogic;
import gameWorld.GameState;
import gameWorld.Player;
import ui.RenderPerspective;

public class GameLogicTest {

	@Test
	public void testLowerHP() throws Exception {
		GameState game = new GameState();
		game.run();
		GameLogic logic = game.logic;
		Player p = new Player(game.getGameBoard(), "player1");
		int pos = game.findAvailableSpaceInCurrentPlayers();
		game.curPlayers[pos] = p;
		int oldHP = p.hp;
		p.setRp(new RenderPerspective(p, game.getGameBoard()));
		logic.lowerHP(p);
		assertTrue(p.hp < oldHP);
		//throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testLegalPlayerMove() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testMoveMonsters() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testActuallyMove() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testGetRightDirection() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testPickUp() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testDrop() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testIsThereAnItem() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

}
