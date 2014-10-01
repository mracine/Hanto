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

import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;

/**
 * An interface describing how a board should act
 * in my implementation of Hanto.
 * 
 * Note: This is an interface so that it leaves the data
 * structure implementations for a board up to the implementations
 * of this interface, since different Hanto versions may require different
 * data structures
 * 
 * @author mrracine
 */
public interface Board {
	
	/**
	 * @return the number of pieces on the board
	 */
	int numberOfPieces();
	
	/**
	 * Checks whether the specified coordinate is occupied
	 * or not.
	 * 
	 * @param coordX the x param of the coordinate
	 * @param coordY the y param of the coordinate
	 * @return whether the coordinate is occupied
	 */
	boolean isCoordinateOccupied(int coordX, int coordY);
	
	/**
	 * Checks whether the specified coordinate is occupied
	 * or not.
	 * 
	 * @param coord the coordinate to look for
	 * @return whether the coordinate is occupied
	 */
	boolean isCoordinateOccupied(HantoCoordinate coord);
	
	/**
	 * Places a piece on the board
	 * @param newCoord the new coordinate to add
	 * @param piece the HantoPiece to place at the coordinate
	 */
	void placePiece(HantoCoordinate newCoord, HantoPiece piece);
	
	/**
	 * @param from the coordinate where the piece will be moved from
	 * @param to the coordinate where the piece will be moved to
	 */
	void movePiece(HantoCoordinate from, HantoCoordinate to);
	
	/**
	 * Returns a piece at the specified coordinate or null
	 * if there is none
	 * 
	 * @param coord the coordinate to look for a piece at
	 * @return the piece at the specified coordinate
	 */
	HantoPiece getPieceAt(HantoCoordinate coord);
	
	/**
	 * Checks whether an adjacent space is occupied or not
	 * This method is typically used on a placement of a piece
	 * 
	 * @param to the destination coordinate
	 * @return whether or not an adjacent space is occupied or not
	 */
	boolean isAnAdjacentSpaceOccupied(HantoCoordinate to);
	
	/**
	 * Checks whether any adjacent spaces have opposing colors
	 * @param to the space to check
	 * @param currentPlayerTurn the color of the current player's turn
	 * @return whether the adjacent spaces has opposing colors
	 */
	boolean hasAdjacentOpposingPieces(HantoCoordinate to, 
			HantoPlayerColor currentPlayerTurn);
	
	/**
	 * Checks whether the piece at the given coordinate
	 * is surrounded
	 * 
	 * @param coord the coordinate to check
	 * @return whether or not the piece is surrounded
	 */
	boolean isSurrounded(HantoCoordinate coord);
	
	/**
	 * Returns an array of neighbors to a certain space
	 * 
	 * @param coord the coordinate to retrieve neighbors from
	 * @return the array of neighbors
	 */
	List<HantoCoordinate> getNeighbors(HantoCoordinate coord);
	
	/**
	 * Clears all pieces from the board
	 */
	void clearAllPieces();
}
