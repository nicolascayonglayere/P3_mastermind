package Propriete;

/**
 * Enumération des différents modes de jeu
 * @author nicolas
 *
 */
public enum ModeJeu {
	CHALLENGER("mode CHALLENGER"),
	DEFENSEUR("mode DEFENSEUR"),
	DUEL("mode DUEL");
	
	private String nom = "";
	
	/**
	 * Constructeur avec parametre
	 * @param pNom
	 */
	ModeJeu(String pNom){
		this.nom = pNom;
	}
	
	/**
	 * Methode retournant le nom de ModeJeu
	 */
	public String toString() {
		return this.nom;
	}
}
