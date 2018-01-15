package test_unitaire_table_jeu;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class TableDeJeuMasterMind_ModeDef extends TableDeJeu_Test {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int combiSecret;
	private char[] tabConstrCombiSecret;
	private Integer[] constrCombiSecret;
	
	private Integer[] constrPropOrdi;
	private Integer[] constrRepOrdi;
	private String str = "";
	private int propOrdi = 0;
	
	private HashMap<Integer, ArrayList<Integer>> tabPool;
	private ArrayList<Integer> tabIntPool;
	
	private int compteurPresent, compteurOK;
	/**
	 * Constructeur sans parametres
	 */
	public TableDeJeuMasterMind_ModeDef() {
		super();
	}
	
	public TableDeJeuMasterMind_ModeDef(String pMode, int pEssai, int pCombo) {
		super (pMode, pEssai, pCombo);
	}
	
	
	public void initCombiSecret() {
		//--Une boite de saisie ou l'on recup la combinaison que l'on decompose dans un tableau
		JOptionPane jop0 = new JOptionPane();
		String combiJoueur = jop0.showInputDialog(null, "Veuillez saisir une combinaison de "+lgueurCombo+" chiffres.", "Combinaison secrète", JOptionPane.QUESTION_MESSAGE);
		this.combiSecret = Integer.valueOf(combiJoueur);
		this.tabConstrCombiSecret = combiJoueur.toCharArray();
		this.constrCombiSecret = new Integer[this.lgueurCombo];
		for (int i = 0; i<lgueurCombo; i++) {
			//System.out.println("Ctrl combo :"+tabConstrCombiSecret[i]);//--Controle
			logger.debug("Ctrl combo :"+tabConstrCombiSecret[i]);
			this.constrCombiSecret[i] = Character.getNumericValue(tabConstrCombiSecret[i]);
		}
		
		//-- Une boite de dialogue pour informer la partie lancee
		JOptionPane jop1 = new JOptionPane();
		String message = "Vous avez choisi une combinaison secrète. \n";
		message += "Votre adversaire joue.";
		jop1.showMessageDialog(null, message, "Combinaison secrète prête !", JOptionPane.INFORMATION_MESSAGE);
		
		//System.out.println("la combo gagnante : "+this.combiSecret);//--Controle
		logger.warn("la combo gagnante : "+this.combiSecret);
	}
	
	public void jeu() {

		if(tourDeJeu == 0) {
			this.constrPropOrdi = new Integer[this.lgueurCombo];
			this.constrRepOrdi = new Integer[this.lgueurCombo];
			this.resultCompa = "";
			this.tabPool = new HashMap<Integer, ArrayList<Integer>>();
			this.tabIntPool = new ArrayList<Integer>();
									
			//--on initialise le tableau de pool d'entier compris entre 0 et 9
			for(int k = 0; k<10; k++)
				this.tabIntPool.add(k);
			
			//--l'ordi construit sa prop à partir du pool de nombre
			for(int i = 0; i<lgueurCombo; i++) {
				
				//--on remplit le tableau de reponse de -1
				constrRepOrdi[i] = -1;
				//--on construit la prop de l'ordi a partir du pool de nombre : 0123
				constrPropOrdi[i] = tabIntPool.get(i);
				str += String.valueOf(constrPropOrdi[i]);
			}
			
		}
		else {
			//--on réinitialise la prop de l'ordi ainsi que le tableau contenant celle-ci
			this.constrPropOrdi = new Integer[this.lgueurCombo];
			propOrdi = 0;
			resultCompa = "";
			str = "";
			//--l'ordi construit sa prop à partir du pool de nombre s'il n'a pas trouvé le chiffre
			for(int i = 0; i<lgueurCombo; i++) {
				if (constrRepOrdi[i] != -1) {
					constrPropOrdi[i] = constrRepOrdi[i];
					str += String.valueOf(constrPropOrdi[i]);
				}
				else {
					tabIntPool = tabPool.get(i);
					//System.out.println("taille pool : "+tabIntPool.size());//--Controle
					logger.debug("taille pool : "+tabIntPool.size());
					for (int j = 0; j<tabIntPool.size(); j++) {//--Controle
						//System.out.println("ctrl1 pool : "+tabIntPool.get(j));//--Controle
						logger.debug("ctrl1 pool : "+tabIntPool.get(j));
					}//--Controle
					constrPropOrdi[i] = tabIntPool.get(0);
					str += String.valueOf(constrPropOrdi[i]);
				}
			}
		}
		propOrdi = Integer.valueOf(str);
		//System.out.println("la proposition de l'odinateur : "+propOrdi);//--Controle
		logger.info("la proposition de l'odinateur : "+propOrdi);
		//--Il la propose en l'affichant ds la zone de texte
		listProp[tourDeJeu].setText(str);
		//--Il compare
		this.compare();
		
		//--affichage du résultat
		listResult[tourDeJeu].setText(resultCompa);
		listProp[tourDeJeu + 1].setEditable(true);
		tourDeJeu ++;
	}
	
	/**
	 * Methode pour comparer la proposition du joueur avec la combinaison secrete
	 */
	public void compare() {
		int difference = 0;
		compteurOK = 0;
		compteurPresent = 0;
		Boolean boolPresent = false;
		
		for(int i = 0; i<lgueurCombo; i++) {
			//System.out.println("decompo de combo : "+constrCombiSecret[i]);//--Controle
			logger.debug("decompo de combo : "+constrCombiSecret[i]);
			difference = constrCombiSecret[i] - constrPropOrdi[i];
			//System.out.println(difference);//--Controle
			logger.debug("Ctrl difference : "+difference);

			//--Si la difference est nulle, la prop est correcte et on construit la reponse de l'ordi
			if (difference == 0) {
				compteurOK ++;
				compteurPresent ++;
				constrRepOrdi[i] = constrPropOrdi[i];

			}
			//--Sinon, on vérifie la présence de la prop de l'ordi dans la combinaison secrete, on incremente le compteur de presence et on chge le booleen
			//--le cas echeant
			else {
				for (int j = 0; j < lgueurCombo; j ++) {
					if (constrPropOrdi[i] == constrCombiSecret[j] && constrPropOrdi[i]!= constrRepOrdi[j]) {
						compteurPresent ++;
						boolPresent = true;
						break;
					}
					else {
						boolPresent = false ;
					}
				}	
				//System.out.println("mon booleen Present :" +boolPresent);//--Controle
				logger.debug("mon booleen Present :" +boolPresent);
				//--Si la prop est fausse, on la retire du pool d'entier
				if(boolPresent == false) {
					tabIntPool.remove(constrPropOrdi[i]);
				}
				for(int k = 0; k<tabIntPool.size(); k++)
					//System.out.println("ma var qui transporte le pool "+tabIntPool.get(k));//--Controle
					logger.debug("ma var qui transporte le pool "+tabIntPool.get(k));


				//--on met le pool dans le tableau des pool
				tabPool.put(i, tabIntPool);	
				//System.out.println("un autre ctrl : "+tabPool.toString());//--Controle
				logger.debug("un autre ctrl : "+tabPool.toString());
			}
		}
		
		//--On affiche le resultat de la proposition
		resultCompa = "Reponse : "+compteurPresent+" présents - "+compteurOK+" bien placés";
		
		int diff2 = propOrdi - combiSecret;
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
