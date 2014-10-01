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
 * The class for a Hanto Crab
 * 
 * @author mrracine
 */
public class Crab extends Piece implements HantoPiece {

	/**
	 * Sets the color of the crab
	 * @param pieceColor the color of the crab
	 */
	public Crab(HantoPlayerColor pieceColor){
		super(pieceColor);
	}
	
	/**
	 * @return the color of this piece
	 */
	@Override
	public HantoPlayerColor getColor() {
		return super.getColor();
	}

	/**
	 * @return the type of this piece, a CRAB
	 */
	@Override
	public HantoPieceType getType() {
		return HantoPieceType.CRAB;
	}
}
