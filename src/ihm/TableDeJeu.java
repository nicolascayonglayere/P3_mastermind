package ihm;

import java.awt.BorderLayout;
import java.text.ParseException;
import java.util.Properties;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import Propriete.GestionFichierProperties;
import Propriete.ModeJeu;
import joueur.Joueur;
import joueur.JoueurElectronique;
import joueur.JoueurHumain;
import pattern_observer.Observateur;
import test_unitaire_pattern_obs.TableDeJeu_Pattern_Obs;

public abstract class TableDeJeu extends JPanel {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(TableDeJeu_Pattern_Obs.class);
	protected Joueur joueur, joueur1;
	private String nom = "Table De Jeu";
	
	protected Properties propriete;
	protected String jeu, modeJeu;
	protected int nbCoupsConfig; 
	protected int lgueurCombo;
	
	private int tourDeJeu ;
	protected JLabel[] listResult;//--Une liste d'étiquette qui accueille le résultat de l'essai
	protected JLabel[] listResultJH, listResultJE;
	protected JFormattedTextField[] listProp;//--Une liste de champs de saisie pour saisir son coup
	protected JFormattedTextField[] propJH, propJE;
	
	//private String coupJoue ;
	//private Integer[] listPropJoueur ;
	protected String resultCompa;
	
	protected JPanel panTbleJeu = new JPanel();
	
	public TableDeJeu() {
		//--on récupère les proprietes
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
	
	public TableDeJeu(String pJeu, String pMode, int pEssai, int pCombo) {
			this.jeu = pJeu;
			this.modeJeu = pMode;
			this.nbCoupsConfig = pEssai;
			this.lgueurCombo = pCombo;
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
					coupJoue = listProp[tourDeJeu].getText();
					joueur.jeu(coupJoue);
					resultCompa = joueur.getResultCompa();
					listResult[tourDeJeu].setText(resultCompa);
					
					
					if(joueur.getVictoire() == false && tourDeJeu+1 < nbCoupsConfig) {
						listProp[tourDeJeu + 1].setEditable(true);
						//joueur.setTourDeJeu(tourDeJeu++);
					}
					else if (joueur.getVictoire() == true) {
						JOptionPane jop = new JOptionPane();
						int option = jop.showConfirmDialog(null, "Félicitation, vous avez trouvé la combinaison secrète ! \n Voulez-vous rejouer ?",
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
										"Défaite", 
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
					listProp[tourDeJeu].setText(joueur.getPropOrdi());
					resultCompa = joueur.getResultCompa();
					listResult[tourDeJeu].setText(resultCompa);
					
					if(joueur.getVictoire() == false && tourDeJeu+1 != nbCoupsConfig) {
						listProp[tourDeJeu + 1].setEditable(true);
						//joueur.setTourDeJeu(tourDeJeu++);
					}
					else if (joueur.getVictoire() == true) {
						JOptionPane jop = new JOptionPane();
						int option = jop.showConfirmDialog(null, "Félicitation, vous avez trouvé la combinaison secrète ! \n Voulez-vous rejouer ?",
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
										"Défaite", 
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
						int option = jop.showConfirmDialog(null, "Félicitation, "+joueur.getNom()+" a trouvé la combinaison secrète ! \n "
								+ "La combinaison de votre adversaire était : "+joueur1.getCombiSecret()+" \n Voulez-vous rejouer ?",
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
										"Défaite", 
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
						int option = jop.showConfirmDialog(null, "Félicitation, "+joueur1.getNom()+" a trouvé la combinaison secrète ! \n "
								+ "La combinaison de votre adversaire était : "+joueur.getCombiSecret()+" \n Voulez-vous rejouer ?",
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
										"Défaite", 
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
	//JLabel[] listEssai = new JLabel[this.nbCoupsConfig];//--une liste d'étiquette qui accueille le n° de la tentative
    //
	//JPanel[] panRef = new JPanel[this.nbCoupsConfig];//--Une liste de JPanel 
	//
	////--On applique un maskFormatter au JFormattedTextField pour s'assurer de la validité de la saisie
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
	//	//--L'etiquette essai n°
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
	//	//--L'étiquette qui affiche le résultat de la comparaison entre la saisie et la combinaison gagnante
	//	listResult[i] = new JLabel("");
	//	listResult[i].setFont(police);
	//	listResult[i].setBackground(Color.white);
	//	listResult[i].setPreferredSize(new Dimension (300, 50));
	//	
	//	//--Le panneau qui accueille les 3 composants précédents
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
