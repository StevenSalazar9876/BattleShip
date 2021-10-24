
import java.awt.Color;
import java.util.ArrayList;
/**
 * Solution du tp1A21 inf111
 * 
 * Regroupe tous les SP pour g�rer les r�gles du jeu et maintenir
 * le gui pour aider l'utilisateur � jouer � la bataille navale tel que d�crit
 * dans l'�nonc� fourni.
 * 
 * @author Pierre B�lisle 
 * @version Copyright A2021
 *
 */
public class UtilitaireGrilleGui {

	/**
	 * Utile pour contr�ler le temps de jeu et laisser le temps 
	 * � l'OS de prendre conscience des �v�nements du GUI.
	 * 
	 * @param tempsDeDelai
	 */
	public static void pause (int tempsDeDelai){
	       
			try {
				Thread.sleep(tempsDeDelai);
			} catch (InterruptedException e) {		
				e.printStackTrace();
			}
	}

	/**
	 * Montre les navire de la flotte dans le gui.
	 * 
	 * Change la couleur de fond des cases � la position des navires.
	 * 
	 * @param grilleJeu  La grille contenant les navires
	 * @param gui L'interface graphique � remplir
	 * @param pourc Le pourcentage de la grille � mettre dans le gui
	 */
	public static void montrerFlotte(Flotte flotte, GrilleGui gui){

		/*
		 * Strat�gie : Pour chaque Navire de la grille de jeu, on colore le fond
		 * de la position de debut � la position de fin avec la couleur 
		 * du navire.
		 */
		ArrayList<Navire> navires = flotte.obtenirFlotteAleatoire();
		int ligne = 0;
		int colonne =0;
		Coord coordonne = null;
		Color couleur =Color.black;
		ArrayList<Coord> coordonnes=null;
		
		for(int i=0; i< navires.size(); i++) {
			
			coordonnes = navires.get(i).nCoordNavire;
			couleur = navires.get(i).getnCouleurNavire();
			
			for(int j=0; j< coordonnes.size()-1; j++) {
				 ligne = coordonnes.get(j).ligne;
				 colonne = coordonnes.get(j).colonne;
				coordonne = new Coord(ligne,colonne);
				if(coordonne != null && couleur !=null) {
				gui.setCouleurFond(coordonne, couleur);
			
				}
			}
			
		}


		// � vous de jouer
	}

	/**
	 * Proc�dure pour changer la valeur d'une case du gui.
	 * 
	 * @param gui
	 * @param coord
	 * @param valeur
	 */
	public static void afficherTir(GrilleGui gui, Coord coord){
		
		gui.setValeur(coord, Constantes.TOUCHE);
	}
	
	/**
	 * Sert  � colorier la case o� le tir a touch�
	 * @param tir
	 * @param gui
	 */
	public static void afficherNavireTouche(GrilleGui gui, Coord tir){
		
		UtilitaireGrilleGui.setCouleurFondCase(gui, tir,java.awt.Color.RED);
	}
	
	/**
	 * Proc�dure priv�e pour r�initialiser le gui et changer la couleur de 
	 * toute une ligne.
	 * 
	 * @param gui

	 * 
	 * 
	 * @param ligne
	 * @param couleur
	 */
     private static void changerCouleurFondLigne(GrilleGui gui, 
    		                                     int ligne, 
    		                                     Color couleur){

    	 /**
    	  * Strat�gie : On utilise for pour parcourir toutes les colonnes
    	  *  (puisqu'on connait le nombre)             
    	  */
    	 
    	 //N�cessaire � l'appel de gui.setCouleurFond
		Coord coord = new Coord(0,0);

		for(int j = 0; j < gui.getNbColonnes(); j++){
			
			coord.ligne =ligne;
			coord.colonne = j;
			gui.setCouleurFond(coord, couleur);
		}

	}
	
	/**
	 * Change la couleur de fond d'une case du gui
	 * 
	 * @param gui
	 * @param coord La coordonn�e de la case � modifier.
	 * @param couleur
	 */
	public static void setCouleurFondCase(GrilleGui gui, 
			                              Coord coord,	
			                              Color couleur){
		
		 
		  gui.setCouleurFond(coord,  couleur);
	}

	/**
	 * Remet le texte du gui dans leurs conditions initiales Constantes.VIDE
	 * dans chaque case.
	 * 
	 * @param gui
	 */
	private static void viderCases(GrilleGui gui, int i){
		
		/**
		 * Strat�gie : On utilise for pour parcourir toutes les colonnes
		 *  (puisqu'on connait le nombre)             
		 */
		
		 //N�cessaire � l'appel de gui.setValeur
		Coord c = new Coord(0,0);

		for(int j = 0; j < gui.getNbColonnes(); j++){
			
			c.ligne = i;
			c.colonne = j;
			gui.setValeur(c, Constantes.VIDE_GUI);
		}
	}	
	
	/**
	 * Remet les couleurs du gui � Constantes.COULEUR_FOND
	 * 
	 * @param gui
	 */
	public static void reinitialiserGui(GrilleGui gui){
		

		// On utilise les SP pr�vus � cet effet.
		for(int i = 0; i < gui.getNbLignes(); i++){
			changerCouleurFondLigne(gui,i,Constantes.COULEUR_FOND);
			viderCases(gui,i);
		}
	}
	
}