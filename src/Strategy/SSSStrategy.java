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
public class SSSStrategy extends Strategy{

	// la partie
	private Play play;
	
	// __construct
	public public SSSStrategy(Board boardC, int difficulte, Player player, Player playerADV, Play play){
		super(boardC, difficulte, player, playerADV);
		this.play = play;
	}
	
	// methode permet d'excuter l'algo
	@Override
	public int executeAlgo() {
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		
		return (int) sssStar(this.player, this.board, new Point(), 0);
	}
	
	//int alphabêta(int depth, int alpha, int bêta)
	public double sssStar(Player p, Board board, Point point, int depth) {
	    // La fonction sssStar prend en paramètres un joueur, un plateau, un point représentant le dernier coup joué et une profondeur de recherche.

	    if (board.endGame() || depth == 0) {
	       // Si le point est un état terminal ou si la profondeur maximale est atteinte, renvoyer la valeur d'évaluation du plateau pour le joueur actuel.
	    	
	        return eval(point, listPointsMove.size(), nbPawn, play);
	    }

	    if (p.getPawn() == this.player.getPawn()) {
	        // Si le joueur actuel est un joueur Max, chercher le coup optimal parmi les coups valides.
	        double maxValue = Double.NEGATIVE_INFINITY;
	        for (Point child : board.getValidMoves()) {
	            // Pour chaque coup valide, faire le mouvement sur le plateau, appeler la fonction sssStar sur l'adversaire, puis annuler le mouvement pour revenir à l'état précédent.
	            board.makeMove(p, child);
	            double value = sssStar(p.getOpponent(), board, child, depth - 1);
	            board.undoMove(p, child);

	            // Mettre à jour la valeur maximale.
	            maxValue = Math.max(maxValue, value);
	        }

	        return maxValue;
	    } else {
	        // Si le joueur actuel est un joueur Min, chercher le coup qui minimise le score du joueur Max.
	        double minValue = Double.POSITIVE_INFINITY;
	        for (Point child : board.getValidMoves()) {
	            // Pour chaque coup valide, faire le mouvement sur le plateau, appeler la fonction sssStar sur l'adversaire, puis annuler le mouvement pour revenir à l'état précédent.
	            board.makeMove(p, child);
	            double value = sssStar(p.getOpponent(), board, child, depth - 1);
	            board.undoMove(p, child);

	            // Mettre à jour la valeur minimale.
	            minValue = Math.min(minValue, value);
	        }

	        return minValue;
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
	