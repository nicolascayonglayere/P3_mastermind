package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Propriete.ModeJeu;
import clavier.Clavier;
import pattern_observer.Observateur;

/**
 * Table de jeu pour 1 joueur, hérite de la classe abstraite TableDeJeu
 * @author nicolas
 *
 */
public class TableDeJeu_3 extends TableDeJeu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cpteurLettre = 0;
		
	/**
	 * Constructeur sans parametre
	 */
	public TableDeJeu_3() {
		super();
	}
	
	/**
	 * Constructeur avec parametre
	 * @param pJeu
	 * @param pMode
	 * @param pEssai
	 * @param pCombo
	 * @param pDev
	 * @param pCouleur
	 */
	public TableDeJeu_3(String pJeu, String pMode, int pEssai, int pCombo, int pDev, int pCouleur) {
		super(pJeu, pMode, pEssai, pCombo, pDev, pCouleur);
	}
	
	/**
	 * Methode initialisant les composants graphiques de la table
	 */
	public void initTable() {
		
		//--Le panneau qui accueille la table de jeu
		panTbleJeu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panTbleJeu.setLayout(new BoxLayout(panTbleJeu, BoxLayout.PAGE_AXIS));
		panTbleJeu.setBackground(Color.WHITE);
		
		Font police = new Font("Arial", Font.BOLD, 18);
		
		JLabel[] listEssai = new JLabel[this.nbCoupsConfig];//--une liste d'étiquette qui accueille le n° de la tentative
		JPanel[] panListEssai = new JPanel[this.nbCoupsConfig];
		
		JPanel[] panRef = new JPanel[this.nbCoupsConfig];
		
		JPanel[] panLblProp = new JPanel[this.nbCoupsConfig];//--Une liste de JPanel qui accueille les etiquettes affichant la prop
		this.listLblProp = new JLabel[this.nbCoupsConfig][this.lgueurCombo];
		
		JPanel[] panResult = new JPanel[this.nbCoupsConfig];
		this.listResult = new JLabel[this.nbCoupsConfig];//--La liste de JLabel qui affiche le resultat de la comparaison
		
		for(int i = 0; i<nbCoupsConfig; i++) {
			//--L'etiquette essai n° et son panneau d'accueil
			listEssai[i] = new JLabel(String.valueOf(i+1));
			listEssai[i].setFont(police);
			listEssai[i].setBackground(Color.white);
			listEssai[i].setHorizontalAlignment(JLabel.CENTER);

			panListEssai[i] = new JPanel();
			panListEssai[i].setLayout(new BorderLayout());
			panListEssai[i].setBackground(Color.WHITE);
			panListEssai[i].add(listEssai[i], BorderLayout.CENTER);
			
			
			//--Le panneau qui accueille les etiquettes affichant la prop
			panLblProp[i] = new JPanel();
			panLblProp[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			panLblProp[i].setBackground(Color.WHITE);
			panLblProp[i].setPreferredSize(new Dimension(300,50));
			GridLayout gl = new GridLayout(1,lgueurCombo);
			gl.setHgap(7);
			panLblProp[i].setLayout(gl);
			
			for (int j = 0 ; j <this.lgueurCombo ; j++) {
				//--L'etiquette affichant la prop
				listLblProp[i][j] = new JLabel("");
				listLblProp[i][j].setFont(police);
				listLblProp[i][j].setOpaque(true);
				listLblProp[i][j].setBackground(Color.LIGHT_GRAY);
				listLblProp[i][j].setHorizontalTextPosition(JLabel.CENTER);
				listLblProp[i][j].setHorizontalAlignment(JLabel.CENTER);

				panLblProp[i].add(listLblProp[i][j]);
			}
			
			//--L'étiquette qui affiche le résultat de la comparaison entre la saisie et la combinaison gagnante
			listResult[i] = new JLabel("");
			listResult[i].setFont(police);
			listResult[i].setBackground(Color.white);
			listResult[i].setPreferredSize(new Dimension (300, 50));
			listResult[i].setHorizontalTextPosition(JLabel.CENTER);
			listResult[i].setHorizontalAlignment(JLabel.CENTER);

			panResult[i] = new JPanel();
			panResult[i].setBackground(Color.WHITE);
			panResult[i].add(listResult[i]);
			
			//--Le panneau qui accueille les 3 composants précédents
			panRef[i] = new JPanel();
			panRef[i].setBorder(BorderFactory.createEtchedBorder());
			panRef[i].setBackground(Color.WHITE);
			panRef[i].setLayout(new BoxLayout(panRef[i], BoxLayout.LINE_AXIS));
			panRef[i].add(panListEssai[i]);
			panRef[i].add(panLblProp[i]);
			panRef[i].add(panResult[i]);
			
			panTbleJeu.add(panRef[i]);
		}
		//--Si le mode de jeu est defenseur, on ajoute un bouton
		if (this.modeJeu.equals(ModeJeu.DEFENSEUR.toString())){
			
			//--Un bouton fin de tour
			JButton finDeTour = new JButton("TOUR SUIVANT");
			finDeTour.setBackground(Color.LIGHT_GRAY);
			finDeTour.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			finDeTour.setPreferredSize(new Dimension (300, 100));
			finDeTour.setAlignmentX(CENTER_ALIGNMENT);

			finDeTour.addActionListener(new ActionListener() {
					@Override
				public void actionPerformed(ActionEvent arg0) {
						joueur1.jeu("");
						logger.debug("TRACES Bton");
				}
			});
			
			panTbleJeu.add(finDeTour);
		}
		//--Si le mode de jeu est challenger, on ajoute un clavier de chiffre ou de couleur et un bouton
		else if (this.modeJeu.equals(ModeJeu.CHALLENGER.toString())) {
			//--Un clavier de chiffre/couleur
			Clavier clavier = new Clavier(couleur);
			
			//--Un bouton fin de tour
			JButton finDeTour = new JButton("TOUR SUIVANT");
			
			clavier.addObservateur(new Observateur() {
				
				@Override
				public void update(Object o) {
					//--clavier numerique
					if (couleur == 0) {
						logger.debug("le tour du joueur : "+joueur.getTourDeJeu());
						logger.debug("la lettre transmise : "+o.toString()+" - "+Integer.valueOf(o.toString()));
						
						listLblProp[joueur.getTourDeJeu()][cpteurLettre].setText(o.toString());
						coupJoue += o.toString();
						cpteurLettre ++;
						if (cpteurLettre == lgueurCombo)
							finDeTour.setEnabled(true);
		
					}
					//--Clavier de couleur
					else if (couleur == 1) {
						//--on met ds la JLabel le fichier couleur du bouton et le chiffre/lettre du bouton
						Color[]listColor = {Color.WHITE, Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PINK};
						
						logger.debug("le tour du joueur : "+joueur.getTourDeJeu());
						logger.debug("la lettre transmise : "+o.toString()+" - "+Integer.valueOf(o.toString()));						
						
						listLblProp[joueur.getTourDeJeu()][cpteurLettre].setBackground(listColor[Integer.valueOf(o.toString())]);
						listLblProp[joueur.getTourDeJeu()][cpteurLettre].setForeground(listColor[Integer.valueOf(o.toString())]);
						listLblProp[joueur.getTourDeJeu()][cpteurLettre].setText(o.toString());
						coupJoue += o.toString();						
						cpteurLettre ++;
						if(cpteurLettre == lgueurCombo)
							finDeTour.setEnabled(true);
					}
				}			
			});
			//--Un bouton fin de tour
			finDeTour.setBackground(Color.LIGHT_GRAY);
			finDeTour.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			finDeTour.setPreferredSize(new Dimension (300, 100));
			finDeTour.setEnabled(false);
			finDeTour.addActionListener(new ActionListener() {
					@Override
				public void actionPerformed(ActionEvent arg0) {
					joueur.jeu(coupJoue);
					cpteurLettre = 0;
					finDeTour.setEnabled(false);
					coupJoue = "";
				}
			});
		
			JPanel panKey = new JPanel();
			panKey.setBackground(Color.WHITE);
			panKey.add(clavier);
			panKey.add(finDeTour);
			panTbleJeu.add(panKey);
		}
	}
	
	/**
	 * Methode initialisant une nouvelle partie
	 */
	public void nouvellePartie() {
		panTbleJeu.removeAll();
		resultCompa ="";

		initTable();
		
		logger.debug("Ctrl modeDev : "+modeDev);
		if (modeDev == 1) {
			modeDevLbl = new JLabel("Combinaison secrète : ");
			Font police = new Font("Arial", Font.BOLD, 14);
			modeDevLbl.setFont(police);
			modeDevLbl.setPreferredSize(new Dimension(800,100));
			modeDevLbl.setBackground(Color.LIGHT_GRAY);
			modeDevLbl.setHorizontalTextPosition(JLabel.CENTER);
			modeDevLbl.setVisible(true);
			panTbleJeu.add(modeDevLbl);
		}
		else {
			modeDevLbl = new JLabel("");
			modeDevLbl.setVisible(false);
		}
		panTbleJeu.revalidate();
		panTbleJeu.repaint();
		
		if (modeJeu.equals(ModeJeu.CHALLENGER.toString())) {
			this.joueur.addObservateur(new Observateur() {
				public void update(Object o) {
					logger.debug("on update l'observateur");
					ArrayList<String> paramObs = (ArrayList<String>)o;
					tourDeJeu = Integer.valueOf(paramObs.get(0));
					logger.debug("Ctrl TourDeJeu : "+tourDeJeu +" - compa : "+paramObs.get(1)+" - victoire : "+paramObs.get(2));
					
					for(int i = 0; i<lgueurCombo; i++) {
						coupJoue += listLblProp[tourDeJeu][i].getText();
					}

					resultCompa = paramObs.get(1);
					listResult[tourDeJeu].setText(resultCompa);
					
					//--Si le joueur n'a pas gagné et qu'il reste des tours à jouer on active la zone de saisie suivante
					if(Boolean.parseBoolean(paramObs.get(2)) == false && tourDeJeu+1 < nbCoupsConfig) {
						coupJoue = "";
					}
					//--si c'est la victoire
					else if (Boolean.parseBoolean(paramObs.get(2)) == true) {
						int option = JOptionPane.showConfirmDialog(null, "Félicitation, vous avez trouvé la combinaison secrète ! \n Voulez-vous rejouer ?",
										"Victoire", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
					//--Si c'est la défaite
					else if(Boolean.parseBoolean(paramObs.get(2)) == false && tourDeJeu+1 == nbCoupsConfig) {
						int option = JOptionPane.showConfirmDialog(null, "C'est perdu ! \n La combinaison gagnante est "+joueur.getCombiSecret()+"\n Voulez-vous rejouer ?",
										"Défaite", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
				}				
			});
		}
		else if (modeJeu.equals(ModeJeu.DEFENSEUR.toString())) {
			this.joueur1.addObservateur(new Observateur() {
				public void update(Object o) {
					logger.debug("on update l'observateur");
					ArrayList<String> paramObs = (ArrayList<String>)o;
					tourDeJeu = Integer.valueOf(paramObs.get(0));
					logger.debug("Ctrl TourDeJeu : "+tourDeJeu +" - prop : "+paramObs.get(1)+" - compa : "+paramObs.get(2)+" - victoire : "+paramObs.get(3));
					
					if(couleur == 0) {
						for (int k = 0; k<paramObs.get(1).toCharArray().length; k++) {
							listLblProp[tourDeJeu][k].setText(String.valueOf(paramObs.get(1).charAt(k)));
						}
					}
					else if (couleur == 1) {
						//--on met ds la JLabel la couleur du bouton et le chiffre/lettre du bouton
						Color[]listColor = {Color.WHITE, Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PINK};
						for (int k = 0; k<paramObs.get(1).toCharArray().length; k++) {                                                	
							listLblProp[tourDeJeu][k].setBackground(listColor[Character.getNumericValue(paramObs.get(1).charAt(k))]); 	
							listLblProp[tourDeJeu][k].setForeground(listColor[Character.getNumericValue(paramObs.get(1).charAt(k))]); 	
							listLblProp[tourDeJeu][k].setText(String.valueOf(paramObs.get(1).charAt(k)));                             	
							}
					}
					
					logger.debug("tjs la victoire : "+Boolean.parseBoolean(paramObs.get(3)));
					//--Si le joueur n'a pas gagné et qu'il reste des tours à jouer on active la zone de saisie suivante
					if(Boolean.parseBoolean(paramObs.get(3)) == false && tourDeJeu+1 != nbCoupsConfig) {
						listResult[tourDeJeu].setText(paramObs.get(2));
					}
					//--si c'est la victoire
					else if (Boolean.parseBoolean(paramObs.get(3)) != false) {
						listResult[tourDeJeu].setText(paramObs.get(2));
						int option = JOptionPane.showConfirmDialog(null, "Félicitation, vous avez trouvé la combinaison secrète ! \n Voulez-vous rejouer ?",
										"Victoire", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
					//--Si c'est la défaite
					else if(Boolean.parseBoolean(paramObs.get(3)) == false && tourDeJeu+1 == nbCoupsConfig) {
						listResult[tourDeJeu].setText(paramObs.get(2));
						int option = JOptionPane.showConfirmDialog(null, "C'est perdu ! \n La combinaison gagnante est "+joueur.getCombiSecret()+"\n Voulez-vous rejouer ?",
										"Défaite", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
				}				
			});
		}
		
			
		this.setLayout(new BorderLayout());
		this.add(panTbleJeu, BorderLayout.CENTER);
		
		//--on initialise le joueur selon le mode de jeu
		if(modeJeu.equals(ModeJeu.CHALLENGER.toString())) {
			this.joueur.initCombiSecret();
			modeDevLbl.setText("Combinaison secrete : "+String.valueOf(this.joueur.getCombiSecret()));
			this.joueur.setTourDeJeu(0);
			this.joueur.setVictoire(false);
		}
		else if (modeJeu.equals(ModeJeu.DEFENSEUR.toString())) {
			this.joueur1.initCombiSecret();
			modeDevLbl.setText("Combinaison secrete : "+String.valueOf(this.joueur1.getCombiSecret()));
			this.joueur1.setTourDeJeu(0);
			this.joueur1.setVictoire(false);
		}
		
	}

}
