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

   

	public ActionListener tacheTIMER;
	private int delais = 1000;
    private int seconde = 0;
    private Timer timer;
    private JLabel time = new JLabel("00:00");
	private Pawn viewPlayerPawn;
	private String viewGameMade;
	
	
    private Box viewPanelFooter = Box.createHorizontalBox();
    private JLabel viewLabelFooter = new JLabel("Groupe M1 INFO @ROUEN");


    private JLabel viewTurnPlayer = new JLabel("");
    
    private Box viewPanelScoreTime = Box.createVerticalBox();
    private JLabel viewAlgoGame = new JLabel("");
    private JLabel viewLevelGame = new JLabel("");
    private JLabel viewScorePlayerBlack = new JLabel("02");
    private JLabel viewScorePlayerWhite = new JLabel("02");
    
    private JMenuBar viewMenuBar = new JMenuBar();
    private JMenu viewMenuGame   = new JMenu("Game");
    private JMenu viewMenuRules  = new JMenu("Rules");
    private JMenu viewMenuHelp   = new JMenu("Help");
    private JMenuItem viewMenuNewGame     = new JMenuItem("New Game");
    private JMenuItem viewMenuPause       = new JMenuItem("Pause");
    private JMenuItem viewMenuExit        = new JMenuItem("Exit");
    private JMenuItem viewMenuLevelEasy   = new JMenuItem("Easy");
    private JMenuItem viewMenuLevelMedium = new JMenuItem("Medium");
    private JMenuItem viewMenuLevelHaro   = new JMenuItem("Haro");
   
	private GridLayout viewLayoutGame;
	private  JPanel viewPanelGame = new JPanel();
    private int viewSizePanelGame;
    private SquareView [][] viewBoardGame;

    private Play play;
    
    private Dimension viewScreen = Toolkit.getDefaultToolkit().getScreenSize();
    private Font fonte = new Font("Arial", Font.PLAIN, 4);
    
    public BoardView(Board board, Play play) throws InterruptedException{
    	    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.play = play;
        
        chrono();
        
        viewLayoutGame = new GridLayout(Board.modelBoardSize, Board.modelBoardSize);
        this.viewPanelGame.setLayout(viewLayoutGame);
        this.viewPanelGame.setBorder(BorderFactory.createEtchedBorder(Color.white, new Color(156, 156, 156)));
        
        this.viewSizePanelGame = Board.modelBoardSize * 70 + 60;
        this.viewPanelGame.setPreferredSize(new Dimension(viewSizePanelGame, viewSizePanelGame));
        this.viewBoardGame = new SquareView[Board.modelBoardSize][Board.modelBoardSize];

        creatviewPanelScoreTime();
        this.getContentPane().add(viewPanelScoreTime, BorderLayout.EAST);
        
        creatviewBoardGame(board);
        this.getContentPane().add(viewPanelGame, BorderLayout.CENTER);
        
        createviewMenu();
        this.getContentPane().add(viewMenuBar, BorderLayout.NORTH);
        
        creatviewFooter();
        this.getContentPane().add(viewPanelFooter, BorderLayout.SOUTH);
            
        this.setTitle("Notre Othello Rouen");
        this.viewPanelGame.setMinimumSize(new Dimension(viewSizePanelGame + 6, viewSizePanelGame + 25));
        this.viewPanelGame.setMaximumSize(new Dimension(viewSizePanelGame + 6, viewSizePanelGame + 25));
        
        this.viewPanelScoreTime.setMinimumSize(new Dimension(120, viewSizePanelGame + 25));
        this.viewPanelScoreTime.setMaximumSize(new Dimension(120, viewSizePanelGame + 25));
        this.viewPanelScoreTime.setPreferredSize(new Dimension(120, viewSizePanelGame + 25));
        
        this.setMinimumSize(new Dimension(viewSizePanelGame + 50, viewSizePanelGame + 50));
        
        this.viewPanelGame.setVisible(true);

        this.setVisible(true);
               
        this.setLocation((viewScreen.width - this.getSize().width)/2,(viewScreen.height - this.getSize().height)/2);
    }
    
    public void viewHelpfortheGame() throws IOException {
    	JOptionPane d = new JOptionPane();
    	String help = readFile(getClass().getResource("/Files/help.txt"));

    	d.showMessageDialog( null, help,
    	      "Help", JOptionPane.INFORMATION_MESSAGE);
    	
    	this.play.getStart().resume();
    	play.pauseGame = false;
   		this.timer.restart();
    }
    
    public void viewRulesoftheGame() throws IOException {
    	JOptionPane d = new JOptionPane();
    	String rules = readFile(getClass().getResource("/Files/rules.txt"));
    	
    	d.showMessageDialog( null, rules,
    	      "Rules of the game", JOptionPane.INFORMATION_MESSAGE);
    	
    	this.play.getStart().resume();
    	play.pauseGame = false;
   		this.timer.restart();
    }
    
    public void viewWiner(String msg) throws IOException {
    	JOptionPane d = new JOptionPane();

    	d.showMessageDialog( null, msg,
    	      "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public JMenu getviewMenuHelp() {
    	return this.viewMenuHelp;
    }
    

    public JMenu getviewMenuRules() {
    	return this.viewMenuRules;
    }
    
    public JMenuItem getviewMenuNewGame() {
    	return this.viewMenuNewGame;
    }
    

    public JMenuItem getviewMenuPause() {
    	return this.viewMenuPause;
    }
    
    public JMenuItem getviewMenuExit() {
    	return this.viewMenuExit;
    }
 
	public String readFile(URL url) throws IOException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		String rules = "", line;
		while ((line = in.readLine()) != null)
		{
			 rules = rules.concat(line + "\n");
		}
		in.close();
				
		return rules;
	}
	
    public void chrono() throws InterruptedException{
        
        tacheTIMER = (ActionEvent e1) -> {
         seconde++;

         if(seconde < 10) time.setText("00:0"+seconde);
         else time.setText("00:"+seconde);
        };
        timer = new Timer(delais,tacheTIMER);
        timer.start();

    }

    private void creatviewFooter(){
    	this.viewPanelFooter.add(Box.createHorizontalGlue());
    	this.viewPanelFooter.add(this.viewLabelFooter);
    	this.viewPanelFooter.add(Box.createHorizontalGlue());
    	
    	viewLabelFooter.setForeground(Color.WHITE);
    	viewPanelFooter.setOpaque(true);
    	this.viewPanelFooter.setBackground(new Color(102,51,0));
    	Border viewBorderPanelGame = BorderFactory.createLineBorder(new Color(102,51,0), 10);
        this.viewPanelFooter.setBorder(viewBorderPanelGame);
    }
    
    private void creatviewPanelScoreTime() {
    	Box viewBoxBlack = Box.createHorizontalBox();
    	Box viewBoxWhite = Box.createHorizontalBox();
    	Box viewBoxLevelGame = Box.createHorizontalBox();
    	Box viewBoxAlgoGame = Box.createHorizontalBox();
    	Box viewBoxTurnPlayer = Box.createHorizontalBox();
    	JLabel Tplayer = new JLabel("Turn : ");
    	
    	JLabel level = new JLabel("Level : ");
    	JLabel algo  = new JLabel("");
    	
    	Box viewBoxMoveDuration = Box.createHorizontalBox();
    	Box viewBoxTime         = Box.createHorizontalBox();
    	
    	JLabel viewIconPlayerWhite = new JLabel();
    	viewIconPlayerWhite.setIcon(ImageView.viewWHITEState);
    	JLabel viewIconPlayerBlack = new JLabel();
    	viewIconPlayerBlack.setIcon(ImageView.viewBLACKState);
    	JLabel viewLabelMoveDuration = new JLabel("Move Duration");
    	
    	
    	viewBoxMoveDuration.add(Box.createHorizontalStrut(5));
    	viewBoxMoveDuration.add(viewLabelMoveDuration);
    	viewBoxMoveDuration.add(Box.createHorizontalStrut(5));
    	viewBoxTime.add(time);
    	viewBoxMoveDuration.setBackground(new Color(102,51,0));
    	
    	viewBoxBlack.add(viewIconPlayerBlack);
    	viewBoxBlack.add(Box.createHorizontalStrut(18));
    	viewBoxBlack.add(this.viewScorePlayerBlack);
   	
    	viewBoxWhite.add(viewIconPlayerWhite);
    	viewBoxWhite.add(Box.createHorizontalStrut(10));
    	viewBoxWhite.add(this.viewScorePlayerWhite);
    	
    	viewBoxLevelGame.add(level);
    	viewBoxLevelGame.add(viewLevelGame);
    	
    	viewBoxTurnPlayer.add(Tplayer);
    	viewBoxTurnPlayer.add(viewTurnPlayer);
    	
    	viewBoxAlgoGame.add(algo);
    	viewBoxAlgoGame.add(viewAlgoGame);
    	
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
    	viewPanelScoreTime.add(viewBox);
    	viewPanelScoreTime.add(viewBoxMoveDuration);
    	viewPanelScoreTime.add(viewBoxTime);
    	viewPanelScoreTime.add(Box.createVerticalStrut(200));
    	
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
    	time.setForeground(Color.RED);
    }
      
    private void createviewMenu() {
        this.viewMenuBar.add(viewMenuGame);
        this.viewMenuBar.add(viewMenuRules);
        this.viewMenuBar.add(viewMenuHelp);
        
        viewMenuGame.add(viewMenuNewGame);
        viewMenuGame.add(viewMenuPause);
        viewMenuGame.add(viewMenuExit);
        
        viewMenuRules.addMouseListener(new MenuListener(this.play));
        viewMenuHelp.addMouseListener(new MenuListener(this.play));
        
        viewMenuNewGame.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
	        	
			   timer.stop();
               play.pauseGame = true;
               play.playnewGame = true;
               
               int option = play.pauseGame();
               
               if(	option != JOptionPane.CANCEL_OPTION && 
               		option != JOptionPane.CLOSED_OPTION){	
            	  
            	   setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                   play.pauseGame = false;
              	   play.playnewGame = false;
            	   Start.newPlay = true;
               	   
            	   if(play.playendGame = true) {
           				try {
           					play.getStart().newGame();
           				} catch (InterruptedException | IOException e) {
           					e.printStackTrace();
           				}
               		}
               	}else {
               		play.pauseGame = false;
               		play.playnewGame = false;
               		timer.restart();
               	}
	        }
        });
       
        viewMenuPause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
            
               timer.stop();
               play.pauseGame = true;
               
               int option = play.pauseGame();
	           	if(	option == JOptionPane.NO_OPTION ||
	           		option == JOptionPane.CLOSED_OPTION){	
	           		System.exit(0);
	           	}
	           	
	           	if(	option == JOptionPane.OK_OPTION ){
	           		play.pauseGame = false;
	           		timer.restart();
		        }
               
            }
        });
        
        viewMenuExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
            	timer.stop();
            	play.getStart().suspend();
            	
            	String msg ="Are you sure you wish to leave this match ? \n You will lose all progress.";
            	String title ="Leave The Game";
            	JOptionPane jop = new JOptionPane();			
            	int option = jop.showConfirmDialog(null, msg, title,
            			JOptionPane.CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            	            	
            	if(	option != JOptionPane.CANCEL_OPTION &&
            		option != JOptionPane.CLOSED_OPTION){	
            		System.exit(0);
            	} 
            	
            	play.getStart().resume();
            	timer.restart();
            }
        });
    }
    
    public void creatviewBoardGame(Board board){
    	
        Border viewBorderPanelGame = BorderFactory.createLineBorder(Color.BLACK, 1);
        this.viewPanelGame.setBorder(viewBorderPanelGame);
    	
    	int posRWSquareWHITE = Board.modelBoardSize / 2;
        int posCOLSquareWHITE = Board.modelBoardSize / 2 - 1;
        int posRWSquareBLACK = Board.modelBoardSize / 2;
        int posCOLSquareBLACK = Board.modelBoardSize / 2;
        
        for (int i = 0; i < Board.modelBoardSize; i++) {
            for (int j = 0; j < Board.modelBoardSize; j++) {
            	viewBoardGame[i][j] = null;
            	viewBoardGame[i][j] = new SquareView();
            	viewBoardGame[i][j].setOpaque(true);
            	
               this.viewPanelGame.add(viewBoardGame[i][j], new GridBagConstraints(i, j, 1, 1, 0.0, 0.0,
            		   GridBagConstraints.CENTER, GridBagConstraints.NONE, 
            		   new Insets(0, 0, 0, 0), 0, 0));
               
	             if(board.modelBoardSqures[i][j].getSquareState() == State.BLACKState) {
	            	 viewBoardGame[i][j].setIcon(ImageView.viewBLACKState);
	             }else if(board.modelBoardSqures[i][j].getSquareState() == State.WHITEState) {
	            	 viewBoardGame[i][j].setIcon(ImageView.viewWHITEState);
	             }
                                            
               viewBoardGame[i][j].setBackground(new Color(1,133,94));
               this.viewPanelGame.setBackground(new Color(1,133,94));
               
               viewBoardGame[i][j].removeMouseListener(new BoardListener(this.play));
               viewBoardGame[i][j].addMouseListener(new BoardListener(this.play));
            }
        }
    }
    
    public void viewUpdateDisplay(Board board) {
    	
    	for (int i = 0; i <Board.modelBoardSize; i++) {
            for (int j = 0; j < Board.modelBoardSize; j++) {
		        if(board.modelBoardSqures[i][j].getSquareState() == State.BLACKState) {
		       	 viewBoardGame[i][j].setIcon(ImageView.viewBLACKState);
		        }else if(board.modelBoardSqures[i][j].getSquareState() == State.WHITEState) {
		       	 viewBoardGame[i][j].setIcon(ImageView.viewWHITEState);
		        }
            }
    	}
    }
    
    public void viewUpdatePointsMove(ArrayList<Point> listpoints) {
    	
    	for(int i=0; i < listpoints.size(); i++) {
    		viewBoardGame[(int) listpoints.get(i).getX()][(int) listpoints.get(i).getY()].setIcon(ImageView.viewChoiseState);
    	}
    }
    
    public void viewNewScore(Player player, Player playerAdv) {
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
    
    public void viewdeletePointsMove() {
    	
    	for (int i = 0; i <Board.modelBoardSize; i++) {
            for (int j = 0; j < Board.modelBoardSize; j++) {
            	if(viewBoardGame[i][j].getIcon() == ImageView.viewChoiseState) viewBoardGame[i][j].setIcon(null);
            }
    	}
    }
    
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
