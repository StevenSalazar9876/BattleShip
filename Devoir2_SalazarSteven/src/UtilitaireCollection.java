
import java.util.Vector;

public class UtilitaireCollection {

	/**
	 * Génère aléatoirement des coordonnées jusqu'à ce qu'une d'entre elles
	 * ne fasse pas partie du tableau reçu
	 * 
	 * @param tableau
	 * 
	 * @return Une nouvelle coordonnée non déjà attribuée.
	 */
	public static Coord obtenirCoupPasDejaJouer(Vector tableau){

		Coord c;

		do{

			c  = UtilitaireFonctions.coordAleatoire();

			//S'arrête avec un coup pas déjà joué
		}while(tableauContientCoord(tableau, c));

		return c;

	}
	
	/**
	 * Équivalent de contains sauf qu'on regarde le contenu des coordonnées
	 *  et non seulement leur références (deepContains)
	 * @param tableau
	 * @param c
	 * @return
	 */

	public static boolean tableauContientCoord(Vector tableau, Coord c){

		/*
		 * Stratégie : On regarde chaque coup du tableau et s'il est identique
		 * au coup c reçcu.  La boucle s'arrête au tour suivant si elle 
		 * le trouve.
		 */
		boolean existe = false;

		//itérateur
		int  i = 0;

		
		while(i < tableau.size() && !existe){
			
			existe = c.equals((Coord)tableau.get(i));
			i++;
		}				 

		return existe;
	}
}