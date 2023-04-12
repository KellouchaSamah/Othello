package Model;

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
	
	// methode permet recuperer le profondeur à 
	// utilisé selon la difficulté du partie
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
	
	// méthode permet de verifier si le jeu est terminé ou non
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
}

