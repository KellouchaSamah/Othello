package Strategy;

import java.awt.Point;
import java.util.ArrayList;

import Controller.Play;
import Model.Board;
import Model.Player;

public class AlphaBetaStrategy extends Strategy{

	// la partie
	private Play play;
	
	public AlphaBetaStrategy(Board boardC, int difficulte, Player player, Player playerADV, Play play){
		super(boardC, difficulte, player, playerADV);
		this.play = play;
	}
	
	// methode qui lance l'algo
	@Override
	public int executeAlgo() {
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		
		return alphabeta(this.player, this.board, 0, new Point(), alpha, beta);
	}
	
	public int alphabeta(Player p, Board board, int depth, Point point, int alpha, int beta) {
		

		if (board.endGame() || (depth == this.difficulte)) {
			ArrayList<Point> listPointsMove = p.PlayerMove.listPointsMovePlayer(p, board);
			int nbPawn = p.getScore();
			return eval(point, listPointsMove.size(), nbPawn, play);
		}
		

		ArrayList<Point> listPointsMove = p.PlayerMove.listPointsMovePlayer(p, board);
		ArrayList<Board> diffBoardsWithPoints = listBoards(p, board, listPointsMove);
		
		if (p.getPawn() == this.player.getPawn()) {

			for (int i = 0; i < diffBoardsWithPoints.size(); i++) {
				
				if(depth == 0) this.bestMovedepth0 = i;
				Board succBoard = diffBoardsWithPoints.get(i);

				int score = alphabeta(this.playerADV, succBoard, depth + 1, listPointsMove.get(i),
				 alpha, beta);
	
				if (score > alpha) {
					alpha = score;
					this.bestMove = this.bestMovedepth0;
					
					if (alpha >= beta)
					   break;
				}
			}
			return alpha ;
		} 
		else {
			for (int i = 0; i < diffBoardsWithPoints.size(); i++) {
				
				if(depth == 0) this.bestMovedepth0 = i;
				Board succBoard = diffBoardsWithPoints.get(i);

				int score = alphabeta(player, succBoard, depth + 1,listPointsMove.get(i), alpha,
				 beta);

				if (score < beta) {
					beta = score;

					this.bestMove = this.bestMovedepth0;
					
					if (alpha >= beta)
						break;
				}
			}
			return beta;
		}
	}

	public Play getPlay() {
		return play;
	}

	public void setPlay(Play play) {
		this.play = play;
	}	
}




