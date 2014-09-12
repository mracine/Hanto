/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.alpha;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

/**
 * The AlphaHantoGame implements the HantoGame interface
 * for an Alpha Game of Hanto.
 * 
 * @author mrracine
 *
 */
public class AlphaHantoGame implements HantoGame {

	private HantoPieceCoordinate blueButterfly;
	private HantoPieceCoordinate redButterfly;

	/**
	 * Keeps track of the current player's turn
	 */
	private HantoPlayerColor currentPlayerTurn;

	/**
	 * Default constructor for AlphaHantoGame
	 * @param movesFirst the first player to move in this game
	 */
	public AlphaHantoGame(HantoPlayerColor movesFirst){
		currentPlayerTurn = movesFirst;
	}

	/**
	 * Makes the move if able. For ALPHA Hanto, we are not concerned with the
	 * "from" coordinate, since it will always be null
	 * 
	 * @param pieceType the type of piece moving. For Alpha, this is only a butterfly 
	 * @param from the initial coordinate. For Alpha, this will always be null
	 * @param to the destination coordinate. For Alpha, this will be (0,0) or
	 * one of the adjacent tiles
	 * @throws HantoException 
	 */
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		MoveResult result; // The result of the move
		HantoPieceCoordinate newCoord = new HantoPieceCoordinate(to, 
				new Butterfly(currentPlayerTurn)); // The new butterfly

		if(currentPlayerTurn == HantoPlayerColor.BLUE){

			// Place the piece at (0, 0)
			if(to.getX() == 0 && to.getY() == 0){
				blueButterfly = newCoord;
			} else {
				throw new HantoException("Blue move is invalid");
			}

			// Switch turns
			currentPlayerTurn = HantoPlayerColor.RED;
			result = MoveResult.OK;

		} else {

			// Place the piece around the BLUE BUTTERFLY
			// First, check that the coordinates correspond
			// to one of the six surrounding hexagons
			// of the BLUE BUTTERFLY

			if(isValidRedMove(to)){
				redButterfly = newCoord;
			} else {
				throw new HantoException("Red move is invalid");
			}

			result = MoveResult.DRAW;
		}

		return result;
	}

	/**
	 * Returns the piece at the specified coordinate
	 * @return the piece at the specified coordinate
	 */
	public HantoPiece getPieceAt(HantoCoordinate where) {

		HantoPiece piece = null;

		// If the piece is at (0, 0), it is BLUE's piece
		if(blueButterfly != null && 
				where.getX() == blueButterfly.getX() && 
				where.getY() == blueButterfly.getY()){

			piece = blueButterfly.getPiece();

		} else if(redButterfly != null &&
				where.getX() == redButterfly.getX() &&
				where.getY() == redButterfly.getY()){

			piece = redButterfly.getPiece();

		}

		return piece;
	}

	/**
	 * Returns a formatted list of pieces and their coordinates
	 * @return the list of pieces on the board
	 */
	public String getPrintableBoard() {

		String formattedBoard = "";

		if(blueButterfly != null){
			formattedBoard += "BLUE B at (0, 0)\n";
		} 
		if(redButterfly != null){
			formattedBoard += "RED B at (";
			formattedBoard += redButterfly.getX() + ", " + redButterfly.getY() + ")\n";
		}

		return formattedBoard;
	}

	/**
	 * 
	 * @param to the desired destination
	 * @return whether or not the move is valid
	 */
	private static boolean isValidRedMove(HantoCoordinate to) {

		boolean isValid = false;

		if((to.getX() == 0 && to.getY() == 1) || 
				(to.getX() == 1 && to.getY() == 0) ||
				(to.getX() == 1 && to.getY() == -1) || 
				(to.getX() == 0 && to.getY() == -1) || 
				(to.getX() == -1 && to.getY() == 0) ||
				(to.getX() == -1 && to.getY() == 1)){
			isValid = true;
		}

		return isValid;
	}
}
