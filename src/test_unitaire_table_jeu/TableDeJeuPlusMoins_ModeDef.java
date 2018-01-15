package test_unitaire_table_jeu;

import java.awt.BorderLayout;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JOptionPane;

public class TableDeJeuPlusMoins_ModeDef extends TableDeJeu_Test{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Random alea = new Random();
	private String str = "";
	
	private int combiSecret;
	private char[] tabConstrCombiSecret;
	private Integer[] constrCombiSecret;
	private Integer[] constrPropOrdi;
	private Integer[] constrRepOrdi;
	private HashMap<Integer, Integer> tabIntervalles;
	//private HashMap<Integer, Integer> intOri, intFin = new HashMap<Integer, Integer>();
	private int propOrdi = 0;
	
	private HashMap<Integer, ArrayList<Integer>> tabPool;
	private ArrayList<Integer> tabIntPool;
	
	public TableDeJeuPlusMoins_ModeDef() {
		super();
	}
	
	public TableDeJeuPlusMoins_ModeDef(String pMode, int pEssai, int pCombo) {
		super(pMode, pEssai, pCombo);
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
		logger.info("la combo gagnante : "+this.combiSecret);
	}
	
	public void jeu1() {
		//--Selon le tour l'ordi restreint sa proposition

			if(tourDeJeu == 0) {
				//--L'ordi choisit une combinaison aléatoire et remplit un tbleau contenant sa reponse de -1
				this.constrPropOrdi = new Integer[this.lgueurCombo];
				this.constrRepOrdi = new Integer[this.lgueurCombo];
				this.tabIntervalles = new HashMap<Integer, Integer>();
				//this.intOri = new HashMap<Integer, Integer>();
				//this.intFin = new HashMap<Integer, Integer>();
				resultCompa ="";
				
				//Random alea = new Random();
				//String str = "";
						
				for (int i = 0; i<lgueurCombo; i++) {
					//--on remplit le tableau de reponse de -1
					constrRepOrdi[i] = -1;
					//--on tire 1 chiffre au hasard
					constrPropOrdi[i] = alea.nextInt(10);
					str += String.valueOf(constrPropOrdi[i]);//--on concatene les différents chiffres 
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
 
			else {
				//--on reinitialise la prop de l'ordi
				constrPropOrdi = new Integer[this.lgueurCombo];
				propOrdi = 0;
				resultCompa = "";
				str = "";
				//--on recupere les prop correctes quand il y en a 
				for(int i = 0; i<lgueurCombo; i++) {
					if (constrRepOrdi[i] != -1) {
						constrPropOrdi[i] = constrRepOrdi[i];
						str += String.valueOf(constrPropOrdi[i]);
					}
					//--sinon on tire au hasard dans l'intervalle restreint
					else {
						//constrPropOrdi[i] = alea.nextInt(10);
						//System.out.println("tabIntervalle : "+tabIntervalles.get(i));//--Controle
						logger.debug("tabIntervalle : "+tabIntervalles.get(i));
						constrPropOrdi[i] = alea.nextInt(tabIntervalles.get(i));
						
						
						//--on genere aleatoirement un nb dans un intervalle defini que l'on met ds une liste intermediaire
						//List<Integer> tabInt = (alea.ints(intOri.get(i), intFin.get(i))).boxed().collect(Collectors.toList());
						//--on recupere la proposition de l'ordi
						//System.out.println("nb aleatoire intermediaire : "+tabInt.get(i));//--Controle
						//constrPropOrdi[i] = tabInt.get(i);
						
						
						str += String.valueOf(constrPropOrdi[i]);
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
			
	}
	
	public void compare() {
		int difference = 0;
		this.tabIntPool = new ArrayList<Integer>();
		//this.tabPool = new HashMap<Integer, Integer[]>();
		
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
				tabIntervalles.put(i, constrPropOrdi[i]-1);
				//intOri.put(i, 0);
				//intFin.put(i,constrPropOrdi[i]-1);
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
				tabIntervalles.put(i, 10);
				//intOri.put(i, constrPropOrdi[i]+1);
				//intFin.put(i, 10);
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
		
		int diff2 = (propOrdi - combiSecret);
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
	
	
	public void jeu() {

		if(tourDeJeu == 0) {
			this.constrPropOrdi = new Integer[this.lgueurCombo];
			this.constrRepOrdi = new Integer[this.lgueurCombo];
			this.resultCompa = "";
			this.tabPool = new HashMap<Integer, ArrayList<Integer>>();
			this.tabIntPool = new ArrayList<Integer>();
			
			this.tabIntervalles = new HashMap<Integer, Integer>();
			
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
}
