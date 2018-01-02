package Propriete;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.text.MaskFormatter;

import ihm.TableDeJeu;
import pattern_observer.Observable;
import pattern_observer.Observateur;
/**
 * La classe qui définit la boite de dialogue permettant la selection des proprietes du jeu
 * @author nicolas
 *
 */
public class BoiteDialogue extends JDialog implements Observable{

	/**
	 * variable d'instance
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panContent;
	private JRadioButton plusMoinsBton, masterBton, challBton, defBton, duelBton;
	private JFormattedTextField saisiEssai;
	private JComboBox<String> saisiLgueurCombi;
	private Properties listProp = new Properties();
	private ArrayList<Observateur> listObs = new ArrayList<Observateur>();
	
	
	
	/**
	 * Constructeur avec param
	 * @param parent
	 * @param ptitre
	 * @param modal
	 */
	public BoiteDialogue(JFrame parent, String ptitre, boolean modal) {
		super(parent, ptitre, modal);
		this.setSize(800,300);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
				
		//--Une etiquette de titre
		JLabel lblTitre = new JLabel("Faites votre jeu !", JLabel.CENTER);
		Font policetitre = new Font("Arial", Font.BOLD, 25);
		lblTitre.setFont(policetitre);
		lblTitre.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//--Les différents composants graphiques
		this.initComp();
	
		//--Le panneau qui recueille les boutons
		JPanel panBouton = new JPanel();
		panBouton.setLayout(new FlowLayout());
		panBouton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//--Le bouton OK
		JButton okBton = new JButton("OK");
		okBton.addActionListener(new OKListener());
		
		//--Le bouton ANNULER
		JButton annulBton = new JButton("Annuler");
		annulBton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		panBouton.add(okBton);
		panBouton.add(annulBton);
		
		this.getContentPane().add(lblTitre, BorderLayout.NORTH);
		this.getContentPane().add(panContent, BorderLayout.CENTER);
		this.getContentPane().add(panBouton, BorderLayout.SOUTH);
		
		this.setVisible(true);
		
	}
	/**
	 * Methode gerant la mise en place des différents composants graphiques de la boite de dialogue
	 */
	public void initComp() {
		//--Le choix du jeu
		JPanel panJeu = new JPanel();
		panJeu.setBackground(Color.WHITE);
		panJeu.setPreferredSize(new Dimension (350, 100));
		panJeu.setBorder(BorderFactory.createTitledBorder("CHOIX DU JEU"));
		
		plusMoinsBton = new JRadioButton(TypeJeu.RECHERCHE_NUM.toString());
		plusMoinsBton.setBackground(Color.WHITE);
		masterBton = new JRadioButton(TypeJeu.MASTERMIND.toString());
		masterBton.setBackground(Color.WHITE);
		ButtonGroup bgJeu = new ButtonGroup();
		bgJeu.add(plusMoinsBton);
		bgJeu.add(masterBton);
		panJeu.add(plusMoinsBton);
		panJeu.add(masterBton);
		
		//--Le mode de jeu
		JPanel panMode = new JPanel();
		panMode.setBackground(Color.WHITE);
		panMode.setPreferredSize(new Dimension(350,100));
		panMode.setBorder(BorderFactory.createTitledBorder("MODE DE JEU"));
		
		challBton = new JRadioButton(ModeJeu.CHALLENGER.toString());
		challBton.setBackground(Color.WHITE);
		defBton = new JRadioButton(ModeJeu.DEFENSEUR.toString());
		defBton.setBackground(Color.WHITE);
		duelBton = new JRadioButton(ModeJeu.DUEL.toString());
		duelBton.setBackground(Color.WHITE);
		ButtonGroup bgMode = new ButtonGroup();
		bgMode.add(challBton);
		bgMode.add(defBton);
		bgMode.add(duelBton);
		panMode.add(challBton);
		panMode.add(defBton);
		panMode.add(duelBton);
		
		
		//--Le nombre d'essai possible
		JPanel panEssai = new JPanel();
		panEssai.setBackground(Color.WHITE);
		panEssai.setPreferredSize(new Dimension (350, 100));
		panEssai.setBorder(BorderFactory.createTitledBorder("NOMBRE D'ESSAIS"));
		
		JLabel lblEssai = new JLabel("Veuillez saisir le nombre d'essais possibles : ");
		try {
			MaskFormatter mask1 = new MaskFormatter("##");
			saisiEssai = new JFormattedTextField(mask1);
			saisiEssai.setPreferredSize(new Dimension(30,30));
			panEssai.add(lblEssai);
			panEssai.add(saisiEssai);
		}catch(ParseException e) {
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Veuillez saisir un nombre","Attention",JOptionPane.WARNING_MESSAGE);
		}
		
		//--La longueur de la combinaison secrete
		JPanel panCombi = new JPanel();
		panCombi.setBackground(Color.WHITE);
		panCombi.setPreferredSize(new Dimension(350, 100));
		panCombi.setBorder(BorderFactory.createTitledBorder("LONGUEUR DE LA COMBINAISON SECRETE"));
		
		JLabel lblCombi = new JLabel("Veuillez saisir la longueur de la combinaison secrète : ");
		saisiLgueurCombi = new JComboBox<String>();//--Essai avec Integer mais je ne suis jamais arrive a afficher quoiquecesoit
		saisiLgueurCombi.addItem("4");
		saisiLgueurCombi.addItem("5");
		saisiLgueurCombi.addItem("6");
		saisiLgueurCombi.addItem("7");
		saisiLgueurCombi.addItem("8");
		saisiLgueurCombi.addItem("9");
		saisiLgueurCombi.addItem("10");		
		//for (int i = 4; i == 10; i++)
		//	saisiLgueurCombi.addItem(String.valueOf(i));
		panCombi.add(lblCombi);
		panCombi.add(saisiLgueurCombi);
		
		//--Le panel qui accueille les composants précédents
		this.panContent = new JPanel();
		panContent.setLayout(new FlowLayout());
		panContent.setBackground(Color.WHITE);
		panContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panContent.add(panJeu);
		panContent.add(panMode);
		panContent.add(panEssai);
		panContent.add(panCombi);
		
	}
	
	/**
	 * classe interne gérant le comportement du bouton OK : export des différents choix ds un fichier .properties
	 * @author nicolas
	 *
	 */
	class OKListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//--On définit les propriétés
			if(plusMoinsBton.isSelected())
				listProp.setProperty("jeu", TypeJeu.RECHERCHE_NUM.toString());
			else if (masterBton.isSelected())
				listProp.setProperty("jeu", TypeJeu.MASTERMIND.toString());
			
			if(challBton.isSelected())
				listProp.setProperty("mode", ModeJeu.CHALLENGER.toString());
			else if(defBton.isSelected())
				listProp.setProperty("mode", ModeJeu.DEFENSEUR.toString());
			else if (duelBton.isSelected())
				listProp.setProperty("mode", ModeJeu.DEFENSEUR.toString());
			
			listProp.setProperty("nombres d'essai", saisiEssai.getText());
			
			listProp.setProperty("longueur combinaison", saisiLgueurCombi.getSelectedItem().toString());
			
			//--On écrit dans le fichier
			GestionFichierProperties gfp = new GestionFichierProperties();
			gfp.ecrireProp(listProp);
			
			//--On lance le jeu
			TableDeJeu tble = new TableDeJeu();
			tble.nouvellePartie();
			
			//--On affiche le jeu
			updateObservateur();
			
			setVisible(false);
		}
		
	}

	@Override
	public void addObservateur(Observateur o) {
		this.listObs.add(o);
		
	}
	@Override
	public void updateObservateur() {
		for(Observateur o : listObs)
			o.update("Refresh");
		
	}
	@Override
	public void delObservateur() {
		this.listObs = new ArrayList<Observateur>();
		
	}
}
