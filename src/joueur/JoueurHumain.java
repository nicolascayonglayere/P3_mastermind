package joueur;

import java.util.Random;

import javax.swing.JOptionPane;

import Propriete.TypeJeu;

/**
 * Classe définissant le je d'un joueur humain
 * @author nicolas
 *
 */
public class JoueurHumain extends Joueur {
	
	/**
	 * Constructeur sans parametre
	 */
	public JoueurHumain() {
		super();
		//--Initialisation du joueur via une boite de dialogue
		this.nom = JOptionPane.showInputDialog(null, "Quel est votre nom ?", "Identification", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(null, "Bonjour "+this.nom, "Bonjour", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Constructeur avec parametres
	 * @param pCombo
	 * @param pJeu
	 */
	public JoueurHumain(int pCombo, String pJeu, int pCouleur) {
		super(pCombo, pJeu, pCouleur);
		//--Initialisation du joueur via une boite de dialogue
		this.nom = JOptionPane.showInputDialog(null, "Quel est votre nom ?", "Identification", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(null, "Bonjour "+this.nom, "Bonjour", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Méthode pour initialiser la combinaison que le joueur devra trouver
	 */
	public void initCombiSecret() {
		this.constrCombiSecret = new Integer[this.lgueurCombo];
		Random alea = new Random();
		String str = "";
				
		for (int i = 0; i<lgueurCombo; i++) {
			if(couleur == 0) {
				//--on tire 1 chiffre au hasard
				constrCombiSecret[i] = alea.nextInt(10);
			}
			else {
				//--on tire 1 chiffre au hasard
				constrCombiSecret[i] = alea.nextInt(8);
			}

			str += String.valueOf(constrCombiSecret[i]);//--on concatene les différents chiffres 
		}
		this.combiSecret = Integer.valueOf(str);
		//System.out.println("la combo gagnante : "+this.combiSecret);//--Controle
		logger.info("la combo gagnante : "+this.combiSecret);
		
		String message = "La combinaison secrète est prête \n";
		message += "A vous de jouer";
		JOptionPane.showMessageDialog(null, message, "Combinaison secrète prête !", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Méthode jeu qui récupere la proposition du joueur et la compare
	 */
	public void jeu(String pCoupJoue) {
		this.listPropJoueur = new Integer[this.lgueurCombo];
		resultCompa = "";
		coupJoue = "";
		coupJoue = pCoupJoue;
		//System.out.println("la proposition du joueur : "+coupJoue);
		logger.debug("la proposition du joueur : "+coupJoue);
		char[] tabint = coupJoue.toCharArray();
		
		for (int i = 0; i<lgueurCombo; i++) {
			try {
				this.listPropJoueur[i] = Character.getNumericValue(tabint[i]);
				//System.out.println("la liste de prop du joueur : "+listPropJoueur[i]);
				logger.debug("la liste de prop du joueur : "+listPropJoueur[i]);
			}catch (ArrayIndexOutOfBoundsException e) {
				logger.warn(e.getMessage());
				//JOptionPane.showMessageDialog(null, "Entrez votre combinaison ", "Trop de clics", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		this.compare();
		
		int diff2 = (Integer.valueOf(coupJoue)) - combiSecret;
		if(diff2 == 0) {
			fin = true;
		}
		
		tourDeJeu++;
	}
	
	/**
	 * Méthode pour comparer la proposition du joueur avec la combinaison secrete
	 */
	public void compare() {
		int difference = 0;
		int compteurOK = 0;
		int compteurPresent = 0;
		
		//--Selon le jeu, on renvoie différents résultats
		if(jeu.equals(TypeJeu.RECHERCHE_NUM.toString())) {
			for(int i = 0; i<lgueurCombo; i++) {
				//System.out.println("decompo du coup joue "+Integer.valueOf(coupJoue.charAt(i)));
				//System.out.println("decompo de combo : "+constrCombiSecret[i]);
				logger.debug("decompo de combo : "+constrCombiSecret[i]);
				difference = constrCombiSecret[i] - listPropJoueur[i];
				//System.out.println(difference);
				logger.debug("resultat difference : "+difference);
				
				if (difference == 0) {
					resultCompa += "=";
				}
				else if (difference < 0 ) {
					resultCompa += "-";
				}
				else if(difference > 0) {
					resultCompa += "+";
				}
			}
			
		}
		else {
			for(int i = 0; i<lgueurCombo; i++) {
				//System.out.println("decompo de combo : "+constrCombiSecret[i]);
				logger.debug("decompo de combo : "+constrCombiSecret[i]);
				difference = constrCombiSecret[i] - listPropJoueur[i];
				//System.out.println(difference);
				logger.debug("Ctrl difference : "+difference);
				
				if (difference == 0) {
					compteurOK ++;
					compteurPresent ++;

				}
				else {
					for (int j = 0; j < lgueurCombo; j ++) {
						if (listPropJoueur[i] == constrCombiSecret[j]) {
							compteurPresent ++;
						}
					} 
				}
				
				resultCompa = "Reponse : "+compteurPresent+" présents - "+compteurOK+" bien placés";
				
			}
		}
	}	
}
