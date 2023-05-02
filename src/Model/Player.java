package Model;

import java.awt.Point;
import java.util.ArrayList;

import Controller.Move;


public class Player {
	
	public boolean isPlayed = false;
	private Pawn pawn;
	private boolean IsComputer;
	private int score;
	public Move PlayerMove;
	private ArrayList<Point> validMoves;
	
	public Player(Pawn pawn,boolean computer) {
		this.pawn = pawn;
		this.IsComputer = computer;
		this.score = 2;
		this.validMoves = new ArrayList<Point>();
		this.PlayerMove = new Move();
	}
	
	public Player(Player player) {
		this.pawn = player.getPawn();
		this.IsComputer = player.isIsComputer();
		this.score = player.getScore();
		this.validMoves = new ArrayList<Point>();
		this.PlayerMove = new Move();
	}
	
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
	
	public void afficheList(ArrayList<Point>  listPoints) {
		for(int i=0;i< listPoints.size();i++) {
			listPoints.get(i);
		}
	}
}
