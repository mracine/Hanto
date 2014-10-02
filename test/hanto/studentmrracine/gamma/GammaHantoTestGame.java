package hanto.studentmrracine.gamma;

import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoTestGame;
import hanto.studentmrracine.common.HantoCoord;
import hanto.studentmrracine.common.HantoPieceFactory;

public class GammaHantoTestGame extends GammaHantoGame implements HantoGame, HantoTestGame {
	
	public GammaHantoTestGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
	}

	@Override
	public void initializeBoard(PieceLocationPair[] initialPieces) {

		HantoPiece p;

		clearBoard();
		
		for(PieceLocationPair pair : initialPieces){
			
			p = HantoPieceFactory.getInstance().createPiece(pair.pieceType, pair.player);
			movePiece(p, null, new HantoCoord(pair.location));
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
	
	/**
	 * Clears the board
	 */
	protected void clearBoard(){
		allCoordinates.clear();
		board.clearAllPieces();
		blueButterfly = null;
		redButterfly = null;
		blueInventory = new GammaInventory();
		redInventory = new GammaInventory();
	}
}
