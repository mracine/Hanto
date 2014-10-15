/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.validation;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentmrracine.common.BaseHanto;
import hanto.studentmrracine.common.Board;
import hanto.studentmrracine.common.HantoUtil;
import hanto.studentmrracine.common.PlayerInventory;

/**
 * A class to validate placement/movement of a piece
 * 
 * @author mrracine
 */
public class MoveValidator {

	/**
	 * Private constructor
	 */
	private MoveValidator(){}
	
	/**
	 * Checks whether a move is legal
	 * 
	 * @param game the game being played
	 * @param pieceType the piece type moving
	 * @param from the starting coordinate
	 * @param to the destination coordinate
	 * @return whether the move is legal or not
	 */
	public static boolean isLegal(BaseHanto game, HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to){
		
		boolean isLegal;
		
		isLegal = preMoveCheck(game, pieceType, from, to) && 
				isLegalMove(game, pieceType, from, to);
		
		return isLegal;
	}
	
	private static boolean preMoveCheck(BaseHanto game, HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to){
		
		boolean preMoveOk = true;
		
		// Check that a player has not already won
		if(game.getLastMoveResult() != MoveResult.OK){
			preMoveOk = false;
		}

		// Check the player inventories
		// Also check that the butterfly has been placed if a movement is to be made
		if(from == null){
			if(game.getCurrentPlayerTurn() == HantoPlayerColor.BLUE && 
					!isInInventory(pieceType, game.getBlueInventory())){
				preMoveOk = false;
			} else if (game.getCurrentPlayerTurn() == HantoPlayerColor.RED &&
					!isInInventory(pieceType, game.getRedInventory())){
				preMoveOk = false;
			}
		} else {
			if(game.getCurrentPlayerTurn() == HantoPlayerColor.BLUE &&
					game.getBlueButterfly() == null){
				preMoveOk = false;
			} else if (game.getRedButterfly() == null){
				preMoveOk = false;
			}
		}
		
		return preMoveOk;
	}
	
	/**
	 * Checks whether the movement is legal or not
	 * 
	 * @param game the game being played
	 * @param pieceType the type of piece to move
	 * @param from the starting coordinate
	 * @param to the destination coordinate
	 * @return whether the move is legal or not
	 */
	private static boolean isLegalMove(BaseHanto game, HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to){
		
		boolean isLegal = true;

		// Check the fourth turn for legality
		if (game.getTurnNumber() >= 3){

			// If the turn number is greater than or equal to 4,
			// the butterfly must be placed
			isLegal = isFourthTurnLegal(game, pieceType);
		} 

		// Pieces move differently on the first turn
		if(game.getTurnNumber() == 1){

			// If the number of pieces is less than 2, it is
			// the first turn. Works better with test games
			isLegal = isFirstTurnLegal(game, from, to);
			
		} else {
			isLegal = isLegal && isLegalMovement(pieceType, from, to, 
					game.getCurrentPlayerTurn(), game.getBoard());
		}

		return isLegal;
	}
	
	/**
	 * Checks the first move of a Hanto game
	 * 
	 * @param game the game being played
	 * @param from the coordinate to move from (should be null)
	 * @param to the coordinate to move to (should be (0, 0) or one of its adjacents)
	 * @return whether or not the first turn move is legal
	 */
	private static boolean isFirstTurnLegal(BaseHanto game, 
			HantoCoordinate from, HantoCoordinate to) {

		boolean isLegal;

		if(game.getMovedFirst() == game.getCurrentPlayerTurn()){

			// First move of game needs to be at (0, 0)
			if(to.getX() != 0 || to.getY() != 0){
				isLegal = false;
			} else {
				isLegal = true;
			}

		} else {

			// This place needs to be adjacent to (0, 0)
			if(!game.getBoard().isAdjacentSpaceOccupied(to)){
				isLegal = false;
			} else {
				isLegal = true;
			}
		}

		return isLegal;
	}

	/**
	 * Checks that the butterfly has been placed by turn 4
	 * 
	 * @param game the game being played
	 * @param pieceType the type of piece to be placed
	 * @return whether or not the move is legal
	 */
	private static boolean isFourthTurnLegal(BaseHanto game, HantoPieceType pieceType) {

		boolean isLegal = true;

		// Check that the players are placing a butterfly on the fourth turn
		if(game.getCurrentPlayerTurn() == HantoPlayerColor.BLUE){

			if(game.getBlueButterfly() == null && pieceType != HantoPieceType.BUTTERFLY){
				isLegal = false;
			}

		} else {

			if(game.getRedButterfly() == null && pieceType != HantoPieceType.BUTTERFLY){
				isLegal = false;
			}
		}

		return isLegal;
	}
	
	/**
	 * 
	 * @param pieceType the type of piece to move
	 * @param from the starting coordinate
	 * @param to the destination coordinate
	 * @param currentPlayerTurn the current player's turn
	 * @param b the board
	 * @return
	 */
	private static boolean isLegalMovement(HantoPieceType pieceType, 
			HantoCoordinate from, HantoCoordinate to, 
			HantoPlayerColor currentPlayerTurn, Board b){

		boolean isLegal;

		// If from is null, the piece is being placed. Else, it is being moved
		if(from == null){
			isLegal = b.isAdjacentSpaceOccupied(to) && 
					!b.isAdjacentOpposingPieces(to, currentPlayerTurn);
		} else {

			switch(pieceType){
			case SPARROW:
				isLegal = PathValidator.isWithinDistance(4, from, to) &&
				ContinuityValidator.isContinuous(b, from, to);
				break;
			case HORSE:
				isLegal = JumpValidator.canJump(from, to, b) && 
				ContinuityValidator.isContinuous(b, from, to);
				break;
			default:
				isLegal =  HantoUtil.areAdjacent(from, to) &&
				SlideValidator.canSlide(b, from, to) &&
				ContinuityValidator.isContinuous(b, from, to);
				break;
			}
		}

		return isLegal;
	}
	
	/**
	 * Checks that the placement is legal according to the player's inventory
	 * 
	 * @param pieceType the type of piece to check the inventory for
	 * @param inventory the inventory to check
	 * @return whether or not the piece is in the inventory
	 */
	private static boolean isInInventory(HantoPieceType pieceType, PlayerInventory inventory) {

		boolean isInInventory = false;

		switch(pieceType){
		case BUTTERFLY:
			isInInventory = inventory.butterfliesInInventory();
			break;
		case CRAB:
			isInInventory = inventory.crabsInInventory();
			break;
		case HORSE:
			isInInventory = inventory.horsesInInventory();
			break;
		case CRANE:
			isInInventory = inventory.cranesInInventory();
			break;
		case DOVE:
			isInInventory = inventory.dovesInInventory();
			break;
		case SPARROW:
			isInInventory = inventory.sparrowsInInventory();
			break;
		}

		return isInInventory;
	}
}
