package controllers.builder;

import java.util.ArrayList;
import controllers.common.IMove;
import model.Board;
import model.Bullpen;
import model.Piece;
import view.BoardView;
import view.BullpenView;

/**
 * Removes all piece from the board in Builder. When the button is pressed this move is executed, 
 * storing the pieces that were removed to later undo the move or redo as needed.
 * @author dfontana
 */
public class RemoveAllPiecesMove implements IMove {
	/**Bullpen View that is being updated when all the pieces are added back**/
	BullpenView bpv;
	/**Bullpen whose pieces are returned to**/
	Bullpen bp;
	/**Board whose peices are being taken from**/
	Board board;
	/**List of all pieces that were removed from the board**/
	ArrayList<Piece> piecesRemoved;
	/**View being updated after move/undo**/
	BoardView bv;
	
	/**
	 * Constructs a Remove All Pieces Move
	 * @param board Board in the builder
	 * @param bp Bullpen in the builder
	 * @param bpv View of the bullpen
	 * @param bv View of board being redrawn
	 */
	RemoveAllPiecesMove (Board board, Bullpen bp, BullpenView bpv, BoardView bv) {
		this.bpv = bpv;
		this.bp = bp;
		this.board = board;
		this.bv = bv;
		this.piecesRemoved = new ArrayList<Piece>();
	}
	
	/**
	 * When all pieces are removed, they are stored in the move to possibly undo it later.
	 * For every piece removed from the board, it's count on the bullpen is incremented by 1.
	 * The view of each Piece's PieceGroup is then updated to reflect the new counts.
	 * @return boolean - true if move is valid and successful
	 */
	@Override
	public boolean doMove() {
		if(!isValid()){ return false;}
		piecesRemoved = board.resetBoard();
		for(Piece p: piecesRemoved){
			bp.incrementPiece(p.getID());
			bpv.updatePieceGroup(p);
		}
		
		//redraw
		board.clearPiecePreview();
		bv.redraw();
		bv.repaint();
		return true;
	}

	/**
	 * Determines if this move can be done.
	 * This move is invalid if there is no pieces currently on the board.
	 * @return boolean - true if there are pieces on the board
	 */
	@Override
	public boolean isValid() {
		if(board.getPieceCount() > 0){return true;}
		return false;
	}

	/**
	 * Undoes the removal of all pieces on a board. Does so by iterating over the pieces that were removed
	 * and adds them back to the board. Decrements count in the bullpen as well and updates the view.
	 * @return boolean False if there are pieces on the board.
	 */
	@Override
	public boolean undo() {
		for(Piece p: piecesRemoved){
			board.putPieceOnBoard(p, p.getOriginRow(), p.getOriginCol());
			bp.decrementPiece(p.getID());
			bpv.updatePieceGroup(p);
		}
		
		//redraw
		board.clearPiecePreview();
		bv.redraw();
		bv.repaint();
		return true;
	}
	
	/**
	 * Redoes the undone move. Only valid if no NEW moves have been done that 
	 * would break the undo order. Redoes the move by calling doMove();
	 * @return boolean if the move could be completed
	 */
	@Override
	public boolean redo() {
		return doMove();
	}

}
