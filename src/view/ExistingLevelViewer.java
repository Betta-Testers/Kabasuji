package view;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * Shows all of the ExistingLevelViews in a scrollpane for the builder.
 * @author hejohnson
 *
 */
public class ExistingLevelViewer extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	/**Panel of the view.**/
	JPanel existingLevels;
	/**Array of the ExistingLevelViews that make up the ExistingLevelViewer.**/
	ArrayList<ExistingLevelView> levelsList;
	
	/**
	 * Creates a new ExistingLevelViewer.
	 */
	ExistingLevelViewer(){
		super();
		this.setEnabled(false);
		this.getHorizontalScrollBar().setUnitIncrement(126);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		levelsList = new ArrayList<ExistingLevelView>();
		existingLevels = new JPanel();
		this.setViewportView(existingLevels);
	}
	/**
	 * Add a level view to the list of existing levels.
	 * @param levelType - level Type
	 * @param levelIndex - level ID
	 * @return ExistingLevelView added to the list of exising levels
	 */
	public ExistingLevelView addLevelView(String levelType, Integer levelIndex, ImageIcon icon) {
		ExistingLevelView elv = new ExistingLevelView(levelType, levelIndex, icon);
		levelsList.add(elv);
		return elv;
	}
	
	/**
	 * Remove a level view to the list of existing levels.
	 * @param levelNumber - int
	 */
	public void removeLevelView(int levelNumber) {
		for(ExistingLevelView elv : levelsList){
			if (elv.getLevelNumber() == levelNumber){
				existingLevels.remove(elv);
				levelsList.remove(elv);
				this.revalidate();
				this.repaint();
				break;
			}
		}
	}
	
	/**
	 * Refreshes the view of all existing levels to display them in
	 * order after the user has made changes to them.
	 */
	public void refreshLevels() {
		existingLevels.removeAll();
		Collections.sort(levelsList);
		for (ExistingLevelView elv : levelsList) {
			existingLevels.add(elv);
		}
	}
	
	/**
	 * Returns all of the currently available level buttons.
	 * @return ArrayList<ExistingLevelView> returns an array list containing all of the current existing level buttons
	 */
	public ArrayList<ExistingLevelView> getExistingLevelButtons() {
		return levelsList;
	}

}
