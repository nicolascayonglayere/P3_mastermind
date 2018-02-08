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

import clavier.Clavier;
import pattern_observer.Observateur;

/**
 * Table de jeu pour 2 joueurs, hérite de la classe abstraite TableDeJeu
 * @author nicolas
 *
 */
public class TableDeJeu_4 extends TableDeJeu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cpteurLettre = 0;
		
	/**
	 * Constructeur sans parametres
	 */
	public TableDeJeu_4() {
		super();
	}
	
	/**
	 * Constructeur avec parametres
	 * @param pJeu
	 * @param pMode
	 * @param pEssai
	 * @param pCombo
	 * @param pDev
	 * @param pCouleur
	 */
	public TableDeJeu_4(String pJeu, String pMode, int pEssai, int pCombo, int pDev, int pCouleur) {
		super(pJeu, pMode, pEssai, pCombo, pDev, pCouleur);

	}
	
	/**
	 * Methode initialisant les composants graphiques de la table
	 */
	public void initTable() {
		
		//--Le panneau qui accueille la table de jeu
		
		panTbleJeu.setPreferredSize(new Dimension (400, 500));
		panTbleJeu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panTbleJeu.setLayout(new BoxLayout(panTbleJeu, BoxLayout.PAGE_AXIS));
		panTbleJeu.setBackground(Color.WHITE);
		
		Font police = new Font("Arial", Font.BOLD, 18);
		
		JPanel[] panRef = new JPanel[this.nbCoupsConfig];//--Une liste de JPanel 
		
		JPanel[] panLblPropJH = new JPanel[this.nbCoupsConfig];//--Une liste de JPanel qui accueille les etiquettes affichant la prop du joueur humain
		this.listLblPropJH = new JLabel[this.nbCoupsConfig][this.lgueurCombo];
		
		JPanel[] panLblPropJE = new JPanel[this.nbCoupsConfig];
		this.listLblPropJE = new JLabel[this.nbCoupsConfig][this.lgueurCombo];
		
		this.listResultJH = new JLabel[this.nbCoupsConfig];
		this.listResultJE = new JLabel[this.nbCoupsConfig];
		

		for(int i = 0; i<nbCoupsConfig; i++) {
			//--Un panel primitif qui accueille le tour de chaque joueur
			JPanel panJoueurHumain = new JPanel();
			panJoueurHumain.setLayout(new BoxLayout(panJoueurHumain, BoxLayout.LINE_AXIS));
			panJoueurHumain.setBackground(Color.WHITE);
			JPanel panJoueurElectronique = new JPanel();
			panJoueurElectronique.setLayout(new BoxLayout(panJoueurElectronique, BoxLayout.LINE_AXIS));
			panJoueurElectronique.setBackground(Color.WHITE);
							
			JLabel etqTourJH = new JLabel("Tour de : "+joueur.getNom());
			etqTourJH.setFont(police);
			etqTourJH.setBackground(Color.WHITE);
			panJoueurHumain.add(etqTourJH);
			
			JLabel etqTourJE = new JLabel("Tour de : "+joueur1.getNom());
			etqTourJE.setFont(police);
			etqTourJE.setBackground(Color.WHITE);
			panJoueurElectronique.add(etqTourJE);
			
			//--Le panneau qui accueille les etiquettes affichant la prop du joueur humain
			panLblPropJH[i] = new JPanel();
			panLblPropJH[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			panLblPropJH[i].setBackground(Color.WHITE);
			panLblPropJH[i].setPreferredSize(new Dimension(300,50));
			GridLayout gl = new GridLayout(1,lgueurCombo);
			gl.setHgap(7);
			panLblPropJH[i].setLayout(gl);
			
			for (int j = 0 ; j <this.lgueurCombo ; j++) {
				//--L'etiquette affichant la prop
				listLblPropJH[i][j] = new JLabel("");
				listLblPropJH[i][j].setFont(police);
				listLblPropJH[i][j].setOpaque(true);
				listLblPropJH[i][j].setBackground(Color.LIGHT_GRAY);
				listLblPropJH[i][j].setHorizontalTextPosition(JLabel.CENTER);
				listLblPropJH[i][j].setHorizontalAlignment(JLabel.CENTER);

				panLblPropJH[i].add(listLblPropJH[i][j]);
			}
			panJoueurHumain.add(panLblPropJH[i]);
			
			//--Le panneau qui accueille les etiquettes affichant la prop de la machine
			panLblPropJE[i] = new JPanel();
			panLblPropJE[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			panLblPropJE[i].setBackground(Color.WHITE);
			panLblPropJE[i].setPreferredSize(new Dimension(300,50));
			GridLayout gl1 = new GridLayout(1,lgueurCombo);
			gl1.setHgap(7);
			panLblPropJE[i].setLayout(gl1);
			
			for (int j = 0 ; j <this.lgueurCombo ; j++) {
				//--L'etiquette affichant la prop
				listLblPropJE[i][j] = new JLabel("");
				listLblPropJE[i][j].setFont(police);
				listLblPropJE[i][j].setOpaque(true);
				listLblPropJE[i][j].setBackground(Color.LIGHT_GRAY);
				listLblPropJE[i][j].setHorizontalTextPosition(JLabel.CENTER);
				listLblPropJE[i][j].setHorizontalAlignment(JLabel.CENTER);

				panLblPropJE[i].add(listLblPropJE[i][j]);
			}
			panJoueurElectronique.add(panLblPropJE[i]);
			
			listResultJH[i] = new JLabel("");
			listResultJH[i].setFont(police);
			listResultJH[i].setBackground(Color.WHITE);
			listResultJH[i].setPreferredSize(new Dimension(300,50));
			panJoueurHumain.add(listResultJH[i]);
			
			listResultJE[i] = new JLabel("");
			listResultJE[i].setFont(police);
			listResultJE[i].setBackground(Color.WHITE);
			listResultJE[i].setPreferredSize(new Dimension(300,50));
			panJoueurElectronique.add(listResultJE[i]);
		
			panRef[i] = new JPanel();
			panRef[i].setLayout(new BoxLayout(panRef[i], BoxLayout.PAGE_AXIS));
			panRef[i].setBorder(BorderFactory.createEtchedBorder());
			panRef[i].setBackground(Color.WHITE);
			panRef[i].add(panJoueurHumain);
			panRef[i].add(panJoueurElectronique);
			
			panTbleJeu.add(panRef[i]);
			
		}
		//--Un clavier de chiffre/couleur et 2 boutons tour suivant
		Clavier clavier = new Clavier(couleur);
		JButton finDeTourJH = new JButton("TOUR SUIVANT");
		JButton finDeTourJE = new JButton("TOUR SUIVANT");
		clavier.addObservateur(new Observateur() {
			
			@Override
			public void update(Object o) {

				if (couleur == 0) {
					logger.debug("le tour du joueur : "+joueur.getTourDeJeu());
					logger.debug("la lettre transmise : "+o.toString()+" - "+Integer.valueOf(o.toString()));
					
					listLblPropJH[joueur.getTourDeJeu()][cpteurLettre].setText(o.toString());
					coupJoue += o.toString();
					cpteurLettre ++;	
					if(cpteurLettre == lgueurCombo)
						finDeTourJH.setEnabled(true);
				}
				else if (couleur == 1) {
					//--on met ds la JLabel le fichier couleur du bouton et le chiffre/lettre du bouton
					Color[]listColor = {Color.WHITE, Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PINK};
					
					logger.debug("le tour du joueur : "+joueur.getTourDeJeu());
					logger.debug("la lettre transmise : "+o.toString()+" - "+Integer.valueOf(o.toString()));						

					listLblPropJH[joueur.getTourDeJeu()][cpteurLettre].setBackground(listColor[Integer.valueOf(o.toString())]);
					listLblPropJH[joueur.getTourDeJeu()][cpteurLettre].setForeground(listColor[Integer.valueOf(o.toString())]);
					listLblPropJH[joueur.getTourDeJeu()][cpteurLettre].setText(o.toString());
					coupJoue += o.toString();						
					cpteurLettre ++;
					if(cpteurLettre == lgueurCombo)
						finDeTourJH.setEnabled(true);
				}
			}			
		});
		//--Un bouton fin de tour pour le joueur humain
		finDeTourJH.setBackground(Color.LIGHT_GRAY);
		finDeTourJH.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		finDeTourJH.setPreferredSize(new Dimension (100, 100));
		finDeTourJH.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				logger.debug("Bton coupjoue : "+coupJoue);
				joueur.jeu(coupJoue);
				cpteurLettre = 0;
				coupJoue = "";
				finDeTourJE.setVisible(true);
				finDeTourJH.setVisible(false);
			}
			
		});
		
		//--Un bouton fin de tour pour le joueur electronique (invisible pour commencer)
		finDeTourJE.setBackground(Color.LIGHT_GRAY);
		finDeTourJE.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		finDeTourJE.setPreferredSize(new Dimension (100, 100));
		finDeTourJE.setVisible(false);
		finDeTourJE.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				joueur1.jeu("");
				cpteurLettre = 0;
				finDeTourJH.setVisible(true);
				finDeTourJE.setVisible(false);
			}
			
		});
		JPanel panKey = new JPanel();
		panKey.setBackground(Color.WHITE);
		panKey.add(clavier);
		panKey.add(finDeTourJH);
		panKey.add(finDeTourJE);
		panTbleJeu.add(panKey);
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
	
		this.joueur.addObservateur(new Observateur() {
			public void update(Object o) {
				logger.debug("on update l'observateur");
				ArrayList<String> paramObs = (ArrayList<String>)o;
				tourDeJeu = Integer.valueOf(paramObs.get(0));
				logger.debug("Ctrl TourDeJeu : "+tourDeJeu +" - compa : "+paramObs.get(1)+" - victoire : "+paramObs.get(2));
				
				resultCompa = paramObs.get(1);
				listResultJH[tourDeJeu].setText(resultCompa);
				
				//--Si le joueur n'a pas gagné et qu'il reste des tours à jouer on active la zone de saisie suivante
				if(Boolean.parseBoolean(paramObs.get(2)) == false && tourDeJeu+1 < nbCoupsConfig) {
					coupJoue = "";
				}
				//--si c'est la victoire
				else if (Boolean.parseBoolean(paramObs.get(2)) == true) {
					int option = JOptionPane.showConfirmDialog(null, "Félicitation, "+joueur.getNom()+" a trouvé la combinaison secrète ! \n "
							+ "La combinaison de votre adversaire était : "+joueur1.getCombiSecret()+" \n Voulez-vous rejouer ?",
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

		this.joueur1.addObservateur(new Observateur() {
			public void update(Object o) {
				ArrayList<String> paramObs = (ArrayList<String>)o;
				tourDeJeu = Integer.valueOf(paramObs.get(0));
				logger.debug("Ctrl TourDeJeu : "+tourDeJeu +" - prop : "+paramObs.get(1)+" - compa : "+paramObs.get(2)+" - victoire : "+paramObs.get(3));
				
				if(couleur == 0) {
					for (int k = 0; k<paramObs.get(1).toCharArray().length; k++) {
						listLblPropJE[tourDeJeu][k].setText(String.valueOf(paramObs.get(1).charAt(k)));
					}
				}
				else if (couleur == 1) {
					//--on met ds la JLabel la couleur du bouton et le chiffre/lettre du bouton
					Color[]listColor = {Color.WHITE, Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PINK};
					for (int k = 0; k<paramObs.get(1).toCharArray().length; k++) {                                                	
						listLblPropJE[tourDeJeu][k].setBackground(listColor[Character.getNumericValue(paramObs.get(1).charAt(k))]); 
						listLblPropJE[tourDeJeu][k].setForeground(listColor[Character.getNumericValue(paramObs.get(1).charAt(k))]); 
						listLblPropJE[tourDeJeu][k].setText(String.valueOf(paramObs.get(1).charAt(k)));                             
						}
				}
				
				logger.debug("tjs la victoire : "+Boolean.parseBoolean(paramObs.get(3)));
				//--Si le joueur n'a pas gagné et qu'il reste des tours à jouer on active la zone de saisie suivante
				if(Boolean.parseBoolean(paramObs.get(3)) == false && tourDeJeu+1 != nbCoupsConfig) {
					listResultJE[tourDeJeu].setText(paramObs.get(2));
				}
				//--si c'est la victoire
				else if (Boolean.parseBoolean(paramObs.get(3)) != false) {
					listResultJE[tourDeJeu].setText(paramObs.get(2));
					int option = JOptionPane.showConfirmDialog(null,"Félicitation, "+joueur1.getNom()+" a trouvé la combinaison secrète ! \n "
							+ "La combinaison de votre adversaire était : "+joueur.getCombiSecret()+" \n Voulez-vous rejouer ?",
									"Victoire", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (option == JOptionPane.OK_OPTION) {
						nouvellePartie();
					}
				}
				//--Si c'est la défaite
				else if(Boolean.parseBoolean(paramObs.get(3)) == false && tourDeJeu+1 == nbCoupsConfig) {
					listResultJE[tourDeJeu].setText(paramObs.get(2));
					int option = JOptionPane.showConfirmDialog(null, "C'est perdu ! \n La combinaison gagnante est "+joueur1.getCombiSecret()+"\n Voulez-vous rejouer ?",
									"Défaite", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (option == JOptionPane.OK_OPTION) {
						nouvellePartie();
					}
				}
			}
		});
		
		this.setLayout(new BorderLayout());
		this.add(panTbleJeu, BorderLayout.CENTER);

		//--on initialise les joueurs
		this.joueur.initCombiSecret();
		modeDevLbl.setText("Combinaison secrete : "+String.valueOf(this.joueur.getCombiSecret()));
		this.joueur1.initCombiSecret();
		this.joueur.setTourDeJeu(0);
		this.joueur1.setTourDeJeu(0);
		this.joueur.setVictoire(false);
		this.joueur1.setVictoire(false);
	}
}
