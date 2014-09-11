package hanto.studentmrracine.alpha;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class Butterfly implements HantoPiece {

	private final HantoPlayerColor color;
	
	/**
	 * Sets the color of the butterfly
	 * @param pieceColor the color of the butterfly
	 */
	public Butterfly(HantoPlayerColor pieceColor){
		this.color = pieceColor;
	}
	
	/**
	 * @return the color of this butterfly
	 */
	public HantoPlayerColor getColor() {
		return color;
	}

	/**
	 * @return the type of this piece, a BUTTERFLY
	 */
	public HantoPieceType getType() {
		return HantoPieceType.BUTTERFLY;
	}
}
