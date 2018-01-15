package test_unitaire_table_jeu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;
import org.apache.log4j.Appender;


import Propriete.GestionFichierProperties;
import joueur.Joueur;
/**
 * Classe abstraite TableDeJeu qui definit une table de jeu
 * @author nicolas
 *
 */
public abstract class TableDeJeu_Test extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(TableDeJeu_Test.class);

	protected String nom = "Table De Jeu";
	
	protected Properties propriete;
	protected String modeJeu;
	protected int nbCoupsConfig; 
	protected int lgueurCombo;
	
	protected int tourDeJeu = 0;
	protected JLabel[] listResult;//--Une liste d'étiquette qui accueille le résultat de l'essai
	protected JFormattedTextField[] listProp;//--Une liste de champs de saisie pour saisir son coup
		
	protected Joueur joueur;
	
	protected String coupJoue ;
	protected Integer[] listPropJoueur ;
	protected String resultCompa;
	
	protected JPanel panTbleJeu = new JPanel();
	
	/**
	 * Constructeur sans parametre
	 */
	public TableDeJeu_Test() {
		//--on récupère les proprietes
		GestionFichierProperties gfp = new GestionFichierProperties();
		this.propriete = gfp.lireProp();
		modeJeu = String.valueOf(propriete.getProperty("mode"));
		//System.out.println("Ctrl mode : "+modeJeu);//--Controle
		logger.debug("Ctrl mode : "+modeJeu);
		this.nbCoupsConfig = Integer.valueOf(this.propriete.getProperty("nombres d'essai"));
		//System.out.println("Ctrl nb coup :"+this.nbCoupsConfig);//--Controle
		logger.debug("Ctrl nb coup :"+this.nbCoupsConfig);
		this.lgueurCombo = Integer.valueOf(this.propriete.getProperty("longueur combinaison"));
		//System.out.println("Ctrl lgueur :"+this.lgueurCombo);//--Controle
		logger.debug("Ctrl lgueur :"+this.lgueurCombo);
		
		
		//--Les composants graphiques
		this.setLayout(new BorderLayout());
		try {
			initTable();
		}catch(ParseException e) {
			e.printStackTrace();
		}
		this.add(panTbleJeu, BorderLayout.CENTER);
	}
	/**
	 * Constructeur avec parametres
	 * @param pEssai
	 * @param pCombo
	 */
	public TableDeJeu_Test(Joueur pJoueur, int pEssai, int pCombo) {
		this.joueur = pJoueur;
		this.nbCoupsConfig = pEssai;
		this.lgueurCombo = pCombo;
		//--Les composants graphiques
		this.setLayout(new BorderLayout());
		try {
			initTable();
		}catch(ParseException e) {
			e.printStackTrace();
		}
		this.add(panTbleJeu, BorderLayout.CENTER);
	}
	
	public TableDeJeu_Test(String pMode, int pEssai, int pCombo) {
		this.modeJeu = pMode;
		this.nbCoupsConfig = pEssai;
		this.lgueurCombo = pCombo;
		//--Les composants graphiques
		this.setLayout(new BorderLayout());
		try {
			initTable();
		}catch(ParseException e) {
			e.printStackTrace();
		}
		this.add(panTbleJeu, BorderLayout.CENTER);
	}
	
	
	/**
	 * Methode initialisant la table selon le nb d'essai et la longueur de la combinaison
	 * @throws ParseException
	 */
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
					jeu();
					//jeu1();
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
	
	public String getNom() {
		return this.nom;
	}
	
	public void initCombiSecret() {}
	
	/**
	 * La méthode jeu, vide pour l'instant, sera definie dans les classes filles
	 */
	public void jeu() {}
	
	public void jeu1() {}
	
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
		initCombiSecret();
		tourDeJeu = 0;
	}
}
