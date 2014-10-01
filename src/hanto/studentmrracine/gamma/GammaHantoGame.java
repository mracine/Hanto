/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentmrracine.common.BaseHanto;
import hanto.studentmrracine.common.ContinuityValidator;

/**
 * The implementation of a Gamma Hanto game
 * @author mrracine
 */
public class GammaHantoGame extends BaseHanto implements HantoGame {

	/**
	 * The default constructor for a Gamma Hanto Game
	 * 
	 * Make sure that the board, blueInventory, and redInventory
	 * are initialized properly
	 */
	public GammaHantoGame(HantoPlayerColor movesFirst){
		super(movesFirst);
		board = new GammaBoard();
		blueInventory = new GammaInventory();
		redInventory = new GammaInventory();
	}

	/**
	 * Makes a move and returns the result of that move
	 * 
	 * @param pieceType the type of piece that will be moving
	 * @param from the coordinate where the piece will move from
	 * @param to the coordinate where the piece will move to
	 * @return the result of making the move
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {

		MoveResult result = super.makeMove(pieceType, from, to);

		if(turnNumber >= 21){
			result = MoveResult.DRAW;
		}

		return result;
	}

	/**
	 * @param where the coordinate where the piece is at
	 * @return the piece at the specified coordinate
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return super.getPieceAt(where);
	}

	/**
	 * Prints the board. Not different from BetaHanto
	 * 
	 * @return the printable board in string form
	 */
	@Override
	public String getPrintableBoard() {
		return super.getPrintableBoard();
	}

	/**
	 * Since the rules for a legal movement are different for Gamma and Delta,
	 * I overrode this method so that flying is not possible
	 * 
	 * @param pieceType the type of piece moving
	 * @param from the original coordinate (if there is one)
	 * @param to the destination coordinate
	 * @return whether or not the move is legal 
	 */
	@Override
	protected boolean isLegalMovement(HantoPieceType pieceType, 
			HantoCoordinate from, HantoCoordinate to){
		
		boolean isLegal = super.isLegalMovement(pieceType, from, to);

		// Pieces can only walk in Gamma Hanto
		if(turnNumber != 1 && from != null){
			isLegal = isLegal && areAdjacent(from, to) && 
					ContinuityValidator.getInstance().isContinuous(board, from, to);
		}

		return isLegal;
	}
}
