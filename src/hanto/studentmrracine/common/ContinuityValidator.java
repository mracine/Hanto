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

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * A singleton class to check the continuity of a Hanto board
 * 
 * @author mrracine
 */
public class ContinuityValidator {

	private static final ContinuityValidator INSTANCE = new ContinuityValidator();
	
	private final List<HantoCoordinate> visited = new ArrayList<HantoCoordinate>();
	private final Stack<HantoCoordinate> toVisit = new Stack<HantoCoordinate>();
	
	/**
	 * Private constructor for singleton
	 */
	private ContinuityValidator(){
		//
	}
	
	/**
	 * @return the instance of this class
	 */
	public static ContinuityValidator getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Checks whether the resulting move would leave the board
	 * in a continuous configuration or not
	 * 
	 * @param b the Hanto board
	 * @param from the starting coordinate of the move
	 * @param start the starting coordinate of the traversal (typically the "to" of the move)
	 * @return whether or not the board is connected
	 */
	public boolean isContinuous(Board b, HantoCoordinate from, HantoCoordinate start){
		
		// Move the piece temporarily to test continuity
		b.movePiece(from, start);
		
		// Clear previous validation data
		clearData();
		
		// Add the initial coordinate to begin with
		toVisit.push(start);
		
		// Continue traversing until there are no unvisited
		// nodes in this configuration
		while(toVisit.size() != 0){
			
			HantoCoord currentNode = new HantoCoord(toVisit.pop());
			
			// Traverse for each neighbor
			for(HantoCoordinate c : b.getNeighbors(currentNode)){
				
				// If the coordinate has not been visited yet,
				// add it to the toVisit stack
				if(!visited.contains(c) && !toVisit.contains(c)){
					toVisit.push(c);
				}
			}
			
			// Add this node on the visited stack
			visited.add(currentNode);			
		}
		
		// Move the piece back
		b.movePiece(start, from);
		
		return visited.size() == b.numberOfPieces();
	}

	/**
	 * Clears both the visited and toVisit stacks
	 * @param b the new board
	 */
	private void clearData() {
		visited.clear();
		toVisit.clear();
	}
}
