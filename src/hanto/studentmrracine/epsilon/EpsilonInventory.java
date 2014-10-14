/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.epsilon;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.studentmrracine.common.Butterfly;
import hanto.studentmrracine.common.Crab;
import hanto.studentmrracine.common.Horse;
import hanto.studentmrracine.common.PlayerInventory;
import hanto.studentmrracine.common.Sparrow;

/**
 * Inventory for Epsilon Hanto
 * 
 * @author mrracine
 */
public class EpsilonInventory implements PlayerInventory {

	HantoPlayerColor owner;
	private int butterflies = 1;
	private int sparrows = 2;
	private int crabs = 6;
	private int horses = 4;

	/**
	 * @param owner the owner of this inventory
	 */
	public EpsilonInventory(HantoPlayerColor owner){
		this.owner = owner;
	}

	public boolean butterfliesInInventory() {
		return butterflies > 0;
	}

	public boolean crabsInInventory() {
		return crabs > 0;
	}

	public boolean horsesInInventory() {
		return horses > 0;
	}

	public boolean cranesInInventory() {
		return false;
	}

	public boolean dovesInInventory() {
		return false;
	}

	public boolean sparrowsInInventory() {
		return sparrows > 0;
	}

	public void removeFromInventory(HantoPiece piece) {

		switch(piece.getType()){
		case BUTTERFLY:
			if(butterfliesInInventory()){
				butterflies--;
			}
			break;
		case SPARROW:
			if(sparrowsInInventory()){
				sparrows--;
			}
			break;
		case CRAB:
			if(crabsInInventory()){
				crabs--;
			}
			break;
		case HORSE:
			if(horsesInInventory()){
				horses--;
			}
			break;
		default:
			break;
		}
	}
	
	public List<HantoPiece> piecesLeft() {

		List<HantoPiece> piecesLeft = new ArrayList<HantoPiece>();

		for(int i = 0; i < butterflies; i++){
			piecesLeft.add(new Butterfly(owner));
		}
		
		for(int i = 0; i < sparrows; i++){
			piecesLeft.add(new Sparrow(owner));
		}
		
		for(int i = 0; i < crabs; i++){
			piecesLeft.add(new Crab(owner));
		}
		
		for(int i = 0; i < horses; i++){
			piecesLeft.add(new Horse(owner));
		}
		
		return piecesLeft;
	}
}
