package model;

/** 
 * A LightningLevel handles the back end for a Lightning game mode, tracking the end conditions and progress of 
 * the game.
 * @author Dylan
 */
public class LightningLevel extends AbstractLevelModel{
	/**Serialized ID used for writing to disk**/
	private static final long serialVersionUID = 407028463108073009L;
	
	/**Total time the level has to be played, in seconds**/
	int totalTime;
	
	/**Time remaining in current level. This value is transient.**/
	transient int timeLeft;
	
	/**Number of tiles unmarked in the current attempt. This value is transient**/
	transient int unmarkedTiles;
	
	/**Overall size of the bullpen for this level**/
	int piecesToGen;

	public LightningLevel(int levelID) {
		super(levelID, "Lightning", false);
	}	

	/** 
	 * A level is complete if the total number of stars earned is 3, meaning there are no more moves to be made, the player
	 * has achieved the most they can.
	 * OR
	 * The player is out of time. 
	 * @return true if the level is done.
	 */
	@Override
	boolean isComplete() {
		if(starsEarned == 3 || timeLeft == 0){
			return true;
		}
		
		return false;
	}

	/**
	 * updateProgress occurs after every move is made. This updates the stars earned for the current level if
	 * the number of marked tiles is equal to the thresholds. The starsEarned is set rather than incremented
	 * to prevent duplicate triggers of the same threshold. (Ie place a piece that marks all but 12 tiles and
	 * the place another piece that marks all but 8 tiles. That would increment twice - not wanted).
	 * 
	 * After all checks are made, the level is saved if the current play through has earned more stars than 
	 * the number tracked on file.
	 */
	@Override
	void updateProgress() {
		if(unmarkedTiles <= 12 && unmarkedTiles > 6){
			starsEarned = 1;
		}
		
		if(unmarkedTiles <= 6 && unmarkedTiles > 0){
			starsEarned = 2;
		}
		
		if(unmarkedTiles == 0){
			starsEarned = 3;
		}
	}
	
	/**
	 * Decreases the number of unmarked tiles. This works towards the level termination
	 * condition of marking an entire board.
	 * @param numMarked The number of tiles marked. Since pieces can overlap, this number won't
	 * always be 6.
	 */
	public void decrementUnmarked(int numMarked){
		unmarkedTiles -= numMarked;
	}
}
