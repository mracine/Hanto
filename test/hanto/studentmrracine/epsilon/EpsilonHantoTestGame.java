package hanto.studentmrracine.epsilon;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoTestGame;
import hanto.common.MoveResult;
import hanto.studentmrracine.common.HantoPieceFactory;

public class EpsilonHantoTestGame extends EpsilonHantoGame implements HantoGame, 
HantoTestGame {

	public EpsilonHantoTestGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		return super.makeMove(pieceType, from, to);
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return super.getPieceAt(where);
	}

	@Override
	public String getPrintableBoard() {
		return super.getPrintableBoard();
	}

	@Override
	public void initializeBoard(PieceLocationPair[] initialPieces) {

		HantoPiece p;

		for(PieceLocationPair pair : initialPieces){

			p = HantoPieceFactory.getInstance().createPiece(pair.pieceType, pair.player);
			movePiece(p, null, pair.location);
		}
	}

	@Override
	public void setTurnNumber(int turnNumber) {
		super.turnNumber = turnNumber;
	}

	@Override
	public void setPlayerMoving(HantoPlayerColor player) {
		currentPlayerTurn = player;
	}
}
