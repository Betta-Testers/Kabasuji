package view;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.PieceGroup;
/**
 * 
 * @author dfontana
 * @author hejohnson
 *
 */
public class PlayingPieceGroupView extends AbstractPieceGroupView{
	private static final long serialVersionUID = 1L;
	GroupLayout groupLayout;
	JLabel label;
	
	public PlayingPieceGroupView(PieceGroup pieceGroup){
		super(pieceGroup);
		
		label = new JLabel();
		updateCount();
		
		setupLayout();
	}
	
	@Override
	void updateCount() {
		int count = pieceGroup.getNumPieces();
		label.setText(Integer.toString(count));
		if(count < 1){
			this.setVisible(false);
		}else{
			this.setVisible(true);
		}
	}
	
	void setupLayout(){
		label.setHorizontalAlignment(SwingConstants.CENTER);
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(button, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
		);

		setLayout(groupLayout);
	}


}
