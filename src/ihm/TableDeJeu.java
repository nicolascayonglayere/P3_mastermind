package ihm;

import java.awt.BorderLayout;
import java.util.Properties;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import Propriete.GestionFichierProperties;
import joueur.Joueur;
import joueur.JoueurElectronique;
import joueur.JoueurHumain;



/**
 * Classe abstraite dont hérite les différentes tables de jeu
 * @author nicolas
 *
 */
public abstract class TableDeJeu extends JPanel {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger("ihm");
	protected Joueur joueur, joueur1;
	protected String nom = "Table De Jeu";
	
	protected Properties propriete;
	protected String jeu, modeJeu;
	protected int nbCoupsConfig; 
	protected int lgueurCombo;
	protected int modeDev = 0; 
	protected int couleur = -1;
	
	protected JLabel modeDevLbl;
	
	protected int tourDeJeu ;
	protected JLabel[] listResult;//--Une liste d'étiquette qui accueille le résultat de l'essai
	protected JLabel[] listResultJH, listResultJE;
	protected JLabel[][] listLblProp;
	protected JLabel[][] listLblPropJH, listLblPropJE;
	protected JFormattedTextField[] listProp;//--Une liste de champs de saisie pour saisir son coup
	protected JFormattedTextField[] propJH, propJE;
	
	protected String resultCompa, coupJoue;
	
	protected JPanel panTbleJeu = new JPanel();
	
	//protected JButton finDeTour = new JButton("TOUR SUIVANT");
	
	/**
	 * Constructeur sans parametres, jamais utilise
	 */
	public TableDeJeu() {
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

		initTable();

		this.add(panTbleJeu, BorderLayout.CENTER);
	}
	
	/**
	 * Constructeur avec parametres
	 * @param pJeu
	 * @param pMode
	 * @param pEssai
	 * @param pCombo
	 */
	public TableDeJeu(String pJeu, String pMode, int pEssai, int pCombo, int pModeDev, int pCouleur) {
		this.jeu = pJeu;
		this.modeJeu = pMode;
		this.nbCoupsConfig = pEssai;
		this.lgueurCombo = pCombo;
		this.modeDev = pModeDev;
		this.couleur = pCouleur;
		this.coupJoue = "";
		
		this.joueur = new JoueurHumain(lgueurCombo, jeu, couleur);
		this.joueur1 = new JoueurElectronique(lgueurCombo, jeu, couleur);

		//--Les composants graphiques
		this.setLayout(new BorderLayout());

		initTable();


		this.add(panTbleJeu, BorderLayout.CENTER);
	}
	/**
	 * Methode pour initialiser les composants graphiques de la table, vide pour l'instant, elle est définie dans les classes filles
	 */
	public void initTable() {}
	
	/**
	 * Méthode pour initialiser une nouvelle partie, vide pour l'instant, elle est définie dans les classes filles 
	 */
	public void nouvellePartie() {}
	
	/**
	 * Méthode qui renvoie le nom de la tablde pour l'afficher
	 * @return
	 */
	public String getNom() {
		return this.nom;
	}
}
