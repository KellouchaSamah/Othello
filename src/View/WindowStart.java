package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Controller.Play;
import Model.Board;
import Model.Pawn;

public class WindowStart extends JDialog  {

	  /* 
	   * Label pour pion, niveau, mode, et l'algo du jeu
	   */
	  private JLabel  pawnLabel, levelLabel, madeLabel, algoLabel;
	  private JRadioButton tranche1, tranche2, tranche3, tranche4;
	  private JComboBox pawn, level, mode, algo;
	  private JTextField taille;
	  /* la partie */
	  private Play play;
	  /*
	   * constructeur
	   */
	  public WindowStart(JFrame parent, String title, boolean modal, Play play){
	    super(parent, title, modal);
	    this.play = play;
	    this.setSize(500, 250);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    this.initComponent();
	  }

	  /*
	   *  methode permet d'initialiser le dialog
	   */
	  private void initComponent(){
	    
		  /*
		   *  choix du pion
		   */
	    JPanel panPawn = new JPanel();
	    panPawn.setBackground(Color.white);
	    panPawn.setPreferredSize(new Dimension(220, 60));
	    panPawn.setBorder(BorderFactory.createTitledBorder("Play as"));
	    pawn = new JComboBox();
	    pawn.addItem("BLACK");
	    pawn.addItem("WHITE");
	    pawnLabel = new JLabel("Pawn : ");
	    panPawn.add(pawnLabel);
	    panPawn.add(pawn);

	    /*
	     *  choix du niveau du jeu
	     */
	    JPanel panLevel = new JPanel();
	    panLevel.setBackground(Color.white);
	    panLevel.setPreferredSize(new Dimension(220, 60));
	    panLevel.setBorder(BorderFactory.createTitledBorder("Game Difficulty"));
	    level = new JComboBox();
	    level.addItem("EASY");
	    level.addItem("MIDUIM");
	    level.addItem("HARD");
	    levelLabel = new JLabel("Level : ");
	    panLevel.add(levelLabel);
	    panLevel.add(level);
	    /*
	     *  choix du mode du jeu
	     */
	    JPanel panMade = new JPanel();
	    panMade.setBackground(Color.white);
	    panMade.setPreferredSize(new Dimension(220, 60));
	    panMade.setBorder(BorderFactory.createTitledBorder("Game Mode"));
	    mode = new JComboBox();
	    mode.addItem("PL vs AI");
	    mode.addItem("AI vs AI");
	    mode.addItem("PL vs PL");
	    madeLabel = new JLabel("Mode : ");
	    panMade.add(madeLabel);
	    panMade.add(mode);
	    /*
	     *  choix de l'algorithme
	     */
	    JPanel panAlgo = new JPanel();
	    panAlgo.setBackground(Color.white);
	    panAlgo.setPreferredSize(new Dimension(220, 60));
	    panAlgo.setBorder(BorderFactory.createTitledBorder("Game Algorithm"));
	    algo = new JComboBox();
	    algo.addItem("RANDOM");
	    algo.addItem("MINIMAX");
	    algo.addItem("ALPHABETA");
	    algo.addItem("SSS*");
//////////////////////////////////////////////////////////you will add others algo///////////////////////////////////////////////////////////////////////////////
	    algoLabel = new JLabel("Algo : ");
	    panAlgo.add(algoLabel);
	    panAlgo.add(algo);

	    JPanel content = new JPanel();
	    content.setBackground(Color.white);
	    content.add(panPawn);
	    content.add(panMade);
	    content.add(panAlgo);
	    content.add(panLevel);
	    
	    JPanel control = new JPanel();
	    JButton okBoutton = new JButton("PLAY");
	    
	    /* Boutton ok */
	    okBoutton.addActionListener(new ActionListener(){
	    
	    /*
	     *  listener pour le boutton ok
	     */
		public void actionPerformed(ActionEvent e) {
				
			/* le joueur faire le choix de son pion */
	    	Pawn Cpawn = Board.convertStringtoPawn(pawn.getSelectedItem().toString());
	    	if (Cpawn == null) {
	    		/* récuperer le pion */
				play.setPlayerPawn(Pawn.BLACKState);
	    	}else{
	    		/* récuperer le pion */
				play.setPlayerPawn(Cpawn);
	    	};
	    	
	    	/* le joueur faire le choix de niveau du jeu  */
	    	String CLevel = level.getSelectedItem().toString();
	    	if (CLevel == null) {
	    		/* récuperer le level */
	    		play.setLevelGame("ESAY");
	    	}else{
	    		/* récuperer le level */
	    		play.setLevelGame(CLevel);
	    	};
	    	
	    	/* le joueur faire le choix de l'algorithme */
	    	String CAlgo = algo.getSelectedItem().toString();
	    	if (CAlgo == null) {
	    		/* récuperer le level  */
	    		play.setAlgoGame("RANDOM");
	    	}else{
	    		/* récuperer le level  */
	    		play.setAlgoGame(CAlgo);
	    	};
	    				
	    	/* le joueur faire le choix de mode du jeu */ 
	    	String Cmade = mode.getSelectedItem().toString();
	    	if (Cmade == null) {
	    		/* récuperer le mode */
	    		play.setGameMade("PL VS AI");
	    	}else{
	    		/* récuperer le mode */
	    		play.setGameMade(Cmade);
	    	};
			
			setVisible(false);
		}      
	    });

	    /* Boutton back*/
	    JButton cancelBouton = new JButton("BACK");
	    /* listener pour le boutton back*/
	    cancelBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e) {
		    	setVisible(false);
		    	System.exit(0);
	      }      
	    });

	    control.add(okBoutton);
	    control.add(cancelBouton);
	    this.getContentPane().add(content, BorderLayout.CENTER);
	    this.getContentPane().add(control, BorderLayout.SOUTH);
	  }

	/* l'ensemble des getter et setter*/
	public Play getPlay() {
		return play;
	}

	public void setPlay(Play play) {
		this.play = play;
	}  
}