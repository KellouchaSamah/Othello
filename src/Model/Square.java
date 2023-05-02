package Model;


public class Square {
	
	private State SquareState;

	public Square(){
        this.SquareState = State.NONEState;
    }
	
    public void setSquareState(State squareState){
        this.SquareState = squareState;
    }
    
    public State getSquareState(){
        return this.SquareState;
    } 
    
    public boolean isFREE(Square square){
        return square.SquareState == State.NONEState;
    }
}








