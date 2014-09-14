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

/**
 * The implementation of a HantoCoordinate for
 * a BetaHantoGame
 * 
 * The coordinates contain references to the pieces
 * that occupy that space
 * 
 * @author mrracine
 *
 */
public class BetaCoordinate implements HantoCoordinate {

	private int x;
	private int y;
	private HantoPiece piece;

	/**
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public BetaCoordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param piece the piece associated with this coordinate
	 */
	public BetaCoordinate(int x, int y, HantoPiece piece){
		this.x = x;
		this.y = y;
		this.piece = piece;
	}
	
	/**
	 * 
	 * @param c the coordinate
	 */
	public BetaCoordinate(HantoCoordinate c){
		x = c.getX();
		y = c.getY();
	}
	
	/**
	 * 
	 * @param c the coordinate
	 * @param piece the piece associated with this coordinate
	 */
	public BetaCoordinate(HantoCoordinate c, HantoPiece piece){
		x = c.getX();
		y = c.getY();
		this.piece = piece;
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
	 * @return the piece associated with this coordinate
	 */
	public HantoPiece getPiece() {
		return piece;
	}

	/**
	 * @param piece the piece that will be placed at this coordinate
	 */
	public void setPiece(HantoPiece piece) {
		this.piece = piece;
	}
}
