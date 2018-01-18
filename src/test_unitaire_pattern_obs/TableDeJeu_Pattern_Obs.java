package test_unitaire_pattern_obs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;

import Propriete.GestionFichierProperties;
import Propriete.ModeJeu;
import joueur.Joueur;
import joueur.JoueurElectronique;
import joueur.JoueurHumain;
import pattern_observer.Observateur;

public class TableDeJeu_Pattern_Obs extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(TableDeJeu_Pattern_Obs.class);
	protected Joueur joueur, joueur1;
	private String nom = "Table De Jeu";
	
	protected Properties propriete;
	protected String jeu, modeJeu;
	protected int nbCoupsConfig; 
	protected int lgueurCombo;
	
	private int tourDeJeu ;
	protected JLabel[] listResult;//--Une liste d'�tiquette qui accueille le r�sultat de l'essai
	protected JLabel[] listResultJH, listResultJE;
	protected JLabel[][] listLblProp;
	protected JLabel[] lblProp;
	protected HashMap<Integer, JLabel[]> listPropLbl;
	protected JFormattedTextField[] listProp;//--Une liste de champs de saisie pour saisir son coup
	protected JFormattedTextField[] propJH, propJE;
	
	//private String coupJoue ;
	//private Integer[] listPropJoueur ;
	protected String resultCompa;
	
	protected JPanel panTbleJeu = new JPanel();
	
	protected int couleur = -1;
	
	public TableDeJeu_Pattern_Obs() {
		//--on r�cup�re les proprietes
				GestionFichierProperties gfp = new GestionFichierProperties();
				this.propriete = gfp.lireProp();
				modeJeu = String.valueOf(propriete.getProperty("mode"));
				//System.out.println("Ctrl mode : "+modeJeu);//--Controle
				logger.debug("Ctrl mode : "+modeJeu);
				this.nbCoupsConfig = Integer.valueOf(this.propriete.getProperty("nombres d'essai"));
				//System.out.println("Ctrl nb coup :"+this.nbCoupsConfig);//--Controle
				logger.debug("Ctrl nb coup :"+this.nbCoupsConfig);
				this.lgueurCombo = Integer.valueOf(this.propriete.getProperty("longueur combinaison"));
				//System.out.println("Ctrl lgueur :"+this.lgueurCombo);//--Controle
				logger.debug("Ctrl lgueur :"+this.lgueurCombo);
				
				
				//--Les composants graphiques
				this.setLayout(new BorderLayout());
				try {
					initTable();
				}catch(ParseException e) {
					e.printStackTrace();
				}
				this.add(panTbleJeu, BorderLayout.CENTER);
	}
	
	public TableDeJeu_Pattern_Obs(String pJeu, String pMode, int pEssai, int pCombo, int pCouleur) {
			this.jeu = pJeu;
			this.modeJeu = pMode;
			this.nbCoupsConfig = pEssai;
			this.lgueurCombo = pCombo;
			this.couleur = pCouleur;
		////--Les composants graphiques
		//this.setLayout(new BorderLayout());
		//try {
		//	initTable();
		//}catch(ParseException e) {
		//	e.printStackTrace();
		//}
		//this.add(panTbleJeu, BorderLayout.CENTER);
		
		if (pMode.equals(ModeJeu.CHALLENGER.toString())) {
			this.joueur = new JoueurHumain(lgueurCombo, jeu);
			this.joueur.addObservateur(new Observateur() {
				public void update(String coupJoue) {
					tourDeJeu = joueur.getTourDeJeu();
					logger.debug("Ctrl TourDeJeu : "+tourDeJeu);
			//	coupJoue = listProp[tourDeJeu].getText();
			//	joueur.jeu(coupJoue);
			//	resultCompa = joueur.getResultCompa();
			//	listResult[tourDeJeu].setText(resultCompa);
					
					
					if(joueur.getVictoire() == false && tourDeJeu+1 < nbCoupsConfig) {
					//for(int i = 0; i<lgueurCombo; i++) {
					//	lblProp = listPropLbl.get(tourDeJeu+1);
					//	coupJoue += lblProp[i].getText();	
					//}
						for(int i = 0; i<lgueurCombo; i++) {
							coupJoue += listLblProp[tourDeJeu][i].getText();
						}
						logger.debug("le coup du joueur "+coupJoue);
						joueur.jeu(coupJoue);
						resultCompa = joueur.getResultCompa();
						listResult[tourDeJeu].setText(resultCompa);
						//listProp[tourDeJeu + 1].setEditable(true);
						//joueur.setTourDeJeu(tourDeJeu++);
					}
					else if (joueur.getVictoire() == true) {
						JOptionPane jop = new JOptionPane();
						int option = jop.showConfirmDialog(null, "F�licitation, vous avez trouv� la combinaison secr�te ! \n Voulez-vous rejouer ?",
										"Victoire", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
					else if(joueur.getVictoire() == false && tourDeJeu+1 == nbCoupsConfig) {
						JOptionPane jop = new JOptionPane();
						int option = jop.showConfirmDialog(null, "C'est perdu ! \n La combinaison gagnante est "+joueur.getCombiSecret()+"\n Voulez-vous rejouer ?",
										"D�faite", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
				}				
			});
		}
		else if (pMode.equals(ModeJeu.DEFENSEUR.toString())) {
			this.joueur = new JoueurElectronique(lgueurCombo, jeu);
			this.joueur.addObservateur(new Observateur() {
				public void update(String coupJoue) {
					tourDeJeu = joueur.getTourDeJeu();	
					logger.debug("Ctrl TourDeJeu : "+tourDeJeu);
					joueur.jeu(coupJoue);
					if(pCouleur == 0) {
						for (int k = 0; k<joueur.getPropOrdi().toCharArray().length; k++) {
							listLblProp[tourDeJeu][k].setText(String.valueOf(joueur.getPropOrdi().charAt(k)));
						}
					}
					else if (pCouleur == 1) {
						//--on met ds la JLabel le fichier couleur du bouton et le chiffre/lettre du bouton
						Color[]listColor = {Color.WHITE, Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PINK};
						ImageIcon[] listImageColor = {new ImageIcon("Ressources/Images/blanc0.JPG"),
											       	new ImageIcon ("Ressources/Images/noir1.JPG"), 
											       	new ImageIcon("Ressources/Images/rouge2.JPG"),
											       	new ImageIcon("Ressources/Images/jaune3.JPG"), 
											       	new ImageIcon("Ressources/Images/vert4.JPG"),
											       	new ImageIcon("Ressources/Images/bleu5.JPG"),
											       	new ImageIcon("Ressources/Images/orange6.JPG"),
											       	new ImageIcon("Ressources/Images/rose8.JPG")};
						for (int k = 0; k<joueur.getPropOrdi().toCharArray().length; k++) {
							listLblProp[tourDeJeu][k].setIcon(listImageColor[Character.getNumericValue(joueur.getPropOrdi().charAt(k))]);
							listLblProp[tourDeJeu][k].setForeground(listColor[Character.getNumericValue(joueur.getPropOrdi().charAt(k))]);
							listLblProp[tourDeJeu][k].setText(String.valueOf(joueur.getPropOrdi().charAt(k)));
						}
					}
				//for (int k = 0; k<joueur.getPropOrdi().toCharArray().length; k++) {
				//	listLblProp[tourDeJeu][k].setText(String.valueOf(joueur.getPropOrdi().charAt(k)));
				//}
					//resultCompa = joueur.getResultCompa();
					//listResult[tourDeJeu].setText(resultCompa);
					
					if(joueur.getVictoire() == false && tourDeJeu+1 != nbCoupsConfig) {
						//listProp[tourDeJeu + 1].setEditable(true);
						//joueur.jeu(coupJoue);
					//for (int k = 0; k<joueur.getPropOrdi().toCharArray().length; k++) {
					//	logger.debug("Ctrl prop ordi : "+joueur.getPropOrdi().charAt(k));
					//	listLblProp[tourDeJeu][k].setText(String.valueOf(joueur.getPropOrdi().charAt(k)));
					//	//lblProp = listPropLbl.get(tourDeJeu);
					//	//lblProp[k].setText(String.valueOf(joueur.getPropOrdi().charAt(k)));
					//}
						resultCompa = joueur.getResultCompa();
						listResult[tourDeJeu].setText(resultCompa);
						//joueur.setTourDeJeu(tourDeJeu++);
					}
					else if (joueur.getVictoire() == true) {
						resultCompa = joueur.getResultCompa();
						listResult[tourDeJeu].setText(resultCompa);
						JOptionPane jop = new JOptionPane();
						int option = jop.showConfirmDialog(null, "F�licitation, vous avez trouv� la combinaison secr�te ! \n Voulez-vous rejouer ?",
										"Victoire", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
					else if(joueur.getVictoire() == false && tourDeJeu+1 == nbCoupsConfig) {
						resultCompa = joueur.getResultCompa();
						listResult[tourDeJeu].setText(resultCompa);
						JOptionPane jop = new JOptionPane();
						int option = jop.showConfirmDialog(null, "C'est perdu ! \n La combinaison gagnante est "+joueur.getCombiSecret()+"\n Voulez-vous rejouer ?",
										"D�faite", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
				}				
			});
		}
		
		else if(pMode.equals(ModeJeu.DUEL.toString())) {
			this.joueur = new JoueurHumain(lgueurCombo, jeu);
			this.joueur.addObservateur(new Observateur() {
				public void update(String coupJoue) {
					tourDeJeu = joueur.getTourDeJeu();
					logger.debug("Ctrl TourDeJeu : "+tourDeJeu);
					coupJoue = propJH[tourDeJeu].getText();
					joueur.jeu(coupJoue);
					resultCompa = joueur.getResultCompa();
					listResultJH[tourDeJeu].setText(resultCompa);
					
					
					if(joueur.getVictoire() == false && tourDeJeu+1 < nbCoupsConfig) {
						propJH[tourDeJeu + 1].setEditable(true);
						//joueur.setTourDeJeu(tourDeJeu++);
					}
					else if (joueur.getVictoire() == true) {
						JOptionPane jop = new JOptionPane();
						int option = jop.showConfirmDialog(null, "F�licitation, "+joueur.getNom()+" a trouv� la combinaison secr�te ! \n "
								+ "La combinaison de votre adversaire �tait : "+joueur1.getCombiSecret()+" \n Voulez-vous rejouer ?",
										"Victoire", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
					else if(joueur.getVictoire() == false && tourDeJeu+1 == nbCoupsConfig) {
						JOptionPane jop = new JOptionPane();
						int option = jop.showConfirmDialog(null, "C'est perdu ! \n La combinaison gagnante est "+joueur.getCombiSecret()+"\n Voulez-vous rejouer ?",
										"D�faite", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
				}				
			});
			this.joueur1 = new JoueurElectronique(lgueurCombo, jeu);
			this.joueur1.addObservateur(new Observateur() {
				public void update(String coupJoue) {
					tourDeJeu = joueur1.getTourDeJeu();	
					logger.debug("Ctrl TourDeJeu : "+tourDeJeu);
					joueur1.jeu(coupJoue);
					propJE[tourDeJeu].setText(joueur1.getPropOrdi());
					resultCompa = joueur1.getResultCompa();
					listResultJE[tourDeJeu].setText(resultCompa);
					
					if(joueur1.getVictoire() == false && tourDeJeu+1 != nbCoupsConfig) {
						propJE[tourDeJeu + 1].setEditable(true);
						//joueur.setTourDeJeu(tourDeJeu++);
					}
					else if (joueur1.getVictoire() == true) {
						JOptionPane jop = new JOptionPane();
						int option = jop.showConfirmDialog(null, "F�licitation, "+joueur1.getNom()+" a trouv� la combinaison secr�te ! \n "
								+ "La combinaison de votre adversaire �tait : "+joueur.getCombiSecret()+" \n Voulez-vous rejouer ?",
										"Victoire", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
					else if(joueur1.getVictoire() == false && tourDeJeu+1 == nbCoupsConfig) {
						JOptionPane jop = new JOptionPane();
						int option = jop.showConfirmDialog(null, "C'est perdu ! \n La combinaison gagnante est "+joueur1.getCombiSecret()+"\n Voulez-vous rejouer ?",
										"D�faite", 
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.OK_OPTION) {
							nouvellePartie();
						}
					}
				}				
			});
		}
		//this.tourDeJeu = this.joueur.getTourDeJeu();
		//--Les composants graphiques
		this.setLayout(new BorderLayout());
		try {
			initTable();
		}catch(ParseException e) {
			e.printStackTrace();
		}
		this.add(panTbleJeu, BorderLayout.CENTER);

	}
	public void initTable() throws ParseException{
	////--Le panneau qui accueille la table de jeu
	//
	//panTbleJeu.setPreferredSize(new Dimension (400, 500));
	//panTbleJeu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	//panTbleJeu.setLayout(new BoxLayout(panTbleJeu, BoxLayout.PAGE_AXIS));
	//panTbleJeu.setBackground(Color.WHITE);
	//
	//Font police = new Font("Arial", Font.BOLD, 18);
	//JLabel[] listEssai = new JLabel[this.nbCoupsConfig];//--une liste d'�tiquette qui accueille le n� de la tentative
    //
	//JPanel[] panRef = new JPanel[this.nbCoupsConfig];//--Une liste de JPanel 
	//
	////--On applique un maskFormatter au JFormattedTextField pour s'assurer de la validit� de la saisie
	//String[] listDiese = new String[this.lgueurCombo];
	//String str = "";
	//for (int k = 0; k<this.lgueurCombo; k++) {
	//	listDiese[k] = "#";
	//	str += listDiese[k];
	//}
	//MaskFormatter mask = new MaskFormatter(str);
    //
	//this.listProp = new JFormattedTextField[this.nbCoupsConfig];
	//this.listResult = new JLabel[this.nbCoupsConfig];
	//
	//for(int i = 0; i<nbCoupsConfig; i++) {
	//	//--L'etiquette essai n�
	//	listEssai[i] = new JLabel(String.valueOf(i+1));
	//	listEssai[i].setFont(police);
	//	listEssai[i].setBackground(Color.white);
	//	
	//	//--La zone de texte ou s'effectue la saisie
	//	listProp[i] = new JFormattedTextField(mask);
	//	listProp[i].setFont(police);
	//	listProp[i].setBackground(Color.white);
	//	listProp[i].setPreferredSize(new Dimension (100, 50));
	//	listProp[i].setEditable(false);
	//	listProp[i].setHorizontalAlignment(JFormattedTextField.CENTER);
	//	listProp[i].addActionListener(new ActionListener() {
	//		@Override
	//		public void actionPerformed(ActionEvent arg0) {
	//			joueur.updateObservateur();
	//		}
	//	});
	//	
	//	//--L'�tiquette qui affiche le r�sultat de la comparaison entre la saisie et la combinaison gagnante
	//	listResult[i] = new JLabel("");
	//	listResult[i].setFont(police);
	//	listResult[i].setBackground(Color.white);
	//	listResult[i].setPreferredSize(new Dimension (300, 50));
	//	
	//	//--Le panneau qui accueille les 3 composants pr�c�dents
	//	panRef[i] = new JPanel();
	//	panRef[i].setBorder(BorderFactory.createEtchedBorder());
	//	panRef[i].setBackground(Color.WHITE);
	//	panRef[i].setLayout(new BoxLayout(panRef[i], BoxLayout.LINE_AXIS));
	//	panRef[i].add(listEssai[i]);
	//	panRef[i].add(listProp[i]);
	//	panRef[i].add(listResult[i]);
	//	
	//	panTbleJeu.add(panRef[i]);
	//}
	}
	
	public void nouvellePartie() {
	//panTbleJeu.removeAll();
	//resultCompa ="";
    //
	//try {
	//	initTable();
	//}catch(ParseException e) {
	//	e.printStackTrace();
	//}
	//
	//panTbleJeu.revalidate();
	//panTbleJeu.repaint();
	//listProp[0].setEditable(true);
	//this.setLayout(new BorderLayout());
	//this.add(panTbleJeu, BorderLayout.CENTER);
	//this.joueur.initCombiSecret();
	////this.constrCombiSecret = this.joueur.getConstrCombiSecret();
	////this.combiSecret = this.joueur.getCombiSecret();
	//this.joueur.setTourDeJeu(0);
	//this.joueur.setVictoire(false);
	}
	
	public String getNom() {
		return this.nom;
	}
}
