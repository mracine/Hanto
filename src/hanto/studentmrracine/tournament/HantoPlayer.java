/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.tournament;

import java.util.List;
import java.util.Random;

import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.studentmrracine.HantoGameFactory;
import hanto.studentmrracine.common.BaseHanto;
import hanto.studentmrracine.common.HantoUtil;
import hanto.studentmrracine.common.PlayerInventory;
import hanto.studentmrracine.epsilon.EpsilonHantoGame;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

/**
 * HantoGamePlayer implementation for the tournament
 * 
 * @author mrracine
 */
public class HantoPlayer implements HantoGamePlayer {

	private BaseHanto game;
	private HantoPlayerColor myColor;
	private PlayerInventory myInventory;

	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor,
			boolean doIMoveFirst) {

		this.myColor = myColor;

		switch(myColor){
		case BLUE:
			if(doIMoveFirst){
				game = (BaseHanto)HantoGameFactory.getInstance().makeHantoGame(version, 
						HantoPlayerColor.BLUE);
			} else {
				game = (BaseHanto)HantoGameFactory.getInstance().makeHantoGame(version, 
						HantoPlayerColor.RED);
			}
			myInventory = game.getBlueInventory();
			break;
		case RED:
			if(doIMoveFirst){
				game = (BaseHanto)HantoGameFactory.getInstance().makeHantoGame(version, 
						HantoPlayerColor.RED);
			} else {
				game = (BaseHanto)HantoGameFactory.getInstance().makeHantoGame(version, 
						HantoPlayerColor.BLUE);
			}
			myInventory = game.getRedInventory();
			break;
		}
	}

	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {

		HantoMoveRecord myMove;

		if(opponentsMove != null) {

			// Try the opponents move
			try {

				game.makeMove(opponentsMove.getPiece(), opponentsMove.getFrom(), opponentsMove.getTo());

			} catch(HantoException e){

				e.printStackTrace();
			}
		}

		// Check the moves I have left
		List<HantoMoveRecord> movesLeft = HantoUtil.movesLeft(game, myColor, myInventory);

		// Two different methods, one for dumb AI, one for smart AI
		//myMove = dumbAI(movesLeft);
		myMove = smartAI(movesLeft);

		// Make my move
		try {

			game.makeMove(myMove.getPiece(), myMove.getFrom(), myMove.getTo());

		} catch(HantoException e){

			e.printStackTrace();
		}

		// Return the move I want to make
		return myMove;
	}

	/**
	 * Picks a move to make randomly and makes it
	 * @param movesLeft the moves I have left
	 * @return the move I want to make
	 */
	private HantoMoveRecord dumbAI(List<HantoMoveRecord> movesLeft) {

		Random rand = new Random();
		int randomNum = rand.nextInt(movesLeft.size() - 1);

		return movesLeft.get(randomNum);
	}

	/**
	 * Will attempt to do a MiniMax tree to make an intelligent move
	 * @param movesLeft the moves I have left
	 * @return the move I want to make
	 */
	private HantoMoveRecord smartAI(List<HantoMoveRecord> movesLeft) {

		// IMPORTANT: Make a copy of the game before messing with it
		BaseHanto testGame = new EpsilonHantoGame(game);

		// Initial call to minimax (for maximizing player)
		// NOTE: nodes are board states
		return MinimaxStrategy.minimaxStrategy(testGame, 3, myColor);
	}
}
