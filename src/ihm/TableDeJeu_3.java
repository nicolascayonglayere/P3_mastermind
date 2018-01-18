package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Propriete.ModeJeu;
import clavier.Clavier;
import clavier.Observateur_Clavier;

public class TableDeJeu_3 extends TableDeJeu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cpteurLettre = 0;
		
	public TableDeJeu_3() {
		super();
	}
	
	public TableDeJeu_3(String pJeu, String pMode, int pEssai, int pCombo, int pDev, int pCouleur) {
		super(pJeu, pMode, pEssai, pCombo, pDev, pCouleur);
	}
	
	public void initTable() {
		
		//--Le panneau qui accueille la table de jeu
		
		//panTbleJeu.setPreferredSize(new Dimension (400, 500));
		panTbleJeu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panTbleJeu.setLayout(new BoxLayout(panTbleJeu, BoxLayout.PAGE_AXIS));
		panTbleJeu.setBackground(Color.WHITE);
		
		Font police = new Font("Arial", Font.BOLD, 18);
		
		JLabel[] listEssai = new JLabel[this.nbCoupsConfig];//--une liste d'étiquette qui accueille le n° de la tentative
		JPanel[] panListEssai = new JPanel[this.nbCoupsConfig];
		
		JPanel[] panRef = new JPanel[this.nbCoupsConfig];
		
		JPanel[] panLblProp = new JPanel[this.nbCoupsConfig];//--Une liste de JPanel qui accueille les etiquettes affichant la prop
		//this.lblProp = new JLabel[this.lgueurCombo];//--Une liste de JLabel qui affiche la prop
		//this.listPropLbl = new HashMap<Integer, JLabel[]>();
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
			//panListEssai[i].setLayout(new BorderLayout());
			panListEssai[i].setBackground(Color.WHITE);
			panListEssai[i].add(listEssai[i]);
			
			
			//--Le panneau qui accueille les etiquettes affichant la prop
			panLblProp[i] = new JPanel();
			panLblProp[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			panLblProp[i].setBackground(Color.WHITE);
			panLblProp[i].setPreferredSize(new Dimension(300,50));
			//panLblProp[i].setLayout(new BoxLayout(panLblProp[i], BoxLayout.LINE_AXIS));
			GridLayout gl = new GridLayout(1,lgueurCombo);
			gl.setHgap(7);
			panLblProp[i].setLayout(gl);
			
			for (int j = 0 ; j <this.lgueurCombo ; j++) {
				//--L'etiquette affichant la prop
				listLblProp[i][j] = new JLabel("");
				listLblProp[i][j].setFont(police);
				//listLblProp[i][j].setBackground(Color.WHITE);
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
		if (this.modeJeu.equals(ModeJeu.DEFENSEUR.toString())){
			
			//--Un bouton fin de tour
			JButton finDeTour = new JButton("TOUR SUIVANT");
			finDeTour.setBackground(Color.LIGHT_GRAY);
			finDeTour.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			finDeTour.setPreferredSize(new Dimension (300, 100));
			finDeTour.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					joueur.updateObservateur();					
				}
				
			});
			panTbleJeu.add(finDeTour);
		}
		else if (this.modeJeu.equals(ModeJeu.CHALLENGER.toString())) {
			//--Un clavier de chiffre
			Clavier clavier = new Clavier(couleur);
			clavier.addObservateur(new Observateur_Clavier() {
				
				@Override
				public void update(String pLettre) {

					if (couleur == 0) {
						logger.debug("le tour du joueur : "+joueur.getTourDeJeu());
						logger.debug("la lettre transmise : "+pLettre+" - "+Integer.valueOf(pLettre));
						listLblProp[joueur.getTourDeJeu()][cpteurLettre].setText(pLettre);
						cpteurLettre ++;	
					}
					else if (couleur == 1) {
						//--on met ds la JLabel le fichier couleur du bouton et le chiffre/lettre du bouton
						Color[]listColor = {Color.WHITE, Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PINK};
						ImageIcon[] listImageColor = {new ImageIcon("Ressources/Images/blanc0.JPG"),
											       	new ImageIcon ("Ressources/Images/noir1.JPG"), 
											       	new ImageIcon("Ressources/Images/rouge2.JPG"),
											       	new ImageIcon("Ressources/Images/jaune3.JPG"), 
											       	new ImageIcon("Ressources/Images/vert4.JPG"),
											       	new ImageIcon("Ressources/Images/bleu5.JPG"),
											       	new ImageIcon("Ressources/Images/orange6.JPG"),
											       	new ImageIcon("Ressources/Images/rose8.JPG")};

						logger.debug("le tour du joueur : "+joueur.getTourDeJeu());
						logger.debug("la lettre transmise : "+pLettre+" - "+Integer.valueOf(pLettre));						
						listLblProp[joueur.getTourDeJeu()][cpteurLettre].setIcon(listImageColor[Integer.valueOf(pLettre)]);
						listLblProp[joueur.getTourDeJeu()][cpteurLettre].setBackground(listColor[Integer.valueOf(pLettre)]);//pquoi cette ligne marche pas
						listLblProp[joueur.getTourDeJeu()][cpteurLettre].setForeground(listColor[Integer.valueOf(pLettre)]);
						listLblProp[joueur.getTourDeJeu()][cpteurLettre].setText(pLettre);
												
						cpteurLettre ++;
					}
				}			
			});
			//--Un bouton fin de tour
			JButton finDeTour = new JButton("TOUR SUIVANT");
			finDeTour.setBackground(Color.LIGHT_GRAY);
			finDeTour.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			finDeTour.setPreferredSize(new Dimension (300, 100));
			finDeTour.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					joueur.updateObservateur();
					cpteurLettre = 0;
				}
				
			});
			clavier.add(finDeTour);
			panTbleJeu.add(clavier);
		}
	}
	
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
		this.setLayout(new BorderLayout());
		this.add(panTbleJeu, BorderLayout.CENTER);
		this.joueur.initCombiSecret();
		modeDevLbl.setText("Combinaison secrete : "+String.valueOf(this.joueur.getCombiSecret()));
		this.joueur.setTourDeJeu(0);
		this.joueur.setVictoire(false);
	}

}
