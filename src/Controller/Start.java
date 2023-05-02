package Controller;

import java.io.IOException;


public class Start extends Thread{
	
	public static boolean newPlay;
	private Play play;
	
	// __construct
	public Start() {
		this.play = new Play(this);
		newPlay = false;
	}
	
	@SuppressWarnings("deprecation")
	public void newGame() throws InterruptedException, IOException {
		
		play.getControllerbordview().dispose();
		this.interrupt();
		Start.main(null);
	}
	
	public Play getPlay() {
		return this.play;
	}
	
	// setPlay
	public void setPlay(Play play) {
		this.play = play;
	}
	
	@Override
	public void run() {
		try {
			
			this.play.startGame();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		// starter le jeu
		Start start = new Start();
	    start.start();
	}	
}
