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
import hanto.studentmrracine.common.HantoUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	 * Default constructor 
	 */
	public GammaBoard(){}
	
	/**
	 * Copy constructor for board
	 * @param b the board to copy
	 */
	public GammaBoard(Board b){
		
		for(HantoCoordinate c : b.getAllOccupiedCoordinates()){
			
			b.placePiece(c, b.getPieceAt(c));
		}
	}
	
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
	public boolean isCoordOccupied(int coordX, int coordY) {

		HantoCoordinate c = new HantoCoord(coordX, coordY);
		return isCoordOccupied(c);
	}

	/**
	 * @param coord the HantoCoordinate
	 * @return whether the space is occupied or not
	 */
	public boolean isCoordOccupied(HantoCoordinate coord) {

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
	public boolean isAdjacentSpaceOccupied(HantoCoordinate coord) {

		return getOccupiedNeighbors(coord).size() > 0;
	}

	/**
	 * Checks whether any adjacent spaces have opposing colors
	 * @param coord the space to check
	 * @return whether the adjacent spaces has opposing colors
	 */
	public boolean isAdjacentOpposingPieces(HantoCoordinate coord, 
			HantoPlayerColor currentPlayerTurn) {

		boolean hasOpposingAdjacentColors = false;
		
		for(HantoCoordinate c : getOccupiedNeighbors(coord)){
			
			if(isSpaceOpposingColor(c, currentPlayerTurn)){
				hasOpposingAdjacentColors = true;
				break;
			}
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
	private boolean isSpaceOpposingColor(HantoCoordinate coord, HantoPlayerColor color){

		boolean spaceIsOpposingColor;

		if(board.get(coord).getColor() != color){
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

		return getOccupiedNeighbors(coord).size() == 6;
	}

	/**
	 * Returns an array of occupied neighbors to a certain coordinate
	 * 
	 * @param coord the coordinate to look for neighbors
	 * @return an array of neighbors to the coordinate
	 */
	public List<HantoCoordinate> getOccupiedNeighbors(HantoCoordinate coord) {

		List<HantoCoordinate> occupiedNeighbors = new ArrayList<HantoCoordinate>();
		
		for(HantoCoordinate c : HantoUtil.getAllNeighbors(coord)){
			if(isCoordOccupied(c)){
				occupiedNeighbors.add(c);
			}
		}

		return occupiedNeighbors;
	}
	
	/**
	 * Returns a list of coordinates occupied by a certain player
	 */
	public List<HantoCoordinate> getPlayerOccupiedCoordinates(
			HantoPlayerColor player) {
		
		List<HantoCoordinate> playerOccupiedCoords = new ArrayList<HantoCoordinate>();
		
		for(HantoCoordinate c : getAllOccupiedCoordinates()){
			
			if(getPieceAt(c).getColor() == player){
				playerOccupiedCoords.add(c);
			}
		}
		
		return playerOccupiedCoords;
	}

	/**
	 * Returns a list of coordinates occupied on the board
	 */
	public List<HantoCoordinate> getAllOccupiedCoordinates() {

		List<HantoCoordinate> allCoordinates = new ArrayList<HantoCoordinate>();
		Iterator<Entry<HantoCoordinate, HantoPiece>> it = board.entrySet().iterator();

		while(it.hasNext()){
			Map.Entry<HantoCoordinate, HantoPiece> pairs = it.next();
			allCoordinates.add(pairs.getKey());
		}

		return allCoordinates;
	}
	
	/**
	 * Clears all pieces from the board
	 */
	public void clearAllPieces() {
		board.clear();
	}
}
