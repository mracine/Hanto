/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.delta;

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
import hanto.studentmrracine.gamma.GammaBoard;

/**
 * The implementation of a Delta Hanto game
 * 
 * @author mrracine
 */
public class DeltaHantoGame extends BaseHanto implements HantoGame {

	/**
	 * @param movesFirst the player to move first
	 */
	public DeltaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);

		// May want to change how this is implemented, but as
		// of right now there is no reason to change the way the
		// board is implemented from Gamma
		board = new GammaBoard();
		blueInventory = new DeltaInventory();
		redInventory = new DeltaInventory();
	}

	/**
	 * Calls BaseHanto with a few minor rule changes
	 */
	public MoveResult makeMove(HantoPieceType pieceType, 
			HantoCoordinate from, HantoCoordinate to) throws HantoException {

		// Convert the coordinates properly so the hashcode works
		from = from == null ? null : new HantoCoord(from);
		to = to == null ? null : new HantoCoord(to);

		// If all are null, the player is resigning
		// If the lastMoveResult is not OK, then a player
		// has either already won or resigned
		if(pieceType == null && from == null && to == null && lastMoveResult == MoveResult.OK){

			if(currentPlayerTurn == HantoPlayerColor.BLUE){
				lastMoveResult = MoveResult.RED_WINS;
			} else {
				lastMoveResult = MoveResult.BLUE_WINS;
			}

		} else {

			// Checks that the parameters are valid
			checkMoveParams(pieceType, from, to);

			preMoveCheck(pieceType, from, to);
			movePiece(HantoPieceFactory.getInstance().createPiece(pieceType, currentPlayerTurn), 
					from, to);
			switchTurn();
			postMoveCheck(); 
		}

		return lastMoveResult;
	}

	/**
	 * Calls BaseHanto's getPieceAt()
	 */
	public HantoPiece getPieceAt(HantoCoordinate where){
		return super.getPieceAt(where);
	}

	/**
	 * Calls BaseHanto's getPrintableBoard()
	 */
	public String getPrintableBoard(){
		return super.getPrintableBoard();
	}
}
