package Strategy;

import java.awt.Point;
import java.util.ArrayList;

import Controller.Play;
import Model.Board;
import Model.Player;


public abstract class Strategy {
	
	protected Board board;
	protected int difficulte ;
	protected Player player;
	protected Player playerADV;
	public int bestMove = 0;
	protected int bestMovedepth0;
	
	public Strategy(){
	}
	
	public Strategy(Board boardC, int difficulte, Player player, Player playerADV) {
		board = new Board(boardC);
		this.difficulte = difficulte;
		this.player = player;
		this.playerADV = playerADV;
	}

	public abstract int executeAlgo();
	
	public ArrayList<Board> listBoards(Player p,
		                              Board board, 
		                              ArrayList<Point> listPointsMove) {
		ArrayList<Board> boards = new ArrayList<>();
		
		for (Point movePoint : listPointsMove) {
			Board resultingBoard = new Board(board);
			
			if(p.getPawn() == player.getPawn()) {
							
				player.PlayerMove.choiceMove(resultingBoard, player, new Point(movePoint));
				player.PlayerMove.chageStatesAdvrs(player, playerADV, new Point(movePoint), resultingBoard);
				
				boards.add(resultingBoard);
			}else {
				playerADV.PlayerMove.choiceMove(resultingBoard, playerADV, new Point(movePoint));
				playerADV.PlayerMove.chageStatesAdvrs(playerADV, player, new Point(movePoint), resultingBoard);
				
				boards.add(resultingBoard);
			}
		}
		return boards;
	}
	
	public int eval(Point powerPoint, int mobilite, int materiel, Play play) {
		int [][] evalPowerPoint;
		
		evalPowerPoint = new int[][]
	            {
					{ 500, -150, 30, 10, 10, 30, -150,  500},
					
					{-150, -250,  0,  0,  0,  0, -250, -150},
					
					{ 30,     0,  1,  2,  2,  1,    0,   30},
					
					{ 10,     0,  2, 16, 16,  2,    0,   10},
					
					{ 10,     0,  2, 16, 16,  2,    0,   10},
					
					{ 30,     0,  1,  2,  2,  1,    0,   30},
					
					{-150, -250,  0,  0,  0,  1, -250, -150},
					
					{ 500, -150, 30, 10, 10, 30, -150,  500},
	            };
		
				if(play.getInbrCoup() <= 30)
					return evalPowerPoint[powerPoint.x][powerPoint.y] + mobilite;
				else if ((play.getInbrCoup() > 30) && (play.getInbrCoup() <= 56)) 
					return evalPowerPoint[powerPoint.x][powerPoint.y] + mobilite + materiel;
				else return materiel;
	}	
}
