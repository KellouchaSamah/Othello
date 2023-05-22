package Controller;


import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import Model.*;
import Strategy.*;
import View.*;

public class Play {

	private Start start;
	private Player ControllerplayerBlack;
	private Player ControllerplayerWhite;
	private Board  controllerplayBoard;
	private BoardView controllerbordview;
	private boolean isblockedBlack = false;
	private boolean isblockedWhite = false;
	public boolean pauseGame = false;
	public boolean playnewGame = false;
	public boolean playendGame = false;
	private int nbrCoup = 0;
	
	private Pawn playerPawn;
	private String levelGame;
	private String algoGame;
	private String gameMade;
	
	public Play(Start start) {
		this.start = start;
	}
	
	public void startGame() throws InterruptedException, IOException {
		
		WindowStart id = new WindowStart(null, "OTHELLO GAME 2022/2023", true, this);
    	id.show(true);
		System.out.println(this.getPlayerPawn());
		System.out.println(this.getAlgoGame());
		System.out.println(this.getGameMade());
		System.out.println(this.getLevelGame());
    	if(this.playerPawn != null){
    		
			controllerplayBoard = new Board();
			controllerbordview = new BoardView(controllerplayBoard, this);
			setProertyBoardview();

			System.out.println(controllerbordview.getviewGameMade());

			if(controllerbordview.getviewGameMade() == "PL vs AI") {
				if(controllerbordview.getviewPlayerPawn() == Pawn.BLACKState) {
					ControllerplayerBlack = new Player(Pawn.BLACKState, false);
					ControllerplayerWhite = new Player(Pawn.WHITEState, true);
				} 
				else {
					ControllerplayerBlack = new Player(Pawn.BLACKState, true);
					ControllerplayerWhite = new Player(Pawn.WHITEState, false);
				}	
			} 
			else if (controllerbordview.getviewGameMade() == "AI vs AI") {
				ControllerplayerBlack = new Player(Pawn.BLACKState, true);
				ControllerplayerWhite = new Player(Pawn.WHITEState, true);
			}
			else if (controllerbordview.getviewGameMade() == "PL vs PL") {
                System.out.println("PL vs PL : i entered ");
				ControllerplayerBlack = new Player(Pawn.BLACKState, false);
				ControllerplayerWhite = new Player(Pawn.WHITEState, false);
				System.out.println(ControllerplayerBlack);

			}
			playersTurn();
    	}
	}
	
	public void setProertyBoardview() {
		System.out.println("i entered to setPropertyBoardView");

		this.controllerbordview.setviewPlayerPawn(this.playerPawn);
		this.controllerbordview.getviewLevelGame().setText(this.levelGame);
		this.controllerbordview.setviewGameMade(this.gameMade);
		this.controllerbordview.getviewAlgoGame().setText(this.algoGame);
	}
	
	public void playersTurn() throws InterruptedException, IOException {
		nbrCoup = 0;
		int k;
		
		controllerbordview.getTimer().start();
		
		while(!controllerplayBoard.endGame() && (isblockedBlack == false || isblockedWhite == false )){		
			k = nbrCoup % 2;
			
			if(Start.newPlay == true) {
				break;
			}
			
			if(pauseGame == true) pauseGame();
						
			if(k == 0) {

				controllerbordview.getviewTurnPlayer().setText("Black");
				blackPlayerTurn();
	
			}else {

				controllerbordview.getviewTurnPlayer().setText("White");
				whitePlayerTurn();
			}
			controllerbordview.setSeconde(0);
			nbrCoup++;
		}
		
		
		if(controllerplayBoard.endGame() || (isblockedBlack == true && isblockedWhite == true )) {
			
			playendGame = true;
			
			if(ControllerplayerWhite.getScore() < ControllerplayerBlack.getScore()) {
				stopGame("Le joueur Black a gagné", ControllerplayerBlack);
				
			}else if(ControllerplayerWhite.getScore() > ControllerplayerBlack.getScore()) {
				stopGame("Le joueur WHite a gagné", ControllerplayerWhite);
			}else {
				stopGame("partie null", null);
			}
		}
		
		if(Start.newPlay == true) {
			start.newGame();
		}
		
	}
	
	public void stopGame(String msg, Player player) throws IOException {
		controllerbordview.getTimer().stop();
		
		Sound sound;
		if(controllerbordview.getviewGameMade() == "PL vs AI"){
			if(!player.isIsComputer()) sound = new Sound("win");
			else sound = new Sound("lost");
		}
				
		controllerbordview.viewWiner(msg);
	}
	
	private int randomALG(ArrayList<Point> listPointsMove) {
		RandomStrategy randomSTG = new RandomStrategy(listPointsMove);
		return randomSTG.executeAlgo();
	}
	
	private int minimaxALG(int difficulte, Player player, Player playerADV) {
		MinimaxStrategy minmaxSTG = new MinimaxStrategy(controllerplayBoard, difficulte, player, playerADV, this);
		minmaxSTG.executeAlgo();
		return minmaxSTG.bestMove;
	}
	
	private int alphabetaALG(int difficulte, Player player, Player playerADV) {
		AlphaBetaStrategy alphabetaSTG = new AlphaBetaStrategy(controllerplayBoard, difficulte, player, playerADV, this);
		alphabetaSTG.executeAlgo();
		return alphabetaSTG.bestMove;
	}

	private int negaalphabetaALG(int difficulte, Player player, Player playerADV) {
		NegaAlphaBetaStrategy negaAlphaBetaStrategy = new NegaAlphaBetaStrategy(controllerplayBoard, difficulte, player, playerADV, this);
		negaAlphaBetaStrategy.executeAlgo();
		return negaAlphaBetaStrategy.bestMove;
	}

	private int negamaxALG(int difficulte, Player player, Player playerADV) {
		NegaMAXStrategy negamaxSTG = new NegaMAXStrategy(controllerplayBoard, difficulte, player, playerADV, this);
		negamaxSTG.executeAlgo();
		return negamaxSTG.bestMove;
	}

	private int sssALG(int difficulte, Player player, Player playerADV) {
		SSSStrategy sssSTG = new SSSStrategy(controllerplayBoard, difficulte, player, playerADV);
		sssSTG.executeAlgo();
		return sssSTG.bestMove;
	}
	
	private void blackPlayerTurn() throws InterruptedException {
		
		if(!ControllerplayerBlack.isIsComputer()) {
			if (controllerbordview.getviewGameMade() == "PL vs PL") controllerbordview.setviewPlayerPawn(Pawn.BLACKState);
			
			
			isblockedBlack = false;
			
			ArrayList<Point> listPointsBlackMove;
			listPointsBlackMove = ControllerplayerBlack.PlayerMove.listPointsMovePlayer(ControllerplayerBlack, this.controllerplayBoard);
				
			if(listPointsBlackMove.size() != 0) controllerbordview.viewUpdatePointsMove(listPointsBlackMove);
			else isblockedBlack = true;
			
		}else {
			
			ArrayList<Point> listPointsBlackMove;
			listPointsBlackMove = ControllerplayerBlack.PlayerMove.listPointsMovePlayer(ControllerplayerBlack, this.controllerplayBoard);

			if(listPointsBlackMove.size() != 0) {
				isblockedBlack = false;
				
				int bestMove = 0;
				
				if(controllerbordview.getviewAlgoGame().getText().toString() == "RANDOM") {
					bestMove = randomALG(listPointsBlackMove);
				}else if(((controllerbordview.getviewAlgoGame().getText().toString() == "MINIMAX")&&(controllerbordview.getviewGameMade() == "AI vs AI")) 
						|| ((controllerbordview.getviewAlgoGame().getText().toString() == "MINIMAX")&&(controllerbordview.getviewGameMade() != "AI vs AI"))) {
					bestMove = minimaxALG(controllerplayBoard.getDifficulte(controllerbordview.getviewLevelGame().getText()),
							new Player(ControllerplayerBlack), new Player(ControllerplayerWhite));
				}else if(controllerbordview.getviewAlgoGame().getText().toString() == "ALPHABETA") {
					bestMove = alphabetaALG(controllerplayBoard.getDifficulte(controllerbordview.getviewLevelGame().getText()),
							new Player(ControllerplayerBlack), new Player(ControllerplayerWhite));
				}else {
					if(controllerbordview.getviewAlgoGame().getText().toString() == "SSS*") {
						bestMove = sssALG(controllerplayBoard.getDifficulte(controllerbordview.getviewLevelGame().getText()),
								new Player(ControllerplayerBlack), new Player(ControllerplayerWhite));
					}else {
						if (controllerbordview.getviewAlgoGame().getText().toString() == "NEGAMAX") {
							bestMove = negamaxALG(controllerplayBoard.getDifficulte(controllerbordview.getviewLevelGame().getText()),
									new Player(ControllerplayerBlack),new Player(ControllerplayerWhite));
						} else if (controllerbordview.getviewAlgoGame().getText().toString() == "NEGAALPHABETA") {
							bestMove = negaalphabetaALG(controllerplayBoard.getDifficulte(controllerbordview.getviewLevelGame().getText()),
									new Player(ControllerplayerBlack),new Player(ControllerplayerWhite));
						}
					}

				}
				
				Thread.sleep(2000) ;
				try {
					Sound s = new Sound("move");
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}				
				ControllerplayerBlack.PlayerMove.choiceMove(controllerplayBoard,
						ControllerplayerBlack, 
						new Point(listPointsBlackMove.get(bestMove)));
				
				ControllerplayerBlack.PlayerMove.chageStatesAdvrs(ControllerplayerBlack,
						ControllerplayerWhite, new Point(listPointsBlackMove.get(bestMove )), 
						this.controllerplayBoard);				
				
				controllerbordview.viewNewScore(ControllerplayerBlack, ControllerplayerWhite);

			}else isblockedBlack = true;
		}
		
		if(isblockedBlack == false) {
	        
			while(!ControllerplayerBlack.getIsPlayed() && controllerbordview.getSeconde() < 20) {

				if(Start.newPlay == true) break;
				
				controllerbordview.viewUpdateDisplay(this.controllerplayBoard);
				if(ControllerplayerBlack.isIsComputer()) {
					ControllerplayerBlack.setIsPlayed(true);
				}
			}
			
			if(!ControllerplayerBlack.isIsComputer()) {
				controllerbordview.viewdeletePointsMove();
			}

			ControllerplayerBlack.setIsPlayed(false);
		}
	}
	
	private void whitePlayerTurn() throws InterruptedException {
						
		if(!ControllerplayerWhite.isIsComputer()) {
			if (controllerbordview.getviewGameMade() == "PL vs PL") controllerbordview.setviewPlayerPawn(Pawn.WHITEState);

			isblockedWhite = false;
						
			ArrayList<Point> listPointsWhiteMove;
			listPointsWhiteMove = ControllerplayerWhite.PlayerMove.listPointsMovePlayer(ControllerplayerWhite, this.controllerplayBoard);
			// mettre � jour l'view
			if(listPointsWhiteMove.size() != 0) controllerbordview.viewUpdatePointsMove(listPointsWhiteMove);
			else isblockedWhite = true;
			
		}else { 
			ArrayList<Point> listPointsWhiteMove;
			listPointsWhiteMove = ControllerplayerWhite.PlayerMove.listPointsMovePlayer(ControllerplayerWhite, this.controllerplayBoard);
			
			if(listPointsWhiteMove.size() != 0) {
				isblockedWhite = false;
				
				int bestMove = 0;
				
				if(controllerbordview.getviewAlgoGame().getText().toString() == "RANDOM") {
					bestMove = randomALG(listPointsWhiteMove);
				}else if(controllerbordview.getviewAlgoGame().getText().toString() == "MINIMAX") {
					bestMove = minimaxALG(controllerplayBoard.getDifficulte(controllerbordview.getviewLevelGame().getText()),
							new Player(ControllerplayerWhite), new Player(ControllerplayerBlack));
				}else if(((controllerbordview.getviewAlgoGame().getText().toString() == "ALPHABETA")&&(controllerbordview.getviewGameMade() == "AI vs AI"))
					|| ((controllerbordview.getviewAlgoGame().getText().toString() == "ALPHABETA")&&(controllerbordview.getviewGameMade() != "AI vs AI"))){
					bestMove = alphabetaALG(controllerplayBoard.getDifficulte(controllerbordview.getviewLevelGame().getText()),
							new Player(ControllerplayerWhite), new Player(ControllerplayerBlack));
				}else {
					if (controllerbordview.getviewAlgoGame().getText().toString() == "SSS*") {
						bestMove = sssALG(controllerplayBoard.getDifficulte(controllerbordview.getviewLevelGame().getText()),
								new Player(ControllerplayerWhite), new Player(ControllerplayerBlack));
					} else {
						if (controllerbordview.getviewAlgoGame().getText().toString() == "NEGAMAX") {
							bestMove = negamaxALG(controllerplayBoard.getDifficulte(controllerbordview.getviewLevelGame().getText()),
									new Player(ControllerplayerWhite), new Player(ControllerplayerBlack));
						} else if (controllerbordview.getviewAlgoGame().getText().toString() == "NEGA_A_B") {
							bestMove = negaalphabetaALG(controllerplayBoard.getDifficulte(controllerbordview.getviewLevelGame().getText()),
									new Player(ControllerplayerWhite), new Player(ControllerplayerBlack));
						}
					}
				}
				Thread.sleep(2000) ;
						
				try {
					Sound s = new Sound("move");
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
				ControllerplayerWhite.PlayerMove.choiceMove(controllerplayBoard,
						ControllerplayerWhite, new Point(listPointsWhiteMove.get(bestMove)));
				ControllerplayerWhite.PlayerMove.chageStatesAdvrs(ControllerplayerWhite,
						ControllerplayerBlack, new Point(listPointsWhiteMove.get(bestMove)), 
						this.controllerplayBoard);
								
				controllerbordview.viewNewScore(ControllerplayerWhite, ControllerplayerBlack);
			
			}else isblockedWhite = true;	
		}

		if(isblockedWhite == false) {

			while(!ControllerplayerWhite.getIsPlayed() && controllerbordview.getSeconde() < 20 ) {
				if(Start.newPlay == true) break;
				
				controllerbordview.viewUpdateDisplay(this.controllerplayBoard);
				if(ControllerplayerWhite.isIsComputer()) {
					ControllerplayerWhite.setIsPlayed(true);
				}
			}
			
			if(!ControllerplayerWhite.isIsComputer()) {
				controllerbordview.viewdeletePointsMove();
			}
			ControllerplayerWhite.setIsPlayed(false);
		}
	}
	
	public int pauseGame() {

		this.start.suspend();
		int option;
		if(playnewGame == false) {
			String msg ="Do you wish to resume play ?";
			
	       	String title ="Game Paused";
	       	JOptionPane jop = new JOptionPane();			
	       	option = jop.showConfirmDialog(null, msg, title,
	       			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	       	
		}else {
			String msg ="Are you sure you want to start a new game? ";
	    	String title ="New Game";
	    	JOptionPane jop = new JOptionPane();			
	    	option = jop.showConfirmDialog(null, msg, title,
	    			JOptionPane.CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
		
		this.start.resume();
       	return option;
	}
	
 	public int getInbrCoup(){
		return nbrCoup;
	}

	public Player getControllerplayerBlack() {
		return ControllerplayerBlack;
	}

	public void setControllerplayerBlack(Player controllerplayerBlack) {
		ControllerplayerBlack = controllerplayerBlack;
	}

	public Player getControllerplayerWhite() {
		return ControllerplayerWhite;
	}

	public void setControllerplayerWhite(Player controllerplayerWhite) {
		ControllerplayerWhite = controllerplayerWhite;
	}

	public Board getControllerplayBoard() {
		return controllerplayBoard;
	}

	public void setControllerplayBoard(Board controllerplayBoard) {
		this.controllerplayBoard = controllerplayBoard;
	}

	public BoardView getControllerbordview() {
		return controllerbordview;
	}

	public void setControllerbordview(BoardView controllerbordview) {
		this.controllerbordview = controllerbordview;
	}

	public Pawn getPlayerPawn() {
		return playerPawn;
	}

	public void setPlayerPawn(Pawn playerPawn) {
		this.playerPawn = playerPawn;
	}

	public String getLevelGame() {
		return levelGame;
	}

	public void setLevelGame(String levelGame) {
		this.levelGame = levelGame;
	}

	public String getAlgoGame() {
		return algoGame;
	}

	public void setAlgoGame(String algoGame) {
		this.algoGame = algoGame;
	}

	public String getGameMade() {
		return gameMade;
	}

	public void setGameMade(String gameMade) {
		this.gameMade = gameMade;
	}

	public Start getStart() {
		return start;
	}

	public void setStart(Start start) {
		this.start = start;
	}
}
