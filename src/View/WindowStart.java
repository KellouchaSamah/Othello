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

	  private JLabel  pawnLabel, levelLabel, madeLabel, algoLabel;
	  private JRadioButton tranche1, tranche2, tranche3, tranche4;
	  private JComboBox pawn;
	private JComboBox level;
	private JComboBox mode;
	private JComboBox algo;
	  private JTextField taille;
	  private Play play;

	  public WindowStart(JFrame parent, String title, boolean modal, Play play){
	    super(parent, title, modal);
	    this.play = play;
	    this.setSize(500, 250);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    this.initComponent();
	  }


	  private void initComponent(){
	    

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

	    JPanel panAlgo = new JPanel();
	    panAlgo.setBackground(Color.white);
	    panAlgo.setPreferredSize(new Dimension(220, 60));
	    panAlgo.setBorder(BorderFactory.createTitledBorder("Game Algorithm"));
	    algo = new JComboBox();
	    algo.addItem("RANDOM");
	    algo.addItem("MINIMAX");
	    algo.addItem("ALPHABETA");
	    algo.addItem("SSS*");
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
	    

	    okBoutton.addActionListener(new ActionListener(){

		public void actionPerformed(ActionEvent e) {
				
	    	Pawn Cpawn = Board.convertStringtoPawn(pawn.getSelectedItem().toString());
	    	if (Cpawn == null) {
				play.setPlayerPawn(Pawn.BLACKState);
	    	}else{
				play.setPlayerPawn(Cpawn);
	    	};
	    	
	    	String CLevel = level.getSelectedItem().toString();
	    	if (CLevel == null) {
	    		play.setLevelGame("ESAY");
	    	}else{
	    		play.setLevelGame(CLevel);
	    	};
	    	
	    	String CAlgo = algo.getSelectedItem().toString();
	    	if (CAlgo == null) {
	    		play.setAlgoGame("RANDOM");
	    	}else{
	    		play.setAlgoGame(CAlgo);
	    	};
	    				
	    	String Cmade = mode.getSelectedItem().toString();
	    	if (Cmade == null) {
	    		play.setGameMade("PL VS AI");
	    	}else{
	    		play.setGameMade(Cmade);
	    	};
			
			setVisible(false);
		}      
	    });

	    JButton cancelBouton = new JButton("BACK");
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

	public Play getPlay() {
		return play;
	}

	public void setPlay(Play play) {
		this.play = play;
	}  
}