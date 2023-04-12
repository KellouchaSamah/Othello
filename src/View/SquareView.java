package View;

import java.awt.Dimension;
import javax.swing.JButton;


public class SquareView extends JButton {
	/*
	 *  Variable indiquant si la case est sélectionné ou non 
	 */
	private boolean viewSelectedSquare = false;
	/*
	 * Constructeur
	 */
	public SquareView() {
		super();
		/*La taille de chaque case*/
		this.setMaximumSize(new Dimension(20, 20)); 
		this.setMinimumSize(new Dimension(20, 20));
		this.setPreferredSize(new Dimension(20, 20));
	}
	/*
	 *  getters & setters
	 */
	public boolean getSelectedSquare() {
		return viewSelectedSquare;
	}

	public void setSelectedSquare(boolean selectedSquare) {
		this.viewSelectedSquare = selectedSquare;
	}
}
