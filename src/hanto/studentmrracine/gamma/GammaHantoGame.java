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
import hanto.studentmrracine.common.HantoCoord;
import hanto.studentmrracine.common.HantoPieceFactory;

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

		// Convert the coordinates properly so the hashcode works
		from = from == null ? null : new HantoCoord(from);
		to = to == null ? null : new HantoCoord(to);		

		// Checks that the parameters are valid
		checkMoveParams(pieceType, from, to);

		preMoveCheck(pieceType, from, to);
		movePiece(HantoPieceFactory.getInstance().createPiece(pieceType, currentPlayerTurn), 
				from, to);
		switchTurn();
		postMoveCheck();

		if(turnNumber >= 21 && lastMoveResult == MoveResult.OK){
			lastMoveResult = MoveResult.DRAW;
		}

		return lastMoveResult;
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
}
