package Model;

import java.awt.Point;
import java.util.ArrayList;

import Controller.Move;

/**
 * La classe permet de modeliser le joueur soit un etre humain ou bien AI
 * @author M1 info Rouen (2019/2020)
 * Othello
 */
public class Player {
	
	// enumeration "WHITE, BLACK, ou NONE"
	public boolean isPlayed = false;
	// enumeration "WHITE, BLACK, ou NONE"
	private Pawn pawn;
	//Si humain ou computer
	private boolean IsComputer;
	//Le score
	private int score;
	// les action de move 
	public Move PlayerMove;
	// list des deplacements valides
	private ArrayList<Point> validMoves;
	
	// __construct
	public Player(Pawn pawn,boolean computer) {
		this.pawn = pawn;
		this.IsComputer = computer;
		this.score = 2;
		this.validMoves = new ArrayList<Point>();
		this.PlayerMove = new Move();
	}
	
	// __construct
	public Player(Player player) {
		this.pawn = player.getPawn();
		this.IsComputer = player.isIsComputer();
		this.score = player.getScore();
		this.validMoves = new ArrayList<Point>();
		this.PlayerMove = new Move();
	}
	
	// # getter et setter
	// l'ensemble des getter et setter
	public boolean getIsPlayed() {
		return isPlayed;
	}

	public void setIsPlayed(boolean played) {
		this.isPlayed = played;
	}

	public Pawn getPawn() {
		return pawn;
	}

	public void setPawn(Pawn pawn) {
		this.pawn = pawn;
	}

	public boolean isIsComputer() {
		return IsComputer;
	}

	public void setIsComputer(boolean isComputer) {
		IsComputer = isComputer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ArrayList<Point> getValidMoves() {
		return validMoves;
	}

	public void setValidMoves(ArrayList<Point> validMoves) {
		this.validMoves = validMoves;
	}
	
	// methode permet de lire une liste des points
	public void afficheList(ArrayList<Point>  listPoints) {
		for(int i=0;i< listPoints.size();i++) {
			listPoints.get(i);
		}
	}
}
