package Propriete;

/**
 * Enumération des différents jeu
 * @author nicolas
 *
 */
public enum TypeJeu {
	RECHERCHE_NUM("Jeu de recherche plus moins"),
	MASTERMIND("Jeu du MasterMind");
	
	private String nom = "";
	
	/**
	 * Constructeur avec parametre
	 * @param pNom
	 */
	TypeJeu(String pNom){
		this.nom = pNom;
	}
	
	/**
	 * méthode retournant le nom de TypeJeu
	 */
	public String toString(){
		return this.nom;
	}
}
