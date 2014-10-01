/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.studentmrracine.common.Board;
import hanto.studentmrracine.common.HantoCoord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation of a board for Gamma Hanto. Note that this implementation
 * is slightly different from my implementation in Beta Hanto. I also may want
 * to change the implementation later, hence why this class implements a Board
 * interface I created
 * 
 * @author mrracine
 */
public class GammaBoard implements Board {

	/**
	 * A 2D HashMap of HantoCoordinates and their corresponding pieces
	 */
	private Map<HantoCoordinate, HantoPiece> board = 
			new HashMap<HantoCoordinate, HantoPiece>();
	
	/**
	 * Lists of coordinates that can be used to quickly access the hashmap
	 */
	private List<HantoCoordinate> bluePieces = 
			new ArrayList<HantoCoordinate>();
	
	private List<HantoCoordinate> redPieces = 
			new ArrayList<HantoCoordinate>();

	/**
	 * @return the number of pieces on the board
	 */
	public int numberOfPieces() {
		return board.size();
	}
	
	/**
	 * @param coordX the x coordinate
	 * @param coordY the y coordinate
	 * @return whether the space is occupied or not
	 */
	public boolean isCoordinateOccupied(int coordX, int coordY) {

		HantoCoordinate c = new HantoCoord(coordX, coordY);
		return isCoordinateOccupied(c);
	}

	/**
	 * @param coord the HantoCoordinate
	 * @return whether the space is occupied or not
	 */
	public boolean isCoordinateOccupied(HantoCoordinate coord) {

		boolean coordinateOccupied;

		if(!board.containsKey(coord)){
			coordinateOccupied = false;
		} else {
			coordinateOccupied = true;
		}

		return coordinateOccupied;
	}

	/**
	 * Places a piece to the board hashmap
	 * @param newCoord the new coordinate to place the piece at
	 */
	public void placePiece(HantoCoordinate newCoord, HantoPiece newPiece) {
		board.put(new HantoCoord(newCoord), newPiece);
	}
	
	/**
	 * Moves a piece on the board. The HantoGame class should check
	 * that the move is valid
	 * 
	 * @param from the original coordinate
	 * @param to the destination coordinate
	 */
	public void movePiece(HantoCoordinate from, HantoCoordinate to) {
		
		HantoPiece toMove = board.get(from);

		board.remove(from);
		board.put(to, toMove);
	}

	/**
	 * Returns a piece at the specified coordinate or null
	 * if there is none
	 * 
	 * @param coord the coordinate to look for a piece at
	 * @return the piece at the specified coordinate
	 */
	public HantoPiece getPieceAt(HantoCoordinate coord){

		HantoPiece piece;

		if(board.containsKey(coord)){
			piece = board.get(coord);
		} else {
			piece = null;
		}

		return piece;
	}

	/**
	 * Checks whether an adjacent space is occupied or not
	 * 
	 * @param coord the destination coordinate
	 * @return whether or not an adjacent space is occupied or not
	 */
	public boolean isAnAdjacentSpaceOccupied(HantoCoordinate coord) {

		boolean isOccupied;

		if((isCoordinateOccupied(coord.getX(), coord.getY() - 1)) || 
				(isCoordinateOccupied(coord.getX(), coord.getY() + 1)) || 
				(isCoordinateOccupied(coord.getX() - 1, coord.getY())) || 
				(isCoordinateOccupied(coord.getX() - 1, coord.getY() + 1)) || 
				(isCoordinateOccupied(coord.getX() + 1, coord.getY())) || 
				(isCoordinateOccupied(coord.getX() + 1, coord.getY() - 1))){
			isOccupied = true;
		} else {
			isOccupied = false;
		}

		return isOccupied;
	}

	/**
	 * Checks whether any adjacent spaces have opposing colors
	 * @param coord the space to check
	 * @return whether the adjacent spaces has opposing colors
	 */
	public boolean hasAdjacentOpposingPieces(HantoCoordinate coord, 
			HantoPlayerColor currentPlayerTurn) {

		boolean hasOpposingAdjacentColors;

		if(isSpaceOpposingColor(coord.getX(), coord.getY() - 1, currentPlayerTurn) ||
				isSpaceOpposingColor(coord.getX(), coord.getY() + 1, currentPlayerTurn) || 
				isSpaceOpposingColor(coord.getX() - 1, coord.getY(), currentPlayerTurn) || 
				isSpaceOpposingColor(coord.getX() - 1, coord.getY() + 1, currentPlayerTurn) || 
				isSpaceOpposingColor(coord.getX() + 1, coord.getY(), currentPlayerTurn) || 
				isSpaceOpposingColor(coord.getX() + 1, coord.getY() - 1, currentPlayerTurn)){
			hasOpposingAdjacentColors = true;
		} else {
			hasOpposingAdjacentColors = false;
		}

		return hasOpposingAdjacentColors;
	}

	/**
	 * Checks whether an individual space has any pieces adjacent to it of
	 * an opposing color
	 * 
	 * @param x the x param of the space
	 * @param y the y param of the space
	 * @param color the color of the current player
	 * @return
	 */
	private boolean isSpaceOpposingColor(int x, int y, HantoPlayerColor color){

		HantoCoord to = new HantoCoord(x, y);
		boolean spaceIsOpposingColor;

		if(!isCoordinateOccupied(to)){
			spaceIsOpposingColor = false;
		} else if(board.get(to).getColor() != color){
			spaceIsOpposingColor = true;
		} else {
			spaceIsOpposingColor = false;
		}

		return spaceIsOpposingColor;
	}

	/**
	 * Checks whether the piece at the given coordinate
	 * is surrounded
	 * 
	 * @param coord the coordinate to check
	 * @return whether or not the piece is surrounded
	 */
	public boolean isSurrounded(HantoCoordinate coord) {

		boolean isSurrounded;

		if(isCoordinateOccupied(coord.getX(), coord.getY() - 1) && 
				isCoordinateOccupied(coord.getX(), coord.getY() + 1) && 
				isCoordinateOccupied(coord.getX() - 1, coord.getY()) && 
				isCoordinateOccupied(coord.getX() - 1, coord.getY() + 1) && 
				isCoordinateOccupied(coord.getX() + 1, coord.getY()) && 
				isCoordinateOccupied(coord.getX() + 1, coord.getY() - 1)){
			isSurrounded = true;
		} else {
			isSurrounded = false;
		}

		return isSurrounded;
	}

	/**
	 * Returns an array of occupied neighbors to a certain coordinate
	 * 
	 * @param coord the coordinate to look for neighbors
	 * @return an array of neighbors to the coordinate
	 */
	public List<HantoCoordinate> getNeighbors(HantoCoordinate coord) {
		
		List<HantoCoordinate> neighbors = new ArrayList<HantoCoordinate>();
		
		if(isCoordinateOccupied(coord.getX(), coord.getY() - 1)){
			neighbors.add(new HantoCoord(coord.getX(), coord.getY() - 1));
		}
		if(isCoordinateOccupied(coord.getX(), coord.getY() + 1)){
			neighbors.add(new HantoCoord(coord.getX(), coord.getY() + 1));
		}
		if(isCoordinateOccupied(coord.getX() - 1, coord.getY())){
			neighbors.add(new HantoCoord(coord.getX() - 1, coord.getY()));
		}
		if(isCoordinateOccupied(coord.getX() - 1, coord.getY() + 1)){
			neighbors.add(new HantoCoord(coord.getX() - 1, coord.getY() + 1));
		}
		if(isCoordinateOccupied(coord.getX() + 1, coord.getY())){
			neighbors.add(new HantoCoord(coord.getX() + 1, coord.getY()));
		}
		if(isCoordinateOccupied(coord.getX() + 1, coord.getY() - 1)){
			neighbors.add(new HantoCoord(coord.getX() + 1, coord.getY() - 1));
		}		
		
		return neighbors;
	}

	/**
	 * Clears all pieces from the board
	 */
	public void clearAllPieces() {
		board.clear();
	}
}
