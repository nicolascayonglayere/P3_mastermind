package ihm;

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

public class TableDeJeu_1 extends TableDeJeu {

private static final long serialVersionUID = 1L;
	
	public TableDeJeu_1() {
		super();
	}
	
	public TableDeJeu_1(String pJeu, String pMode, int pEssai, int pCombo) {
		super(pJeu, pMode, pEssai, pCombo);
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
		this.listResult = new JLabel[this.nbCoupsConfig];
		
		for(int i = 0; i<nbCoupsConfig; i++) {
			//--L'etiquette essai n°
			listEssai[i] = new JLabel(String.valueOf(i+1));
			listEssai[i].setFont(police);
			listEssai[i].setBackground(Color.white);
			
			//--La zone de texte ou s'effectue la saisie
			listProp[i] = new JFormattedTextField(mask);
			listProp[i].setFont(police);
			listProp[i].setBackground(Color.white);
			listProp[i].setPreferredSize(new Dimension (100, 50));
			listProp[i].setEditable(false);
			listProp[i].setHorizontalAlignment(JFormattedTextField.CENTER);
			listProp[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					joueur.updateObservateur();
				}
			});
			
			//--L'étiquette qui affiche le résultat de la comparaison entre la saisie et la combinaison gagnante
			listResult[i] = new JLabel("");
			listResult[i].setFont(police);
			listResult[i].setBackground(Color.white);
			listResult[i].setPreferredSize(new Dimension (300, 50));
			
			//--Le panneau qui accueille les 3 composants précédents
			panRef[i] = new JPanel();
			panRef[i].setBorder(BorderFactory.createEtchedBorder());
			panRef[i].setBackground(Color.WHITE);
			panRef[i].setLayout(new BoxLayout(panRef[i], BoxLayout.LINE_AXIS));
			panRef[i].add(listEssai[i]);
			panRef[i].add(listProp[i]);
			panRef[i].add(listResult[i]);
			
			panTbleJeu.add(panRef[i]);
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
		listProp[0].setEditable(true);
		this.setLayout(new BorderLayout());
		this.add(panTbleJeu, BorderLayout.CENTER);
		this.joueur.initCombiSecret();
		//this.constrCombiSecret = this.joueur.getConstrCombiSecret();
		//this.combiSecret = this.joueur.getCombiSecret();
		this.joueur.setTourDeJeu(0);
		this.joueur.setVictoire(false);
	}
}
