package controllers.builder;

import java.util.ArrayList;

import controllers.common.IMove;
import model.AbstractTile;
import model.Board;
import model.BoardTile;
import model.Bullpen;
import model.Piece;
import model.PieceTile;
import view.BoardView;
import view.SelectedPieceView;

/**
 * Move class for converting a piece into a hint on the board
 * @author ejbosia
 * @author dfontana
 */
public class PieceToHintMove implements IMove{
	/**Bullpen whose piece is being used to make the hint**/
	Bullpen bp;
	/**Board that is having a hint placed on it**/
	Board board;
	/**Piece being used to make the hint**/
	Piece p;
	/**ArrayList of board tiles that get turned into a hint**/
	ArrayList<BoardTile> hintModel;
	/**Tile that was clicked**/
	AbstractTile source;
	/**Updates the selected piece view after the move is done**/
	SelectedPieceView spv;
	/**Updates the board after the move is done**/
	BoardView bv;
	
	/**
	 * Constructs a Piece to Hint Move
	 * @param bp - Bullpen being modified
	 * @param b - Board having a hint placed on it
	 * @param source - Tile that was clicked
	 * @param spv - selected piece view being cleared after move
	 * @param bv - boardView being redrawn after move
	 */
	PieceToHintMove(Bullpen bp, Board b, AbstractTile source, SelectedPieceView spv, BoardView bv) {
		this.bp = bp;
		this.board = b;
		this.source = source;
		this.p = bp.getSelectedPiece();	
		this.spv = spv;
		this.bv = bv;
		this.hintModel = new ArrayList<BoardTile>();
	}
	
	/**
	 * To do the move the location of the piece is set to where the tile clicked was
	 * Then the tiles of the piece occupies are set to be hint tiles and the piece is
	 * added to the known hints on the board.
	 * Finally the selected piece of the bullpen is cleared
	 * @return true if the move could be done
	 */
	public boolean doMove() {
		if (isValid()) {
			p.setLocation(source.getRow(), source.getCol());
			for(PieceTile pt : p.getTiles()){
				BoardTile bt = ((BoardTile) board.getTileAt(pt.getCol()*board.getTileSize(), pt.getRow()*board.getTileSize()));
				bt.setHint(true);
				hintModel.add(bt);
			}
			board.addHint(hintModel);
			board.clearPiecePreview();
			bp.clearSelectedPiece();
			
			//Redraw
			spv.getPiecePanel().redraw();
			spv.getPiecePanel().repaint();
			bv.redraw();
			bv.repaint();
			return true;
		}
		return false;
	}
	
	/**
	 * The move is valid if the selected piece is not null, the piece can fit on the board,
	 * and there are no hints already within its bounds.
	 * @return true if the move can be done
	 */
	public boolean isValid() {
		if(bp.getSelectedPiece() != null){
			if(board.willFit(p, source.getRow(), source.getCol())){
				return true;
			}
		}
		return false;
	}

	/**
	 * The move is undone by marking all the tiles that the piece occupied as not hints.
	 * The piece is then removed from the hints that are on the board.
	 * The selected piece of the bullpen is then restored to be the piece.
	 * @return true
	 */
	public boolean undo() {
		for(BoardTile t : hintModel){ 
			t.setHint(false);
		}
		
		board.removeHint(hintModel);
		bp.setSelectedPiece(p);
		
		//Redraw
		spv.getPiecePanel().redraw();
		spv.getPiecePanel().repaint();
		bv.redraw();
		bv.repaint();
		return true;
	}
	
	/** 
	 * The move is redone by executing the move again.
	 * @return true if the move was redone
	 */
	public boolean redo() {
		return doMove();
	}
}
