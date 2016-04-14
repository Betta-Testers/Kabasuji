/**
 * 
 */
package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.AbstractLevelModel;
import model.AbstractTile;
import model.Piece;
import model.PieceTile;
import view.BullpenView;

/**
 * @author hejohnson
 *
 */
public class PuzzleBoardMouseController implements MouseListener, MouseMotionListener{
	AbstractLevelModel levelModel;
	Piece draggedPiece;
	AbstractTile source;
	Piece p;
	Move m;
	
	PuzzleBoardMouseController (AbstractLevelModel levelModel) {
		this.levelModel = levelModel;
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		
	}

	@Override
	public void mouseEntered(MouseEvent me) {
	
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if (draggedPiece == null) {
			levelModel.getBoard().clearPiecePreview();
		} else {
			levelModel.getBullpen().addSinglePiece(draggedPiece.getID());
			levelModel.updateLevelEndConditions();
		}
	}

	@Override
	public void mousePressed(MouseEvent me) {
		source  = levelModel.getTileAt(me.getX(), me.getY());
		if (source instanceof PieceTile) {
			draggedPiece = ((PieceTile)source).getPiece();
			levelModel.getBoard().removePiece(draggedPiece);		
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		source  = levelModel.getTileAt(me.getX(), me.getY());
		
		if (draggedPiece == null) {
			m = new PlacePieceOnBoardFromBullpenMove(levelModel, source);
		} else {
			m = new MovePieceOnBoardMove(levelModel, source, draggedPiece);
		}
		
		if (m.doMove()) {
			levelModel.pushMove(m); // If it's a builder, the level will push onto the stack. If player, the level can just discard it
		} else {
			if (draggedPiece != null) { // Can't place the piece, and a piece was being dragged. Just return the piece to the original location.
				levelModel.getBoard().placePiece(p, p.getOriginRow(), p.getOriginCol());
			}
		} 
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent me) {
		source  = levelModel.getBoard().getTileAt(me.getX(), me.getY());
		Piece p = levelModel.getBullpen().getSelectedPiece();
		levelModel.getBoard().showPiecePreview(p, source.getRow(), source.getCol());
	}
}
