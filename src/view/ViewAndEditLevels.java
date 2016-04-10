package view;

import java.awt.Font;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JLabel;
import javax.swing.JSplitPane;

public class ViewAndEditLevels extends JSplitPane {
	ExistingLevelViewer levelViewer;	
	ViewAndEditLevels (TreeMap<Integer, String> levelData) {
		super();
		this.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.setEnabled(false);
		JLabel lblExistingLevels = new JLabel("Existing Levels (Click to edit)");
		lblExistingLevels.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		this.setLeftComponent(lblExistingLevels);
		levelViewer = new ExistingLevelViewer();
		this.setRightComponent(levelViewer);
		
		for (int levelNum : levelData.keySet()){
			addLevel(levelData.get(levelNum), levelNum);
		}
	}
	
	public ArrayList<ExistingLevelView> getExistingLevelButtons() {
		return levelViewer.getExistingLevelButtons();
	}
	
	public void addLevel(String levelType, int levelNum) {
		levelViewer.addLevelView(levelType, levelNum);
	}
}
