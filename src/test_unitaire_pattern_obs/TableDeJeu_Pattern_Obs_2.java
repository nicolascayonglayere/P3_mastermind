package test_unitaire_pattern_obs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import joueur.JoueurElectronique;

public class TableDeJeu_Pattern_Obs_2 extends TableDeJeu_Pattern_Obs {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JFormattedTextField[] propJH, propJE;
	//private JoueurElectronique joueur1;
	
	public TableDeJeu_Pattern_Obs_2() {
		super();
	}
	
	public TableDeJeu_Pattern_Obs_2(String pJeu, String pMode, int pEssai, int pCombo, int pCouleur) {
		super(pJeu, pMode, pEssai, pCombo, pCouleur);
	}
	
	public void initTable() throws ParseException{
		//--Le panneau qui accueille la table de jeu
		
				panTbleJeu.setPreferredSize(new Dimension (400, 500));
				panTbleJeu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				panTbleJeu.setLayout(new BoxLayout(panTbleJeu, BoxLayout.PAGE_AXIS));
				panTbleJeu.setBackground(Color.WHITE);
				
				Font police = new Font("Arial", Font.BOLD, 18);
				JLabel[] listEssai = new JLabel[this.nbCoupsConfig];//--une liste d'étiquette qui accueille le n° de la tentative

				JPanel[] panRef = new JPanel[this.nbCoupsConfig];//--Une liste de JPanel 
				
				
				//--On applique un maskFormatter au JFormattedTextField pour s'assurer de la validité de la saisie
				String[] listDiese = new String[this.lgueurCombo];
				String str = "";
				for (int k = 0; k<this.lgueurCombo; k++) {
					listDiese[k] = "#";
					str += listDiese[k];
				}
				MaskFormatter mask = new MaskFormatter(str);

				this.listProp = new JFormattedTextField[this.nbCoupsConfig];
				this.listResultJH = new JLabel[this.nbCoupsConfig];
				this.listResultJE = new JLabel[this.nbCoupsConfig];
				this.propJH = new JFormattedTextField[this.nbCoupsConfig];
				this.propJE = new JFormattedTextField[this.nbCoupsConfig];
				
				for(int i = 0; i<nbCoupsConfig; i++) {
					//--Un panel primitif qui accueille le tour de chaque joueur
					JPanel panJoueurHumain = new JPanel();
					panJoueurHumain.setLayout(new BoxLayout(panJoueurHumain, BoxLayout.LINE_AXIS));
					JPanel panJoueurElectronique = new JPanel();
					panJoueurElectronique.setLayout(new BoxLayout(panJoueurElectronique, BoxLayout.LINE_AXIS));
									
					JLabel etqTourJH = new JLabel("Tour de : "+joueur.getNom());
					etqTourJH.setFont(police);
					etqTourJH.setBackground(Color.WHITE);
					panJoueurHumain.add(etqTourJH);
					
					JLabel etqTourJE = new JLabel("Tour de : "+joueur1.getNom());
					etqTourJE.setFont(police);
					etqTourJE.setBackground(Color.WHITE);
					panJoueurElectronique.add(etqTourJE);
					
					propJH[i] = new JFormattedTextField(mask);
					propJH[i].setFont(police);
					propJH[i].setBackground(Color.WHITE);
					propJH[i].setPreferredSize(new Dimension(100, 50));
					propJH[i].setEditable(false);
					propJH[i].setHorizontalAlignment(JFormattedTextField.CENTER);
					propJH[i].addActionListener(new ActionListener() {
	
						@Override
						public void actionPerformed(ActionEvent arg0) {
							joueur.updateObservateur();						
						}
						
					});
					panJoueurHumain.add(propJH[i]);
					
					//JLabel propJE = new JLabel("");
					propJE[i] = new JFormattedTextField(mask);
					propJE[i].setFont(police);
					propJE[i].setBackground(Color.WHITE);
					propJE[i].setPreferredSize(new Dimension(100, 50));
					propJE[i].setEditable(false);
					propJE[i].setHorizontalAlignment(JFormattedTextField.CENTER);
					propJE[i].addActionListener(new ActionListener() {
	
						@Override
						public void actionPerformed(ActionEvent arg0) {
							joueur1.updateObservateur();						
						}					
					});
					panJoueurElectronique.add(propJE[i]);
					
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
					
					
					
					
			////--L'etiquette essai n°
			//listEssai[i] = new JLabel(String.valueOf(i+1));
			//listEssai[i].setFont(police);
			//listEssai[i].setBackground(Color.white);
			//
			////--La zone de texte ou s'effectue la saisie
			//listProp[i] = new JFormattedTextField(mask);
			//listProp[i].setFont(police);
			//listProp[i].setBackground(Color.white);
			//listProp[i].setPreferredSize(new Dimension (100, 50));
			//listProp[i].setEditable(false);
			//listProp[i].setHorizontalAlignment(JFormattedTextField.CENTER);
			//listProp[i].addActionListener(new ActionListener() {
			//	@Override
			//	public void actionPerformed(ActionEvent arg0) {
			//		joueur.updateObservateur();
			//	}
			//});
			//
			////--L'étiquette qui affiche le résultat de la comparaison entre la saisie et la combinaison gagnante
			//listResult[i] = new JLabel("");
			//listResult[i].setFont(police);
			//listResult[i].setBackground(Color.white);
			//listResult[i].setPreferredSize(new Dimension (300, 50));
			//
			////--Le panneau qui accueille les 3 composants précédents
			//panRef[i] = new JPanel();
			//panRef[i].setBorder(BorderFactory.createEtchedBorder());
			//panRef[i].setBackground(Color.WHITE);
			//panRef[i].setLayout(new BoxLayout(panRef[i], BoxLayout.LINE_AXIS));
			//panRef[i].add(listEssai[i]);
			//panRef[i].add(listProp[i]);
			//panRef[i].add(listResult[i]);
			//
			//panTbleJeu.add(panRef[i]);
				}
	}
	
	public void nouvellePartie() {
		panTbleJeu.removeAll();
		resultCompa ="";

		try {
			initTable();
		}catch(ParseException e) {
			e.printStackTrace();
		}
		
		panTbleJeu.revalidate();
		panTbleJeu.repaint();
		//listProp[0].setEditable(true);
		propJH[0].setEditable(true);
		propJE[0].setEditable(true);
		this.setLayout(new BorderLayout());
		this.add(panTbleJeu, BorderLayout.CENTER);
		this.joueur.initCombiSecret();
		this.joueur1.initCombiSecret();
		//this.constrCombiSecret = this.joueur.getConstrCombiSecret();
		//this.combiSecret = this.joueur.getCombiSecret();
		this.joueur.setTourDeJeu(0);
		this.joueur.setVictoire(false);
	}
}
