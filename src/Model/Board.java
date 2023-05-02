package Model;

import java.util.ArrayList;


public class Board {

	public static int modelBoardSize = 8;
	public Square[][] modelBoardSqures = new Square[modelBoardSize][modelBoardSize];
	
	public Board(){
	    for (int i=0; i<modelBoardSize; i++)
	    {
	      for (int j=0; j<modelBoardSize; j++)
	      {
	    	  modelBoardSqures[i][j] = new Square();
	      }
	    }
	  modelBoardSqures[3][4].setSquareState(State.WHITEState);
	  modelBoardSqures[3][3].setSquareState(State.BLACKState);
	  modelBoardSqures[4][4].setSquareState(State.BLACKState);
	  modelBoardSqures[4][3].setSquareState(State.WHITEState);
	    
	}
	
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
	
	public static State convertPawntoState(Pawn pawn) {
		if(pawn == Pawn.BLACKState) {
			return State.BLACKState;
		}else if(pawn == Pawn.WHITEState) {
			return State.WHITEState;
		}else return State.NONEState; 
	}
	
	public static Pawn convertStatetoPawn(State state) {
		if(state == State.BLACKState) {
			return Pawn.BLACKState;
		}else if(state == State.WHITEState) {
			return Pawn.WHITEState;
		}else return Pawn.NONEState; 
	}
	
	public static Pawn convertStringtoPawn(String string) {
		if(string == "BLACK") {
			return Pawn.BLACKState;
		}else if(string == "WHITE") {
			return Pawn.WHITEState;
		}else return Pawn.NONEState; 
	}
	
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
	    for (int i = 0; i < Board.modelBoardSize; i++) {
	        for (int j = 0; j < Board.modelBoardSize; j++) {
	            Square square = modelBoardSqures[i][j];
	            if (square.getSquareState() == State.NONEState) {
	                if (player.getIsPlayed()) {
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

	    for(Direction dir : Direction.values()) {
	        int ii = i + dir.i;
	        int jj = j + dir.j;
	        boolean validSequence = false;

	        while(isInsideBoard(ii, jj) && modelBoardSqures[ii][jj].getSquareState() == playerState.opposite()) {
	            ii += dir.i;
	            jj += dir.j;
	        }

	        if(isInsideBoard(ii, jj) && modelBoardSqures[ii][jj].getSquareState() == playerState) {
	            validSequence = true;

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

	        if(!validSequence) {
	            continue;
	        }
	    }
	    
	}

	private boolean isInsideBoard(int i, int j) {
	    return i >= 0 && i < modelBoardSize && j >= 0 && j < modelBoardSize;
	}

}

