package Model;

import java.util.ArrayList;

/**
 * La classe permet modeliser le plateau du jeu
 * @author M1 info Rouen (2019/2020)
 * Othello
 */
public class Board {

	// la taille du plateau
	public static int modelBoardSize = 8;
	// la matrice des cases
	public Square[][] modelBoardSqures = new Square[modelBoardSize][modelBoardSize];
	
	// __construct sans params
	public Board(){  
	    for (int i=0; i<modelBoardSize; i++)
	    {
	      for (int j=0; j<modelBoardSize; j++)
	      {
	    	  modelBoardSqures[i][j] = new Square();
	      }
	    }
	    //initialiser la case 3.3 a blanc
	  modelBoardSqures[3][4].setSquareState(State.WHITEState);
	    //initialiser la case 3.4 a Noir
	  modelBoardSqures[3][3].setSquareState(State.BLACKState);
	    //initialiser la case 4.3 a Noir
	  modelBoardSqures[4][4].setSquareState(State.BLACKState);
	    //initialiser la case 4.4 a blanc
	  modelBoardSqures[4][3].setSquareState(State.WHITEState);
	    
	}
	
	// __construct avec params
	public Board(Board b){ 
		
	    for (int i=0; i<modelBoardSize; i++){
	      for (int j=0; j<modelBoardSize; j++){
	    	  if(b.modelBoardSqures[i][j].getSquareState() == State.BLACKState) {
	    		  modelBoardSqures[i][j] = new Square();
	    		 this.modelBoardSqures[i][j].setSquareState(State.BLACKState);
	    	  }else if(b.modelBoardSqures[i][j].getSquareState() == State.NONEState) {
	    		  modelBoardSqures[i][j] = new Square();
	    		  this.modelBoardSqures[i][j].setSquareState(State.NONEState);
	    	  }else if(b.modelBoardSqures[i][j].getSquareState() == State.WHITEState) {
	    		  modelBoardSqures[i][j] = new Square();
	    		  this.modelBoardSqures[i][j].setSquareState(State.WHITEState);
	    	  }
	      }
	    }	    
	}
	
	// converture Pawn vers State 
	public static State convertPawntoState(Pawn pawn) {
		if(pawn == Pawn.BLACKState) {
			return State.BLACKState;
		}else if(pawn == Pawn.WHITEState) {
			return State.WHITEState;
		}else return State.NONEState; 
	}
	
	// converture State vers Pawn
	public static Pawn convertStatetoPawn(State state) {
		if(state == State.BLACKState) {
			return Pawn.BLACKState;
		}else if(state == State.WHITEState) {
			return Pawn.WHITEState;
		}else return Pawn.NONEState; 
	}
	
	// converture String vers Pawn
	public static Pawn convertStringtoPawn(String string) {
		if(string == "BLACK") {
			return Pawn.BLACKState;
		}else if(string == "WHITE") {
			return Pawn.WHITEState;
		}else return Pawn.NONEState; 
	}
	
	// methode permet recuperer le profondeur � 
	// utilis� selon la difficult� du partie
	public int getDifficulte(String difficult) {
		
		if(difficult == "EASY") {
			return 1;
		}else if(difficult == "MUDIEM"){
			return 2;
		}else if(difficult == "HARD"){
			return 4;
		}
		return 0;
	}
	
	// m�thode permet de verifier si le jeu est termin� ou non
	public boolean endGame(){
		Boolean existCaseNULL = false;
		
		for(int i = 0; i < Board.modelBoardSize; i++ ) {
			for(int j = 0; j < Board.modelBoardSize; j++ ) {
				
				if(modelBoardSqures[i][j].getSquareState() == State.NONEState) {
					existCaseNULL  = true;
				}
			}
		}
		
		if( existCaseNULL == false ) {
			return true ;
		}else return false ;
	}

	public ArrayList<Board> getSuccessors(Player player) {
	    ArrayList<Board> successors = new ArrayList<>();
	    // boucler sur toutes les cases du plateau
	    for (int i = 0; i < Board.modelBoardSize; i++) {
	        for (int j = 0; j < Board.modelBoardSize; j++) {
	            Square square = modelBoardSqures[i][j];
	            // si la case est vide
	            if (square.getSquareState() == State.NONEState) {
	                // vérifier si le joueur peut jouer dans cette case
	                if (player.getIsPlayed()) {
	                    // créer un nouveau plateau et jouer dans cette case
	                    Board newBoard = new Board(this);
	                    newBoard.play(i, j, player.getPawn());
	                    successors.add(newBoard);
	                }
	            }
	        }
	    }
	    return successors;
	}
	public void play(int i, int j, Pawn pawn) {
	    State playerState = pawn.getPlayerState();
	     modelBoardSqures[i][j].setSquareState(playerState);

	    // parcours des directions à partir de la case jouée
	    for(Direction dir : Direction.values()) {
	        int ii = i + dir.i; // i de départ de la direction
	        int jj = j + dir.j; // j de départ de la direction
	        boolean validSequence = false; // indique si une séquence valide a été trouvée

	        // recherche de la première case de la séquence
	        while(isInsideBoard(ii, jj) && modelBoardSqures[ii][jj].getSquareState() == playerState.opposite()) {
	            ii += dir.i;
	            jj += dir.j;
	        }

	        // si la première case trouvée est d'une couleur différente de celle du joueur, on cherche une séquence valide
	        if(isInsideBoard(ii, jj) && modelBoardSqures[ii][jj].getSquareState() == playerState) {
	            validSequence = true;

	            // parcours des cases de la direction pour vérifier si la séquence est valide
	            while(ii != i || jj != j) {
	                ii -= dir.i;
	                jj -= dir.j;
	                if(modelBoardSqures[ii][jj].getSquareState() == playerState.opposite()) {
	                    modelBoardSqures[ii][jj].setSquareState(playerState);
	                } else {
	                    validSequence = false;
	                    break;
	                }
	            }
	        }

	        // si aucune séquence valide n'a été trouvée dans cette direction, on ne fait rien
	        if(!validSequence) {
	            continue;
	        }
	    }
	    
	}

	private boolean isInsideBoard(int i, int j) {
	    return i >= 0 && i < modelBoardSize && j >= 0 && j < modelBoardSize;
	}

}

