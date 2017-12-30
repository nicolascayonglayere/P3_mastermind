package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TableDeJeu extends JPanel {

	/**
	 * Attributs de la classe 
	 */
	private static final long serialVersionUID = 1L;
	private String nom = "Table De Jeu";
	private int nbCoupsConfig = 10;//--Ici je recup la donnée du fichier properties 
	private int lgueurCombo = 4;//--Ici je recup la donnée du fichier properties
	//private String[][] coupsJoues = new String[nbCoupsConfig][nbCoupsConfig];
	//private PlusMoins regle ;
	
	private int tourDeJeu = 0;
	private JLabel[] listResult = new JLabel[this.nbCoupsConfig];//--Une liste d'étiquette qui accueille le résultat de l'essai
	private JFormattedTextField[] listProp = new JFormattedTextField[this.nbCoupsConfig];//--Une liste de champs de saisie pour saisir son coup
	
	private int combiSecret = 0;
	private Integer[] constrCombiSecret = new Integer[this.lgueurCombo];
	private String coupJoue ;
	private Integer[] listPropJoueur = new Integer[this.lgueurCombo];
	private String resultCompa;
	
	private JPanel panTbleJeu = new JPanel();
	
	/**
	 * Constructeur sans paramètres
	 */
	public TableDeJeu() {
		//--Le joueur
		
		//--Les composants graphiques
		this.setLayout(new BorderLayout());
		initTable();
		
		//--Le clavier pas utile pour l'instant
		
		this.add(panTbleJeu);
		
		//--Le type de jeu
		//this.regle = new PlusMoins(coupJoue);
		
		//--La combinaison gagnante
		this.initCombiSecret();

	}
	
	/**
	 * La méthode créant la table de jeu
	 * @return
	 */
	public void initTable() {
		//--Le panneau qui accueille la table de jeu
		
		panTbleJeu.setPreferredSize(new Dimension (400, 500));
		panTbleJeu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panTbleJeu.setLayout(new BoxLayout(panTbleJeu, BoxLayout.PAGE_AXIS));
		panTbleJeu.setBackground(Color.WHITE);
		
		Font police = new Font("Arial", Font.BOLD, 18);
		JLabel[] listEssai = new JLabel[this.nbCoupsConfig];//--une liste d'étiquette qui accueille le n° de la tentative

		JPanel[] panRef = new JPanel[this.nbCoupsConfig];//--Une liste de JPanel qui accueille les 3 comp précédents
		
		for(int i = 0; i<nbCoupsConfig; i++) {
			listEssai[i] = new JLabel(String.valueOf(i+1));
			listEssai[i].setFont(police);
			listEssai[i].setBackground(Color.white);
			
			listProp[i] = new JFormattedTextField(NumberFormat.getNumberInstance());
			listProp[i].setFont(police);
			listProp[i].setBackground(Color.white);
			listProp[i].setPreferredSize(new Dimension (200, 50));
			listProp[i].setHorizontalAlignment(JFormattedTextField.CENTER);
			listProp[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					jeu();
					//listProp[i-1].setEditable(false);
				}
			});
			
			
			listResult[i] = new JLabel("");
			listResult[i].setFont(police);
			listResult[i].setBackground(Color.white);
			listResult[i].setPreferredSize(new Dimension (200, 50));
			
			
			panRef[i] = new JPanel();
			panRef[i].setBorder(BorderFactory.createEtchedBorder());
			panRef[i].setBackground(Color.WHITE);
			panRef[i].setLayout(new BoxLayout(panRef[i], BoxLayout.LINE_AXIS));
			panRef[i].add(listEssai[i]);
			panRef[i].add(listProp[i]);
			panRef[i].add(listResult[i]);
			
			panTbleJeu.add(panRef[i]);
		}
		
		//--Le panneau de ref
		//JPanel[] panRef = new JPanel[this.nbCoupsConfig];
		//JLabel[] listLabel = new JLabel[this.lgueurCombo];

		//for (int j = 0; j <nbCoupsConfig; j++) {
		//	for (int i = 0; i<lgueurCombo; i++) {
		//		listLabel[i] = new JLabel();
		//		listLabel[i].setBorder(BorderFactory.createEtchedBorder());
		//		listLabel[i].setBackground(Color.white);
		//		
		//		panRef[j] = new JPanel();
		//		panRef[j].setPreferredSize(new Dimension (100, 500));
		//		panRef[j].setBorder(BorderFactory.createLoweredBevelBorder());
		//		panRef[j].setLayout(new BoxLayout(panRef[j], BoxLayout.LINE_AXIS));
		//		panRef[j].setBackground(Color.LIGHT_GRAY);
		//		panRef[j].add(listLabel[i]);
		//	}
		//	panTbleJeu.add(panRef[j]);
		//}
		
	}
	
	public void jeu() {
		//while(tourDeJeu != nbCoupsConfig + 1) {
			resultCompa = "";
			coupJoue = listProp[tourDeJeu].getText();
			System.out.println("la proposition du joueur : "+coupJoue);
			char[] tabint = coupJoue.toCharArray();
			
			for (int i = 0; i<lgueurCombo; i++) {
				this.listPropJoueur[i] = (int)tabint[i];
				System.out.println("la liste de prop du joueur : "+listPropJoueur[i]);
			}
			
			this.compare();
			listResult[tourDeJeu].setText(resultCompa);
			tourDeJeu ++;
		//}
	}
	
	/**
	 * Méthode générant la combinaison secrete
	 */
	public void initCombiSecret() {
		Random alea = new Random();
		String str = "";
				
		for (int i = 0; i<lgueurCombo; i++) {
			constrCombiSecret[i] = alea.nextInt(10);
			str += String.valueOf(constrCombiSecret[i]);
		}
		this.combiSecret = Integer.valueOf(str);
		System.out.println("la combo gagnante : "+this.combiSecret);//--Controle
	}
	
	/**
	 * méthode comparant la prop du joueur avec la combinaison secrete
	 */
	public void compare() {
		int difference = 0;
		
		for(int i = 0; i<lgueurCombo; i++) {
			//System.out.println("decompo du coup joue "+Integer.valueOf(coupJoue.charAt(i)));
			System.out.println("decompo de combo : "+constrCombiSecret[i]);
			difference = constrCombiSecret[i] - listPropJoueur[i];
			System.out.println(difference);
			
			if (difference == 0) {
				System.out.println("decompo du coup joue "+Integer.valueOf(coupJoue.charAt(i)));
				resultCompa += "=";
				System.out.println("TRACE =");
			}
			else if (difference < 0 ) {
				System.out.println("decompo du coup joue "+Integer.valueOf(coupJoue.charAt(i)));
				resultCompa += "-";
				System.out.println("TRACE -");
			}
			else if(difference > 0) {
				System.out.println("decompo du coup joue "+Integer.valueOf(coupJoue.charAt(i)));
				resultCompa += "+";
				System.out.println("TRACE +");
			}
		}
	}
	
	/**
	 * Methode retournant le nom de la classe pour l'affichage
	 * @return
	 */
	public String getNom() {
		return this.nom;
	}
	
	
}
