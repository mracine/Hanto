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
import hanto.studentmrracine.common.ContinuityValidator;
import hanto.studentmrracine.common.SlideValidator;
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
			lastMoveResult = super.makeMove(pieceType, from, to); 
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

	/**
	 * Since the rules for a legal movement are different for Gamma and Delta,
	 * I completely overrode this method to allow Sparrows to fly for Delta but
	 * not Gamma
	 * 
	 * @param pieceType the type of piece moving
	 * @param from the original coordinate (if there is one)
	 * @param to the destination coordinate
	 * @return whether or not the move is legal 
	 */
	@Override
	protected boolean isLegalMovement(HantoPieceType pieceType, 
			HantoCoordinate from, HantoCoordinate to){	

		boolean isLegal;

		if (turnNumber == 4){

			// If the turn number is greater than or equal to 4,
			// the butterfly must be placed
			isLegal = isFourthTurnLegal(pieceType);

		} else if(board.numberOfPieces() < 2){

			// If the number of pieces is less than 2, it is
			// the first turn. Works better with test games
			isLegal = isFirstTurnLegal(from, to);

		} else {

			// If from is null, the piece is being placed. Else, it is being moved
			if(from == null){
				isLegal = board.isAnAdjacentSpaceOccupied(to) && 
						!board.hasAdjacentOpposingPieces(to, currentPlayerTurn);
			} else {

				switch(pieceType){
				case SPARROW:
					isLegal = ContinuityValidator.getInstance().isContinuous(board, from, to);
					break;
				default:
					isLegal = areAdjacent(from, to) &&
					SlideValidator.getInstance().canSlide(board, from, to) &&
					ContinuityValidator.getInstance().isContinuous(board, from, to);
					break;
				}
			}
		}

		return isLegal;
	}
}
