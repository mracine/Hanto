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
 * Piece class for a Horse
 * 
 * @author mrracine
 */
public class Horse extends Piece implements HantoPiece {

	public Horse(HantoPlayerColor pieceColor) {
		super(pieceColor);
	}

	@Override
	public HantoPieceType getType() {
		return HantoPieceType.HORSE;
	}
}
