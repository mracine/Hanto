/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.epsilon;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentmrracine.common.BaseHanto;
import hanto.studentmrracine.gamma.GammaBoard;

/**
 * Game class for Epsilon Hanto
 * 
 * @author mrracine
 */
public class EpsilonHantoGame extends BaseHanto implements HantoGame {

	public EpsilonHantoGame(HantoPlayerColor movesFirst) {
		
		super(movesFirst);
		
		// Board doesn't need to change after all
		board = new GammaBoard();
		
		// Set up player inventories
		blueInventory = new EpsilonInventory(HantoPlayerColor.BLUE);
		redInventory = new EpsilonInventory(HantoPlayerColor.RED);
	}
	
	/**
	 * Copy constructor for an EpsilonHantoGame
	 * @param g the game
	 */
	public EpsilonHantoGame(BaseHanto g){
		super(g);
	}
	
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		return super.makeMove(pieceType, from, to);
	}
	
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return super.getPieceAt(where);
	}
	
	@Override
	public String getPrintableBoard() {
		return super.getPrintableBoard();
	}
}
