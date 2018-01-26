package joueur;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import Propriete.TypeJeu;

/**
 * Classe définissant le comportement du joueur electronique
 * @author nicolas
 *
 */
public class JoueurElectronique extends Joueur {
	private String str = "";
	
	private String combiJoueur;
	private char[] tabConstrCombiSecret;
	private Integer[] constrPropOrdi;
	private Integer[] constrRepOrdi;
	
	private int propOrdi = 0;
	
	private HashMap<Integer, ArrayList<Integer>> tabPool;
	private ArrayList<Integer> tabIntPool;
	
	/**
	 * Constructeur sans parametre
	 */
	public JoueurElectronique() {
		super();
		this.nom = "mon PC";
	}
	
	/**
	 * Constructeur avec parametres
	 * @param pCombo
	 * @param pJeu
	 */
	public JoueurElectronique(int pCombo, String pJeu, int pCouleur) {
		super(pCombo, pJeu, pCouleur);
		this.nom = "mon PC";
	}
	
	/**
	 * Méthode initialisant la combinaison que l'ordinateur doit découvrir
	 */
	public void initCombiSecret() {
		if (couleur == 0) {
			//Une boite de saisie ou l'on recup la combinaison que l'on decompose dans un tableau
			try {
				this.combiJoueur = JOptionPane.showInputDialog(null, "Veuillez saisir une combinaison de "+lgueurCombo+" chiffres.", "Combinaison secrète", JOptionPane.QUESTION_MESSAGE);
				this.combiSecret = Integer.valueOf(combiJoueur);
				logger.debug("ctrl saisie : "+combiJoueur+" - "+combiSecret);
			}catch (NumberFormatException e) {
				logger.error("Combinaison secrète non saisie");
				JOptionPane.showMessageDialog(null, "Veuillez saisir une combinaison de "+lgueurCombo+" chiffres.", "Attention Combinaison secrète", JOptionPane.WARNING_MESSAGE);
				initCombiSecret();
			}
		}
		else {
			//--une boite de saisie perso avec clavier couleur
			BoiteDialSaisieCouleur bdSaisie = new BoiteDialSaisieCouleur(null, "SAISIE COMBINAISON COULEUR", true);
			this.combiJoueur = bdSaisie.getCombinaison();
			this.combiSecret = Integer.valueOf(combiJoueur);
			logger.debug("ctrl saisie : "+combiJoueur+" - "+combiSecret);
			
		}


		this.tabConstrCombiSecret = this.combiJoueur.toCharArray();
		this.constrCombiSecret = new Integer[this.lgueurCombo];
		for (int i = 0; i<lgueurCombo; i++) {
			logger.debug("Ctrl combo :"+tabConstrCombiSecret[i]);
			this.constrCombiSecret[i] = Character.getNumericValue(tabConstrCombiSecret[i]);
		}
		
		// Une boite de dialogue pour informer la partie lancee
		String message = "Vous avez choisi une combinaison secrète. \n";
		message += "Votre adversaire joue.";
		JOptionPane.showMessageDialog(null, message, "Combinaison secrète prête !", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Méthode récupérant la proposition de l'ordi et qui la compare à la combinaison
	 */
	public void jeu(String pCoupJoue) {
		if(tourDeJeu == 0) {
			this.str = "";
			this.constrPropOrdi = new Integer[this.lgueurCombo];
			this.constrRepOrdi = new Integer[this.lgueurCombo];
			this.resultCompa = "";
			this.tabPool = new HashMap<Integer, ArrayList<Integer>>();
			this.tabIntPool = new ArrayList<Integer>();
			
			if (couleur == 0) {
				//--on initialise le tableau de pool d'entier compris entre 0 et 9
				for(int k = 0; k<10; k++)
					this.tabIntPool.add(k);
			}
			else {
				//--on initialise le tableau de pool d'entier compris entre 0 et 7
				for(int k = 0; k<8; k++)
					this.tabIntPool.add(k);
			}
				

			
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
		coupJoue = str;
		propOrdi = Integer.valueOf(str);
		//System.out.println("la proposition de l'odinateur : "+propOrdi);//--Controle
		logger.info("la proposition de l'odinateur : "+propOrdi);
		
		//--Il compare
		this.compare();
		tourDeJeu ++;
	}
	
	/**
	 * Méthode comparant la proposition de l'ordi à la combinaison secrete
	 */
	public void compare() {
		int difference = 0;
		int compteurOK = 0;
		int compteurPresent = 0;
		Boolean boolPresent = false;
		
		//--Selon le jeu, le résultat de la comparaison diffère
		if(jeu.equals(TypeJeu.RECHERCHE_NUM.toString())) {
			for(int i = 0; i<lgueurCombo; i++) {
				//System.out.println("decompo de combo : "+constrCombiSecret[i]);//--Controle
				logger.debug("decompo de combo : "+constrCombiSecret[i]);
				difference = constrCombiSecret[i] - constrPropOrdi[i];
				//System.out.println(difference);//--Controle
				logger.debug("Ctrl difference :"+difference);
				this.tabIntPool = new ArrayList<Integer>();
				//--Si la diférence est nulle, le chiffre est dans la combinaison secrete et on l'affecte dans la reponse de l'ordi
				if (difference == 0) {
					resultCompa += "=";
					constrRepOrdi[i] = constrPropOrdi[i];
				}
				//--Si la différence est negative on restreint l'intervalle de choix de l'ordi
				else if (difference < 0 ) {
					resultCompa += "-";
	
					for(int j = 0; j<constrPropOrdi[i]; j++) {
						tabIntPool.add(j);
						//System.out.println("ctrl pool construit ap comparaison <0: "+tabIntPool.get(j));//--Controle
						logger.debug("ctrl pool construit ap comparaison <0: "+tabIntPool.get(j));
					}
					for(int k = 0; k<tabIntPool.size(); k++)
						//System.out.println("ma var qui transporte le pool "+tabIntPool.get(k));//--Controle
						logger.debug("ma var qui transporte le pool "+tabIntPool.get(k));
					
					tabPool.put(i, tabIntPool);
					//System.out.println("un autre ctrl : "+tabPool.toString());//--Controle
					logger.debug("un autre ctrl : "+tabPool.toString()+" - "+tabPool.get(i).size());
				}
				//--Si la différence est positive on restreint aussi l'intervalle de choix de l'ordi
				else if(difference > 0) {
					resultCompa += "+";
	
					for(int j = 0; j<10-constrPropOrdi[i]; j++) {
						tabIntPool.add(j+constrPropOrdi[i]+1);
						//System.out.println("ctrl pool construit ap comparaison >0: "+tabIntPool.get(j));//--Controle
						logger.debug("ctrl pool construit ap comparaison >0: "+tabIntPool.get(j));
					}
					for(int k = 0; k<tabIntPool.size(); k++)
						//System.out.println("ma var qui transporte le pool "+tabIntPool.get(k));//--Controle
						logger.debug("ma var qui transporte le pool "+tabIntPool.get(k));
					
					tabPool.put(i, tabIntPool);
					//System.out.println("un autre ctrl : "+tabPool.toString() + " - "+tabPool.get(i).size());//--Controle
					logger.debug("un autre ctrl : "+tabPool.toString()+" - "+tabPool.get(i).size());
				}
			}
		}
		else {
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
		}
		
		int diff2 = propOrdi - combiSecret;
		if(diff2 == 0) {
			fin = true;
		}
	}
	
}
