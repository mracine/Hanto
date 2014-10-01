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

import hanto.studentmrracine.common.PlayerInventory;

/**
 * The implementation of a PlayerInventory for Gamma Hanto
 * Contains 1 Butterfly and 5 Sparrows
 * 
 * @author mrracine
 */
public class GammaInventory implements PlayerInventory {
	
	private int butterflies = 1;
	private int sparrows = 5;

	/**
	 * Checks whether or not the butterfly has been placed
	 */
	public boolean butterfliesInInventory() {
		return butterflies > 0;
	}

	/**
	 * Places a butterfly on the board
	 */
	public void placeButterfly() {
		if(butterfliesInInventory()){
			butterflies--;
		}
	}
	
	/**
	 * Checks whether or not there are sparrows in the inventory
	 */
	public boolean sparrowsInInventory() {
		return sparrows > 0;
	}

	/**
	 * Places a sparrow on the board
	 */
	public void placeSparrow() {
		if(sparrowsInInventory()){
			sparrows--;
		}
	}

	
	/**
	 * The methods below are not needed in Gamma Hanto but may be needed
	 * for later versions
	 */
	
	
	/**
	 * Returns false for Gamma Hanto
	 */
	public boolean crabsInInventory() {
		return false;
	}

	/**
	 * Does nothing for Gamma Hanto
	 */
	public void placeCrab() {}

	/**
	 * Returns false for Gamma Hanto
	 */
	public boolean horsesInInventory() {
		return false;
	}

	/**
	 * Does nothing for Gamma Hanto
	 */
	public void placeHorse() {}

	/**
	 * Returns false for Gamma Hanto
	 */
	public boolean cranesInInventory() {
		return false;
	}

	/**
	 * Does nothing for Gamma Hanto
	 */
	public void placeCrane() {}

	/**
	 * Returns false for Gamma Hanto
	 */
	public boolean dovesInInventory() {
		return false;
	}

	/**
	 * Does nothing for Gamma Hanto
	 */
	public void placeDove() {}
}
