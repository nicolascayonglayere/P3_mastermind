package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

/**
 * Table de jeu pour 2 joueurs, hérite de TableDeJeu
 * @author nicolas
 *
 */
public class TableDeJeu_2 extends TableDeJeu {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur sans parametre
	 */
	public TableDeJeu_2() {
		super();
	}
	
	/**
	 * Constructeur avec parametre
	 * @param pJeu
	 * @param pMode
	 * @param pEssai
	 * @param pCombo
	 */
	public TableDeJeu_2(String pJeu, String pMode, int pEssai, int pCombo, int pModeDev, int pCouleur) {
		super(pJeu, pMode, pEssai, pCombo, pModeDev, pCouleur);
	}
	
	/**
	 * Méthode initialisant les composants graphiques de la table
	 */
	public void initTable() throws ParseException{
		//--Le panneau qui accueille la table de jeu
		
		panTbleJeu.setPreferredSize(new Dimension (400, 500));
		panTbleJeu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panTbleJeu.setLayout(new BoxLayout(panTbleJeu, BoxLayout.PAGE_AXIS));
		panTbleJeu.setBackground(Color.WHITE);
		
		Font police = new Font("Arial", Font.BOLD, 18);
		JPanel[] panRef = new JPanel[this.nbCoupsConfig];//--Une liste de JPanel 
			
		//--On applique un maskFormatter au JFormattedTextField pour s'assurer de la validité de la saisie
		String[] listDiese = new String[this.lgueurCombo];
		String str = "";
		for (int k = 0; k<this.lgueurCombo; k++) {
			listDiese[k] = "#";
			str += listDiese[k];
		}
		MaskFormatter mask = new MaskFormatter(str);

		//this.listProp = new JFormattedTextField[this.nbCoupsConfig];
		this.listResultJH = new JLabel[this.nbCoupsConfig];
		this.listResultJE = new JLabel[this.nbCoupsConfig];
		this.propJH = new JFormattedTextField[this.nbCoupsConfig];
		this.propJE = new JFormattedTextField[this.nbCoupsConfig];
		
		for(int i = 0; i<nbCoupsConfig; i++) {
			//--Un panel primitif qui accueille le tour de chaque joueur
			JPanel panJoueurHumain = new JPanel();
			panJoueurHumain.setLayout(new BoxLayout(panJoueurHumain, BoxLayout.LINE_AXIS));
			JPanel panJoueurElectronique = new JPanel();
			panJoueurElectronique.setLayout(new BoxLayout(panJoueurElectronique, BoxLayout.LINE_AXIS));
							
			JLabel etqTourJH = new JLabel("Tour de : "+joueur.getNom());
			etqTourJH.setFont(police);
			etqTourJH.setBackground(Color.WHITE);
			panJoueurHumain.add(etqTourJH);
			
			JLabel etqTourJE = new JLabel("Tour de : "+joueur1.getNom());
			etqTourJE.setFont(police);
			etqTourJE.setBackground(Color.WHITE);
			panJoueurElectronique.add(etqTourJE);
			
			propJH[i] = new JFormattedTextField(mask);
			propJH[i].setFont(police);
			propJH[i].setBackground(Color.WHITE);
			propJH[i].setPreferredSize(new Dimension(100, 50));
			propJH[i].setEditable(false);
			propJH[i].setHorizontalAlignment(JFormattedTextField.CENTER);
			propJH[i].addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					joueur.updateObservateur();						
				}
				
			});
			panJoueurHumain.add(propJH[i]);
			
			propJE[i] = new JFormattedTextField(mask);
			propJE[i].setFont(police);
			propJE[i].setBackground(Color.WHITE);
			propJE[i].setPreferredSize(new Dimension(100, 50));
			propJE[i].setEditable(false);
			propJE[i].setHorizontalAlignment(JFormattedTextField.CENTER);
			propJE[i].addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					joueur1.updateObservateur();						
				}					
			});
			panJoueurElectronique.add(propJE[i]);
			
			listResultJH[i] = new JLabel("");
			listResultJH[i].setFont(police);
			listResultJH[i].setBackground(Color.WHITE);
			listResultJH[i].setPreferredSize(new Dimension(300,50));
			panJoueurHumain.add(listResultJH[i]);
			listResultJE[i] = new JLabel("");
			listResultJE[i].setFont(police);
			listResultJE[i].setBackground(Color.WHITE);
			listResultJE[i].setPreferredSize(new Dimension(300,50));
			panJoueurElectronique.add(listResultJE[i]);
	
		
			panRef[i] = new JPanel();
			panRef[i].setLayout(new BoxLayout(panRef[i], BoxLayout.PAGE_AXIS));
			panRef[i].setBorder(BorderFactory.createEtchedBorder());
			panRef[i].setBackground(Color.WHITE);
			panRef[i].add(panJoueurHumain);
			panRef[i].add(panJoueurElectronique);
			
			panTbleJeu.add(panRef[i]);
		}
	}
	
	/**
	 * Méthode initialisant une nouvelle partie
	 */
	public void nouvellePartie() {
		panTbleJeu.removeAll();
		resultCompa ="";

		try {
			initTable();
		}catch(ParseException e) {
			e.printStackTrace();
		}
		logger.debug("Ctrl modeDev : "+modeDev);
		if (modeDev == 1) {
			modeDevLbl = new JLabel("Combinaisons secrètes : ");
			Font police = new Font("Arial", Font.BOLD, 14);
			modeDevLbl.setFont(police);
			modeDevLbl.setPreferredSize(new Dimension(800,50));
			modeDevLbl.setHorizontalTextPosition(JLabel.CENTER);
			
			panTbleJeu.add(modeDevLbl);
		}
		else {
			modeDevLbl = new JLabel("");
			modeDevLbl.setVisible(false);
		}
		
		panTbleJeu.revalidate();
		panTbleJeu.repaint();
		propJH[0].setEditable(true);
		propJE[0].setEditable(true);
		this.setLayout(new BorderLayout());
		this.add(panTbleJeu, BorderLayout.CENTER);
		this.joueur.initCombiSecret();
		this.joueur1.initCombiSecret();
		modeDevLbl.setText("La combo du joueur humain : "+String.valueOf(this.joueur.getCombiSecret())+
						   " - La combo de l'ordi : "+String.valueOf(this.joueur1.getCombiSecret()));
		this.joueur.setTourDeJeu(0);
		this.joueur.setVictoire(false);
		this.joueur1.setTourDeJeu(0);
		this.joueur1.setVictoire(false);
	}
}
