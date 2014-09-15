/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.beta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;

import java.util.HashMap;
import java.util.Map;

/**
 * The class that holds data structures and methods
 * pertaining to the board and its coordinates
 * 
 * @author mrracine
 *
 */
public class Board {

	/**
	 * A 2D HashMap of HantoCoordinates and their corresponding pieces
	 */
	private Map<Integer, Map<Integer, BetaCoordinate>> board = 
			new HashMap<Integer, Map<Integer, BetaCoordinate>>();
	
	/**
	 * @param coord the coordinate to look for
	 * @return whether the coordinate is occupied
	 */
	public boolean hasCoordinate(HantoCoordinate coord){
		
		boolean hasCoordinate;
		
		if (board.containsKey(coord.getX()) && 
				board.get(coord.getX()).containsKey(coord.getY())){
			hasCoordinate = true;
		} else {
			hasCoordinate = false;
		}
		
		return hasCoordinate;
	}
	
	/**
	 * @param coordX the x param of the coordinate
	 * @param coordY the y param of the coordinate
	 * @return whether the coordinate is occupied
	 */
	public boolean hasCoordinate(int coordX, int coordY){
		
		boolean hasCoordinate;
		
		if (board.containsKey(coordX) && 
				board.get(coordX).containsKey(coordY)){
			hasCoordinate = true;
		} else {
			hasCoordinate = false;
		}
		
		return hasCoordinate;
	}
	
	/**
	 * Returns a BetaCoordinate if the coordinate exists
	 * @param coord the specified coordinate
	 * @return the coordinate
	 */
	public BetaCoordinate getCoord(HantoCoordinate coord){
		
		BetaCoordinate betaCoord;
		
		if(hasCoordinate(coord)){
			betaCoord = board.get(coord.getX()).get(coord.getY());
		} else {
			betaCoord = null;
		}
		
		return betaCoord;
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
		
		if(hasCoordinate(coord)){
			piece = board.get(coord.getX()).get(coord.getY()).getPiece();
		} else {
			piece = null;
		}
		
		return piece;
	}
	
	/**
	 * Adds a piece to the board hashmap
	 * 
	 * @param newCoord the new BetaCoordinate to add
	 */
	public void addPiece(BetaCoordinate newCoord){
		
		if(!board.containsKey(newCoord.getX())){
			Map<Integer, BetaCoordinate> temp = new HashMap<Integer, BetaCoordinate>();
			temp.put(newCoord.getY(), newCoord);
			board.put(newCoord.getX(), temp);
		} else {
			board.get(newCoord.getX()).put(newCoord.getY(), newCoord);
		}
	}
}
