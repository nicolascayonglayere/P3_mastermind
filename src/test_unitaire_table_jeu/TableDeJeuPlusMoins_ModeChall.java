package test_unitaire_table_jeu;

import java.awt.BorderLayout;
import java.text.ParseException;
import java.util.Random;

import javax.swing.JOptionPane;

import joueur.Joueur;

public class TableDeJeuPlusMoins_ModeChall extends TableDeJeu_Test {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int combiSecret = 0;
	private Integer[] constrCombiSecret;


	/**
	 * Constructeur sans paramètres
	 */
	public TableDeJeuPlusMoins_ModeChall() {
		super();
	}
	
	/**
	 * Constructeur avec parametre
	 * @param pEssai
	 * @param pCombo
	 */
	public TableDeJeuPlusMoins_ModeChall(Joueur pJoueur, int pEssai, int pCombo) {
		super(pJoueur, pEssai, pCombo);
	}
	
	public TableDeJeuPlusMoins_ModeChall(String pMode, int pEssai, int pCombo) {
		super(pMode, pEssai, pCombo);
	}
	
	/**
	 * méthode qui prépare la comparaison et qui retourne le résultat de cette comparaison
	 */
	public void jeu() {
		
		this.listPropJoueur = new Integer[this.lgueurCombo];
		resultCompa = "";
		coupJoue = listProp[tourDeJeu].getText();
		//System.out.println("la proposition du joueur : "+coupJoue);
		logger.debug("la proposition du joueur : "+coupJoue);
		char[] tabint = coupJoue.toCharArray();
		
		for (int i = 0; i<lgueurCombo; i++) {
			this.listPropJoueur[i] = Character.getNumericValue(tabint[i]);
			//System.out.println("la liste de prop du joueur : "+listPropJoueur[i]);
			logger.debug("la liste de prop du joueur : "+listPropJoueur[i]);
		}
		
		this.compare();
		listResult[tourDeJeu].setText(resultCompa);
		listProp[tourDeJeu + 1].setEditable(true);
		tourDeJeu ++;
		

	}
	
	/**
	 * Méthode générant la combinaison secrete
	 */
	public void initCombiSecret() {
		this.constrCombiSecret = new Integer[this.lgueurCombo];
		Random alea = new Random();
		String str = "";
				
		for (int i = 0; i<lgueurCombo; i++) {
			//--on tire 1 chiffre au hasard
			constrCombiSecret[i] = alea.nextInt(10);
			str += String.valueOf(constrCombiSecret[i]);//--on concatene les différents chiffres 
		}
		this.combiSecret = Integer.valueOf(str);
		//System.out.println("la combo gagnante : "+this.combiSecret);//--Controle
		logger.warn("la combo gagnante : "+this.combiSecret);
		
		JOptionPane jop = new JOptionPane();
		String message = "La combinaison secrète est prête \n";
		message += "A vous de jouer";
		jop.showMessageDialog(null, message, "Combinaison secrète prête !", JOptionPane.INFORMATION_MESSAGE);
		listProp[0].setEditable(true);
	}
	
	/**
	 * méthode comparant la prop du joueur avec la combinaison secrete
	 */
	public void compare() {
		int difference = 0;
		
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
		
		int diff2 = (Integer.valueOf(coupJoue)) - combiSecret;
		//--Victoire
		if(diff2 == 0) {
			JOptionPane jop = new JOptionPane();
			int option = jop.showConfirmDialog(null, "Félicitation, vous avez trouvé la combinaison secrète ! \n Voulez-vous rejouer ?",
							"Victoire", 
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
			if (option == JOptionPane.OK_OPTION) {
				nouvellePartie();
			}				
		}
		
		//--Defaite
		if(tourDeJeu+1 == nbCoupsConfig && diff2 !=0) {
			JOptionPane jop = new JOptionPane();
			int option = jop.showConfirmDialog(null, "C'est perdu ! \n La combinaison gagnante est "+combiSecret+"\n Voulez-vous rejouer ?",
							"Défaite", 
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
			if (option == JOptionPane.OK_OPTION) {
				nouvellePartie();
			}
		}
	}
			
}
