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

import java.util.Queue;

import hanto.common.HantoCoordinate;
import hanto.studentmrracine.common.Board;
import hanto.studentmrracine.common.HantoUtil;

/**
 * Class for validating jumping
 * 
 * @author mrracine
 */
public class JumpValidator {

	/**
	 * Private constructor
	 */
	private JumpValidator(){}

	/**
	 * Checks whether the piece can jump or not as specified by the Hanto rules
	 * @param from the starting coordinate
	 * @param to the destination coordinate
	 * @param b the board
	 * @return whether or not the piece can jump
	 */
	public static boolean canJump(HantoCoordinate from, HantoCoordinate to, Board b){

		boolean canJump = true;

		Queue<HantoCoordinate> shortestPath = PathValidator.getShortestPath(from, to);

		while(!shortestPath.isEmpty()){
			HantoCoordinate current = shortestPath.remove();

			// If the coordinate is not occupied and it is not the destination,
			// the piece cannot jump
			if(!b.isCoordOccupied(current) && !current.equals(to)){
				canJump = false;
				break;
			}
		}

		canJump = canJump && 
				!HantoUtil.areAdjacent(from, to) &&
				isInStraightLine(from, to);

		return canJump;
	}

	/**
	 * Checks whether the two coordinates are in a straight line or not
	 * 
	 * @param from the starting coordinate
	 * @param to the destination coordinate
	 * @return whether the pieces are in a straight line or not
	 */
	private static boolean isInStraightLine(HantoCoordinate from, HantoCoordinate to){

		boolean isInStraightLine;

		int diffX = from.getX() - to.getX();
		int diffY = from.getY() - to.getY();
		int deltaX = Math.abs(diffX);
		int deltaY = Math.abs(diffY);
		
		double div = 0.0;
		
		if(diffY != 0){
			div = ((double)diffX)/((double)diffY);
		}

		if(deltaX == 0 || deltaY == 0 || div == -1.0){
			isInStraightLine = true;
		} else {
			isInStraightLine = false;
		}

		return isInStraightLine;
	}
}
