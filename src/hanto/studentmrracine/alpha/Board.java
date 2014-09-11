package hanto.studentmrracine.alpha;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;

import java.util.Map;
import java.util.HashMap;

public class Board {

	private final Map<HantoCoordinate, HantoPiece> m = 
			new HashMap<HantoCoordinate, HantoPiece>();

	/**
	 * 
	 * @param c the coordinate for the piece
	 * @param p the piece to be placed
	 */
	public void placePiece(HantoCoordinate c, HantoPiece p)
			throws HantoException{
		// Place the piece if the coordinate is not already occupied
		if(!m.containsKey(c)){
			m.put(c, p);
		} else {
			throw new HantoException("Error: Coordinate already occupied");
		}
	}

	/**
	 * Returns the board as a string
	 * The string contains each piece, its color,
	 * and its coordinate.
	 * 
	 * @return the board printed as a string
	 */
	public String getBoardString(){
		String board = "";

		// Print the strings if the board is not empty
		if(!m.isEmpty()){
			for (HantoCoordinate c : m.keySet()){
				board += m.get(c).getColor().toString(); // The piece color
				board += " " + m.get(c).getType().getSymbol(); // The piece type's symbol
				board += " at (" + c.getX() + ", " + c.getY() + ")"; // The coordinate
				board += "\n"; // Add a newline
			}
		}

		return board;
	}

	/**
	 * Returns the Hanto piece at the given coordinate
	 * @param where the coordinate of the piece
	 * @return the piece at where
	 */
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return m.get(where);
	}
}
