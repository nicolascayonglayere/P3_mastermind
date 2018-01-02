package pattern_observer;

/**
 * L'interface Observable du pattern Observer avec ses 3 methodes vides : 
 * ajout d'abonne, mise ajour des abonnes, suppression d'abonnes
 * @author nicolas
 *
 */

public interface Observable {
	public void addObservateur(Observateur o);
	public void updateObservateur();
	public void delObservateur();
}
