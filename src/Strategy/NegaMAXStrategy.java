package Strategy;
import java.awt.Point;
import java.util.ArrayList;

import Controller.Play;
import Model.Board;
import Model.Player;

public class NegaMAXStrategy extends Strategy{

    // la partie
    private Play play;
    // __construct
    public NegaMAXStrategy(Board boardC, int difficulte, Player player, Player playerADV, Play play){
        super(boardC, difficulte, player, playerADV);
        this.play = play;
    }

    // methode permet d'excuter l'algo
    @Override
    public int executeAlgo() {
        return negamax(player, this.board, 0, new Point(), 1);
    }

    // int fonction negamax
    public int negamax(Player p, Board board, int depth, Point point, int color) {

        // if (game over or depth = 0)
        if (board.endGame() || (depth == this.difficulte)) {

            //r�cuperer la mobilit� : nombre de cases jouables
            ArrayList<Point> listPointsMove = p.PlayerMove.listPointsMovePlayer(p, board);
            //nombre de pions (mat�riel).
            int nbPawn = p.getScore();
            //  return winning score or eval();
            return color * eval(point, listPointsMove.size(), nbPawn, play);
        }

        int bestScore = Integer.MIN_VALUE;

        // r�cuperer la liste des boards
        ArrayList<Point> listPointsMove = p.PlayerMove.listPointsMovePlayer(p, board);
        ArrayList<Board> diffBoardsWithPoints = listBoards(p, board, listPointsMove);

        // for (each possible move m) {
        for (int i = 0; i < diffBoardsWithPoints.size(); i++) {

            if(depth == 0) this.bestMovedepth0 = i;

            // make move m;
            Board succBoard = diffBoardsWithPoints.get(i);

            // int score = -negamax (depth - 1)
            int score = -negamax(this.playerADV, succBoard, depth + 1, listPointsMove.get(i), -color);

            // unmake move m;

            // if (score > bestScore)
            if (score > bestScore) {
                // bestScore = score;
                bestScore = score;
                // bestMove = m ;
                this.bestMove = this.bestMovedepth0;
            }
        }

        // return bestscore ;
        return bestScore;
    }
}
