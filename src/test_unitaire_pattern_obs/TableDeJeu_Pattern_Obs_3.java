package test_unitaire_pattern_obs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Propriete.ModeJeu;
import clavier.Clavier;
import clavier.Observateur_Clavier;

public class TableDeJeu_Pattern_Obs_3 extends TableDeJeu_Pattern_Obs {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cpteurLettre = 0;
	//private HashMap<Integer, Color> listCouleur ;
	//private Color[]listColor = {Color.WHITE, Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PINK}; ;
	
	public TableDeJeu_Pattern_Obs_3() {
		super();
	}
	
	public TableDeJeu_Pattern_Obs_3(String pJeu, String pMode, int pEssai, int pCombo, int pCouleur) {
		super(pJeu, pMode, pEssai, pCombo, pCouleur);
	}
	
	public void initTable() {
		
		//--Le panneau qui accueille la table de jeu
		
		panTbleJeu.setPreferredSize(new Dimension (400, 500));
		panTbleJeu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panTbleJeu.setLayout(new BoxLayout(panTbleJeu, BoxLayout.PAGE_AXIS));
		panTbleJeu.setBackground(Color.WHITE);
		
		Font police = new Font("Arial", Font.BOLD, 18);
		
		JLabel[] listEssai = new JLabel[this.nbCoupsConfig];//--une liste d'étiquette qui accueille le n° de la tentative
		JPanel[] panListEssai = new JPanel[this.nbCoupsConfig];
		
		JPanel[] panRef = new JPanel[this.nbCoupsConfig];
		
		JPanel[] panLblProp = new JPanel[this.nbCoupsConfig];//--Une liste de JPanel qui accueille les etiquettes affichant la prop
		this.lblProp = new JLabel[this.lgueurCombo];//--Une liste de JLabel qui affiche la prop
		this.listPropLbl = new HashMap<Integer, JLabel[]>();
		this.listLblProp = new JLabel[this.nbCoupsConfig][this.lgueurCombo];
		
		JPanel[] panResult = new JPanel[this.nbCoupsConfig];
		this.listResult = new JLabel[this.nbCoupsConfig];//--La liste de JLabel qui affiche le resultat de la comparaison
		
		for(int i = 0; i<nbCoupsConfig; i++) {
			//--L'etiquette essai n° et son panneau d'accueil
			listEssai[i] = new JLabel(String.valueOf(i+1));
			listEssai[i].setFont(police);
			listEssai[i].setBackground(Color.white);
			panListEssai[i] = new JPanel();
			panListEssai[i].setBackground(Color.WHITE);
			panListEssai[i].add(listEssai[i]);
			
			
			//--Le panneau qui accueille les etiquettes affichant la prop
			panLblProp[i] = new JPanel();
			panLblProp[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			panLblProp[i].setBackground(Color.WHITE);
			//panLblProp[i].setPreferredSize(new Dimension(300,50));
			//panLblProp[i].setLayout(new BoxLayout(panLblProp[i], BoxLayout.LINE_AXIS));
			GridLayout gl = new GridLayout(1,lgueurCombo);
			gl.setHgap(7);
			panLblProp[i].setLayout(gl);
			
			for (int j = 0 ; j <this.lgueurCombo ; j++) {
				//--L'etiquette affichant la prop
				//lblProp[j] = new JLabel("");
				//lblProp[j].setFont(police);
				//lblProp[j].setBackground(Color.white);
				//lblProp[j].setPreferredSize(new Dimension(100,50));
				//lblProp[j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				listLblProp[i][j] = new JLabel("");
				listLblProp[i][j].setFont(police);
				//listLblProp[i][j].setBackground(Color.WHITE);
				listLblProp[i][j].setHorizontalTextPosition(JLabel.CENTER);
				//listLblProp[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				//listLblProp[j].setText(joueur.getPropOrdi());
				panLblProp[i].add(listLblProp[i][j]);
				//panLblProp[i].add(lblProp[j]);
				
				
			}
			//listPropLbl.put(i, lblProp);			
			
	//	listProp[i] = new JFormattedTextField(mask);
	//	listProp[i].setFont(police);
	//	listProp[i].setBackground(Color.white);
	//	listProp[i].setPreferredSize(new Dimension (100, 50));
	//	listProp[i].setEditable(false);
	//	listProp[i].setHorizontalAlignment(JFormattedTextField.CENTER);
	//	listProp[i].addActionListener(new ActionListener() {
	//		@Override
	//		public void actionPerformed(ActionEvent arg0) {
	//			joueur.updateObservateur();
	//		}
	//	});
			
			//--L'étiquette qui affiche le résultat de la comparaison entre la saisie et la combinaison gagnante
			listResult[i] = new JLabel("");
			listResult[i].setFont(police);
			listResult[i].setBackground(Color.white);
			listResult[i].setPreferredSize(new Dimension (300, 50));
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
				////for(int j = 0 ; j < nbCoupsConfig; j++) {
				//	for (int i = 0 ; i<lgueurCombo; i ++) {
				//		//lblProp = listPropLbl.get(j);
				//		lblProp = listPropLbl.get(joueur.getTourDeJeu());
				//		logger.debug("Ctrl taille list Lbl : "+lblProp.length);
				//		lblProp[i].setText(pLettre);
				//	//}
				//}
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
					//
					//listCouleur = new HashMap<Integer, Color>();
					//for(int i = 0; i<listColor.length; i++)
					//	listCouleur.put(i, listColor[i]);
						logger.debug("le tour du joueur : "+joueur.getTourDeJeu());
						logger.debug("la lettre transmise : "+pLettre+" - "+Integer.valueOf(pLettre));						
						listLblProp[joueur.getTourDeJeu()][cpteurLettre].setIcon(listImageColor[Integer.valueOf(pLettre)]);
						listLblProp[joueur.getTourDeJeu()][cpteurLettre].setBackground(listColor[Integer.valueOf(pLettre)]);//pquoi cette ligne marche pas
						listLblProp[joueur.getTourDeJeu()][cpteurLettre].setForeground(listColor[Integer.valueOf(pLettre)]);
						listLblProp[joueur.getTourDeJeu()][cpteurLettre].setText(pLettre);
					//for(int i = 0; i<clavier.getListCouleur().size(); i++) {
					//	if (i == clavier.getListCouleur().get(Integer.valueOf(pLettre)))
					//		
					//		listLblProp[joueur.getTourDeJeu()][cpteurLettre].setBackground(listCouleur.get(i));
					//}
							
						cpteurLettre ++;
					}
					
				//if(cpteurLettre<lgueurCombo) {
				//	cpteurLettre ++;
				//}
				//else if (cpteurLettre == lgueurCombo) {
				//	cpteurLettre = 0;
				//}
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
			//panTbleJeu.add(finDeTour);
		}
	}
	
	public void nouvellePartie() {
		panTbleJeu.removeAll();
		resultCompa ="";

		initTable();
		
		panTbleJeu.revalidate();
		panTbleJeu.repaint();
		//listProp[0].setEditable(true);
		this.setLayout(new BorderLayout());
		this.add(panTbleJeu, BorderLayout.CENTER);
		this.joueur.initCombiSecret();
		//this.constrCombiSecret = this.joueur.getConstrCombiSecret();
		//this.combiSecret = this.joueur.getCombiSecret();
		this.joueur.setTourDeJeu(0);
		this.joueur.setVictoire(false);
	}

}
