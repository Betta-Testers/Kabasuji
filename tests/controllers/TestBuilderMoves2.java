package controllers;

import java.io.File;

import view.BuilderView;
import model.AbstractTile;
import model.Board;
import model.BoardTile;
import model.Bullpen;
import model.EmptyTile;
import model.Piece;
import model.PieceTile;
import model.PuzzleLevel;
import app.Builder;
import junit.framework.TestCase;

/**
 * 
 * @author awharrison
 *
 */
public class TestBuilderMoves2 extends TestCase {
	
	PuzzleLevel pl;
	Builder build;
	BuilderView bv;
	Board b;
	
	@Override
	public void setUp(){
		new File("./imbriusLevelTESTING/").mkdirs();
		build = new Builder("./imbriusLevelTESTING/");
		build.createPuzzleLevel();
		pl = (PuzzleLevel)build.getCurrentLevel();
		bv = build.getBuilderView();
		pl.setBoard(new Board());
		b = pl.getBoard();
	}
	
	@Override
	public void tearDown(){
		File dir = new File("./imbriusLevelTESTING/");
		for(File file: dir.listFiles()) file.delete();
		dir.delete();
	}
	
	public void testPlacePieceRemovePiece() {
		// get the number of board tiles on the board
		assertEquals(0, b.getNumBoardTiles());
		
		// create a move
		Move m;
		for(int i = 0; i < 12; i++){
			for(int j = 0; j < 12; j++){
				m = new SwapTileEmptyToBoardMove(bv, (EmptyTile)b.getTileAt(i*32, j*32), pl);
				m.doMove();
			}
		}
		
		// verify that the pile selected for placement is a board tile
		AbstractTile at = b.getTileAt(6, 6);
		assertEquals("board", at.getTileType());
		assertEquals(144, b.getNumBoardTiles());
		
		// create and perform place piece move
		m = new MovePieceOnBoardMove(pl, (BoardTile)b.getTileAt(144, 144), new Piece(2));
		m.doMove();
		// verify that the tile is now a piece tile
		at = b.getTileAt(144, 144);
		assertEquals("piece", at.getTileType());
		
		// verify that the number of board tiles has decreased by 6
		assertEquals(138, b.getNumBoardTiles());
		
		// remove the piece from the board
		m = new MovePieceOffBoardMove(pl, ((PieceTile)b.getTileAt(144, 144)).getPiece());
		m.doMove();
		// verify that the tile is now a piece tile
		at = b.getTileAt(144, 144);
		assertEquals("board", at.getTileType());
		
		// test undo
		m.undo();
		at = b.getTileAt(144, 144);
		assertEquals("piece", at.getTileType());
		
		// test redo
		m.redo();
		at = b.getTileAt(144, 144);
		assertEquals("board", at.getTileType());
		
	}
	
	public void testScore() {
		// set move limit and board
		pl.setMoveLimit(4);
		Move m;
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 6; j++){
				m = new SwapTileEmptyToBoardMove(bv, (EmptyTile)b.getTileAt(i*b.getTileSize(), j*b.getTileSize()), pl);
				m.doMove();
			}
		}
		assertEquals(24, b.getNumBoardTiles());
		
		// move 1
		AbstractTile at = b.getTileAt(0, 2*b.getTileSize());
		m = new MovePieceOnBoardMove(pl, (BoardTile)at, new Piece(1));
		m.doMove();
		// verify that the tile is now a piece tile
		at = b.getTileAt(0, 2*b.getTileSize());
		assertEquals("piece", at.getTileType());
		assertEquals(18, b.getNumBoardTiles());
		pl.incrementMovesMade();
		// Number of stars earned is 0
		assertEquals(0, pl.getStarsEarned());
		assertFalse(pl.checkStatus());
		
		// move 2
		at = b.getTileAt(b.getTileSize(), 2*b.getTileSize());
		m = new MovePieceOnBoardMove(pl, (BoardTile)at, new Piece(1));
		m.doMove();
		// verify that the tile is now a piece tile
		at = b.getTileAt(b.getTileSize(), 2*b.getTileSize());
		assertEquals("piece", at.getTileType());
		assertEquals(12, b.getNumBoardTiles());
		pl.incrementMovesMade();
		// Number of stars earned is 0
		assertEquals(0, pl.getStarsEarned());
		assertFalse(pl.checkStatus());
		
		// move 3
		at = b.getTileAt(2*b.getTileSize(), 2*b.getTileSize());
		m = new MovePieceOnBoardMove(pl, (BoardTile)at, new Piece(1));
		m.doMove();
		// verify that the tile is now a piece tile
		at = b.getTileAt(2*b.getTileSize(), 2*b.getTileSize());
		assertEquals("piece", at.getTileType());
		assertEquals(6, b.getNumBoardTiles());
		pl.incrementMovesMade();
		// Number of stars earned is 1
		assertEquals(1, pl.getStarsEarned());
		assertFalse(pl.checkStatus());
		
		// move 4
		at = b.getTileAt(3*b.getTileSize(), 2*b.getTileSize());
		m = new MovePieceOnBoardMove(pl, (BoardTile)at, new Piece(1));
		m.doMove();
		// verify that the tile is now a piece tile
		at = b.getTileAt(3*b.getTileSize(), 2*b.getTileSize());
		assertEquals("piece", at.getTileType());
		assertEquals(0, b.getNumBoardTiles());
		pl.incrementMovesMade();
		// TODO check in on getStarsEarned
		// Number of stars earned is 2
		assertEquals(2, pl.getStarsEarned());
		assertTrue(pl.checkStatus());
		
		// test undo move
		m.undo();
		at = b.getTileAt(3*b.getTileSize(), 2*b.getTileSize());
		assertEquals("board", at.getTileType());
		assertEquals(6, b.getNumBoardTiles());
		pl.incrementMovesMade();
		// Number of stars earned is 3...?
		assertEquals(3, pl.getStarsEarned());
		assertFalse(pl.checkStatus());
		
		// test redo move
		m.redo();
		at = b.getTileAt(3*b.getTileSize(), 2*b.getTileSize());
		assertEquals("piece", at.getTileType());
		assertEquals(0, b.getNumBoardTiles());
		pl.incrementMovesMade();
		// TODO check in on getStarsEarned
		// Number of stars earned is 2
		assertEquals(2, pl.getStarsEarned());
		assertTrue(pl.checkStatus());
	}
	
	public void testBullpen() {
		Bullpen bp = new Bullpen();
		bp.addSinglePiece(1);
		bp.addSinglePiece(3);
		bp.addSinglePiece(7);
		bp.addSinglePiece(2);
		
		assertEquals(4, bp.numAvailablePieces());
		pl.setBullpen(bp);
		assertTrue(pl.getBullpen().setSelectedPiece(2));
		
		// get the number of board tiles on the board
		assertEquals(0, b.getNumBoardTiles());
		
		// create a move
		Move m;
		for(int i = 0; i < 12; i++){
			for(int j = 0; j < 12; j++){
				m = new SwapTileEmptyToBoardMove(bv, (EmptyTile)b.getTileAt(i*b.getTileSize(), j*b.getTileSize()), pl);
				m.doMove();
			}
		}
		
		// verify that the pile selected for placement is a board tile
		AbstractTile at = b.getTileAt(4*b.getTileSize(), 5*b.getTileSize());
		assertEquals(144, b.getNumBoardTiles());
		
		// TODO selectedPiece not decrementing...
		m = new PlacePieceOnBoardFromBullpenMove(pl, at);
		assertTrue(m.doMove());
		assertEquals(null, bp.getSelectedPiece());
		assertEquals(3, bp.numAvailablePieces());
	}
}