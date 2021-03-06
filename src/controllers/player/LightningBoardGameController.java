package controllers.player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;

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
 * Controls placement of pieces on a Lightning board. Handles piece preview display and swapping to lightning tiles.
 * Tells the level to update the star count and closes the level if 3 stars earned.
 * @author hejohnson
 */

public class LightningBoardGameController implements MouseListener, MouseMotionListener{
	/** The level currently being played **/
	AbstractLevelModel levelModel;
	/** THe overarching game object **/
	Game game;
	/** The view of the board **/
	BoardView boardView;
	/** The view of the bullpen and associated pieces **/
	BullpenView bpv;
	/** The view of the piece selected from the bullpen to be placed on the board **/
	SelectedPieceView spv;
	/** The window that holds the bullpen and board **/
	LevelView levelView;
	
	/**
	 *
	 * @param game The game object
	 * @param lv The level view
	 */
	public LightningBoardGameController (Game game, LevelView lv) {
		this.game = game;
		this.levelModel = game.getCurrentLevel();
		this.levelView = lv;
		this.boardView = levelView.getBoardView();
		this.bpv = levelView.getBullpenView();
		this.spv = levelView.getSelectedPieceView();
	}

	/**
	 * Handles "placing" pieces on the board
	 * Tells the piece it has been placed, then swaps out all the tiles that it would cover with lightning tiles
	 * If 3 stars have been achieved, close the window
	 */
	@Override
	public void mouseReleased(MouseEvent me) {
		AbstractTile source  = levelModel.getBoard().getTileAt(me.getX(), me.getY());
		PlacePieceOnBoardFromBullpenMove m = new PlacePieceOnBoardFromBullpenMove(levelModel, source,  levelView.getBullpenView(), spv, boardView);
		
		if (m.doMove()) {
			Piece p = m.getPlacedPiece();
			levelModel.getBoard().removePiece(p);
			
			Piece addedPiece = levelModel.getBullpen().addRandomPiece();
			bpv.updatePieceGroup(addedPiece);
			
			for (PieceTile pt : p.getTiles()) {
				levelModel.getBoard().swapTile(new LightningTile(pt.getRow(), pt.getCol()));
			}
			if (levelModel.checkStatus()) {
				levelView.dispatchEvent(new WindowEvent(levelView, WindowEvent.WINDOW_CLOSING));
			}
			levelView.getLevelInfoView().setStars(levelModel.getStarsEarned());
		}
		
		
		levelModel.getBoard().clearPiecePreview();
		boardView.redraw();
		boardView.repaint();
	}

	/**
	 * Shows the preview of the piece where it would be placed, with the origin of the piece located at the tile that the mouse is over
	 */
	@Override
	public void mouseMoved(MouseEvent me) {
		Piece p;
		AbstractTile source  = levelModel.getBoard().getTileAt(me.getX(), me.getY());
		p = levelModel.getBullpen().getSelectedPiece();
		
		if(p != null){
			levelModel.getBoard().clearPiecePreview();
			levelModel.getBoard().showPiecePreview(p, source.getRow(), source.getCol());
			boardView.redraw();
			boardView.repaint();
		}
	}
	
	/**
	 * Clears the piece preview when the mouse leaves the board.
	 * @param arg0 mouse event of leaving the board
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		levelModel.getBoard().clearPiecePreview();
		boardView.redraw();
		boardView.repaint();
	}
	
	/**
	 * Does nothing on mouseClick.
	 * @param me - Mouse click event.
	 */
	@Override
	public void mouseClicked(MouseEvent me) {}
	/**
	 * Does nothing on mouseEntered.
	 * @param me - Mouse enter event.
	 */
	@Override
	public void mouseEntered(MouseEvent me) {}
	/**
	 * Does nothing on mousePressed.
	 * @param me - Mouse pressed event.
	 */
	@Override
	public void mousePressed(MouseEvent me) {}
	/**
	 * Does nothing on mouseDragged.
	 * @param arg0 - Mouse dragged event.
	 */
	@Override
	public void mouseDragged(MouseEvent arg0) {}
}
