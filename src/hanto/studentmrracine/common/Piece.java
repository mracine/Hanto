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
 * The abstract class for a Hanto piece. All piece classes that implement
 * the HantoPiece interface share the common parameter of color. This
 * abstract class simply abstracts out that paramter from classes that extend it
 * 
 * @author mrracine
 */
public abstract class Piece implements HantoPiece {

	/**
	 * The color of the piece
	 */
	protected final HantoPlayerColor color;
	
	/**
	 * Sets the color of the piece
	 * @param pieceColor the color of the piece
	 */
	protected Piece(HantoPlayerColor pieceColor){
		color = pieceColor;
	}
	
	/**
	 * @return the color of this piece
	 */
	public HantoPlayerColor getColor() {
		return color;
	}
	
	/**
	 * @return the type of this piece, a CRAB
	 */
	public abstract HantoPieceType getType();
}
