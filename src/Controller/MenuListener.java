package Controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * La classe permet de mettre les listener sur le menu du jeu
 * @author M1 info Rouen (2019/2020)
 * Othello
 */
public class MenuListener implements MouseListener{

	// la partie
	private Play play;
	
	// __construct
	public MenuListener(Play play) {
		this.play = play;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	// methode permer de gerer la menu cliqu� 
	@Override
	public void mousePressed(MouseEvent e) {
		// le menu help
		if(e.getSource() == this.play.getControllerbordview().getviewMenuHelp()) {
			try {
				
				// stoper le time
				this.play.getControllerbordview().getTimer().stop();
               // pauser le jeu 
				this.play.getStart().suspend();
	           // affichere le help  
               this.play.getControllerbordview().viewHelpfortheGame();
				
			} catch (IOException e1) {
				e1.printStackTrace();
				e1.getMessage();
			}
		}
		
		// le menu rules
		else if(e.getSource() == this.play.getControllerbordview().getviewMenuRules()) {
			try {
				// stoper le time
				this.play.getControllerbordview().getTimer().stop();
               // pauser le jeu 
				this.play.getStart().suspend();
               // afficher les regles du jeu
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
