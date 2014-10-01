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

/**
 * Interface for getting/setting the number of pieces for
 * a player's inventory
 * 
 * @author mrracine
 */
public interface PlayerInventory {

	/**
	 * Checks whether the player's butterfly has been placed
	 * or not
	 * 
	 * @return whether or not the butterfly has been placed
	 */
	boolean butterfliesInInventory();
	
	/**
	 * Places a butterfly on the board
	 */
	void placeButterfly();
	
	/**
	 * Returns whether or not there are crabs in the player's inventory
	 * @return whether or not there are crabs in the inventory
	 */
	boolean crabsInInventory();
	
	/**
	 * Places a crab on the board
	 */
	void placeCrab();
	
	/**
	 * Returns whether or not there are horses in the player's inventory
	 * @return whether or not there are horses in the inventory
	 */
	boolean horsesInInventory();
	
	/**
	 * Places a horse on the board
	 */
	void placeHorse();
	
	/**
	 * Returns whether or not there are cranes in the player's inventory
	 * @return whether or not there are cranes in the inventory
	 */
	boolean cranesInInventory();
	
	/**
	 * Places a crane on the board
	 */
	void placeCrane();
	
	/**
	 * Returns whether or not there are doves in the player's inventory
	 * @return whether or not there are doves in the inventory
	 */
	boolean dovesInInventory();
	
	/**
	 * Places a dove on the board
	 */
	void placeDove();
	
	/**
	 * Returns whether or not there are sparrows in the player's inventory
	 * @return whether or not there are sparrows in the inventory
	 */
	boolean sparrowsInInventory();
	
	/**
	 * Places a sparrow on the board
	 */
	void placeSparrow();
}
