package joueur;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import pattern_observer.Observable;
import pattern_observer.Observateur;

public abstract class Joueur implements Observable {
	static Logger logger = Logger.getLogger(Joueur.class);
	
	protected String nom, jeu;
	protected int tourDeJeu;
	protected int combiSecret, lgueurCombo;
	protected Integer[] constrCombiSecret;
	
	protected Integer[] listPropJoueur ;
	protected String resultCompa, coupJoue;
	protected Boolean fin;
	
	protected ArrayList<Observateur> listObs;
	
	public Joueur() {
		this.nom = "";
		this.combiSecret = 0;
		this.tourDeJeu = 0;
		this.listObs = new ArrayList<Observateur>();
	}
	
	public Joueur(int pCombo, String pJeu) {
		this.nom = "";
		this.tourDeJeu = 0;
		this.combiSecret = 0;
		this.lgueurCombo = pCombo;
		this.jeu = pJeu;
		this.fin = false;
		this.listObs = new ArrayList<Observateur>();
	}
	
	public void initCombiSecret() {}
	
	public Integer[] getConstrCombiSecret(){
		return this.constrCombiSecret;
	}
	
	public int getCombiSecret() {
		return this.combiSecret;
	}
	public void jeu(String pCoupJoue) {}
	
	public void compare() {}
	
	public void setVictoire(Boolean pVictoire) {
		this.fin = pVictoire;
	}
	
	public boolean getVictoire() {
		return this.fin;
	}
	
	public String getResultCompa() {
		return this.resultCompa;
	}
	
	public void setTourDeJeu(int pTourDeJeu) {
		this.tourDeJeu = pTourDeJeu;
	}
	
	public int getTourDeJeu() {
		return this.tourDeJeu;
	}
	
	public String getPropOrdi() {
		return this.coupJoue;
	}
	public void addObservateur(Observateur o) {
		this.listObs.add(o);
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void updateObservateur() {
		for (Observateur o : this.listObs) {
			o.update("");
		}
	}
	
	public void delObservateur() {
		this.listObs = new ArrayList<Observateur>();
	}
}
