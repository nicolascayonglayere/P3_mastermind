package clavier;

/**
 * L'interface Observable du pattern Observer avec ses 3 methodes vides : 
 * ajout d'abonne, mise ajour des abonnes, suppression d'abonnes
 * @author nicolas
 *
 */

public interface Observable_Clavier {
	public void addObservateur(Observateur_Clavier o);
	public void updateObservateur();
	public void delObservateur();
}
