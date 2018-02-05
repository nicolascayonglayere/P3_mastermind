package joueur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Propriete.GestionFichierProperties;
import clavier.Clavier;
import pattern_observer.Observateur;

/**
 * Classe definissant une boite de dialogue pour la saisie de la combinaison en couleur
 * @author nicolas
 *
 */
public class BoiteDialSaisieCouleur extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Properties proprietes;
	private int lgueurCombo;
	private JLabel[] listLbl;
	private int cpteurLettre = 0;
	private String combinaison = "";
	
	
	/**
	 * Constructeur avec parametre
	 * @param parent
	 * @param ptitre
	 * @param modal
	 */
	public BoiteDialSaisieCouleur(JFrame parent, String ptitre, boolean modal) {
		super(parent, ptitre, modal);
		
		//--la boite de dialogue
		this.setSize(400,200);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
				
		//--Une etiquette de titre
		JLabel lblTitre = new JLabel("Saisissez la combinaison secrète", JLabel.CENTER);
		Font policetitre = new Font("Arial", Font.BOLD, 18);
		lblTitre.setFont(policetitre);
		lblTitre.setBackground(Color.WHITE);
		lblTitre.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//--on recupere le nombre d'etiquette dans le fichier .properties
		GestionFichierProperties gfp = new GestionFichierProperties();
		proprietes = gfp.lireProp();
		lgueurCombo = Integer.valueOf(proprietes.getProperty("longueur combinaison"));
		
		//--Un panneau qui accueille les etiquettes affichant la couleur
		JPanel panSaisi = new JPanel();
		panSaisi.setBackground(Color.WHITE);
		panSaisi.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
		GridLayout gl  = new GridLayout(1,lgueurCombo);
		gl.setHgap(20);
		panSaisi.setLayout(gl);
		
		//--Les etiquettes affichant la couleur
		this.listLbl = new JLabel[lgueurCombo];
		for (int i = 0; i< lgueurCombo; i++) {
			listLbl[i] = new JLabel();
			listLbl[i].setBackground(Color.LIGHT_GRAY);
			listLbl[i].setOpaque(true);
			listLbl[i].setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
			panSaisi.add(listLbl[i]);
		}
		
		//--Le clavier couleur
		Clavier clavier = new Clavier(1);
		clavier .addObservateur(new Observateur() {

			@Override
			public void update(Object o) {
				//--on met ds la JLabel le fichier couleur du bouton et le chiffre/lettre du bouton
				Color[]listColor = {Color.WHITE, Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PINK};

				listLbl[cpteurLettre].setBackground(listColor[Integer.valueOf(o.toString())]);
				listLbl[cpteurLettre].setForeground(listColor[Integer.valueOf(o.toString())]);
				listLbl[cpteurLettre].setText(o.toString());
				cpteurLettre ++;
			}
			
		});

		//--Le panneau qui recueille les boutons
		JPanel panBouton = new JPanel();
		panBouton.setLayout(new FlowLayout());
		panBouton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panBouton.setBackground(Color.WHITE);
		
		//--Le bouton OK
		JButton okBton = new JButton("OK");
		okBton.addActionListener(new OKListener());
				
		//--Le bouton ANNULER
		JButton annulBton = new JButton("Annuler");
		annulBton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i<lgueurCombo; i++) {
					listLbl[i].setText("0");
					combinaison += listLbl[i].getText();
				}
					setVisible(false);
			}
		});
				
		panBouton.add(clavier);
		panBouton.add(okBton);
		panBouton.add(annulBton);
				
		this.getContentPane().add(lblTitre, BorderLayout.NORTH);
		this.getContentPane().add(panSaisi, BorderLayout.CENTER);
		this.getContentPane().add(panBouton, BorderLayout.SOUTH);
				
		this.setVisible(true);	
		
	}
	public String getCombinaison() {
		return this.combinaison;
	}
	
	/**
	 * classe interne definissant le comportement du bouton OK lorsqu'on clique dessus (on vérifie que la combinaison n'est pas null)
	 * @author nicolas
	 *
	 */
	class OKListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			for (int i = 0; i<lgueurCombo; i++) {
				combinaison += listLbl[i].getText();
			}
			if(combinaison != null)
				setVisible(false);
			else
				JOptionPane.showMessageDialog(null, "Veuillez saisir une combinaison de "+lgueurCombo+" couleurs ", "Attention saisie combinaison", JOptionPane.WARNING_MESSAGE);
			
		}
		
	}

}
