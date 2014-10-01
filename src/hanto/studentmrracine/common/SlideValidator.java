/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.common;

import hanto.common.HantoCoordinate;

/**
 * A class that validates a piece can slide from one location
 * to another
 * 
 * @author mrracine
 *
 */
public class SlideValidator {

	private static final SlideValidator INSTANCE = new SlideValidator();

	/**
	 * Private constructor for singleton
	 */
	private SlideValidator(){
		//
	}

	/**
	 * @return the instance of this class
	 */
	public static SlideValidator getInstance(){
		return INSTANCE;
	}

	/**
	 * Checks whether the piece can slide from one location to another
	 * 
	 * @param b the Hanto board
	 * @param from the starting coordinate of the move
	 * @param to the destination coordinate
	 * @return whether or not the board is connected
	 */
	public boolean canSlide(Board b, HantoCoordinate from, HantoCoordinate to){

		boolean canSlide = false;
		
		HantoCoord f = new HantoCoord(from);
		HantoCoord t = new HantoCoord(to);
		
		int deltaX = t.getX() - f.getX();
		int deltaY = t.getY() - f.getY();

		/**
		 * First, check to see which direction the piece is moving in
		 */
		
		// Moving up
		if(deltaX == 0 && deltaY >= 1){
			canSlide = !isBlocked(b, new HantoCoord(f.getX() + 1, f.getY()),
					new HantoCoord(f.getX() - 1, f.getY() + 1));
		}
		
		// Moving northeast (relatively)
		else if(deltaX >= 1 && deltaY == 0){
			canSlide = !isBlocked(b, new HantoCoord(f.getX(), f.getY() + 1),
					new HantoCoord(f.getX() + 1, f.getY() - 1));
		}
		
		// Moving southeast (relatively)
		else if(deltaX >= 1 && deltaY <= -1){
			canSlide = !isBlocked(b, new HantoCoord(f.getX() + 1, f.getY()),
					new HantoCoord(f.getX(), f.getY() - 1));
		}
		
		// Moving down
		else if(deltaX == 0 && deltaY <= -1){
			canSlide = !isBlocked(b, new HantoCoord(f.getX() + 1, f.getY() - 1),
					new HantoCoord(f.getX() - 1, f.getY()));
		}
		
		// Moving southwest (relatively)
		else if(deltaX <= -1 && deltaY == 0){
			canSlide = !isBlocked(b, new HantoCoord(f.getX(), f.getY() - 1),
					new HantoCoord(f.getX() - 1, f.getY() + 1));
		}
		
		// Moving northwest (relatively)
		else if(deltaX <= -1 && deltaY >= 1){
			canSlide = !isBlocked(b, new HantoCoord(f.getX(), f.getY() + 1),
					new HantoCoord(f.getX() - 1, f.getY()));
		}
		
		return canSlide;
	}

	/**
	 * Checks whether the two coordinates that could block the move are occupied
	 * @param b the board
	 * @param coord1 the first coordinate
	 * @param coord2 the second coordinate
	 * @return whether both coordinates are occupied
	 */
	private boolean isBlocked(Board b, HantoCoord coord1, HantoCoord coord2) {
		return b.isCoordinateOccupied(coord1) && b.isCoordinateOccupied(coord2);
	}
}
