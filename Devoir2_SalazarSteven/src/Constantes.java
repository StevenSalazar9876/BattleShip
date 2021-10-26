import java.awt.Color;

/**
 * Contient les d�clarations de constantes globales pour le projet de bataille
 * navale. 
 *  
 * @author Pierre B�lisle 
 * @version Copyright A2021
 */
public class Constantes {
	
	//CONSTANTES
	// Constantes initiales pour les couleurs du jeu (valeurs arbitraires).
	public static final Color COULEUR_FOND = Color.WHITE;
	public static final Color COULEUR_TEXTE = Color.BLACK;

	public static final Color COULEUR_LIGNE_IDENTIQUE = Color.RED;
	public static final Color COULEUR_COLONNE_IDENTIQUE = Color.GREEN;

	public static final Color COULEUR_VALEUR_TROP = Color.YELLOW;
	
	public static final Color COULEUR_TROP_CONSECUTIF = Color.CYAN;
	
	// Les valeurs possibles d'une case dans la grille solution.
	public static final String TOUCHE= "X";
	
	// Vide une case dans le gui avec setValeur.
	public static final String VIDE_GUI = " ";

	// N�cessaire � l'affichage des options du menu.
	public static final String[] OPTIONS = 
		{"Premiere fois", "Debutant", "Intermediaire", "Avance", "Expert"};

	// Position dans le tableau des options du menu.
	public static final int PREMIERE_FOIS = 0;
	public static final int DEBUTANT = 1;
	public static final int INTERMEDIAIRE = 2;
	public static final int AVANCE = 3;
	public static final int EXPERT = 4;
	
	// Taille de la grille de jeu (� ajuster pour que tout le projet s'adapte).
	public static final int TAILLE = 10;
	
	// Les noms de bateaux.
	public static final String SOUS_MARIN = "sous-marin";
	public static final String PORTE_AVION = "porte-avion";
	public static final String DESTROYER = "destroyer";
	public static final String CROISEUR = "croiseur";
	public static final String CUIRASSE = "cuirasse";

}
