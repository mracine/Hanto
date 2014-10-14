/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.tournament;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.studentmrracine.common.BaseHanto;
import hanto.studentmrracine.common.Board;
import hanto.studentmrracine.common.HantoUtil;
import hanto.studentmrracine.common.PlayerInventory;
import hanto.studentmrracine.epsilon.EpsilonHantoGame;
import hanto.studentmrracine.validation.SlideValidator;
import hanto.tournament.HantoMoveRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements a minimax strategy with alpha-beta pruning
 * 
 * @author mrracine
 */
public class MinimaxStrategy {
	
	/**
	 * A hashmap of heuristic values to move records
	 */
	private static Map<Double, HantoMoveRecord> moveHeuristics = 
			new HashMap<Double, HantoMoveRecord>();
	
	/**
	 * A hashmap of move records and game states
	 */
	private static Map<BaseHanto, HantoMoveRecord> moveStates = 
			new HashMap<BaseHanto, HantoMoveRecord>();

	/**
	 * Private constructor
	 */
	private MinimaxStrategy(){}

	/**
	 * Returns the move that the given player should make
	 * 
	 * @param g the game being played (typically a copy)
	 * @param depth the depth of the search
	 * @param myColor the player's color
	 * @return the best move to make
	 */
	public static HantoMoveRecord minimaxStrategy(BaseHanto g, int depth, HantoPlayerColor myColor){

		clearData();
		
		double best = minimax(g, depth, myColor, true);
		return moveHeuristics.get(best);
	}

	/**
	 * A recursive function that does a minimax search
	 * 
	 * @param g the game being played
	 * @param depth the depth of the search
	 * @param myColor the color of the current player
	 * @param maximizingPlayer whether or not this player is maximizing
	 * @return the heuristic value of a move
	 */
	private static double minimax(BaseHanto g, int depth, 
			HantoPlayerColor myColor, boolean maximizingPlayer){

		double bestValue = 0.0;
		List<HantoMoveRecord> possibleMoves = null;
		List<BaseHanto> gameStates = new ArrayList<BaseHanto>();

		if(depth == 0){

			bestValue = heuristic(g, myColor);

		} else {

			switch(myColor){
			case BLUE:
				possibleMoves = HantoUtil.movesLeft(g, myColor, g.getBlueInventory());
				break;
			case RED:
				possibleMoves = HantoUtil.movesLeft(g, myColor, g.getRedInventory());
				break;
			}

			// Get a list of all game states from this node
			for(HantoMoveRecord mr : possibleMoves){
				
				BaseHanto b = new EpsilonHantoGame(g);

				// Should never throw an exception, but still
				try {
					b.makeMove(mr.getPiece(), mr.getFrom(), mr.getTo());
				} catch (HantoException e){
					e.printStackTrace();
				}
				
				gameStates.add(b);
				
				// Put this pair in the hashmap
				moveStates.put(b, mr);
			}

			if (maximizingPlayer){

				// If this is the maximizing player, we want the move
				// that will give this player the best board combination
				bestValue = Double.NEGATIVE_INFINITY;	

				for(BaseHanto child : gameStates){
					
					double currentValue = Double.NEGATIVE_INFINITY;
					
					switch(myColor){
					case BLUE:
						currentValue = minimax(child, depth - 1, HantoPlayerColor.RED, false);
						break;
					case RED:
						currentValue = minimax(child, depth - 1, HantoPlayerColor.BLUE, false);
						break;
					}
					
					if(currentValue > bestValue){
						bestValue = currentValue;
						moveHeuristics.put(bestValue, moveStates.get(child));
					}
				}

			} else {

				// If this is NOT the maximizing player, we want the move
				// that will give the opposing player the worst board
				// combination
				bestValue = Double.POSITIVE_INFINITY;	

				for(BaseHanto child : gameStates){
					
					double currentValue = Double.POSITIVE_INFINITY;
					
					currentValue = minimax(child, depth - 1, myColor, true);
					
					if(currentValue < bestValue){
						bestValue = currentValue;
						moveHeuristics.put(bestValue, moveStates.get(child));
					}
				}
			}
		}

		return bestValue;
	}

	/**
	 * Returns a heuristic value based on the board's state and the given player
	 * 
	 * @param g the game being played
	 * @param player the player to get the heuristic value for
	 * @return the heuristic value
	 */
	private static double heuristic(BaseHanto g, HantoPlayerColor player){

		double heuristicTotal = 0.0;

		switch(player){
		case BLUE:
			// The total goes up for spaces surrounding red's butterfly
			heuristicTotal += getButterflyHeuristic(g.getBoard(), g.getRedButterfly());
			heuristicTotal += getButterflyBlockedHeuristic(g.getBoard(), g.getRedButterfly());

			// The total goes down for spaces surrounding blue's butterfly
			heuristicTotal -= getButterflyHeuristic(g.getBoard(), g.getBlueButterfly());
			heuristicTotal -= getButterflyBlockedHeuristic(g.getBoard(), g.getBlueButterfly());

			// The total goes up for the number of moves I have
			heuristicTotal += getMovesLeftHeuristic(g, 
					HantoPlayerColor.BLUE, g.getBlueInventory());

			// The total goes down for the number of moves my opponent has
			heuristicTotal -= getMovesLeftHeuristic(g, 
					HantoPlayerColor.RED, g.getRedInventory());

			break;
		case RED:
			// The total goes up for spaces surrounding blue's butterfly
			heuristicTotal += getButterflyHeuristic(g.getBoard(), g.getBlueButterfly());
			heuristicTotal += getButterflyBlockedHeuristic(g.getBoard(), g.getBlueButterfly());

			// The total goes down for spaces surrounding red's butterfly
			heuristicTotal -= getButterflyHeuristic(g.getBoard(), g.getRedButterfly());
			heuristicTotal -= getButterflyBlockedHeuristic(g.getBoard(), g.getRedButterfly());

			// The total goes up for the number of moves I have
			heuristicTotal += getMovesLeftHeuristic(g, 
					HantoPlayerColor.RED, g.getRedInventory());

			// The total goes down for the number of moves my opponent has
			heuristicTotal -= getMovesLeftHeuristic(g, 
					HantoPlayerColor.BLUE, g.getBlueInventory());

			break;
		}

		// Need to determine which pieces are more advantageous to move
		// Sparrows probably the most versatile
		// Horses move the farthest
		// Crabs are like pawns
		// Butterfly is like king
		
		// Multiply by 3 for hashcode
		int prime = 3;

		return heuristicTotal * prime;
	}

	/**
	 * Returns a heuristic estimate based on the pieces surrounding a butterfly
	 * 
	 * @param b the board
	 * @param butterflyLocation the butterfly's location
	 * @return a heuristic total
	 */
	private static double getButterflyHeuristic(Board b, HantoCoordinate butterflyLocation){

		return b.getOccupiedNeighbors(butterflyLocation).size() * 10;
	}

	/**
	 * Returns a heuristic based on if the butterfly can move or not
	 * @param b the board
	 * @param butterflyLocation the butterfly's location
	 * @return the heuristic
	 */
	private static double getButterflyBlockedHeuristic(Board b, HantoCoordinate butterflyLocation){

		double heuristic;
		boolean canMove = false;

		// Checks if the butterfly is not surrounded but unable to move
		for(HantoCoordinate c : HantoUtil.getAllNeighbors(butterflyLocation)){
			canMove = canMove || SlideValidator.canSlide(b, butterflyLocation, c);
		}

		if(!canMove){
			heuristic = 100.0;
		} else {
			heuristic = 0.0;
		}

		return heuristic;
	}

	/**
	 * Returns a heuristic value based on the number of moves a player has left
	 * @param g the game being played
	 * @param player the player to check for moves left
	 * @param inventory that player's inventory
	 * @return the heuristic value
	 */
	private static double getMovesLeftHeuristic(BaseHanto g, HantoPlayerColor player,
			PlayerInventory inventory){

		return HantoUtil.movesLeft(g, player, inventory).size() * 5;
	}
	
	/**
	 * Clears the hashmap data
	 */
	private static void clearData() {
		moveHeuristics.clear();
		moveStates.clear();
	}
}
