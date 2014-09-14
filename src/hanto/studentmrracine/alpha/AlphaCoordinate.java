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
import hanto.common.HantoPiece;

/**
 * The implementation of HantoCoordinate for an Alpha Hanto Game
 * 
 * Note: This implementation contains references to HantoPieces
 * Since the coordinate is a tile on the board, there piece can
 * be referenced from the coordinate. If no piece is there, the piece
 * should be null
 * @author mrracine
 *
 */
public class AlphaCoordinate implements HantoCoordinate {

	private int x;
	private int y;
	private HantoPiece piece;
	
	/**
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public AlphaCoordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @param c the coordinate
	 */
	public AlphaCoordinate(HantoCoordinate c){
		x = c.getX();
		y = c.getY();
	}
	
	/**
	 * 
	 * @param c the coordinate
	 * @param h the piece at this coordinate
	 */
	public AlphaCoordinate(HantoCoordinate c, HantoPiece h){
		x = c.getX();
		y = c.getY();
		piece = h;
	}
	
	/**
	 * @return the x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @return the piece at this coordinate
	 */
	public HantoPiece getPiece() {
		return piece;
	}
}
