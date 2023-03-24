package Controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;

import Model.Board;
import Model.Pawn;
import Model.Sound;
import View.ImageView;

/**
 * La classe permet de mettre les listener sur le plateau du jeu
 * @author M1 info Rouen (2019/2020)
 * Othello
 */
public class BoardListener implements MouseListener{

	// la partie
	private Play play;
	
	// __construct
	public BoardListener(Play play) {
		this.play = play;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	// methode permer de gerer la case cliqu� 
	@Override
	public void mousePressed(MouseEvent e) {
		
		for (int i = 0; i <Board.modelBoardSize; i++) {
            for (int j = 0; j < Board.modelBoardSize; j++) {
        		if(e.getSource() == play.getControllerbordihm().getIhmBoardGame()[i][j] && e.getButton()== 1) {
        			
            		if(!play.getControllerbordihm().getIhmBoardGame()[i][j].getSelectedSquare() && play.getControllerbordihm().getIhmBoardGame()[i][j].getIcon() == ImageView.ihmChoiseState) {
            			try {
            				// le son
							Sound s = new Sound("move");
							
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
            			// changer l'icon (etat)
            			if(play.getControllerbordihm().getIhmPlayerPawn() == Pawn.BLACKState && !play.getControllerplayerBlack().isIsComputer()) {
            				//BoardIHM.ihmBoardGame[i][j].setIcon(StateIHM.ihmBLACKState);
            				
            				// r�cuperer la position de la case
            				// effectuer la mouvement
            				play.getControllerplayerBlack().PlayerMove.choiceMove(play.getControllerplayBoard(), play.getControllerplayerBlack(), new Point(i, j));
            				// changer les �tats de joueur adversaire
            				play.getControllerplayerBlack().PlayerMove.chageStatesAdvrs(play.getControllerplayerBlack(), play.getControllerplayerWhite(), new Point(i, j), play.getControllerplayBoard());
            				
            				// changer le score
            				play.getControllerbordihm().ihmNewScore(play.getControllerplayerBlack(), play.getControllerplayerWhite());
            				// induque que le joueur est jou� 
            				play.getControllerplayerBlack().setIsPlayed(true);
            				
            			}else if (play.getControllerbordihm().getIhmPlayerPawn() == Pawn.WHITEState && !play.getControllerplayerWhite().isIsComputer()) {
            				//BoardIHM.ihmBoardGame[i][j].setIcon(StateIHM.ihmWHITEState);
            				// r�cuperer la position de la case
            				// effectuer la mouvement
            				
            				play.getControllerplayerWhite().PlayerMove.choiceMove(play.getControllerplayBoard(), play.getControllerplayerWhite(), new Point(i, j));
            				// changer les �tats de joueur adversaire
            				play.getControllerplayerWhite().PlayerMove.chageStatesAdvrs(play.getControllerplayerWhite(), play.getControllerplayerBlack(), new Point(i, j), play.getControllerplayBoard());
            				
            					
            				// changer le score
            				play.getControllerbordihm().ihmNewScore(play.getControllerplayerWhite(), play.getControllerplayerBlack());
            				
            				// induque que le joueur est jou� 
            				play.getControllerplayerWhite().setIsPlayed(true);
            			}
            		}
        		}
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
