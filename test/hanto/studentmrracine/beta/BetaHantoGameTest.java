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
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

import org.junit.Test;

public class BetaHantoGameTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test the sparrow enumeration
	 */
	public void testSparrowEnum(){
		assertEquals("Sparrow", HantoPieceType.SPARROW.getPrintableName());
		assertEquals("Sparrow", HantoPieceType.SPARROW.toString());
		assertEquals("S", HantoPieceType.SPARROW.getSymbol());
	}
	
	/**
	 * Test the sparrow class
	 */
	public void testSparrowClass(){
		HantoPiece s = new Sparrow(HantoPlayerColor.BLUE);
		assertEquals(HantoPlayerColor.BLUE, s.getColor());
		assertEquals(HantoPieceType.SPARROW, s.getType());
	}
}
