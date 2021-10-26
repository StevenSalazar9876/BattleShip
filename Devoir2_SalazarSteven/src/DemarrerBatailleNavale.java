
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Simule l'intelligence de résolution d'un jeu de bataille navale (Battleship)
 * 
 * Ce programme implémente quelques stratégies possible pour trouver les navires 
 * cachés d'un jeu.
 *  
 *@author Pierre Bélisle
 *@version Copyright A2021
 *
 */

public class DemarrerBatailleNavale {

	/*
	 * Stratégie globale : On utilise une GrilleGui pour obtenir une
	 * grille pour afficher la flotte du joueur que l'ordinateur doit trouver.
	 *                              
	 *     Classes nécessaires de votre tp2 : 
	 *     
	 *     Navire : Les données et les méthodes concernant un navire. 
	 *     GrilleGui : grille d'affichage et de saisie graphique
	 *     UtilitaireGrilleGui : Interface entre le programme et la grilleGui.
	 *     Flotte : Contient tous les navires du joueur
	 *            
	 *     Classe fournies :
	 *     
	 *     StrategieOrdiPremiereFois : Joue n'importe comment
	 *     StrategieOrdiDebutant : Ne jou epas 2 fois au même endroit.
	 *                                  
	 *     UtilitaireCollection : Recherche d'une coordonnée dans une collection.
	 *     UtilitaireFonctions : Permet d'obtenir des tirs au hasard.
	 *                           
	 *                               
	 */

	static int nbRepetitions = 0;
	static int nbTirs = 0;

	public static void main(String[] args) {

		// Création de l'interface graphique qui permet de jouer. 
		GrilleGui gui = new GrilleGui(Constantes.TAILLE, Constantes.TAILLE,
				Constantes.COULEUR_TEXTE, 
				Constantes.COULEUR_FOND,
				Constantes.OPTIONS,
				GrilleGui.QUITTE);

		// Laisse le temps de créer le gui.
		UtilitaireGrilleGui.pause(250);

		// Boucle virtuellement infinie.  Le programme quitte sur X du gui.
		boolean quitter = false;

		while(!quitter){

			// Retiendra le nombre total de fois que les tirs sont allés 
			// sur des cases déjà tirées.
			nbRepetitions = 0;

			//Retient le nombre total de tirs.
			nbTirs = 0;

			demarrerModeOrdi(gui);

			// Petit message qui donne le temps de voir ce qui s'est passée.
			JOptionPane.showMessageDialog(null,"Solution trouvée en " +
			nbTirs 	+ 	" coups avec " + nbRepetitions + " répétition de tirs");

		}
	}

	/**
	 * Procédure qui 
	 * @param gui
	 */
	public static void demarrerModeOrdi(GrilleGui gui){

		// On crée une nouvelle flotte aléatoire et on la montre.
		ArrayList<Navire> flotteOrdi = Flotte.obtenirFlotteAleatoire();

	//	UtilitaireGrilleGui.montrerFlotte(flotteOrdi, gui);

		// On attend que l'utilisateur clique sur un des boutons d'options.
		while(!gui.optionMenuEstCliquee()){
			UtilitaireGrilleGui.pause(1);	
		};

		// On remet la page blanche pour vérifier que l'application trouve bien 
		// les navires.
		UtilitaireGrilleGui.reinitialiserGui(gui);

		// On obtient l'option du menu qui a été cliqué.
		String menu = gui.getOptionMenuClique();

		// On compare les String pour trouver la stratégie choisie.
		// Selon la sélection, on crée le joueur avec la bonne stratégie 
		// et on démarre la partie en lui passant le paramètre approprié.				
		if(menu.equals(
				Constantes.OPTIONS[Constantes.PREMIERE_FOIS])){

			JoueurPremiereFois ordi =  new JoueurPremiereFois();
	//		demarrerPartie(ordi, flotteOrdi, Constantes.PREMIERE_FOIS, gui);

		}
		else if(menu.equals(
				Constantes.OPTIONS[Constantes.DEBUTANT])){

			JoueurDebutant ordi = new JoueurDebutant();
	//		demarrerPartie(ordi, flotteOrdi, Constantes.DEBUTANT, gui);
		}
		else if(menu.equals(
				Constantes.OPTIONS[Constantes.INTERMEDIAIRE])){

	//		JoueurIntermediaire ordi = new JoueurIntermediaire();
	//		demarrerPartie(ordi, flotteOrdi, Constantes.INTERMEDIAIRE, gui);
		}		

		else if(menu.equals(
				Constantes.OPTIONS[Constantes.AVANCE])){

//			JoueurAvance ordi = new JoueurAvance();
		//	demarrerPartie(ordi, flotteOrdi, Constantes.AVANCE, gui);
		}	
		
		// Le choix expert.
		else
			if(JOptionPane.showConfirmDialog(null,"Pas encore implémenté") == 
			JOptionPane.CANCEL_OPTION)
				System.exit(0);
	}

	/**
	 * Démarre la partie avec l'ordi reçu qui peut avoir une stratégie
	 * différente.
	 * 
	 * Principalement pour éviter la répétition de code.
	 * 
	 * @param ordi
	 * @param flotte
	 * @param lequel
	 * @param gui
	 */
	public static void demarrerPartie(Object ordi, 
			Flotte flotte, 
			int lequel, 
			GrilleGui gui){

		while(!flotte.jeuTermine()){
		

			Coord tir = getTirOrdi(ordi, lequel);
			
			// Un tir de plus pour les stats.
			nbTirs++;

			// Compte les répétitions de tirs enregardant si dans 
			// le gui c'est touché.			
			if(gui.getValeur(tir) == Constantes.TOUCHE)
				nbRepetitions++;

			// On montre le tir.
			UtilitaireGrilleGui.afficherTir(gui, tir);
			
			// Donne le temps de voir le tir (debug).
			UtilitaireGrilleGui.pause(100);

			// Si le tir a touché mais à une nouvelle position
			// On affiche la case touchée.
			if(!flotte.dejaRecuCoup(tir)){
				
				if(flotte.leTirTouche(tir)){
					UtilitaireGrilleGui.afficherNavireTouche(gui, tir);
					aviserOrdi(ordi, lequel, tir);
				}				
			}
		}
	}

	/*
	 * Retourne le tir selon le niveau du joueur fourni
	 * avec le deuxième paramètre.
	 * 
	 * @param ordi
	 * @param lequel Le niveau du joueur
	 * 
	 * @return La coord du tir de l'ordi.
	 */
	private static Coord getTirOrdi(Object ordi, int lequel){
		
		//Tir à retourner
		Coord tir = null;
		
		// On présume que le paramètre est valide.
		switch (lequel){
	
			case Constantes.PREMIERE_FOIS : {
	
				tir =((JoueurPremiereFois) ordi).getTir();
	
			}break;
	
			case Constantes.DEBUTANT :  {
	
				tir =((JoueurDebutant) ordi).getTir();
				
			}break;
	
			case Constantes.INTERMEDIAIRE : {  
				
				// Écrivez le code nécessaire ici
				
			}break;
	
			case Constantes.AVANCE : {
				
				// Écrivez le code nécessaire ici
				
			}break;
			
			case Constantes.EXPERT :  {
	
				// Écrivez le code nécessaire ici
	
			}break;
		
		}
		return tir;		
	}
	/*
	 * Sélectionne la bonne stratégie selon le deuxième paramètre
	 * qui représente le niveau du joueur.
	 */	
	private static void aviserOrdi(Object ordi, int lequel, Coord tir){
		
		switch (lequel){

			case Constantes.PREMIERE_FOIS : {
						
				((JoueurPremiereFois) ordi).aviseTouche(tir);
				
			}break;
	
			case Constantes.DEBUTANT :  {
				
				((JoueurDebutant) ordi).aviseTouche(tir);
				
			}break;
				
			case Constantes.INTERMEDIAIRE :{  
				
				// Écrivez le code nécessaire ici
				
			}break;
	
			case Constantes.AVANCE :{
				
				// Écrivez le code nécessaire ici
				
			}break;
			
			case Constantes.EXPERT :  {
	
				// Écrivez le code nécessaire ici
	
			}break;
			
		}
	}
}