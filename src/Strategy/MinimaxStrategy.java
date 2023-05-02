package Strategy;

import java.awt.Point;
import java.util.ArrayList;

import Controller.Play;
import Model.Board;
import Model.Player;

public class MinimaxStrategy extends Strategy{
	
	private Play play;
	
	public MinimaxStrategy(Board boardC, int difficulte, Player player, Player playerADV, Play play){
		super(boardC, difficulte, player, playerADV);
		this.play = play;
	}
	
	@Override
	public int executeAlgo() {
		return minimax(player, this.board, 0, new Point());
	}
	
	public int minimax(Player p, Board board, int depth, Point point) {
		
		if (board.endGame() || (depth == this.difficulte)) {
			
			ArrayList<Point> listPointsMove = p.PlayerMove.listPointsMovePlayer(p, board);
			int nbPawn = p.getScore();
			return eval(point, listPointsMove.size(), nbPawn, play);
		}
		
		int bestScore;
		
		ArrayList<Point> listPointsMove = p.PlayerMove.listPointsMovePlayer(p, board);
		ArrayList<Board> diffBoardsWithPoints = listBoards(p, board, listPointsMove);
	
		if (p.getPawn() == this.player.getPawn()) {
			bestScore = Integer.MIN_VALUE;

			for (int i = 0; i < diffBoardsWithPoints.size(); i++) {

				if(depth == 0) this.bestMovedepth0 = i;
				Board succBoard = diffBoardsWithPoints.get(i);
				int score = minimax(this.playerADV, succBoard, depth + 1, listPointsMove.get(i));
				if (score > bestScore) {
					bestScore = score;
					this.bestMove = this.bestMovedepth0;
				}
			}
		} 
		else {
			bestScore = Integer.MAX_VALUE;
			for (int i = 0; i < diffBoardsWithPoints.size(); i++) {
				if(depth == 0) this.bestMovedepth0 = i;
				Board succBoard = diffBoardsWithPoints.get(i);
				int score = minimax(this.player, succBoard, depth + 1,listPointsMove.get(i));

				if (score < bestScore) {
					bestScore = score;
					this.bestMove = this.bestMovedepth0;
				}
			}
		}
		return bestScore;
	}	
}
