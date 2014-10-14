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

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * This is a singleton class to provide for any HantoPiece (implemented thus far)
 * 
 * @author mrracine
 */
public class HantoPieceFactory {

	private static final HantoPieceFactory INSTANCE = new HantoPieceFactory();

	/**
	 * Default private constructor
	 */
	private HantoPieceFactory(){
		//
	}

	/**
	 * @return the instance
	 */
	public static HantoPieceFactory getInstance(){
		return INSTANCE;
	}

	/**
	 * Creates a piece as specified
	 * 
	 * @param newPiece the type of piece being created
	 * @param color the color of the piece being created
	 * @return the piece created
	 */
	public HantoPiece createPiece(HantoPieceType newPiece, HantoPlayerColor color) {

		HantoPiece piece = null;

		switch(newPiece){
		case BUTTERFLY:
			piece = new Butterfly(color);
			break;
		case SPARROW:
			piece = new Sparrow(color);
			break;
		case CRAB:
			piece = new Crab(color);
			break;
		case HORSE:
			piece = new Horse(color);
		default:
			break;
		}

		return piece;
	}	
}
