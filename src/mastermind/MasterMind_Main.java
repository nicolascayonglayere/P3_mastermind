package mastermind;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import Propriete.BoiteDialogue;
import Propriete.GestionFichierProperties;
import Propriete.ModeJeu;
import Propriete.TypeJeu;
import ihm.Accueil;
import ihm.TableDeJeu;
import ihm.TableDeJeu_3;
import ihm.TableDeJeu_4;
import pattern_observer.Observateur;

/**
 * La classe MasterMind Main hérite de JFrame et affiche les menus l'écran d'accueil et la table de jeu
 * @author nicolas
 *
 */
public class MasterMind_Main extends JFrame {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger("ihm");
	
	private JMenuBar barre = new JMenuBar();
	
	private JMenu menuJeu = new JMenu("Jeu");
	private JMenuItem nvelle = new JMenuItem("nouvelle partie");
	private JMenuItem play = new JMenuItem("jouer");
	private JMenuItem quit = new JMenuItem("quitter");
	
	private JMenu aPropos = new JMenu("A propos");
	private JMenuItem regle = new JMenuItem("regles");
	private JMenuItem contAPropos = new JMenuItem("?");
	
	private Accueil accueil = new Accueil();
	
	private Properties propriete;
	private String typeJeu, modeJeu;
	private int nbCoupsConfig; 
	private int lgueurCombo;
	private int modeDev;
	private int couleur;
	
	private TableDeJeu tbleJeu;
	private Boolean inGame = false;

	
	
	
	/**
	 * Constructeur sans parametre
	 */
	public MasterMind_Main() {
		//--Param de la fenetre
		this.setTitle("LE MASTERMIND");
		this.setForeground(Color.BLACK);
		this.setBackground(Color.WHITE);
		this.setSize(new Dimension (800,900));
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				quit.doClick();
			}
		});
		//--Essai Look&Feel
		try {
			  //On force à utiliser le « look and feel » du système
			  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			  //Ici on force tous les composants de notre fenêtre (this) à se redessiner avec le « look and feel » du système
			  SwingUtilities.updateComponentTreeUI(this);
			}
		catch (InstantiationException e) {
			logger.error(e.getMessage());
		}
		catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
		catch (UnsupportedLookAndFeelException e) {
			logger.error(e.getMessage());
		}
		catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		}
		
		
		//--Les menus de la fenetre
		this.initMenu();
		
				
		//--Le panneau d'accueil
		this.afficher(accueil.getNom());
	}
	
	/**
	 * Initialisation de la barre des menus
	 * @param args
	 */
	public void initMenu() {
		//--Le menu jeu
		menuJeu.add(nvelle);
		menuJeu.add(play);
		menuJeu.addSeparator();
		menuJeu.add(quit);
		
		
		nvelle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//if(tbleJeu.getInGame() == false) {
				if (inGame == false) {
					BoiteDialogue bDialog = new BoiteDialogue(null, "CONFIGURATION DU JEU", true);
					bDialog.addObservateur(new Observateur() {
						@Override
						public void update(Object o) {
							play.doClick();
						}						
					});
					bDialog.setVisible(true);
					
			
				}
				else {
					int jop = JOptionPane.showConfirmDialog(null, "Souhaitez-vous quitter la partie en cours ?", "Attention partie en cours",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if(jop == JOptionPane.YES_OPTION) {
						inGame = false;
						nvelle.doClick();
					}
				}
			}
		});
		nvelle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK ));
		
		play.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK ));
		play.addActionListener(new NewGameListener());
		
		
		//--une boite de dialogue lorsque l'on quitte
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int option = JOptionPane.showConfirmDialog(null, "Voulez-vous quitter ?", "Quitter", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.OK_OPTION) 
					System.exit(0);
			}
		});
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK ));
		
		//--Le menu a propos
		aPropos.add(regle);
		regle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//--on récupère les proprietes du fichier 
				GestionFichierProperties gfp = new GestionFichierProperties();
				propriete = gfp.lireProp();
				typeJeu = String.valueOf(propriete.getProperty("jeu"));
				//System.out.println("Ctrl jeu :"+typeJeu);//--Controle
				logger.info("Ctrl jeu :"+typeJeu);
				modeJeu = String.valueOf(propriete.getProperty("mode"));
				//System.out.println("Ctrl mode : "+modeJeu);//--Controle
				logger.info("Ctrl mode : "+modeJeu);
				nbCoupsConfig = Integer.valueOf(propriete.getProperty("nombres d'essai"));
				//System.out.println("Ctrl nb coup :"+nbCoupsConfig);//--Controle
				logger.info("Ctrl nb coup :"+nbCoupsConfig);
				lgueurCombo = Integer.valueOf(propriete.getProperty("longueur combinaison"));
				//System.out.println("Ctrl lgueur :"+lgueurCombo);//--Controle
				logger.info("Ctrl lgueur :"+lgueurCombo);
				modeDev = Integer.valueOf(propriete.getProperty("developpement"));
				logger.info("Ctrl mode dev : "+modeDev);
				if(typeJeu.equals(TypeJeu.MASTERMIND.toString()))
					couleur = Integer.valueOf(propriete.getProperty("couleur"));
				else 
					couleur = 0;
				logger.info("Ctrl chiffre/couleur : "+couleur);
				
				accueil = new Accueil(typeJeu, modeJeu, nbCoupsConfig, lgueurCombo, couleur);
				afficher(accueil.getNom());					
			}			
		});
		
		aPropos.add(contAPropos);
		contAPropos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message = "Appli du Mastermind. Pas de copyright !\n";
				message += "2017 - Version 1";
				JOptionPane.showMessageDialog(null, message, "A propos", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		//--Ajout des menus dans la barre des menus
		barre.add(menuJeu);
		barre.add(aPropos);
		
		this.setJMenuBar(barre);
	}
	
	/**
	 * Methode pour afficher les differents panneaux selon leur nom
	 * @param pNomPage
	 */
	public void afficher(String pNomPage) {
		this.getContentPane().removeAll();
		switch(pNomPage) {
			case("Accueil") : this.getContentPane().add(accueil);break;
			case("Table De Jeu") : this.getContentPane().add(tbleJeu);break;
		}
		this.getContentPane().revalidate();
		this.getContentPane().repaint();
	}
	
	/**
	 * La méthode appelle un boite de dialogue qui permet de créer une nouvelle partie
	 * @param args
	 */
	class NewGameListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (inGame == false) {
				//--on récupère les proprietes du fichier 
				GestionFichierProperties gfp = new GestionFichierProperties();
				propriete = gfp.lireProp();
				typeJeu = String.valueOf(propriete.getProperty("jeu"));
				//System.out.println("Ctrl jeu :"+typeJeu);//--Controle
				logger.info("Ctrl jeu :"+typeJeu);
				modeJeu = String.valueOf(propriete.getProperty("mode"));
				//System.out.println("Ctrl mode : "+modeJeu);//--Controle
				logger.info("Ctrl mode : "+modeJeu);
				nbCoupsConfig = Integer.valueOf(propriete.getProperty("nombres d'essai"));
				//System.out.println("Ctrl nb coup :"+nbCoupsConfig);//--Controle
				logger.info("Ctrl nb coup :"+nbCoupsConfig);
				lgueurCombo = Integer.valueOf(propriete.getProperty("longueur combinaison"));
				//System.out.println("Ctrl lgueur :"+lgueurCombo);//--Controle
				logger.info("Ctrl lgueur :"+lgueurCombo);
				modeDev = Integer.valueOf(propriete.getProperty("developpement"));
				logger.info("Ctrl mode dev : "+modeDev);
				
				if(typeJeu.equals(TypeJeu.MASTERMIND.toString()))
					couleur = Integer.valueOf(propriete.getProperty("couleur"));
				else 
					couleur = 0;
				logger.info("Ctrl chiffre/couleur : "+couleur);
				
				if((modeJeu.equals(ModeJeu.DUEL.toString()))){
					tbleJeu = new TableDeJeu_4(typeJeu, modeJeu, nbCoupsConfig, lgueurCombo, modeDev, couleur);
					afficher(tbleJeu.getNom());
					tbleJeu.nouvellePartie();
					inGame = tbleJeu.getInGame();
				}
				else {
					tbleJeu = new TableDeJeu_3(typeJeu, modeJeu, nbCoupsConfig, lgueurCombo, modeDev, couleur);
					afficher(tbleJeu.getNom());
					tbleJeu.nouvellePartie();
					inGame = tbleJeu.getInGame();
				}
			}
			else {
				int jop = JOptionPane.showConfirmDialog(null, "Souhaitez-vous quitter la partie en cours ?", "Attention partie en cours",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(jop == JOptionPane.YES_OPTION) {
					inGame = false;
					play.doClick();
				}
			}			
		}
	}
		
	

	public static void main(String[] args) {
		MasterMind_Main f = new MasterMind_Main();
		f.setVisible(true);

	}
}
