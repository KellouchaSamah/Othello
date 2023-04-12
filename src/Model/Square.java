package Model;

/**
 * La classe permet de modeliser les cases d'un plateau du jeu
 * @author M1 info Rouen (2019/2020)
 * Othello
 */
public class Square {
	
	//l'etat de la case
	private State SquareState;

	//__constrcteur 
	public Square(){
        this.SquareState = State.NONEState;
    }
	
	// modifier l'�tat de la case
    public void setSquareState(State squareState){
        this.SquareState = squareState;
    }
    
    // avoir l'etat actual de la case
    public State getSquareState(){
        return this.SquareState;
    } 
    
    // savoir si la case est libre ou non
    public boolean isFREE(Square square){
        return square.SquareState == State.NONEState;
    }
}








