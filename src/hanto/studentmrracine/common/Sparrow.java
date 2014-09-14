package hanto.studentmrracine.common;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class Sparrow implements HantoPiece {
	
	private final HantoPlayerColor color;
	
	/**
	 * The default constructor for a sparrow
	 */
	public Sparrow(HantoPlayerColor pieceColor){
		this.color = pieceColor;
	}

	/**
	 * @return the color of this piece
	 */
	public HantoPlayerColor getColor() {
		return color;
	}

	/**
	 * @return the type of this piece, a SPARROW
	 */
	public HantoPieceType getType() {
		return HantoPieceType.SPARROW;
	}
}
