package Strategy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * La classe permet d'implementer l'algorithme rondom
 * @author M1 info Rouen (2019/2020)
 * Othello
 */
public class RandomStrategy extends Strategy{
		
	// la liste points possible à jouer  
	private ArrayList<Point> listPointsMove;

	// __construct
	public RandomStrategy(ArrayList<Point> listPointsMove){
		this.listPointsMove = listPointsMove;
	}
	
	// methode permet d'excuter le random
	@Override
	public int executeAlgo() {
		Random r = new Random();
		return r.nextInt(this.listPointsMove.size()) ;
	}
}
