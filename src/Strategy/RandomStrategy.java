package Strategy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class RandomStrategy extends Strategy{
		
	private ArrayList<Point> listPointsMove;

	public RandomStrategy(ArrayList<Point> listPointsMove){
		this.listPointsMove = listPointsMove;
	}
	
	@Override
	public int executeAlgo() {
		Random r = new Random();
		return r.nextInt(this.listPointsMove.size()) ;
	}
}
