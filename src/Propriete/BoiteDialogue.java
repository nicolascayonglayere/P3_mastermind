package Propriete;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.text.MaskFormatter;

/**
 * La classe qui définit la boite de dialogue permettant la selection des proprietes du jeu
 * @author nicolas
 *
 */
public class BoiteDialogue extends JDialog {

	/**
	 * variable d'instance
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panContent;
	private JPanel panCouleur = new JPanel();
	private JRadioButton plusMoinsBton, masterBton, challBton, defBton, duelBton, chiffreBton, couleurBton;
	private JFormattedTextField saisiEssai;
	private JComboBox<String> saisiLgueurCombi;
	private JCheckBox modeDevBox;
	private Properties listProp = new Properties();
	
	
	
	
	/**
	 * Constructeur avec param
	 * @param parent
	 * @param ptitre
	 * @param modal
	 */
	public BoiteDialogue(JFrame parent, String ptitre, boolean modal) {
		super(parent, ptitre, modal);
		this.setSize(800,400);
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
		plusMoinsBton.setSelected(true);
		masterBton = new JRadioButton(TypeJeu.MASTERMIND.toString());
		masterBton.setBackground(Color.WHITE);
		masterBton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//--Choix entre chiffre et couleur

				panCouleur.setBackground(Color.WHITE);
				panCouleur.setPreferredSize(new Dimension(350,100));
				panCouleur.setBorder(BorderFactory.createTitledBorder("CHOIX DE LA NATURE DE LA COMBINAISON"));
				
				chiffreBton = new JRadioButton("CHIFFRES");
				chiffreBton.setBackground(Color.WHITE);
				couleurBton = new JRadioButton("COULEURS");
				couleurBton.setBackground(Color.WHITE);
				
				ButtonGroup bgCouleur = new ButtonGroup();
				bgCouleur.add(chiffreBton);
				bgCouleur.add(couleurBton);
				panCouleur.add(chiffreBton);
				panCouleur.add(couleurBton);
				
				panContent.add(panCouleur);				
			}
			
		});
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
		challBton.setSelected(true);
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
			saisiEssai.setText("10");
			panEssai.add(lblEssai);
			panEssai.add(saisiEssai);
		}catch(ParseException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Veuillez saisir un nombre","Attention",JOptionPane.WARNING_MESSAGE);
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

		panCombi.add(lblCombi);
		panCombi.add(saisiLgueurCombi);

		//--Le mode développement
		JPanel panDev = new JPanel();
		panDev.setBackground(Color.WHITE);
		panDev.setBorder(BorderFactory.createTitledBorder("MODE DEVELOPPEMENT"));
		modeDevBox = new JCheckBox("Mode Developpement");
		modeDevBox.setBackground(Color.WHITE);
		panDev.add(modeDevBox);
		
		
		//--Le panel qui accueille les composants précédents
		this.panContent = new JPanel();
		panContent.setLayout(new FlowLayout());
		panContent.setBackground(Color.WHITE);
		panContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panContent.add(panJeu);
		panContent.add(panMode);
		panContent.add(panEssai);
		panContent.add(panCombi);
		panContent.add(panCouleur);	
		panContent.add(panDev);
		
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
			else if (masterBton.isSelected()) {
				listProp.setProperty("jeu", TypeJeu.MASTERMIND.toString());
				
				if(chiffreBton.isSelected() || (!chiffreBton.isSelected() && !couleurBton.isSelected()))
					listProp.setProperty("couleur", "0");
				else if(couleurBton.isSelected())
					listProp.setProperty("couleur", "1");
			}
			
			if(challBton.isSelected())
				listProp.setProperty("mode", ModeJeu.CHALLENGER.toString());
			else if(defBton.isSelected())
				listProp.setProperty("mode", ModeJeu.DEFENSEUR.toString());
			else if (duelBton.isSelected())
				listProp.setProperty("mode", ModeJeu.DUEL.toString());
			

			listProp.setProperty("nombres d'essai", saisiEssai.getText());

			
			listProp.setProperty("longueur combinaison", saisiLgueurCombi.getSelectedItem().toString());
			
			if(modeDevBox.isSelected())
				listProp.setProperty("developpement", "1");
			else
				listProp.setProperty("developpement", "0");
			

			
			//--On écrit dans le fichier
			GestionFichierProperties gfp = new GestionFichierProperties();
			gfp.ecrireProp(listProp);
			
			if (saisiEssai.getValue() != null) {
				setVisible(false);
			}
			else {
				JOptionPane.showMessageDialog(null, "Veuillez saisir un nombre à deux chiffres entre 01 et 99", "Attention nombres essais", JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}

}
