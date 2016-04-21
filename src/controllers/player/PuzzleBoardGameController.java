package controllers.player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import app.Game;
import controllers.common.Move;
import controllers.common.MovePieceOffBoardMove;
import controllers.common.MovePieceOnBoardMove;
import controllers.common.PlacePieceOnBoardFromBullpenMove;
import model.AbstractLevelModel;
import model.AbstractTile;
import model.Piece;
import model.PieceTile;
import view.BoardView;
import view.BullpenView;
import view.LevelView;
import view.SelectedPieceView;

/**
 * @author hejohnson
 * @author awharrison
 *
 */

//TODO add view update stuff

public class PuzzleBoardGameController implements MouseListener, MouseMotionListener{
	AbstractLevelModel levelModel;
	Piece draggedPiece;
	AbstractTile source;
	Piece p;
	Move m;
	Game game;
	BoardView boardView;
	BullpenView bpv;
	SelectedPieceView spv;

	public PuzzleBoardGameController (Game game, LevelView levelView) {
		this.game = game;
		this.boardView = levelView.getBoardView();
		this.levelModel = game.getCurrentLevel();
		this.bpv = levelView.getBullpenView();
		this.spv = levelView.getSelectedPieceView();
	}
	
	public Piece getDraggedPiece() {
		return this.draggedPiece;
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
		} else { // currently dragging a piece
			Move m = new MovePieceOffBoardMove(levelModel, draggedPiece);
			m.doMove();
			if (levelModel.checkStatus()) {
				game.updateStars(levelModel.getID(), levelModel.getStarsEarned());
			}
		}
		boardView.redraw();
		boardView.repaint();
	}

	@Override
	public void mousePressed(MouseEvent me) {
		source  = levelModel.getBoard().getTileAt(me.getX(), me.getY());
		if (source instanceof PieceTile) {
			draggedPiece = ((PieceTile)source).getPiece();
			levelModel.getBoard().removePiece(draggedPiece);	
			boardView.redraw();
			boardView.repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		source  = levelModel.getBoard().getTileAt(me.getX(), me.getY());

		if (draggedPiece == null) {
			m = new PlacePieceOnBoardFromBullpenMove(levelModel, source, game.getLevelView().getBullpenView());
		} else {
			m = new MovePieceOnBoardMove(levelModel, source, draggedPiece);
		}

		if (m.doMove()) {
			//levelModel.pushMove(m); // If it's a builder, the level will push onto the stack. If player, the level can just discard it
			spv.getPiecePanel().redraw();
			spv.getPiecePanel().repaint();
			if (levelModel.checkStatus()) {
				game.updateStars(levelModel.getID(), levelModel.getStarsEarned());
			}

		} else {
			if (draggedPiece != null) { // Can't place the piece, and a piece was being dragged. Just return the piece to the original location.
				levelModel.getBoard().putPieceOnBoard(draggedPiece, draggedPiece.getOriginRow(), draggedPiece.getOriginCol());
			}
		} 
		boardView.redraw();
		boardView.repaint();
		draggedPiece = null;
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
		if(p != null){
			levelModel.getBoard().clearPiecePreview();
			levelModel.getBoard().showPiecePreview(p, source.getRow(), source.getCol());
			boardView.redraw();
			boardView.repaint();
		}
	}
}