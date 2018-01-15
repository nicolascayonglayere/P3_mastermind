package test_unitaire_table_jeu;

import java.util.Random;

import javax.swing.JOptionPane;

public class TableDeJeuPlusMoins_ModeDuel extends TableDeJeu_Test {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int combiSecretOrdi = 0;
	private Integer[] constrCombiSecretOrdi;
	
	private int combiSecret;
	private char[] tabConstrCombiSecret;
	private Integer[] constrCombiSecret;
	private Integer[] constrPropOrdi;
	private Integer[] constrRepOrdi;

	
	public TableDeJeuPlusMoins_ModeDuel() {
		super();
	}
	
	public TableDeJeuPlusMoins_ModeDuel(String pMode, int pEssai, int pCombo) {
		super(pMode, pEssai, pCombo);
	}
	
	/**
	 * Méthode générant la combinaison secrete
	 */
	public void initCombiSecret() {
		this.constrCombiSecretOrdi = new Integer[this.lgueurCombo];
		Random alea = new Random();
		String str = "";
				
		for (int i = 0; i<lgueurCombo; i++) {
			//--on tire 1 chiffre au hasard
			constrCombiSecretOrdi[i] = alea.nextInt(10);
			str += String.valueOf(constrCombiSecretOrdi[i]);//--on concatene les différents chiffres 
		}
		this.combiSecretOrdi = Integer.valueOf(str);
		//System.out.println("la combo gagnante : "+this.combiSecret);//--Controle
		logger.warn("la combo gagnante : "+this.combiSecretOrdi);
		
		JOptionPane jop = new JOptionPane();
		String message = "La combinaison secrète est prête \n";
		message += "A vous de jouer";
		jop.showMessageDialog(null, message, "Combinaison secrète prête !", JOptionPane.INFORMATION_MESSAGE);
		
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
		String message1 = "Vous avez choisi une combinaison secrète. \n";
		message1 += "Votre adversaire joue.";
		jop1.showMessageDialog(null, message1, "Combinaison secrète prête !", JOptionPane.INFORMATION_MESSAGE);
		
		//System.out.println("la combo gagnante : "+this.combiSecret);//--Controle
		logger.info("la combo gagnante : "+this.combiSecret);
		
		listProp[0].setEditable(true);
	}
	
	
}
//joueurHumain.initCombiSecret
//joueurElectronique.initCombiSecret

//tant que l'un ou l'autre n'a pas gagne
//	joueurHumain.jeu
//	joueurElectronique.jeu

//	joueurHumain.compare
//	joueurElectronique.compare
