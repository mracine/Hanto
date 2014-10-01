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

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

/**
 * The abstract class for Hanto games. Note that the abstract class
 * has a board within it, but the implementation of the board
 * is left up to classes that extend this class
 * 
 * @author mrracine
 */
public abstract class BaseHanto implements HantoGame {

	/**
	 * The number of currently playing turn
	 */
	protected int turnNumber = 1;

	/**
	 * The current player's turn color
	 */
	protected HantoPlayerColor currentPlayerTurn;

	/**
	 * The color of the player that started the game
	 */
	protected HantoPlayerColor movedFirst;

	/**
	 * The locations of the two player's butterflies
	 */
	protected HantoCoordinate blueButterfly;
	protected HantoCoordinate redButterfly;

	/**
	 * The player inventories for Hanto 
	 */
	protected PlayerInventory blueInventory;
	protected PlayerInventory redInventory;

	/**
	 * The result of the last move
	 */
	protected MoveResult lastMoveResult = MoveResult.OK;
	
	/**
	 * The board and its methods
	 */
	protected Board board;

	/**
	 * A list of all coordinates on the board
	 * Used for quicker access when printing the board
	 */
	protected List<HantoCoordinate> allCoordinates = new ArrayList<HantoCoordinate>();

	/**
	 * The default constructor for a Hanto game
	 * Make sure that the board, blueInventory, and redInventory
	 * are initialized properly
	 * 
	 * @param movesFirst the color of the first player to move
	 */
	protected BaseHanto(HantoPlayerColor movesFirst){
		movedFirst = movesFirst;
		currentPlayerTurn = movedFirst;
	}

	/**
	 * Makes a move and returns the result of that move
	 * 
	 * @param pieceType the type of piece that will be moving
	 * @param from the coordinate where the piece will move from
	 * @param to the coordinate where the piece will move to
	 * @return the result of making the move
	 */
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {

		// Convert the coordinates properly so
		// the hashcode works
		from = from == null ? null : new HantoCoord(from);
		to = to == null ? null : new HantoCoord(to);		
		
		// Checks that the parameters are valid
		checkMoveParams(pieceType, from, to);

		preMoveCheck(pieceType, from, to);
		movePiece(HantoPieceFactory.getInstance().createPiece(pieceType, currentPlayerTurn), 
				from, to);
		switchTurn();
		
		return postMoveCheck();
	}

	/**
	 * @param where the coordinate where the piece is at
	 * @return the piece at the specified coordinate
	 */
	public HantoPiece getPieceAt(HantoCoordinate where) {
		where = where == null ? null : new HantoCoord(where);
		return board.getPieceAt(where);
	}

	/**
	 * @return the printable board in string form
	 */
	public String getPrintableBoard() {

		String formattedBoard = "";

		for(HantoCoordinate c : allCoordinates){
			formattedBoard += board.getPieceAt(c).getColor().toString();
			formattedBoard += " ";
			formattedBoard += board.getPieceAt(c).getType().getSymbol();
			formattedBoard += " at (";
			formattedBoard += c.getX() + ", ";
			formattedBoard += c.getY() + ")";
			formattedBoard += "\n";
		}

		return formattedBoard;
	}

	/**
	 * Validates that neither parameter is null and throws the appropriate exception
	 * if one or both are
	 * 
	 * @param to the coordinate the piece will be placed at/moved to
	 * @param from the destination coordinate
	 * @param pieceType the type of piece that will be placed/moved
	 * @throws HantoException
	 */
	protected void checkMoveParams(HantoPieceType pieceType, HantoCoordinate from, 
			HantoCoordinate to) throws HantoException {

		if(to == null && pieceType == null){
			throw new HantoException(
					"Both the destination coordinate and piece type cannot be null");
		} else if (to == null){
			throw new HantoException("The destination coordinate cannot be null");
		} else if (pieceType == null){
			throw new HantoException("The piece type cannot be null");
		} else if(from != null && !board.isCoordinateOccupied(from)){

			// If from is not null and the coordinate is not occupied,
			// an exception should be thrown
			throw new HantoException("'From' coordinate is not occupied");
		} else if(from != null && board.getPieceAt(from).getType() != pieceType){

			// If the piece is being moved and the piece at the from coordinate
			// is not the pieceType specified, an exception should be thrown
			throw new HantoException(
					"Piece at (" + from.getX() + ", " + from.getY() + ") is not a " +
							pieceType.toString());
		} else if(from != null && board.getPieceAt(from).getColor() != currentPlayerTurn){
			throw new HantoException(
					"Piece at (" + from.getX() + ", " + from.getY() + ") is not yours to move");
		}
	}

	/**
	 * Checks the first move of a Hanto game
	 * @param from the coordinate to move from (should be null)
	 * @param to the coordinate to move to (should be (0, 0) or one of its adjacents)
	 * @return whether or not the first turn move is legal
	 */
	protected boolean isFirstTurnLegal(HantoCoordinate from, HantoCoordinate to) {

		boolean isLegal;

		if(movedFirst == currentPlayerTurn){

			// First move of game needs to be at (0, 0)
			if(to.getX() != 0 || to.getY() != 0){
				isLegal = false;
			} else {
				isLegal = true;
			}

		} else {

			// This place needs to be adjacent to (0, 0)
			if(!board.isAnAdjacentSpaceOccupied(to)){
				isLegal = false;
			} else {
				isLegal = true;
			}
		}

		return isLegal;
	}

	/**
	 * Checks that the butterfly has been placed by turn 4
	 * 
	 * @param pieceType the type of piece to be placed
	 * @return whether or not the move is legal
	 */
	protected boolean isFourthTurnLegal(HantoPieceType pieceType) {

		boolean isLegal = true;

		// Check that the players are placing a butterfly on the fourth turn
		if(currentPlayerTurn == HantoPlayerColor.BLUE){

			if(blueButterfly == null && pieceType != HantoPieceType.BUTTERFLY){
				isLegal = false;
			}

		} else {

			if(redButterfly == null && pieceType != HantoPieceType.BUTTERFLY){
				isLegal = false;
			}
		}

		return isLegal;
	}

	/**
	 * Checks the move before it is made
	 * 
	 * @param pieceType the type of piece to be placed/moved
	 * @param from the starting coordinate 
	 * @param to the destination coordinate
	 * @throws HantoException
	 */
	protected void preMoveCheck(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {

		// Check that a player has not already won
		if(lastMoveResult != MoveResult.OK){
			throw new HantoException("Game has ended: " + lastMoveResult.toString());
		}
		
		// Check the player inventories
		// Also check that the butterfly has been placed if a movement is to be made
		if(from == null){
			if(currentPlayerTurn == HantoPlayerColor.BLUE && 
					!isInInventory(pieceType, blueInventory)){
				throw new HantoException("Piece is not in Blue's inventory");
			} else if (!isInInventory(pieceType, redInventory)){
				throw new HantoException("Piece is not in Red's inventory");
			}
		} else {
			if(currentPlayerTurn == HantoPlayerColor.BLUE &&
					blueButterfly == null){
				throw new HantoException("Blue movement cannot be made until butterfly is placed");
			} else if (redButterfly == null){
				throw new HantoException("Red movement cannot be made until butterfly is placed");
			}
		}

		// Check the move placement (regardless of placement/movement)
		if(!isLegalMovement(pieceType, from, to)){
			throw new HantoException("Movement is illegal");
		}
	}

	/**
	 * Places/moves the piece
	 * 
	 * @param piece the type of piece to be placed/moved
	 * @param from the starting coordinate
	 * @param to the destination coordinate
	 */
	protected void movePiece(HantoPiece piece, HantoCoordinate from,
			HantoCoordinate to) {

		// If the piece is a butterfly, remember its coordinate accordingly
		if(piece.getType() == HantoPieceType.BUTTERFLY){
			switch(piece.getColor()){
			case BLUE:
				blueButterfly = to;
				break;
			case RED:
				redButterfly = to;
				break;
			}
		}

		// If from is null, simply place the piece. Else, move the piece
		// Also add/remove the coordinates from the list as necessary
		if(from == null){

			if(piece.getColor() == HantoPlayerColor.BLUE){
				removeFromInventory(piece.getType(), blueInventory);
			} else {
				removeFromInventory(piece.getType(), redInventory);
			}

			board.placePiece(to, piece);
			allCoordinates.add(to);
		} else {
			board.movePiece(from, to);
			allCoordinates.remove(from);
			allCoordinates.add(to);
		}
	}

	/**
	 * Checks the result of the move
	 * @return the result of the move
	 */
	protected MoveResult postMoveCheck() {

		MoveResult result;
		
		// Checks the result to return
		if(isWin(redButterfly) && isWin(blueButterfly)){
			result = MoveResult.DRAW;
		} else if(isWin(redButterfly)){
			result = MoveResult.BLUE_WINS;
		} else if(isWin(blueButterfly)){
			result = MoveResult.RED_WINS;
		} else {
			result = MoveResult.OK;
		}
		
		lastMoveResult = result;

		return result;
	}

	/**
	 * Checks that the placement of this piece is legal
	 * 
	 * @param pieceType the type of the piece being checked (for Butterfly, turn 4)
	 * @param from the starting coordinate
	 * @param to the coordinate to place the piece
	 * @return whether or not the placement of the piece is legal
	 */
	protected boolean isLegalMovement(HantoPieceType pieceType, 
			HantoCoordinate from, HantoCoordinate to) {

		boolean isLegal = true;

		if (turnNumber == 4){
			
			// If the turn number is greater than or equal to 4,
			// the butterfly must be placed
			isLegal = isFourthTurnLegal(pieceType);
			
		} else if(board.numberOfPieces() < 2){
			
			// If the number of pieces is less than 2, it is
			// the first turn. Works better with test games
			isLegal = isFirstTurnLegal(from, to);
			
		} else {

			// If from is null, the piece is being placed. Else, it is being moved
			if(from == null){
				isLegal = isLegal &&
						board.isAnAdjacentSpaceOccupied(to) && 
						!board.hasAdjacentOpposingPieces(to, currentPlayerTurn);
			} else {
				
				// Check that the piece can slide and leaves
				// a continuous configuration
				isLegal = isLegal && areAdjacent(from, to) &&
						SlideValidator.getInstance().canSlide(board, from, to) &&
						ContinuityValidator.getInstance().isContinuous(board, from, to);
			}
		}

		return isLegal;
	}

	/**
	 * Checks whether the two coordinates are adjacent or not
	 * @param from the initial coordinate
	 * @param to the destination coordinate
	 * @return whether the two coordinate are adjacent
	 */
	protected boolean areAdjacent(HantoCoordinate from, HantoCoordinate to){

		List<HantoCoordinate> adjacents = new ArrayList<HantoCoordinate>();

		adjacents.add(new HantoCoord(from.getX(), from.getY() - 1));
		adjacents.add(new HantoCoord(from.getX(), from.getY() + 1));
		adjacents.add(new HantoCoord(from.getX() - 1, from.getY()));
		adjacents.add(new HantoCoord(from.getX() - 1, from.getY() + 1));
		adjacents.add(new HantoCoord(from.getX() + 1, from.getY()));
		adjacents.add(new HantoCoord(from.getX() + 1, from.getY() - 1));

		return adjacents.contains(to);
	}
	
	/**
	 * Checks whether the given piece's coordinate is surrounded
	 * If the blue butterfly coordinate is given, we are checking for
	 * a RED WIN.
	 * 
	 * If the red butterfly coordinate is given, we are checking for
	 * a BLUE WIN.
	 * 
	 * @param butterflyCoord the piece to check if it is surrounded
	 * @return whether or not the piece is surrounded (win condition for butterfly)
	 */
	protected boolean isWin(HantoCoordinate butterflyCoord) {

		boolean isWin;

		if(butterflyCoord == null){
			isWin = false;
		} else {
			isWin = board.isSurrounded(butterflyCoord);
		}

		return isWin;
	}
	
	/**
	 * Removes a specified piece from the given inventory
	 * 
	 * @param pieceType the piece to remove from the inventory
	 * @param inventory
	 */
	protected void removeFromInventory(HantoPieceType pieceType,
			PlayerInventory inventory) {

		switch(pieceType){
		case BUTTERFLY:
			inventory.placeButterfly();
			break;
		case CRAB:
			inventory.placeCrab();
			break;
		case HORSE:
			inventory.placeHorse();
			break;
		case CRANE:
			inventory.placeCrane();
			break;
		case DOVE:
			inventory.placeDove();
			break;
		case SPARROW:
			inventory.placeSparrow();
			break;
		}		
	}

	/**
	 * Checks that the placement is legal according to the player's inventory
	 * 
	 * @param pieceType the type of piece to check the inventory for
	 * @param inventory the inventory to check
	 * @return whether or not the piece is in the inventory
	 */
	protected boolean isInInventory(HantoPieceType pieceType, PlayerInventory inventory) {

		boolean isInInventory = false;

		switch(pieceType){
		case BUTTERFLY:
			isInInventory = inventory.butterfliesInInventory();
			break;
		case CRAB:
			isInInventory = inventory.crabsInInventory();
			break;
		case HORSE:
			isInInventory = inventory.horsesInInventory();
			break;
		case CRANE:
			isInInventory = inventory.cranesInInventory();
			break;
		case DOVE:
			isInInventory = inventory.dovesInInventory();
			break;
		case SPARROW:
			isInInventory = inventory.sparrowsInInventory();
			break;
		}

		return isInInventory;
	}

	/**
	 * Switches the current player's turn
	 */
	protected void switchTurn() {

		// Switches the current player's turn
		if(currentPlayerTurn == HantoPlayerColor.BLUE){
			currentPlayerTurn = HantoPlayerColor.RED;
		} else {
			currentPlayerTurn = HantoPlayerColor.BLUE;
		}

		// Increment the turns if a full turn has been played
		if(currentPlayerTurn == movedFirst){
			turnNumber++;
		}
	}
}
