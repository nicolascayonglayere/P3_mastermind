package test_unitaire_table_jeu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class TableDeJeuMasterMind_ModeDef1 extends TableDeJeu_Test {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> poolNbPropOrdi = new ArrayList<Integer>();
	private int combiSecret;
	private char[] tabConstrCombiSecret;
	private Integer[] constrCombiSecret;
	
	private int compteurPresent, compteurOK;

	private Integer[] prop, prop0;
	private Integer scProp0, scProp = 0;
	private ArrayList<Integer[]> ttePropPossible = new ArrayList<Integer[]>();
	private ArrayList<Integer[]> ttePropPossible0 = new ArrayList<Integer[]>();
	
	private Integer[] constrPropOrdi = new Integer[this.lgueurCombo];
	
	private String str = "";
	private int propOrdi = 0;
	
	private Random alea = new Random();
	
	private Integer[] listeScore = {0,1,2,3,4,10,11,12,13,20,21,22,30,40};
	
	
	//private ArrayList<Integer> scoreRel = new ArrayList<Integer>();
	
	/**
	 * Constructeur sans parametres
	 */
	public TableDeJeuMasterMind_ModeDef1() {
		super();
	}
	
	public TableDeJeuMasterMind_ModeDef1(String pMode, int pEssai, int pCombo) {
		super (pMode, pEssai, pCombo);
		
		//--on intialise le pool de nombre, ici de 0 à 9
		for (int i = 0; i <10; i++)
			this.poolNbPropOrdi.add(i);	
		
		int nbTotProp = 1;
		for(int i = 0; i < this.lgueurCombo; i ++) {
			nbTotProp *= poolNbPropOrdi.size();
		}
		
		this.prop = new Integer[this.lgueurCombo];
		this.prop0 = new Integer[this.lgueurCombo];
		
		//--on initialise la liste de toutes les combinaisons possibles qui sont chaque nombre entre 0 et le nb total de proposition
		//--on formate les nombres en ajoutant des 0 devant
		String str0 = "0";
		for(int i = 0; i<this.lgueurCombo - 1; i++) {
			str0 +="0";
		}
		for (int i = 0; i<nbTotProp; i++) {
			Integer[] propConstr = new Integer[this.lgueurCombo];
			String str1 = String.valueOf(i);
			DecimalFormat nf = new DecimalFormat(str0);
			str1 = nf.format(Integer.valueOf(str1));
			for (int j = 0; j<this.lgueurCombo; j++) {
				char [] tabint = str1.toCharArray();
				propConstr[j] = Character.getNumericValue(tabint[j]);
				logger.debug("val de liste possible : "+propConstr[j]);//--Controle
			}
			ttePropPossible0.add(propConstr);
			ttePropPossible.add(propConstr);			
		}
		logger.debug("taille de liste des possibles creee "+ttePropPossible.size());//--Controle
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
	
	public void jeu1() {
		Integer[] propPoss = new Integer[this.lgueurCombo];
		ArrayList<Integer[]> supprTtePropPossible = new ArrayList<Integer[]>();

		this.resultCompa = "";
		
		for(int i =0; i<10; i++) {
			Integer[] ctrlliste =  ttePropPossible.get(i);
			for(int j = 0; j<this.lgueurCombo; j++) {
				logger.debug("ctrl liste possible : "+ctrlliste[j]);//--Contrle
			}
		}
		
		//--Au premier tour il prend un nb au hasard dans la liste des prop possibles
		if(tourDeJeu == 0)
			//prop0 = ttePropPossible.get(alea.nextInt(ttePropPossible.size()));
			//prop0 = ttePropPossible.get(0);
			prop0 = meilleureProp(ttePropPossible0, ttePropPossible0);
		
		//--Ensuite, on se base sur le score relatif(score calc avec la prop précédente co liste de comparaison) pour determiner la proposition de l'ordi
		//--on remove de la liste des possibles toutes les combinaisons qui ne tiennent pas la comparaison et on prend la première de la nouvelle liste des possibles comme proposition de l'ordi
		else {
			prop = prop0;

			for (int i = 0 ; i<ttePropPossible.size(); i++) {

				propPoss = ttePropPossible.get(i);
				int scRelPropPoss = calcScore(propPoss, prop);
				for(int k : propPoss)
					logger.debug("detail prop comparee" + k);//--Controle
				
				logger.debug("comp score rel :"+ scProp0+ " - "+calcScore(propPoss, prop));//--Controle
				
				if(scRelPropPoss != scProp0) {
					supprTtePropPossible.add(propPoss);	
				}
			}
			logger.debug("taille liste suppr : "+supprTtePropPossible.size());//--Controle
			
			for(Integer[] i : supprTtePropPossible) {
				ttePropPossible.remove(i);
			}
			logger.debug("taille des nvelles listes "+ttePropPossible.size());//--Controle
			
			//prop0 = ttePropPossible.get(0);
			prop0 = meilleureProp(ttePropPossible, ttePropPossible0);
		}
		
			
		for(int i = 0; i<this.lgueurCombo; i++) {
			
			constrPropOrdi[i] = prop0[i];
			str += String.valueOf(constrPropOrdi[i]);
			
		}
		logger.debug("propo ordi : " +str);
		scProp0 = calcScore(prop0, constrCombiSecret);
		//scoreRel.add(scProp0);
		
		//--Le résultat de la comparaison apres calcul du score
		resultCompa = "Reponse : "+compteurPresent+" présents - "+compteurOK+" bien placés";

		propOrdi = Integer.valueOf(str);
		//System.out.println("la proposition de l'odinateur : "+propOrdi);//--Controle
		logger.info("la proposition de l'odinateur : "+propOrdi+" et son score "+scProp0);
		//--Il la propose en l'affichant ds la zone de texte
		listProp[tourDeJeu].setText(str);
		
		//--affichage du résultat
		listResult[tourDeJeu].setText(resultCompa);
		this.victoire();
		listProp[tourDeJeu + 1].setEditable(true);
		tourDeJeu ++;
		propOrdi = 0;
		str = "";
		
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
		compteurOK = 0;
		compteurPresent = 0;
		
		int compteurOccurCombo = 0;//--le nb de fois ou la val apparait dans la combinaison secrete
		int compteurOccurProp = 0;//--le nb de fois ou la val apparait dans la proposition
	
		for(int i = 0; i<lgueurCombo; i++) {
			//System.out.println("decompo de combo : "+constrCombiSecret[i]);//--Controle
			//logger.debug("decompo de liste comparee : "+plistCompa[i]);
			//logger.debug("decompo prop ordi : "+pconstrPropOrdi[i]);
			difference = plistCompa[i] - pconstrPropOrdi[i];
			//System.out.println(difference);//--Controle
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
	
		//logger.debug("le score : "+scProp+" de la prop " +pconstrPropOrdi.toString());
		return scProp;
	}
	
	
	/**
	 * Methode vérifiant si c la victoire ou la defaite
	 */
	public void victoire() {
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
	
	public Integer[] meilleureProp(ArrayList<Integer[]> pCandidat, ArrayList<Integer[]> pTtePropPossible0) {
		Integer[] listePoids = new Integer[listeScore.length];
		//ArrayList<Integer> listePoids = new ArrayList<Integer>();
		ArrayList<Integer> poidsPropList = new ArrayList<Integer>();
		for(Integer[] l : pTtePropPossible0) {
			for(int i = 0; i<listeScore.length; i++) {//for (int sc : listeScore) {
				listePoids[i] = 0; //--nb de candidat
				for(Integer[] candidat : pCandidat) {
					if(calcScore(candidat,l) == listeScore[i]);
						listePoids[i]++;
				}
				//logger.debug("poids : "+c);
				
			}
			int max = -1;
			for(int k : listePoids) {

				if(max < k)
					max = k;
			}
		//	Integer[] poidsProp = new Integer[listeScore.length] ;
			int poidsProp = max;
			poidsPropList.add(poidsProp);
		}
		int min = -1;
		for(int k : poidsPropList) {
			if (min>k)
				min = k;
		}
		return pCandidat.get(min);
	}
}
