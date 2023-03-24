package Controller;


import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import Model.Board;
import Model.Player;
import Model.Sound;
import Strategy.AlphaBetaStrategy;
import Strategy.MinimaxStrategy;
import Strategy.RandomStrategy;
import View.BoardIHM;
import View.WindowStart;
import Model.Pawn;

/**
 * La classe pour creer une partie de jeu 
 * cette classe contient l'IHM et les deux joueurs et le bouard du jeu
 * @author M1 info Rouen (2019/2020)
 * Othello
 */
public class Play {

	// notre partie du jeu en cours 
	private Start start;
	// jouer avec poin noir
	private Player ControllerplayerBlack;
	// jouer avec poinblanc
	private Player ControllerplayerWhite;
	// plateau du jeu
	private Board  controllerplayBoard;
	// IHM du jeu 
	private BoardIHM controllerbordihm;
	// verifie si le joueur black est bloqu�
	private boolean isblockedBlack = false;
	// verifie si le joueur white est bloqu�
	private boolean isblockedWhite = false;
	// le jeu en pause 
	public boolean pauseGame = false;
	// indique new game
	public boolean playnewGame = false;
	// indique la fin du jeu
	public boolean playendGame = false;
	// nombre des coup
	private int nbrCoup = 0;
	
	// # variable pour l'ihm 
	// ihm playerPawn
	private Pawn playerPawn;
	// ihm levelGame
	private String levelGame;
	// ihm AlgoGame
	private String algoGame;
	// GameMade 
	private String gameMade;
	
	// __construct
	public Play(Start start) {
		this.start = start;
	}
	
	// m�thode permet de cr�er l'ihm et le plateu du jeu 
	// et les deux joueurs 
	public void startGame() throws InterruptedException, IOException {
		
		WindowStart id = new WindowStart(null, "Match Settings", true, this);
    	id.show(true);  	
    	
    	if(this.playerPawn != null){
    		
			// cr�er le plateau du jeu 
			controllerplayBoard = new Board();
			// creation l'affichage IHM 
			controllerbordihm = new BoardIHM(controllerplayBoard, this);
			setProertyBoardIHM();
			
			// creation des joueurs selon le choix 
			// joueur VS ordi
			if(controllerbordihm.getIhmGameMade() == "PL vs AI") {
				// player -> black / ordi -> white
				if(controllerbordihm.getIhmPlayerPawn() == Pawn.BLACKState) {
					// creation du joueur 1
					ControllerplayerBlack = new Player(Pawn.BLACKState, false);
					// creation le computer 
					ControllerplayerWhite = new Player(Pawn.WHITEState, true);
				} 
				// player -> white / ordi -> black
				else {
					// creation le computer 
					ControllerplayerBlack = new Player(Pawn.BLACKState, true);
					// cr�ation du joueur 1
					ControllerplayerWhite = new Player(Pawn.WHITEState, false);
				}	
			} 
			// AI vs AI
			else if (controllerbordihm.getIhmGameMade() == "AI vs AI") {
				// creation le computer 
				ControllerplayerBlack = new Player(Pawn.BLACKState, true);
				// cr�ation du joueur 1
				ControllerplayerWhite = new Player(Pawn.WHITEState, true);
			}
			// PL vs PL
			else if (controllerbordihm.getIhmGameMade() == "PL vs PL") {
				// creation le joueur 1 
				ControllerplayerBlack = new Player(Pawn.BLACKState, false);
				// cr�ation du joueur 2
				ControllerplayerWhite = new Player(Pawn.WHITEState, false);
			}
			// les coups entre les deux joueurs 
			playersTurn();
    	}
	}
	
	// methode permet de mettre � jour les property de la partie
	public void setProertyBoardIHM() {
		// le pion choisi par le joueur
		this.controllerbordihm.setIhmPlayerPawn(this.playerPawn);
		// le niveu de la partie
		this.controllerbordihm.getIhmLevelGame().setText(this.levelGame);
		// le mode du jeu
		this.controllerbordihm.setIhmGameMade(this.gameMade);
		// l'algorithme utilis� pour la partie
		this.controllerbordihm.getIhmAlgoGame().setText(this.algoGame);
	}
	
	// m�thode permet de alterner entre les deux joueurs
	public void playersTurn() throws InterruptedException, IOException {
		nbrCoup = 0;
		int k;
		
		//Démarrer le chrono
		controllerbordihm.getTimer().start();
		
		while(!controllerplayBoard.endGame() && (isblockedBlack == false || isblockedWhite == false )){		
			k = nbrCoup % 2;
			
			if(Start.newPlay == true) {
				break;
			}
			
			// pause le jeu
			if(pauseGame == true) pauseGame();
						
			if(k == 0) {
				// System.out.println("joueur " + "Black");
				
				controllerbordihm.getIhmTurnPlayer().setText("Black");
				blackPlayerTurn();
	
			}else {
				// System.out.println("joueur " + "WHITE");
				
				controllerbordihm.getIhmTurnPlayer().setText("White");
				whitePlayerTurn();
			}
			// restaurer le temps 
			controllerbordihm.setSeconde(0);
			nbrCoup++;
		}
		
		
		if(controllerplayBoard.endGame() || (isblockedBlack == true && isblockedWhite == true )) {
			
			// indique la fin du jeu
			playendGame = true;
			
			if(ControllerplayerWhite.getScore() < ControllerplayerBlack.getScore()) {
				// affichier le message 
				stopGame("Le joueur Black a gagn�", ControllerplayerBlack); 
				
			}else if(ControllerplayerWhite.getScore() > ControllerplayerBlack.getScore()) {
				// affichier le message
				stopGame("Le joueur WHite a gagn�", ControllerplayerWhite);
			}else {
				// affichier le message
				stopGame("partie null", null);
			}
		}
		
		if(Start.newPlay == true) {
			//exitGame(); 
			start.newGame();
		}
		
	}
	
	// methode permet de stoper le jeu et gerer les son de la fin du jeu
	public void stopGame(String msg, Player player) throws IOException {
		//stoper le chrono
		controllerbordihm.getTimer().stop();
		
		// son
		Sound sound;
		if(controllerbordihm.getIhmGameMade() == "PL vs AI"){
			// le son pour gagner
			if(!player.isIsComputer()) sound = new Sound("win");
			// le son pour perdere
			else sound = new Sound("lost");
		}
				
		// affichier le message
		controllerbordihm.ihmWiner(msg);		
	}
	
	// algorithme random 
	private int randomALG(ArrayList<Point> listPointsMove) {
		// choisir al�atoiremet une position Randome
		RandomStrategy randomSTG = new RandomStrategy(listPointsMove);
		return randomSTG.executeAlgo();
	}
	
	// algorithme minimax
	private int minimaxALG(int difficulte, Player player, Player playerADV) {
		//choisir al�atoiremet une position minamax
		MinimaxStrategy minmaxSTG = new MinimaxStrategy(controllerplayBoard, difficulte, player, playerADV, this);
		minmaxSTG.executeAlgo();
		return minmaxSTG.bestMove;
	}
	
	// algorithme alphabeta
	private int alphabetaALG(int difficulte, Player player, Player playerADV) {
		//choisir al�atoiremet une position alphabeta
		AlphaBetaStrategy alphabetaSTG = new AlphaBetaStrategy(controllerplayBoard, difficulte, player, playerADV, this);
		alphabetaSTG.executeAlgo();
		return alphabetaSTG.bestMove;
	}
	
	// joueur noir
	private void blackPlayerTurn() throws InterruptedException {
		
		// if non computer 
		if(!ControllerplayerBlack.isIsComputer()) {
			// pour alterner entre deux joueurs
			if (controllerbordihm.getIhmGameMade() == "PL vs PL") controllerbordihm.setIhmPlayerPawn(Pawn.BLACKState);
			
			
			// indique que le Player n'est en etat de blocage
			isblockedBlack = false; 
			
			// le player r�cuper les position du possible de move
			ArrayList<Point> listPointsBlackMove;
			listPointsBlackMove = ControllerplayerBlack.PlayerMove.listPointsMovePlayer(ControllerplayerBlack, this.controllerplayBoard);
				
			if(listPointsBlackMove.size() != 0) controllerbordihm.ihmUpdatePointsMove(listPointsBlackMove);
			else isblockedBlack = true;
			
		}else {
			
			// AI Black
			// AI r�cupe les position du possible de move
			ArrayList<Point> listPointsBlackMove;
			listPointsBlackMove = ControllerplayerBlack.PlayerMove.listPointsMovePlayer(ControllerplayerBlack, this.controllerplayBoard);

			if(listPointsBlackMove.size() != 0) {
				// indique que AI n'est en etat de blocage
				isblockedBlack = false;
				
				// indexe de la position
				int bestMove = 0;
				
				// niveau du jeu 
				if(controllerbordihm.getIhmAlgoGame().getText().toString() == "RANDOM") {
					// indice de la position
					bestMove = randomALG(listPointsBlackMove);
				}else if(((controllerbordihm.getIhmAlgoGame().getText().toString() == "MINIMAX")&&(controllerbordihm.getIhmGameMade() == "AI vs AI")) 
						|| ((controllerbordihm.getIhmAlgoGame().getText().toString() == "MINIMAX")&&(controllerbordihm.getIhmGameMade() != "AI vs AI"))) {
					// indice de la position
					bestMove = minimaxALG(controllerplayBoard.getDifficulte(controllerbordihm.getIhmLevelGame().getText()),
							new Player(ControllerplayerBlack), new Player(ControllerplayerWhite));
				}else if(controllerbordihm.getIhmAlgoGame().getText().toString() == "ALPHABETA") {
					// indice de la position
					bestMove = alphabetaALG(controllerplayBoard.getDifficulte(controllerbordihm.getIhmLevelGame().getText()),
							new Player(ControllerplayerBlack), new Player(ControllerplayerWhite));
				}
				
				// attendre 2 secondes
				Thread.sleep(2000) ;
				// son
				try {
    				// le son
					Sound s = new Sound("move");
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}				
				// effectuer la mouvement
				ControllerplayerBlack.PlayerMove.choiceMove(controllerplayBoard, 
						ControllerplayerBlack, 
						new Point(listPointsBlackMove.get(bestMove)));
				
				// changer les �tats de joueur adversaire
				ControllerplayerBlack.PlayerMove.chageStatesAdvrs(ControllerplayerBlack, 
						ControllerplayerWhite, new Point(listPointsBlackMove.get(bestMove )), 
						this.controllerplayBoard);				
				
				// changer le score
				controllerbordihm.ihmNewScore(ControllerplayerBlack, ControllerplayerWhite);	    				

			}else isblockedBlack = true;
		}
		
		if(isblockedBlack == false) {
	        
			while(!ControllerplayerBlack.getIsPlayed() && controllerbordihm.getSeconde() < 20) {
				// attendre le palyer de choisir la position
				// modifier l'affichage et sortir de la boucle
				
				// new game 
				if(Start.newPlay == true) break;
				
				// afficher la nouvelle boardIHM
				controllerbordihm.ihmUpdateDisplay(this.controllerplayBoard);
				if(ControllerplayerBlack.isIsComputer()) {
					ControllerplayerBlack.setIsPlayed(true);
				}
			}
			
			// suprimer les icon des cases de mouvement possibles 
			if(!ControllerplayerBlack.isIsComputer()) {
				controllerbordihm.ihmdeletePointsMove();
			}

			// attendre la prochaine coup
			ControllerplayerBlack.setIsPlayed(false);	
		}
	}
	
	// joueur blanc
	private void whitePlayerTurn() throws InterruptedException {
						
		// if non computer 
		if(!ControllerplayerWhite.isIsComputer()) {
			// pour alterner entre deux joueurs
			if (controllerbordihm.getIhmGameMade() == "PL vs PL") controllerbordihm.setIhmPlayerPawn(Pawn.WHITEState);

			// indique que le Player n'est en etat de blocage
			isblockedWhite = false; 
						
			// le player r�cuper les position du possible de move
			ArrayList<Point> listPointsWhiteMove;
			listPointsWhiteMove = ControllerplayerWhite.PlayerMove.listPointsMovePlayer(ControllerplayerWhite, this.controllerplayBoard);
			// mettre � jour l'ihm
			if(listPointsWhiteMove.size() != 0) controllerbordihm.ihmUpdatePointsMove(listPointsWhiteMove);
			else isblockedWhite = true;
			
		}else { 
			// AI White
			// AI r�cupe les position du possible de move
			ArrayList<Point> listPointsWhiteMove;
			listPointsWhiteMove = ControllerplayerWhite.PlayerMove.listPointsMovePlayer(ControllerplayerWhite, this.controllerplayBoard);
			
			if(listPointsWhiteMove.size() != 0) {
				// indique que AI n'est en etat de blocage
				isblockedWhite = false;
				
				// indexe de la position
				int bestMove = 0;
				
				// niveau du jeu 
				if(controllerbordihm.getIhmAlgoGame().getText().toString() == "RANDOM") {
					// indice de la position
					bestMove = randomALG(listPointsWhiteMove);
				}else if(controllerbordihm.getIhmAlgoGame().getText().toString() == "MINIMAX") {
					// indice de la position
					bestMove = minimaxALG(controllerplayBoard.getDifficulte(controllerbordihm.getIhmLevelGame().getText()), 
							new Player(ControllerplayerWhite), new Player(ControllerplayerBlack));
				}else if(((controllerbordihm.getIhmAlgoGame().getText().toString() == "ALPHABETA")&&(controllerbordihm.getIhmGameMade() == "AI vs AI"))
					|| ((controllerbordihm.getIhmAlgoGame().getText().toString() == "ALPHABETA")&&(controllerbordihm.getIhmGameMade() != "AI vs AI"))){
					// indice de la position
					bestMove = alphabetaALG(controllerplayBoard.getDifficulte(controllerbordihm.getIhmLevelGame().getText()), 
							new Player(ControllerplayerWhite), new Player(ControllerplayerBlack));
				}
				
				// attendre 2 scondes
				Thread.sleep(2000) ;
						
				// son
				try {
    				// le son pour chaque mouvement
					Sound s = new Sound("move");
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
				// effectuer la mouvement
				ControllerplayerWhite.PlayerMove.choiceMove(controllerplayBoard, 
						ControllerplayerWhite, new Point(listPointsWhiteMove.get(bestMove)));
				// changer les �tats de joueur adversaire
				ControllerplayerWhite.PlayerMove.chageStatesAdvrs(ControllerplayerWhite, 
						ControllerplayerBlack, new Point(listPointsWhiteMove.get(bestMove)), 
						this.controllerplayBoard);
								
				// changer le score
				controllerbordihm.ihmNewScore(ControllerplayerWhite, ControllerplayerBlack);
			
			}else isblockedWhite = true;	
		}

		if(isblockedWhite == false) {

			while(!ControllerplayerWhite.getIsPlayed() && controllerbordihm.getSeconde() < 20 ) {
				// attendre le palyer de choisir la position
				// modifier l'affichage et sortir de la boucle

				// bloqu� les case
				// methode permet le computer de jouer 
				// new game 
				if(Start.newPlay == true) break;
				
				// induque que le joueur est jou� 
				// afficher la nouvelle boardIHM
				controllerbordihm.ihmUpdateDisplay(this.controllerplayBoard);
				if(ControllerplayerWhite.isIsComputer()) {
					ControllerplayerWhite.setIsPlayed(true);
				}
			}
			
			// suprimer les icon des cases de mouvement possibles 
			if(!ControllerplayerWhite.isIsComputer()) {
				controllerbordihm.ihmdeletePointsMove();
			}
			// attendre la prochaine coup
			ControllerplayerWhite.setIsPlayed(false);
		}
	}
	
	// mettre le jeu en pause 
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
	
	// # l'ensemble des getter et setter
	// lire le nombre du coup
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

	public BoardIHM getControllerbordihm() {
		return controllerbordihm;
	}

	public void setControllerbordihm(BoardIHM controllerbordihm) {
		this.controllerbordihm = controllerbordihm;
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
