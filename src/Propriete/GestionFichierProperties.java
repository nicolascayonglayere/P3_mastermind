package Propriete;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import test_unitaire_table_jeu.TableDeJeu_Test;



/**
 * Classe gérant le fichier properties
 * @author nicolas
 *
 */
public class GestionFichierProperties {
	static Logger logger = Logger.getLogger(GestionFichierProperties.class);
	
	private File configFile;
	private File defautConfigFile;
	private Properties prop;
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	/**
	 * Constructeur sans parametre
	 */
	public GestionFichierProperties(){
		this.configFile = new File("Ressources/Fichiers/config.properties");
		this.defautConfigFile = new File("Ressources/Fichiers/defaut_config.properties");
		this.prop = new Properties();
	}
	
	/**
	 * Methode gérant l'écriture du fichier
	 */
	public void ecrireProp(Properties pListProp) {
		//--si le fichier defaut_config n'existe pas, on le crée avec les les valeurs de la boite de dialogue comme propriétés par défaut
		if (! defautConfigFile.exists()) {
			try {
                defautConfigFile.createNewFile();
                this.prop = pListProp;
				
                oos = new ObjectOutputStream(
						new BufferedOutputStream(
								new FileOutputStream(defautConfigFile)));
				prop.store(oos, null);

				//System.out.println("Taille du fichier = " + defautConfigFile.length()); //Controle
				logger.info("Taille du fichier = " + defautConfigFile.length());

			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		//--sinon, on crée (s'il n'existe pas deja) un fichier config vide et on écrit les valeurs de la boite de dialogue
		else {
			try {
				if(!configFile.exists()) {
					try {
						configFile.createNewFile();
						this.prop = pListProp;
						
		                oos = new ObjectOutputStream(
								new BufferedOutputStream(
										new FileOutputStream(configFile)));
						prop.store(oos, null);
						
						//System.out.println("Taille du fichier = " + configFile.length()); //Controle
						logger.info("Taille du fichier = " + configFile.length());
					}catch (FileNotFoundException e) {
						e.printStackTrace();
					}catch (IOException e) {
						e.printStackTrace();
					}
				}
				else {//--si le fichier existe deja on ecrit les valeurs de la boite de dialogue
					this.prop = pListProp;
					
					oos = new ObjectOutputStream(
							new BufferedOutputStream(
									new FileOutputStream(configFile)));
					prop.store(oos, null);
					//System.out.println("Taille du fichier = " + configFile.length()); //Controle
					logger.info("Taille du fichier = " + configFile.length());
					
					oos.close();
				}
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();				
			}
		}
	}
	
	public Properties lireProp() {
		//--Si le fichier existe, on verifie s'il n'est pas vide auquel cas on le lit et on cree le contenu de notre listJoueur
		try {
			if (configFile.exists()) {
				if (configFile.length() !=0) {
					ois = new ObjectInputStream(
								new BufferedInputStream(
										new FileInputStream(configFile)));
					this.prop.load(ois);
					//System.out.println("list proprietes recup sur le fichier lors de lecture : \n"+this.prop.toString());//Controle
					logger.info("list proprietes recup sur le fichier lors de lecture : \n"+this.prop.toString());
					ois.close();
				}
			}
			//--Sinon, on le cree on recupere les prop par defaut
			else {
				ois = new ObjectInputStream(
						new BufferedInputStream(
								new FileInputStream(defautConfigFile)));
				this.prop.load(ois);
				//System.out.println("list proprietes recup sur le fichier lors de lecture : \n"+this.prop.toString());//Controle
				logger.info("list proprietes recup sur le fichier lors de lecture : \n"+this.prop.toString());
				ois.close();
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return this.prop;
	}
}
