package test_unitaire_table_jeu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

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
	private Integer[][] constrPropOrdi1 = new Integer[this.lgueurCombo][this.nbCoupsConfig];
	private ArrayList<Integer> poolNbPropOrdi = new ArrayList<Integer>();
	private ArrayList<Integer> poolRepOrdi= new ArrayList<Integer>();
	//private Integer[]poolRepOrdi = new Integer[this.lgueurCombo];
	private Integer[] constrRepOrdi;
	private String str = "";
	private int propOrdi = 0;
	
	private HashMap<Integer, ArrayList<Integer>> tabPool;
	private ArrayList<Integer> tabIntPool;
	
	private int compteurPresent, compteurOK;
	
	private Random alea = new Random();
	
	//-----------------------------------
	private ArrayList<Integer[]> ttePropPossible = new ArrayList<Integer[]>();
	private ArrayList<Integer> scoreTtePropPossible = new ArrayList<Integer>();
	//private HashMap<Integer[], Integer> ttePropPossible = new HashMap<Integer[], Integer>();
	private Integer[] prop, prop0;
	private Integer scProp, scProp0 = 0;
	
	
	/**
	 * Constructeur sans parametres
	 */
	public TableDeJeuMasterMind_ModeDef() {
		super();
	}
	
	public TableDeJeuMasterMind_ModeDef(String pMode, int pEssai, int pCombo) {
		super (pMode, pEssai, pCombo);
				
		for (int i = 0; i <10; i++)
			this.poolNbPropOrdi.add(i);	
		this.constrRepOrdi = new Integer[this.lgueurCombo];
		for(int i = 0; i<lgueurCombo; i++) {				
			//--on remplit le tableau de reponse de -1
			this.constrRepOrdi[i] = -1;
			//this.poolRepOrdi[i] = -1;
		}
	//this.prop = new Integer[this.lgueurCombo];
	//for (int i = 0; i<poolNbPropOrdi.size(); i ++) {
	//	for(int j = 0; j<this.lgueurCombo; j++) {
	//		prop[j] = i;
	//	}
	//	ttePropPossible.add(prop);
	//	scoreTtePropPossible.add(calcScore(prop));
	//	//ttePropPossible.put(prop, calcScore(prop));
	//}
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
	
	/**
	 * Methode jeu1, strategie humaine.
	 */
	public void jeu1() {

		compteurPresent = 0;
		compteurOK = 0;
		//Integer[] key = new Integer[ttePropPossible.size()];
		this.resultCompa = "";
		int nbTotProp = 1;
	//Iterator<Entry<Integer[], Integer>> iterator = ttePropPossible.entrySet().iterator();
	//while (iterator.hasNext()) {
	//	Entry<Integer[], Integer> entry = iterator.next();
	//	key =  (Integer[]) entry.getKey();
	//	Integer value = (Integer) entry.getValue();
	//
	//	logger.debug(key + " = " + value);
	//	
	//}
	//prop = key;
		//prop = ttePropPossible.get(alea.nextInt(ttePropPossible.size()));
		for(int i = 0; i < this.lgueurCombo; i ++) {
			nbTotProp *= poolNbPropOrdi.size();
		}

		this.prop = new Integer[this.lgueurCombo];
		this.prop0 = new Integer[this.lgueurCombo];
		

		for (int i = 0; i<nbTotProp; i++) {
			String str = String.valueOf(i);
			if (str.length()<this.lgueurCombo) {
				DecimalFormat nf = new DecimalFormat("0000");
				str = nf.format(Integer.valueOf(str));
				for (int j = 0; j<this.lgueurCombo; j++) {
					char [] tabint = str.toCharArray();
					prop[j] = Character.getNumericValue(tabint[j]);
				}
				ttePropPossible.add(prop);
			}
			else {
				for (int j = 0; j<this.lgueurCombo; j++) {
					char [] tabint = str.toCharArray();
					prop[j] = Character.getNumericValue(tabint[j]);
				}
				ttePropPossible.add(prop);
			}
			
		}


		logger.debug("taille des listes creees "+ttePropPossible.size()+" - "+scoreTtePropPossible.size());
		
		prop0 = ttePropPossible.get(0);
		//prop0 = ttePropPossible.get(alea.nextInt(ttePropPossible.size()));
		for(int i = 0; i<this.lgueurCombo; i++) {
			
			constrPropOrdi1[i][tourDeJeu] = prop0[i];
			str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
			
		}
		logger.debug("propo ordi : " +str);
		scProp0 = calcScore(prop0, constrCombiSecret);
		
		
		
		
		

//		int nbDep = 0;
	//	this.constrPropOrdi1 = new Integer[this.lgueurCombo][this.nbCoupsConfig];
	//	this.resultCompa = "";
		//this.poolRepOrdi = new ArrayList<Integer>();
		//this.poolNbPropOrdi = new ArrayList<Integer>();
	//this.constrRepOrdi = new Integer[this.lgueurCombo];
	////this.propOrdi = 0;
	//for (int i = 0; i <10; i++)
	//	this.poolNbPropOrdi.add(i);	
	//for(int i = 0; i<lgueurCombo; i++) 				
	//	//--on remplit le tableau de reponse de -1
	//	constrRepOrdi[i] = -1;
	
		//tant que l'on a pas trouve les 4 val de la combinaison, on les cherche 
		//if(this.poolRepOrdi.size() < this.lgueurCombo) {
		//	this.constrPropOrdi1 = new Integer[this.lgueurCombo][this.nbCoupsConfig];
		//	this.resultCompa = "";
		//nbDep = poolNbPropOrdi.get(alea.nextInt(poolNbPropOrdi.size()));
		//
		//	
			//--1er tour et tant que l'on a pas trouve 1 val de la combinaison
		//if (poolRepOrdi.size() == 0) {
		//	for (int i = 0; i<this.lgueurCombo; i++) {
		//		//--on construit la prop de l'ordi avec un seul nombre tiré au hasard
		//		constrPropOrdi1[i][tourDeJeu] = nbDep;
		//		str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
		//		logger.debug("constr prop ordi 1er tour : "+str);
		//	}
		//}		
		////--des qu'on a 1 val de la combinaison on l'utilise pour construire la prop de l'ordi
		//else if (poolRepOrdi.size() !=0 ){
		//	for (int i = 0; i<this.poolRepOrdi.size(); i++) {
		//			logger.debug("le contenu du pool de reponse : "+poolRepOrdi.get(i));
		//			constrPropOrdi1[i][tourDeJeu] = poolRepOrdi.get(i);
		//			str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
		//			logger.debug("constr prop ordi poolRep : "+str);
		//	}
		//	for(int i = this.poolRepOrdi.size();i<this.lgueurCombo; i++) {
		//		//--on complete la prop avec une val aleatoire possible
		//		constrPropOrdi1[i][tourDeJeu] = nbDep;
		//		str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
		//		logger.debug("constr prop ordi completion poolRep: "+str);

					
				//
				////--on vérifie qu'il n'y a pas de bonne réponse
				//if(constrRepOrdi[i] == -1) {
				//	for(int j = 0; j<poolRepOrdi.size(); j++) {
				//		logger.debug("le contenu du pool de reponse : "+poolRepOrdi.get(j));
				//		//--on vérifie de jouer a un endroit different du tour precedent
				//		
				//		constrPropOrdi1[i][tourDeJeu] = poolRepOrdi.get(j);
				//		str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
				//		logger.debug("constr prop ordi poolRep : "+str);
				//	}
				//	for(int j = poolRepOrdi.size(); j<this.lgueurCombo; j++) {
				//		//--on complete la prop avec une val aleatoire possible
				//		constrPropOrdi1[j][tourDeJeu] = nbDep;
				//		str += String.valueOf(constrPropOrdi1[j][tourDeJeu]);
				//		logger.debug("constr prop ordi completion poolRep: "+str);	
				//	}
				//	
				//}
				//else {
				//	for(int k : constrRepOrdi) {
				//		logger.debug("le contenu du pool de reponse : "+constrRepOrdi[i]);
				//		constrPropOrdi1[i][tourDeJeu] = constrRepOrdi[i];
				//		str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
				//		logger.debug("constr prop ordi reponse : "+str);
				//	}
				//}
				////--on complete la prop avec une val aleatoire possible
				//constrPropOrdi1[i][tourDeJeu] = nbDep;
				//str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
				//logger.debug("constr prop ordi completion poolRep: "+str);	
			//	}
		//		
		//		
		//	}
		//		//--on complete la prop avec une val aleatoire possible
					//constrPropOrdi1[i][tourDeJeu] = nbDep;
		//			str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
		//			logger.debug("constr prop ordi completion poolRep: "+str);
		
		
		//		//--si on a la bonne val on la met dans la proposition
		//		else if (constrRepOrdi[i] != -1){
		//			constrPropOrdi1[i][tourDeJeu] = constrRepOrdi[i];
		//			str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
		//			logger.debug("constr prop ordi reponse : "+str);
		//		}
		//		//--lorsqu'on a 1 val de la combinaison, on la met dans la prop que l'on complete avec une val aleatoire possible
		//		else if(poolRepOrdi.size() !=0 && poolRepOrdi.size() < i+2 ){
		//			//for(int j = 0; j<poolRepOrdi.size(); j++) {
		//				logger.debug("le contenu du pool de reponse : "+poolRepOrdi.get(i));
		//				constrPropOrdi1[i][tourDeJeu] = poolRepOrdi.get(i);
		//				str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
		//				logger.debug("constr prop ordi poolRep : "+str);
		//			//}
		//			//for(int j = 0; j<(this.lgueurCombo-poolRepOrdi.size()); j++) {
		//			//constrPropOrdi1[i][tourDeJeu] = nbDep;
		//			//str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
		//			//logger.debug("constr prop ordi completion poolRep: "+str);
		//			//}
		//		}
		//		//--on complete la prop avec une val aleatoire possible
		//		else {
		//			constrPropOrdi1[i][tourDeJeu] = nbDep;
		//			str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
		//			logger.debug("constr prop ordi completion poolRep: "+str);
		//		}
		//	}
		//}
	//nbDep = poolNbPropOrdi.get(alea.nextInt(poolNbPropOrdi.size()));
	//for(int j = 0; j<this.poolRepOrdi.length; j++) {
	//	//--1er tour et tant que l'on a pas trouve 1 val de la combinaison
	//	if(this.poolRepOrdi[j] == -1) {
	//		//--on construit la prop de l'ordi avec un seul nombre tiré au hasard
	//		constrPropOrdi1[j][tourDeJeu] = nbDep;
	//		str += String.valueOf(constrPropOrdi1[j][tourDeJeu]);
	//		logger.debug("constr prop ordi 1er tour : "+str);	
	//	}
	//	else {
	//		//--lorsqu'on a 1 val de la combinaison, on la met dans la prop que l'on complete avec une val aleatoire possible
	//		logger.debug("le contenu du pool de reponse : "+poolRepOrdi[j]);
	//		constrPropOrdi1[j][tourDeJeu] = poolRepOrdi[j];
	//		str += String.valueOf(constrPropOrdi1[j][tourDeJeu]);
	//		logger.debug("constr prop ordi poolRep : "+str);
	//	}
	//}
		
		
	////--1er tour et tant que l'on a pas trouve 1 val de la combinaison
	//if(this.poolRepOrdi.size() == 0) {
	//	nbDep = poolNbPropOrdi.get(alea.nextInt(poolNbPropOrdi.size()));
	//	for (int i = 0; i<this.lgueurCombo; i++) {
	//		//--on construit la prop de l'ordi avec un seul nombre tiré au hasard
	//		constrPropOrdi1[i][tourDeJeu] = nbDep;
	//		str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
	//		logger.debug("constr prop ordi 1er tour : "+str);	
	//	}
	//}
	////--lorsqu'on a 1 val de la combinaison, on la met dans la prop que l'on complete avec une val aleatoire possible
	//else if(0 < this.poolRepOrdi.size() && this.poolRepOrdi.size()<this.lgueurCombo) {
	//	for (int i = 0; i< this.poolRepOrdi.size(); i++) {
	//		logger.debug("le contenu du pool de reponse : "+poolRepOrdi.get(i));
	//		constrPropOrdi1[i][tourDeJeu] = poolRepOrdi.get(i);
	//		str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
	//		logger.debug("constr prop ordi poolRep : "+str);
	//	}
	//	//--On complete avec une val aleatoire possible
	//	nbDep = poolNbPropOrdi.get(alea.nextInt(poolNbPropOrdi.size()));
	//	for(int i = this.poolRepOrdi.size(); i<this.lgueurCombo; i++) {
	//		constrPropOrdi1[i][tourDeJeu] = nbDep;
	//		str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
	//		logger.debug("constr prop ordi completion poolRep: "+str);
	//	}
	//}
	//
	//
	//else if(this.poolRepOrdi.size() == this.lgueurCombo) {
	//	//this.constrPropOrdi1 = new Integer[this.lgueurCombo][this.nbCoupsConfig];
	//	this.resultCompa = "";
	//	
	//	for(int i = 0; i<this.lgueurCombo; i++) {
	//		constrPropOrdi1[i][tourDeJeu] = this.poolRepOrdi.get(i);
	//		str += String.valueOf(constrPropOrdi1[i][tourDeJeu]);
	//		logger.debug("constr prop ordi 2: "+str);
	//	}
		//}
		
		propOrdi = Integer.valueOf(str);
		//System.out.println("la proposition de l'odinateur : "+propOrdi);//--Controle
		logger.info("la proposition de l'odinateur : "+propOrdi);
		//--Il la propose en l'affichant ds la zone de texte
		listProp[tourDeJeu].setText(str);
		//--Il compare
		//this.compare1();
		this.compare2();
		
		//--affichage du résultat
		listResult[tourDeJeu].setText(resultCompa);
		listProp[tourDeJeu + 1].setEditable(true);
		tourDeJeu ++;
		propOrdi = 0;
		str = "";
	}
	
	public int calcScore(Integer[]pconstrPropOrdi, Integer[]plistCompa) {
		int difference = 0;
		compteurOK = 0;
		
		int compteurOccurCombo = 0;
		int compteurOccurProp = 0;
	
		//--on calcule le score
		for(int i = 0; i<lgueurCombo; i++) {
			//System.out.println("decompo de combo : "+constrCombiSecret[i]);//--Controle
			logger.debug("decompo de liste comparee : "+plistCompa[i]);
			logger.debug("decompo prop ordi : "+pconstrPropOrdi[i]);
			difference = plistCompa[i] - pconstrPropOrdi[i];
			//System.out.println(difference);//--Controle
			logger.debug("Ctrl difference : "+difference);
			if(difference == 0) {
				compteurOK ++;
			}
		}
		compteurPresent = - compteurOK;
		
		for(int j = 0; j<10-1; j++) {
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
	
		logger.debug("le score : "+scProp+" de la prop " +pconstrPropOrdi.toString());
		return scProp;
	}
	
	public void calcScore() {
		int difference = 0;
		compteurOK = 0;
		int compteurOccurCombo = 0;
		int compteurOccurProp = 0;
	
		//--on calcule le score
		for(int i = 0; i<lgueurCombo; i++) {
			//System.out.println("decompo de combo : "+constrCombiSecret[i]);//--Controle
			logger.debug("decompo de combo : "+constrCombiSecret[i]);
			logger.debug("decompo prop ordi : "+constrPropOrdi1[i][tourDeJeu]);
			difference = constrCombiSecret[i] - constrPropOrdi1[i][tourDeJeu];
			//System.out.println(difference);//--Controle
			logger.debug("Ctrl difference : "+difference);
			if(difference == 0) {
				compteurOK ++;
			}
		}
		compteurPresent = - compteurOK;
		
		for(int j = 0; j<10-1; j++) {
			for(int i =0; i<lgueurCombo; i++) {
				if (constrCombiSecret[i] == j)
					compteurOccurCombo++;
				if(constrPropOrdi1[i][tourDeJeu] == j)
					compteurOccurProp++;
			}
			if(compteurOccurCombo < compteurOccurProp)
				compteurPresent = compteurPresent + compteurOccurCombo;
			else
				compteurPresent = compteurPresent + compteurOccurProp;
		}
		scProp = 10*compteurOK + compteurPresent;	
			
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
	
	public void compare2() {
		ArrayList<Integer> supprTtePropPossible = new ArrayList<Integer>();
		Integer[] constrPropOrdi = new Integer[this.lgueurCombo];
		int difference = 0;
		compteurOK = 0;
		this.poolNbPropOrdi = new ArrayList<Integer>();
		for (int i = 0; i<constrPropOrdi.length; i++) {
			constrPropOrdi[i] = constrPropOrdi1[i][tourDeJeu];
			logger.debug("la taille de la propOrdi : "+constrPropOrdi.length+" - son contenu :"+constrPropOrdi[i]);
		}
		
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
		
		for (int i = 0 ; i<ttePropPossible.size(); i++) {//for(Integer[] l : ttePropPossible) {
			Integer[] propPoss = new Integer[this.lgueurCombo];
			propPoss = ttePropPossible.get(i);
			for(int k : propPoss)
				logger.debug("detail prop comparee" + k);
			
			if(scProp0 != calcScore(propPoss, constrPropOrdi)) {
				supprTtePropPossible.add(i); 
			}
		}
		logger.debug("taille des listes "+ttePropPossible.size()+" - "+scoreTtePropPossible.size()+" - listSuppr : "+supprTtePropPossible.size());
		for(int i = 0; i <supprTtePropPossible.size(); i++) {
			int index = supprTtePropPossible.get(i);
			ttePropPossible.remove(index);
			//scoreTtePropPossible.remove(index);
		}
		logger.debug("taille des nvelles listes "+ttePropPossible.size()+" - "+scoreTtePropPossible.size());
		
		//--si score  = 0 on remove toutes propPossible avec la val et leur score correspondant
	//if(scProp == 0) {
	//	for(int i = 0; i< ttePropPossible.size() ; i++) {
	//		Integer[] propPoss = new Integer[this.lgueurCombo];
	//		propPoss = ttePropPossible.get(i);
	//		
	//		for(int j =0; j<this.lgueurCombo; j++) {//for(int j = 0; j<this.lgueurCombo; j++) {
	//			for(int k = 0; k<this.lgueurCombo; k++){
	//				logger.debug("on compare : "+propPoss[k]+" avec "+constrPropOrdi1[j][tourDeJeu]);
	//				if(constrPropOrdi1[j][tourDeJeu] == propPoss[k]) {
	//					supprTtePropPossible.add(i);
	//					//logger.debug("la liste des prop a suppr : "+i);
	//				}
	//			}
	//		}
	//	}
	//	logger.debug("taille des listes "+ttePropPossible.size()+" - "+scoreTtePropPossible.size()+" - listSuppr : "+supprTtePropPossible.size());
	//	for(int i = 0; i <supprTtePropPossible.size(); i++) {
	//		int index = supprTtePropPossible.get(i);
	//		ttePropPossible.remove(index);
	//		scoreTtePropPossible.remove(supprTtePropPossible.get(i));
	//	}
	//	logger.debug("taille des nvelles listes "+ttePropPossible.size()+" - "+scoreTtePropPossible.size());
	//}
	////--sinon, on parcours les scores de ttesPropPossibles ayant au moins une val candidate et on remove tous ceux =< scProp dans les 2 ArrayList
	//else {
	//	for(int i = 0; i< ttePropPossible.size() ; i++) {
	//		Integer[] propPoss = new Integer[this.lgueurCombo]; 
	//		int scPropPoss = calcScore(propPoss);
	//		for(int j = 0; j<this.lgueurCombo; j++) {
	//			if(constrPropOrdi1[j][tourDeJeu] == propPoss[j] && (scPropPoss < scProp || scPropPoss == scProp)) {
	//				supprTtePropPossible.add(i);
	//			}
	//		}
	//	}
	//	for(int i = 0; i <supprTtePropPossible.size(); i++) {
	//		ttePropPossible.remove(supprTtePropPossible.get(i));
	//		scoreTtePropPossible.remove(supprTtePropPossible.get(i));
	//	}
	//}
		
		
		
	//for (int i = 0; i < ttePropPossible.size(); i++) {
	//	logger.debug("on compare la score de la prop : "+scProp+" avec les score "+ttePropPossible.get(i));
	//	if (scProp > ttePropPossible.get(i)) {
	//		ttePropPossible.remove(i);
	//	} 
	//}
		resultCompa = "Reponse : "+compteurPresent+" présents - "+compteurOK+" bien placés";
					
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
	public void compare1() {
		int difference = 0;
		compteurOK = 0;
		compteurPresent = 0;
		Boolean boolPresent = false;
		Boolean poolRep = false;
				
		for(int i = 0; i<lgueurCombo; i++) {
			//System.out.println("decompo de combo : "+constrCombiSecret[i]);//--Controle
			logger.debug("decompo de combo : "+constrCombiSecret[i]);
			logger.debug("decompo prop ordi : "+constrPropOrdi1[i][tourDeJeu]);
			difference = constrCombiSecret[i] - constrPropOrdi1[i][tourDeJeu];
			//System.out.println(difference);//--Controle
			logger.debug("Ctrl difference : "+difference);

			
			//--On vérifie que la valeur n'est pas deja ds le pool de repOrdi
		//for(int k : poolRepOrdi) {
		//	if(constrPropOrdi1[i][tourDeJeu] == k)
		//		poolRep = true;
		//	else
		//		poolRep = false;
		//}
		
			//--Si la difference est nulle, la prop est correcte et on construit la reponse de l'ordi
			if (difference == 0) {
				compteurOK ++;
				compteurPresent ++;
				poolRepOrdi.add(constrPropOrdi1[i][tourDeJeu]);
				poolNbPropOrdi.remove(constrPropOrdi1[i][tourDeJeu]);
				//if(tourDeJeu + 1 < this.nbCoupsConfig) {
					//constrPropOrdi1[i][tourDeJeu +1] = constrPropOrdi1[i][tourDeJeu];
					//constrPropOrdi1[alea.nextInt(3)][tourDeJeu +1] = constrPropOrdi1[i][tourDeJeu];
					//constrRepOrdi[alea.nextInt(3)] = constrPropOrdi1[i][tourDeJeu];
				//}
			//else if (tourDeJeu + 1 == this.nbCoupsConfig) {
			//	
			//}
		
			}
			
			//--Sinon, on vérifie la présence de la prop de l'ordi dans la combinaison secrete, on incremente le compteur de presence et on chge le booleen
			//--le cas echeant
			else {
				for (int j = 0; j < lgueurCombo; j ++) {
					if (constrPropOrdi1[i][tourDeJeu] == constrCombiSecret[j]) {// && constrPropOrdi1[i][tourDeJeu]!= constrPropOrdi1[j][tourDeJeu+1]) {
						//--On vérifie que la valeur n'est pas deja ds le pool de repOrdi
						for(int k : poolRepOrdi) {
							if(constrPropOrdi1[i][tourDeJeu] == k)
								poolRep = true;
							else
								poolRep = false;
						}
						
						if (poolRep = false) {
							poolRepOrdi.add(constrPropOrdi1[i][tourDeJeu]);
							poolNbPropOrdi.remove(constrPropOrdi1[i][tourDeJeu]);
							compteurPresent ++;
							boolPresent = true;
							break;
						}
						
						
						//constrPropOrdi1[i][tourDeJeu +1] = constrPropOrdi1[alea.nextInt(3)][tourDeJeu];
					//compteurPresent ++;
					//boolPresent = true;
					//break;
					}
					else {
						
						boolPresent = false ;
					}
				}
				//System.out.println("mon booleen Present :" +boolPresent);//--Controle
				logger.debug("mon booleen Present :" +boolPresent);
				//--Si la prop est fausse, on la retire du pool d'entier
				if(boolPresent == false) {
					this.poolNbPropOrdi.remove(constrPropOrdi1[i][tourDeJeu]);
				}
			}
		}
		//for (int k = 0 ; k<lgueurCombo; k++)
			//logger.debug("la prop du prochain tour apres comparaison : "+constrPropOrdi1[k][tourDeJeu + 1]);
		
		for(int k = 0; k<poolNbPropOrdi.size(); k++) {
			logger.debug("le pool de nb : "+poolNbPropOrdi.get(k));//--Controle
		}
		for(int k = 0; k<poolRepOrdi.size(); k++)
			logger.debug("le pool de repOrdi : "+poolRepOrdi.get(k));
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
