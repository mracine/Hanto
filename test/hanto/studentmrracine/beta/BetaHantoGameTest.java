/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.beta;

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
import hanto.studentmrracine.common.Butterfly;
import hanto.studentmrracine.common.Sparrow;

import org.junit.Test;

public class BetaHantoGameTest {

	private HantoGame bHanto = HantoGameFactory.getInstance().makeHantoGame(
			HantoGameID.BETA_HANTO, HantoPlayerColor.BLUE);

	/**
	 * Test the sparrow enumeration
	 */
	@Test
	public void testSparrowEnum(){
		assertEquals("Sparrow", HantoPieceType.SPARROW.getPrintableName());
		assertEquals("Sparrow", HantoPieceType.SPARROW.toString());
		assertEquals("S", HantoPieceType.SPARROW.getSymbol());
	}

	/**
	 * Test the sparrow class
	 */
	@Test
	public void testSparrowClass(){
		HantoPiece s = new Sparrow(HantoPlayerColor.BLUE);
		assertEquals(HantoPlayerColor.BLUE, s.getColor());
		assertEquals(HantoPieceType.SPARROW, s.getType());
	}

	/**
	 * Tests the BetaCoordinate class
	 */
	@Test
	public void testBetaCoordinate(){
		BetaCoordinate bc1 = new BetaCoordinate(0, 0);
		assertEquals(0, bc1.getX());
		assertEquals(0, bc1.getY());
		
		HantoCoordinate hc1 = new BetaCoordinate(-1, 2);
		BetaCoordinate bc2 = new BetaCoordinate(hc1);
		assertEquals(-1, bc2.getX());
		assertEquals(2, bc2.getY());
		
		bc2.setPiece(new Sparrow(HantoPlayerColor.BLUE));
		assertEquals(HantoPieceType.SPARROW, bc2.getPiece().getType());
		assertEquals(HantoPlayerColor.BLUE, bc2.getPiece().getColor());
		
		BetaCoordinate bc3 = new BetaCoordinate(1, 2, new Butterfly(HantoPlayerColor.RED));
		assertEquals(HantoPieceType.BUTTERFLY, bc3.getPiece().getType());
		assertEquals(HantoPlayerColor.RED, bc3.getPiece().getColor());
		assertEquals(1, bc3.getX());
		assertEquals(2, bc3.getY());
	}
	
	/**
	 * Tests a sample playthrough of a Beta Hanto game
	 */
	@Test
	public void testGame1() {

		try {
			// BLUE places SPARROW at (0, 0)
			assertEquals(MoveResult.OK,
					bHanto.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(0, 0)));

			// RED places SPARROW at (0, 1)
			assertEquals(MoveResult.OK,
					bHanto.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(0, 1)));

			// BLUE places SPARROW at (1, 0)
			assertEquals(MoveResult.OK,
					bHanto.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(1, 0)));

			// RED places SPARROW at (2, 0)
			assertEquals(MoveResult.OK,
					bHanto.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(2, 0)));

			// BLUE places SPARROW at (1, -1)
			assertEquals(MoveResult.OK,
					bHanto.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(1, -1)));

			// RED places SPARROW at (1, -2)
			assertEquals(MoveResult.OK,
					bHanto.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(1, -2)));

			// BLUE places BUTTERFLY at (0, -2)
			assertEquals(MoveResult.OK,
					bHanto.makeMove(HantoPieceType.BUTTERFLY, null, new BetaCoordinate(0, -2)));

			// RED places BUTTERFLY at (-1, -1)
			assertEquals(MoveResult.OK,
					bHanto.makeMove(HantoPieceType.BUTTERFLY, null, new BetaCoordinate(-1, -1)));

			// BLUE places SPARROW at (-1, 0)
			assertEquals(MoveResult.OK,
					bHanto.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(-1, 0)));
			
			// RED places SPARROW at (-2, -1)
			assertEquals(MoveResult.OK,
					bHanto.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(-2, -1)));
			
			// BLUE places SPARROW at (-1, 1)
			assertEquals(MoveResult.OK,
					bHanto.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(-1, 1)));
			
			// RED places SPARROW at (-2, 2)
			assertEquals(MoveResult.DRAW,
					bHanto.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(-2, 2)));
		} catch (HantoException e) { }
	}

	/**
	 * Tests an illegal first move
	 */
	@Test
	public void testIllegalFirstMove() {
		BetaHantoGame testB = new BetaHantoGame(HantoPlayerColor.BLUE);

		try {
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.BUTTERFLY, null, new BetaCoordinate(0, 1)));
		} catch (HantoException e) {
			assertEquals("First move is invalid", e.getMessage());
		}	
	}

	/**
	 * Tests when a BUTTERFLY is not placed by the 4th move
	 */
	@Test
	public void testBlueButterflyByTurnFour(){

		BetaHantoGame testB = new BetaHantoGame(HantoPlayerColor.BLUE);

		try {
			// BLUE places SPARROW at (0, 0)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(0, 0)));

			// RED places SPARROW at (0, 1)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(0, 1)));

			// BLUE places SPARROW at (1, 0)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(1, 0)));

			// RED places SPARROW at (2, 0)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(2, 0)));

			// BLUE places SPARROW at (1, -1)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(1, -1)));

			// RED places SPARROW at (1, -2)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(1, -2)));

			// BLUE places BUTTERFLY at (0, -2)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(0, -2)));

		} catch (HantoException e) { 
			assertEquals("Blue butterfly must be placed", e.getMessage());
		}
	}

	/**
	 * Tests when a BUTTERFLY is not placed by the 4th move
	 */
	@Test
	public void testRedButterflyByTurnFour(){

		BetaHantoGame testB = new BetaHantoGame(HantoPlayerColor.BLUE);

		try {
			// BLUE places SPARROW at (0, 0)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(0, 0)));

			// RED places SPARROW at (0, 1)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(0, 1)));

			// BLUE places SPARROW at (1, 0)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(1, 0)));

			// RED places SPARROW at (2, 0)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(2, 0)));

			// BLUE places SPARROW at (1, -1)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(1, -1)));

			// RED places SPARROW at (1, -2)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(1, -2)));

			// BLUE places BUTTERFLY at (0, -2)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.BUTTERFLY, null, new BetaCoordinate(0, -2)));

			// RED places BUTTERFLY at (-1, -1)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(-1, -1)));

		} catch (HantoException e) { 
			assertEquals("Red butterfly must be placed", e.getMessage());
		}
	}

	/**
	 * Test the game condition where blue wins
	 */
	@Test
	public void testBlueWin() {

		BetaHantoGame testB = new BetaHantoGame(HantoPlayerColor.BLUE);

		try {
			// BLUE places SPARROW at (0, 0)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(0, 0)));

			// RED places BUTTERFLY at (0, 1)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.BUTTERFLY, null, new BetaCoordinate(0, 1)));

			// BLUE places SPARROW at (1, 0)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(1, 0)));

			// RED places SPARROW at (1, 1)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(1, 1)));

			// BLUE places SPARROW at (0, 2)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(0, 2)));

			// RED places SPARROW at (-1, 2)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(-1, 2)));

			// BLUE places BUTTERFLY at (-1, 1)
			assertEquals(MoveResult.BLUE_WINS,
					testB.makeMove(HantoPieceType.BUTTERFLY, null, new BetaCoordinate(-1, 1)));

		} catch (HantoException e) { }
	}

	/**
	 * Test the game condition where red wins
	 */
	@Test
	public void testRedWin(){

		BetaHantoGame testB = new BetaHantoGame(HantoPlayerColor.BLUE);

		try {
			// BLUE places SPARROW at (0, 0)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.BUTTERFLY, null, new BetaCoordinate(0, 0)));

			// RED places BUTTERFLY at (0, 1)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(0, 1)));

			// BLUE places SPARROW at (1, 0)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(1, 0)));

			// RED places SPARROW at (1, -1)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(1, -1)));

			// BLUE places SPARROW at (0, -1)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(0, -1)));

			// RED places SPARROW at (-1, 0)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(-1, 0)));

			// BLUE places BUTTERFLY at (-1, 1)
			assertEquals(MoveResult.RED_WINS,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(-1, 1)));

		} catch (HantoException e) { }
	}

	/**
	 * Tests an unconnected move
	 */
	@Test
	public void testUnconnectedMove(){

		BetaHantoGame testB = new BetaHantoGame(HantoPlayerColor.BLUE);

		try {
			// BLUE places BUTTERFLY at (0, 0)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.BUTTERFLY, null, new BetaCoordinate(0, 0)));
			
			// RED places a BUTTERFLY at (0, 3)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.BUTTERFLY, null, new BetaCoordinate(0, 3)));
			
		} catch (HantoException e) {
			assertEquals("Move is invalid", e.getMessage());
		}
	}

	/**
	 * Tests an occupied space
	 */
	@Test
	public void testOccupiedSpace(){
		
		BetaHantoGame testB = new BetaHantoGame(HantoPlayerColor.BLUE);

		try {
			// BLUE places BUTTERFLY at (0, 0)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.BUTTERFLY, null, new BetaCoordinate(0, 0)));
			
			// RED places a BUTTERFLY at (0, 0)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.BUTTERFLY, null, new BetaCoordinate(0, 0)));
			
		} catch (HantoException e) {
			assertEquals("Move is invalid", e.getMessage());
		}
	}

	/**
	 * Tests the getPieceAt() function
	 */
	@Test
	public void testGetPieceAt(){
		
		BetaHantoGame testB = new BetaHantoGame(HantoPlayerColor.BLUE);
		
		assertEquals(null, testB.getPieceAt(new BetaCoordinate(0, 0)));
		
		try {
			
			// BLUE places BUTTERFLY at (0, 0)
			assertEquals(MoveResult.OK, 
					testB.makeMove(HantoPieceType.BUTTERFLY, null, new BetaCoordinate(0, 0)));
			
			assertEquals(HantoPieceType.BUTTERFLY, testB.getPieceAt(new BetaCoordinate(0, 0)).getType());
			assertEquals(HantoPlayerColor.BLUE, testB.getPieceAt(new BetaCoordinate(0, 0)).getColor());
			
			// RED places SPARROW at (0, 1)
			assertEquals(MoveResult.OK,
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(0, 1)));
			
			assertEquals(HantoPieceType.SPARROW, testB.getPieceAt(new BetaCoordinate(0, 1)).getType());
			assertEquals(HantoPlayerColor.RED, testB.getPieceAt(new BetaCoordinate(0, 1)).getColor());
			
		} catch (HantoException e) { }
	}
	
	/**
	 * Tests the printable board function
	 */
	@Test
	public void testPrintableBoard(){
		
		BetaHantoGame testB = new BetaHantoGame(HantoPlayerColor.BLUE);
		
		try {
			// BLUE places BUTTERFLY at (0, 0)
			assertEquals(MoveResult.OK, 
					testB.makeMove(HantoPieceType.BUTTERFLY, null, new BetaCoordinate(0, 0)));
			
			assertEquals("BLUE B at (0, 0)\n", testB.getPrintableBoard());
			
			// RED places SPARROW at (0, 1)
			assertEquals(MoveResult.OK, 
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(0, 1)));
			
			assertEquals("BLUE B at (0, 0)\n"
					+ "RED S at (0, 1)\n", testB.getPrintableBoard());
			
			// BLUE places SPARROW at (0, 2)
			assertEquals(MoveResult.OK, 
					testB.makeMove(HantoPieceType.SPARROW, null, new BetaCoordinate(0, 2)));
			
			assertEquals("BLUE B at (0, 0)\n"
					+ "RED S at (0, 1)\n"
					+ "BLUE S at (0, 2)\n", testB.getPrintableBoard());
			
		} catch (HantoException e) { }
	}
	
	/**
	 * Tests an invalid piece type
	 */
	@Test
	public void testInvalidPieceType(){
		
		BetaHantoGame testB = new BetaHantoGame(HantoPlayerColor.BLUE);
		
		try {
			assertEquals(MoveResult.OK, 
					testB.makeMove(HantoPieceType.CRANE, null, new BetaCoordinate(0, 0)));
		} catch (HantoException e) {
			assertEquals("Invalid piece type", e.getMessage());
		}
	}
}
