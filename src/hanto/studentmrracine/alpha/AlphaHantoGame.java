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

	private final Board b = new Board();
	private HantoPlayerColor currentPlayerTurn;

	/**
	 * Default constructor for AlphaHantoGame
	 * @param movesFirst the first player to move in this game
	 */
	public AlphaHantoGame(HantoPlayerColor movesFirst){
		currentPlayerTurn = movesFirst;
	}

	@Override
	/**
	 * @param pieceType the type of piece moving. For Alpha, this is only a butterfly 
	 * @param from the initial coordinate. For Alpha, this will always be null
	 * @param to the destination coordinate. For Alpha, this will be (0,0) or
	 * one of the adjacent tiles
	 */
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		MoveResult moveResult;

		try{
			b.placePiece(to, new Butterfly(currentPlayerTurn));
		} catch (HantoException e){ }

		if(currentPlayerTurn == HantoPlayerColor.BLUE){
			moveResult = MoveResult.OK;
			currentPlayerTurn = HantoPlayerColor.RED;
		} else {
			moveResult = MoveResult.DRAW;
		}

		return moveResult;
	}

	/**
	 * Returns the piece at the specified coordinate
	 * @return the piece at the specified coordinate
	 */
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return b.getPieceAt(where);
	}

	/**
	 * Returns a formatted list of pieces and their coordinates
	 * @return the list of pieces on the board
	 */
	public String getPrintableBoard() {
		return b.getBoardString();
	}
}
