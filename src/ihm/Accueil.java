package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * La classe Accueil hérite de JPanel
 * @author nicolas
 *
 */
public class Accueil extends JPanel{
	/**
	 * Les attributs de la classe
	 */
	private static final long serialVersionUID = 1L;
	private String nom = "Accueil";
	
	/**
	 * Constructeur sans parametre
	 */
	public Accueil() {
		//--Zone de texte
		JTextArea message = new JTextArea();
		Font police = new Font("Arial", Font.BOLD, 18);
		message.setFont(police);
		message.setPreferredSize(new Dimension(760,75));
		message.setForeground(Color.BLACK);
		message.setEditable(false);
		message.setText("EN CONSTRUCTION");

		this.add(message);
	}
	
	/**
	 * Methode retournant le nom du panneau
	 */
	public String getNom() {
		return this.nom;
	}
	
}
