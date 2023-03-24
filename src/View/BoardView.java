package View;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

import Controller.BoardListener;
import Controller.MenuListener;
import Controller.Play;
import Controller.Start;
import Model.Board;
import Model.Pawn;
import Model.Player;
import Model.State;


public class BoardView extends JFrame {

    // #sérial 
	private static final long serialVersionUID = 1L;
	// #chrono 
	public ActionListener tacheTIMER;
	private int delais = 1000;
    private int seconde = 0;
    private Timer timer;
    private JLabel time = new JLabel("00:00");
	// #combo
	// la boutton combo
	private Pawn viewPlayerPawn;
	private String viewGameMade;
	
	
	// #panel de footer
    // d�claration le panel 
    private Box viewPanelFooter    = Box.createHorizontalBox();
    private JLabel viewLabelFooter = new JLabel("Groupe M1 INFO @ROUEN");

    //  
    // Label de tour du joueur
    private JLabel viewTurnPlayer = new JLabel("");
    
    // #panel de temps et scores 
    // d�claration  le panel 
    private Box viewPanelScoreTime = Box.createVerticalBox();
    // label de l'algorithme
    private JLabel viewAlgoGame = new JLabel("");
    // Label de level
    private JLabel viewLevelGame = new JLabel("");
    // Label de score 
    private JLabel viewScorePlayerBlack = new JLabel("02");
    private JLabel viewScorePlayerWhite = new JLabel("02");
    
    // #Menu 
    // menu principale 
    private JMenuBar viewMenuBar = new JMenuBar();
    private JMenu viewMenuGame   = new JMenu("Game");
    private JMenu viewMenuRules  = new JMenu("Rules");
    private JMenu viewMenuHelp   = new JMenu("Help");
    // sous menu Game
    private JMenuItem viewMenuNewGame     = new JMenuItem("New Game");
    private JMenuItem viewMenuPause       = new JMenuItem("Pause");
    private JMenuItem viewMenuExit        = new JMenuItem("Exit");
    // sous menu Level
    private JMenuItem viewMenuLevelEasy   = new JMenuItem("Easy");
    private JMenuItem viewMenuLevelMedium = new JMenuItem("Medium");
    private JMenuItem viewMenuLevelHaro   = new JMenuItem("Haro");
   
    // #plateau de jeu 
    // layout du panel du plateau de jeu 
	private GridLayout viewLayoutGame;
	// le panel du plateau de jeu 
	private  JPanel viewPanelGame = new JPanel();
    // size de plateau de jeu
    private int viewSizePanelGame;
    // matrice des cases
    private SquareView [][] viewBoardGame;
    // play 
    private Play play;
    
    // #fenetre 
    // positionner la fenetre 
    private Dimension viewScreen = Toolkit.getDefaultToolkit().getScreenSize();
    // le fonte
    private Font fonte = new Font("Arial", Font.PLAIN, 4);
    
    // __construct
    public BoardView(Board board, Play play) throws InterruptedException{
    	    	
    	// fermuture de la fenetre 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // play 
        this.play = play;
        
        // lancer la chrono
        chrono();
        
        // ajouter le layout 
        viewLayoutGame = new GridLayout(Board.modelBoardSize, Board.modelBoardSize);
        this.viewPanelGame.setLayout(viewLayoutGame);
        // ajouter le border au panel du jeu
        this.viewPanelGame.setBorder(BorderFactory.createEtchedBorder(Color.white, new Color(156, 156, 156)));
        
        // la taille du plateau du jeu 
        this.viewSizePanelGame = Board.modelBoardSize * 70 + 60; 
        this.viewPanelGame.setPreferredSize(new Dimension(viewSizePanelGame, viewSizePanelGame));
        // CREER LE PLATEAU DES cases
        this.viewBoardGame = new SquareView[Board.modelBoardSize][Board.modelBoardSize];

        // cr�ation de le panel des score et time du jeu
        creatviewPanelScoreTime();
        // ajouter le paneau � la fenetre 
        this.getContentPane().add(viewPanelScoreTime, BorderLayout.EAST);
        
        // cr�ation de l'interface du panneau du jeu
        creatviewBoardGame(board);
        // ajouter le paneau � la fenetre 
        this.getContentPane().add(viewPanelGame, BorderLayout.CENTER);  
        
        // cr�ation de Menu du jeu
        createviewMenu();        
        // ajouter le menu � la fenetre 
        this.getContentPane().add(viewMenuBar, BorderLayout.NORTH);
        
        // ajouter le panneau de footer 
        creatviewFooter();
        this.getContentPane().add(viewPanelFooter, BorderLayout.SOUTH);
            
        // #fenetre 
        // titre de la fenetre
        this.setTitle("Notre Othello Rouen");
        // fixation de la taille du panneau du jeu 
        this.viewPanelGame.setMinimumSize(new Dimension(viewSizePanelGame + 6, viewSizePanelGame + 25)); 
        this.viewPanelGame.setMaximumSize(new Dimension(viewSizePanelGame + 6, viewSizePanelGame + 25));
        
        // fixation de la taille du panneau du score & time
        this.viewPanelScoreTime.setMinimumSize(new Dimension(120, viewSizePanelGame + 25)); 
        this.viewPanelScoreTime.setMaximumSize(new Dimension(120, viewSizePanelGame + 25));
        this.viewPanelScoreTime.setPreferredSize(new Dimension(120, viewSizePanelGame + 25));
        
        // fixation la taille de la fenetre 
        this.setMinimumSize(new Dimension(viewSizePanelGame + 50, viewSizePanelGame + 50));
        
        // visibilite de la fenetre et les panneaux
        this.viewPanelGame.setVisible(true);

        //Stop_Chrono();
        this.setVisible(true);
               
        // Pour centrer la fenetre du jeu paraport � l'�cran
        this.setLocation((viewScreen.width - this.getSize().width)/2,(viewScreen.height - this.getSize().height)/2);
    }
    
    // lire les help du jeu
    public void viewHelpfortheGame() throws IOException {
    	// boite d'affichage
    	JOptionPane d = new JOptionPane();
    	// lire le fichier rulesGame.txt
    	String help = readFile(getClass().getResource("/Files/help.txt"));

    	// affichier les ragles 
    	d.showMessageDialog( null, help, 
    	      "Help", JOptionPane.INFORMATION_MESSAGE);
    	
    	// reprendre le jeu 
    	this.play.getStart().resume();
    	play.pauseGame = false;
   		this.timer.restart();
    }
    
    // lire les ragles du jeu
    public void viewRulesoftheGame() throws IOException {
    	// boite d'affichage
    	JOptionPane d = new JOptionPane();
    	// lire le fichier rulesGame.txt
    	String rules = readFile(getClass().getResource("/Files/rules.txt"));
    	
    	// affichier les ragles 
    	d.showMessageDialog( null, rules, 
    	      "Rules of the game", JOptionPane.INFORMATION_MESSAGE);
    	
    	// reprendre le jeu 
    	this.play.getStart().resume();
    	play.pauseGame = false;
   		this.timer.restart();
    }
    
    // methode permet d'affichier la fin du jeu 
    public void viewWiner(String msg) throws IOException {    	
    	// boite d'affichage
    	JOptionPane d = new JOptionPane();
    	// lire le fichier rulesGame.txt    	

    	// affichier le msg 
    	d.showMessageDialog( null, msg, 
    	      "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // return menu help 
    public JMenu getviewMenuHelp() {
    	return this.viewMenuHelp;
    }
    
    // return menu rules 
    public JMenu getviewMenuRules() {
    	return this.viewMenuRules;
    }
    
    // return menu New Game 
    public JMenuItem getviewMenuNewGame() {
    	return this.viewMenuNewGame;
    }
    
    // return menu Pause 
    public JMenuItem getviewMenuPause() {
    	return this.viewMenuPause;
    }
    
    // return menu Exit 
    public JMenuItem getviewMenuExit() {
    	return this.viewMenuExit;
    }
 
    // lire le fichier rulesGame.txt
	public String readFile(URL url) throws IOException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		String rules = "", line;
		while ((line = in.readLine()) != null)
		{
	      // Afficher le contenu du fichier
			 rules = rules.concat(line + "\n");
		}
		in.close();
				
		return rules;
	}
	
    // chronometre
    public void chrono() throws InterruptedException{
        
        tacheTIMER = (ActionEvent e1) -> {
         seconde++;

         //Afficher le chrono dans un JLabel
         if(seconde < 10) time.setText("00:0"+seconde);
         else time.setText("00:"+seconde);
        };
        //Action et temps d'éxecution de la tache
        timer = new Timer(delais,tacheTIMER);
        //Démarrer le chrono
        timer.start(); 

    }

    // cr�ation le footer
    private void creatviewFooter(){
    	this.viewPanelFooter.add(Box.createHorizontalGlue());
    	this.viewPanelFooter.add(this.viewLabelFooter);
    	this.viewPanelFooter.add(Box.createHorizontalGlue());
    	
    	// modification du colleur   
    	viewLabelFooter.setForeground(Color.WHITE);
    	viewPanelFooter.setOpaque(true);
    	this.viewPanelFooter.setBackground(new Color(102,51,0));
    	Border viewBorderPanelGame = BorderFactory.createLineBorder(new Color(102,51,0), 10);
        this.viewPanelFooter.setBorder(viewBorderPanelGame);
    }
    
    // cr�ation de le panel des score et time du jeu
    private void creatviewPanelScoreTime() {
    	// les boxs pour l'affichage des icon et score
    	Box viewBoxBlack = Box.createHorizontalBox();
    	Box viewBoxWhite = Box.createHorizontalBox();
    	Box viewBoxLevelGame = Box.createHorizontalBox();
    	Box viewBoxAlgoGame = Box.createHorizontalBox();
    	Box viewBoxTurnPlayer = Box.createHorizontalBox();
    	JLabel Tplayer = new JLabel("Turn : ");
    	
    	// label pour le niveau du jeu
    	JLabel level = new JLabel("Level : ");
    	JLabel algo  = new JLabel("");
    	
    	// boxs pour l'affichage de durr� 
    	Box viewBoxMoveDuration = Box.createHorizontalBox();
    	Box viewBoxTime         = Box.createHorizontalBox();
    	
    	// le label de icon White
    	JLabel viewIconPlayerWhite = new JLabel();
    	viewIconPlayerWhite.setIcon(ImageView.viewWHITEState);
    	// le label de icon Black
    	JLabel viewIconPlayerBlack = new JLabel();
    	viewIconPlayerBlack.setIcon(ImageView.viewBLACKState);
    	// le label de durr� 
    	JLabel viewLabelMoveDuration = new JLabel("Move Duration");
    	
    	
    	// les boxs de temps  
    	viewBoxMoveDuration.add(Box.createHorizontalStrut(5));
    	viewBoxMoveDuration.add(viewLabelMoveDuration);
    	viewBoxMoveDuration.add(Box.createHorizontalStrut(5));
    	viewBoxTime.add(time);
    	viewBoxMoveDuration.setBackground(new Color(102,51,0));
    	
    	// ajouter l'icon et le score dans le box Black
    	viewBoxBlack.add(viewIconPlayerBlack);
    	viewBoxBlack.add(Box.createHorizontalStrut(18));
    	viewBoxBlack.add(this.viewScorePlayerBlack);
   	
    	// ajouter l'icon et le score dans le box White
    	viewBoxWhite.add(viewIconPlayerWhite);
    	viewBoxWhite.add(Box.createHorizontalStrut(10));
    	viewBoxWhite.add(this.viewScorePlayerWhite);
    	
    	// niveau de jeu
    	viewBoxLevelGame.add(level);
    	viewBoxLevelGame.add(viewLevelGame);
    	
    	// tour du joueur 
    	viewBoxTurnPlayer.add(Tplayer);
    	viewBoxTurnPlayer.add(viewTurnPlayer);
    	
    	// algorithme du jeu
    	viewBoxAlgoGame.add(algo);
    	viewBoxAlgoGame.add(viewAlgoGame);
    	
    	// box l'affichage de score
    	Box viewBox = Box.createVerticalBox();
    	viewBox.add(Box.createVerticalStrut(50));
    	viewBox.add(viewBoxAlgoGame);
    	viewBox.add(Box.createVerticalStrut(20));
    	viewBox.add(viewBoxTurnPlayer);
    	viewBox.add(Box.createVerticalStrut(70));
    	viewBox.add(viewBoxLevelGame);
    	viewBox.add(Box.createVerticalStrut(20));
    	viewBox.add(viewBoxWhite);
    	viewBox.add(Box.createVerticalStrut(10));
    	viewBox.add(viewBoxBlack);
    	viewBox.add(Box.createVerticalStrut(150));
    	
    	Border viewBorderPanelGame = BorderFactory.createLineBorder(new Color(1,133,94), 1);
    	viewBoxAlgoGame.setBorder(viewBorderPanelGame);
    	// ajouter les boxs dans le paneau de score et time
    	viewPanelScoreTime.add(viewBox);
    	viewPanelScoreTime.add(viewBoxMoveDuration);
    	viewPanelScoreTime.add(viewBoxTime);
    	viewPanelScoreTime.add(Box.createVerticalStrut(200));
    	
    	// modification du colleur pour chaque label 
    	algo.setForeground(new Color(1,133,94));
    	viewAlgoGame.setForeground(new Color(1,133,94));
    	level.setForeground(Color.WHITE);
    	Tplayer.setForeground(Color.GRAY);
    	this.viewTurnPlayer.setForeground(Color.GRAY);
    	this.viewLevelGame.setForeground(Color.RED);
    	this.viewScorePlayerBlack.setForeground(Color.WHITE);
    	this.viewScorePlayerWhite.setForeground(Color.WHITE);
    	viewLabelMoveDuration.setForeground(Color.WHITE);
    	viewPanelScoreTime.setBackground(new Color(102,51,0));
    	viewPanelScoreTime.setOpaque(true);
    	// modification la color du horloge 
    	time.setForeground(Color.RED);
    }
      
    // cr�ation de Menu du jeu
    private void createviewMenu() {
    	// cr�ation du menu principale 
        this.viewMenuBar.add(viewMenuGame);
        this.viewMenuBar.add(viewMenuRules);
        this.viewMenuBar.add(viewMenuHelp);
        
        // ajouter les sous menu Game
        viewMenuGame.add(viewMenuNewGame);
        viewMenuGame.add(viewMenuPause);
        viewMenuGame.add(viewMenuExit);
        
        // ajouter les listeners
        viewMenuRules.addMouseListener(new MenuListener(this.play));
        viewMenuHelp.addMouseListener(new MenuListener(this.play));
        
        // listener pour le nouvelle partie
        viewMenuNewGame.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
	        	
	        	// stoper le time
			   timer.stop();
               play.pauseGame = true;
               play.playnewGame = true;
               
               int option = play.pauseGame();
               
               if(	option != JOptionPane.CANCEL_OPTION && 
               		option != JOptionPane.CLOSED_OPTION){	
            	  
            	   // fermer la fenetre d'affichage
            	   setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                   play.pauseGame = false;
              	   play.playnewGame = false;
              	   // indique que le joueur a demmander de jouer un nouvelle partie
            	   Start.newPlay = true;
               	   
            	   // appelle � la fonction nouveau partie 
            	   if(play.playendGame = true) {
           				try {
           					play.getStart().newGame();
           				} catch (InterruptedException | IOException e) {
           					e.printStackTrace();
           				}
               		}
               	}else {
               		// le boutton cancel
               		play.pauseGame = false;
               		play.playnewGame = false;
               		timer.restart();
               	}
	        }
        });
       
        // listener pour pause le jeu
        viewMenuPause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
            
            	// stoper le time
               timer.stop();
               play.pauseGame = true;
               
               int option = play.pauseGame();
	           	// boutton ok
	           	if(	option == JOptionPane.NO_OPTION || 
	           		option == JOptionPane.CLOSED_OPTION){	
	           		System.exit(0);
	           	}
	           	
	           	// le boutton cancel
	           	if(	option == JOptionPane.OK_OPTION ){	
	           		play.pauseGame = false;
	           		timer.restart();
		        }
               
            }
        });
        
        // listener pour quitterle jeu
        viewMenuExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
            	timer.stop();
            	play.getStart().suspend();
            	
            	// la boite de dialog
            	String msg ="Are you sure you wish to leave this match ? \n You will lose all progress.";
            	String title ="Leave The Game";
            	JOptionPane jop = new JOptionPane();			
            	int option = jop.showConfirmDialog(null, msg, title,
            			JOptionPane.CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            	            	
            	// le bouton ok
            	if(	option != JOptionPane.CANCEL_OPTION && 
            		option != JOptionPane.CLOSED_OPTION){	
            		System.exit(0);
            	} 
            	
            	play.getStart().resume();
            	timer.restart();
            }
        });
    }
    
    // cr�ation de l'interface du panneau du jeu
    public void creatviewBoardGame(Board board){
    	
    	// Create a border pour le panal
        Border viewBorderPanelGame = BorderFactory.createLineBorder(Color.BLACK, 1);
        this.viewPanelGame.setBorder(viewBorderPanelGame);
    	
        // RW == ligne, Col == colone
        // poser les quatres pion de d�mmarage 
    	int posRWSquareWHITE = Board.modelBoardSize / 2; 
        int posCOLSquareWHITE = Board.modelBoardSize / 2 - 1;
        int posRWSquareBLACK = Board.modelBoardSize / 2;
        int posCOLSquareBLACK = Board.modelBoardSize / 2;
        
        // cr�ation l'ensemble des cases 
        for (int i = 0; i < Board.modelBoardSize; i++) {
            for (int j = 0; j < Board.modelBoardSize; j++) {
            	viewBoardGame[i][j] = null;
            	viewBoardGame[i][j] = new SquareView();
            	viewBoardGame[i][j].setOpaque(true);
            	
            	/*GrigBagConstraint.NONE pour ne pas redimensionner le composant.*/
               this.viewPanelGame.add(viewBoardGame[i][j], new GridBagConstraints(i, j, 1, 1, 0.0, 0.0, 
            		   GridBagConstraints.CENTER, GridBagConstraints.NONE, 
            		   new Insets(0, 0, 0, 0), 0, 0));
               
               	// remplir le board view � partir de board 
	             if(board.modelBoardSqures[i][j].getSquareState() == State.BLACKState) {
	            	 viewBoardGame[i][j].setIcon(ImageView.viewBLACKState);
	             }else if(board.modelBoardSqures[i][j].getSquareState() == State.WHITEState) {
	            	 viewBoardGame[i][j].setIcon(ImageView.viewWHITEState);
	             }
                                            
               // color #01855e https://www.rapidtables.com/web/color/RGB_Color.html
               viewBoardGame[i][j].setBackground(new Color(1,133,94));
               this.viewPanelGame.setBackground(new Color(1,133,94));
               
               //pour eviter un bug lors de l'appel de nouveau() 
               viewBoardGame[i][j].removeMouseListener(new BoardListener(this.play));
               // ajouter les listener pour les clics!!!
               viewBoardGame[i][j].addMouseListener(new BoardListener(this.play)); 
            }
        }
    }
    
    // afficher le board view
    public void viewUpdateDisplay(Board board) {
    	
    	for (int i = 0; i <Board.modelBoardSize; i++) {
            for (int j = 0; j < Board.modelBoardSize; j++) {
		    	// remplir le board view � partir de board 
		        if(board.modelBoardSqures[i][j].getSquareState() == State.BLACKState) {
		       	 viewBoardGame[i][j].setIcon(ImageView.viewBLACKState);
		        }else if(board.modelBoardSqures[i][j].getSquareState() == State.WHITEState) {
		       	 viewBoardGame[i][j].setIcon(ImageView.viewWHITEState);
		        }
            }
    	}
    }
    
    // affichier les position possible de deplacement
    public void viewUpdatePointsMove(ArrayList<Point> listpoints) {
    	
    	for(int i=0; i < listpoints.size(); i++) {
    		viewBoardGame[(int) listpoints.get(i).getX()][(int) listpoints.get(i).getY()].setIcon(ImageView.viewChoiseState);
    	}
    }
    
    // methode pour afficher le score 
    public void viewNewScore(Player player, Player playerAdv) {
    	// changer le score
		player.setScore(player.getScore() + 1);
		int scorePlayer = player.getScore();
				
		
		if(player.getPawn() == Pawn.BLACKState) {
			if(scorePlayer  < 10) {
				viewScorePlayerBlack.setText("0"+ (scorePlayer ));
			}else viewScorePlayerBlack.setText(""+ (scorePlayer ));
		}else {
			if(scorePlayer  < 10) {
				viewScorePlayerWhite.setText("0"+ (scorePlayer ));
			}else viewScorePlayerWhite.setText(""+ (scorePlayer ));
		}
		
		// player adversaire 
		int scorePlayerAdv = playerAdv.getScore();
		if(playerAdv.getPawn() == Pawn.BLACKState) {
			if(scorePlayerAdv  < 10) {
				viewScorePlayerBlack.setText("0"+ (scorePlayerAdv ));
			}else viewScorePlayerBlack.setText(""+ (scorePlayerAdv));
		}else {
			if(scorePlayerAdv  < 10) {
				viewScorePlayerWhite.setText("0"+ (scorePlayerAdv ));
			}else viewScorePlayerWhite.setText(""+ (scorePlayerAdv));
		}
    }
    
    // affichier les position possible de deplacement
    public void viewdeletePointsMove() {
    	
    	for (int i = 0; i <Board.modelBoardSize; i++) {
            for (int j = 0; j < Board.modelBoardSize; j++) {
		    	// remplir le board view � partir de board 
            	if(viewBoardGame[i][j].getIcon() == ImageView.viewChoiseState) viewBoardGame[i][j].setIcon(null);
            }
    	}
    }
    
    // # getter et setter 
    // l'ensemble des getter et setter
	public Pawn getviewPlayerPawn() {
		return viewPlayerPawn;
	}


	public void setviewPlayerPawn(Pawn viewPlayerPawn) {
		this.viewPlayerPawn = viewPlayerPawn;
	}


	public String getviewGameMade() {
		return viewGameMade;
	}


	public void setviewGameMade(String viewGameMade) {
		this.viewGameMade = viewGameMade;
	}


	public Box getviewPanelFooter() {
		return viewPanelFooter;
	}


	public void setviewPanelFooter(Box viewPanelFooter) {
		this.viewPanelFooter = viewPanelFooter;
	}

	public JLabel getviewLabelFooter() {
		return viewLabelFooter;
	}

	public void setviewLabelFooter(JLabel viewLabelFooter) {
		this.viewLabelFooter = viewLabelFooter;
	}

	public JLabel getviewTurnPlayer() {
		return viewTurnPlayer;
	}

	public void setviewTurnPlayer(JLabel viewTurnPlayer) {
		this.viewTurnPlayer = viewTurnPlayer;
	}

	public Box getviewPanelScoreTime() {
		return viewPanelScoreTime;
	}

	public void setviewPanelScoreTime(Box viewPanelScoreTime) {
		this.viewPanelScoreTime = viewPanelScoreTime;
	}

	public JLabel getviewAlgoGame() {
		return viewAlgoGame;
	}

	public void setviewAlgoGame(JLabel viewAlgoGame) {
		this.viewAlgoGame = viewAlgoGame;
	}

	public JLabel getviewLevelGame() {
		return viewLevelGame;
	}

	public void setviewLevelGame(JLabel viewLevelGame) {
		this.viewLevelGame = viewLevelGame;
	}

	public JLabel getviewScorePlayerBlack() {
		return viewScorePlayerBlack;
	}

	public void setviewScorePlayerBlack(JLabel viewScorePlayerBlack) {
		this.viewScorePlayerBlack = viewScorePlayerBlack;
	}

	public JLabel getviewScorePlayerWhite() {
		return viewScorePlayerWhite;
	}

	public void setviewScorePlayerWhite(JLabel viewScorePlayerWhite) {
		this.viewScorePlayerWhite = viewScorePlayerWhite;
	}

	public JMenuBar getviewMenuBar() {
		return viewMenuBar;
	}

	public void setviewMenuBar(JMenuBar viewMenuBar) {
		this.viewMenuBar = viewMenuBar;
	}

	public JMenu getviewMenuGame() {
		return viewMenuGame;
	}

	public void setviewMenuGame(JMenu viewMenuGame) {
		this.viewMenuGame = viewMenuGame;
	}

	public JMenuItem getviewMenuLevelEasy() {
		return viewMenuLevelEasy;
	}

	public void setviewMenuLevelEasy(JMenuItem viewMenuLevelEasy) {
		this.viewMenuLevelEasy = viewMenuLevelEasy;
	}

	public JMenuItem getviewMenuLevelMedium() {
		return viewMenuLevelMedium;
	}

	public void setviewMenuLevelMedium(JMenuItem viewMenuLevelMedium) {
		this.viewMenuLevelMedium = viewMenuLevelMedium;
	}

	public JMenuItem getviewMenuLevelHaro() {
		return viewMenuLevelHaro;
	}

	public void setviewMenuLevelHaro(JMenuItem viewMenuLevelHaro) {
		this.viewMenuLevelHaro = viewMenuLevelHaro;
	}

	public GridLayout getviewLayoutGame() {
		return viewLayoutGame;
	}

	public void setviewLayoutGame(GridLayout viewLayoutGame) {
		this.viewLayoutGame = viewLayoutGame;
	}

	public JPanel getviewPanelGame() {
		return viewPanelGame;
	}

	public void setviewPanelGame(JPanel viewPanelGame) {
		this.viewPanelGame = viewPanelGame;
	}

	public int getviewSizePanelGame() {
		return viewSizePanelGame;
	}

	public void setviewSizePanelGame(int viewSizePanelGame) {
		this.viewSizePanelGame = viewSizePanelGame;
	}

	public SquareView[][] getviewBoardGame() {
		return viewBoardGame;
	}

	public void setviewBoardGame(SquareView[][] viewBoardGame) {
		this.viewBoardGame = viewBoardGame;
	}

	public Dimension getviewScreen() {
		return viewScreen;
	}

	public void setviewScreen(Dimension viewScreen) {
		this.viewScreen = viewScreen;
	}

	public void setviewMenuRules(JMenu viewMenuRules) {
		this.viewMenuRules = viewMenuRules;
	}

	public void setviewMenuHelp(JMenu viewMenuHelp) {
		this.viewMenuHelp = viewMenuHelp;
	}

	public void setviewMenuNewGame(JMenuItem viewMenuNewGame) {
		this.viewMenuNewGame = viewMenuNewGame;
	}

	public void setviewMenuPause(JMenuItem viewMenuPause) {
		this.viewMenuPause = viewMenuPause;
	}

	public void setviewMenuExit(JMenuItem viewMenuExit) {
		this.viewMenuExit = viewMenuExit;
	}

	public int getSeconde() {
		return seconde;
	}

	public void setSeconde(int seconde) {
		this.seconde = seconde;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Play getPlay() {
		return play;
	}

	public void setPlay(Play play) {
		this.play = play;
	}
}
