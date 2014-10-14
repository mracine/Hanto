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
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentmrracine.validation.MoveValidator;
import hanto.tournament.HantoMoveRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that contains static utility methods relating to Hanto
 * 
 * @author mrracine
 */
public class HantoUtil {

	/**
	 * Private constructor
	 */
	private HantoUtil(){}

	/**
	 * Retrieves all neighbors of a coordinate
	 * 
	 * @param c the coordinate to retrieve neighbors from
	 * @return the neighbors
	 */
	public static List<HantoCoordinate> getAllNeighbors(HantoCoordinate c){

		List<HantoCoordinate> neighbors = new ArrayList<HantoCoordinate>();

		neighbors.add(new HantoCoord(c.getX(), c.getY() - 1));
		neighbors.add(new HantoCoord(c.getX(), c.getY() + 1));
		neighbors.add(new HantoCoord(c.getX() - 1, c.getY()));
		neighbors.add(new HantoCoord(c.getX() - 1, c.getY() + 1));
		neighbors.add(new HantoCoord(c.getX() + 1, c.getY()));
		neighbors.add(new HantoCoord(c.getX() + 1, c.getY() - 1));

		return neighbors;
	}

	/**
	 * Returns a list of possible moves
	 * 
	 * @param game the hanto game to check for
	 * @param player the player making the move
	 * @param inventory the inventory of the player
	 * @return all moves available to that player
	 */
	public static List<HantoMoveRecord> movesLeft(BaseHanto game, 
			HantoPlayerColor player, PlayerInventory inventory){

		List<HantoMoveRecord> movesLeft = new ArrayList<HantoMoveRecord>();
		List<HantoCoordinate> perimeter = new ArrayList<HantoCoordinate>();

		perimeter = getPerimeterSpaces(game.getBoard());

		// The first move of the game
		if(perimeter.size() == 0){
			
			for(HantoPiece p : inventory.piecesLeft()){

				movesLeft.add(new HantoMoveRecord(p.getType(), null, 
						new HantoCoord(0, 0)));
			}
			
		} else {

			// Check the piece placements in the player's inventory
			for(HantoPiece p : inventory.piecesLeft()){

				for(HantoCoordinate c : perimeter){

					if(MoveValidator.isLegal(game, p.getType(), null, c)){
						movesLeft.add(new HantoMoveRecord(p.getType(), null,
								new HantoCoord(c)));
					}
				}
			}
			
			// Check the piece movements on the board
			for(HantoCoordinate current : game.getBoard().getPlayerOccupiedCoordinates(player)){

				HantoPieceType currentPieceType = game.getBoard().getPieceAt(current).getType();

				for(HantoCoordinate c : perimeter){

					if(MoveValidator.isLegal(game, currentPieceType, current, c)){
						movesLeft.add(new HantoMoveRecord(
								currentPieceType, new HantoCoord(current),
								new HantoCoord(c)));
					}
				}
			}
		}

		return movesLeft;
	}

	/**
	 * Returns all coordinates around the perimeter of the pieces
	 * 
	 * @param b the board
	 * @return a list containing all unoccupied perimeter coordinates
	 */
	private static List<HantoCoordinate> getPerimeterSpaces(Board b) {

		List<HantoCoordinate> perimeter = new ArrayList<HantoCoordinate>();
		List<HantoCoordinate> toVisit = b.getAllOccupiedCoordinates();

		for(HantoCoordinate current : toVisit){

			for(HantoCoordinate neighbor : HantoUtil.getAllNeighbors(current)){

				if(!toVisit.contains(neighbor) && !perimeter.contains(neighbor)){
					perimeter.add(neighbor);
				}
			}
		}

		return perimeter;
	}

	/**
	 * Checks whether the two coordinates are adjacent or not
	 * @param from the initial coordinate
	 * @param to the destination coordinate
	 * @return whether the two coordinate are adjacent
	 */
	public static boolean areAdjacent(HantoCoordinate from, HantoCoordinate to){

		return HantoUtil.getAllNeighbors(from).contains(to);
	}

	/**
	 * Checks whether the given coordinate is adjacent to an opposing butterfly
	 * 
	 * @param b the game being played
	 * @param myColor the current player's color
	 * @param c the coordinate to check
	 * @return whether the space is adjacent or not
	 */
	public static boolean isAdjacentOpposingButterfly(BaseHanto b, 
			HantoPlayerColor myColor, HantoCoordinate coord) {
		
		boolean isAdjacent = false;
		
		List<HantoCoordinate> neighbors = b.getBoard().getOccupiedNeighbors(coord);
		
		for(HantoCoordinate c : neighbors){
			
			if(b.getBoard().getPieceAt(c).getType() == HantoPieceType.BUTTERFLY && 
					myColor != b.getBoard().getPieceAt(c).getColor()){
				isAdjacent = true;
			}
		}
		
		return isAdjacent;
	}
}
