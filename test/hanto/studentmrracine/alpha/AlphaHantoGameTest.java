/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.alpha;

import static org.junit.Assert.*;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentmrracine.HantoGameFactory;
import hanto.studentmrracine.common.Butterfly;

import org.junit.Test;

/**
 * The test class for Alpha Hanto 
 * @author mrracine
 *
 */
public class AlphaHantoGameTest {

	private final HantoGame aHanto = HantoGameFactory.getInstance().makeHantoGame(
			HantoGameID.ALPHA_HANTO, HantoPlayerColor.BLUE);

	/**
	 * Tests the HantoPieceType enumeration
	 */
	@Test
	public void testButterflyEnum(){
		assertEquals("Butterfly", HantoPieceType.BUTTERFLY.getPrintableName());
		assertEquals("Butterfly", HantoPieceType.BUTTERFLY.toString());
		assertEquals("B", HantoPieceType.BUTTERFLY.getSymbol());
	}

	/**
	 * Tests the butterfly class for Alpha Hanto 
	 */
	@Test
	public void testButterflyClass(){
		HantoPiece b = new Butterfly(HantoPlayerColor.BLUE);
		assertEquals(HantoPieceType.BUTTERFLY, b.getType());
		assertEquals(HantoPlayerColor.BLUE, b.getColor());

		HantoPiece r = new Butterfly(HantoPlayerColor.RED);
		assertEquals(HantoPieceType.BUTTERFLY, r.getType());
		assertEquals(HantoPlayerColor.RED, r.getColor());
	}

	/**
	 * Tests the HantoPieceCoordinate class
	 */
	@Test
	public void testHantoPieceCoordinate(){
		HantoCoordinate origin = new AlphaCoordinate(0, 0);
		HantoCoordinate testCoord = new AlphaCoordinate(origin);
		assertEquals(0, origin.getX());
		assertEquals(0, origin.getY());
		assertEquals(0, testCoord.getX());
		assertEquals(0, testCoord.getY());

		HantoPiece blueButterfly = new Butterfly(HantoPlayerColor.BLUE);
		AlphaCoordinate testCoordWithPiece = 
				new AlphaCoordinate(origin, blueButterfly);
		assertEquals(HantoPieceType.BUTTERFLY, testCoordWithPiece.getPiece().getType());
	}

	/**
	 * Tests BLUE's first move of placing a BUTTERFLY
	 * at (0, 0) and RED's move of placing a BUTTERFLY
	 * at (0, 1)
	 */
	@Test
	public void testGame(){
		try {
			assertEquals(null, aHanto.getPieceAt(new AlphaCoordinate(0, 0)));
			assertEquals("", aHanto.getPrintableBoard());

			// Place the BLUE BUTTERFLY
			assertEquals(MoveResult.OK, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(0, 0)));

			assertEquals(HantoPieceType.BUTTERFLY, aHanto.getPieceAt(
					new AlphaCoordinate(0, 0)).getType());
			assertEquals(HantoPlayerColor.BLUE, aHanto.getPieceAt(
					new AlphaCoordinate(0, 0)).getColor());
			assertEquals("BLUE B at (0, 0)\n", aHanto.getPrintableBoard());

			// Place the RED BUTTERFLY
			assertEquals(MoveResult.DRAW, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(0, 1)));

			assertEquals(HantoPieceType.BUTTERFLY, aHanto.getPieceAt(
					new AlphaCoordinate(0, 1)).getType());
			assertEquals(HantoPlayerColor.RED, aHanto.getPieceAt(
					new AlphaCoordinate(0, 1)).getColor());
			assertEquals("BLUE B at (0, 0)\nRED B at (0, 1)\n", aHanto.getPrintableBoard());

		} catch (HantoException e) { 
			e.printStackTrace();
		}
	}

	/**
	 * Tests RED's move of placing a butterfly at (1, 0)
	 */
	@Test
	public void testGame2(){
		try {
			// Place the BLUE BUTTERFLY
			assertEquals(MoveResult.OK, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(0, 0)));

			// Place the RED BUTTERFLY
			assertEquals(MoveResult.DRAW, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(1, 0)));

			assertEquals(HantoPieceType.BUTTERFLY, aHanto.getPieceAt(
					new AlphaCoordinate(1, 0)).getType());
			assertEquals(HantoPlayerColor.RED, aHanto.getPieceAt(
					new AlphaCoordinate(1, 0)).getColor());
			assertEquals("BLUE B at (0, 0)\nRED B at (1, 0)\n", aHanto.getPrintableBoard());

		} catch (HantoException e) { 
			e.printStackTrace();
		}
	}

	/**
	 * Tests RED's move of placing a butterfly at (1, -1)
	 */
	@Test
	public void testGame3(){
		try {
			// Place the BLUE BUTTERFLY
			assertEquals(MoveResult.OK, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(0, 0)));

			// Place the RED BUTTERFLY
			assertEquals(MoveResult.DRAW, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(1, -1)));

			assertEquals(HantoPieceType.BUTTERFLY, aHanto.getPieceAt(
					new AlphaCoordinate(1, -1)).getType());
			assertEquals(HantoPlayerColor.RED, aHanto.getPieceAt(
					new AlphaCoordinate(1, -1)).getColor());
			assertEquals("BLUE B at (0, 0)\nRED B at (1, -1)\n", aHanto.getPrintableBoard());

		} catch (HantoException e) { 
			e.printStackTrace();
		}
	}

	/**
	 * Tests RED's move of placing a butterfly at (0, -1)
	 */
	@Test
	public void testGame4(){
		try {
			// Place the BLUE BUTTERFLY
			assertEquals(MoveResult.OK, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(0, 0)));

			// Place the RED BUTTERFLY
			assertEquals(MoveResult.DRAW, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(0, -1)));

			assertEquals(HantoPieceType.BUTTERFLY, aHanto.getPieceAt(
					new AlphaCoordinate(0, -1)).getType());
			assertEquals(HantoPlayerColor.RED, aHanto.getPieceAt(
					new AlphaCoordinate(0, -1)).getColor());
			assertEquals("BLUE B at (0, 0)\nRED B at (0, -1)\n", aHanto.getPrintableBoard());

		} catch (HantoException e) { 
			e.printStackTrace();
		}
	}

	/**
	 * Tests RED's move of placing a butterfly at (0, -1)
	 */
	@Test
	public void testGame5(){
		try {
			// Place the BLUE BUTTERFLY
			assertEquals(MoveResult.OK, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(0, 0)));

			// Place the RED BUTTERFLY
			assertEquals(MoveResult.DRAW, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(0, -1)));

			assertEquals(HantoPieceType.BUTTERFLY, aHanto.getPieceAt(
					new AlphaCoordinate(0, -1)).getType());
			assertEquals(HantoPlayerColor.RED, aHanto.getPieceAt(
					new AlphaCoordinate(0, -1)).getColor());
			assertEquals("BLUE B at (0, 0)\nRED B at (0, -1)\n", aHanto.getPrintableBoard());

		} catch (HantoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests RED's move of placing a butterfly at (-1, 0)
	 */
	@Test
	public void testGame6(){
		try {
			// Place the BLUE BUTTERFLY
			assertEquals(MoveResult.OK, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(0, 0)));

			// Place the RED BUTTERFLY
			assertEquals(MoveResult.DRAW, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(-1, 0)));

			assertEquals(HantoPieceType.BUTTERFLY, aHanto.getPieceAt(
					new AlphaCoordinate(-1, 0)).getType());
			assertEquals(HantoPlayerColor.RED, aHanto.getPieceAt(
					new AlphaCoordinate(-1, 0)).getColor());
			assertEquals("BLUE B at (0, 0)\nRED B at (-1, 0)\n", aHanto.getPrintableBoard());

		} catch (HantoException e) { 
			e.printStackTrace();
		}
	}

	/**
	 * Tests RED's move of placing a butterfly at (-1, 1)
	 */
	@Test
	public void testGame7(){
		try {
			// Place the BLUE BUTTERFLY
			assertEquals(MoveResult.OK, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(0, 0)));

			// Place the RED BUTTERFLY
			assertEquals(MoveResult.DRAW, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(-1, 1)));

			assertEquals(HantoPieceType.BUTTERFLY, aHanto.getPieceAt(
					new AlphaCoordinate(-1, 1)).getType());
			assertEquals(HantoPlayerColor.RED, aHanto.getPieceAt(
					new AlphaCoordinate(-1, 1)).getColor());
			assertEquals("BLUE B at (0, 0)\nRED B at (-1, 1)\n", aHanto.getPrintableBoard());

		} catch (HantoException e) { 
			e.printStackTrace();
		}
	}

	/**
	 * Tests an invalid RED move
	 */
	@Test
	public void testRedInvalid(){
		try {
			// Place the BLUE BUTTERFLY
			assertEquals(MoveResult.OK, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(0, 0)));

			// Place the RED BUTTERFLY
			assertEquals(MoveResult.DRAW, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(0, 2)));
		} catch (HantoException e) { 
			assertEquals("Red move is invalid", e.getMessage());
			assertEquals(null, aHanto.getPieceAt(new AlphaCoordinate(0, 2)));
			assertEquals(null, aHanto.getPieceAt(new AlphaCoordinate(0, 2)));
			assertEquals("BLUE B at (0, 0)\n", aHanto.getPrintableBoard());
		}
	}

	/**
	 * Tests an invalid BLUE move
	 */
	@Test
	public void testBlueInvalid(){
		try {
			// Place the BLUE BUTTERFLY
			assertEquals(MoveResult.OK, aHanto.makeMove(HantoPieceType.BUTTERFLY, 
					null, new AlphaCoordinate(0, 1)));
		} catch (HantoException e) {
			assertEquals("Blue move is invalid", e.getMessage());
			assertEquals(null, aHanto.getPieceAt(new AlphaCoordinate(0, 1)));
			assertEquals(null, aHanto.getPieceAt(new AlphaCoordinate(0, 1)));
			assertEquals("", aHanto.getPrintableBoard());
		}
	}

	/**
	 * Tests the creation of an AlphaHantoGame
	 */
	@Test
	public void getAnAlphaHantoGameFromTheFactory() {
		assertTrue(aHanto instanceof AlphaHantoGame);
	}

	/**
	 * Tests a valid BLUE first move
	 * @throws HantoException
	 */
	@Test
	public void blueMakesValidFirstMove() throws HantoException {
		final MoveResult mr = aHanto.makeMove(HantoPieceType.BUTTERFLY, null, new AlphaCoordinate(0, 0));
		assertEquals(MoveResult.OK, mr);
	}

	/**
	 * Tests that the BUTTERFLY is at (0, 0) after the first move
	 * @throws HantoException
	 */
	@Test
	public void afterFirstMoveBlueButterflyIsAt0_0() throws HantoException {
		aHanto.makeMove(HantoPieceType.BUTTERFLY, null, new AlphaCoordinate(0, 0));
		final HantoPiece p = aHanto.getPieceAt(new AlphaCoordinate(0, 0));
		assertEquals(HantoPieceType.BUTTERFLY, p.getType());
		assertEquals(HantoPlayerColor.BLUE, p.getColor());
	}

	/**
	 * Tests that BLUE cannot place a non BUTTERFLY piece
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void bluePlacesNonButterfly() throws HantoException {
		aHanto.makeMove(HantoPieceType.SPARROW, null, new AlphaCoordinate(0, 0));
	}

	/**
	 * Tests that RED places a BUTTERFLY next to BLUE's
	 * @throws HantoException
	 */
	@Test
	public void redPlacesButterflyNextToBlueButterfly() throws HantoException {
		aHanto.makeMove(HantoPieceType.BUTTERFLY, null, new AlphaCoordinate(0, 0));
		aHanto.makeMove(HantoPieceType.BUTTERFLY, null, new AlphaCoordinate(0, 1));
		final HantoPiece p = aHanto.getPieceAt(new AlphaCoordinate(0, 1));
		assertEquals(HantoPieceType.BUTTERFLY, p.getType());
		assertEquals(HantoPlayerColor.RED, p.getColor());
	}

	/**
	 * Tests that BLUE cannot place a BUTTERFLY anywhere but (0, 0)
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void blueAttemptsToPlaceButterflyAtWrongLocation() throws HantoException {
		aHanto.makeMove(HantoPieceType.BUTTERFLY, null, new AlphaCoordinate(-1, 1));
	}

	/**
	 * Tests that RED makes a valid second move
	 * @throws HantoException
	 */
	@Test
	public void redMakesValidSecondMoveAndGameIsDrawn() throws HantoException {
		aHanto.makeMove(HantoPieceType.BUTTERFLY, null, new AlphaCoordinate(0, 0));
		final MoveResult mr = aHanto.makeMove(HantoPieceType.BUTTERFLY, null, new AlphaCoordinate(-1, 1));
		assertEquals(MoveResult.DRAW, mr);
	}

	/**
	 * Tests that RED cannot place a piece non-adjacent to BLUE's
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void redPlacesButterflyNonAdjacentToBlueButterfly() throws HantoException {
		aHanto.makeMove(HantoPieceType.BUTTERFLY, null, new AlphaCoordinate(0, 0));
		aHanto.makeMove(HantoPieceType.BUTTERFLY, null, new AlphaCoordinate(0, 2));
	}

	/**
	 * Tests that a player cannot move his/her piece rather than place it
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void attemptToMoveRatherThanPlace() throws HantoException {
		aHanto.makeMove(HantoPieceType.BUTTERFLY, new AlphaCoordinate(0, 1), new AlphaCoordinate(0, 0));
	}
}
