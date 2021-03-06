package view;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

/**
 * Class for displaying the number of moves left in a puzzle GAME.
 * 
 * @author dfontana
 * @author awharrison
 */
public class NumberMovesLeftView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**Shows the number of moves remaining.**/
	JTextPane numberMoves;
	/**Shows the title of the NumbberMovesLeftView, which is "Moves Remaining"**/
	JLabel lblTitle;
	/** Button to clear the selected piece view **/
	JButton clearSelectedPiece;

	/**
	 * Creates a NumberMovesLeftView with a given initial amount of moves.
	 * @param initialMoves - int
	 */
	public NumberMovesLeftView(int initialMoves) {
		this.setPreferredSize(new Dimension(180, 90));
		numberMoves = new JTextPane();
		numberMoves.setFont(new Font("Tahoma", Font.PLAIN, 24));
		numberMoves.setText("00");
		lblTitle = new JLabel("Moves Remaining");
		numberMoves.setText(Integer.toString(initialMoves));
		clearSelectedPiece = new JButton("Clear Selected Piece");
		setupLayout();
		this.setBackground(Color.WHITE);
	}
	
	/**
	 * Method to set the text of the moves left textPane. 
	 * Method converts integer argument into a string.
	 * The passed argument should be two places. Ex: 10 or 09
	 * @param movesLeft - int
	 */
	public void updateMovesLeft(int movesLeft){
		numberMoves.setText(Integer.toString(movesLeft));
	}
	
	/**
	 * Sets up the layout of NumberMovesLeftView.
	 */
	private void setupLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(25)
								.addComponent(clearSelectedPiece))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(49)
							.addComponent(lblTitle))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(74)
							.addComponent(numberMoves, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(clearSelectedPiece)
					.addGap(18)
					.addComponent(lblTitle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(numberMoves, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(17, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}

	/**
	 * Sets the action listener for the clear selected piece button 
	 * @param al The action listener to add
	 */
	public void addClearSelectedActionListener(ActionListener al){
		clearSelectedPiece.addActionListener(al);
	}
}
