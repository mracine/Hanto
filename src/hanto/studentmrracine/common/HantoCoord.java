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
 * The new HantoCoordinate implementation for Gamma and Delta Hanto.
 * Removes the ability for a coordinate to know about the piece that
 * occupies its space since the board HashMap has been reworked
 * 
 * @author mrracine
 */
public class HantoCoord implements HantoCoordinate {

	private int x;
	private int y;

	/**
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public HantoCoord(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @param c the coordinate
	 */
	public HantoCoord(HantoCoordinate c){
		x = c.getX();
		y = c.getY();
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
	 * @param other the other object to compare this one to
	 * @return whether the two objects are equal
	 */
	@Override
	public boolean equals(Object other){

		boolean isEqual;

		if(other == null){
			isEqual = false;
		} else if(other instanceof HantoCoordinate){

			HantoCoord otherCoord = 
					new HantoCoord((HantoCoordinate) other);

			if((otherCoord.getX() == x) && (otherCoord.getY() == y)){
				isEqual = true;
			} else {
				isEqual = false;
			}

		} else {
			isEqual = false;
		}

		return isEqual;
	}

	/**
	 * @return the hashcode for this object
	 */
	@Override
	public int hashCode(){

		int hash = 3;
		final int prime = 31;
		
		hash = (prime * hash) + x;
		hash = (prime * hash) + y;

		return hash;
	}
}
