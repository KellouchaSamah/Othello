package Strategy;

import Controller.Play;
import Model.Board;
import Model.Player;

import java.awt.*;
import java.util.ArrayList;

public class NegaAlphaBetaStrategy extends Strategy {


    // la partie
    private Play play;


    // __construct
    public NegaAlphaBetaStrategy(Board boardC, int difficulte, Player player, Player playerADV, Play play) {
        super(boardC, difficulte, player, playerADV);
        this.play = play;
    }


    // méthode permettant d'exécuter l'algorithme
    @Override
    public int executeAlgo() {
        return negaAlphaBeta(player, this.board, 0, new Point(), Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
    }


    // méthode negaAlphaBeta
    public int negaAlphaBeta(Player p, Board board, int depth, Point point, int alpha, int beta, int color) {


        // if (game over or depth = 0)
        if (board.endGame() || (depth == this.difficulte)) {


            // récupérer la mobilité : nombre de cases jouables
            ArrayList<Point> listPointsMove = p.PlayerMove.listPointsMovePlayer(p, board);
            // nombre de pions (matériel)
            int nbPawn = p.getScore();
            // return winning score or eval()
            return color * eval(point, listPointsMove.size(), nbPawn, play);
        }


        // r�cuperer la liste des boards
        ArrayList<Point> listPointsMove = p.PlayerMove.listPointsMovePlayer(p, board);
        ArrayList<Board> diffBoardsWithPoints = listBoards(p, board, listPointsMove);


        // for (each possible move m) {
        for (int i = 0; i < diffBoardsWithPoints.size(); i++) {


            if (depth == 0) this.bestMovedepth0 = i;


            // make move m;
            Board succBoard = diffBoardsWithPoints.get(i);


            // int score = -negaAlphaBeta (depth - 1)
            int score = -negaAlphaBeta(this.playerADV, succBoard, depth + 1, listPointsMove.get(i), -beta, -alpha, -color);


            // unmake move m;


            // if (score >= beta)
            if (score >= beta) {
                // return beta;
                return beta;
            }


            // if (score > alpha)
            if (score > alpha) {
                // alpha = score;
                alpha = score;
                // if (depth == 0) bestMove = m ;
                if (depth == 0) this.bestMove = this.bestMovedepth0;
            }
        }


        // return alpha;
        return alpha;
    }
}
