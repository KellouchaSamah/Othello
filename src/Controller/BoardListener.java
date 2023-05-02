package Controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;

import Model.Board;
import Model.Pawn;
import Model.Sound;
import View.ImageView;

public class BoardListener implements MouseListener{

	private Play play;
	
	public BoardListener(Play play) {
		this.play = play;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		for (int i = 0; i <Board.modelBoardSize; i++) {
            for (int j = 0; j < Board.modelBoardSize; j++) {
        		if(e.getSource() == play.getControllerbordview().getviewBoardGame()[i][j] && e.getButton()== 1) {
        			
            		if(!play.getControllerbordview().getviewBoardGame()[i][j].getSelectedSquare() && play.getControllerbordview().getviewBoardGame()[i][j].getIcon() == ImageView.viewChoiseState) {
            			try {
							Sound s = new Sound("move");
							
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
            			if(play.getControllerbordview().getviewPlayerPawn() == Pawn.BLACKState && !play.getControllerplayerBlack().isIsComputer()) {

            				play.getControllerplayerBlack().PlayerMove.choiceMove(play.getControllerplayBoard(), play.getControllerplayerBlack(), new Point(i, j));
            				play.getControllerplayerBlack().PlayerMove.chageStatesAdvrs(play.getControllerplayerBlack(), play.getControllerplayerWhite(), new Point(i, j), play.getControllerplayBoard());
            				
            				play.getControllerbordview().viewNewScore(play.getControllerplayerBlack(), play.getControllerplayerWhite());
            				play.getControllerplayerBlack().setIsPlayed(true);
            				
            			}else if (play.getControllerbordview().getviewPlayerPawn() == Pawn.WHITEState && !play.getControllerplayerWhite().isIsComputer()) {

            				play.getControllerplayerWhite().PlayerMove.choiceMove(play.getControllerplayBoard(), play.getControllerplayerWhite(), new Point(i, j));
            				play.getControllerplayerWhite().PlayerMove.chageStatesAdvrs(play.getControllerplayerWhite(), play.getControllerplayerBlack(), new Point(i, j), play.getControllerplayBoard());
            				
            					
            				play.getControllerbordview().viewNewScore(play.getControllerplayerWhite(), play.getControllerplayerBlack());
            				
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
