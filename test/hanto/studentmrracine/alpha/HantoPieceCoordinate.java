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

public class HantoPieceCoordinate implements HantoCoordinate {

	private int x;
	private int y;
	private HantoPiece piece;
	
	/**
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public HantoPieceCoordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @param c the coordinate
	 */
	public HantoPieceCoordinate(HantoCoordinate c){
		this.x = c.getX();
		this.y = c.getY();
	}
	
	/**
	 * 
	 * @param c the coordinate
	 * @param h the piece at this coordinate
	 */
	public HantoPieceCoordinate(HantoCoordinate c, HantoPiece h){
		this.x = c.getX();
		this.y = c.getY();
		this.piece = h;
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
