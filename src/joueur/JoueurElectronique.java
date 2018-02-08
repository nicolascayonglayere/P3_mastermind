package joueur;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JOptionPane;

import Propriete.TypeJeu;
import clavier.BoiteDialSaisieCouleur;

/**
 * Classe définissant le comportement du joueur electronique
 * @author nicolas
 *
 */
public class JoueurElectronique extends Joueur {
	private String str = "";
	
	private String combiJoueur;
	private char[] tabConstrCombiSecret;
	private Integer[] constrPropOrdi= new Integer[this.lgueurCombo];
	private Integer[] constrRepOrdi;
	
	private int propOrdi = 0;
	
	private HashMap<Integer, ArrayList<Integer>> tabPool;
	private ArrayList<Integer> tabIntPool;
	
	private ArrayList<Integer> poolNbPropOrdi = new ArrayList<Integer>();;
	
	private ArrayList<Integer[]> ttePropPossible; 
	
	private Integer[] prop, prop0;
	private Integer scProp0 = 0;
	
	private Random alea = new Random();
	private int nbTotProp;
	
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
		
		//--on initialise le pool de nombre autorisé
		if(jeu.equals(TypeJeu.MASTERMIND.toString())) {
				if(this.couleur == 0) {
					//--on intialise le pool de nombre, ici de 0 à 9
					for (int i = 0; i <10; i++)
						this.poolNbPropOrdi.add(i);	
				}
				else {
					//--on intialise le pool de nombre, ici de 0 à 7
					for (int i = 0; i <8; i++) {
						this.poolNbPropOrdi.add(i);
					}
				}
				
				nbTotProp = (int) Math.pow(this.poolNbPropOrdi.size(), this.lgueurCombo);
		}
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

		//--on récupère la combinaison secrete que l'on met dans une liste
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
	 * Methode de calcul d'un nombre decimal pNombre en base pBase
	 * @param pNbre
	 * @param pBase
	 * @return
	 */
	public int nbBase(int pNbre, int pBase) {
		int q = pNbre/pBase;
		int x = pNbre%pBase;
		int p = 1;
		while(q != 0) {
			p = 10*p;
			x = x + 10*(q%pBase);
			q = q/pBase;
		}
		
		return x;
	}
	
	
	
	/**
	 * Methode qui construit la proposition de l'ordinateur, renvoie le resultat de la comparaison avec la combinaison secrete et passe au tour suivant
	 * JEU Mastermind
	 * @param pCoupJoue 
	 */
	public void jeu(String pCoupJoue) {
		Integer[] propPoss = new Integer[this.lgueurCombo];
		ArrayList<Integer[]> supprTtePropPossible = new ArrayList<Integer[]>();
		this.paramObs = new ArrayList<String>();
		propOrdi = 0;
		str = "";
		int controle = 0;
		controle ++;
		logger.debug("on joue plein de fois : "+controle);
		//this.poolNbPropOrdi = new ArrayList<Integer>();

		this.resultCompa = "";
		
		if(jeu.equals(TypeJeu.MASTERMIND.toString())) {
			//--Au premier tour il prend un nb au hasard dans la liste des prop possibles
			if(tourDeJeu == 0) {
				//--on initialise les listes des possibles et des propositions
				this.ttePropPossible= new ArrayList<Integer[]>();
				this.prop = new Integer[this.lgueurCombo];
				this.prop0 = new Integer[this.lgueurCombo];
			
				//--on initialise la liste de toutes les combinaisons possibles qui sont chaque nombre entre 0 et le nb total de proposition
				int k = 0;
				//--on formate les nombres en ajoutant des 0 devant
				String str0 = "0";
				for(int i = 0; i<this.lgueurCombo - 1; i++) {
					str0 +="0";
				}
			
				//--on cree la premiere prop possible 0000
				DecimalFormat nf = new DecimalFormat(str0);
				char[]tabInt2 = str0.toCharArray();
				Integer[] possIni = new Integer[this.lgueurCombo];
				for (int i =0; i<this.lgueurCombo; i++) {
					possIni[i]=Character.getNumericValue(tabInt2[i]);
				}
				ttePropPossible.add(possIni);
			
				//--on remplit notre tableau de toutes les propositions possibles
				while(ttePropPossible.size() < nbTotProp) {
					//logger.debug("notre nb "+k);
					String str1 = String.valueOf(k);
					str1 = nf.format(Integer.valueOf(str1));
					
					//--on cree la prop à partir du nombre formate
					Integer[] propConstr = new Integer[this.lgueurCombo];
					for (int j = 0; j<this.lgueurCombo; j++) {
						char [] tabint = str1.toCharArray();
						propConstr[j] = Character.getNumericValue(tabint[j]);
						//logger.debug("val de liste possible : "+propConstr[j]);//--Controle
					}
					
					//--on controle le nombre et on l'ajoute le cas echecant
					boolean cont = false;
					for(int x = 0; x<this.lgueurCombo; x ++) {
						if(propConstr[x]<this.poolNbPropOrdi.size()) {
							cont = true;					
						}
						else {
							cont = false;
							break;
						}
					}
					//logger.debug(cont);
					boolean ajout = false;
					if(cont) {
						for (Integer[] prop : ttePropPossible) {
							if (propConstr == prop)
								ajout = false;
							else
								ajout = true;
						}
					}
					//logger.debug("on ajoute : "+ajout);
					if(ajout == true)
						ttePropPossible.add(propConstr);
					
					//--on incremente le nombre
					k++;
				}
				logger.debug("taille de liste des possibles creee "+ttePropPossible.size());//--Controle
				prop0 = ttePropPossible.get(alea.nextInt(ttePropPossible.size()));
		}
			
		//--Les tours suivants, on se base sur le score relatif(score calc avec la prop précédente co liste de comparaison) pour determiner la proposition de l'ordi
		//--on remove de la liste des possibles toutes les combinaisons qui ne tiennent pas la comparaison et on prend la première de la nouvelle liste des possibles comme proposition de l'ordi
		else {
			prop = prop0;
		
			for (int i = 0 ; i<ttePropPossible.size(); i++) {
		
				propPoss = ttePropPossible.get(i);
				int scRelPropPoss = calcScore(propPoss, prop);
				//for(int k : propPoss)
					//logger.debug("detail prop comparee" + k);//--Controle
				
				//logger.debug("comp score rel :"+ scProp0+ " - "+calcScore(propPoss, prop));//--Controle
				
				if(scRelPropPoss != scProp0) {
					supprTtePropPossible.add(propPoss);	
				}
			}
			logger.debug("taille liste suppr : "+supprTtePropPossible.size());//--Controle
			
			for(Integer[] i : supprTtePropPossible) {
				ttePropPossible.remove(i);
			}
			logger.debug("taille des nvelles listes "+ttePropPossible.size());//--Controle
			
			prop0 = ttePropPossible.get(0);
		}
			
		//--on construit la proposition de l'ordi	
		for(int i = 0; i<this.lgueurCombo; i++) {			
			constrPropOrdi[i] = prop0[i];
			str += String.valueOf(constrPropOrdi[i]);			
		}
		logger.debug("propo ordi : " +str);
		coupJoue = str;
		scProp0 = calcScore(prop0, constrCombiSecret);
		
		propOrdi = Integer.valueOf(str);
		logger.info("la proposition de l'odinateur : "+propOrdi+" et son score "+scProp0);

		//--on verifie si c gagne
		int diff2 = propOrdi - combiSecret;
		if(diff2 == 0) {
			fin = true;
		}
		this.paramObs.add(String.valueOf(tourDeJeu));//a essayer
		this.paramObs.add(str);
		this.paramObs.add(resultCompa);
		this.paramObs.add(Boolean.toString(fin));
		this.updateObservateur();//a essayer updateObservateur();
		tourDeJeu ++;
		propOrdi = 0;
		str = "";
		}
	else if(jeu.equals(TypeJeu.RECHERCHE_NUM.toString()))
			jeu1(pCoupJoue);
	}
	
	/**
	 * Methode calculant le score d'une liste proposée par rapport à une liste de comparaison
	 * @param pconstrPropOrdi
	 * @param plistCompa
	 * @return le score de la porposition
	 */
	public int calcScore(Integer[]pconstrPropOrdi, Integer[]plistCompa) {
		int scProp = 0;
		int difference = 0;
		int compteurOK = 0;
		int compteurPresent = 0;
		
		int compteurOccurCombo = 0;//--le nb de fois ou la val apparait dans la combinaison secrete
		int compteurOccurProp = 0;//--le nb de fois ou la val apparait dans la proposition
	
		for(int i = 0; i<lgueurCombo; i++) {
			//logger.debug("decompo de liste comparee : "+plistCompa[i]);
			//logger.debug("decompo prop ordi : "+pconstrPropOrdi[i]);
			difference = plistCompa[i] - pconstrPropOrdi[i];
			//logger.debug("Ctrl difference : "+difference);
			if(difference == 0) {
				compteurOK ++;
			}
		}
		compteurPresent = - compteurOK;
		
		for(int j = 0; j<this.poolNbPropOrdi.size()-1; j++) {
			compteurOccurCombo = 0;
			compteurOccurProp = 0;
			for(int i =0; i<lgueurCombo; i++) {
				if (plistCompa[i] == j)
					compteurOccurCombo++;
				if(pconstrPropOrdi[i] == j)
					compteurOccurProp++;
			}
			if(compteurOccurCombo < compteurOccurProp)
				compteurPresent = compteurPresent + compteurOccurCombo;
			else
				compteurPresent = compteurPresent + compteurOccurProp;
		}
		scProp = 10*compteurOK + compteurPresent;

		//--Le résultat de la comparaison apres calcul du score
		resultCompa = "Reponse : "+compteurPresent+" présents - "+compteurOK+" bien placés";
		return scProp;
	}



	
	/**
	 * Méthode récupérant la proposition de l'ordi et qui la compare à la combinaison
	 * JEU recherche +/-
	 */
		public void jeu1(String pCoupJoue) {
		if(tourDeJeu == 0) {
			this.str = "";
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
		
		this.paramObs.add(String.valueOf(tourDeJeu));//a essayer
		this.paramObs.add(str);
		this.paramObs.add(resultCompa);
		this.paramObs.add(Boolean.toString(fin));
		this.updateObservateur();//a essayer updateObservateur();
		
		tourDeJeu ++;
	}
	
	/**
	 * Méthode comparant la proposition de l'ordi à la combinaison secrete et construisant le pool avec lequel l'ordi construira sa prochaine prop
	 */
		public void compare() {
		int difference = 0;

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
				
		int diff2 = propOrdi - combiSecret;
		if(diff2 == 0) {
			fin = true;
		}
	}
	
}
