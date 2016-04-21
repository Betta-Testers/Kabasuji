/**
 * 
 */
package controllers.common;

import model.AbstractLevelModel;
import model.AbstractTile;
import model.Board;
import model.Bullpen;
import model.Piece;
import view.BullpenView;

/**
 * @author hejohnson
 *
 */
public class PlacePieceOnBoardFromBullpenMove extends Move{
	AbstractLevelModel levelModel;
	Bullpen bullpen;
	Board board;
	Piece p;
	AbstractTile sourceTile;
	BullpenView bpv;
	
	public PlacePieceOnBoardFromBullpenMove (AbstractLevelModel lm, AbstractTile tile, BullpenView bpv) {
		this.bpv = bpv;
		this.levelModel = lm;
		this.bullpen = levelModel.getBullpen();
		this.board = levelModel.getBoard();
		this.sourceTile = tile;
		this.p = bullpen.getSelectedPiece();
	}
	
	public boolean doMove() {
		if (isValid()) {
			board.putPieceOnBoard(p, sourceTile.getRow(), sourceTile.getCol());
			bullpen.decrementSelectedPiece();
			bpv.updatePieceGroup(p);
			bullpen.clearSelectedPiece();
			return true;
		}
		return false;
	}
	
	public boolean isValid() {
		return board.willFit(p, sourceTile.getRow(), sourceTile.getCol());
	}
	
	public boolean undo() {
		board.removePiece(p);
		bullpen.addSinglePiece(p.getID());
		return true;
	}
	
	@Override
	public boolean redo() {
		doMove();
		return true;
	}
	
	public Piece getPlacedPiece() {
		return this.p;
	}
}