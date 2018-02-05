package pattern_observer;



/**
 * Interface Observateur du pattern Observer
 * avec la methode qui met a jour l'affichage de la fenetre
 * @author nicolas
 *
 */
public interface Observateur {
	//public void update(String value);
	public void update(Object o);
}
