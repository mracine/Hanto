/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.beta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentmrracine.common.Butterfly;
import hanto.studentmrracine.common.Sparrow;

/**
 * The implementation of a BetaHantoGame
 * 
 * The coordinates are quickly referenced in a 2D Hashmap.
 * 
 * Since a newly created HantoCoordinate object is unique
 * from another HantoCoordinate object with the same x and y
 * values, the 2D Hashmap works for correctly identifying
 * whether a coordinate is occupied or not
 * 
 * @author mrracine
 *
 */
public class BetaHantoGame implements HantoGame {

	/**
	 * A 2D HashMap of HantoCoordinates and their corresponding pieces
	 */
	private Map<Integer, Map<Integer, BetaCoordinate>> board = 
			new HashMap<Integer, Map<Integer, BetaCoordinate>>();
	
	/**
	 * A list of all coordinates on the board
	 * Used for quicker access when printing the board
	 */
	private List<BetaCoordinate> allCoordinates = new ArrayList<BetaCoordinate>();

	/**
	 * The locations of the two player's butterflies
	 */
	private HantoCoordinate blueButterfly;
	private HantoCoordinate redButterfly;

	/**
	 * The current player's turn color
	 */
	private HantoPlayerColor currentPlayerTurn;
	private HantoPlayerColor movesFirst;

	/**
	 * The number of currently playing turn
	 */
	private int turnNumber = 1;

	/**
	 * Initializes a BetaHantoGame
	 * @param movesFirst the first player to take a turn
	 * in a game of BetaHanto
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst){

		// Set the current player's turn
		this.movesFirst = movesFirst;
		currentPlayerTurn = movesFirst;
	}

	/**
	 * Makes a move in the Beta Hanto game
	 * For Beta Hanto, the from coordinate should always be null
	 * @return the result of the move
	 */
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {

		MoveResult result;

		// If it is the first move, validate it
		// Else, make sure that the player has placed the butterfly
		// Else, throw an exception if the move is not legal
		if (turnNumber == 1 && currentPlayerTurn == movesFirst){
			
			if(!firstMoveValid(to)){
				throw new HantoException("First move is invalid");
			}
		} else if (turnNumber == 4){
			
			if(currentPlayerTurn == HantoPlayerColor.BLUE){
				
				if(blueButterfly == null && pieceType != HantoPieceType.BUTTERFLY){
					throw new HantoException("Blue butterfly must be placed");
				}
			} else if(currentPlayerTurn == HantoPlayerColor.RED){
				
				if(redButterfly == null && pieceType != HantoPieceType.BUTTERFLY){
					throw new HantoException("Red butterfly must be placed");
				}
			}
		} else if (from != null){
			throw new HantoException("Cannot move in Beta Hanto");
		} else if(!isLegalMove(to)){
			throw new HantoException("Move is invalid");
		}

		// Make the move depending on the player color
		if(currentPlayerTurn == HantoPlayerColor.BLUE){
			
			if(pieceType == HantoPieceType.BUTTERFLY){
				blueButterfly = new BetaCoordinate(to.getX(), to.getY());
			}

		} else {

			if(pieceType == HantoPieceType.BUTTERFLY){
				redButterfly = new BetaCoordinate(to.getX(), to.getY());
			}
		}
		
		HantoPiece newPiece = null;
		
		// Create a new piece to be stored at this coordinate
		if(pieceType == HantoPieceType.BUTTERFLY){
			newPiece = new Butterfly(currentPlayerTurn);
		} else if (pieceType == HantoPieceType.SPARROW){
			newPiece = new Sparrow(currentPlayerTurn);
		} else {
			throw new HantoException("Invalid piece type");
		}
		
		// Create a new BetaCoordinate based on the HantoCoordinate
		BetaCoordinate newCoord = new BetaCoordinate(to, newPiece);
		
		// Add this coordinate to the board's list
		allCoordinates.add(newCoord);
		
		// Add the coordinate to the board hashmap
		if(!board.containsKey(to.getX())){
			Map<Integer, BetaCoordinate> temp = new HashMap<Integer, BetaCoordinate>();
			temp.put(newCoord.getY(), newCoord);
			board.put(newCoord.getX(), temp);
		} else {
			board.get(newCoord.getX()).put(newCoord.getY(), newCoord);
		}

		switchTurn(); // Switch the current player's turn

		// Checks the result to return
		if(isBlueWin()){
			result = MoveResult.BLUE_WINS;
		} else if(isRedWin()){
			result = MoveResult.RED_WINS;
		} else if(isDraw()){
			result = MoveResult.DRAW;
		} else {
			result = MoveResult.OK;
		}

		return result;
	}

	public HantoPiece getPieceAt(HantoCoordinate where) {

		HantoPiece piece;
		
		if(board.containsKey(where.getX()) && 
			board.get(where.getX()).containsKey(where.getY())){
			
			piece = board.get(where.getX()).get(where.getY()).getPiece();
		} else {
			piece = null;
		}
		
		return piece;
	}

	public String getPrintableBoard() {
		
		String formattedBoard = "";

		for(BetaCoordinate b : allCoordinates){
			formattedBoard += b.getPiece().getColor().toString();
			formattedBoard += " ";
			formattedBoard += b.getPiece().getType().getSymbol();
			formattedBoard += " at (";
			formattedBoard += b.getX() + ", ";
			formattedBoard += b.getY() + ")";
			formattedBoard += "\n";
		}
		
		return formattedBoard;
	}

	/**
	 * 
	 * @param coord the coordinate of the space
	 * @return whether the move is legal or not
	 */
	private boolean isLegalMove(HantoCoordinate coord){

		boolean isLegal;

		// In order for the move to be valid, one of the 
		// following spaces surrounding "coord" need to be occupied
		//
		// This is not code in comments, but I want to show what
		// the adjacent coordinates are for each piece
		//
		// (x, y - 1) // $codepro.audit.disable codeInComments
		// (x, y + 1) // $codepro.audit.disable codeInComments
		// (x - 1, y) // $codepro.audit.disable codeInComments
		// (x - 1, y + 1) // $codepro.audit.disable codeInComments
		// (x + 1, y) // $codepro.audit.disable codeInComments
		// (x + 1, y - 1) // $codepro.audit.disable codeInComments

		// If the coordinate already exists, the move is invalid
		// Else, if one of the surrounding hexagons contains a piece,
		// the move is valid
		// Else, the move is invalid
		if(board.containsKey(coord.getX()) && 
				board.get(coord.getX()).containsKey(coord.getY())){
			isLegal = false;
		} else if((board.containsKey(coord.getX()) && 
				board.get(coord.getX()).containsKey(coord.getY() - 1)) || 
				(board.containsKey(coord.getX()) && 
						board.get(coord.getX()).containsKey(coord.getY() + 1)) || 
						(board.containsKey(coord.getX() - 1) && 
								board.get(coord.getX() - 1).containsKey(coord.getY())) || 
								(board.containsKey(coord.getX() - 1) && 
										board.get(coord.getX() - 1).containsKey(coord.getY() + 1)) || 
										(board.containsKey(coord.getX() + 1) && 
												board.get(coord.getX() + 1).containsKey(coord.getY())) || 
												(board.containsKey(coord.getX() + 1) && 
														board.get(coord.getX() + 1).containsKey(coord.getY() - 1))){
			isLegal = true;
		} else {
			isLegal = false;
		}

		return isLegal;
	}

	/**
	 * Sets the currentPlayerTurn to the opposite player
	 * @return the current player color's turn
	 */
	private void switchTurn(){

		if(currentPlayerTurn == HantoPlayerColor.BLUE){
			currentPlayerTurn = HantoPlayerColor.RED;
		} else {
			currentPlayerTurn = HantoPlayerColor.BLUE;
		}

		// Increment the turns if a full turn has been played
		if(currentPlayerTurn == movesFirst){
			turnNumber++;
		}
	}

	/**
	 * Checks whether the resulting move is a win condition
	 * for BLUE by checking the RED BUTTERFLY
	 */
	private boolean isBlueWin(){

		boolean isWin;

		// This function is similar to the isLegalMove() function,
		// except that surrounding spaces need to all be occupied

		if(redButterfly == null){
			isWin = false;
		} else if((board.containsKey(redButterfly.getX()) && 
				board.get(redButterfly.getX()).containsKey(redButterfly.getY() - 1)) && 
				(board.containsKey(redButterfly.getX()) && 
						board.get(redButterfly.getX()).containsKey(redButterfly.getY() + 1)) && 
						(board.containsKey(redButterfly.getX() - 1) && 
								board.get(redButterfly.getX() - 1).containsKey(redButterfly.getY())) && 
								(board.containsKey(redButterfly.getX() - 1) && 
										board.get(redButterfly.getX() - 1).containsKey(redButterfly.getY() + 1)) && 
										(board.containsKey(redButterfly.getX() + 1) && 
												board.get(redButterfly.getX() + 1).containsKey(redButterfly.getY())) && 
												(board.containsKey(redButterfly.getX() + 1) && 
														board.get(redButterfly.getX() + 1).containsKey(redButterfly.getY() - 1))){
			isWin = true;
		} else {
			isWin = false;
		}

		return isWin;
	}

	/**
	 * Checks whether the resulting move is a win condition
	 * for RED by checking the BLUE BUTTERFLY
	 */
	private boolean isRedWin(){

		boolean isWin;

		if(blueButterfly == null){
			isWin = false;
		} else if((board.containsKey(blueButterfly.getX()) && 
				board.get(blueButterfly.getX()).containsKey(blueButterfly.getY() - 1)) && 
				(board.containsKey(blueButterfly.getX()) && 
						board.get(blueButterfly.getX()).containsKey(blueButterfly.getY() + 1)) && 
						(board.containsKey(blueButterfly.getX() - 1) && 
								board.get(blueButterfly.getX() - 1).containsKey(blueButterfly.getY())) && 
								(board.containsKey(blueButterfly.getX() - 1) && 
										board.get(blueButterfly.getX() - 1).containsKey(blueButterfly.getY() + 1)) && 
										(board.containsKey(blueButterfly.getX() + 1) && 
												board.get(blueButterfly.getX() + 1).containsKey(blueButterfly.getY())) && 
												(board.containsKey(blueButterfly.getX() + 1) && 
														board.get(blueButterfly.getX() + 1).containsKey(blueButterfly.getY() - 1))){
			isWin = true;
		} else {
			isWin = false;
		}

		return isWin;
	}
	
	/**
	 * @return whether or not the game is a draw
	 */
	private boolean isDraw() {
		
		// If the current turn number is 7, the game ends in a draw
		// The turn number is set before this is checked,
		// so this will check essentially at the very beginning of turn 7
		return turnNumber >= 7;
	}
	
	/**
	 * 
	 * @param to the desination coordinate
	 * @return whether this coordinate is (0, 0)
	 */
	private boolean firstMoveValid(HantoCoordinate to) {
		
		boolean isValid;
		
		if(to.getX() == 0 && to.getY() == 0){
			isValid = true;
		} else {
			isValid = false;
		}
		return isValid;
	}
}
