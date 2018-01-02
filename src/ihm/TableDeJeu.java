package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Properties;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import Propriete.GestionFichierProperties;


public class TableDeJeu extends JPanel {

	/**
	 * Attributs de la classe 
	 */
	private static final long serialVersionUID = 1L;
	private String nom = "Table De Jeu";
	private Properties propriete;
	private int nbCoupsConfig; 
	private int lgueurCombo;
	//private String[][] coupsJoues = new String[nbCoupsConfig][nbCoupsConfig];
	//private PlusMoins regle ;
	
	private int tourDeJeu = 0;
	private JLabel[] listResult;//--Une liste d'�tiquette qui accueille le r�sultat de l'essai
	private JFormattedTextField[] listProp;//--Une liste de champs de saisie pour saisir son coup
		
	private int combiSecret = 0;
	private Integer[] constrCombiSecret;
	private String coupJoue ;
	private Integer[] listPropJoueur ;
	private String resultCompa;
	
	private JPanel panTbleJeu = new JPanel();
	
	/**
	 * Constructeur sans param�tres
	 */
	public TableDeJeu() {
		//--on r�cup�re les proprietes
		GestionFichierProperties gfp = new GestionFichierProperties();
		this.propriete = gfp.lireProp();
		this.nbCoupsConfig = Integer.valueOf(this.propriete.getProperty("nombres d'essai"));
		System.out.println("Ctrl nb coup :"+this.nbCoupsConfig);//--Controle
		this.lgueurCombo = Integer.valueOf(this.propriete.getProperty("longueur combinaison"));
		System.out.println("Ctrl lgueur :"+this.lgueurCombo);//--Controle
		
		//--Le joueur
		
		//--Les composants graphiques
		this.setLayout(new BorderLayout());
		try {
			initTable();
		}catch(ParseException e) {
			e.printStackTrace();
		}
		
		//--Le clavier pas utile pour l'instant
		
		this.add(panTbleJeu, BorderLayout.CENTER);
	}
	
	/**
	 * Constructeur avec parametre
	 * @param pEssai
	 * @param pCombo
	 */
	public TableDeJeu(int pEssai, int pCombo) {
		//--on r�cup�re les proprietes
		GestionFichierProperties gfp = new GestionFichierProperties();
		this.propriete = gfp.lireProp();
		
		//--Les composants graphiques
		this.setLayout(new BorderLayout());
		try {
			initTable();
		}catch(ParseException e) {
			e.printStackTrace();
		}
		this.add(panTbleJeu, BorderLayout.CENTER);
	}
	
	/**
	 * La m�thode cr�ant la table de jeu
	 * @return
	 */
	public void initTable() throws ParseException{
		//--Le panneau qui accueille la table de jeu
		
		panTbleJeu.setPreferredSize(new Dimension (400, 500));
		panTbleJeu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panTbleJeu.setLayout(new BoxLayout(panTbleJeu, BoxLayout.PAGE_AXIS));
		panTbleJeu.setBackground(Color.WHITE);
		
		Font police = new Font("Arial", Font.BOLD, 18);
		JLabel[] listEssai = new JLabel[this.nbCoupsConfig];//--une liste d'�tiquette qui accueille le n� de la tentative

		JPanel[] panRef = new JPanel[this.nbCoupsConfig];//--Une liste de JPanel qui accueille les 3 comp pr�c�dents
		
		//--On applique un maskFormatter au JFormattedTextField pour s'assurer de la validit� de la saisie
		String[] listDiese = new String[this.lgueurCombo];
		String str = "";
		for (int k = 0; k<this.lgueurCombo; k++) {
			listDiese[k] = "#";
			str += listDiese[k];
		}
		MaskFormatter mask = new MaskFormatter(str);

		this.listProp = new JFormattedTextField[this.nbCoupsConfig];
		this.listResult = new JLabel[this.nbCoupsConfig];
		
		for(int i = 0; i<nbCoupsConfig; i++) {
			//--L'etiquette essai n�
			listEssai[i] = new JLabel(String.valueOf(i+1));
			listEssai[i].setFont(police);
			listEssai[i].setBackground(Color.white);
			
			//--La zone de texte ou s'effectue la saisie
			listProp[i] = new JFormattedTextField(mask);
			listProp[i].setFont(police);
			listProp[i].setBackground(Color.white);
			listProp[i].setPreferredSize(new Dimension (200, 50));
			listProp[i].setEditable(false);
			listProp[i].setHorizontalAlignment(JFormattedTextField.CENTER);
			listProp[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					jeu();
				}
			});
			
			//--L'�tiquette qui affiche le r�sultat de la comparaison entre la saisie et la combinaison gagnante
			listResult[i] = new JLabel("");
			listResult[i].setFont(police);
			listResult[i].setBackground(Color.white);
			listResult[i].setPreferredSize(new Dimension (200, 50));
			
			//--Le panneau qui accueille les 3 composants pr�c�dents
			panRef[i] = new JPanel();
			panRef[i].setBorder(BorderFactory.createEtchedBorder());
			panRef[i].setBackground(Color.WHITE);
			panRef[i].setLayout(new BoxLayout(panRef[i], BoxLayout.LINE_AXIS));
			panRef[i].add(listEssai[i]);
			panRef[i].add(listProp[i]);
			panRef[i].add(listResult[i]);
			
			panTbleJeu.add(panRef[i]);
		}
	}
	/**
	 * m�thode qui pr�pare la comparaison et qui retourne le r�sultat de cette comparaison
	 */
	public void jeu() {
		this.listPropJoueur = new Integer[this.lgueurCombo];
		resultCompa = "";
		coupJoue = listProp[tourDeJeu].getText();
		System.out.println("la proposition du joueur : "+coupJoue);
		char[] tabint = coupJoue.toCharArray();
		
		for (int i = 0; i<lgueurCombo; i++) {
			this.listPropJoueur[i] = Character.getNumericValue(tabint[i]);
			System.out.println("la liste de prop du joueur : "+listPropJoueur[i]);
		}
		
		this.compare();
		listResult[tourDeJeu].setText(resultCompa);
		listProp[tourDeJeu + 1].setEditable(true);
		tourDeJeu ++;
		

	}
	
	/**
	 * M�thode g�n�rant la combinaison secrete
	 */
	public void initCombiSecret() {
		this.constrCombiSecret = new Integer[this.lgueurCombo];
		Random alea = new Random();
		String str = "";
				
		for (int i = 0; i<lgueurCombo; i++) {
			//--on tire 1 chiffre au hasard
			constrCombiSecret[i] = alea.nextInt(10);
			str += String.valueOf(constrCombiSecret[i]);//--on concatene les diff�rents chiffres 
		}
		this.combiSecret = Integer.valueOf(str);
		System.out.println("la combo gagnante : "+this.combiSecret);//--Controle
		
		JOptionPane jop = new JOptionPane();
		String message = "La combinaison secr�te est pr�te \n";
		message += "A vous de jouer";
		jop.showMessageDialog(null, message, "Combinaison secr�te pr�te !", JOptionPane.INFORMATION_MESSAGE);
		listProp[0].setEditable(true);
	}
	
	/**
	 * m�thode comparant la prop du joueur avec la combinaison secrete
	 */
	public void compare() {
		int difference = 0;
		
		for(int i = 0; i<lgueurCombo; i++) {
			//System.out.println("decompo du coup joue "+Integer.valueOf(coupJoue.charAt(i)));
			System.out.println("decompo de combo : "+constrCombiSecret[i]);
			difference = constrCombiSecret[i] - listPropJoueur[i];
			System.out.println(difference);
			
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
			int option = jop.showConfirmDialog(null, "F�licitation, vous avez trouv� la combinaison secr�te ! \n Voulez-vous rejouer ?",
							"Victoire", 
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
			if (option == JOptionPane.OK_OPTION) {
				nouvellePartie();
			}				
		}
		
		System.out.println(tourDeJeu);
		//--Defaite
		if(tourDeJeu+1 == nbCoupsConfig && diff2 !=0) {
			JOptionPane jop = new JOptionPane();
			int option = jop.showConfirmDialog(null, "C'est perdu ! \n La combinaison gagnante est "+combiSecret+"\n Voulez-vous rejouer ?",
							"D�faite", 
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
			if (option == JOptionPane.OK_OPTION) {
				nouvellePartie();
			}
		}
	}
	
	/**
	 * Methode g�n�rant une nouvelle partie
	 * 
	 */
	public void nouvellePartie() {
		
		panTbleJeu.removeAll();
		try {
			initTable();
		}catch(ParseException e) {
			e.printStackTrace();
		}
		
		panTbleJeu.revalidate();
		panTbleJeu.repaint();
		this.setLayout(new BorderLayout());
		this.add(panTbleJeu, BorderLayout.CENTER);
		initCombiSecret();
		tourDeJeu = 0;
		
		
	}
	
	
	
	/**
	 * Methode retournant le nom de la classe pour l'affichage
	 * @return
	 */
	public String getNom() {
		return this.nom;
	}
	
	
}
