package Strategy;

import java.awt.Point;
import java.util.ArrayList;

import Controller.Play;
import Model.Board;
import Model.Player;

/**
 * La classe permet d'implementer l'algorithme alphabeta
 * @author M1 info Rouen (2019/2020)
 * Othello
 */
public class AlphaBetaStrategy extends Strategy{

	// la partie
	private Play play;
	
	// __construct
	public AlphaBetaStrategy(Board boardC, int difficulte, Player player, Player playerADV, Play play){
		super(boardC, difficulte, player, playerADV);
		this.play = play;
	}
	
	// methode permet d'excuter l'algo
	@Override
	public int executeAlgo() {
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		
		return alphabeta(this.player, this.board, 0, new Point(), alpha, beta);
	}
	
	//int alphabêta(int depth, int alpha, int bêta)
	public int alphabeta(Player p, Board board, int depth, Point point, int alpha, int beta) {
		
		//   if (game over or depth <= 0)
		//   return winning score or eval();
		
		if (board.endGame() || (depth == this.difficulte)) {
			//récuperer la mobilité : nombre de cases jouables
			ArrayList<Point> listPointsMove = p.PlayerMove.listPointsMovePlayer(p, board);
			//nombre de pions (matériel).
			int nbPawn = p.getScore();
			return eval(point, listPointsMove.size(), nbPawn, play);
		}
		
		//  move bestMove;
		
		// récuperer la liste des boards
		ArrayList<Point> listPointsMove = p.PlayerMove.listPointsMovePlayer(p, board);
		ArrayList<Board> diffBoardsWithPoints = listBoards(p, board, listPointsMove);
		
		//   if(nœud == MAX) { //Programme
		if (p.getPawn() == this.player.getPawn()) {

			//	for (each possible move m) {
			for (int i = 0; i < diffBoardsWithPoints.size(); i++) {
				
				if(depth == 0) this.bestMovedepth0 = i;
				//	make move m;
				Board succBoard = diffBoardsWithPoints.get(i);

				//	int score = alphabêta(depth - 1, alpha, bêta)
				//	unmake move m;
				int score = alphabeta(this.playerADV, succBoard, depth + 1, listPointsMove.get(i),
				 alpha, beta);
	
                //  if (score > alpha) {
				if (score > alpha) {
					// alpha = score;
					alpha = score;
					// bestMove = m ;
					this.bestMove = this.bestMovedepth0;
					
					if (alpha >= beta)
					   break;
				}
			}
			// return alpha ;
			return alpha ;
		} 
		//type MIN = adversaire
		else {			
			// for (each possible move m) {
			for (int i = 0; i < diffBoardsWithPoints.size(); i++) {
				
				if(depth == 0) this.bestMovedepth0 = i;
				// make move m;
				Board succBoard = diffBoardsWithPoints.get(i);

				// int score = alphabêta(depth - 1, alpha, bêta)
				int score = alphabeta(player, succBoard, depth + 1,listPointsMove.get(i), alpha,
				 beta);
				//	unmake move m;
				
				// if (score < bêta) {
				if (score < beta) {
					//	bêta = score;
					beta = score;

					// bestMove = m ;
					this.bestMove = this.bestMovedepth0;
					
					if (alpha >= beta)
						break;
				}
			}
			return beta;
		}
	}

	// # getter et setter
	public Play getPlay() {
		return play;
	}

	public void setPlay(Play play) {
		this.play = play;
	}	
}




