package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import Propriete.ModeJeu;
import Propriete.TypeJeu;

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
	private String jeu, modeJeu ;
	private int nbEssai, lgueurCombo, couleur;
	private String message;
	
	/**
	 * Constructeur sans parametre
	 */
	public Accueil() {
		this.setLayout(new BorderLayout());
		//--Zone de texte
		JTextArea accueil = new JTextArea();
		Font police = new Font("Arial", Font.BOLD, 18);
		accueil.setFont(police);
		//accueil.setPreferredSize(new Dimension(760,75));
		accueil.setForeground(Color.BLACK);
		accueil.setEditable(false);
		accueil.setText("\n\t BIENVENUE DANS LE JEU DU MASTERMIND \n\n\n\n\n\n"
				+ " Dans ce jeu vous pouvez choisir de découvrir entre une recherche +/- d'une combinaison\n cachée de chiffre.\n\n"
				+ " Ou vous pouvez choisir le je du Mastermind avec des chiffres ou des couleurs ! \n\n"
				+ " Paramétrez une nouvelle partie ou lancez une partie");

		this.add(accueil, BorderLayout.CENTER);
	}
	
	/**
	 * Constructeur avec parametre
	 * @param pJeu
	 * @param pMode
	 * @param pEssai
	 * @param pCombo
	 * @param pCouleur
	 */
	public Accueil(String pJeu, String pMode, int pEssai, int pCombo, int pCouleur) {
		this.jeu = pJeu;
		this.modeJeu = pMode;
		this.nbEssai = pEssai;
		this.lgueurCombo = pCombo;
		this.couleur = pCouleur;
		String str = "";
		if(couleur == 0)
			str = "chiffres";
		else if (couleur == 1)
			str = "couleurs";
		this.setLayout(new BorderLayout());
		//--Zone de texte
		JTextArea accueil = new JTextArea();
		Font police = new Font("Arial", Font.BOLD, 18);
		accueil.setFont(police);
		accueil.setForeground(Color.BLACK);
		accueil.setEditable(false);
		//--Selon le jeu et le mode on affiche un message
		if (jeu.equals(TypeJeu.RECHERCHE_NUM.toString())) {
			message = "\n\t Vous avez choisi le jeu de recherche +/- \n\n\n\n\n";
			message += " La combinaison cachée contient "+lgueurCombo+" chiffres et vous avez "+nbEssai+" essais pour la trouver.\n\n";
			if(modeJeu.equals(ModeJeu.CHALLENGER.toString())) {
				message += " Tapez votre proposition sur les touches à l'écran ou sur votre clavier numérique\n puis cliquez sur le bouton Tour suivant.\n\n";
				message += " L'ordinateur vous retourne le résultat de la comparaison et vous pouvez entrer\n votre proposition suivante.\n\n";
				message += " Bonne chance ! ";
			}
			else if (modeJeu.equals(ModeJeu.DEFENSEUR.toString())) {
				message += " Saississez une combinaison que votre ordinateur doit découvrir. \n\n";
				message += " Cliquez sur le bouton Tour suivant pour obtenir sa proposition\n et le résultat de la comparaison.";
			}
			else if (modeJeu.equals(ModeJeu.DUEL.toString())) {
				message += " Saississez une combinaison que votre ordinateur doit découvrir. \n\n";
				message += " Tapez votre proposition sur les touches à l'écran ou sur votre clavier numérique\n puis cliquez sur le bouton Tour suivant.\n\n";
				message += " L'ordinateur vous retourne le résultat de la comparaison ainsi que sa proposition et\n le résultat.\n\n";
				message += " Bonne chance !";
			}
			
		}
		else if (jeu.equals(TypeJeu.MASTERMIND.toString())) {
			message = "\n\t Vous avez choisi le jeu du Mastermind \n\n\n\n\n";
			message += " La combinaison cachée contient "+lgueurCombo+" "+str+" et vous avez "+nbEssai+" essais pour la trouver.\n\n";
			
			if(modeJeu.equals(ModeJeu.CHALLENGER.toString())) {
				message += " Tapez votre proposition sur les touches à l'écran ou sur votre clavier numérique\n puis cliquez sur le bouton Tour suivant.\n\n";
				message += " L'ordinateur vous retourne le résultat de la comparaison et vous pouvez entrer\n votre proposition suivante.\n\n";
				message += " Bonne chance ! ";
			}
			else if (modeJeu.equals(ModeJeu.DEFENSEUR.toString())) {
				message += " Saississez une combinaison que votre ordinateur doit découvrir. \n\n";
				message += " Cliquez sur le bouton Tour suivant pour obtenir sa proposition\n et le résultat de la comparaison.";
			}
			else if (modeJeu.equals(ModeJeu.DUEL.toString())) {
				message += " Saississez une combinaison que votre ordinateur doit découvrir. \n\n";
				message += " Tapez votre proposition sur les touches à l'écran ou sur votre clavier numérique\n puis cliquez sur le bouton Tour suivant.\n\n";
				message += " L'ordinateur vous retourne le résultat de la comparaison ainsi que sa proposition et\n le résultat.\n\n";
				message += " Bonne chance !";
			}
		}
		accueil.setText(message);

		this.add(accueil, BorderLayout.CENTER);
	}
	
	/**
	 * Methode retournant le nom du panneau
	 */
	public String getNom() {
		return this.nom;
	}
	
}
