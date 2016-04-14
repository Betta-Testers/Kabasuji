package model;

import java.awt.Color;

import junit.framework.TestCase;

/**
 * 
 * @author hejohnson
 *
 */

public class TestTiles extends TestCase {	
	public void testBoardTile() {
		BoardTile bt = new BoardTile(1, 1);
		
		bt.setMouseOverColor(true);
		assertEquals(Color.GREEN, bt.color);
		bt.setMouseOverColor(false);
		assertEquals(Color.RED, bt.color);
		bt.resetColor();
		assertEquals(Color.WHITE, bt.color);
		assertEquals("board", bt.toString());
	}
	
	public void testReleaseTile() {
		ReleaseTile rt = new ReleaseTile(1, 1, 4, Color.BLUE);
		assertEquals(Color.BLUE, rt.getColorSet());
		assertEquals(4, rt.getNumber());
		assertEquals("release", rt.toString());
	}
	
	public void testSimpleTiles() {
		EmptyTile et = new EmptyTile(1,1);
		assertEquals(Color.LIGHT_GRAY, et.color);
		assertEquals("empty", et.toString());
		et.setMouseOverColor(true);
		assertEquals(Color.GREEN, et.color);
		et.resetColor();
		assertEquals(Color.LIGHT_GRAY, et.color);
		
		LightningTile lt = new LightningTile(1,1);
		assertEquals(Color.GREEN, lt.color);
		assertEquals("lightning", lt.toString());
		
		HintTile ht = new HintTile(1,1);
		assertEquals(Color.DARK_GRAY, ht.color);
		assertEquals("hint", ht.toString());
	}
	
	public void testPieceTile() {
		Piece piece = new Piece(0);
		assertNotNull(piece.getColor());
		PieceTile tile = piece.tiles[1];
		PieceTile origin = piece.tiles[0];
		
		assertEquals(piece.tiles[1].getColInPiece(), 1);
		assertEquals(piece.tiles[1].getRowInPiece(), 0);
		
		tile.updateColInPiece(-1);
		tile.updateRowInPiece(-1);
		
		try{
			origin.updateColInPiece(-1);
			fail();
		}catch (RuntimeException e){

		}
		
		try{
			origin.updateRowInPiece(-1);
			fail();
		}catch (RuntimeException e){

		}
		
		assertEquals("piece", tile.toString());
		
		assertEquals(new Color(240, 0, 0), tile.color);
		
		origin.setLocation(5, 7);
		assertEquals(origin.colOnBoard, 7);
		assertEquals(origin.rowOnBoard, 5);
		
	}
	
}
