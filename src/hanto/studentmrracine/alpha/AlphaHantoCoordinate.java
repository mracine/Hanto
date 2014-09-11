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

/**
 * The AlphaHantoCoordinate class implements the HantoCoordinate
 * for this version of Hanto
 * 
 * @author mrracine
 *
 */
public class AlphaHantoCoordinate implements HantoCoordinate {
	
	private int x;
	private int y;
	
	/**
	 * The constructor for an AlphaHantoCoordinate
	 * 
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public AlphaHantoCoordinate(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
	/**
	 * @return the x-coordinate
	 */
	public int getX() {
		return x;
	}

	@Override
	/**
	 * @return the y-coordinate
	 */
	public int getY() {
		return y;
	}
}
