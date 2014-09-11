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
import hanto.HantoGameFactory;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

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
	 * Tests the board for an Alpha Hanto game
	 * 
	 * Since the pieces are being stored in a HashMap,
	 * order cannot be determined when printing out the board.
	 * Therefore, the printable board is just verified to contain
	 * the given strings.
	 */
	@Test
	public void testBoard(){
		Board b = new Board();

		// The board should be empty now
		assertEquals("", b.getBoardString());

		HantoPiece blue = new Butterfly(HantoPlayerColor.BLUE);
		AlphaHantoCoordinate blueCoord = new AlphaHantoCoordinate(0, 0);
		
		// Add a piece to the board
		try {
			b.placePiece(blueCoord, blue);
		} catch (HantoException e) { }

		// Check that the board has a blue butterfly
		assertTrue(b.getBoardString().contains("BLUE B at (0, 0)\n"));
		assertEquals(HantoPieceType.BUTTERFLY, b.getPieceAt(blueCoord).getType());
	}

	/**
	 * Tests the coordinate class for Alpha Hanto
	 */
	@Test
	public void testAlphaHantoCoordinate(){
		HantoCoordinate a = new AlphaHantoCoordinate(4, 7);
		assertEquals(4, a.getX());
		assertEquals(7, a.getY());
	}

	/**
	 * Tests an Alpha Hanto game
	 * 
	 * For Alpha, BLUE places his/her butterfly at (0,0). Then,
	 * RED places his/her butterfly adjacent to BLUE's. 
	 * The game ends in a draw.
	 */
	@Test
	public void testGame(){
		// Have BLUE add its butterfly
		try {
			assertEquals(MoveResult.OK, 
					aHanto.makeMove(HantoPieceType.BUTTERFLY, null, 
							new AlphaHantoCoordinate(0, 0)));
		} catch (HantoException e) { }
		
		// Verify that the piece has been added
		assertTrue(aHanto.getPrintableBoard().contains("BLUE B at (0, 0)\n"));

		try {
			assertEquals(MoveResult.DRAW, 
					aHanto.makeMove(HantoPieceType.BUTTERFLY, null, 
							new AlphaHantoCoordinate(0, 1)));
		} catch (HantoException e) { }
		
		// Verify that the piece has been added
		assertTrue(aHanto.getPrintableBoard().contains("RED B at (0, 1)\n"));
	}
}
