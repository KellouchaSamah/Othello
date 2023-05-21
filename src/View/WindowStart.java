package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

public class WindowStart extends JDialog {

	private JLabel pawnLabel, levelLabel, madeLabel, algoLabel;
	private JRadioButton black, white, easy, medium, hard, plVsAI, aiVsAI, plVsPL, random, minimax, alphabeta, sss, A;
	private Play play;

	public WindowStart(JFrame parent, String title, boolean modal, Play play)  {
		super(parent, title, modal);
		this.play = play;
		this.setSize(550, 250);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		//this.setBackground(Color.pink);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
	}

	private void initComponent() {

		JPanel panPawn = new JPanel(new GridLayout(4, 1));
		panPawn.setBackground(Color.white);
		panPawn.setPreferredSize(new Dimension(100, 50));
		panPawn.setBorder(BorderFactory.createTitledBorder("Play as"));
		black = new JRadioButton("BLACK");
		black.setBackground(Color.white);
		white = new JRadioButton("WHITE");
		white.setBackground(Color.white);

		panPawn.add(black);
		panPawn.add(white);

		JPanel panLevel = new JPanel(new GridLayout(4, 1));
		panLevel.setBackground(Color.white);
		panLevel.setPreferredSize(new Dimension(100, 50));
		panLevel.setBorder(BorderFactory.createTitledBorder("Game Difficulty"));
		easy = new JRadioButton("EASY");
		medium = new JRadioButton("MEDIUM");
		hard = new JRadioButton("HARD");
		easy.setBackground(Color.white);
		medium.setBackground(Color.white);
		hard.setBackground(Color.white);
		panLevel.add(easy);
		panLevel.add(medium);
		panLevel.add(hard);

		JPanel panMade = new JPanel(new GridLayout(4, 1));
		panMade.setBackground(Color.white);
		panMade.setPreferredSize(new Dimension(100, 50));
		panMade.setBorder(BorderFactory.createTitledBorder("Game Mode"));
		plVsAI = new JRadioButton("PL vs AI");
		aiVsAI = new JRadioButton("AI vs AI");
		plVsPL = new JRadioButton("PL vs PL");
		plVsAI.setBackground(Color.white);
		aiVsAI.setBackground(Color.white);
		plVsPL.setBackground(Color.white);
		panMade.add(plVsAI);
		panMade.add(aiVsAI);
		panMade.add(plVsPL);

		JPanel panAlgo = new JPanel(new GridLayout(6, 1));
		panAlgo.setBackground(Color.white);
		panAlgo.setPreferredSize(new Dimension(100, 50));
		panAlgo.setBorder(BorderFactory.createTitledBorder("Game Algorithm"));
		random = new JRadioButton("RANDOM");
		minimax = new JRadioButton("MINIMAX");
		alphabeta = new JRadioButton("ALPHABETA");
		sss = new JRadioButton("SSS*");
		A = new JRadioButton("A*");
		random.setBackground(Color.white);
		minimax.setBackground(Color.white);
		alphabeta.setBackground(Color.white);
		sss.setBackground(Color.white);
		A.setBackground(Color.white);
		panAlgo.add(random);
		panAlgo.add(minimax);
		panAlgo.add(alphabeta);
		panAlgo.add(sss);
		panAlgo.add(A);

		JPanel content = new JPanel(new GridLayout(1, 4));
		content.setBackground(Color.white);
		content.add(panAlgo);
		content.add(panLevel);
		content.add(panMade);
		content.add(panPawn);
		// OK button
		JButton okButton = new JButton("PLAY");
		//selectionner l'algorithme
		random.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (random.isSelected()) {
					alphabeta.setEnabled(false);
					sss.setEnabled(false);
					minimax.setEnabled(false);
					alphabeta.setEnabled(false);
					A.setEnabled(false);


				}
			}
		});
		alphabeta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (alphabeta.isSelected()) {
					random.setEnabled(false);
					sss.setEnabled(false);
					minimax.setEnabled(false);
					alphabeta.setEnabled(false);
					A.setEnabled(false);


				}
			}
		});
		minimax.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (minimax.isSelected()) {
					alphabeta.setEnabled(false);
					sss.setEnabled(false);
					random.setEnabled(false);
					alphabeta.setEnabled(false);
					A.setEnabled(false);


				}
			}
		});
		sss.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (sss.isSelected()) {
					alphabeta.setEnabled(false);
					random.setEnabled(false);
					minimax.setEnabled(false);
					alphabeta.setEnabled(false);
					A.setEnabled(false);


				}
			}
		});
		A.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (A.isSelected()) {
					alphabeta.setEnabled(false);
					random.setEnabled(false);
					minimax.setEnabled(false);
					alphabeta.setEnabled(false);
					sss.setEnabled(false);


				}
			}
		});
		//selectionner le niveau
		easy.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (easy.isSelected()) {
					hard.setEnabled(false);
					medium.setEnabled(false);
				}
			}
		});
		medium.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (medium.isSelected()) {
					easy.setEnabled(false);
					hard.setEnabled(false);
				}
			}
		});
		hard.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (hard.isSelected()) {
					easy.setEnabled(false);
					medium.setEnabled(false);
				}
			}
		});
		//selectionner le mode
		plVsPL.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (plVsPL.isSelected()) {
					plVsAI.setEnabled(false);
					aiVsAI.setEnabled(false);
				}
			}
		});
		plVsAI.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (plVsAI.isSelected()) {
					plVsPL.setEnabled(false);
					aiVsAI.setEnabled(false);
				}
			}
		});
		aiVsAI.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (aiVsAI.isSelected()) {
					plVsAI.setEnabled(false);
					plVsPL.setEnabled(false);
				}
			}
		});

		// selectionner le joueur :
		black.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (black.isSelected()) {
					white.setEnabled(false); // Disable the 'white' radio button
				} else {
					white.setEnabled(true); // Enable the 'white' radio button
				}
			}
		});
		white.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (white.isSelected()) {
					black.setEnabled(false);
				} else {
					black.setEnabled(true);
				}
			}
		});
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Board board = new Board();
				Pawn pawn = null;

				if (black.isSelected()) {
					white.setEnabled(false);
					play.setPlayerPawn(Pawn.BLACKState);

				} else {
					play.setPlayerPawn(Pawn.WHITEState);
				}


				if (easy.isSelected()) {
					play.setLevelGame("EASY");
				} else if (medium.isSelected()) {
					play.setLevelGame("MEDIUM");
				} else {
					play.setLevelGame("HARD");
				}

				if (plVsAI.isSelected()) {
					play.setGameMade("PL vs AI");
				} else if (aiVsAI.isSelected()) {
					play.setGameMade("AI vs AI");
				} else {
					play.setGameMade("PL vs PL");
				}

				if(random.isSelected()){
					play.setAlgoGame("RANDOM");
				} else if (alphabeta.isSelected()) {
					play.setAlgoGame("ALPHABETA");
				} else if (sss.isSelected()) {
					play.setAlgoGame("SSS*");
				} else if (minimax.isSelected()){
					play.setAlgoGame("MINIMAX");
				} else {
					play.setAlgoGame("RANDOM");
				}
				dispose();
			}
		});

		// Cancel button
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		JPanel control = new JPanel();
		control.setBackground(Color.white);
		control.add(okButton);
		control.add(cancelButton);


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


