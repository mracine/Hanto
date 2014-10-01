package hanto.studentmrracine.gamma;

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
import hanto.studentmrracine.common.Board;
import hanto.studentmrracine.common.Butterfly;
import hanto.studentmrracine.common.HantoCoord;
import hanto.studentmrracine.common.PlayerInventory;
import hanto.studentmrracine.common.SlideValidator;
import hanto.studentmrracine.common.Sparrow;

import org.junit.Test;

public class GammaHantoTests {

	@Test
	public void testBoardIsCoordinateOccupied() {
		Board b = new GammaBoard();
		b.placePiece(new HantoCoord(0, 0), new Sparrow(HantoPlayerColor.BLUE));
		assertTrue(b.isCoordinateOccupied(0, 0));
		assertTrue(b.isCoordinateOccupied(new HantoCoord(0, 0)));
		assertFalse(b.isCoordinateOccupied(0, 1));
	}

	@Test
	public void testBoardGetPieceAt(){
		Board b = new GammaBoard();
		b.placePiece(new HantoCoord(0, 0), new Sparrow(HantoPlayerColor.BLUE));

		HantoPiece p = b.getPieceAt(new HantoCoord(0, 0));

		assertEquals(HantoPlayerColor.BLUE, p.getColor());
		assertEquals(HantoPieceType.SPARROW, p.getType());
		assertEquals(null, b.getPieceAt(new HantoCoord(0, 1)));
	}

	/**
	 * Since game logic is not contained within this class,
	 * these moves are perfectly legal, and good for testing
	 */
	@Test
	public void testBoardMovePiece(){
		Board b = new GammaBoard();

		b.placePiece(new HantoCoord(0, 0), new Sparrow(HantoPlayerColor.BLUE));

		HantoPiece p = b.getPieceAt(new HantoCoord(0, 0));
		assertEquals(HantoPlayerColor.BLUE, p.getColor());
		assertEquals(HantoPieceType.SPARROW, p.getType());

		b.movePiece(new HantoCoord(0, 0), new HantoCoord(5, 7));

		p = b.getPieceAt(new HantoCoord(5, 7));
		assertEquals(HantoPlayerColor.BLUE, p.getColor());
		assertEquals(HantoPieceType.SPARROW, p.getType());

		p = b.getPieceAt(new HantoCoord(0, 0));
		assertEquals(null, p);
	}

	@Test
	public void testBoardAdjacentSpaceOccupied(){
		Board b = new GammaBoard();

		b.placePiece(new HantoCoord(0, 0), new Sparrow(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(0, 1), new Sparrow(HantoPlayerColor.RED));

		// Place two pieces next to each other
		assertTrue(b.isAnAdjacentSpaceOccupied(new HantoCoord(0, 0)));
		assertTrue(b.isAnAdjacentSpaceOccupied(new HantoCoord(0, 1)));

		// Move piece out of range (not adjacent)
		b.movePiece(new HantoCoord(0, 1), new HantoCoord(0, 2));
		assertFalse(b.isAnAdjacentSpaceOccupied(new HantoCoord(0, 0)));

		// Start to move piece around (0, 0)
		b.movePiece(new HantoCoord(0, 2), new HantoCoord(1, 0));
		assertTrue(b.isAnAdjacentSpaceOccupied(new HantoCoord(0, 0)));

		b.movePiece(new HantoCoord(1, 0), new HantoCoord(1, -1));
		assertTrue(b.isAnAdjacentSpaceOccupied(new HantoCoord(0, 0)));

		b.movePiece(new HantoCoord(1, -1), new HantoCoord(0, -1));
		assertTrue(b.isAnAdjacentSpaceOccupied(new HantoCoord(0, 0)));

		b.movePiece(new HantoCoord(0, -1), new HantoCoord(-1, 0));
		assertTrue(b.isAnAdjacentSpaceOccupied(new HantoCoord(0, 0)));

		b.movePiece(new HantoCoord(-1, 0), new HantoCoord(-1, 1));
		assertTrue(b.isAnAdjacentSpaceOccupied(new HantoCoord(0, 0)));		
	}

	@Test
	public void testBoardIsSurrounded(){
		Board b = new GammaBoard();

		b.placePiece(new HantoCoord(0, 0), new Sparrow(HantoPlayerColor.BLUE));

		assertFalse(b.isSurrounded(new HantoCoord(0, 0)));

		// Surround the piece
		b.placePiece(new HantoCoord(0, 1), new Sparrow(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(1, 0), new Sparrow(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(1, -1), new Sparrow(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(0, -1), new Sparrow(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(-1, 0), new Sparrow(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(-1, 1), new Sparrow(HantoPlayerColor.BLUE));

		assertTrue(b.isSurrounded(new HantoCoord(0, 0)));
	}

	@Test
	public void testBoardSpaceHasOpposingColors(){
		Board b = new GammaBoard();

		// Check different placements around (0, 0)
		b.placePiece(new HantoCoord(0, 0), new Sparrow(HantoPlayerColor.BLUE));		
		assertTrue(b.hasAdjacentOpposingPieces(new HantoCoord(0, 1), HantoPlayerColor.RED));
		assertTrue(b.hasAdjacentOpposingPieces(new HantoCoord(1, 0), HantoPlayerColor.RED));
		assertTrue(b.hasAdjacentOpposingPieces(new HantoCoord(1, -1), HantoPlayerColor.RED));
		assertTrue(b.hasAdjacentOpposingPieces(new HantoCoord(0, -1), HantoPlayerColor.RED));
		assertTrue(b.hasAdjacentOpposingPieces(new HantoCoord(-1, 0), HantoPlayerColor.RED));
		assertTrue(b.hasAdjacentOpposingPieces(new HantoCoord(-1, 1), HantoPlayerColor.RED));

		assertFalse(b.hasAdjacentOpposingPieces(new HantoCoord(0, 1), HantoPlayerColor.BLUE));
		assertFalse(b.hasAdjacentOpposingPieces(new HantoCoord(4, 4), HantoPlayerColor.BLUE));
	}

	@Test
	public void testHantoTestGameFactory() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		assertEquals(MoveResult.OK, 
				g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 0)));
	}

	@Test(expected = HantoException.class)
	public void testNullPieceTypeAndTo() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		g.makeMove(null, null, null);
	}

	@Test(expected = HantoException.class)
	public void testNullPieceType() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		g.makeMove(null, null, new HantoCoord(0, 0));
	}

	@Test(expected = HantoException.class)
	public void testNullTo() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		g.makeMove(HantoPieceType.SPARROW, null, null);
	}

	@Test(expected = HantoException.class)
	public void testIllegalFirstMove() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 1));
	}

	@Test(expected = HantoException.class)
	public void testIllegalSecondMove() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 0));
		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 2));
	}

	@Test(expected = HantoException.class)
	public void testNonNullFromOnFirstMove() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		g.makeMove(HantoPieceType.SPARROW, new HantoCoord(0, 0), new HantoCoord(0, 1));
	}

	/**
	 * This move is illegal since it is a BLUE piece placed
	 * next to a RED one
	 */
	@Test(expected = HantoException.class)
	public void testIllegalSecondTurn() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 0));
		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 1));
		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(1, 0));
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
				HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.GAMMA_HANTO);

		g.initializeBoard(initPieces);
		g.setTurnNumber(4);

		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(1, -2));
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
				HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.GAMMA_HANTO);

		g.initializeBoard(initPieces);
		g.setTurnNumber(4);
		g.setPlayerMoving(HantoPlayerColor.RED);

		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 2));
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
				HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.GAMMA_HANTO);

		g.initializeBoard(initPieces);
		g.setTurnNumber(4);

		assertEquals(MoveResult.OK, 
				g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(1, -2)));
	}

	@Test
	public void testGetPieceAt() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 0));
		assertEquals(Sparrow.class, g.getPieceAt(new HantoCoord(0, 0)).getClass());
		assertEquals(HantoPlayerColor.BLUE, g.getPieceAt(new HantoCoord(0, 0)).getColor());
		assertEquals(HantoPieceType.SPARROW, g.getPieceAt(new HantoCoord(0, 0)).getType());

		g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 1));
		assertEquals(Butterfly.class, g.getPieceAt(new HantoCoord(0, 1)).getClass());
		assertEquals(HantoPlayerColor.RED, g.getPieceAt(new HantoCoord(0, 1)).getColor());
		assertEquals(HantoPieceType.BUTTERFLY, g.getPieceAt(new HantoCoord(0, 1)).getType());
	}

	/**
	 * Tests if the 20 move limit for GAMMA has been reached
	 */
	@Test
	public void testGammaEndGame() throws HantoException {

		HantoTestGame g = 
				HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.GAMMA_HANTO);

		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 0));
		g.setTurnNumber(21);

		assertEquals(MoveResult.DRAW, 
				g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 1)));
	}

	@Test(expected = HantoException.class)
	public void testIllegalPieceTypeMovement() throws HantoException {

		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 0)));
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 1)));
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.BUTTERFLY, new HantoCoord(0, 0), new HantoCoord(1, 0)));		
	}

	@Test
	public void testOKPieceMovement() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 0)));
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 1)));
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.BUTTERFLY, new HantoCoord(0, 0), new HantoCoord(1, 0)));		
	}

	@Test(expected = HantoException.class)
	public void testCloserPieceWalk() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 0)));
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 1)));
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.SPARROW, new HantoCoord(0, 0), new HantoCoord(1, 1)));
	}

	@Test(expected = HantoException.class)
	public void testFartherPieceWalk() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 0)));
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 1)));
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.SPARROW, new HantoCoord(0, 0), new HantoCoord(2, 0)));
	}

	@Test(expected = HantoException.class)
	public void testSneakyPlayer() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 0)));
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 1)));
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.BUTTERFLY, new HantoCoord(0, 1), new HantoCoord(1, 0)));
	}

	@Test(expected = HantoException.class)
	public void testDiscontinuousMove() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 0)));
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 1)));
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(1, -1)));
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(1, 1)));
		assertEquals(MoveResult.OK,
				g.makeMove(HantoPieceType.SPARROW, new HantoCoord(0, 0), new HantoCoord(0, -1)));
	}

	/**
	 * This test defies the rules a little bit to check
	 * the win conditions are properly determined (a butterfly is surrounded)
	 */
	@Test
	public void testRedWin() throws HantoException {
		HantoTestGame.PieceLocationPair[] initPieces = new HantoTestGame.PieceLocationPair[]{
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new HantoCoord(0, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new HantoCoord(0, 1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(1, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(1, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(0, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(-1, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(-2, 1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(-1, 2)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(1, 1)),
		};

		HantoTestGame g = 
				HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.GAMMA_HANTO);

		g.initializeBoard(initPieces);
		g.setTurnNumber(4);
		g.setPlayerMoving(HantoPlayerColor.RED);

		assertEquals(MoveResult.RED_WINS,
				g.makeMove(HantoPieceType.SPARROW, new HantoCoord(-1, 2), new HantoCoord(-1, 1)));
	}

	/**
	 * Same as with testRedWin
	 */
	@Test
	public void testBlueWin() throws HantoException {

		HantoTestGame.PieceLocationPair[] initPieces = new HantoTestGame.PieceLocationPair[]{
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new HantoCoord(0, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new HantoCoord(0, 1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(1, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(1, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(0, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(-1, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(-2, 1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(-1, 2)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(1, 1)),
		};

		HantoTestGame g = 
				HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.GAMMA_HANTO);

		g.initializeBoard(initPieces);
		g.setTurnNumber(4);
		g.setPlayerMoving(HantoPlayerColor.BLUE);

		assertEquals(MoveResult.BLUE_WINS,
				g.makeMove(HantoPieceType.SPARROW, new HantoCoord(-1, 2), new HantoCoord(-1, 1)));

	}

	/**
	 * Same as with testBlueWin and testRedWin
	 */
	@Test
	public void testDraw() throws HantoException {
		HantoTestGame.PieceLocationPair[] initPieces = new HantoTestGame.PieceLocationPair[]{
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new HantoCoord(0, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new HantoCoord(0, 1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(0, 2)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(1, 1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(1, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(1, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(0, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(-1, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(-1, 2)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(-2, 1))
		};

		HantoTestGame g = 
				HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.GAMMA_HANTO);

		g.initializeBoard(initPieces);
		g.setTurnNumber(5);
		g.setPlayerMoving(HantoPlayerColor.BLUE);

		assertEquals(MoveResult.DRAW,
				g.makeMove(HantoPieceType.SPARROW, new HantoCoord(-2, 1), new HantoCoord(-1, 1)));

	}

	@Test
	public void testPrintableBoard() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);

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

	}

	@Test(expected = HantoException.class)
	public void testIllegalBlueButterflyInventoryMove() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);

		g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 0));
		g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 1));

		g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(1, -1));
	}

	@Test(expected = HantoException.class)
	public void testIllegalRedButterflyInventoryMove() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);

		g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 0));
		g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 1));
		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(1, -1));

		g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(1, 1));
	}

	@Test(expected = HantoException.class)
	public void testIllegalCrabMove() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);

		g.makeMove(HantoPieceType.CRAB, null, new HantoCoord(0, 0));
	}

	@Test(expected = HantoException.class)
	public void testIllegalHorseMove() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);

		g.makeMove(HantoPieceType.HORSE, null, new HantoCoord(0, 0));
	}

	@Test(expected = HantoException.class)
	public void testIllegalCraneMove() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);

		g.makeMove(HantoPieceType.CRANE, null, new HantoCoord(0, 0));
	}

	@Test(expected = HantoException.class)
	public void testIllegalDoveMove() throws HantoException {
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);

		g.makeMove(HantoPieceType.DOVE, null, new HantoCoord(0, 0));
	}

	@Test
	public void testGammaInventoryCrab(){
		PlayerInventory p = new GammaInventory();

		assertFalse(p.crabsInInventory());
		p.placeCrab();
		assertFalse(p.crabsInInventory());
	}

	@Test
	public void testGammaInventoryHorse(){
		PlayerInventory p = new GammaInventory();

		assertFalse(p.horsesInInventory());
		p.placeHorse();
		assertFalse(p.horsesInInventory());
	}

	@Test
	public void testGammaInventoryCrane(){
		PlayerInventory p = new GammaInventory();

		assertFalse(p.cranesInInventory());
		p.placeCrane();
		assertFalse(p.cranesInInventory());
	}

	@Test
	public void testGammaInventoryDove(){
		PlayerInventory p = new GammaInventory();

		assertFalse(p.dovesInInventory());
		p.placeDove();
		assertFalse(p.dovesInInventory());
	}

	@Test(expected = HantoException.class)
	public void testIllegalMoveAfterGameHasEnded() throws HantoException {
		HantoTestGame.PieceLocationPair[] initPieces = new HantoTestGame.PieceLocationPair[]{
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new HantoCoord(0, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new HantoCoord(0, 1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(1, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(1, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(0, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(-1, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(-2, 1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new HantoCoord(-1, 2)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new HantoCoord(1, 1)),
		};

		HantoTestGame g = 
				HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.GAMMA_HANTO);

		g.initializeBoard(initPieces);
		g.setTurnNumber(4);
		g.setPlayerMoving(HantoPlayerColor.BLUE);

		g.makeMove(HantoPieceType.SPARROW, new HantoCoord(-1, 2), new HantoCoord(-1, 1));
		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(2, 2));
	}
	
	@Test
	public void checkBoardNoNegativeInventory(){
		PlayerInventory i = new GammaInventory();
		
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
		i.placeSparrow();
		assertFalse(i.sparrowsInInventory());
		i.placeSparrow();
		assertFalse(i.sparrowsInInventory());		
	}
	
	@Test
	public void testSlideUp(){
		
		Board b = new GammaBoard();
		
		// Place three pieces to test for movement
		b.placePiece(new HantoCoord(0, 0), new Butterfly(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(-1, 1), new Butterfly(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(1, 0), new Butterfly(HantoPlayerColor.BLUE));
		
		// Verify move is illegal
		assertFalse(SlideValidator.getInstance().canSlide(b, 
				new HantoCoord(0, 0), new HantoCoord(0, 1)));
		
		b.movePiece(new HantoCoord(-1, 1), new HantoCoord(-1, 0));
		
		// Verify move is legal
		assertTrue(SlideValidator.getInstance().canSlide(b, 
				new HantoCoord(0, 0), new HantoCoord(0, 1)));
		
		b.movePiece(new HantoCoord(-1, 0), new HantoCoord(-1, 1));
		b.movePiece(new HantoCoord(1, 0), new HantoCoord(1, -1));
		
		// Verify move is legal
		assertTrue(SlideValidator.getInstance().canSlide(b, 
				new HantoCoord(0, 0), new HantoCoord(0, 1)));
	}
	
	@Test
	public void testSlideNorthEast(){
		
		Board b = new GammaBoard();
		
		// Place three pieces to test for movement
		b.placePiece(new HantoCoord(0, 0), new Butterfly(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(0, 1), new Butterfly(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(1, -1), new Butterfly(HantoPlayerColor.BLUE));
		
		// Verify move is illegal
		assertFalse(SlideValidator.getInstance().canSlide(b, 
				new HantoCoord(0, 0), new HantoCoord(1, 0)));
		
		b.movePiece(new HantoCoord(0, 1), new HantoCoord(-1, 1));
		
		// Verify move is legal
		assertTrue(SlideValidator.getInstance().canSlide(b, 
				new HantoCoord(0, 0), new HantoCoord(1, 0)));
		
		b.movePiece(new HantoCoord(-1, 1), new HantoCoord(0, 1));
		b.movePiece(new HantoCoord(1, -1), new HantoCoord(0, -1));
		
		// Verify move is legal
		assertTrue(SlideValidator.getInstance().canSlide(b, 
				new HantoCoord(0, 0), new HantoCoord(1, 0)));
	}
	
	@Test
	public void testSlideSouthEast(){
		
		Board b = new GammaBoard();
		
		// Place three pieces to test for movement
		b.placePiece(new HantoCoord(0, 0), new Butterfly(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(1, 0), new Butterfly(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(0, -1), new Butterfly(HantoPlayerColor.BLUE));
		
		// Verify move is illegal
		assertFalse(SlideValidator.getInstance().canSlide(b, 
				new HantoCoord(0, 0), new HantoCoord(1, -1)));
		
		b.movePiece(new HantoCoord(1, 0), new HantoCoord(0, 1));
		
		// Verify move is legal
		assertTrue(SlideValidator.getInstance().canSlide(b, 
				new HantoCoord(0, 0), new HantoCoord(1, -1)));
		
		b.movePiece(new HantoCoord(0, 1), new HantoCoord(1, 0));
		b.movePiece(new HantoCoord(0, -1), new HantoCoord(-1, 0));
		
		// Verify move is legal
		assertTrue(SlideValidator.getInstance().canSlide(b, 
				new HantoCoord(1, 0), new HantoCoord(0, 1)));
	}
	
	@Test
	public void testSlideSouthWest(){
		
		Board b = new GammaBoard();
		
		// Place three pieces to test for movement
		b.placePiece(new HantoCoord(0, 0), new Butterfly(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(0, -1), new Butterfly(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(-1, 1), new Butterfly(HantoPlayerColor.BLUE));
		
		// Verify move is illegal
		assertFalse(SlideValidator.getInstance().canSlide(b, 
				new HantoCoord(0, 0), new HantoCoord(-1, 0)));
		
		b.movePiece(new HantoCoord(0, -1), new HantoCoord(1, -1));
		
		// Verify move is legal
		assertTrue(SlideValidator.getInstance().canSlide(b, 
				new HantoCoord(0, 0), new HantoCoord(-1, 0)));
		
		b.movePiece(new HantoCoord(1, -1), new HantoCoord(0, -1));
		b.movePiece(new HantoCoord(-1, 1), new HantoCoord(0, 1));
		
		// Verify move is legal
		assertTrue(SlideValidator.getInstance().canSlide(b, 
				new HantoCoord(0, 0), new HantoCoord(-1, 0)));
	}
	
	@Test
	public void testSlideNorthWest(){
		
		Board b = new GammaBoard();
		
		// Place three pieces to test for movement
		b.placePiece(new HantoCoord(0, 0), new Butterfly(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(0, 1), new Butterfly(HantoPlayerColor.BLUE));
		b.placePiece(new HantoCoord(-1, 0), new Butterfly(HantoPlayerColor.BLUE));
		
		// Verify move is illegal
		assertFalse(SlideValidator.getInstance().canSlide(b, 
				new HantoCoord(0, 0), new HantoCoord(-1, 1)));
		
		b.movePiece(new HantoCoord(0, 1), new HantoCoord(1, 0));
		
		// Verify move is legal
		assertTrue(SlideValidator.getInstance().canSlide(b, 
				new HantoCoord(0, 0), new HantoCoord(-1, 1)));
		
		b.movePiece(new HantoCoord(1, 0), new HantoCoord(0, 1));
		b.movePiece(new HantoCoord(-1, 0), new HantoCoord(0, -1));
		
		// Verify move is legal
		assertTrue(SlideValidator.getInstance().canSlide(b, 
				new HantoCoord(0, 0), new HantoCoord(-1, 1)));
	}
	
	@Test(expected = HantoException.class)
	public void testRedCannotMoveUntilButterFlyPlaced() throws HantoException {
		
		HantoGame g = 
				HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
		
		g.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoord(0, 0));
		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(0, 1));
		g.makeMove(HantoPieceType.SPARROW, null, new HantoCoord(1, -1));
		g.makeMove(HantoPieceType.SPARROW, new HantoCoord(0, 1), new HantoCoord(1, 0));
	}
}
