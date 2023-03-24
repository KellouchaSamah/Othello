package Strategy;

import java.awt.Point;
import java.util.ArrayList;

import Controller.Play;
import Model.Board;
import Model.Player;

/**
 * La classe abstract permet d'implementer la strategie du jeu et la fonction d'evaluation
 * @author M1 info Rouen (2019/2020)
 * Othello
 */
public abstract class Strategy {
	
	// le plateau du jeu
	protected Board board;
	// la difficulter de jeu 
	protected int difficulte ;
	// player 
	protected Player player;
	// playerADV 
	protected Player playerADV;
	// bestMove
	public int bestMove = 0;
	// 
	protected int bestMovedepth0;
	
	// __construct sans prams
	public Strategy(){
	}
	
	// __construct avec prams
	public Strategy(Board boardC, int difficulte, Player player, Player playerADV) {
		board = new Board(boardC);
		this.difficulte = difficulte;
		this.player = player;
		this.playerADV = playerADV;
	}

	// methode permet d'exécuter les deffrents alogos
	public abstract int executeAlgo();
	
	// récupérer les board de differents positions possibles 
	public ArrayList<Board> listBoards(Player p, 
		                              Board board, 
		                              ArrayList<Point> listPointsMove) {
		// la liste des boards
		ArrayList<Board> boards = new ArrayList<>();
		
		for (Point movePoint : listPointsMove) {
			Board resultingBoard = new Board(board);
			
			if(p.getPawn() == player.getPawn()) {
							
				// effectuer la mouvement
				player.PlayerMove.choiceMove(resultingBoard, player, new Point(movePoint));
				// changer les états de joueur adversaire
				player.PlayerMove.chageStatesAdvrs(player, playerADV, new Point(movePoint), resultingBoard);
				
				boards.add(resultingBoard);
			}else {
				// effectuer la mouvement
				playerADV.PlayerMove.choiceMove(resultingBoard, playerADV, new Point(movePoint));
				// changer les états de joueur adversaire
				playerADV.PlayerMove.chageStatesAdvrs(playerADV, player, new Point(movePoint), resultingBoard);
				
				boards.add(resultingBoard);
			}
		}
		return boards;
	}
	
	// méthode permet d'évaluer chaque position
	public int eval(Point powerPoint, int mobilite, int materiel, Play play) {
		int [][] evalPowerPoint;
		
		// table d'evaluation selon le critères de la force de position
		evalPowerPoint = new int[][]
	            {
					{ 500, -150, 30, 10, 10, 30, -150,  500},
					
					{-150, -250,  0,  0,  0,  0, -250, -150},
					
					{ 30,     0,  1,  2,  2,  1,    0,   30},
					
					{ 10,     0,  2, 16, 16,  2,    0,   10},
					
					{ 10,     0,  2, 16, 16,  2,    0,   10},
					
					{ 30,     0,  1,  2,  2,  1,    0,   30},
					
					{-150, -250,  0,  0,  0,  1, -250, -150},
					
					{ 500, -150, 30, 10, 10, 30, -150,  500},
	            };
		
				// if nbrcoup <= 30 mobilité et position
				// if nbrcoup > 30 et <= 55  mobilité et position et material
				// if nbrcoup > 55 material
				if(play.getInbrCoup() <= 30) 
					return evalPowerPoint[powerPoint.x][powerPoint.y] + mobilite;
				else if ((play.getInbrCoup() > 30) && (play.getInbrCoup() <= 56)) 
					return evalPowerPoint[powerPoint.x][powerPoint.y] + mobilite + materiel;
				else return materiel;
	}	
}
