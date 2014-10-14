package hanto.common;

import static org.junit.Assert.*;
import hanto.studentmrracine.common.HantoCoord;
import hanto.tournament.HantoMoveRecord;

import org.junit.Test;

public class CommonTests {

	/**
	 * HantoCoord tests
	 */
	@Test
	public void testHantoCoordEquals(){

		// Other cases have already been covered
		HantoCoord coord1 = new HantoCoord(0, 0);
		assertFalse(coord1.equals(null));
		
		assertFalse(coord1.equals(new Object()));
	}
	
	@Test(expected = HantoException.class)
	public void testHantoException() throws HantoException {
		throw new HantoException("", null);
	}
	
	@Test
	public void testHantoMoveRecord(){
		HantoMoveRecord mr = new HantoMoveRecord(HantoPieceType.BUTTERFLY, 
				null, new HantoCoord(0, 0));
		
		assertEquals(HantoPieceType.BUTTERFLY, mr.getPiece());
		assertNull(mr.getFrom());
		assertEquals(new HantoCoord(0, 0), mr.getTo());
	}
}
