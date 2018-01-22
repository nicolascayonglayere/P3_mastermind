package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.text.ParseException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import Propriete.GestionFichierProperties;
import Propriete.ModeJeu;
import joueur.Joueur;
import joueur.JoueurElectronique;
import joueur.JoueurHumain;
import pattern_observer.Observateur;


/**
 * Classe abstraite dont hérite les différentes tables de jeu
 * @author nicolas
 *
 */
public abstract class TableDeJeu extends JPanel {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger("ihm");
	protected Joueur joueur, joueur1;
	private String nom = "Table De Jeu";
	
	protected Properties propriete;
	protected String jeu, modeJeu;
	protected int nbCoupsConfig; 
	protected int lgueurCombo;
	protected int modeDev = 0; 
	protected int couleur = -1;
	
	protected JLabel modeDevLbl;
	
	private int tourDeJeu ;
	protected JLabel[] listResult;//--Une liste d'étiquette qui accueille le résultat de l'essai
	protected JLabel[] listResultJH, listResultJE;
	protected JLabel[][] listLblProp;
	protected JLabel[][] listLblPropJH, listLblPropJE;
	protected JFormattedTextField[] listProp;//--Une liste de champs de saisie pour saisir son coup
	protected JFormattedTextField[] propJH, propJE;
	
	protected String resultCompa;
	
	protected JPanel panTbleJeu = new JPanel();
	
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
		try {
			initTable();
		}catch(ParseException e) {
			e.printStackTrace();
		}
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

		//--Selon le mode de jeu on initialise le joueur et la méthode update du pattern Observateur pour faire jouer le joueur et mettre a jour la table
		if (pMode.equals(ModeJeu.CHALLENGER.toString())) {
			this.joueur = new JoueurHumain(lgueurCombo, jeu);
			this.joueur.addObservateur(new Observateur() {
				public void update(String coupJoue) {
					tourDeJeu = joueur.getTourDeJeu();
					logger.debug("Ctrl TourDeJeu : "+tourDeJeu);
				//coupJoue = listProp[tourDeJeu].getText();
				//joueur.jeu(coupJoue);
				//resultCompa = joueur.getResultCompa();
				//listResult[tourDeJeu].setText(resultCompa);
					
					//--Si le joueur n'a pas gagné et qu'il reste des tours à jouer on active la zone de saisie suivante
					if(joueur.getVictoire() == false && tourDeJeu+1 < nbCoupsConfig) {
						for(int i = 0; i<lgueurCombo; i++) {
							coupJoue += listLblProp[tourDeJeu][i].getText();
						}
						//coupJoue = listProp[tourDeJeu].getText();//--table de jeu 1
						joueur.jeu(coupJoue);
						resultCompa = joueur.getResultCompa();
						listResult[tourDeJeu].setText(resultCompa);
						
						//listProp[tourDeJeu + 1].setEditable(true);//--table de jeu 1
					}
					//--si c'est la victoire
					else if (joueur.getVictoire() == true) {
						resultCompa = joueur.getResultCompa();
						listResult[tourDeJeu].setText(resultCompa);
						int option = JOptionPane.showConfirmDialog(null, "Félicitation, vous avez trouvé la combinaison secrète ! \n Voulez-vous rejouer ?",
										"Victoire", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
					//--Si c'est la défaite
					else if(joueur.getVictoire() == false && tourDeJeu+1 == nbCoupsConfig) {
						resultCompa = joueur.getResultCompa();
						listResult[tourDeJeu].setText(resultCompa);
						int option = JOptionPane.showConfirmDialog(null, "C'est perdu ! \n La combinaison gagnante est "+joueur.getCombiSecret()+"\n Voulez-vous rejouer ?",
										"Défaite", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
				}				
			});
		}
		else if (pMode.equals(ModeJeu.DEFENSEUR.toString())) {
			this.joueur = new JoueurElectronique(lgueurCombo, jeu);
			this.joueur.addObservateur(new Observateur() {
				public void update(String coupJoue) {
					tourDeJeu = joueur.getTourDeJeu();	
					logger.debug("Ctrl TourDeJeu : "+tourDeJeu);
					joueur.jeu(coupJoue);
					
					if(pCouleur == 0) {
						for (int k = 0; k<joueur.getPropOrdi().toCharArray().length; k++) {
							listLblProp[tourDeJeu][k].setText(String.valueOf(joueur.getPropOrdi().charAt(k)));
						}
					}
					else if (pCouleur == 1) {
						//--on met ds la JLabel le fichier couleur du bouton et le chiffre/lettre du bouton
						Color[]listColor = {Color.WHITE, Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PINK};
						ImageIcon[] listImageColor = {new ImageIcon("Ressources/Images/blanc0.JPG"),
											       	new ImageIcon ("Ressources/Images/noir1.JPG"), 
											       	new ImageIcon("Ressources/Images/rouge2.JPG"),
											       	new ImageIcon("Ressources/Images/jaune3.JPG"), 
											       	new ImageIcon("Ressources/Images/vert4.JPG"),
											       	new ImageIcon("Ressources/Images/bleu5.JPG"),
											       	new ImageIcon("Ressources/Images/orange6.JPG"),
											       	new ImageIcon("Ressources/Images/rose8.JPG")};
						for (int k = 0; k<joueur.getPropOrdi().toCharArray().length; k++) {
							listLblProp[tourDeJeu][k].setIcon(listImageColor[Character.getNumericValue(joueur.getPropOrdi().charAt(k))]);
							listLblProp[tourDeJeu][k].setForeground(listColor[Character.getNumericValue(joueur.getPropOrdi().charAt(k))]);
							listLblProp[tourDeJeu][k].setText(String.valueOf(joueur.getPropOrdi().charAt(k)));
						}
					}
					
				//listProp[tourDeJeu].setText(joueur.getPropOrdi());
				//resultCompa = joueur.getResultCompa();
				//listResult[tourDeJeu].setText(resultCompa);

					//--Si le joueur n'a pas gagné et qu'il reste des tours à jouer on active la zone de saisie suivante
					if(joueur.getVictoire() == false && tourDeJeu+1 != nbCoupsConfig) {
						resultCompa = joueur.getResultCompa();
						listResult[tourDeJeu].setText(resultCompa);
						//listProp[tourDeJeu + 1].setEditable(true);//--Table de jeu 1
					}
					//--si c'est la victoire
					else if (joueur.getVictoire() == true) {
						resultCompa = joueur.getResultCompa();
						listResult[tourDeJeu].setText(resultCompa);
						int option = JOptionPane.showConfirmDialog(null, "Félicitation, vous avez trouvé la combinaison secrète ! \n Voulez-vous rejouer ?",
										"Victoire", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
					//--Si c'est la défaite
					else if(joueur.getVictoire() == false && tourDeJeu+1 == nbCoupsConfig) {
						resultCompa = joueur.getResultCompa();
						listResult[tourDeJeu].setText(resultCompa);
						int option = JOptionPane.showConfirmDialog(null, "C'est perdu ! \n La combinaison gagnante est "+joueur.getCombiSecret()+"\n Voulez-vous rejouer ?",
										"Défaite", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
				}				
			});
		}
		
		else if(pMode.equals(ModeJeu.DUEL.toString())) {
			this.joueur = new JoueurHumain(lgueurCombo, jeu);
			this.joueur.addObservateur(new Observateur() {
				public void update(String coupJoue) {
					tourDeJeu = joueur.getTourDeJeu();
					logger.debug("Ctrl TourDeJeu : "+tourDeJeu);


				//joueur.jeu(coupJoue);
				//resultCompa = joueur.getResultCompa();
				//listResultJH[tourDeJeu].setText(resultCompa);
					
					//--Si le joueur n'a pas gagné et qu'il reste des tours à jouer on active la zone de saisie suivante
					if(joueur.getVictoire() == false && tourDeJeu+1 < nbCoupsConfig) {
						for(int i = 0; i<lgueurCombo; i++) {
							coupJoue += listLblPropJH[tourDeJeu][i].getText();
						}
						//coupJoue = listProp[tourDeJeu].getText();//--table de jeu 1
						joueur.jeu(coupJoue);
						resultCompa = joueur.getResultCompa();
						listResultJH[tourDeJeu].setText(resultCompa);
						//propJH[tourDeJeu + 1].setEditable(true);
					}
					//--si c'est la victoire
					else if (joueur.getVictoire() == true) {
						resultCompa = joueur.getResultCompa();
						listResultJH[tourDeJeu].setText(resultCompa);
						int option = JOptionPane.showConfirmDialog(null, "Félicitation, "+joueur.getNom()+" a trouvé la combinaison secrète ! \n "
								+ "La combinaison de votre adversaire était : "+joueur1.getCombiSecret()+" \n Voulez-vous rejouer ?",
										"Victoire", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
					//--Si c'est la défaite
					else if(joueur.getVictoire() == false && tourDeJeu+1 == nbCoupsConfig) {
						resultCompa = joueur.getResultCompa();
						listResultJH[tourDeJeu].setText(resultCompa);
						int option = JOptionPane.showConfirmDialog(null, "C'est perdu ! \n La combinaison gagnante est "+joueur.getCombiSecret()+"\n Voulez-vous rejouer ?",
										"Défaite", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
				}				
			});
			this.joueur1 = new JoueurElectronique(lgueurCombo, jeu);
			this.joueur1.addObservateur(new Observateur() {
				public void update(String coupJoue) {
					tourDeJeu = joueur1.getTourDeJeu();	
					logger.debug("Ctrl TourDeJeu : "+tourDeJeu);
					joueur1.jeu(coupJoue);
				//propJE[tourDeJeu].setText(joueur1.getPropOrdi());
				//resultCompa = joueur1.getResultCompa();
				//listResultJE[tourDeJeu].setText(resultCompa);
					if(pCouleur == 0) {
						for (int k = 0; k<joueur.getPropOrdi().toCharArray().length; k++) {
							listLblPropJE[tourDeJeu][k].setText(String.valueOf(joueur1.getPropOrdi().charAt(k)));
						}
					}
					else if (pCouleur == 1) {
						//--on met ds la JLabel le fichier couleur du bouton et le chiffre/lettre du bouton
						Color[]listColor = {Color.WHITE, Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PINK};
						ImageIcon[] listImageColor = {new ImageIcon("Ressources/Images/blanc0.JPG"),
											       	new ImageIcon ("Ressources/Images/noir1.JPG"), 
											       	new ImageIcon("Ressources/Images/rouge2.JPG"),
											       	new ImageIcon("Ressources/Images/jaune3.JPG"), 
											       	new ImageIcon("Ressources/Images/vert4.JPG"),
											       	new ImageIcon("Ressources/Images/bleu5.JPG"),
											       	new ImageIcon("Ressources/Images/orange6.JPG"),
											       	new ImageIcon("Ressources/Images/rose8.JPG")};
						for (int k = 0; k<joueur.getPropOrdi().toCharArray().length; k++) {
							listLblPropJE[tourDeJeu][k].setIcon(listImageColor[Character.getNumericValue(joueur1.getPropOrdi().charAt(k))]);
							listLblPropJE[tourDeJeu][k].setForeground(listColor[Character.getNumericValue(joueur1.getPropOrdi().charAt(k))]);
							listLblPropJE[tourDeJeu][k].setText(String.valueOf(joueur1.getPropOrdi().charAt(k)));
						}
					}
					
					//--Si le joueur n'a pas gagné et qu'il reste des tours à jouer on active la zone de saisie suivante
					if(joueur1.getVictoire() == false && tourDeJeu+1 != nbCoupsConfig) {
						resultCompa = joueur.getResultCompa();
						listResultJE[tourDeJeu].setText(resultCompa);
						//propJE[tourDeJeu + 1].setEditable(true);
					}
					
					//--si c'est la victoire
					else if (joueur1.getVictoire() == true) {
						resultCompa = joueur.getResultCompa();
						listResultJE[tourDeJeu].setText(resultCompa);
						int option = JOptionPane.showConfirmDialog(null, "Félicitation, "+joueur1.getNom()+" a trouvé la combinaison secrète ! \n "
								+ "La combinaison de votre adversaire était : "+joueur.getCombiSecret()+" \n Voulez-vous rejouer ?",
										"Victoire", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
					//--Si c'est la défaite
					else if(joueur1.getVictoire() == false && tourDeJeu+1 == nbCoupsConfig) {
						resultCompa = joueur.getResultCompa();
						listResultJE[tourDeJeu].setText(resultCompa);
						int option = JOptionPane.showConfirmDialog(null, "C'est perdu ! \n La combinaison gagnante est "+joueur1.getCombiSecret()+"\n Voulez-vous rejouer ?",
										"Défaite", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
				}				
			});
		}
		//--Les composants graphiques
		this.setLayout(new BorderLayout());
		try {
			initTable();
		}catch(ParseException e) {
			e.printStackTrace();
		}

		this.add(panTbleJeu, BorderLayout.CENTER);
	}
	/**
	 * Methode pour initialiser les composants graphiques de la table, vide pour l'instant, elle est définie dans les classes filles
	 * @throws ParseException
	 */
	public void initTable() throws ParseException{}
	
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
