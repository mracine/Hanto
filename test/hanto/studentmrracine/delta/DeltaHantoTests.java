package hanto.studentmrracine.delta;

import static org.junit.Assert.*;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoTestGame;
import hanto.common.HantoTestGameFactory;
import hanto.common.MoveResult;
import hanto.studentmrracine.HantoGameFactory;
import hanto.studentmrracine.common.Butterfly;
import hanto.studentmrracine.common.HantoCoord;
import hanto.studentmrracine.common.HantoPieceFactory;
import hanto.studentmrracine.common.PlayerInventory;
import hanto.studentmrracine.common.Sparrow;

import org.junit.Test;

public class DeltaHantoTests {

	@Test
	public void testCrab(){
		HantoPiece crab = HantoPieceFactory.getInstance().createPiece(
				HantoPieceType.CRAB, HantoPlayerColor.BLUE);
		assertEquals(HantoPlayerColor.BLUE, crab.getColor());
		assertEquals(HantoPieceType.CRAB, crab.getType());
	}

	@Test
	public void testDeltaFactory() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);
		assertEquals(MoveResult.OK, 
				g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 0)));
	}
	
	@Test
	public void testGetPieceAt() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);
		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 0));
		assertEquals(Sparrow.class, g.getPieceAt(new HantoCoord(0, 0)).getClass());
		assertEquals(HantoPlayerColor.BLUE, g.getPieceAt(new HantoCoord(0, 0)).getColor());
		assertEquals(HantoPieceType.SPARROW, g.getPieceAt(new HantoCoord(0, 0)).getType());

		g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 1));
		assertEquals(Butterfly.class, g.getPieceAt(new HantoCoord(0, 1)).getClass());
		assertEquals(HantoPlayerColor.RED, g.getPieceAt(new HantoCoord(0, 1)).getColor());
		assertEquals(HantoPieceType.BUTTERFLY, g.getPieceAt(new HantoCoord(0, 1)).getType());
	}
	
	@Test
	public void testPrintableBoard() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);

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
		
	}

	@Test(expected = HantoException.class)
	public void testIllegalHorseMove() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);

		g.makeMove(HantoPieceType.HORSE, null, new HantoCoord(0, 0));
	}

	@Test(expected = HantoException.class)
	public void testIllegalCraneMove() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);

		g.makeMove(HantoPieceType.CRANE, null, new HantoCoord(0, 0));
	}

	@Test(expected = HantoException.class)
	public void testIllegalDoveMove() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);

		g.makeMove(HantoPieceType.DOVE, null, new HantoCoord(0, 0));
	}

	@Test
	public void testGammaInventoryHorse(){
		PlayerInventory p = new DeltaInventory();
		
		assertFalse(p.horsesInInventory());
		p.placeHorse();
		assertFalse(p.horsesInInventory());
	}

	@Test
	public void testGammaInventoryCrane(){
		PlayerInventory p = new DeltaInventory();
		
		assertFalse(p.cranesInInventory());
		p.placeCrane();
		assertFalse(p.cranesInInventory());
	}

	@Test
	public void testGammaInventoryDove(){
		PlayerInventory p = new DeltaInventory();
		
		assertFalse(p.dovesInInventory());
		p.placeDove();
		assertFalse(p.dovesInInventory());
	}

	@Test
	public void testBlueResignation() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);
		assertEquals(MoveResult.RED_WINS, g.makeMove(null, null, null));
	}
	
	@Test
	public void testRedResignation() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);
		assertEquals(MoveResult.OK, 
				g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 0)));
		assertEquals(MoveResult.BLUE_WINS, g.makeMove(null, null, null));
	}

	@Test(expected = HantoException.class)
	public void testIllegalMoveAfterResignation() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);
		g.makeMove(null, null, null);
		g.makeMove(null, null, null);
	}

	@Test
	public void testSparrowFly() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);
		assertEquals(MoveResult.OK, 
				g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 0)));
		assertEquals(MoveResult.OK, 
				g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 1)));
		assertEquals(MoveResult.OK, 
				g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(-1, 0)));
		assertEquals(MoveResult.OK, 
				g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 2)));		
		assertEquals(MoveResult.OK, 
				g.makeMove(HantoPieceType.SPARROW, new HantoCoord(-1, 0), new HantoCoord(0, 3)));
	}
	
	@Test
	public void testDeltaInventoryButterflies(){
		
		PlayerInventory i = new DeltaInventory();
		
		assertTrue(i.butterfliesInInventory());
		i.placeButterfly();
		assertFalse(i.butterfliesInInventory());
		i.placeButterfly();
		assertFalse(i.butterfliesInInventory());
		
		assertTrue(i.sparrowsInInventory());
		i.placeSparrow();
		assertTrue(i.sparrowsInInventory());
		i.placeSparrow();
		i.placeSparrow();
		i.placeSparrow();
		assertFalse(i.sparrowsInInventory());
		i.placeSparrow();
		assertFalse(i.sparrowsInInventory());
		
		assertTrue(i.crabsInInventory());
		i.placeCrab();
		assertTrue(i.crabsInInventory());
		i.placeCrab();
		i.placeCrab();
		i.placeCrab();
		assertFalse(i.crabsInInventory());
		i.placeCrab();
		assertFalse(i.crabsInInventory());
	}
	
	@Test(expected = HantoException.class)
	public void testIllegalRedFourthTurn() throws HantoException {

		HantoTestGame.PieceLocationPair[] initPieces = new HantoTestGame.PieceLocationPair[]{
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(0, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(0, 1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(1, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(1, 1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(0, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(-1, 2)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new HantoCoord(1, -2))
		};

		HantoTestGame g = 
				HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.DELTA_HANTO);

		g.initializeBoard(initPieces);
		g.setTurnNumber(4);
		g.setPlayerMoving(HantoPlayerColor.RED);

		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 2));
	}
	
	@Test(expected = HantoException.class)
	public void testIllegalBlueFourthTurn() throws HantoException {

		HantoTestGame.PieceLocationPair[] initPieces = new HantoTestGame.PieceLocationPair[]{
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(0, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(0, 1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(1, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(1, 1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(0, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(-1, 2))
		};

		HantoTestGame g = 
				HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.DELTA_HANTO);

		g.initializeBoard(initPieces);
		g.setTurnNumber(4);

		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(1, -2));
	}
	
	@Test
	public void testOKFourthTurn() throws HantoException {

		HantoTestGame.PieceLocationPair[] initPieces = new HantoTestGame.PieceLocationPair[]{
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(0, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(0, 1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(1, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(1, 1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(0, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(-1, 2))
		};

		HantoTestGame g = 
				HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.DELTA_HANTO);

		g.initializeBoard(initPieces);
		g.setTurnNumber(4);

		assertEquals(MoveResult.OK, 
				g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(1, -2)));
	}
}
