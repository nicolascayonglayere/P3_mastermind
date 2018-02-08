package joueur;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import pattern_observer.Observable;
import pattern_observer.Observateur;

/**
 * Classe abstraite joueur, son comportement est défini dans les classes filles
 * @author nicolas
 *
 */
public abstract class Joueur implements Observable {
	static Logger logger = Logger.getLogger("ihm");
	
	protected String nom, jeu;
	protected int tourDeJeu;
	protected int combiSecret, lgueurCombo, couleur;
	protected Integer[] constrCombiSecret;
	
	protected Integer[] listPropJoueur ;
	protected String resultCompa, coupJoue;
	protected Boolean fin;
	
	protected ArrayList<Observateur> listObs;
	protected ArrayList<String> paramObs;
	
	/**
	 * Constructeur sans parametre
	 */
	public Joueur() {
		this.nom = "";
		this.combiSecret = 0;
		this.tourDeJeu = 0;
		this.listObs = new ArrayList<Observateur>();
		this.paramObs = new ArrayList<String>();
	}
	
	/**
	 * Constructeur avec parametres
	 * @param pCombo
	 * @param pJeu
	 */
	public Joueur(int pCombo, String pJeu, int pCouleur) {
		this.nom = "";
		this.tourDeJeu = 0;
		this.combiSecret = 0;
		this.lgueurCombo = pCombo;
		this.jeu = pJeu;
		this.fin = false;
		this.listObs = new ArrayList<Observateur>();
		this.paramObs = new ArrayList<String>();
		this.couleur = pCouleur;
	}
	
	/**
	 * Methode initialisant la combinaison secrete
	 */
	public void initCombiSecret() {}
	
	public Integer[] getConstrCombiSecret(){
		return this.constrCombiSecret;
	}
	
	/**
	 * Methode encapsulant la combinaison secrete
	 * @return
	 */
	public int getCombiSecret() {
		return this.combiSecret;
	}
	
	/**
	 * Methode définissant comment joue le joueur
	 * @param pCoupJoue
	 */
	public void jeu(String pCoupJoue) {}
			
	/**
	 * Methode pour comparer la proposition du joueur avec la combinaison secrete
	 */
	public void compare() {}
	
	/**
	 * Methode definissant si le joueur a gagne
	 * @param pVictoire
	 */
	public void setVictoire(Boolean pVictoire) {
		this.fin = pVictoire;
	}
	
	/**
	 * Methode encapsulant l'information de la victoire
	 * @return
	 */
	public boolean getVictoire() {
		return this.fin;
	}
	
	/**
	 * Méthode encapsulant le résultat de la comparaison
	 * @return
	 */
	public String getResultCompa() {
		return this.resultCompa;
	}
	
	/**
	 * Méthode pour initialiser le tour de jeu du joueur
	 * @param pTourDeJeu
	 */
	public void setTourDeJeu(int pTourDeJeu) {
		this.tourDeJeu = pTourDeJeu;
	}
	
	/**
	 * Methode encapsulant le tour de jeu du joueur
	 * @return
	 */
	public int getTourDeJeu() {
		return this.tourDeJeu;
	}
	
	/**
	 * Methode encapsulant la proposition de l'ordinateur lorsqu'il joue
	 * @return
	 */
	public String getPropOrdi() {
		return this.coupJoue;
	}
	
	/**
	 * Methode d'ajout d'un observateur/Pattern Observeur
	 */
	public void addObservateur(Observateur o) {
		this.listObs.add(o);
	}
	
	/**
	 * Méthode encapsulant le nom du joueur
	 * @return
	 */
	public String getNom() {
		return this.nom;
	}
	
	/**
	 * Methode de mise a jour de l'observateur/Pattern Observeur
	 */
	public void updateObservateur() {
		for (Observateur o : this.listObs) {
			o.update(this.paramObs);
		}
	}
	
	/**
	 * Méthode de suppression de l'observateur/Pattern Observeur
	 */
	public void delObservateur() {
		this.listObs = new ArrayList<Observateur>();
	}
}
