package controllers.player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import app.Game;
import controllers.common.PlacePieceOnBoardFromBullpenMove;
import model.AbstractLevelModel;
import model.AbstractTile;
import model.LightningTile;
import model.Piece;
import model.PieceTile;
import view.BoardView;
import view.BullpenView;
import view.LevelView;
import view.SelectedPieceView;

/**
 * @author hejohnson
 */

//TODO add view update stuff

public class LightningBoardGameController implements MouseListener, MouseMotionListener{
	AbstractLevelModel levelModel;
	Game game;
	BoardView boardView;
	BullpenView bpv;
	SelectedPieceView spv;
	LevelView levelView;
	
	public LightningBoardGameController (Game game, LevelView levelView) {
		this.game = game;
		this.levelModel = game.getCurrentLevel();
		this.boardView = game.getLevelView().getBoardView();
		this.levelView = levelView;
		this.boardView = levelView.getBoardView();
		this.bpv = levelView.getBullpenView();
		this.spv = levelView.getSelectedPieceView();
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		
	}

	@Override
	public void mouseEntered(MouseEvent me) {
	
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		AbstractTile source  = levelModel.getBoard().getTileAt(me.getX(), me.getY());
		PlacePieceOnBoardFromBullpenMove m = new PlacePieceOnBoardFromBullpenMove(levelModel, source,  game.getLevelView().getBullpenView());
		
		if (m.doMove()) {
			Piece p = m.getPlacedPiece();
			spv.getPiecePanel().redraw();
			spv.getPiecePanel().repaint();
			levelModel.getBoard().removePiece(p);
			for (PieceTile pt : p.getTiles()) {
				levelModel.getBoard().swapTile(new LightningTile(pt.getRow(), pt.getCol()));
			}
			boardView.redraw();
			boardView.repaint();
			if (levelModel.checkStatus()) {
				game.updateStars(levelModel.getID(), levelModel.getStarsEarned());
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		AbstractTile source  = levelModel.getBoard().getTileAt(me.getX(), me.getY());
		Piece p = levelModel.getBullpen().getSelectedPiece();
		levelModel.getBoard().showPiecePreview(p, source.getRow(), source.getCol());
		boardView.redraw();
		boardView.repaint();
	}
}
