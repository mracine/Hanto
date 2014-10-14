package hanto.studentmrracine.epsilon;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static org.junit.Assert.*;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoTestGame;
import hanto.common.HantoTestGameFactory;
import hanto.common.HantoTestGame.PieceLocationPair;
import hanto.studentmrracine.HantoGameFactory;
import hanto.studentmrracine.common.BaseHanto;
import hanto.studentmrracine.common.Butterfly;
import hanto.studentmrracine.common.Crab;
import hanto.studentmrracine.common.HantoCoord;
import hanto.studentmrracine.common.Horse;
import hanto.studentmrracine.common.PlayerInventory;
import hanto.studentmrracine.common.Sparrow;
import hanto.studentmrracine.tournament.HantoPlayer;
import hanto.studentmrracine.validation.JumpValidator;
import hanto.studentmrracine.validation.MovesLeftValidator;
import hanto.studentmrracine.validation.PathValidator;
import hanto.tournament.HantoGamePlayer;

import org.junit.Test;

public class EpsilonHantoTests {

	@Test
	public void testDistance5(){
		assertTrue(PathValidator.isWithinDistance(
				5, new HantoCoord(0, 0), new HantoCoord(0, 5)));
	}
	
	@Test
	public void testDistanceFail5(){
		assertFalse(PathValidator.isWithinDistance(
				4, new HantoCoord(0, 0), new HantoCoord(0, 5)));
	}
	
	@Test
	public void testDistance1(){
		assertTrue(PathValidator.isWithinDistance(
				1, new HantoCoord(0, 0), new HantoCoord(0, 1)));
	}
	
	@Test
	public void testDistanceFail1(){
		assertFalse(PathValidator.isWithinDistance(
				0, new HantoCoord(0, 0), new HantoCoord(0, 1)));
	}
	
	@Test
	public void testDistance0(){
		assertTrue(PathValidator.isWithinDistance(
				0, new HantoCoord(0, 0), new HantoCoord(0, 0)));
	}
	
	@Test
	public void testDistanceFail0(){
		assertFalse(PathValidator.isWithinDistance(
				-1, new HantoCoord(0, 0), new HantoCoord(0, 0)));
	}
	
	@Test
	public void testDistance3(){
		assertTrue(PathValidator.isWithinDistance(
				3, new HantoCoord(2, -2), new HantoCoord(0, -3)));
	}
	
	@Test
	public void testDistanceFail3(){
		assertFalse(PathValidator.isWithinDistance(
				2, new HantoCoord(2, -2), new HantoCoord(0, -3)));
	}
	
	@Test
	public void testNoMovesLeft(){
		HantoTestGame g = HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.EPSILON_HANTO);
		
		final PieceLocationPair[] board = new PieceLocationPair[] {
			    plPair(BLUE, BUTTERFLY, 0, 0), 
			    plPair(RED, BUTTERFLY, 0, 1),
			    plPair(RED, SPARROW, 1, -1),
			    plPair(RED, SPARROW, -1, 0)
		};
		
		g.initializeBoard(board);
		g.setPlayerMoving(BLUE);
		g.setTurnNumber(3);
		
		BaseHanto b = (BaseHanto)g;
		
		assertFalse(MovesLeftValidator.hasMovesLeft(b, BLUE, b.getBlueInventory(), b.getBoard()));
	}
	
	@Test
	public void testButterfliesInventory(){
		PlayerInventory i = new EpsilonInventory(BLUE);
		
		assertTrue(i.butterfliesInInventory());
		i.removeFromInventory(new Butterfly(HantoPlayerColor.BLUE));
		assertFalse(i.butterfliesInInventory());
		i.removeFromInventory(new Butterfly(HantoPlayerColor.BLUE));
		assertFalse(i.butterfliesInInventory());
		
		assertTrue(i.sparrowsInInventory());
		i.removeFromInventory(new Sparrow(HantoPlayerColor.BLUE));
		assertTrue(i.sparrowsInInventory());
		i.removeFromInventory(new Sparrow(HantoPlayerColor.BLUE));
		assertFalse(i.sparrowsInInventory());
		i.removeFromInventory(new Sparrow(HantoPlayerColor.BLUE));
		assertFalse(i.sparrowsInInventory());
		
		assertTrue(i.crabsInInventory());
		i.removeFromInventory(new Crab(HantoPlayerColor.BLUE));
		assertTrue(i.crabsInInventory());
		i.removeFromInventory(new Crab(HantoPlayerColor.BLUE));
		i.removeFromInventory(new Crab(HantoPlayerColor.BLUE));
		i.removeFromInventory(new Crab(HantoPlayerColor.BLUE));
		i.removeFromInventory(new Crab(HantoPlayerColor.BLUE));
		i.removeFromInventory(new Crab(HantoPlayerColor.BLUE));
		assertFalse(i.crabsInInventory());
		i.removeFromInventory(new Crab(HantoPlayerColor.BLUE));
		assertFalse(i.crabsInInventory());
		
		assertTrue(i.horsesInInventory());
		i.removeFromInventory(new Horse(HantoPlayerColor.BLUE));
		assertTrue(i.horsesInInventory());
		i.removeFromInventory(new Horse(HantoPlayerColor.BLUE));
		i.removeFromInventory(new Horse(HantoPlayerColor.BLUE));
		i.removeFromInventory(new Horse(HantoPlayerColor.BLUE));
		assertFalse(i.horsesInInventory());
		i.removeFromInventory(new Horse(HantoPlayerColor.BLUE));
		assertFalse(i.horsesInInventory());
	}
	
	@Test
	public void testEpsilonInventoryDoves(){
		PlayerInventory p = new EpsilonInventory(BLUE);
		assertFalse(p.dovesInInventory());
	}
	
	@Test
	public void testPrintableBoard() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.EPSILON_HANTO);

		assertEquals("", g.getPrintableBoard());

		g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 0));
		assertEquals("BLUE B at (0, 0)\n", g.getPrintableBoard());

		g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 1));
		assertEquals("BLUE B at (0, 0)\n"
				+ "RED B at (0, 1)\n", g.getPrintableBoard());
		
		g.makeMove(HantoPieceType.BUTTERFLY, new HantoCoord(0, 0), new HantoCoord(1, 0));
		assertEquals("RED B at (0, 1)\n"
				+ "BLUE B at (1, 0)\n", g.getPrintableBoard());
		
		g.makeMove(HantoPieceType.BUTTERFLY, new HantoCoord(0, 1), new HantoCoord(1, 1));
		assertEquals("BLUE B at (1, 0)\n"
				+ "RED B at (1, 1)\n", g.getPrintableBoard());
		
		g.makeMove(HantoPieceType.CRAB, null, new HantoCoord(0, 0));
		assertEquals("BLUE B at (1, 0)\n"
				+ "RED B at (1, 1)\n"
				+ "BLUE C at (0, 0)\n", g.getPrintableBoard());
		
		g.makeMove(HantoPieceType.HORSE, null, new HantoCoord(0, 2));
		assertEquals("BLUE B at (1, 0)\n"
				+ "RED B at (1, 1)\n"
				+ "BLUE C at (0, 0)\n"
				+ "RED H at (0, 2)\n", g.getPrintableBoard());
		
	}
	
	/**
	 * Factory method to create a piece location pair.
	 * @param player the player color
	 * @param pieceType the piece type
	 * @param x starting location
	 * @param y end location
	 * @return
	 */
	private PieceLocationPair plPair(HantoPlayerColor player, HantoPieceType pieceType, 
			int x, int y)
	{
		return new PieceLocationPair(player, pieceType, new HantoCoord(x, y));
	}
	
	@Test
	public void testHantoPlayer(){
		
		HantoGamePlayer p = new HantoPlayer();
		p.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE, true);
		p.makeMove(null);
	}
}
