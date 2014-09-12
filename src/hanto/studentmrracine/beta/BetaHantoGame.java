package hanto.studentmrracine.beta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

public class BetaHantoGame implements HantoGame {

	public BetaHantoGame(HantoPlayerColor movesFirst){
		//
	}
	
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}
	


	/**
	 * 
	 * @param coord the coordinate of the space
	 * @return whether the move is legal or not
	 */
	private boolean isLegalSpace(HantoCoordinate coord){
		
		// In order for the move to be valid, one of the 
		// following spaces surrounding "coord" need to be occupied
		// (x, y - 1)
		// (x, y + 1)
		// (x - 1, y)
		// (x - 1, y + 1)
		// (x + 1, y)
		// (x + 1, y - 1)
		
		return true;
	}
}
