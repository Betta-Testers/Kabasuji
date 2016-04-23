package controllers.builder;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import view.ExistingLevelView;

import app.Builder;

/**
 * Opens an existing level to edit in the Builder
 * @author hejohnson
 *
 */
public class ExistingLevelEditController implements java.awt.event.ActionListener {
	Builder b;
	
	public ExistingLevelEditController (Builder b) {
		this.b = b;
	}

	/**
	 * Reads the source button for the level to be read into file and launched
	 * in the editor. Directs flow of information in the builder. When all is done
	 * the level should be displayed in the editor and the type select hidden
	 * from view.
	 */
	public void actionPerformed(ActionEvent ae) {
		JButton sourceButton = (JButton) ae.getSource();
		ExistingLevelView elv = (ExistingLevelView) sourceButton.getParent();
		int levelID = elv.getLevelNumber();
		b.editLevel(levelID);
		b.getBuilderView().setVisible(true);
		b.getLevelTypeSelectView().setVisible(false);
	}
}