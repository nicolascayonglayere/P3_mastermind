package joueur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Propriete.GestionFichierProperties;
import clavier.Clavier;
import clavier.Observateur_Clavier;

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
	
	public BoiteDialSaisieCouleur(JFrame parent, String ptitre, boolean modal) {
		super(parent, ptitre, modal);
		
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
		//panSaisi.setPreferredSize(new Dimension (200, 50));
		GridLayout gl  = new GridLayout(1,lgueurCombo);
		gl.setHgap(20);
		panSaisi.setLayout(gl);
		
		this.listLbl = new JLabel[lgueurCombo];
		for (int i = 0; i< lgueurCombo; i++) {
			listLbl[i] = new JLabel();
			//listLbl[i].setPreferredSize(new Dimension(50,50));
			//listLbl[i].setSize(new Dimension(50,50));
			listLbl[i].setBackground(Color.LIGHT_GRAY);
			listLbl[i].setOpaque(true);
			listLbl[i].setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
			panSaisi.add(listLbl[i]);
		}
		
		//--Le clavier couleur
		Clavier clavier = new Clavier(1);
		clavier .addObservateur(new Observateur_Clavier() {

			@Override
			public void update(String pLettre) {
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

				//logger.debug("le tour du joueur : "+joueur.getTourDeJeu());
				//logger.debug("la lettre transmise : "+pLettre+" - "+Integer.valueOf(pLettre));
				//listLbl[cpteurLettre].setIcon(listImageColor[Integer.valueOf(pLettre)]);
				listLbl[cpteurLettre].setBackground(listColor[Integer.valueOf(pLettre)]);
				listLbl[cpteurLettre].setForeground(listColor[Integer.valueOf(pLettre)]);
				listLbl[cpteurLettre].setText(pLettre);
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
