package View;

import java.awt.Dimension;
import javax.swing.JButton;


public class SquareView extends JButton {

	private boolean viewSelectedSquare = false;

	public SquareView() {
		super();
		this.setMaximumSize(new Dimension(20, 20));
		this.setMinimumSize(new Dimension(20, 20));
		this.setPreferredSize(new Dimension(20, 20));
	}

	public boolean getSelectedSquare() {
		return viewSelectedSquare;
	}

	public void setSelectedSquare(boolean selectedSquare) {
		this.viewSelectedSquare = selectedSquare;
	}
}
