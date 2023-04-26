package Controller;

import java.io.IOException;


public class Start extends Thread{
	
	// pour creer un new jeu 
	public static boolean newPlay;
	// pour partie du jeu 
	private Play play;
	
	// __construct
	public Start() {
		this.play = new Play(this);
		newPlay = false;
	}
	
	// pour commencer un nouvelle partie 
	@SuppressWarnings("deprecation")
	public void newGame() throws InterruptedException, IOException {
		
		// fermer la fenetre d'affichage 
		play.getControllerbordview().dispose();
		this.interrupt();
		// creer une nouvelle partie
		Start.main(null);
	}
	
	// recuperer la partie en cours 
	public Play getPlay() {
		return this.play;
	}
	
	// setPlay
	public void setPlay(Play play) {
		this.play = play;
	}
	
	// methode pour commencer le jeu
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

	// la fonction main de notre application
	public static void main(String[] args){
		// starter le jeu
		Start start = new Start();
	    start.start();
	}	
}
