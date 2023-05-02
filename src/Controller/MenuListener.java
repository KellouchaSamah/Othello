package Controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;


public class MenuListener implements MouseListener{

	private Play play;
	
	public MenuListener(Play play) {
		this.play = play;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == this.play.getControllerbordview().getviewMenuHelp()) {
			try {
				
				this.play.getControllerbordview().getTimer().stop();
				this.play.getStart().suspend();
               this.play.getControllerbordview().viewHelpfortheGame();
				
			} catch (IOException e1) {
				e1.printStackTrace();
				e1.getMessage();
			}
		}
		
		else if(e.getSource() == this.play.getControllerbordview().getviewMenuRules()) {
			try {
				this.play.getControllerbordview().getTimer().stop();
				this.play.getStart().suspend();
               this.play.getControllerbordview().viewRulesoftheGame();
               
			} catch (IOException e1) {
				e1.printStackTrace();
				e1.getMessage();
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
