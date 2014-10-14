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

import hanto.common.HantoPlayerColor;
import hanto.studentmrracine.common.BaseHanto;
import hanto.studentmrracine.common.Board;
import hanto.studentmrracine.common.HantoUtil;
import hanto.studentmrracine.common.PlayerInventory;

/**
 * Singleton class to check whether a player has any valid moves left
 * 
 * @author mrracine
 */
public class MovesLeftValidator {

	/**
	 * Private constructor for singleton
	 */
	private MovesLeftValidator(){}

	/**
	 * Returns whether the specified player has any moves left or not
	 * 
	 * @param game the game being played
	 * @param player the player color to test
	 * @param inventory the player inventory to check
	 * @param b the board
	 * @return whether or not the player has moves left
	 */
	public static boolean hasMovesLeft(BaseHanto game, HantoPlayerColor player, 
			PlayerInventory inventory, Board b){
		
		return HantoUtil.movesLeft(game, player, inventory).size() > 0;
	}
}
