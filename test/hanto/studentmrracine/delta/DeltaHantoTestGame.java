package hanto.studentmrracine.delta;

import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoTestGame;
import hanto.studentmrracine.common.HantoPieceFactory;
import hanto.studentmrracine.delta.DeltaHantoGame;

public class DeltaHantoTestGame extends DeltaHantoGame implements HantoGame,
HantoTestGame {

	public DeltaHantoTestGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
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
		currentPlayerTurn = movedFirst;
	}

	@Override
	public void setPlayerMoving(HantoPlayerColor player) {
		currentPlayerTurn = player;
	}
}
