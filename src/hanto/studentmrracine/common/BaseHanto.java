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
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.studentmrracine.validation.MoveValidator;
import hanto.studentmrracine.validation.MovesLeftValidator;

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
			HantoCoordinate to) throws HantoException, 
			HantoPrematureResignationException {

		// Convert the coordinates properly so the hashcode works
		from = from == null ? null : new HantoCoord(from);
		to = to == null ? null : new HantoCoord(to);		

		// Checks whether the current player has moves left or not
		checkMovesLeft(pieceType, from, to);

		if(lastMoveResult == MoveResult.OK){
			// Checks that the parameters are valid
			checkMoveParams(pieceType, from, to);

			preMoveCheck(pieceType, from, to);
			movePiece(HantoPieceFactory.getInstance().createPiece(pieceType, currentPlayerTurn), 
					from, to);
			switchTurn();
			postMoveCheck();
		}

		return lastMoveResult;
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
	 * Checks the moves left and throws an exception if necessary
	 * 
	 * @param pieceType the piece type
	 * @param from the starting coordinate
	 * @param to the destination coordinate
	 * @throws HantoPrematureResignationException
	 */
	protected void checkMovesLeft(HantoPieceType pieceType, 
			HantoCoordinate from, HantoCoordinate to) 
					throws HantoPrematureResignationException {

		PlayerInventory i;

		if(currentPlayerTurn == HantoPlayerColor.BLUE){
			i = blueInventory;
		} else {
			i = redInventory;
		}

		boolean hasMovesLeft = MovesLeftValidator.hasMovesLeft(this, currentPlayerTurn, i, board);

		if(hasMovesLeft && pieceType == null && from == null && to == null){
			throw new HantoPrematureResignationException();
		} else if(!hasMovesLeft && lastMoveResult == MoveResult.OK) {
			
			if(currentPlayerTurn == HantoPlayerColor.BLUE){
				lastMoveResult = MoveResult.RED_WINS;
			} else {
				lastMoveResult = MoveResult.BLUE_WINS;
			}
		}
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

		// Check a premature resignation

		if(to == null && pieceType == null){
			throw new HantoException(
					"Both the destination coordinate and piece type cannot be null");
		} else if (to == null){
			throw new HantoException("The destination coordinate cannot be null");
		} else if (pieceType == null){
			throw new HantoException("The piece type cannot be null");
		} else if(from != null && !board.isCoordOccupied(from)){

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
	 * Checks the move before it is made
	 * 
	 * @param pieceType the type of piece to be placed/moved
	 * @param from the starting coordinate 
	 * @param to the destination coordinate
	 * @throws HantoException
	 */
	protected void preMoveCheck(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		
		if(!MoveValidator.isLegal(this, pieceType, from, to)){
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
				removeFromInventory(piece, blueInventory);
			} else {
				removeFromInventory(piece, redInventory);
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
	 */
	protected void postMoveCheck() {

		// Checks the result to return
		if(isWin(redButterfly) && isWin(blueButterfly)){
			lastMoveResult = MoveResult.DRAW;
		} else if(isWin(redButterfly)){
			lastMoveResult = MoveResult.BLUE_WINS;
		} else if(isWin(blueButterfly)){
			lastMoveResult = MoveResult.RED_WINS;
		} else {
			lastMoveResult = MoveResult.OK;
		}
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
	 * @param piece the piece to remove from the inventory
	 * @param inventory
	 */
	protected void removeFromInventory(HantoPiece piece,
			PlayerInventory inventory) {

		inventory.removeFromInventory(piece);
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

	/**
	 * Getters
	 */

	/**
	 * @return the current turn number
	 */
	public int getTurnNumber() {
		return turnNumber;
	}

	/**
	 * @return which player moved first
	 */
	public HantoPlayerColor getMovedFirst() {
		return movedFirst;
	}

	/**
	 * @return the location of the blue butterfly
	 */
	public HantoCoordinate getBlueButterfly() {
		return blueButterfly;
	}

	/**
	 * @return the location of the red butterfly
	 */
	public HantoCoordinate getRedButterfly() {
		return redButterfly;
	}

	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * @return the color of the player currently moving
	 */
	public HantoPlayerColor getCurrentPlayerTurn() {
		return currentPlayerTurn;
	}
	
	/**
	 * @return the blue player's inventory
	 */
	public PlayerInventory getBlueInventory() {
		return blueInventory;
	}

	/**
	 * @return the red player's inventory
	 */
	public PlayerInventory getRedInventory() {
		return redInventory;
	}
	
	/**
	 * @return the last move result of this game
	 */
	public MoveResult getLastMoveResult() {
		return lastMoveResult;
	}
}
