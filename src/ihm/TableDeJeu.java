package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TableDeJeu extends JPanel {

	/**
	 * Attributs de la classe 
	 */
	private static final long serialVersionUID = 1L;
	private String nom = "Table De Jeu";
	private int nbCoupsConfig = 10;//--Ici je recup la donnée du fichier properties 
	private int lgueurCombo = 4;//--Ici je recup la donnée du fichier properties
	private String[][] coupsJoues = new String[nbCoupsConfig][nbCoupsConfig];

	
	JPanel panTbleJeu = new JPanel();
	
	/**
	 * Constructeur sans paramètres
	 */
	public TableDeJeu() {
		//--Le joueur
		
		//--Les composants graphiques
		this.setLayout(new BorderLayout());
		initTable();
		
		//--Le clavier
		
		this.add(panTbleJeu, BorderLayout.WEST);
	}
	
	/**
	 * La méthode créant la table de jeu
	 * @return
	 */
	public void initTable() {
		//--Le panneau qui accueille la table de jeu
		
		panTbleJeu.setPreferredSize(new Dimension (400, 500));
		panTbleJeu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panTbleJeu.setLayout(new BoxLayout(panTbleJeu, BoxLayout.PAGE_AXIS));
		panTbleJeu.setBackground(Color.WHITE);
		
		//--Le panneau de ref
		JPanel[] panRef = new JPanel[this.nbCoupsConfig];
		JLabel[] listLabel = new JLabel[this.lgueurCombo];

		for (int j = 0; j <nbCoupsConfig; j++) {
			for (int i = 0; i<lgueurCombo; i++) {
				listLabel[i].setBorder(BorderFactory.createEtchedBorder());
				listLabel[i].setBackground(Color.white);
				
				panRef[j].setPreferredSize(new Dimension (100, 500));
				panRef[j].setBorder(BorderFactory.createLoweredBevelBorder());
				panRef[j].setLayout(new BoxLayout(panRef[j], BoxLayout.LINE_AXIS));
				panRef[j].setBackground(Color.LIGHT_GRAY);
				panRef[j].add(listLabel[i]);
			}
			panTbleJeu.add(panRef[j]);
		}
		for (int j = 0; j<nbCoupsConfig;j++ ) {
			//panTbleJeu.add(panRef[j]);
		}
	}
	
	
	public String getNom() {
		return this.nom;
	}
	
	
}
