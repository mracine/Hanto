/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.common;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * The implementation of a Sparrow. Implements HantoPiece
 * 
 * @author mrracine
 *
 */
public class Sparrow extends Piece implements HantoPiece {
	
	/**
	 * The default constructor for a sparrow
	 * @param pieceColor the color of this sparrow piece
	 */
	public Sparrow(HantoPlayerColor pieceColor){
		super(pieceColor);
	}

	/**
	 * @return the type of this piece, a SPARROW
	 */
	public HantoPieceType getType() {
		return HantoPieceType.SPARROW;
	}
}
