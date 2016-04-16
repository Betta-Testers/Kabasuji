package model;

import java.io.IOException;
import java.io.Serializable;

import app.Game;
import controllers.ExitLevelButtonController;
import view.LevelView;

/** 
 * A LightningLevel handles the back end for a Lightning game mode, tracking the end conditions and progress of 
 * the game.
 * @author Dylan
 */
public class LightningLevel extends AbstractLevelModel implements Serializable{
	/**Serialized ID used for writing to disk**/
	private static final long serialVersionUID = 407028463108073009L;
	
	/**Total time the level has to be played, in seconds**/
	int totalTime;
	
	/**Overall size of the bullpen for this level**/
	int piecesToGen;

	public LightningLevel(int levelID) {
		super(levelID, "Lightning", false);
		totalTime = 0;
		piecesToGen = 0;
	}
	
	/**
	 * CheckStatus occurs after every move is made. This updates the stars earned for the current level if
	 * the number of marked tiles is equal to the thresholds. The starsEarned is set rather than incremented
	 * to prevent duplicate triggers of the same threshold. (Ie place a piece that marks all but 12 tiles and
	 * the place another piece that marks all but 8 tiles. That would increment twice - not wanted).
	 * 
	 * CheckStatus then returns true if the level's termination conditions are met or not (all tiles marked).
	 * @author Dylan
	 * @return boolean - true if level is done
	 */
	@Override
	public boolean checkStatus() {
		int unmarkedTiles = board.getNumBoardTiles();
		
		if(unmarkedTiles <= 12 && unmarkedTiles > 6){	starsEarned = 1;}
		if(unmarkedTiles <= 6 && unmarkedTiles > 0){ 	starsEarned = 2;}
		if(unmarkedTiles == 0){ 						starsEarned = 3;}
		
		return (unmarkedTiles == 0);
	}
	
	/**
	 * Sets the totalTime for the lightning level
	 * @author Dylan
	 * @param totalTime
	 */
	public void setTotalTime(int totalTime){
		this.totalTime = totalTime;
	}
	
	/**
	 * Returns the totalTime this lightning level allows
	 * @author Dylan
	 * @return int - totalTime in seconds
	 */
	public int getTotalTime(){
		return this.totalTime;
	}
	
	/**
	 * Returns the number of pieces to initialize the bullpen to.
	 * @author Dylan
	 * @return int - pieces to gen
	 */
	public int getPiecesToGen(){
		return this.piecesToGen;
	}
	
	/**
	 * Initializes the view to display correctly for a lightninglevel. 
	 * @return LevelView - view of the initialized LevelView
	 */
	@Override
	public LevelView initializeGame(Game g) {
		LevelView view = new LevelView("Lightning");
		view.addWindowListener(new ExitLevelButtonController(view, g));
		return view;
	}
	
	
	public String toString(){
		return levelType+levelID+totalTime+piecesToGen;
	}
	
	/**
	 * When deserializing this, the transient fields needs to be initialized.
	 * This method does just that, by calling the initialize method.
	 */
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
		in.defaultReadObject();
	}

}
