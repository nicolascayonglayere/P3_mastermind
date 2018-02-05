package test_unitaire_pattern_obs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clavier.Clavier;
import pattern_observer.Observateur;

public class TableDeJeu_Pattern_Obs_4 extends TableDeJeu_Pattern_Obs {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cpteurLettre = 0;
	
	public TableDeJeu_Pattern_Obs_4() {
		super();
	}
	
	public TableDeJeu_Pattern_Obs_4(String pJeu, String pMode, int pEssai, int pCombo, int pCouleur) {
		super(pJeu, pMode, pEssai, pCombo, pCouleur);
	}
	
	public void initTable() {
		
		//--Le panneau qui accueille la table de jeu
		
		//panTbleJeu.setPreferredSize(new Dimension (400, 500));
		panTbleJeu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panTbleJeu.setLayout(new BoxLayout(panTbleJeu, BoxLayout.PAGE_AXIS));
		panTbleJeu.setBackground(Color.WHITE);
		
		Font police = new Font("Arial", Font.BOLD, 18);
		
		//JLabel[] listEssai = new JLabel[this.nbCoupsConfig];//--une liste d'�tiquette qui accueille le n� de la tentative 
		//JPanel[] panListEssai = new JPanel[this.nbCoupsConfig];
		
		JPanel[] panRef = new JPanel[this.nbCoupsConfig];//--Une liste de JPanel 
		
		JPanel[] panLblPropJH = new JPanel[this.nbCoupsConfig];//--Une liste de JPanel qui accueille les etiquettes affichant la prop du joueur humain
		this.listLblPropJH = new JLabel[this.nbCoupsConfig][this.lgueurCombo];
		
		JPanel[] panLblPropJE = new JPanel[this.nbCoupsConfig];
		this.listLblPropJE = new JLabel[this.nbCoupsConfig][this.lgueurCombo];
		
		//JPanel[] panResult = new JPanel[this.nbCoupsConfig];
		//this.listResult = new JLabel[this.nbCoupsConfig];//--La liste de JLabel qui affiche le resultat de la comparaison
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
			//panLblProp[i].setLayout(new BoxLayout(panLblProp[i], BoxLayout.LINE_AXIS));
			GridLayout gl = new GridLayout(1,lgueurCombo);
			gl.setHgap(7);
			panLblPropJH[i].setLayout(gl);
			
			for (int j = 0 ; j <this.lgueurCombo ; j++) {
				//--L'etiquette affichant la prop
				listLblPropJH[i][j] = new JLabel("");
				listLblPropJH[i][j].setFont(police);
				//listLblProp[i][j].setBackground(Color.WHITE);
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
			//panLblProp[i].setLayout(new BoxLayout(panLblProp[i], BoxLayout.LINE_AXIS));
			GridLayout gl1 = new GridLayout(1,lgueurCombo);
			gl1.setHgap(7);
			panLblPropJE[i].setLayout(gl1);
			
			for (int j = 0 ; j <this.lgueurCombo ; j++) {
				//--L'etiquette affichant la prop
				listLblPropJE[i][j] = new JLabel("");
				listLblPropJE[i][j].setFont(police);
				//listLblProp[i][j].setBackground(Color.WHITE);
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
		//--Un clavier de chiffre/couleur et un bouton tour suivant
		Clavier clavier = new Clavier(couleur);
		clavier.addObservateur(new Observateur() {
			
			@Override
			public void update(Object o) {

				if (couleur == 0) {
					logger.debug("le tour du joueur : "+joueur.getTourDeJeu());
					logger.debug("la lettre transmise : "+o.toString()+" - "+Integer.valueOf(o.toString()));
					listLblPropJH[joueur.getTourDeJeu()][cpteurLettre].setText(o.toString());
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
					logger.debug("la lettre transmise : "+o.toString()+" - "+Integer.valueOf(o.toString()));						
					listLblPropJH[joueur.getTourDeJeu()][cpteurLettre].setIcon(listImageColor[Integer.valueOf(o.toString())]);
					listLblPropJH[joueur.getTourDeJeu()][cpteurLettre].setBackground(listColor[Integer.valueOf(o.toString())]);//pquoi cette ligne marche pas
					listLblPropJH[joueur.getTourDeJeu()][cpteurLettre].setForeground(listColor[Integer.valueOf(o.toString())]);
					listLblPropJH[joueur.getTourDeJeu()][cpteurLettre].setText(o.toString());
											
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
				joueur1.updateObservateur();
				cpteurLettre = 0;
			}
			
		});
		clavier.add(finDeTour);
		panTbleJeu.add(clavier);
	}
	
	public void nouvellePartie() {
		panTbleJeu.removeAll();
		resultCompa ="";
	
	
			initTable();
	
		panTbleJeu.revalidate();
		panTbleJeu.repaint();

		this.setLayout(new BorderLayout());
		this.add(panTbleJeu, BorderLayout.CENTER);
		this.joueur.initCombiSecret();
		this.joueur1.initCombiSecret();
		//this.constrCombiSecret = this.joueur.getConstrCombiSecret();
		//this.combiSecret = this.joueur.getCombiSecret();
		this.joueur.setTourDeJeu(0);
		this.joueur1.setTourDeJeu(0);
		this.joueur.setVictoire(false);
		this.joueur1.setVictoire(false);
	}


}
