package clavier;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


/**
 * La classe définissant un clavier numérique 
 * @author nicolas
 *
 */
public class Clavier extends JPanel implements Observable_Clavier {

	/**
	 * 
	 */
	//--variables d'instance
	private static final long serialVersionUID = 1L;
	private String[] listChar = {"0","1","2","3","4","5","6","7","8","9"};
	private Color[]listColor = {Color.WHITE, Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PINK};
	private HashMap<Color, Integer> listCouleur;
	private JButton[] listBouton = new JButton[this.listColor.length];
	private JPanel panBouton = new JPanel();
	private GridLayout gl = new GridLayout(2,5);
	private String lettre  ;
	//private Color couleur;
		//private char charLettre;
	private int couleurMode = -1;
	private ArrayList<Observateur_Clavier> listObs = new ArrayList<Observateur_Clavier>();

		
		/**
		 * Constructeur du clavier sans parametres
		 */
		public Clavier() {
			//--Le panel qui accueillera les boutons selon un rangement sur grille gl
			gl.setHgap(7);
			gl.setVgap(7);
			panBouton.setLayout(gl);
			//panBouton.addKeyListener(new ClavierListener());
			listCouleur = new HashMap<Color, Integer>();
			Font police = new Font("Arial", Font.BOLD, 18);
			//--On construit nos boutons dans la liste
			for(int i = 0; i <listColor.length; i++) {

				listCouleur.put(listColor[i], i);
				//--on cree nos boutons avec des ecouteurs clavier integres
				listBouton[i]=(createBouton(this.listChar[i], 96+i));
				listBouton[i].setFont(police);
				listBouton[i].setPreferredSize(new Dimension(50,50));
				//--On ajoute les ecouteurs souris sur les boutons, les ecouteurs claviers sont inclus ds les boutons
				listBouton[i].addActionListener(new ClavierListener());
				listBouton[i].setEnabled(true);
			
				//--On ajoute les boutons au panel
				panBouton.add(listBouton[i]);
			}
			//--On ajoute le panel à l'objet
			this.add(panBouton);
		}
		
		public Clavier(int pCouleur) {
			this.setBackground(Color.WHITE);
			this.couleurMode = pCouleur;
			if (pCouleur == 0) {
				listBouton = new JButton[this.listChar.length];
				//--Le panel qui accueillera les boutons selon un rangement sur grille gl
				gl.setHgap(7);
				gl.setVgap(7);
				panBouton.setLayout(gl);
				//listCouleur = new HashMap<Integer, Color>();
				Font police = new Font("Arial", Font.BOLD, 18);
				//--On construit nos boutons dans la liste
				for(int i = 0; i <listChar.length; i++) {
					//--on cree nos boutons avec des ecouteurs clavier integres
					listBouton[i]=(createBouton(this.listChar[i], 96+i));
					//listBouton[i].setBackground(listColor[i]);
					listBouton[i].setFont(police);
					listBouton[i].setPreferredSize(new Dimension(50,50));
					//--On ajoute les ecouteurs souris sur les boutons, les ecouteurs claviers sont inclus ds les boutons
					listBouton[i].addActionListener(new ClavierListener());
					listBouton[i].setEnabled(true);
				
					//--On ajoute les boutons au panel
					panBouton.add(listBouton[i]);
				}
				//--On ajoute le panel à l'objet
				this.add(panBouton);
			}
			else if(pCouleur == 1) {
				//this.listCouleur = new HashMap<Integer, File>();
				//--Le panel qui accueillera les boutons selon un rangement sur grille gl
				gl.setHgap(7);
				gl.setVgap(7);
				panBouton.setLayout(gl);
				listCouleur = new HashMap<Color, Integer>();
				Font police = new Font("Arial", Font.BOLD, 18);
				//--On construit nos boutons dans la liste
				for(int i = 0; i <listColor.length; i++) {
					//--on cree nos boutons avec des ecouteurs clavier integres
					listBouton[i] = new JButton();
					listBouton[i].setBackground(listColor[i]);
					//listBouton[i]=(createBouton(this.listChar[i], 96+i));
					listBouton[i].setFont(police);
					listBouton[i].setPreferredSize(new Dimension(55,55));
					//--On ajoute les ecouteurs souris sur les boutons, les ecouteurs claviers sont inclus ds les boutons
					listBouton[i].addActionListener(new ClavierListener());
					listBouton[i].setEnabled(true);
					listCouleur.put(listColor[i], i);
					//--On ajoute les boutons au panel
					panBouton.add(listBouton[i]);
				}
				//--On ajoute le panel à l'objet
				this.add(panBouton);
				
			}
		}
		/**
		 * Encapsulation de la lettre
		 * @return un char = lettre tapee
		 */
	//public char getLettre() {
	//	return this.charLettre;
	//}
		public String getLettre() {
			return this.lettre;
		}
		
		
		public HashMap<Color, Integer> getListCouleur(){
			return this.listCouleur;
		}
		/**
		 * Pattern Observer; methode d'ajout des abonnes
		 */
		public void addObservateur(Observateur_Clavier o) {
			this.listObs.add(o);
			
		}

		/**
		 * Pattern Observer; mise a jour des abonnes
		 */
		public void updateObservateur() {
			for (Observateur_Clavier o : listObs) {
				//o.update(this.charLettre);
				o.update(this.lettre);
			}
			
		}

		/**
		 * Pattern Observer; suppression d'abonnes
		 */
		public void delObservateur() {
			this.listObs = new ArrayList<Observateur_Clavier>();
			
		}
		/**
		 * Méthode générant un bouton qui bind une touche du clavier
		 * @param name
		 * @param virtualKey
		 * @return
		 */
		public JButton createBouton(String name, int virtualKey) {
	        JButton btn = new JButton(name);
	        btn.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                System.out.println(e.getActionCommand() + " was clicked");
	            }
	        });
	        InputMap im = btn.getInputMap(WHEN_IN_FOCUSED_WINDOW);
	        ActionMap am = btn.getActionMap();
	        im.put(KeyStroke.getKeyStroke(virtualKey, 0), "clickMe");
	        am.put("clickMe", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                JButton btn = (JButton) e.getSource();
	                btn.doClick();
	            }
	        });
	        return btn;
	    }
		
		/**
		 * Classe interne = ecouteur du clavier
		 * @author nicolas
		 *
		 */
		class ClavierListener implements ActionListener {
			/**
			 * methode retournant la lettre lorsqu'on click dessus
			 */
			public void actionPerformed(ActionEvent e) {
				if (couleurMode == 0) {
					lettre = ((JButton)e.getSource()).getText();
					//System.out.println("lettre transmis : "+lettre);
				}
				
				else if (couleurMode == 1) {
					//if(e.getSource() == listBouton[0])
						//lettre = "0";
					//couleur = (Color)e.getSource();
					for (int i = 0 ; i<listColor.length; i++) {
						if (e.getSource() == listBouton[i])
							lettre = String.valueOf((i));
						
						//if(couleur.toString().equals(listCouleur.get(i).toString()))
							//lettre = String.valueOf(i);
					}
				}
			//couleur = (Color)(e.getSource());
			//
			//lettre = String.valueOf(i)
				//charLettre = lettre.charAt(0);
				updateObservateur();
				//((JButton)e.getSource()).setEnabled(false);
			}

		}
}
