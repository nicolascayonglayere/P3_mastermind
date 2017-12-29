package mastermind;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import ihm.Accueil;
import ihm.TableDeJeu;
/**
 * La classe Mastermind hérite de JFrame et contient le Main
 * @author nicolas
 *
 */
public class Mastermind extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JMenuBar barre = new JMenuBar();
	
	private JMenu menuJeu = new JMenu("Jeu");
	private JMenuItem nvelle = new JMenuItem("nouvelle partie");
	private JMenuItem quit = new JMenuItem("quitter");
	
	private JMenu aPropos = new JMenu("A propos");
	private JMenuItem contAPropos = new JMenuItem("?");
	
	private Accueil accueil = new Accueil();
	private TableDeJeu tbleJeu = new TableDeJeu();
	
	/**
	 * Constructeur sans parametre
	 */
	public Mastermind() {
		//--Param de la fenetre
		this.setTitle("LE MASTERMIND");
		this.setForeground(Color.BLACK);
		this.setBackground(Color.WHITE);
		this.setSize(new Dimension (800,650));
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				quit.doClick();
			}
		});
		
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
		menuJeu.addSeparator();
		menuJeu.add(quit);
		
		nvelle.addActionListener(new NewGameListener());
		nvelle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK ));
		
		//--une boite de dialogue lorsque l'on quitte
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop = new JOptionPane();
				int option = jop.showConfirmDialog(null, "Voulez-vous quitter ?",
								"Quitter", 
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.OK_OPTION) 
					System.exit(0);
			}
		});
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK ));
		
		//--Le menu a propos
		aPropos.add(contAPropos);
		contAPropos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop = new JOptionPane();
				String message = "Appli du Mastermind. Pas de copyright !\n";
				message += "2017 - Version 1";
				jop.showMessageDialog(null, message, "A propos", JOptionPane.INFORMATION_MESSAGE);
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
			//case("Regles") : this.getContentPane().add(regle); break;
			//case("Scores") : this.getContentPane().add(score);break;
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
			afficher(tbleJeu.getNom());
			
		}
		
	}
	
	
	
	public static void main(String[] args) {
		Mastermind f = new Mastermind();
		f.setVisible(true);

	}

}
